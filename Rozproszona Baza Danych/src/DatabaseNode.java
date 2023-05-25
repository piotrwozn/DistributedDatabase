/*
  autor: Piotr Woźnicki
  numer studenta: so0139
  projekt: Rozproszona Baza Danych
  przedmiot: SKJ-Sieci komputerowe i programowanie sieciowe w języku Java
  grupa: 24c
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;


@SuppressWarnings("SuspiciousListRemoveInLoop")
public class DatabaseNode implements Runnable {
    private final int tcpport;
    private final List<NodeInfo> neighbors;
    private final List<String> questions = new ArrayList<>();
    private final AtomicLong id = new AtomicLong(0);
    private volatile int key;
    private volatile int value;
    private volatile ServerSocket serverSocket;
    private volatile boolean running;
    private boolean isFirst;

    private final Map<String, ResponseProcessor> messageProcessors = new HashMap<String, ResponseProcessor>() {{
        put(Constants.GET, (inputLine, fromNode, isAnswered) -> getResponse(inputLine, fromNode, isAnswered));
        put(Constants.SET, (inputLine, fromNode, isAnswered) -> setResponse(inputLine, fromNode, isAnswered));
        put(Constants.FIND, (inputLine, fromNode, isAnswered) -> findResponse(inputLine, fromNode, isAnswered));
        put(Constants.MAX, (inputLine, fromNode, isAnswered) -> maxResponse(inputLine, fromNode, isAnswered));
        put(Constants.MIN, (inputLine, fromNode, isAnswered) -> minResponse(inputLine, fromNode, isAnswered));
        put(Constants.TERMINATE, (inputLine, fromNode, isAnswered) -> terminateResponse(inputLine));
        put(Constants.TERMINATEALL, (inputLine, fromNode, isAnswered) -> terminateAll(fromNode, inputLine));
        put(Constants.ADDNEIGHBOUR, (inputLine, fromNode, isAnswered) -> addNeighbour(inputLine));
        put(Constants.RUFIRST, (inputLine, fromNode, isAnswered) -> checkConnection(inputLine, isAnswered, false));
        put(Constants.YOUFIRST, (inputLine, fromNode, isAnswered) -> youFirst());
    }};


    /**
     * konstruktor ma na celu przypisanie wartości do nowo utworzonego węzła oraz stworzenie socket serwera
     *
     * @param tcpport   - port, na którym jest włączony węzeł
     * @param key       - klucz przypisany do węzła
     * @param value     - wartość przypisana do węzła
     * @param neighbors - lista sąsiadujących węzłów
     * @param isFirst   - zmienna, która mówi, czy jest to pierwszy węzeł
     */
    public DatabaseNode(int tcpport, int key, int value, List<NodeInfo> neighbors, boolean isFirst) {
        this.tcpport = tcpport;
        this.key = key;
        this.value = value;
        this.neighbors = neighbors;
        this.isFirst = isFirst;
        this.running = true;

        try {
            this.serverSocket = new ServerSocket(tcpport, 100, InetAddress.getByName("localhost"));
        } catch (IOException e) {
            System.err.println("Error: Port : " + this.tcpport + " already in use");
            System.exit(1);
        }
    }

    /**
     * metoda ma na celu określenie wartości portu klucza i wartości, ale tworzy też listę węzłów sąsiadujących
     *
     * @param args - tablica argumentów, które podawane są podczas startu działania
     */
    public static void main(String[] args) {
        // Parsowanie argumentów wejściowych
        int tcpport = 0; // port TCP dla połączenia z innymi węzłami
        int key = 0; // klucz dla pierwszego rekordu
        int value = 0; // wartość dla pierwszego rekordu
        boolean isFirst = false; // czy węzeł jest pierwszym w sieci
        List<NodeInfo> neighbors = new ArrayList<>(); // lista sąsiadów węzła
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-tcpport":
                    tcpport = Integer.parseInt(args[++i]);
                    break;
                case "-record":
                    String[] record = args[++i].split(":");
                    key = Integer.parseInt(record[0]);
                    value = Integer.parseInt(record[1]);
                    break;
                case "-connect":
                    String[] address = args[++i].split(":");
                    String host = address[0];
                    int port = Integer.parseInt(address[1]);
                    neighbors.add(new NodeInfo(host, port));
                    break;
            }
        }
        if (neighbors.size() == 0) {
            isFirst = true;
        }
        // Stworzenie nowej instancji klasy DatabaseNode i przypisanie do niej wcześniej zdobytych wartości
        DatabaseNode node = new DatabaseNode(tcpport, key, value, neighbors, isFirst);
        // Włączenie wątku węzła sieci
        node.run();
    }

    /**
     * metoda ma na celu oczekiwanie na wiadomość od klienta lub od innego węzła
     */
    @Override
    public void run() {
        // Wypisanie komunikatu o uruchomieniu serwera
        System.out.println("Wlaczanie serwera");
        // Dodanie sąsiadów do węzła (jeśli podano ich adresy w pliku konfiguracyjnym)
        addNeighbours();
        // Wypisanie komunikatu o oczekiwaniu na połączenie
        System.out.println("Serwer oczekuje na polaczenie");
        // Pętla przyjmująca połączenia od klientów lub nowych węzłów
        while (running) {
            try {
                // Oczekiwanie na połączenie od klienta lub nowego węzła
                Socket clientSocket = serverSocket.accept();
                // Tworzenie nowego wątku dla obsługi wiadomości od klienta/węzła
                MessageHandler messageHandler = new MessageHandler(clientSocket, this);
                new Thread(messageHandler).start();
            } catch (IOException e) {
                // Jeśli socket został zamknięty, to wychodzimy z pętli
                if (!serverSocket.isClosed()) {
                    System.err.println("Error: Could not accept connection from client or node.");
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * metoda ma na celu zmianę wartości key i value dla danego węzła
     *
     * @param key   - nowy klucz
     * @param value - nowa wartość value
     * @return "OK" - wartość zwracana do klienta po zmianie wartości
     */
    public String changeRecord(int key, int value) {
        // Zmienienie wartości klucza i węzła dla węzła
        this.key = key;
        this.value = value;
        return Constants.OK;
    }


    /**
     * metoda ma na celu zwrócenie wartości key i value węzła
     *
     * @return klucz: wartość węzła, do którego podłączony jest klient
     */
    public String getValue() {
        return this.key + ":" + this.value;
    }


    /**
     * metoda ma na celu znaleźć największą lub najmniejszą wartość w sieci
     *
     * @param comparisonType - zmienna, która ma określić czy wykonujemy metode MIN, czy MAX
     * @param fromNode       - zmienna, która ma powiedzieć czy funkcja została wywołana
     *                       przez zapytanie od węzła, czy od klienta
     * @param message        - wiadomość, która otrzymał węzeł od innego węzła, jeżeli zapytanie było od klienta to ta zmienna
     *                       jest pusta
     * @return klucz: wartość zwraca największą lub najmniejsza wartość
     * @throws IOException - błąd występujący przy socket
     */
    public String getExtremum(String comparisonType, boolean fromNode, String message) throws IOException {
        // Zmienne do przechowywania największej/najmniejszej wartości oraz klucza
        int extremumValue = value;
        int extremumKey = key;
        String getExtremumMessage;
        List<String> responses = new ArrayList<>();
        List<Socket> sockets;
        int i = 0;

        // Wysłanie wiadomości do sąsiadów z prośbą o zwrot największej/najmniejszej wartości
        if (fromNode) {
            getExtremumMessage = message;
            sockets = sendMessageToNeighbors(getExtremumMessage);
        } else {
            getExtremumMessage = Constants.NODE + " " + tcpport + " " + id + " " + comparisonType;
            sockets = sendMessageToNeighbors(getExtremumMessage);
            id.getAndIncrement();
            addQuestion(getExtremumMessage);
        }

        // Pobranie odpowiedzi od sąsiadów
        getResponses(responses, sockets, i);
        // Porównanie wartości odpowiedzi z największą/najmniejszą wartością
        for (String response : responses) {
            String[] parts = response.split(":");
            int neighborKey = Integer.parseInt(parts[0]);
            int neighborValue = Integer.parseInt(parts[1]);
            if ((neighborValue > extremumValue && comparisonType.equals(Constants.MAX)) || (neighborValue < extremumValue && comparisonType.equals(Constants.MIN))) {
                extremumValue = neighborValue;
                extremumKey = neighborKey;
            }
        }

        // Zwracanie największej/najmniejszej wartości
        return extremumKey + ":" + extremumValue;
    }

    /**
     * metoda obsługuje operacje "get-value","set-value","find-key" i komunikaty od innych węzłów "GET","SET","FIND"
     * @param fromNode - zmienna, która ma powiedzieć czy funkcja została wywołana
     *                 przez zapytanie od węzła, czy od klienta
     * @param message - wiadomość, która otrzymał węzeł od innego węzła, jeżeli zapytanie było od klienta to ta zmienna
     *                 jest pusta
     * @param requestType - zmienna, która ma określić czy wykonujemy metode GET, SET, czy FIND
     * @return response - zmienna zależna od metody, która wykonujemy, ale w każdym przypadku, jeśli jest pusta to zwracamy
     *                      "ERROR"
     * @throws IOException - błąd występujący przy socket
     */
    public String handleRequest(boolean fromNode, String message, String requestType) throws IOException {
        // Deklaracja zmiennych do przechowywania klucza, wartości, wiadomości żądania i odpowiedzi
        int key;
        int value = 0;
        String requestMessage = null;
        List<String> responses = new ArrayList<>();
        List<Socket> sockets;
        // Zmienna pomocnicza
        int i = 0;
        // Tablica ciągów na podstawie podzielonej wiadomości
        String[] strings = message.split(" ");
        // Sprawdzenie, czy typ żądania to GET lub FIND
        boolean b = requestType.equals(Constants.GET) || requestType.equals(Constants.FIND);
        if(fromNode) {
            // Pobranie klucza z tablicy ciągów
            key = Integer.parseInt(strings[4]);
            if (!b) {
                // Pobranie wartości z tablicy ciągów
                value = Integer.parseInt(strings[5]);
            }
        } else {
            if(b) {
                key = Integer.parseInt(strings[1]);
            } else {
                String[] tokens = strings[1].split(":");
                key = Integer.parseInt(tokens[0]);
                value = Integer.parseInt(tokens[1]);
            }
        }

        switch (requestType) {
            case Constants.GET:
                // Sprawdzenie, czy klucz jest taki sam jak w klasie
                if (this.key == key) {
                    // Zwracanie klucza i wartości
                    return this.key + ":" + this.value;
                }
                break;
            case Constants.SET:
                // Sprawdzenie, czy klucz jest taki sam jak w klasie
                if (this.key == key) {
                    // Ustawienie wartości
                    this.value = value;
                    return Constants.OK;
                }
                break;
            case Constants.FIND:
                // Sprawdzenie, czy klucz jest taki sam jak w klasie
                if (this.key == key) {
                    // Zwracanie adresu IP i portu serwera
                    return serverSocket.getInetAddress().getHostAddress() + ":" + serverSocket.getLocalPort();
                }
                break;
        }
        if (fromNode) {
            requestMessage = message;
            // Wywołanie metody do wysłania wiadomości do sąsiadów
            sockets = sendMessageToNeighbors(requestMessage);
        } else {
            switch (requestType) {
                case Constants.GET:
                case Constants.FIND:
                    requestMessage = Constants.NODE + " " + tcpport + " " + id + " " + requestType + " " + key;
                    break;
                case Constants.SET:
                    requestMessage = Constants.NODE + " " + tcpport + " " + id + " " + requestType + " " + key + " " + value;
                    break;
            }
            // Wywołanie metody do wysłania wiadomości do sąsiadów
            sockets = sendMessageToNeighbors(requestMessage);
            // Inkrementacja id
            id.getAndIncrement();
            // Dodanie pytania
            addQuestion(requestMessage);
        }
        // Pobranie odpowiedzi
        String response = getCheckResponse(responses, sockets, i);
        // Zwracanie odpowiedzi lub komunikatu bledu
        return response != null ? response : Constants.ERROR;
    }

    /**
     * metoda ma na celu wyłączenie jednego węzła, funkcja wysyła też do wszystkich swoich sąsiadów wiadomość,
     * aby usunąć go z listy sąsiadów
     *
     * @return "OK"-wiadomość, która otrzymuje klient po zamknięciu węzła
     * @throws IOException - błąd występujący przy socket
     */
    public String terminate() throws IOException {
        if (isFirst && neighbors.size() > 0) {
            // Jeśli jest to pierwszy węzeł i ma sąsiadów, wysyła komunikat YOUFIRST do jednego z nich
            String message = Constants.NODE + " " + tcpport + " " + id + " " + Constants.YOUFIRST;
            sendMessageToNeighbor(message);
        }
        // Wysłanie wiadomości do sąsiadów z informacją o zakończeniu pracy
        StringBuilder terminateMessage = new StringBuilder(Constants.NODE + " " + tcpport + " " + id + " " + Constants.TERMINATE);
        for (NodeInfo neighbor : neighbors) {
            terminateMessage.append(" ").append(neighbor.getAddress()).append(":").append(neighbor.getPort());
        }
        sendMessageToNeighbors(terminateMessage.toString());
        // Zakończenie pracy węzła
        System.out.println("Wylaczenie wezla i zwolnienie socketu");
        this.running = false;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            System.err.println("Error: Could not close server socket.");
            e.printStackTrace();
        }
        // Zwracanie odpowiedzi dla klienta
        return Constants.OK;
    }

    /**
     * Metoda odpowiedzialna za zakończenie działania wszystkich węzłów
     * @param fromNode - określa czy wiadomość zostaje przesłana od innego węzła
     * @param inputLine - ciąg znaków przesłany przez klienta lub inny węzeł
     * @return - zwraca informację dla klienta lub pusty ciąg znaków
     * @throws IOException - w przypadku problemów z połączeniem
     */
    public String terminateAll(boolean fromNode, String inputLine) throws IOException {
        running = false;
        // Wysłanie wiadomości do sąsiadujących węzłów i usunięcie węzła z sąsiadów
        String terminateAllMessage = Constants.NODE + " " + tcpport + " " + id + " " + Constants.TERMINATEALL;
        if (fromNode) {
            String[] tokens = inputLine.split(" ");
            for (int i = 0; i < neighbors.size(); i++) {
                if (neighbors.get(i).getPort() == Integer.parseInt(tokens[1])) {
                    neighbors.remove(i);
                }
            }
        }
        List<Socket> sockets = sendMessageToNeighbors(terminateAllMessage);
        // Zamkniecie socket, które wysyłały wiadomości do sąsiadów
        for (Socket socket : sockets) {
            socket.close();
        }
        // Zamkniecie głównego socket serwera i wyłączenie węzła
        System.out.println("Wylaczenie wezla i zwolnienie socketu");
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            System.err.println("Error: Could not close server socket.");
            e.printStackTrace();
        }
        if (fromNode) {
            return Constants.NOTHING;
        } else {
            // Zwrócenie wiadomości do klienta
            return Constants.OK;
        }
    }
    /**
     * metoda sprawdza, czy pytanie nie powtórzyło się już wcześniej
     *
     * @param inputLine - wiadomość od innego węzła
     * @return answer-zmienna, która przyjmuje wartość true, jeżeli pytanie już występowało i false, jeżeli
     * nie wystąpiło wcześniej
     */
    public boolean checkQuestions(String inputLine) {
        // Zmienna przechowująca odpowiedź na pytanie, czy dana wiadomość została już odpowiedziana
        boolean answer = false;
        try {
            // Przeszukanie listy pytań
            for (String next : questions) {
                // Sprawdzenie, czy wiadomość znajduje się na liście pytań
                if (next.equals(inputLine)) {
                    answer = true;
                    break;
                }
            }
        } catch (NullPointerException | ConcurrentModificationException ignored) {
        }
        // Zwrot odpowiedzi
        return answer;
    }



    /**
     * metoda ma na celu dodanie pytania do listy
     *
     * @param inputLine - wiadomość, która otrzymał węzeł od innego węzła
     */
    public void addQuestion(String inputLine) {
        questions.add(inputLine);
    }

    /**
     * Metoda przetwarzająca otrzymaną wiadomość od innego węzła
     * @param inputLine wiadomość do przetworzenia
     * @return odpowiedź na wiadomość
     * @throws IOException w przypadku błędu w komunikacji
     */
    public String processMessage(String inputLine) throws IOException {
        // Podział otrzymanej wiadomości na tokeny
        String[] tokens = inputLine.split(" ");
        // Pobranie komendy z wiadomości
        String command = tokens[3];
        String response = Constants.NOTHING;
        // Sprawdzenie, czy wiadomość nie była już wcześniej odpowiedziana
        boolean isAnswered = checkQuestions(inputLine);
        if (!isAnswered) {
            // Dodanie wiadomości do listy pytań
            addQuestion(inputLine);
        }
        // Sprawdzenie komendy od innego węzła
        try {
            if (messageProcessors.containsKey(command)) {
                // Wywołanie odpowiedniej metody przetwarzającej wiadomość
                response = messageProcessors.get(command).process(inputLine, true, isAnswered);
            }
        } catch (SocketException ignored) {
        }
        return response;
    }

    private String youFirst() {
        // Ustawienie zmiennej isFirst na true
        isFirst = true;
        // Zwrócenie komunikatu o braku dalszej akcji
        return Constants.NOTHING;
    }

    private void sendMessageToNeighbor(String message) {
        try {
            // Pobranie pierwszego sąsiada z listy
            NodeInfo neighbor = neighbors.get(0);
            // Pobranie adresu i portu sąsiada
            String host = neighbor.getAddress();
            int port = neighbor.getPort();
            // Stworzenie gniazda i połączenie z sąsiadem
            Socket socket = new Socket(host, port);
            // Utworzenie strumienia wyjściowego i wysłanie wiadomości
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(message);
            // Zamknięcie gniazda
            socket.close();
        } catch (IOException ignored) {
        }
    }

    private String addNeighbour(String inputLine) {
        // Rozdzielenie ciągu znaków na poszczególne elementy
        String[] tokens = inputLine.split(" ");
        // Dodanie nowego sąsiada do listy sąsiadów
        neighbors.add(new NodeInfo(tokens[1], Integer.parseInt(tokens[2])));
        System.out.println("Dodawanie sasiada : " + neighbors.get(neighbors.size() - 1));
        // Zwrócenie braku odpowiedzi
        return Constants.NOTHING;
    }

    private String terminateResponse(String inputLine) {
        // Pobranie numeru portu z wiadomości
        String[] tokens = inputLine.split(" ");
        // Usuwanie sąsiada z listy
        for (int i = 0; i < neighbors.size(); i++) {
            if (neighbors.get(i).getPort() == Integer.parseInt(tokens[1])) {
                neighbors.remove(i);
            }
        }
        // Usuwanie zapytania związanego z tym sąsiadem z listy
        for (int j = 0; j < questions.size(); j++) {
            if (questions.get(j).contains(tokens[1])) {
                questions.remove(j);
            }
        }
        // Sprawdzenie, czy sąsiad jest pierwszym
        String answer = checkConnection(inputLine, false, true);
        if (answer.equals(Constants.ERROR)) {
            // Dodanie nowych sąsiadów
            String[] strings = inputLine.split(" ");
            for (int j = 0; j < strings.length; j++) {
                if (j >= 4) {
                    String[] tokens2 = strings[j].split(":");
                    if (!Objects.equals(tokens2[0], serverSocket.getInetAddress().getHostAddress()) && Integer.parseInt(tokens2[1]) != serverSocket.getLocalPort()) {
                        neighbors.add(new NodeInfo(tokens2[0], Integer.parseInt(tokens2[1])));
                    }
                }
            }
            // Opóźnienie wysłania wiadomości
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // Wysłanie wiadomości do nowych sąsiadów o dodanie do listy
            addNeighbours();
        }
        // Zwrócenie braku odpowiedzi
        return Constants.NOTHING;
    }



    private String minResponse(String inputLine, boolean fromNode, boolean isAnswered) throws IOException {
        // Sprawdzenie, czy już odpowiedziano
        if (isAnswered) {
            // Zwracanie klucza i wartości
            return key + ":" + value;
        } else {
            // Obsługa żądania
            return getExtremum(Constants.MIN, fromNode, inputLine);
        }
    }

    private String maxResponse(String inputLine, boolean fromNode, boolean isAnswered) throws IOException {
        // Sprawdzenie, czy już odpowiedziano
        if (isAnswered) {
            // Zwracanie klucza i wartości
            return key + ":" + value;
        } else {
            // Obsługa żądania
            return getExtremum(Constants.MAX, fromNode, inputLine);
        }
    }

    private String findResponse(String inputLine, boolean fromNode, boolean isAnswered) throws IOException {
        // Podział linii wejściowej na tokeny
        String[] tokens = inputLine.split(" ");
        System.out.println("Klucz : " + tokens[4]);
        int key = Integer.parseInt(tokens[4]);
        // Sprawdzenie, czy już odpowiedziano
        if (isAnswered) {
            // Sprawdzenie, czy klucz jest zgodny z kluczem węzła
            if (this.key == key) {
                // Zwracanie adresu IP i portu
                return serverSocket.getInetAddress().getHostAddress() + ":" + serverSocket.getLocalPort();
            } else {
                // Zwracanie błędu
                return Constants.ERROR;
            }
        } else {
            // Obsługa żądania
            return handleRequest(fromNode,inputLine,Constants.FIND);
        }
    }

    private String setResponse(String inputLine, boolean fromNode, boolean isAnswered) throws IOException {
        // Podział linii wejściowej na tokeny
        String[] tokens = inputLine.split(" ");
        System.out.println("Klucz : " + tokens[4]);
        System.out.println("Wartosc : " + tokens[5]);
        int key = Integer.parseInt(tokens[4]);
        int value = Integer.parseInt(tokens[5]);
        // Sprawdzenie, czy już odpowiedziano
        if (isAnswered) {
            // Sprawdzenie, czy klucz jest zgodny z kluczem węzła
            if (this.key == key) {
                // Ustawienie wartości
                this.value = value;
                return Constants.OK;
            } else {
                // Zwracanie błędu
                return Constants.ERROR;
            }
        } else {
            // Obsługa żądania
            return handleRequest(fromNode,inputLine,Constants.SET);
        }

    }
    private String getResponse(String inputLine, boolean fromNode, boolean isAnswered) throws IOException {
        // Podział linii wejściowej na tokeny
        String[] tokens = inputLine.split(" ");
        System.out.println("Klucz : " + tokens[4]);
        int key = Integer.parseInt(tokens[4]);
        // Sprawdzenie, czy już odpowiedziano
        if (isAnswered) {
            // Sprawdzenie, czy klucz jest zgodny z kluczem węzła
            if (this.key == key) {
                // Zwracanie klucza i wartości
                return this.key + ":" + this.value;
            } else {
                // Zwracanie błędu
                return Constants.ERROR;
            }
        } else {
            // Obsługa żądania
            return handleRequest(fromNode,inputLine,Constants.GET);
        }
    }


    private List<Socket> sendMessageToNeighbors(String message) throws IOException {
        // Stworzenie listy socket, do których węzeł wysłał zapytanie
        List<Socket> sockets = new ArrayList<>();
        try {
            // Wysłanie zapytania do sąsiadów
            for (NodeInfo neighbor : this.neighbors) {
                String host = neighbor.getAddress();
                int port = neighbor.getPort();
                // Tworzenie gniazda
                Socket socket = new Socket(host, port);
                // Dodanie gniazda do listy
                sockets.add(socket);
                // Tworzenie PrintWriter do wysłania wiadomości
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                // Wysłanie wiadomości
                out.println(message);
            }
        } catch (ConcurrentModificationException | SocketException ignored) {
        }
        // Zwrócenie listy socket
        return sockets;
    }

    private void getResponses(List<String> responses, List<Socket> sockets, int i) throws IOException {
        // Pętla do pobierania odpowiedzi od wszystkich sąsiadów
        while (responses.size() < neighbors.size()) {
            // Pobieranie odpowiedzi od sąsiadów
            BufferedReader in = new BufferedReader(new InputStreamReader(sockets.get(i).getInputStream()));
            String response = in.readLine();
            // Dodanie odpowiedzi do listy
            responses.add(response);
            // Zamykanie gniazda
            sockets.get(i).close();
            i++;
        }
    }

    private void addNeighbours() {
        try {
            // Wysłanie prośby do sąsiadów o dodanie do listy
            System.out.println("Wyslanie prosby do neighbours o dodanie do listy");
            // Tworzenie listy gniazd
            List<Socket> sockets = sendMessageToNeighbors(Constants.NODE + " " + serverSocket.getInetAddress().getHostAddress() + " " + serverSocket.getLocalPort() + " " + Constants.ADDNEIGHBOUR);
            // Zamykanie gniazd
            for (Socket socket : sockets) {
                socket.close();
            }
        } catch (IOException e) {
            // Rzucanie wyjątku
            throw new RuntimeException(e);
        }
    }

    private String getCheckResponse(List<String> responses, List<Socket> sockets, int i) throws IOException {
        getResponses(responses, sockets, i);
        // Sprawdzanie otrzymanych odpowiedzi
        for (String response : responses) {
            if (!response.equals(Constants.ERROR)) {
                // Zwracanie adresu i portu znalezionego węzła
                return response;
            }
        }
        return null;
    }

    public List<String> getQuestions() {
        return questions;
    }

    private String checkConnection(String inputLine, boolean isAnswered, boolean first) {
        // Sprawdzenie, czy już odpowiedziano
        if (isAnswered) {
            if (isFirst) {
                // Zwracanie OK, jeśli jest pierwszy
                return Constants.OK;
            } else {
                // Zwracanie błędu, jeśli nie jest pierwszy
                return Constants.ERROR;
            }
        } else {
            // Sprawdzenie, który jest pierwszy
            return checkFirst(inputLine, first);
        }
    }

    private String checkFirst(String inputLine, boolean first) {
        // Lista odpowiedzi
        List<String> responses = new ArrayList<>();
        int i = 0;
        // Lista gniazd
        List<Socket> sockets;
        // Zmienna odpowiedzi
        String answer = Constants.ERROR;
        // Sprawdzenie, czy jest pierwszy
        if (isFirst) {
            return Constants.OK;
        } else {
            try {
                // Wysłanie wiadomości do sąsiadów w celu sprawdzenia, który jest pierwszy
                if (first) {
                    String message = "NODE " + tcpport + " " + id + " " + Constants.RUFIRST;
                    sockets = sendMessageToNeighbors(message);
                } else {
                    sockets = sendMessageToNeighbors(inputLine);
                }
                // Pobranie odpowiedzi
                getResponses(responses, sockets, i);

                for (int j = 0; j < responses.size(); j++) {
                    if (responses.get(i).equals(Constants.OK)) {
                        answer = Constants.OK;
                        break;
                    }
                }
            } catch (IOException ignored) {
            }
            // Zwracanie odpowiedzi
            return answer;
        }
    }

}
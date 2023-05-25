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
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class MessageHandler implements Runnable {
    private final Socket clientSocket;
    private final DatabaseNode node;
    private String response = "Nie ma takiej komendy";
    private String input;
    private final boolean fromNode = false;
    private final Map<String, Runnable> messageProcessors = new HashMap<String, Runnable>() {{
        put(Constants.getValue, () -> getValue());
        put(Constants.setValue, () -> setValue());
        put(Constants.findKey, () -> findKey());
        put(Constants.getMax, () -> getMax());
        put(Constants.getMin, () -> getMin());
        put(Constants.terminate, () -> terminate());
        put(Constants.terminateAll, () -> terminateAll());
        put(Constants.newRecord, () -> newRecord());
        put(Constants.getCurrent, () -> getValueAndKey());
        put(Constants.NODE, () -> node());
    }};

    /**
     * konstruktor ma na celu przypisanie wartości do nowo stworzonej instancji klienta
     *
     * @param clientSocket - socket, na którym podłączony jest client lub węzeł.
     *                     Socket ten służy do odbioru wiadomości od klienta lub węzła, jak i wysłania odpowiedzi
     * @param node         - węzeł, do którego przypisany jest klient
     */
    public MessageHandler(Socket clientSocket, DatabaseNode node) {
        this.clientSocket = clientSocket;
        this.node = node;
    }


    /**
     * metoda ma na celu przetworzenie wiadomości, jeżeli jest ona od innego węzła lub klienta
     */
    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            boolean gate = true;
            while (gate) {
                // Serwer oczekuje na wiadomość od klienta lub węzła
                input = in.readLine();
                String[] tokens = input.split(" ");
                // Uśpienie wątku przy otrzymaniu wiadomości od klienta
                if(!tokens[0].equals("NODE")) {
                    Thread.sleep(1000);
                }
                // Sprawdzenie komendy i wywołanie odpowiedniej metody
                check(tokens[0]);

                // Wysłanie odpowiedzi do klienta
                answer(out);
                clientSocket.close();
                gate = false;
            }

        } catch (IOException | InterruptedException ignored) {
        }
    }

    private void answer(PrintWriter out) {
        // jeśli odpowiedź jest pusta, to nie wysyłamy nic
        if (!response.equals(Constants.NOTHING)) {
            System.out.println("Odpowiedz : " + response);
            // wysłanie odpowiedzi do klienta lub węzła
            out.println(response);
        }
    }

    private void check(String command) {
        // Pobranie metody przypisanej do komendy lub metody domyślnej (zwracającej komunikat)
        Runnable commandProcessor = messageProcessors.getOrDefault(command, () -> response = "Nie ma takiej komendy");
        // Wywołanie metody
        commandProcessor.run();
    }






    private void getValueAndKey() {
        response = node.getValue();
    }

    private void node() {
        String[] tokens = input.split(" ");
        // Wypisanie informacji o przyjęciu komendy od innego węzła
        System.out.println("Przyjeto komende od innego wezla :");
        System.out.println(tokens[3]);
        try {
            // Przetworzenie komendy przesłanej przez inny węzeł
            response = node.processMessage(input);
        } catch (IOException ignored) {
        }
    }


    private void findKey() {
        // Pobranie komendy od klienta
        String[] tokens = input.split(" ");
        infoClient(tokens[0]);
        // Wypisanie klucza, którego szukamy
        System.out.println("Klucz : " + tokens[1]);
        try {
            // Wysłanie żądania do węzła
            response = node.handleRequest(fromNode,input,Constants.FIND);
        } catch (IOException ignored) {
        }
    }
    private void getMin() {
        String[] tokens = input.split(" ");
        // Wypisanie informacji o otrzymanej komendzie od klienta
        infoClient(tokens[0]);
        try {
            // Pobranie minimalnej wartości za pomocą metody getExtremum() z klasy Node
            response = node.getExtremum(Constants.MIN, fromNode, "");
        } catch (IOException ignored) {
        }
    }

    private void terminateAll() {
        // Wyświetlenie informacji o otrzymanej komendzie od klienta
        String[] tokens = input.split(" ");
        infoClient(tokens[0]);
        try {
            // Wywołanie metody zakończenia działania wszystkich węzłów
            response = node.terminateAll(fromNode, input);
        } catch (IOException ignored) {
        }
    }

    private void terminate() {
        // Wypisanie informacji o przyjęciu komendy od klienta
        String[] tokens = input.split(" ");
        infoClient(tokens[0]);
        try {
            // Wywołanie metody zakończenia pracy węzła i zapisanie odpowiedzi
            response = node.terminate();
        } catch (IOException ignored) {
        }
    }

    private void getMax() {
        // Pobranie pierwszego tokenu z komendy, która została przesłana przez klienta
        String[] tokens = input.split(" ");
        // Wypisanie komendy, która została przesłana przez klienta
        infoClient(tokens[0]);
        try {
            // Pobranie wartości maksymalnej w sieci
            response = node.getExtremum(Constants.MAX, fromNode, "");
        } catch (IOException ignored) {
        }
    }

    private void newRecord() {
        // Pobranie klucza i wartości z wejściowego ciągu znaków
        int[] keyAndValue = getKeyAndValue();
        // Wywołanie metody odpowiedzialnej za zmianę rekordu węzła
        response = node.changeRecord(keyAndValue[0], keyAndValue[1]);
    }

    private void setValue() {
        try {
            // wywołanie metody obsługującej żądanie SET na obiekcie klasy Node
            response = node.handleRequest(fromNode,input,Constants.SET);
        } catch (IOException ignored) {
        }
    }

    private int[] getKeyAndValue() {
        // Pobranie informacji od klienta
        String[] tokens = input.split(" ");
        infoClient(tokens[0]);
        // Parsowanie klucza i wartości z otrzymanej wiadomości
        String[] values = tokens[1].split(":");
        int[] keyAndValue = new int[2];
        keyAndValue[0] = Integer.parseInt(values[0]);
        keyAndValue[1] = Integer.parseInt(values[1]);
        // Wypisanie klucza i wartości
        System.out.println("Klucz : " + keyAndValue[0]);
        System.out.println("Wartosc : " + keyAndValue[1]);
        return keyAndValue;
    }

    private void getValue() {
        // Podzielenie ciągu wejściowego na tokeny
        String[] tokens = input.split(" ");
        // Wypisanie informacji o komendzie od klienta
        infoClient(tokens[0]);
        // Wypisanie klucza przesłanego przez klienta
        System.out.println("Klucz : " + tokens[1]);
        try {
            // Wywołanie metody handleRequest z komendą GET
            response = node.handleRequest(fromNode,input,Constants.GET);
        } catch (IOException ignored) {
        }
    }

    private void infoClient(String tokens) {
        // Wypisanie informacji o otrzymanej komendzie od klienta
        System.out.println("Przyjeto komende od klienta :");
        System.out.println(tokens);
    }
}
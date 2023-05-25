A.DEFINICJA

Rozproszona baza danych
Rozproszona baza danych (ang. distributed database) to baza danych, która jest roz³o¿ona na kilku hostach lub
maszynach i umo¿liwia dostêp do danych z dowolnego miejsca w sieci. Rozproszona baza danych ma na celu
zwiêkszenie niezawodnoœci i wydajnoœci oraz umo¿liwienie dostêpu do danych z ró¿nych miejsc w sieci.
W przeciwieñstwie do centralnej bazy danych, w której dane s¹ przechowywane i przetwarzane na jednym serwerze,
rozproszona baza danych rozprowadza dane i obci¹¿enie procesora pomiêdzy wieloma serwerami, co pozwala na lepsze
wykorzystanie zasobów i lepsz¹ skalowalnoœæ.

TCP
Protokó³ TCP (Transmission Control Protocol) jest to protokó³ warstwy transportowej sieci komputerowej, który umo¿liwia
niezawodne przesy³anie danych pomiêdzy komputerami w sieci. TCP dzia³a poprzez nadawanie danych w pakietach
(ang. packets) i kontrolowanie przep³ywu danych pomiêdzy urz¹dzeniami w sieci, aby upewniæ siê, ¿e dane s¹ poprawnie
przes³ane i odebrane. Protokó³ TCP zapewnia niezawodnoœæ poprzez wysy³anie potwierdzeñ odbioru dla ka¿dego pakietu
danych oraz mo¿liwoœæ ponownego wys³ania pakietów, jeœli s¹ one utracone lub uszkodzone w trakcie transmisji.
Protokó³ TCP jest czêsto u¿ywany do przesy³ania danych przez Internet i jest jednym z najwa¿niejszych protoko³ów sieciowych.

(Ÿród³o dla definicji TCP I Rozproszona baza danych https://chat.openai.com/chat)

S¹siadowanie wêz³ów
Wêz³y uznajemy za wêz³y s¹siaduj¹ce, je¿eli wêz³y s¹ po³¹czone i mo¿liwa jest komunikacja miêdzy nimi w celu wykonania
operacji.

B.WSTÊP

G³ównym celem projektu jest stworzenie rozproszonej bazy danych, któr¹ bêdzie mo¿na skalowaæ. Oznacza to,
¿e wraz ze wzrostem iloœci u¿ytkowników lub iloœci przechowywanych danych, mo¿na w prosty sposób zarz¹dzaæ (dodawaæ lub odejmowaæ)
iloœci¹ wêz³ów w sieci, co wp³ywa na wydajnoœæ rozwi¹zania.

Podstawowe zaimplementowane funkcjonalnoœci w ramach projektu:
1. Do³¹czenie nowego wêz³a do sieci
2. Od³¹czenie wêz³a od sieci i zakoñczenie jego dzia³ania
3. Zmiana klucza i wartoœci w wêŸle, do którego pod³¹czony jest klient
4. Zmiana wartoœci dla podanego przez klienta klucza
5. Wyœwietlenie wartoœci dla podanego przez klienta klucza
6. Wyœwietlenie adresu ip i portu wêz³a, który przechowuje klucz podany przez u¿ytkownika
7. Znalezienie najwy¿szej wartoœci i wyœwietlenie jej razem z przypisanej do wartoœci kluczem
8. Znalezienie najmniejszej wartoœci i wyœwietlenie jej razem z przypisanej do wartoœci kluczem
9. Wy³¹czenie wszystkich wêz³ów

Dodatkowe zaimplementowane funkcjonalnoœci:
1.Regeneracja bazy danych

Komunikacja miêdzy wêz³ami oraz miêdzy wêz³em a klientem, odbywa siê za pomoc¹ protoko³u TCP.
W poni¿szej dokumentacji przedstawiono szczegó³owo:
1. Opis protoko³u
2. Kompilacja i instalacja
3. Funkcjonalnoœci

C.OPIS PROTOKO£U

Protokó³ wykorzystywany w komunikacji miêdzy klientem a wêz³em oraz miêdzy wêz³em a wêz³em, to protokó³ TCP/IP.
Definicja protoko³u TCP w sekcji Definicje powy¿ej. Komunikaty opisane s¹ w sekcji Dokumentacja Techniczna (poni¿ej).

D.INTERFEJSY SYSTEMU

Interfejsy u¿ytkownika:
1. Klienci rozproszonej bazy danych mog¹ wys³aæ zapytania i otrzymywaæ odpowiedzi poprzez interfejs TCP, który umo¿liwia
im po³¹czenie siê z dowolnym wêz³em w sieci i wys³anie zapytania za pomoc¹ odpowiednich komend.

Interfejsy systemowe:
1. Wêz³y komunikuj¹ siê ze sob¹ za pomoc¹ protoko³u TCP z innymi wêz³ami w sieci, wysy³aj¹c oraz
otrzymuj¹c odpowiednie komunikaty.
2. Wêz³y mog¹ odbieraæ zapytania od klientów i odpowiadaæ na nie poprzez interfejs TCP.
3. Administrator wêz³a ma mo¿liwoœæ œledzenia:
3.1. Dodania nowego s¹siada (Komunikat "ADDNEIGHBOUR")
3.2. Komunikatów od innych wêz³ów (Komunikat "Przyjêto komende od innego wêz³a")
3.3. Zapytañ od klienta (Komunikat "Przyjêto komende od klienta")

E. WYMAGANIA SYSTEMOWE I KONFIGURACJA SIECI

E.1. Wymagania systemowe
1. Java Development Kit (JDK) w wersji 1.8.

E.2. Konfiguracja systemu

E.2.1. Kompilacja kodu

W celu skompilowania kodu nale¿y w³¹czyæ:
-kompilacja.bat na systemie operacyjnym Windows
-kompilacja.sh na systemie operacyjnym Linux
Wskazane pliki znajduj¹ siê w katalogu kompilacja.

E.2.2. W celu utworzenia pierwszego wêz³a w sieci nale¿y uruchomiæ go podaj¹c odpowiednie argumenty w konsoli.

Przyk³ad uruchomienia pierwszego wêz³a w sieci:

java DatabaseNode -tcpport 9990 -record 5:5

java DatabaseNode -tcpport <numer portu TCP> -record <klucz>:<wartoœæ>

Gdzie:
-tcpport <numer portu TCP> okreœla numer portu TCP, na którym tworzymy nowy wêze³.
-record <klucz>:<wartoœæ> oznacza parê liczb ca³kowitych pocz¹tkowo przechowywanych w bazie na danym wêŸle,
gdzie pierwsza to klucz a druga to wartoœæ zwi¹zana z tym kluczem. Nie ma wymogu unikalnoœci zarówno klucza,
jak i wartoœci.

E.2.3. W celu utworzenia kolejnych wêz³ów w sieci nale¿y uruchomiæ go z dodatkowym parametrem:

Przyk³ad uruchomienia kolejnego wêz³a w sieci z dwoma wêz³ami s¹siaduj¹cymi (wêz³y te musz¹ byæ ju¿ uruchomione):

java DatabaseNode -tcpport 9990 -record 5:5 -connect localhost:9989 -connect localhost:9988

Gdzie:
-connect <adres ip>:<port> oznacza wêze³ ju¿ uruchomiony wêze³, z którym dany wêze³ ma s¹siadowaæ (Definicja s¹siadowania
wêz³ów w sekcji Definicje).

Przyk³adowy skrypt tworz¹cy sieæ:
Utworzenie przyk³adowej sieci.bat lub .sh
Wskazany skrypt tworzy sieæ, któr¹ zilustrowano w pliku Przyk³adowa sieæ.pdf.

E.3. W³¹czenie klienta

Klienci mog¹ nawi¹zaæ po³¹czenie z dowolnym wêz³em sieci za pomoc¹ polecenia w konsoli
Przyk³adowe wywo³anie klienta:

java DatabaseClient -gateway localhost:1 -operation get-min

java DatabaseClient -gateway <adres ip>:<port> -operation <operacja z parametrami>

Gdzie:
-gateway <adres IP>:<port> wskazuje na wêze³, z którym komunikuje siê klient.
-operation <operacja z parametrami> dostêpne operacje s¹ opisane w Dokumentacji Technicznej w podpunkcie F.3.

F.DOKUMENTACJA TECHNICZNA

F.1. Uruchomienie wêz³a
Opis dzia³ania:
F.1.1. Pobranie parametrów wywo³ania(DatabaseNode.java : main()):
-port TCP
-klucz i wartoœæ recordu w wêŸle
-adres IP i port wêz³a, do którego nowy wêze³ ma siê pod³¹czyæ
F.1.2. Stworzenie nowej instancji klasy DatabaseNode i przypisanie do niej wczeœniej zdobytych wartoœci(main())
F.1.3. W³¹czenie w¹tku wêz³a sieci, czyli(DatabaseNode.java : main(),run()):
-wys³anie do innych wêz³ów wiadomoœci "ADDNEIGHBOUR", która ma na celu poinformowanie o do³¹czeniu nowego
s¹siaduj¹cego wêz³a do sieci. Jeœli wêze³ nie ma podanych wêz³ów, do których ma siê pod³¹czyæ,
to rozpoczyna dzia³anie jako "bootstrap node", czyli wêze³, który inicjuje sieæ.
-oczekiwanie na po³¹czenie od klienta lub innego wêz³a

F.2. Algorytmy komunikacji (MessageHandler.java; DatabaseNode.java: run() i node service())

F.2.1. Algorytm komunikacji klient-wêze³

Opis dzia³ania:

F.2.1.1. Wêze³ otrzymuje komunikat o chêci po³¹czenia od klienta.
F.2.1.2. Tworzy now¹ instancjê klasy MessageHandler.java przypisuj¹c jej socket klienta i wêze³
(do którego jest pod³¹czony klient)
F.2.1.3. Uruchamiany jest nowy w¹tek (MessageHandler.java : run())

Opis dzia³ania MessageHandler.java znajduje siê w punkcie F.2.3.

F.2.2. Algorytm komunikacji wêze³-wêze³

Zgodnie z opisem komunikacji klient-wêze³. Punkt powy¿ej.

F.2.3. Odbiór i interpretacja zapytania (MessageHandler.java : run(),check())

F.2.3.1. Odebranie wiadomoœci od klienta / w¹tku
F.2.3.2. Sprawdzenie jaki typ zapytania wys³a³ nam klient / w¹tek
F.2.3.3. Je¿eli dane zapytanie jest obs³ugiwane
(obs³ugiwane zapytania wraz z funkcjami, które je obs³uguj¹ znajduj¹ siê w punkcie F.3.)
to w³¹cza odpowiadaj¹ca mu metode. (w przypadku komunikatu od innego wêz³a wiadomoœæ jest przetwarzana ponownie w
funkcji DatabaseNode.java : processMessage(). Dzia³anie tej funkcji jest opisane w punkcie F.4.2.)
F.2.3.4. W przeciwnym przypadku wysy³a odpowiedŸ "Nie ma takiej komendy"
F.2.3.5. Zamkniêcie socketu i wy³¹czenie w¹tku

F.3. Komunikacja klient-wêze³

Operacje, które mo¿e u¿yæ klient:

F.3.1. set-value <klucz>:<wartoœæ> (DatabaseNode.java : handleRequest())

Opis ogólny
Zapytanie wys³ane bezpoœrednio do wêz³a w celu zmiany wartoœci przypisanej do klucza dla ka¿dego wêz³a w sieci .

Opis dzia³ania
F.3.1.1. Wêze³ po otrzymaniu wskazanego zapytania sprawdza, czy klucz, który chce zmieniæ klient, to klucz przypisany do niego.
F.3.1.2. Je¿eli wskazany klucz jest przypisany do danego wêz³a, zmieniamy wartoœæ i wysy³amy odpowiedŸ "OK" i koñczy dzia³anie.
F.3.1.3. W przeciwnym przypadku wêze³ rozsy³a komunikat "NODE tcpport id SET klucz wartoœæ"
F.3.1.4. Wêze³ dodaje wskazany w punkcie powy¿ej komunikat do listy obs³u¿onych komunikatów.
F.3.1.5. Wêze³ przyjmuje odpowiedzi od s¹siaduj¹cych wêz³ów i weryfikuje je. (DatabaseNode.java : getCheckResponse())
F.3.1.6. Je¿eli co najmniej jeden wêze³ w sieci zmieni³ wartoœæ, to wys³ana jest do klienta odpowiedŸ "OK"
F.3.1.7. W przeciwnym przypadku wys³ana odpowiedŸ to "ERROR" oznacza, ¿e nie ma podanego klucza w sieci.

F.3.2. get-value <klucz> (DatabaseNode.java : handleRequest())

Opis ogólny
Zapytanie, które ma na celu zwrot komunikatu <klucz>:<wartoœæ>, klucz jest podawany przez klienta,
natomiast zwracana jest wartoœæ przypisana do klucza wraz z kluczem.

Opis dzia³ania
F.3.2.1. Wêze³ po otrzymaniu wskazanego zapytania sprawdza, czy klucz, który wskaza³ klient, to klucz przypisany do niego.
F.3.2.2. Je¿eli wskazany klucz jest przypisany do danego wêz³a, to wysy³amy klucz i wartoœæ przypisana do niego i koñczy dzia³anie.
F.3.2.3. W przeciwnym przypadku wêze³ rozsy³a komunikat "NODE tcpport id GET klucz"
F.3.2.4. Wêze³ dodaje wskazany w punkcie powy¿ej komunikat do listy obs³u¿onych komunikatów.
F.3.2.5. Wêze³ przyjmuje odpowiedzi od s¹siaduj¹cych wêz³ów i weryfikuje je. (DatabaseNode.java : getCheckResponse())
F.3.2.6. Je¿eli co najmniej jeden wêze³ w sieci wskaza³ wartoœæ dla danego klucza, to wys³ana jest do klienta odpowiedŸ
klucz i przypisana do niego wartoœæ
F.3.2.7. W przeciwnym przypadku wys³ana odpowiedŸ to "ERROR" oznacza, ¿e nie ma podanego klucza w sieci.

F.3.3. find-key <klucz> (DatabaseNode.java : handleRequest())

Opis ogólny
Zapytanie, które ma na celu zwrot adresu ip i portu wêz³a, w którym znajduje siê record o podanym
kluczu. OdpowiedŸ jest w postaci <adres ip>:<port>.

Opis dzia³ania
F.3.3.1. Wêze³ po otrzymaniu wskazanego zapytania sprawdza, czy klucz, który wskaza³ klient, to klucz przypisany do niego.
F.3.3.2. Je¿eli wskazany klucz jest przypisany do danego wêz³a, to wysy³amy adres ip i port wêz³a i koñczy dzia³anie.
F.3.3.3. W przeciwnym przypadku wêze³ rozsy³a komunikat "NODE tcpport id FIND klucz"
F.3.3.4. Wêze³ dodaje wskazany w punkcie powy¿ej komunikat do listy obs³u¿onych komunikatów.
F.3.3.5. Wêze³ przyjmuje odpowiedzi od s¹siaduj¹cych wêz³ów i weryfikuje je. (DatabaseNode.java : getCheckResponse())
F.3.3.6. Je¿eli co najmniej jeden wêze³ w sieci wskaza³ adres ip i port, to wys³ana jest do klienta odpowiedŸ
         adres ip i port wêz³a, do którego przypisany jest dany klucz.
F.3.3.7. W przeciwnym przypadku wys³ana odpowiedŸ to "ERROR" oznacza, ¿e nie ma podanego klucza w sieci.

F.3.4. get-max (DatabaseNode.java : getExtremum())

Opis ogólny
Zapytanie maj¹ce na celu zwrot najwiêkszej wartoœci w sieci, odpowiedŸ jest w postaci <klucz>:<wartoœæ>.

Opis dzia³ania
F.3.4.1. Wêze³ po otrzymaniu wskazanego zapytania przyjmuje, ¿e maksymalna wartoœæ jest to jego lokalna wartoœæ, czyli
przyjmuje jako maksymalna wartoœæ swoj¹ wartoœæ, a jako klucz swój klucz
F.3.4.2. Wêze³ rozsy³a komunikat "NODE tcpport id MAX"
F.3.4.3. Wêze³ dodaje wskazany w punkcie powy¿ej komunikat do listy obs³u¿onych komunikatów
F.3.4.4. Ka¿d¹ odpowiedŸ porównuje z przypisanym maxem
F.3.4.5. Je¿eli dana odpowiedŸ jest wiêksza od wskazanego powy¿ej maxa, to przypisuje j¹, jako wartoœæ maksymaln¹, oraz
wskazany klucz jest kluczem maksymalnej wartoœci
F.3.4.6. W przeciwnym przypadku przechodzi do nastêpnej odpowiedzi
F.3.4.7. Po sprawdzeniu wszystkich odpowiedzi wysy³a do klienta maksymaln¹ wartoœæ i klucz maksymalnej wartoœci

F.3.5. get-min (DatabaseNode.java : getExtremum())

Opis ogólny
Zapytanie, które ma na celu zwrot najmniejszej wartoœci w sieci. OdpowiedŸ ma postaæ <klucz>:<wartoœæ>.

Opis dzia³ania
F.3.5.1. Wêze³ po otrzymaniu wskazanego zapytania przyjmuje, ¿e minimalna wartoœæ jest to jego lokalna wartoœæ, czyli
przyjmuje jako minimalna wartoœæ swoj¹ wartoœæ, a jako klucz swój klucz
F.3.5.2. Wêze³ rozsy³a komunikat "NODE tcpport id MIN"
F.3.5.3. Wêze³ dodaje wskazany w punkcie powy¿ej komunikat do listy obs³u¿onych komunikatów
F.3.5.4. Ka¿d¹ odpowiedŸ porównuje z przypisanym minem
F.3.5.5. Je¿eli dana odpowiedŸ jest wiêksza od wskazanego powy¿ej mina, to przypisuje j¹, jako wartoœæ minimalna, oraz
wskazany klucz jest kluczem minimalnej wartoœci
F.3.5.6. W przeciwnym przypadku przechodzi do nastêpnej odpowiedzi
F.3.5.7. Po sprawdzeniu wszystkich odpowiedzi wysy³a do klienta minimalna wartoœæ i klucz minimalnej wartoœci

F.3.6. new-record <klucz>:<wartoœæ> (DatabaseNode.java : changeRecord())

Opis ogólny
Zapytanie maj¹ce na celu zmianê klucza, jak i wartoœci dla wêz³a, do którego pod³¹czony jest klient.

Opis dzia³ania
Wêze³ otrzymuj¹c zapytanie, od klienta zmienia klucz i wartoœæ w swoim rekordzie, po czym zwraca odpowiedŸ "OK",
po zakoñczonej operacji, co oznacza, ¿e zmiana wartoœci i klucza przebieg³a pomyœlnie.

F.3.7. terminate (DatabaseNode.java : terminate())

Opis ogólny
Zapytanie, które ma na celu zakoñczenie dzia³ania wêz³a, do którego jest pod³¹czony klient i od³¹czenie go
od sieci.

Opis dzia³ania
F.3.7.1. Wêze³ otrzymuj¹c tak¹ wiadomoœæ wysy³a, do wszystkich s¹siaduj¹cych wêz³ów "NODE tcpport id TERMINATE".
F.3.7.2. Koñczy swoje dzia³anie i zwalnia socket.
F.3.7.3. Klientowi jest wys³ana odpowiedŸ "OK", co oznacza, ¿e ca³oœæ przebieg³a pomyœlnie.

F.3.8. terminate-all (DatabaseNode.java : terminateAll())

Opis ogólny
Zapytanie, które ma na celu zakoñczenie dzia³ania wszystkich wêz³ów nale¿¹cych do jednej sieci.

Opis dzia³ania
F.3.7.1. Wêze³ otrzymuj¹c tak¹ wiadomoœæ wysy³a, do wszystkich s¹siaduj¹cych wêz³ów "NODE tcpport id TERMINATEALL".
F.3.7.2. Koñczy swoje dzia³anie i zwalnia socket.
F.3.7.3. Klientowi jest wys³ana odpowiedŸ "OK", co oznacza, ¿e ca³oœæ przebieg³a pomyœlnie.

F.4.Komunikacja wêze³-wêze³:

F.4.1. Konstrukcja komunikatów wêz³ów wygl¹da nastêpuj¹co:

NODE <tcpport> <id> <operacja z parametrami>

Gdzie:
-tcpport to port tcp wêz³a, z którego rozsy³any jest komunikat do innych wêz³ów.
-id to indywidualne dla ka¿dego wêz³a id pytania, które kaza³ rozes³aæ po sieci.
-operacja to operacja, której wymaga dany wêze³. Ta czêœæ mo¿e, ale nie musi zawieraæ parametry w zale¿noœci,
jak¹ operacje rozsy³a wêze³.

F.4.2. Metoda przetwarzaj¹ca komunikaty od wêz³a (DatabaseNode.java : processMessage())

F.4.2.1. Sprawdzenie jaki typ komunikatu wys³a³ nam w¹tek
F.4.2.2. Uruchamiana jest przypisana do typu komunikatu metoda
F.4.2.3. Zwrócenie odpowiedzi do (MessageHandler.java : run())
(obs³ugiwane zapytania wraz z funkcjami, które je obs³uguj¹ znajduj¹ siê w punkcie F.4.3)

F.4.3. Operacje, których mo¿e u¿yæ wêze³:

F.4.3.1. GET klucz (DatabaseNode.java : processMessage(), getResponse(), handleRequest())

Opis ogólny
Komunikat, który ma na celu zwrot <klucz>:<wartoœæ>, klucz jest podawany przez inny wêze³,
natomiast zwracana jest wartoœæ przypisana do klucza wraz z kluczem.

Opis dzia³ania
F.4.3.1.1. Wêze³ weryfikuje czy pobrany komunikat by³ ju¿ obs³u¿ony
F.4.3.1.2. Je¿eli tak, to wysy³a odpowiedŸ "ERROR"
F.4.3.1.3. W przeciwnym przypadku wêze³ sprawdza, czy klucz, który wskaza³ klient, to klucz przypisany do niego.
F.4.3.1.4. Je¿eli wskazany klucz jest przypisany do danego wêz³a, to wysy³amy klucz i wartoœæ przypisana do niego i koñczy dzia³anie.
F.4.3.1.5. W przeciwnym przypadku wêze³ rozsy³a pobrany komunikat do s¹siaduj¹cych wêz³ów.
F.4.3.1.6. Wêze³ dodaje wskazany w punkcie powy¿ej komunikat do listy obs³u¿onych komunikatów.
F.4.3.1.7. Wêze³ przyjmuje odpowiedzi od s¹siaduj¹cych wêz³ów i weryfikuje je. (DatabaseNode.java : getCheckResponse())
F.4.3.1.8. Je¿eli co najmniej jeden wêze³ w sieci wskaza³ wartoœæ dla danego klucza, to wys³ana jest do wêz³a,
od którego przyjêto komunikat odpowiedŸ klucz i przypisana do niego wartoœæ
F.4.3.1.9. W przeciwnym przypadku wys³ana odpowiedŸ to "ERROR".

F.4.3.2. SET klucz wartoœæ (DatabaseNode.java : processMessage(), setResponse(), handleRequest())

Opis ogólny
Komunikat wys³any do wêz³a w celu zmiany wartoœci przypisanej do klucza.

Opis dzia³ania
F.4.3.2.1. Wêze³ weryfikuje czy pobrany komunikat by³ ju¿ obs³u¿ony
F.4.3.2.2. Je¿eli tak, to wysy³a odpowiedŸ "ERROR"
F.4.3.2.3. W przeciwnym przypadku wêze³ sprawdza, czy klucz, który chce zmieniæ klient, to klucz przypisany do niego.
F.4.3.2.4. Je¿eli wskazany klucz jest przypisany do danego wêz³a, zmieniamy wartoœæ i wysy³amy odpowiedŸ "OK" i koñczy dzia³anie.
F.4.3.2.5. W przeciwnym przypadku wêze³ rozsy³a pobrany komunikat do s¹siaduj¹cych wêz³ów.
F.4.3.2.6. Wêze³ dodaje wskazany w punkcie powy¿ej komunikat do listy obs³u¿onych komunikatów.
F.4.3.2.7. Wêze³ przyjmuje odpowiedzi od s¹siaduj¹cych wêz³ów i weryfikuje je. (DatabaseNode.java : getCheckResponse())
F.4.3.2.8. Je¿eli co najmniej jeden wêze³ w sieci zmieni³ wartoœæ, to wys³ana jest do wêz³a, od którego przyjêto
komunikat, odpowiedŸ "OK"
F.4.3.2.9. W przeciwnym przypadku wys³ana odpowiedŸ to "ERROR" oznacza, ¿e nie ma podanego klucza w sieci.

F.4.3.3. FIND klucz (DatabaseNode.java : processMessage(), findResponse(), handleRequest())

Opis ogólny
Komunikat, który ma na celu zwrot adresu ip i portu wêz³a, w którym znajduje siê record o podanym
kluczu. OdpowiedŸ jest w postaci <adres ip>:<port>.

Opis dzia³ania
F.4.3.3.1. Wêze³ weryfikuje czy pobrany komunikat by³ ju¿ obs³u¿ony
F.4.3.3.2. Je¿eli tak, to wysy³a odpowiedŸ "ERROR"
F.4.3.3.3. W przeciwnym przypadku sprawdza, czy klucz, który wskaza³ klient, to klucz przypisany do niego.
F.4.3.3.4. Je¿eli wskazany klucz jest przypisany do danego wêz³a, to wysy³amy adres ip i port wêz³a i koñczy dzia³anie.
F.4.3.3.5. W przeciwnym przypadku wêze³ rozsy³a pobrany komunikat do s¹siaduj¹cych wêz³ów.
F.4.3.3.6. Wêze³ dodaje wskazany w punkcie powy¿ej komunikat do listy obs³u¿onych komunikatów.
F.4.3.3.7. Wêze³ przyjmuje odpowiedzi od s¹siaduj¹cych wêz³ów i weryfikuje je. (DatabaseNode.java : getCheckResponse())
F.4.3.3.8. Je¿eli co najmniej jeden wêze³ w sieci wskaza³ adres ip i port, to wys³ana jest do wêz³a, od którego
przyjêto komunikat, odpowiedŸ adres ip i port wêz³a, do którego przypisany jest dany klucz.
F.4.3.3.9. W przeciwnym przypadku wys³ana odpowiedŸ to "ERROR" oznacza, ¿e nie ma podanego klucza w sieci.

F.4.3.4. MAX (DatabaseNode.java : processMessage(), maxResponse(), getExtremum())

Opis ogólny
Komunikat maj¹cy na celu zwrot najwiêkszej wartoœci w sieci, odpowiedŸ jest w postaci <klucz>:<wartoœæ>.

Opis dzia³ania
F.4.3.4.1. Wêze³ weryfikuje czy pobrany komunikat by³ ju¿ obs³u¿ony
F.4.3.4.2. Je¿eli tak, to wysy³a odpowiedŸ w postaci swojego klucza i przypisanej do niej wartoœci
F.4.3.4.3. W przeciwnym przypadku wêze³ przyjmuje, ¿e maksymalna wartoœæ jest to jego lokalna wartoœæ, czyli
przyjmuje jako maksymalna wartoœæ swoj¹ wartoœæ, a jako klucz swój klucz
F.4.3.4.4. Wêze³ rozsy³a pobrany komunikat do s¹siaduj¹cych wêz³ów.
F.4.3.4.5. Wêze³ dodaje wskazany w punkcie powy¿ej komunikat do listy obs³u¿onych komunikatów
F.4.3.4.6. Ka¿d¹ odpowiedŸ porównuje z przypisanym maxem
F.4.3.4.7. Je¿eli dana odpowiedŸ jest wiêksza od wskazanego powy¿ej maxa, to przypisuje j¹, jako wartoœæ maksymaln¹, oraz
wskazany klucz jest kluczem maksymalnej wartoœci
F.4.3.4.8. W przeciwnym przypadku przechodzi do nastêpnej odpowiedzi
F.4.3.4.9. Po sprawdzeniu wszystkich odpowiedzi wysy³a do wêz³a, od którego otrzyma³ komunikat, odpowiedŸ w postaci
maksymalnej wartoœci i klucza maksymalnej wartoœci

F.4.3.5. MIN (DatabaseNode.java : processMessage(), minResponse(), getExtremum())

Opis ogólny
Komunikat, który ma na celu zwrot najmniejszej wartoœci w sieci. OdpowiedŸ ma postaæ <klucz>:<wartoœæ>.

Opis dzia³ania
F.4.3.5.1. Wêze³ weryfikuje czy pobrany komunikat by³ ju¿ obs³u¿ony
F.4.3.5.2. Je¿eli tak, to wysy³a odpowiedŸ w postaci swojego klucza i przypisanej do niej wartoœci
F.4.3.5.3. W przeciwnym przypadku wêze³ przyjmuje, ¿e minimalna wartoœæ jest to jego lokalna wartoœæ, czyli
przyjmuje jako minimalna wartoœæ swoj¹ wartoœæ, a jako klucz swój klucz
F.4.3.5.4. Wêze³ rozsy³a pobrany komunikat do s¹siaduj¹cych wêz³ów.
F.4.3.5.5. Wêze³ dodaje wskazany w punkcie powy¿ej komunikat do listy obs³u¿onych komunikatów
F.4.3.5.6. Ka¿d¹ odpowiedŸ porównuje z przypisanym minem
F.4.3.5.7. Je¿eli dana odpowiedŸ jest mniejsza od wskazanego powy¿ej mina, to przypisuje j¹, jako wartoœæ minimaln¹, oraz
wskazany klucz jest kluczem minimalnej wartoœci
F.4.3.5.8. W przeciwnym przypadku przechodzi do nastêpnej odpowiedzi
F.4.3.5.9. Po sprawdzeniu wszystkich odpowiedzi wysy³a do wêz³a, od którego otrzyma³ komunikat, odpowiedŸ w postaci
minimalnej wartoœci i klucza minimalnej wartoœci

F.4.3.6. TERMINATE (DatabaseNode.java : processMessage(), terminateResponse())

Opis ogólny
Komunikat ma na celu poinformowanie s¹siaduj¹cych wêz³ów o tym, ¿e s¹siaduj¹cy do nich wêze³ od³¹cza siê od sieci
i koñczy dzia³anie

Opis dzia³ania
F.4.3.6.1. Wêze³ usuwa z listy s¹siaduj¹cych wêz³ów wêze³, który wys³a³ komunikat.
F.4.3.6.2. Wêze³ usuwa wszystkie obs³u¿one pytania od tego wêz³a z listy pytañ.
F.4.3.6.3. Wêze³ nie wysy³a ¿adnej odpowiedzi

F.4.3.7. TERMINATEALL (DatabaseNode.java : processMessage(), terminateAll())

Opis ogólny
Komunikat ma na celu wy³¹czenie dzia³ania wêz³a i wys³anie do s¹siaduj¹cych wêz³ów tego samego komunikatu, aby wszystkie
wêz³y w sieci zosta³y wy³¹czone.

Opis dzia³ania
F.4.3.7.1. Wêze³ usuwa z listy s¹siaduj¹cych wêz³ów wêze³, który wys³a³ komunikat.
F.4.3.7.2. Wêze³ wysy³a, do wszystkich s¹siaduj¹cych wêz³ów "NODE tcpport id TERMINATEALL".
F.4.3.7.3. Koñczy swoje dzia³anie i zwalnia socket.
F.4.3.7.4. Wêze³ nie wysy³a ¿adnej odpowiedzi

F.4.3.8. ADDNEIGHBOUR (DatabaseNode.java : processMessage(), addNeighbour())

Opis ogólny
Komunikat ma na celu poinformowanie o do³¹czeniu do sieci nowego wêz³a i dodanie go do listy s¹siadów przez te wêz³y,
z którymi s¹siaduje

Opis dzia³ania
Jedyn¹ czynnoœci¹, która wykonuje wêze³ po otrzymaniu komunikatu, to dodanie nowego wêz³a do listy s¹siadów.

F.5. Testy

Przygotowane i wykonane testy jednostkowe znajduj¹ siê w podfolderze Rozproszona Baza Danych/tests.

Testy ogólne (znajduj¹ siê w Rozproszona Baza Danych/src/testy):
-utworzenie sieci sk³adaj¹cej siê z 25 wêz³ów (Rysunek sieci przedstawiony w pliku Przyk³adowa sieæ.pdf)
-uruchomienie kilku klientów
-wys³anie zapytañ do sieci od klientów
-wy³¹czenie wszystkich wêz³ów (wy³¹czenie sieci)

W tym samym folderze znajduje siê testy z grupy wyk³adowej.

F.6. Regeneracja baz danych
Regeneracja zosta³a zaimportowana za pomoc¹ kilku nowych komunikatów:
F.6.1. YOUFIRST (DatabaseNode.java: processMessage (), youFirst())
Komunikat ten zostaje wys³any w momencie zamkniêcia bootstrap node, do kolejnego utworzonego wêz³a, przez to on teraz staje siê bootstrap nodem.
F.6.2. RUFIRST (DatabaseNode.java: terminateResponse(), checkConnection(), checkFirst())
Komunikat ma na celu sprawdzenie, czy wêze³ po od³¹czeniu wêz³a dalej ma po³¹czenie z bootstrap nodem, je¿eli nie to wysy³a do wszystkich s¹siadów wy³¹czonego wêz³a, aby doda³ go s¹siadów, aby nie utraciæ po³¹czenia z bootstrap nodem.

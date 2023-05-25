A.DEFINICJA

Rozproszona baza danych
Rozproszona baza danych (ang. distributed database) to baza danych, kt�ra jest roz�o�ona na kilku hostach lub
maszynach i umo�liwia dost�p do danych z dowolnego miejsca w sieci. Rozproszona baza danych ma na celu
zwi�kszenie niezawodno�ci i wydajno�ci oraz umo�liwienie dost�pu do danych z r�nych miejsc w sieci.
W przeciwie�stwie do centralnej bazy danych, w kt�rej dane s� przechowywane i przetwarzane na jednym serwerze,
rozproszona baza danych rozprowadza dane i obci��enie procesora pomi�dzy wieloma serwerami, co pozwala na lepsze
wykorzystanie zasob�w i lepsz� skalowalno��.

TCP
Protok� TCP (Transmission Control Protocol) jest to protok� warstwy transportowej sieci komputerowej, kt�ry umo�liwia
niezawodne przesy�anie danych pomi�dzy komputerami w sieci. TCP dzia�a poprzez nadawanie danych w pakietach
(ang. packets) i kontrolowanie przep�ywu danych pomi�dzy urz�dzeniami w sieci, aby upewni� si�, �e dane s� poprawnie
przes�ane i odebrane. Protok� TCP zapewnia niezawodno�� poprzez wysy�anie potwierdze� odbioru dla ka�dego pakietu
danych oraz mo�liwo�� ponownego wys�ania pakiet�w, je�li s� one utracone lub uszkodzone w trakcie transmisji.
Protok� TCP jest cz�sto u�ywany do przesy�ania danych przez Internet i jest jednym z najwa�niejszych protoko��w sieciowych.

(�r�d�o dla definicji TCP I Rozproszona baza danych https://chat.openai.com/chat)

S�siadowanie w�z��w
W�z�y uznajemy za w�z�y s�siaduj�ce, je�eli w�z�y s� po��czone i mo�liwa jest komunikacja mi�dzy nimi w celu wykonania
operacji.

B.WST�P

G��wnym celem projektu jest stworzenie rozproszonej bazy danych, kt�r� b�dzie mo�na skalowa�. Oznacza to,
�e wraz ze wzrostem ilo�ci u�ytkownik�w lub ilo�ci przechowywanych danych, mo�na w prosty spos�b zarz�dza� (dodawa� lub odejmowa�)
ilo�ci� w�z��w w sieci, co wp�ywa na wydajno�� rozwi�zania.

Podstawowe zaimplementowane funkcjonalno�ci w ramach projektu:
1. Do��czenie nowego w�z�a do sieci
2. Od��czenie w�z�a od sieci i zako�czenie jego dzia�ania
3. Zmiana klucza i warto�ci w w�le, do kt�rego pod��czony jest klient
4. Zmiana warto�ci dla podanego przez klienta klucza
5. Wy�wietlenie warto�ci dla podanego przez klienta klucza
6. Wy�wietlenie adresu ip i portu w�z�a, kt�ry przechowuje klucz podany przez u�ytkownika
7. Znalezienie najwy�szej warto�ci i wy�wietlenie jej razem z przypisanej do warto�ci kluczem
8. Znalezienie najmniejszej warto�ci i wy�wietlenie jej razem z przypisanej do warto�ci kluczem
9. Wy��czenie wszystkich w�z��w

Dodatkowe zaimplementowane funkcjonalno�ci:
1.Regeneracja bazy danych

Komunikacja mi�dzy w�z�ami oraz mi�dzy w�z�em a klientem, odbywa si� za pomoc� protoko�u TCP.
W poni�szej dokumentacji przedstawiono szczeg�owo:
1. Opis protoko�u
2. Kompilacja i instalacja
3. Funkcjonalno�ci

C.OPIS PROTOKO�U

Protok� wykorzystywany w komunikacji mi�dzy klientem a w�z�em oraz mi�dzy w�z�em a w�z�em, to protok� TCP/IP.
Definicja protoko�u TCP w sekcji Definicje powy�ej. Komunikaty opisane s� w sekcji Dokumentacja Techniczna (poni�ej).

D.INTERFEJSY SYSTEMU

Interfejsy u�ytkownika:
1. Klienci rozproszonej bazy danych mog� wys�a� zapytania i otrzymywa� odpowiedzi poprzez interfejs TCP, kt�ry umo�liwia
im po��czenie si� z dowolnym w�z�em w sieci i wys�anie zapytania za pomoc� odpowiednich komend.

Interfejsy systemowe:
1. W�z�y komunikuj� si� ze sob� za pomoc� protoko�u TCP z innymi w�z�ami w sieci, wysy�aj�c oraz
otrzymuj�c odpowiednie komunikaty.
2. W�z�y mog� odbiera� zapytania od klient�w i odpowiada� na nie poprzez interfejs TCP.
3. Administrator w�z�a ma mo�liwo�� �ledzenia:
3.1. Dodania nowego s�siada (Komunikat "ADDNEIGHBOUR")
3.2. Komunikat�w od innych w�z��w (Komunikat "Przyj�to komende od innego w�z�a")
3.3. Zapyta� od klienta (Komunikat "Przyj�to komende od klienta")

E. WYMAGANIA SYSTEMOWE I KONFIGURACJA SIECI

E.1. Wymagania systemowe
1. Java Development Kit (JDK) w wersji 1.8.

E.2. Konfiguracja systemu

E.2.1. Kompilacja kodu

W celu skompilowania kodu nale�y w��czy�:
-kompilacja.bat na systemie operacyjnym Windows
-kompilacja.sh na systemie operacyjnym Linux
Wskazane pliki znajduj� si� w katalogu kompilacja.

E.2.2. W celu utworzenia pierwszego w�z�a w sieci nale�y uruchomi� go podaj�c odpowiednie argumenty w konsoli.

Przyk�ad uruchomienia pierwszego w�z�a w sieci:

java DatabaseNode -tcpport 9990 -record 5:5

java DatabaseNode -tcpport <numer portu TCP> -record <klucz>:<warto��>

Gdzie:
-tcpport <numer portu TCP> okre�la numer portu TCP, na kt�rym tworzymy nowy w�ze�.
-record <klucz>:<warto��> oznacza par� liczb ca�kowitych pocz�tkowo przechowywanych w bazie na danym w�le,
gdzie pierwsza to klucz a druga to warto�� zwi�zana z tym kluczem. Nie ma wymogu unikalno�ci zar�wno klucza,
jak i warto�ci.

E.2.3. W celu utworzenia kolejnych w�z��w w sieci nale�y uruchomi� go z dodatkowym parametrem:

Przyk�ad uruchomienia kolejnego w�z�a w sieci z dwoma w�z�ami s�siaduj�cymi (w�z�y te musz� by� ju� uruchomione):

java DatabaseNode -tcpport 9990 -record 5:5 -connect localhost:9989 -connect localhost:9988

Gdzie:
-connect <adres ip>:<port> oznacza w�ze� ju� uruchomiony w�ze�, z kt�rym dany w�ze� ma s�siadowa� (Definicja s�siadowania
w�z��w w sekcji Definicje).

Przyk�adowy skrypt tworz�cy sie�:
Utworzenie przyk�adowej sieci.bat lub .sh
Wskazany skrypt tworzy sie�, kt�r� zilustrowano w pliku Przyk�adowa sie�.pdf.

E.3. W��czenie klienta

Klienci mog� nawi�za� po��czenie z dowolnym w�z�em sieci za pomoc� polecenia w konsoli
Przyk�adowe wywo�anie klienta:

java DatabaseClient -gateway localhost:1 -operation get-min

java DatabaseClient -gateway <adres ip>:<port> -operation <operacja z parametrami>

Gdzie:
-gateway <adres IP>:<port> wskazuje na w�ze�, z kt�rym komunikuje si� klient.
-operation <operacja z parametrami> dost�pne operacje s� opisane w Dokumentacji Technicznej w podpunkcie F.3.

F.DOKUMENTACJA TECHNICZNA

F.1. Uruchomienie w�z�a
Opis dzia�ania:
F.1.1. Pobranie parametr�w wywo�ania(DatabaseNode.java : main()):
-port TCP
-klucz i warto�� recordu w w�le
-adres IP i port w�z�a, do kt�rego nowy w�ze� ma si� pod��czy�
F.1.2. Stworzenie nowej instancji klasy DatabaseNode i przypisanie do niej wcze�niej zdobytych warto�ci(main())
F.1.3. W��czenie w�tku w�z�a sieci, czyli(DatabaseNode.java : main(),run()):
-wys�anie do innych w�z��w wiadomo�ci "ADDNEIGHBOUR", kt�ra ma na celu poinformowanie o do��czeniu nowego
s�siaduj�cego w�z�a do sieci. Je�li w�ze� nie ma podanych w�z��w, do kt�rych ma si� pod��czy�,
to rozpoczyna dzia�anie jako "bootstrap node", czyli w�ze�, kt�ry inicjuje sie�.
-oczekiwanie na po��czenie od klienta lub innego w�z�a

F.2. Algorytmy komunikacji (MessageHandler.java; DatabaseNode.java: run() i node service())

F.2.1. Algorytm komunikacji klient-w�ze�

Opis dzia�ania:

F.2.1.1. W�ze� otrzymuje komunikat o ch�ci po��czenia od klienta.
F.2.1.2. Tworzy now� instancj� klasy MessageHandler.java przypisuj�c jej socket klienta i w�ze�
(do kt�rego jest pod��czony klient)
F.2.1.3. Uruchamiany jest nowy w�tek (MessageHandler.java : run())

Opis dzia�ania MessageHandler.java znajduje si� w punkcie F.2.3.

F.2.2. Algorytm komunikacji w�ze�-w�ze�

Zgodnie z opisem komunikacji klient-w�ze�. Punkt powy�ej.

F.2.3. Odbi�r i interpretacja zapytania (MessageHandler.java : run(),check())

F.2.3.1. Odebranie wiadomo�ci od klienta / w�tku
F.2.3.2. Sprawdzenie jaki typ zapytania wys�a� nam klient / w�tek
F.2.3.3. Je�eli dane zapytanie jest obs�ugiwane
(obs�ugiwane zapytania wraz z funkcjami, kt�re je obs�uguj� znajduj� si� w punkcie F.3.)
to w��cza odpowiadaj�ca mu metode. (w przypadku komunikatu od innego w�z�a wiadomo�� jest przetwarzana ponownie w
funkcji DatabaseNode.java : processMessage(). Dzia�anie tej funkcji jest opisane w punkcie F.4.2.)
F.2.3.4. W przeciwnym przypadku wysy�a odpowied� "Nie ma takiej komendy"
F.2.3.5. Zamkni�cie socketu i wy��czenie w�tku

F.3. Komunikacja klient-w�ze�

Operacje, kt�re mo�e u�y� klient:

F.3.1. set-value <klucz>:<warto��> (DatabaseNode.java : handleRequest())

Opis og�lny
Zapytanie wys�ane bezpo�rednio do w�z�a w celu zmiany warto�ci przypisanej do klucza dla ka�dego w�z�a w sieci .

Opis dzia�ania
F.3.1.1. W�ze� po otrzymaniu wskazanego zapytania sprawdza, czy klucz, kt�ry chce zmieni� klient, to klucz przypisany do niego.
F.3.1.2. Je�eli wskazany klucz jest przypisany do danego w�z�a, zmieniamy warto�� i wysy�amy odpowied� "OK" i ko�czy dzia�anie.
F.3.1.3. W przeciwnym przypadku w�ze� rozsy�a komunikat "NODE tcpport id SET klucz warto��"
F.3.1.4. W�ze� dodaje wskazany w punkcie powy�ej komunikat do listy obs�u�onych komunikat�w.
F.3.1.5. W�ze� przyjmuje odpowiedzi od s�siaduj�cych w�z��w i weryfikuje je. (DatabaseNode.java : getCheckResponse())
F.3.1.6. Je�eli co najmniej jeden w�ze� w sieci zmieni� warto��, to wys�ana jest do klienta odpowied� "OK"
F.3.1.7. W przeciwnym przypadku wys�ana odpowied� to "ERROR" oznacza, �e nie ma podanego klucza w sieci.

F.3.2. get-value <klucz> (DatabaseNode.java : handleRequest())

Opis og�lny
Zapytanie, kt�re ma na celu zwrot komunikatu <klucz>:<warto��>, klucz jest podawany przez klienta,
natomiast zwracana jest warto�� przypisana do klucza wraz z kluczem.

Opis dzia�ania
F.3.2.1. W�ze� po otrzymaniu wskazanego zapytania sprawdza, czy klucz, kt�ry wskaza� klient, to klucz przypisany do niego.
F.3.2.2. Je�eli wskazany klucz jest przypisany do danego w�z�a, to wysy�amy klucz i warto�� przypisana do niego i ko�czy dzia�anie.
F.3.2.3. W przeciwnym przypadku w�ze� rozsy�a komunikat "NODE tcpport id GET klucz"
F.3.2.4. W�ze� dodaje wskazany w punkcie powy�ej komunikat do listy obs�u�onych komunikat�w.
F.3.2.5. W�ze� przyjmuje odpowiedzi od s�siaduj�cych w�z��w i weryfikuje je. (DatabaseNode.java : getCheckResponse())
F.3.2.6. Je�eli co najmniej jeden w�ze� w sieci wskaza� warto�� dla danego klucza, to wys�ana jest do klienta odpowied�
klucz i przypisana do niego warto��
F.3.2.7. W przeciwnym przypadku wys�ana odpowied� to "ERROR" oznacza, �e nie ma podanego klucza w sieci.

F.3.3. find-key <klucz> (DatabaseNode.java : handleRequest())

Opis og�lny
Zapytanie, kt�re ma na celu zwrot adresu ip i portu w�z�a, w kt�rym znajduje si� record o podanym
kluczu. Odpowied� jest w postaci <adres ip>:<port>.

Opis dzia�ania
F.3.3.1. W�ze� po otrzymaniu wskazanego zapytania sprawdza, czy klucz, kt�ry wskaza� klient, to klucz przypisany do niego.
F.3.3.2. Je�eli wskazany klucz jest przypisany do danego w�z�a, to wysy�amy adres ip i port w�z�a i ko�czy dzia�anie.
F.3.3.3. W przeciwnym przypadku w�ze� rozsy�a komunikat "NODE tcpport id FIND klucz"
F.3.3.4. W�ze� dodaje wskazany w punkcie powy�ej komunikat do listy obs�u�onych komunikat�w.
F.3.3.5. W�ze� przyjmuje odpowiedzi od s�siaduj�cych w�z��w i weryfikuje je. (DatabaseNode.java : getCheckResponse())
F.3.3.6. Je�eli co najmniej jeden w�ze� w sieci wskaza� adres ip i port, to wys�ana jest do klienta odpowied�
         adres ip i port w�z�a, do kt�rego przypisany jest dany klucz.
F.3.3.7. W przeciwnym przypadku wys�ana odpowied� to "ERROR" oznacza, �e nie ma podanego klucza w sieci.

F.3.4. get-max (DatabaseNode.java : getExtremum())

Opis og�lny
Zapytanie maj�ce na celu zwrot najwi�kszej warto�ci w sieci, odpowied� jest w postaci <klucz>:<warto��>.

Opis dzia�ania
F.3.4.1. W�ze� po otrzymaniu wskazanego zapytania przyjmuje, �e maksymalna warto�� jest to jego lokalna warto��, czyli
przyjmuje jako maksymalna warto�� swoj� warto��, a jako klucz sw�j klucz
F.3.4.2. W�ze� rozsy�a komunikat "NODE tcpport id MAX"
F.3.4.3. W�ze� dodaje wskazany w punkcie powy�ej komunikat do listy obs�u�onych komunikat�w
F.3.4.4. Ka�d� odpowied� por�wnuje z przypisanym maxem
F.3.4.5. Je�eli dana odpowied� jest wi�ksza od wskazanego powy�ej maxa, to przypisuje j�, jako warto�� maksymaln�, oraz
wskazany klucz jest kluczem maksymalnej warto�ci
F.3.4.6. W przeciwnym przypadku przechodzi do nast�pnej odpowiedzi
F.3.4.7. Po sprawdzeniu wszystkich odpowiedzi wysy�a do klienta maksymaln� warto�� i klucz maksymalnej warto�ci

F.3.5. get-min (DatabaseNode.java : getExtremum())

Opis og�lny
Zapytanie, kt�re ma na celu zwrot najmniejszej warto�ci w sieci. Odpowied� ma posta� <klucz>:<warto��>.

Opis dzia�ania
F.3.5.1. W�ze� po otrzymaniu wskazanego zapytania przyjmuje, �e minimalna warto�� jest to jego lokalna warto��, czyli
przyjmuje jako minimalna warto�� swoj� warto��, a jako klucz sw�j klucz
F.3.5.2. W�ze� rozsy�a komunikat "NODE tcpport id MIN"
F.3.5.3. W�ze� dodaje wskazany w punkcie powy�ej komunikat do listy obs�u�onych komunikat�w
F.3.5.4. Ka�d� odpowied� por�wnuje z przypisanym minem
F.3.5.5. Je�eli dana odpowied� jest wi�ksza od wskazanego powy�ej mina, to przypisuje j�, jako warto�� minimalna, oraz
wskazany klucz jest kluczem minimalnej warto�ci
F.3.5.6. W przeciwnym przypadku przechodzi do nast�pnej odpowiedzi
F.3.5.7. Po sprawdzeniu wszystkich odpowiedzi wysy�a do klienta minimalna warto�� i klucz minimalnej warto�ci

F.3.6. new-record <klucz>:<warto��> (DatabaseNode.java : changeRecord())

Opis og�lny
Zapytanie maj�ce na celu zmian� klucza, jak i warto�ci dla w�z�a, do kt�rego pod��czony jest klient.

Opis dzia�ania
W�ze� otrzymuj�c zapytanie, od klienta zmienia klucz i warto�� w swoim rekordzie, po czym zwraca odpowied� "OK",
po zako�czonej operacji, co oznacza, �e zmiana warto�ci i klucza przebieg�a pomy�lnie.

F.3.7. terminate (DatabaseNode.java : terminate())

Opis og�lny
Zapytanie, kt�re ma na celu zako�czenie dzia�ania w�z�a, do kt�rego jest pod��czony klient i od��czenie go
od sieci.

Opis dzia�ania
F.3.7.1. W�ze� otrzymuj�c tak� wiadomo�� wysy�a, do wszystkich s�siaduj�cych w�z��w "NODE tcpport id TERMINATE".
F.3.7.2. Ko�czy swoje dzia�anie i zwalnia socket.
F.3.7.3. Klientowi jest wys�ana odpowied� "OK", co oznacza, �e ca�o�� przebieg�a pomy�lnie.

F.3.8. terminate-all (DatabaseNode.java : terminateAll())

Opis og�lny
Zapytanie, kt�re ma na celu zako�czenie dzia�ania wszystkich w�z��w nale��cych do jednej sieci.

Opis dzia�ania
F.3.7.1. W�ze� otrzymuj�c tak� wiadomo�� wysy�a, do wszystkich s�siaduj�cych w�z��w "NODE tcpport id TERMINATEALL".
F.3.7.2. Ko�czy swoje dzia�anie i zwalnia socket.
F.3.7.3. Klientowi jest wys�ana odpowied� "OK", co oznacza, �e ca�o�� przebieg�a pomy�lnie.

F.4.Komunikacja w�ze�-w�ze�:

F.4.1. Konstrukcja komunikat�w w�z��w wygl�da nast�puj�co:

NODE <tcpport> <id> <operacja z parametrami>

Gdzie:
-tcpport to port tcp w�z�a, z kt�rego rozsy�any jest komunikat do innych w�z��w.
-id to indywidualne dla ka�dego w�z�a id pytania, kt�re kaza� rozes�a� po sieci.
-operacja to operacja, kt�rej wymaga dany w�ze�. Ta cz�� mo�e, ale nie musi zawiera� parametry w zale�no�ci,
jak� operacje rozsy�a w�ze�.

F.4.2. Metoda przetwarzaj�ca komunikaty od w�z�a (DatabaseNode.java : processMessage())

F.4.2.1. Sprawdzenie jaki typ komunikatu wys�a� nam w�tek
F.4.2.2. Uruchamiana jest przypisana do typu komunikatu metoda
F.4.2.3. Zwr�cenie odpowiedzi do (MessageHandler.java : run())
(obs�ugiwane zapytania wraz z funkcjami, kt�re je obs�uguj� znajduj� si� w punkcie F.4.3)

F.4.3. Operacje, kt�rych mo�e u�y� w�ze�:

F.4.3.1. GET klucz (DatabaseNode.java : processMessage(), getResponse(), handleRequest())

Opis og�lny
Komunikat, kt�ry ma na celu zwrot <klucz>:<warto��>, klucz jest podawany przez inny w�ze�,
natomiast zwracana jest warto�� przypisana do klucza wraz z kluczem.

Opis dzia�ania
F.4.3.1.1. W�ze� weryfikuje czy pobrany komunikat by� ju� obs�u�ony
F.4.3.1.2. Je�eli tak, to wysy�a odpowied� "ERROR"
F.4.3.1.3. W przeciwnym przypadku w�ze� sprawdza, czy klucz, kt�ry wskaza� klient, to klucz przypisany do niego.
F.4.3.1.4. Je�eli wskazany klucz jest przypisany do danego w�z�a, to wysy�amy klucz i warto�� przypisana do niego i ko�czy dzia�anie.
F.4.3.1.5. W przeciwnym przypadku w�ze� rozsy�a pobrany komunikat do s�siaduj�cych w�z��w.
F.4.3.1.6. W�ze� dodaje wskazany w punkcie powy�ej komunikat do listy obs�u�onych komunikat�w.
F.4.3.1.7. W�ze� przyjmuje odpowiedzi od s�siaduj�cych w�z��w i weryfikuje je. (DatabaseNode.java : getCheckResponse())
F.4.3.1.8. Je�eli co najmniej jeden w�ze� w sieci wskaza� warto�� dla danego klucza, to wys�ana jest do w�z�a,
od kt�rego przyj�to komunikat odpowied� klucz i przypisana do niego warto��
F.4.3.1.9. W przeciwnym przypadku wys�ana odpowied� to "ERROR".

F.4.3.2. SET klucz warto�� (DatabaseNode.java : processMessage(), setResponse(), handleRequest())

Opis og�lny
Komunikat wys�any do w�z�a w celu zmiany warto�ci przypisanej do klucza.

Opis dzia�ania
F.4.3.2.1. W�ze� weryfikuje czy pobrany komunikat by� ju� obs�u�ony
F.4.3.2.2. Je�eli tak, to wysy�a odpowied� "ERROR"
F.4.3.2.3. W przeciwnym przypadku w�ze� sprawdza, czy klucz, kt�ry chce zmieni� klient, to klucz przypisany do niego.
F.4.3.2.4. Je�eli wskazany klucz jest przypisany do danego w�z�a, zmieniamy warto�� i wysy�amy odpowied� "OK" i ko�czy dzia�anie.
F.4.3.2.5. W przeciwnym przypadku w�ze� rozsy�a pobrany komunikat do s�siaduj�cych w�z��w.
F.4.3.2.6. W�ze� dodaje wskazany w punkcie powy�ej komunikat do listy obs�u�onych komunikat�w.
F.4.3.2.7. W�ze� przyjmuje odpowiedzi od s�siaduj�cych w�z��w i weryfikuje je. (DatabaseNode.java : getCheckResponse())
F.4.3.2.8. Je�eli co najmniej jeden w�ze� w sieci zmieni� warto��, to wys�ana jest do w�z�a, od kt�rego przyj�to
komunikat, odpowied� "OK"
F.4.3.2.9. W przeciwnym przypadku wys�ana odpowied� to "ERROR" oznacza, �e nie ma podanego klucza w sieci.

F.4.3.3. FIND klucz (DatabaseNode.java : processMessage(), findResponse(), handleRequest())

Opis og�lny
Komunikat, kt�ry ma na celu zwrot adresu ip i portu w�z�a, w kt�rym znajduje si� record o podanym
kluczu. Odpowied� jest w postaci <adres ip>:<port>.

Opis dzia�ania
F.4.3.3.1. W�ze� weryfikuje czy pobrany komunikat by� ju� obs�u�ony
F.4.3.3.2. Je�eli tak, to wysy�a odpowied� "ERROR"
F.4.3.3.3. W przeciwnym przypadku sprawdza, czy klucz, kt�ry wskaza� klient, to klucz przypisany do niego.
F.4.3.3.4. Je�eli wskazany klucz jest przypisany do danego w�z�a, to wysy�amy adres ip i port w�z�a i ko�czy dzia�anie.
F.4.3.3.5. W przeciwnym przypadku w�ze� rozsy�a pobrany komunikat do s�siaduj�cych w�z��w.
F.4.3.3.6. W�ze� dodaje wskazany w punkcie powy�ej komunikat do listy obs�u�onych komunikat�w.
F.4.3.3.7. W�ze� przyjmuje odpowiedzi od s�siaduj�cych w�z��w i weryfikuje je. (DatabaseNode.java : getCheckResponse())
F.4.3.3.8. Je�eli co najmniej jeden w�ze� w sieci wskaza� adres ip i port, to wys�ana jest do w�z�a, od kt�rego
przyj�to komunikat, odpowied� adres ip i port w�z�a, do kt�rego przypisany jest dany klucz.
F.4.3.3.9. W przeciwnym przypadku wys�ana odpowied� to "ERROR" oznacza, �e nie ma podanego klucza w sieci.

F.4.3.4. MAX (DatabaseNode.java : processMessage(), maxResponse(), getExtremum())

Opis og�lny
Komunikat maj�cy na celu zwrot najwi�kszej warto�ci w sieci, odpowied� jest w postaci <klucz>:<warto��>.

Opis dzia�ania
F.4.3.4.1. W�ze� weryfikuje czy pobrany komunikat by� ju� obs�u�ony
F.4.3.4.2. Je�eli tak, to wysy�a odpowied� w postaci swojego klucza i przypisanej do niej warto�ci
F.4.3.4.3. W przeciwnym przypadku w�ze� przyjmuje, �e maksymalna warto�� jest to jego lokalna warto��, czyli
przyjmuje jako maksymalna warto�� swoj� warto��, a jako klucz sw�j klucz
F.4.3.4.4. W�ze� rozsy�a pobrany komunikat do s�siaduj�cych w�z��w.
F.4.3.4.5. W�ze� dodaje wskazany w punkcie powy�ej komunikat do listy obs�u�onych komunikat�w
F.4.3.4.6. Ka�d� odpowied� por�wnuje z przypisanym maxem
F.4.3.4.7. Je�eli dana odpowied� jest wi�ksza od wskazanego powy�ej maxa, to przypisuje j�, jako warto�� maksymaln�, oraz
wskazany klucz jest kluczem maksymalnej warto�ci
F.4.3.4.8. W przeciwnym przypadku przechodzi do nast�pnej odpowiedzi
F.4.3.4.9. Po sprawdzeniu wszystkich odpowiedzi wysy�a do w�z�a, od kt�rego otrzyma� komunikat, odpowied� w postaci
maksymalnej warto�ci i klucza maksymalnej warto�ci

F.4.3.5. MIN (DatabaseNode.java : processMessage(), minResponse(), getExtremum())

Opis og�lny
Komunikat, kt�ry ma na celu zwrot najmniejszej warto�ci w sieci. Odpowied� ma posta� <klucz>:<warto��>.

Opis dzia�ania
F.4.3.5.1. W�ze� weryfikuje czy pobrany komunikat by� ju� obs�u�ony
F.4.3.5.2. Je�eli tak, to wysy�a odpowied� w postaci swojego klucza i przypisanej do niej warto�ci
F.4.3.5.3. W przeciwnym przypadku w�ze� przyjmuje, �e minimalna warto�� jest to jego lokalna warto��, czyli
przyjmuje jako minimalna warto�� swoj� warto��, a jako klucz sw�j klucz
F.4.3.5.4. W�ze� rozsy�a pobrany komunikat do s�siaduj�cych w�z��w.
F.4.3.5.5. W�ze� dodaje wskazany w punkcie powy�ej komunikat do listy obs�u�onych komunikat�w
F.4.3.5.6. Ka�d� odpowied� por�wnuje z przypisanym minem
F.4.3.5.7. Je�eli dana odpowied� jest mniejsza od wskazanego powy�ej mina, to przypisuje j�, jako warto�� minimaln�, oraz
wskazany klucz jest kluczem minimalnej warto�ci
F.4.3.5.8. W przeciwnym przypadku przechodzi do nast�pnej odpowiedzi
F.4.3.5.9. Po sprawdzeniu wszystkich odpowiedzi wysy�a do w�z�a, od kt�rego otrzyma� komunikat, odpowied� w postaci
minimalnej warto�ci i klucza minimalnej warto�ci

F.4.3.6. TERMINATE (DatabaseNode.java : processMessage(), terminateResponse())

Opis og�lny
Komunikat ma na celu poinformowanie s�siaduj�cych w�z��w o tym, �e s�siaduj�cy do nich w�ze� od��cza si� od sieci
i ko�czy dzia�anie

Opis dzia�ania
F.4.3.6.1. W�ze� usuwa z listy s�siaduj�cych w�z��w w�ze�, kt�ry wys�a� komunikat.
F.4.3.6.2. W�ze� usuwa wszystkie obs�u�one pytania od tego w�z�a z listy pyta�.
F.4.3.6.3. W�ze� nie wysy�a �adnej odpowiedzi

F.4.3.7. TERMINATEALL (DatabaseNode.java : processMessage(), terminateAll())

Opis og�lny
Komunikat ma na celu wy��czenie dzia�ania w�z�a i wys�anie do s�siaduj�cych w�z��w tego samego komunikatu, aby wszystkie
w�z�y w sieci zosta�y wy��czone.

Opis dzia�ania
F.4.3.7.1. W�ze� usuwa z listy s�siaduj�cych w�z��w w�ze�, kt�ry wys�a� komunikat.
F.4.3.7.2. W�ze� wysy�a, do wszystkich s�siaduj�cych w�z��w "NODE tcpport id TERMINATEALL".
F.4.3.7.3. Ko�czy swoje dzia�anie i zwalnia socket.
F.4.3.7.4. W�ze� nie wysy�a �adnej odpowiedzi

F.4.3.8. ADDNEIGHBOUR (DatabaseNode.java : processMessage(), addNeighbour())

Opis og�lny
Komunikat ma na celu poinformowanie o do��czeniu do sieci nowego w�z�a i dodanie go do listy s�siad�w przez te w�z�y,
z kt�rymi s�siaduje

Opis dzia�ania
Jedyn� czynno�ci�, kt�ra wykonuje w�ze� po otrzymaniu komunikatu, to dodanie nowego w�z�a do listy s�siad�w.

F.5. Testy

Przygotowane i wykonane testy jednostkowe znajduj� si� w podfolderze Rozproszona Baza Danych/tests.

Testy og�lne (znajduj� si� w Rozproszona Baza Danych/src/testy):
-utworzenie sieci sk�adaj�cej si� z 25 w�z��w (Rysunek sieci przedstawiony w pliku Przyk�adowa sie�.pdf)
-uruchomienie kilku klient�w
-wys�anie zapyta� do sieci od klient�w
-wy��czenie wszystkich w�z��w (wy��czenie sieci)

W tym samym folderze znajduje si� testy z grupy wyk�adowej.

F.6. Regeneracja baz danych
Regeneracja zosta�a zaimportowana za pomoc� kilku nowych komunikat�w:
F.6.1. YOUFIRST (DatabaseNode.java: processMessage (), youFirst())
Komunikat ten zostaje wys�any w momencie zamkni�cia bootstrap node, do kolejnego utworzonego w�z�a, przez to on teraz staje si� bootstrap nodem.
F.6.2. RUFIRST (DatabaseNode.java: terminateResponse(), checkConnection(), checkFirst())
Komunikat ma na celu sprawdzenie, czy w�ze� po od��czeniu w�z�a dalej ma po��czenie z bootstrap nodem, je�eli nie to wysy�a do wszystkich s�siad�w wy��czonego w�z�a, aby doda� go s�siad�w, aby nie utraci� po��czenia z bootstrap nodem.

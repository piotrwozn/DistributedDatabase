/*
  autor: Piotr Woźnicki
  numer studenta: so0139
  projekt: Rozproszona Baza Danych
  przedmiot: SKJ-Sieci komputerowe i programowanie sieciowe w języku Java
  grupa: 24c
 */


/**
 * Klasa ma na celu przechowywanie adresu ip i portu dla węzła
 */
public class NodeInfo {
    private final String address;
    private final int port;

    public NodeInfo(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public synchronized String getAddress() {
        return this.address;
    }

    public synchronized int getPort() {
        return this.port;
    }

    @Override
    public synchronized String toString() {
        return address + ":" + port;
    }
}

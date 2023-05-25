/*
  autor: Piotr Woźnicki
  numer studenta: so0139
  projekt: Rozproszona Baza Danych
  przedmiot: SKJ-Sieci komputerowe i programowanie sieciowe w języku Java
  grupa: 24c
 */

import java.io.IOException;

@FunctionalInterface
public interface ResponseProcessor {
    String process(String inputLine, boolean fromNode, boolean isAnswered) throws IOException;


}

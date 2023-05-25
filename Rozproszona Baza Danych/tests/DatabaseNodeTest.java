import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseNodeTest {

    private final boolean isFirst = true;

    @Test
    void changeRecord() throws IOException {
        DatabaseNode databaseNode = new DatabaseNode(1,1,2,new ArrayList<>(), true);
        databaseNode.changeRecord(3,1);
        String string = databaseNode.getValue();
        String[] tokens = string.split(":");
        assertEquals("3",tokens[0]);
        assertEquals("1",tokens[1]);
        assertEquals("OK",databaseNode.changeRecord(5,2));
        databaseNode.terminate();
    }

    @Test
    void getValue() throws IOException {
        DatabaseNode databaseNode = new DatabaseNode(1,1,2,new ArrayList<>(), isFirst);
        assertEquals("1:2",databaseNode.getValue());
        databaseNode.terminate();
      }

    @Test
    void getMax() throws IOException {
        DatabaseNode databaseNode = new DatabaseNode(1,1,2,new ArrayList<>(), isFirst);
        assertEquals("1:2",databaseNode.getExtremum("MAX",false,""));
        databaseNode.terminate();
      }

    @Test
    void getMin() throws IOException {
        DatabaseNode databaseNode = new DatabaseNode(1,1,2,new ArrayList<>(), isFirst);
        assertEquals("1:2",databaseNode.getExtremum("MIN",false,""));
        databaseNode.terminate();
      }

    @Test
    void findKey() throws IOException {
        String input = "find-key 1";
        String input2 = "find-key 2";
        DatabaseNode databaseNode = new DatabaseNode(1,1,2,new ArrayList<>(), isFirst);
        assertEquals("127.0.0.1:1",databaseNode.handleRequest(false,input,Constants.FIND));
        assertEquals("ERROR",databaseNode.handleRequest(false,input2,Constants.FIND));
        databaseNode.terminate();
      }


    @Test
    void get() throws IOException {
        String input = "get-value 1";
        String input2 = "get-value 2";
        DatabaseNode databaseNode = new DatabaseNode(1,1,2,new ArrayList<>(), isFirst);
        assertEquals("1:2",databaseNode.handleRequest(false,input,Constants.GET));
        assertEquals("ERROR",databaseNode.handleRequest(false,input2,Constants.GET));
        databaseNode.terminate();
      }


    @Test
    void set() throws IOException {
        String input = "set-value 1:5";
        String input2 = "set-value 1:7";
        String input3 = "set-value 2:8";
        DatabaseNode databaseNode = new DatabaseNode(1,1,2,new ArrayList<>(), isFirst);
        databaseNode.handleRequest(false,input,Constants.SET);
        String string = databaseNode.getValue();
        String[] tokens = string.split(":");
        assertEquals("5",tokens[1]);
        assertEquals("OK",databaseNode.handleRequest(false,input2,Constants.SET));
        assertEquals("ERROR",databaseNode.handleRequest(false,input3,Constants.SET));
        databaseNode.terminate();
      }


    @Test
    void terminate() throws IOException {
        DatabaseNode databaseNode = new DatabaseNode(1,1,2,new ArrayList<>(), isFirst);
        assertEquals("OK",databaseNode.terminate());
        databaseNode.terminate();
      }

    @Test
    void terminateAll() throws IOException {
        DatabaseNode databaseNode = new DatabaseNode(1,1,2,new ArrayList<>(), isFirst);
        assertEquals("OK",databaseNode.terminateAll(false,""));
        databaseNode.terminate();
      }

    @Test
    void addQuestion() throws IOException {
        DatabaseNode databaseNode = new DatabaseNode(1,1,2,new ArrayList<>(), isFirst);
        databaseNode.addQuestion("kocham SKJ");
        List<String> strings = databaseNode.getQuestions();
        assertEquals("kocham SKJ",strings.get(0));
        databaseNode.terminate();
      }

    @Test
    void checkQuestions() throws IOException {
        DatabaseNode databaseNode = new DatabaseNode(1,1,2,new ArrayList<>(), isFirst);
        databaseNode.addQuestion("kocham SKJ");
        assertTrue(databaseNode.checkQuestions("kocham SKJ"));
        assertFalse(databaseNode.checkQuestions("Nie kocham SKJ"));
        databaseNode.terminate();
      }

    @Test
    void processMessage() throws IOException {
        DatabaseNode databaseNode = new DatabaseNode(1,1,2,new ArrayList<>(), isFirst);
        assertEquals("1:2",databaseNode.processMessage("NODE 2 2 GET 1"));
        assertEquals("ERROR",databaseNode.processMessage("NODE 2 2 GET 3"));
        assertEquals("OK",databaseNode.processMessage("NODE 2 2 SET 1 5"));
        assertEquals("ERROR",databaseNode.processMessage("NODE 2 2 SET 3 5"));
        assertEquals("127.0.0.1:1",databaseNode.processMessage("NODE 2 2 FIND 1"));
        assertEquals("ERROR",databaseNode.processMessage("NODE 2 2 FIND 3"));
        assertEquals("1:5",databaseNode.processMessage("NODE 2 2 MAX"));
        assertEquals("1:5",databaseNode.processMessage("NODE 2 2 MIN"));
        databaseNode.terminate();
      }

}
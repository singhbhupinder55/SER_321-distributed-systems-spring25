import org.junit.Test;
import static org.junit.Assert.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ServerTest {

    Socket sock;
    OutputStream out;
    ObjectOutputStream os;

    DataInputStream in;


    // Establishing a connection to the server, make sure you start the server on localhost and 8888
    @org.junit.Before
    public void setUp() throws Exception {
        // Establish connection to server and create in/out streams
        sock = new Socket("localhost", 8888); // connect to host and socket

        // get output channel
        out = sock.getOutputStream();

        // create an object output writer (Java only)
        os = new ObjectOutputStream(out);

        // setup input stream
        in = new DataInputStream(sock.getInputStream());

        // Clear inventory before each test
        JSONObject resetRequest = new JSONObject();
        resetRequest.put("type", "inventory");
        resetRequest.put("task", "reset");  // Implement a reset action on server

        os.writeObject(resetRequest.toString());
        os.flush();
        in.readUTF();  // Read response to ensure reset is processed

        System.out.println("Inventory reset before tests.");
    }

    @org.junit.After
    public void close() throws Exception {
        if (out != null)  out.close();
        if (sock != null) sock.close();
    }

    /** ======================== Inventory Test Cases ======================== **/

    @Test
    public void testViewEmptyInventory() throws IOException {
        JSONObject req = new JSONObject();
        req.put("type", "inventory");
        req.put("task", "view");

        os.writeObject(req.toString());
        os.flush();

        String response = in.readUTF();
        JSONObject res = new JSONObject(response);

        assertTrue(res.getBoolean("ok"));
        assertEquals("inventory", res.getString("type"));
        assertEquals(0, res.getJSONArray("inventory").length());
    }

    @Test
    public void testAddItemToInventory() throws IOException {
        JSONObject req = new JSONObject();
        req.put("type", "inventory");
        req.put("task", "add");
        req.put("productName", "Laptop");
        req.put("quantity", 5);

        os.writeObject(req.toString());
        os.flush();

        String response = in.readUTF();
        JSONObject res = new JSONObject(response);

        assertTrue(res.getBoolean("ok"));
        assertEquals("inventory", res.getString("type"));
        assertEquals("Item added: Laptop (5)", res.getString("message"));
    }

    @Test
    public void testBuyItemFromInventory() throws IOException {
        // Add item first
        testAddItemToInventory();

        // Buy item
        JSONObject req = new JSONObject();
        req.put("type", "inventory");
        req.put("task", "buy");
        req.put("productName", "Laptop");
        req.put("quantity", 3);

        os.writeObject(req.toString());
        os.flush();

        String response = in.readUTF();
        JSONObject res = new JSONObject(response);

        assertTrue(res.getBoolean("ok"));
        assertEquals("inventory", res.getString("type"));
        assertEquals("Purchased 3 of Laptop", res.getString("message"));
    }

    @Test
    public void testAddItemWithZeroQuantity() throws IOException {
        JSONObject req = new JSONObject();
        req.put("type", "inventory");
        req.put("task", "add");
        req.put("productName", "Monitor");
        req.put("quantity", 0);  // Invalid quantity

        os.writeObject(req.toString());
        os.flush();

        String response = in.readUTF();
        JSONObject res = new JSONObject(response);

        assertFalse(res.getBoolean("ok"));
        assertEquals("Invalid quantity. Must be a positive integer.", res.getString("message"));
    }
    @Test
    public void testBuyItemWithNegativeQuantity() throws IOException {
        // Add item first
        testAddItemToInventory();

        JSONObject req = new JSONObject();
        req.put("type", "inventory");
        req.put("task", "buy");
        req.put("productName", "Laptop");
        req.put("quantity", -3);  // Invalid quantity

        os.writeObject(req.toString());
        os.flush();

        String response = in.readUTF();
        JSONObject res = new JSONObject(response);

        assertFalse(res.getBoolean("ok"));
        assertEquals("Invalid quantity. Must be a positive integer.", res.getString("message"));
    }
    @Test
    public void testAddItemWithoutProductName() throws IOException {
        JSONObject req = new JSONObject();
        req.put("type", "inventory");
        req.put("task", "add");
        req.put("quantity", 5);  // Missing productName

        os.writeObject(req.toString());
        os.flush();

        String response = in.readUTF();
        JSONObject res = new JSONObject(response);

        assertFalse(res.getBoolean("ok"));
        assertEquals("Field productName does not exist in request", res.getString("message"));
    }
    @Test
    public void testBuyItemWithoutProductName() throws IOException {
        JSONObject req = new JSONObject();
        req.put("type", "inventory");
        req.put("task", "buy");
        req.put("quantity", 5);  // Missing productName

        os.writeObject(req.toString());
        os.flush();

        String response = in.readUTF();
        JSONObject res = new JSONObject(response);

        assertFalse(res.getBoolean("ok"));
        assertEquals("Field productName does not exist in request", res.getString("message"));
    }
    @Test
    public void testBuyItemWithoutQuantity() throws IOException {
        JSONObject req = new JSONObject();
        req.put("type", "inventory");
        req.put("task", "buy");
        req.put("productName", "Laptop");  // Missing quantity

        os.writeObject(req.toString());
        os.flush();

        String response = in.readUTF();
        JSONObject res = new JSONObject(response);

        assertFalse(res.getBoolean("ok"));
        assertEquals("Field quantity does not exist in request", res.getString("message"));
    }
    @Test
    public void testCharCountEmptyString() throws IOException {
        JSONObject req = new JSONObject();
        req.put("type", "charcount");
        req.put("count", "");  // Empty string

        os.writeObject(req.toString());
        os.flush();

        String response = in.readUTF();
        JSONObject res = new JSONObject(response);

        assertTrue(res.getBoolean("ok"));
        assertEquals(0, res.getInt("result"));
    }
    @Test
    public void testCharCountCharacterNotFound() throws IOException {
        JSONObject req = new JSONObject();
        req.put("type", "charcount");
        req.put("count", "hello world");
        req.put("findchar", true);
        req.put("find", "z");  // Character not in string

        os.writeObject(req.toString());
        os.flush();

        String response = in.readUTF();
        JSONObject res = new JSONObject(response);

        assertTrue(res.getBoolean("ok"));
        assertEquals(0, res.getInt("result"));
    }
    @Test
    public void testAddManyEmptyList() throws IOException {
        JSONObject req = new JSONObject();
        req.put("type", "addmany");
        req.put("nums", new JSONArray());  // Empty list

        os.writeObject(req.toString());
        os.flush();

        String response = in.readUTF();
        JSONObject res = new JSONObject(response);

        assertTrue(res.getBoolean("ok"));
        assertEquals("addmany", res.getString("type"));
        assertEquals(0, res.getInt("result"));  // Ensure empty sum is handled properly
    }


    @Test
    public void testBuyMoreThanAvailable() throws IOException {
        // Add item first
        testAddItemToInventory();

        // Attempt to buy more than available
        JSONObject req = new JSONObject();
        req.put("type", "inventory");
        req.put("task", "buy");
        req.put("productName", "Laptop");
        req.put("quantity", 10);

        os.writeObject(req.toString());
        os.flush();

        String response = in.readUTF();
        JSONObject res = new JSONObject(response);

        assertFalse(res.getBoolean("ok"));
        assertEquals("inventory", res.getString("type"));
        assertEquals("Product Laptop not available in quantity 10", res.getString("message"));
    }

    @Test
    public void testBuyNonExistingItem() throws IOException {
        JSONObject req = new JSONObject();
        req.put("type", "inventory");
        req.put("task", "buy");
        req.put("productName", "Tablet");
        req.put("quantity", 2);

        os.writeObject(req.toString());
        os.flush();

        String response = in.readUTF();
        JSONObject res = new JSONObject(response);

        assertFalse(res.getBoolean("ok"));
        assertEquals("inventory", res.getString("type"));
        assertEquals("Product Tablet not in inventory", res.getString("message"));
    }

    /** ======================== CharCount Test Cases ======================== **/

    @Test
    public void testCharCountTotalCharacters() throws IOException {
        JSONObject req = new JSONObject();
        req.put("type", "charcount");
        req.put("count", "hello world");

        os.writeObject(req.toString());
        os.flush();

        String response = in.readUTF();
        JSONObject res = new JSONObject(response);

        assertTrue(res.getBoolean("ok"));
        assertEquals("charcount", res.getString("type"));
        assertEquals(11, res.getInt("result"));
    }

    @Test
    public void testCharCountSpecificCharacter() throws IOException {
        JSONObject req = new JSONObject();
        req.put("type", "charcount");
        req.put("count", "hello world");
        req.put("findchar", true);
        req.put("find", "l");

        os.writeObject(req.toString());
        os.flush();

        String response = in.readUTF();
        JSONObject res = new JSONObject(response);

        assertTrue(res.getBoolean("ok"));
        assertEquals("charcount", res.getString("type"));
        assertEquals(3, res.getInt("result"));
    }

    @Test
    public void testCharCountMissingCountField() throws IOException {
        JSONObject req = new JSONObject();
        req.put("type", "charcount");

        os.writeObject(req.toString());
        os.flush();

        String response = in.readUTF();
        JSONObject res = new JSONObject(response);

        assertFalse(res.getBoolean("ok"));
        assertEquals("Field count does not exist in request", res.getString("message"));
    }

    @Test
    public void testCharCountInvalidFindField() throws IOException {
        JSONObject req = new JSONObject();
        req.put("type", "charcount");
        req.put("count", "hello world");
        req.put("findchar", true);
        req.put("find", "ll"); // Invalid: more than one character

        os.writeObject(req.toString());
        os.flush();

        String response = in.readUTF();
        JSONObject res = new JSONObject(response);

        assertFalse(res.getBoolean("ok"));
        assertEquals("Find must be a single character.", res.getString("message"));
    }

    /** ======================== General Error Handling ======================== **/

    @Test
    public void testInvalidRequestType() throws IOException {
        JSONObject req = new JSONObject();
        req.put("type", "invalid_request");

        os.writeObject(req.toString());
        os.flush();

        String response = in.readUTF();
        JSONObject res = new JSONObject(response);

        assertFalse(res.getBoolean("ok"));
        assertEquals("Type invalid_request is not supported.", res.getString("message"));
    }

    @Test
    public void testNonJSONRequest() throws IOException {
        os.writeObject("not a json");

        String response = in.readUTF();
        JSONObject res = new JSONObject(response);

        assertFalse(res.getBoolean("ok"));
        assertEquals("req not JSON", res.getString("message"));
    }








    @Test
    public void addRequest() throws IOException {
        // create a correct req for server
        JSONObject req = new JSONObject();
        req.put("type", "add");
        req.put("num1", "1");
        req.put("num2", "2");

        // write the whole message
        os.writeObject(req.toString());
        // make sure it wrote and doesn't get cached in a buffer
        os.flush();

        String i = (String) in.readUTF();
        // assuming I get correct JSON back
        JSONObject res = new JSONObject(i);

        // test response
        assertTrue(res.getBoolean("ok"));
        assertEquals("add", res.getString("type"));
        assertEquals(3, res.getInt("result"));

        // Wrong request to server num2 missing
        JSONObject req2 = new JSONObject();
        req2.put("type", "add");
        req2.put("num1", "1");
        // write the whole message
        os.writeObject(req2.toString());
        // make sure it wrote and doesn't get cached in a buffer
        os.flush();

        i = (String) in.readUTF();
        // assuming I get correct JSON back
        res = new JSONObject(i);

        System.out.println(res);

        // test response
        assertFalse(res.getBoolean("ok"));
        assertEquals("Field num2 does not exist in request", res.getString("message"));

        // Wrong request to server num1 missing
        JSONObject req3 = new JSONObject();
        req3.put("type", "add");
        req3.put("num2", "1");
        // write the whole message
        os.writeObject(req3.toString());
        // make sure it wrote and doesn't get cached in a buffer
        os.flush();

        i = (String) in.readUTF();
        // assuming I get correct JSON back
        res = new JSONObject(i);

        System.out.println(res);

        // test response
        assertFalse(res.getBoolean("ok"));
        assertEquals("Field num1 does not exist in request", res.getString("message"));

        // Wrong request to server num1 num2 missing
        JSONObject req4 = new JSONObject();
        req4.put("type", "add");
        // write the whole message
        os.writeObject(req4.toString());
        // make sure it wrote and doesn't get cached in a buffer
        os.flush();

        i = (String) in.readUTF();
        // assuming I get correct JSON back
        res = new JSONObject(i);

        System.out.println(res);

        // test response
        assertFalse(res.getBoolean("ok"));
        assertEquals("Field num1 does not exist in request", res.getString("message"));

        // Wrong request to server num2 missing
        JSONObject req5 = new JSONObject();
        req5.put("type", "add");
        req5.put("num1", "hello");
        req5.put("num2", "2");
        // write the whole message
        os.writeObject(req5.toString());
        // make sure it wrote and doesn't get cached in a buffer
        os.flush();

        i = (String) in.readUTF();
        // assuming I get correct JSON back
        res = new JSONObject(i);

        System.out.println(res);

        // test response
        assertFalse(res.getBoolean("ok"));
        assertEquals("Field num1/num2 needs to be of type: int", res.getString("message"));
    }

    @Test
    public void echoRequest() throws IOException {
        // valid request with data
        JSONObject req1 = new JSONObject();
        req1.put("type", "echo");
        req1.put("data", "gimme this back!");
        // write the whole message
        os.writeObject(req1.toString());
        // make sure it wrote and doesn't get cached in a buffer
        os.flush();
        String i = (String) in.readUTF();
        // assuming I get correct JSON back
        JSONObject res = new JSONObject(i);
        // test response
        assertTrue(res.getBoolean("ok"));
        assertEquals("echo", res.getString("type"));
        assertEquals("Here is your echo: gimme this back!", res.getString("echo"));

        // Invalid request - no data sent
        JSONObject req2 = new JSONObject();
        req2.put("type", "echo");
        // write the whole message
        os.writeObject(req2.toString());
        // make sure it wrote and doesn't get cached in a buffer
        os.flush();
        i = (String) in.readUTF();
        // assuming I get correct JSON back
        res = new JSONObject(i);
        System.out.println(res);
        // test response
        assertFalse(res.getBoolean("ok"));
        assertEquals("Field data does not exist in request", res.getString("message"));
    }

    @Test
    public void addManyRequest() throws IOException {
        // create a correct req for server
        JSONObject req = new JSONObject();
        req.put("type", "addmany");
        List<String> myList = Arrays.asList(
                "12",
                "15",
                "111",
                "42"
        );
        req.put("nums", myList);
        // write the whole message
        os.writeObject(req.toString());
        // make sure it wrote and doesn't get cached in a buffer
        os.flush();
        String i = (String) in.readUTF();
        // assuming I get correct JSON back
        JSONObject res = new JSONObject(i);
        // test response
        assertTrue(res.getBoolean("ok"));
        assertEquals("addmany", res.getString("type"));
        assertEquals(180, res.getInt("result"));

        // Invalid request to server
        JSONObject req2 = new JSONObject();
        req2.put("type", "addmany");
        myList = Arrays.asList(
                "two",
                "15",
                "111",
                "42"
        );
        req2.put("nums", myList);
        // write the whole message
        os.writeObject(req2.toString());
        // make sure it wrote and doesn't get cached in a buffer
        os.flush();
        i = (String) in.readUTF();
        // assuming I get correct JSON back
        res = new JSONObject(i);
        System.out.println(res);
        // test response
        assertFalse(res.getBoolean("ok"));
        assertEquals("Values in array need to be ints", res.getString("message"));
    }

    @Test
    public void notJSON() throws IOException {
        // create a correct req for server
        os.writeObject("a");

        String i = (String) in.readUTF();
        // assuming I get correct JSON back
        JSONObject res = new JSONObject(i);

        // test response
        assertFalse(res.getBoolean("ok"));
        assertEquals("req not JSON", res.getString("message"));

        // calling the other test to make sure server continues to work and the "continue" does what it is supposed to do
        addRequest();
    }


}
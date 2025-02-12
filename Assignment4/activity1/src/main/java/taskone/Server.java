/**
  File: Server.java
  Author: Student in Fall 2020B
  Description: Server class in package taskone.
*/

package taskone;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import org.json.JSONObject;

/**
 * Class: Server
 * Description: Server tasks.
 */
class Server {
    static Socket conn;
    static Performer performer;


    public static void main(String[] args) throws Exception {
        int port;
        StringList strings = new StringList();
        performer = new Performer(strings);

        if (args.length != 1) {
            // gradle runServer -Pport=9099 -q --console=plain
            System.out.println("Usage: gradle runServer -Pport=9099 -q --console=plain");
            System.exit(1);
        }
        port = -1;
        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException nfe) {
            System.out.println("[Port] must be an integer");
            System.exit(2);
        }
        ServerSocket server = new ServerSocket(port);
        System.out.println("Server Started...");
        while (true) {
            System.out.println("Accepting a Request...");
            conn = server.accept();
            System.out.println("Client connected!");
            doPerform();

        }
    }

    public static void doPerform() {
        boolean quit = false;
        OutputStream out = null;
        InputStream in = null;
        try {
            out = conn.getOutputStream();
            in = conn.getInputStream();
            System.out.println("Server connected to client:");
            while (!quit) {

                byte[] messageBytes = NetworkUtils.receive(in);
                JSONObject message = JsonUtils.fromByteArray(messageBytes);
                JSONObject returnMessage = new JSONObject();

                if (!message.has("selected") || !(message.get("selected") instanceof Integer)) {
                    returnMessage = Performer.error("Invalid input: 'selected' must be an integer.");
                } else {

                int choice = message.getInt("selected");
                switch (choice) {
                    case 1:
                        String inStr = message.optString("data", "");
                        returnMessage = performer.add(inStr);
                        break;

                    case 3:
                        int index = -1; // Initialize index
                        boolean validInput = false;

                        while (!validInput) {
                            if (!message.has("data") || message.getString("data").trim().isEmpty()) {
                                returnMessage = Performer.error("Missing index. Please enter a valid number.");
                            } else {
                                try {
                                    index = Integer.parseInt(message.getString("data")) - 1; // Convert user input (1-based to 0-based)
                                    validInput = true; // If parsing succeeds, break out of the loop
                                } catch (NumberFormatException e) {
                                    returnMessage = Performer.error("Invalid input. Please enter a valid number.");
                                }
                            }

                            if (!validInput) {
                                byte[] output = JsonUtils.toByteArray(returnMessage);
                                NetworkUtils.send(out, output);
                                messageBytes = NetworkUtils.receive(in); // Ask client again
                                message = JsonUtils.fromByteArray(messageBytes);
                            }
                        }

                        returnMessage = performer.display(index);
                        break;


                    case 4:
                        returnMessage = performer.count();
                        break;
                    case 0:
                        returnMessage = performer.quit();
                        quit = true;
                        break;
                    default:
                        returnMessage = Performer.error("Invalid selection: " + choice + " is not an option.");
                        break;
                }
            }
                // we are converting the JSON object we have to a byte[]
                byte[] output = JsonUtils.toByteArray(returnMessage);
                NetworkUtils.send(out, output);
            }
            // close the resource
            System.out.println("close the resources of client ");
            out.close();
            in.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

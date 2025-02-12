package taskone;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import org.json.JSONObject;

/**
 * ThreadedServer class to handle multiple clients concurrently.
 */
public class ThreadedServer {
    private static StringList sharedList = new StringList();  // Shared resource (Thread-Safe)

    public static void main(String[] args) throws Exception {
        int port;

        if (args.length != 1) {
            System.out.println("Usage: gradle runThreadedServer -Pport=9099 -q --console=plain");
            System.exit(1);
        }

        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException nfe) {
            System.out.println("[Port] must be an integer");
            System.exit(2);
            return;
        }

        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Threaded Server Started...");

        while (true) {
            System.out.println("Waiting for a client...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("New client connected!");

            // Spawn a new thread for each client
            new ClientHandler(clientSocket, sharedList).start();
        }
    }
}

/**
 * Handles each client in a separate thread.
 */
class ClientHandler extends Thread {
    private Socket socket;
    private Performer performer;
    private OutputStream out;
    private InputStream in;

    public ClientHandler(Socket socket, StringList sharedList) {
        this.socket = socket;
        this.performer = new Performer(sharedList);
    }

    public void run() {
        try {
            out = socket.getOutputStream();
            in = socket.getInputStream();

            System.out.println("Client connected, processing requests...");

            boolean quit = false;
            while (!quit) {
                byte[] messageBytes = NetworkUtils.receive(in);
                JSONObject message = JsonUtils.fromByteArray(messageBytes);
                JSONObject returnMessage = new JSONObject();

                if (!message.has("selected") || !(message.get("selected") instanceof Integer)) {
                    returnMessage = Performer.error("Invalid input: 'selected' must be an integer.");
                } else {
                    int choice = message.getInt("selected");

                    switch (choice) {
                        case 1: // Add
                            String inStr = message.optString("data", "");
                            returnMessage = performer.add(inStr);
                            break;
                        case 3: // Display
                            int index = -1;
                            try {
                                index = Integer.parseInt(message.getString("data")) - 1; // User input 1-based to 0-based
                                returnMessage = performer.display(index);
                            } catch (NumberFormatException e) {
                                returnMessage = Performer.error("Invalid input. Please enter a valid number.");
                            }
                            break;
                        case 4: // Count
                            returnMessage = performer.count();
                            break;
                        case 0: // Quit
                            returnMessage = performer.quit();
                            quit = true;
                            break;
                        default:
                            returnMessage = Performer.error("Invalid selection: " + choice + " is not an option.");
                            break;
                    }
                }

                // Send response back to client
                byte[] output = JsonUtils.toByteArray(returnMessage);
                NetworkUtils.send(out, output);
            }

            // Cleanup resources after client disconnects
            System.out.println("Closing client connection...");
            out.close();
            in.close();
            socket.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

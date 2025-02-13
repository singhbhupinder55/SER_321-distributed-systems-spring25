package taskone;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.json.JSONObject;

/**
 * Handles each client in a separate thread.
 */
public class ClientHandler implements Runnable {
    private Socket socket;
    private Performer performer;
    private OutputStream out;
    private InputStream in;

    public ClientHandler(Socket socket, StringList sharedList) {
        this.socket = socket;
        this.performer = new Performer(sharedList);
    }

    @Override
    public void run() {
        try {
            out = socket.getOutputStream();
            in = socket.getInputStream();

            System.out.println("Client connected, processing requests...");

            boolean quit = false;
            while (!quit) {
                byte[] messageBytes = NetworkUtils.receive(in);

                if (messageBytes == null || messageBytes.length == 0) {
                    System.out.println("Client disconnected unexpectedly.");
                    break;
                }

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
                            int index;
                            try {
                                index = Integer.parseInt(message.getString("data")) - 1;
                                returnMessage = performer.display(index);
                            } catch (NumberFormatException e) {
                                returnMessage = Performer.error("Invalid input. Please enter a valid number.");
                            }
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

                byte[] output = JsonUtils.toByteArray(returnMessage);
                NetworkUtils.send(out, output);

                // Flush output before closing connection
                out.flush();
            }

            System.out.println("Closing client connection...");
            out.close();
            in.close();
            socket.close();
        } catch (IOException | InterruptedException e) {
            System.out.println("Error handling client connection: " + e.getMessage());
        }
    }
}

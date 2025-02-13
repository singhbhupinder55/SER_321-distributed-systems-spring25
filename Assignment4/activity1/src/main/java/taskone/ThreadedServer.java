package taskone;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import org.json.JSONObject;

/**
 * ThreadedServer class to handle multiple clients concurrently.
 * Allows unbounded incoming connections.
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
        System.out.println("Threaded Server Started on port " + port + "...");

        while (true) {
            System.out.println("Waiting for a client...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("New client connected!");

            // Spawn a new thread for each client
            new Thread(new ClientHandler(clientSocket, sharedList)).start();
        }
    }

}

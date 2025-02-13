
package taskone;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import org.json.JSONObject;

/**
 * ThreadPoolServer - A server with a fixed number of concurrent clients.
 */
public class ThreadPoolServer {
    private static StringList sharedList = new StringList();  // Shared list (Thread-Safe)
    private static final int DEFAULT_MAX_THREADS = 4;  // Default max connections if not specified
    private static int maxThreads;  // Maximum allowed concurrent clients

    public static void main(String[] args) throws IOException {
        int port;

        if (args.length < 1) {
            System.out.println("Usage: gradle runThreadPoolServer -Pport=9099 -Pthreads=3 -q --console=plain");
            System.out.println("No thread count specified. Defaulting to " + DEFAULT_MAX_THREADS + " connections.");
            port = 9099; // Default port
            maxThreads = DEFAULT_MAX_THREADS;
        } else {
            try {
                port = Integer.parseInt(args[0]);
                maxThreads = (args.length > 1) ? Integer.parseInt(args[1]) : DEFAULT_MAX_THREADS;
            } catch (NumberFormatException e) {
                System.out.println("Invalid arguments! Port and threads must be integers.");
                System.exit(2);
                return;
            }
        }

        // Create a thread pool with a fixed number of threads
        ExecutorService threadPool = Executors.newFixedThreadPool(maxThreads);
        ServerSocket serverSocket = new ServerSocket(port);

        System.out.println("ThreadPoolServer Started on port " + port + " with max " + maxThreads + " clients at a time.");

        while (true) {
            System.out.println("Waiting for a client...");
            Socket clientSocket = serverSocket.accept();

            // Check if thread pool is full
            if (((ThreadPoolExecutor) threadPool).getActiveCount() >= maxThreads) {
                System.out.println("Client connection refused: Server at max capacity.");
                sendConnectionRefused(clientSocket);
                continue; // Reject the client and continue accepting new ones
            }

            System.out.println("New client connected!");
            threadPool.execute(new ClientHandler(clientSocket, sharedList));
        }
    }

    /**
     * Send a "Connection Refused" message to the client and close the connection.
     */
    private static void sendConnectionRefused(Socket clientSocket) {
        try {
            OutputStream out = clientSocket.getOutputStream();
            JSONObject errorMessage = new JSONObject();
            errorMessage.put("type", "error");
            errorMessage.put("message", "Server is at full capacity. Try again later.");
            errorMessage.put("disconnect", true);  // Client should recognize this and exit.

            byte[] responseBytes = JsonUtils.toByteArray(errorMessage);
            out.write(responseBytes);
            out.flush();

            // Close streams and force socket shutdown
            clientSocket.shutdownOutput();
            clientSocket.shutdownInput();
            clientSocket.close();

            System.out.println("Rejected client connection closed.");
        } catch (IOException e) {
            System.out.println("Failed to send connection refused message.");
        }
    }


}

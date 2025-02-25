package distributed;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Leader {
    private static final int PORT = 8888;
    private static final int MIN_NODES = 3;
    private static final List<Socket> connectedNodes = new ArrayList<>();
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println(" Leader is running on port " + PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                executor.submit(() -> handleConnection(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleConnection(Socket socket) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String request = in.readLine();
            if (request == null) {
                System.out.println(" Empty connection, closing socket.");
                socket.close();
                return;
            }

            if (request.equals("NODE")) {
                // This ensures Nodes properly register with the Leader
                synchronized (connectedNodes) {
                    connectedNodes.add(socket);
                }
                System.out.println("🔗 Node registered. Total Nodes: " + connectedNodes.size());
            } else if (request.startsWith("CLIENT")) {
                System.out.println(" Client connected.");
                handleClientRequest(request.substring(7), out);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClientRequest(String request, PrintWriter out) {
        synchronized (connectedNodes) {
            if (connectedNodes.size() < MIN_NODES) {
                System.out.println(" Error: Not enough nodes available. Need at least " + MIN_NODES);
                out.println(" Error: Not enough nodes available.");
                return;
            }
        }

        try {
            String[] parts = request.split(";");

            // Validate format
            if (parts.length != 2) {
                System.out.println(" Error: Invalid request format.");
                out.println(" Error: Invalid request format.");
                return;
            }

            // Validate number list
            List<Integer> numbers = new ArrayList<>();
            String[] numTokens = parts[0].split(",");
            for (String num : numTokens) {
                try {
                    numbers.add(Integer.parseInt(num.trim()));
                } catch (NumberFormatException e) {
                    System.out.println(" Error: Received invalid number from Client: " + num);
                    out.println(" Error: Received invalid number from Client: " + num);
                    return;
                }
            }

            // Validate delay
            int delay;
            try {
                delay = Integer.parseInt(parts[1].trim());
                if (delay < 0) {
                    System.out.println(" Error: Invalid delay received.");
                    out.println(" Error: Invalid delay received.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println(" Error: Delay must be an integer.");
                out.println(" Error: Delay must be an integer.");
                return;
            }

            System.out.println(" Received numbers: " + numbers + " with delay: " + delay + "ms");

            //  **Debug: Print how numbers are distributed among nodes**
            List<List<Integer>> partitions = splitList(numbers, connectedNodes.size());
            for (int i = 0; i < partitions.size(); i++) {
                System.out.println(" Node " + (i + 1) + " will process: " + partitions.get(i));
            }

            //Measure time for sequential computation
            long startTime = System.currentTimeMillis(); // Start timer
            double sequentialSum = computeSequentialSum(numbers, delay);
            long sequentialTime = System.currentTimeMillis() - startTime; // End timer
            System.out.println("⏳ Sequential Computation Time: " + sequentialTime + "ms");

            //Measure time for distributed computation**
            startTime = System.currentTimeMillis(); // Start timer
            double distributedSum = computeDistributedSum(numbers, delay);
            long distributedTime = System.currentTimeMillis() - startTime; // End timer
            System.out.println("⚡ Distributed Computation Time: " + distributedTime + "ms");

            out.println(" Sequential Sum: " + sequentialSum + " (Time: " + sequentialTime + "ms), " +
                    "Distributed Sum: " + distributedSum + " (Time: " + distributedTime + "ms)");
        } catch (Exception e) {
            e.printStackTrace();
            out.println(" Error processing request.");
        }
    }


    private static double computeSequentialSum(List<Integer> numbers, int delay) {
        double sum = 0;
        try {
            for (int num : numbers) {
                sum += num;
                Thread.sleep(delay);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return sum;
    }


    private static String sendToNode(Socket nodeSocket, String dataToSend) {
        try {
            PrintWriter out = new PrintWriter(nodeSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(nodeSocket.getInputStream()));

            out.println(dataToSend); // Send data to the node
            String response = in.readLine(); // Receive computed sum from node

            return response;
        } catch (IOException e) {
            System.out.println(" Error communicating with Node.");
            e.printStackTrace();
            return "0"; // Return default value if communication fails
        }
    }


    private static double computeDistributedSum(List<Integer> numbers, int delay) {
        int nodeCount = Math.min(numbers.size(), MIN_NODES);
        List<List<Integer>> partitions = splitList(numbers, nodeCount);

        ExecutorService executor = Executors.newFixedThreadPool(nodeCount);
        List<Future<Double>> futures = new ArrayList<>();

        for (int i = 0; i < partitions.size(); i++) {
            final int nodeIndex = i + 1; // Ensure final variable for lambda

            List<Integer> partition = partitions.get(i);
            String dataToSend = partition.toString().replaceAll("[\\[\\] ]", "") + ";" + delay;

            System.out.println("📡 Sending to Node " + nodeIndex + ": " + dataToSend);

            futures.add(executor.submit(() -> {
                String response = sendToNode(connectedNodes.get(nodeIndex - 1), dataToSend);
                System.out.println("📩 Received from Node " + nodeIndex + ": " + response);
                return Double.parseDouble(response);
            }));
        }

        double sum = 0;
        for (Future<Double> future : futures) {
            try {
                sum += future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();
        return sum;
    }




    private static List<List<Integer>> splitList(List<Integer> numbers, int parts) {
        List<List<Integer>> result = new ArrayList<>();
        int size = numbers.size() / parts;

        for (int i = 0; i < parts; i++) {
            int start = i * size;
            int end = (i == parts - 1) ? numbers.size() : (i + 1) * size;
            result.add(numbers.subList(start, end));
        }
        return result;
    }
}

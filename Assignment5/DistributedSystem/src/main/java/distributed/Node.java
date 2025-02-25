package distributed;

import java.io.*;
import java.net.*;
import java.util.Random;

public class Node {
    private static final int LEADER_PORT = 8888;
    private static final String LEADER_IP = "localhost";
    private static final boolean IS_FAULTY = "1".equals(System.getProperty("Fault", "0")); // Check for -pFault=1

    public static void main(String[] args) {
        try (Socket socket = new Socket(LEADER_IP, LEADER_PORT)) {
            System.out.println(" Connected to Leader at " + LEADER_IP + ":" + LEADER_PORT);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Send identification message to Leader
            out.println("NODE");

            while (true) {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String request = in.readLine();
                if (request == null) break;

                // ✅ **Debug: Print the numbers received by each node**
                System.out.println("📩 Received from Leader: " + request);

                String[] parts = request.split(";");

                if (request.equals("VERIFY")) {
                    out.println(runVerification());
                } else {
                    int sum = computeSum(parts[0], Integer.parseInt(parts[1]));
                    // Introduce Fault if enabled
                    if (IS_FAULTY) {
                        sum = introduceFault(sum);
                        System.out.println("️ Faulty Node Active! Sending incorrect sum: " + sum);
                    } else {
                        System.out.println(" Correct sum computed: " + sum);
                    }

                    // ✅ **Debug: Print the computed sum before sending**
                    System.out.println("📤 Sending computed sum: " + sum);
                    out.println(sum);
                }
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int computeSum(String numbers, int delay) {
        int sum = 0;
        try {
            for (String num : numbers.split(",")) {
                sum += Integer.parseInt(num.trim());
                Thread.sleep(delay);
            }
        } catch (InterruptedException e) {
            System.out.println(" Error computing sum");
            //Thread.currentThread().interrupt();
        }
        return sum;
    }

    private static int introduceFault(int correctSum) {
        Random random = new Random();
        int faultType = random.nextInt(3);
        switch (faultType) {
            case 0: return correctSum + random.nextInt(50) + 1;
            case 1: return correctSum - random.nextInt(50) - 1;
            case 2: return correctSum * (random.nextInt(3) + 1);
            default: return correctSum;
        }
    }


    private static String runVerification() {
        return IS_FAULTY ? "NO" : "YES"; // If node is faulty, it disagrees
    }

}

package distributed;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    private static final String LEADER_IP = "localhost";
    private static final int LEADER_PORT = 8888;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("📡 Client Started.");

            // Validate number input
            List<Integer> numbers = new ArrayList<>();
            while (numbers.isEmpty()) {
                System.out.print("🔢 Enter numbers separated by space: ");
                String inputLine = scanner.nextLine().trim();

                if (inputLine.isEmpty()) {
                    System.out.println("❌ Error: Input cannot be empty.");
                    continue;
                }

                String[] tokens = inputLine.split("\\s+");
                boolean validInput = true;
                for (String token : tokens) {
                    try {
                        numbers.add(Integer.parseInt(token));
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Error: Invalid number detected: " + token);
                        validInput = false;
                        break;
                    }
                }

                if (!validInput) {
                    numbers.clear(); // Clear invalid input and prompt again
                }
            }

            // Validate delay input
            int delay = -1;
            while (delay < 0) {
                System.out.print("Enter delay (in ms): ");
                if (scanner.hasNextInt()) {
                    delay = scanner.nextInt();
                    if (delay < 0) {
                        System.out.println("❌ Error: Delay must be a non-negative number.");
                    }
                } else {
                    System.out.println("❌ Error: Invalid delay input. Please enter an integer.");
                    scanner.next(); // Consume invalid input
                }
            }
            scanner.nextLine(); // Consume newline

            // Send request to Leader
            String request = "CLIENT " + numbers.toString().replaceAll("[\\[\\]]", "") + ";" + delay;

            Socket socket = new Socket(LEADER_IP, LEADER_PORT);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println(request);
            String response = in.readLine();
            //  Format response output
            System.out.println("\n📜 **Final Results from Leader:**");
            if (response.contains("Sequential Sum") && response.contains("Distributed Sum")) {
                formatLeaderResponse(response);
            } else {
                System.out.println("❌ Error: Unexpected response from Leader!");
            }

            in.close();
            out.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void formatLeaderResponse(String response) {
        String[] parts = response.split(",");

        for (String part : parts) {
            part = part.trim();
            if (part.contains("Sequential Sum")) {
                System.out.println("📌 " + part);
            } else if (part.contains("Distributed Sum")) {
                System.out.println("⚡ " + part);
            } else if (part.contains("Consensus: Passed")) {
                System.out.println("✅ Consensus: Passed! All nodes agree.");
            } else if (part.contains("Consensus failed")) {
                System.out.println("❌ Consensus failed! Some nodes disagreed.");
            } else if (part.contains("Node Verifications:")) {
                System.out.println("📜 Node Verifications: " + part.replace("Node Verifications:", "").trim());
            } else {
                System.out.println("📜 " + part);
            }
        }
    }
}

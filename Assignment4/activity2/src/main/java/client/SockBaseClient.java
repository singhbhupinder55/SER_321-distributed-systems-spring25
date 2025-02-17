package client;

import buffers.RequestProtos.*;
import buffers.ResponseProtos.*;

import java.io.*;
import java.net.Socket;

class SockBaseClient {
    public static void main (String[] args) throws Exception {
        Socket serverSock = null;
        OutputStream out = null;
        InputStream in = null;
        int i1=0, i2=0;
        int port = 8000; // default port

        // Make sure two arguments are given
        if (args.length != 2) {
            System.out.println("Expected arguments: <host(String)> <port(int)>");
            System.exit(1);
        }
        String host = args[0];
        try {
            port = Integer.parseInt(args[1]);
        } catch (NumberFormatException nfe) {
            System.out.println("[Port] must be integer");
            System.exit(2);
        }

        // Build the first request object just including the name
        Request op = nameRequest().build();
        Response response;
        try {
            // connect to the server
            serverSock = new Socket(host, port);

            // write to the server
            out = serverSock.getOutputStream();
            in = serverSock.getInputStream();

            op.writeDelimitedTo(out);

            while (true) {
                // read from the server
                response = Response.parseDelimitedFrom(in);
              //  System.out.println("Got a response: " + response.toString());

                Request.Builder req = Request.newBuilder();

                switch (response.getResponseType()) {
                    case GREETING:
                        System.out.println("\n========================================================");
                        System.out.println(response.getMessage());
                        System.out.println("======================================================");
                        req = chooseMenu(req, response);
                        break;

                    case LEADERBOARD:
                        System.out.println("\n================== 🏆 Leaderboard 🏆 ==================");
                        System.out.println(response.getMessage());
                        System.out.println("======================================================\n");
                        req = chooseMenu(req, response); // Ensures menu is shown again
                        break;

                    case START:
                        // ✅ Display the board received from the server
                        System.out.println("\n================== 🧩 Sudoku Board ==================");
                        System.out.println(response.getMessage());  // This contains the board
                        System.out.println("====================================================");

                        System.out.println(response.getMenuoptions());

                        int[] move = getValidatedMove();  // Get user input
                        req.setOperationType(Request.OperationType.UPDATE);
                        req.setRow(move[0]);
                        req.setColumn(move[1]);
                        req.setValue(move[2]);
                        break;

                    case PLAY:
                        System.out.println("\n================== 🧩 Updated Sudoku Board ==================");
                        System.out.println(response.getMessage());  // Display updated board
                        System.out.println("====================================================");
                        System.out.println(response.getMenuoptions()); // Show in-game menu

                        System.out.println(response.getMenuoptions());

                        // Ask for the next move
                        int[] nextMove = getValidatedMove();
                        req.setOperationType(Request.OperationType.UPDATE);
                        req.setRow(nextMove[0]);
                        req.setColumn(nextMove[1]);
                        req.setValue(nextMove[2]);
                        break;



                    case ERROR:
                        System.out.println("Error: " + response.getMessage() + "Type: " + response.getErrorType());

                        switch (response.getNext()) {
                            case 1:
                                req = nameRequest(); // Go back to name request
                                break;
                            case 2:
                                req = chooseMenu(Request.newBuilder(), response); // Go back to main menu
                                break;
                            case 3:
                                // If in-game, prompt user for another move
                                int[] retryMove = getValidatedMove();
                                req.setOperationType(Request.OperationType.UPDATE);
                                req.setRow(retryMove[0]);
                                req.setColumn(retryMove[1]);
                                req.setValue(retryMove[2]);
                                break;
                            case 4:
                                System.out.print("\nPlease enter a valid difficulty (1 - 20): ");
                                BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
                                String difficultyStr = stdin.readLine();
                                req.setOperationType(Request.OperationType.START);
                                req.setDifficulty(Integer.parseInt(difficultyStr));
                                break;
                            default:
                                System.out.println("Unexpected error state, returning to main menu.");
                                req = chooseMenu(Request.newBuilder(), response);
                                break;
                        }
                        break;
                }
                req.build().writeDelimitedTo(out);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            exitAndClose(in, out, serverSock);
        }
    }

    /**
     * handles building a simple name requests, asks the user for their name and builds the request
     * @return Request.Builder which holds all teh information for the NAME request
     */
    static Request.Builder nameRequest() throws IOException {
        System.out.println("Please provide your name for the server.");
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        String strToSend = stdin.readLine();

        return Request.newBuilder()
                .setOperationType(Request.OperationType.NAME)
                .setName(strToSend);
    }

    /**
     * Shows the main menu and lets the user choose a number, it builds the request for the next server call
     * @return Request.Builder which holds the information the server needs for a specific request
     */
    static Request.Builder chooseMenu(Request.Builder req, Response response) throws IOException {
        while (true) {
            System.out.println("\n==================================================");
            System.out.println(response.getMenuoptions());
            System.out.println("==================================================");
            System.out.print("Enter a number 1-3: ");
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
            String menu_select = stdin.readLine();
            System.out.println(menu_select);
            switch (menu_select) {
                case "1":
                req.setOperationType(Request.OperationType.LEADERBOARD);
                return req;
                // needs to include the other requests
                case "2":
                    System.out.print("\nEnter difficulty level (1 - 20): ");
                    String difficultyStr = stdin.readLine();  // Get difficulty input from use
                    req.setOperationType(Request.OperationType.START);
                    req.setDifficulty(Integer.parseInt(difficultyStr)); // Send difficulty to server
                    return req;
                case "3":
                    req.setOperationType(Request.OperationType.QUIT);
                    return req;

                default:
                    System.out.println("\nInvalid choice! Please enter a valid option (1, 2, or 3).");
                    break;
            }
        }
    }

    /**
     * Exits the connection
     */
    static void exitAndClose(InputStream in, OutputStream out, Socket serverSock) throws IOException {
        if (in != null)   in.close();
        if (out != null)  out.close();
        if (serverSock != null) serverSock.close();
        System.exit(0);
    }

    /**
     * Handles the clear menu logic when the user chooses that in Game menu. It retuns the values exactly
     * as needed in the CLEAR request row int[0], column int[1], value int[3]
     */
    static int[] boardSelectionClear() throws Exception {
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Choose what kind of clear by entering an integer (1 - 5)");
        System.out.print(" 1 - Clear value \n 2 - Clear row \n 3 - Clear column \n 4 - Clear Grid \n 5 - Clear Board \n");

        String selection = stdin.readLine();

        while (true) {
            if (selection.equalsIgnoreCase("exit")) {
                return new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
            }
            try {
                int temp = Integer.parseInt(selection);

                if (temp < 1 || temp > 5) {
                    throw new NumberFormatException();
                }

                break;
            } catch (NumberFormatException nfe) {
                System.out.println("That's not an integer!");
                System.out.println("Choose what kind of clear by entering an integer (1 - 5)");
                System.out.print("1 - Clear value \n 2 - Clear row \n 3 - Clear column \n 4 - Clear Grid \n 5 - Clear Board \n");
            }
            selection = stdin.readLine();
        }

        int[] coordinates = new int[3];

        switch (selection) {
            case "1":
                // clear value, so array will have {row, col, 1}
                coordinates = boardSelectionClearValue();
                break;
            case "2":
                // clear row, so array will have {row, -1, 2}
                coordinates = boardSelectionClearRow();
                break;
            case "3":
                // clear col, so array will have {-1, col, 3}
                coordinates = boardSelectionClearCol();
                break;
            case "4":
                // clear grid, so array will have {gridNum, -1, 4}
                coordinates = boardSelectionClearGrid();
                break;
            case "5":
                // clear entire board, so array will have {-1, -1, 5}
                coordinates[0] = -1;
                coordinates[1] = -1;
                coordinates[2] = 5;
                break;
            default:
                break;
        }

        return coordinates;
    }

    static int[] boardSelectionClearValue() throws Exception {
        int[] coordinates = new int[3];

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Choose coordinates of the value you want to clear");
        System.out.print("Enter the row as an integer (1 - 9): ");
        String row = stdin.readLine();

        while (true) {
            if (row.equalsIgnoreCase("exit")) {
                return new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
            }
            try {
                Integer.parseInt(row);
                break;
            } catch (NumberFormatException nfe) {
                System.out.println("That's not an integer!");
                System.out.print("Enter the row as an integer (1 - 9): ");
            }
            row = stdin.readLine();
        }

        coordinates[0] = Integer.parseInt(row);

        System.out.print("Enter the column as an integer (1 - 9): ");
        String col = stdin.readLine();

        while (true) {
            if (col.equalsIgnoreCase("exit")) {
                return new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
            }
            try {
                Integer.parseInt(col);
                break;
            } catch (NumberFormatException nfe) {
                System.out.println("That's not an integer!");
                System.out.print("Enter the column as an integer (1 - 9): ");
            }
            col = stdin.readLine();
        }

        coordinates[1] = Integer.parseInt(col);
        coordinates[2] = 1;

        return coordinates;
    }

    static int[] boardSelectionClearRow() throws Exception {
        int[] coordinates = new int[3];

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Choose the row you want to clear");
        System.out.print("Enter the row as an integer (1 - 9): ");
        String row = stdin.readLine();

        while (true) {
            if (row.equalsIgnoreCase("exit")) {
                return new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
            }
            try {
                Integer.parseInt(row);
                break;
            } catch (NumberFormatException nfe) {
                System.out.println("That's not an integer!");
                System.out.print("Enter the row as an integer (1 - 9): ");
            }
            row = stdin.readLine();
        }

        coordinates[0] = Integer.parseInt(row);
        coordinates[1] = -1;
        coordinates[2] = 2;

        return coordinates;
    }

    static int[] boardSelectionClearCol() throws Exception {
        int[] coordinates = new int[3];

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Choose the column you want to clear");
        System.out.print("Enter the column as an integer (1 - 9): ");
        String col = stdin.readLine();

        while (true) {
            if (col.equalsIgnoreCase("exit")) {
                return new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
            }
            try {
                Integer.parseInt(col);
                break;
            } catch (NumberFormatException nfe) {
                System.out.println("That's not an integer!");
                System.out.print("Enter the column as an integer (1 - 9): ");
            }
            col = stdin.readLine();
        }

        coordinates[0] = -1;
        coordinates[1] = Integer.parseInt(col);
        coordinates[2] = 3;
        return coordinates;
    }

    static int[] boardSelectionClearGrid() throws Exception {
        int[] coordinates = new int[3];

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Choose area of the grid you want to clear");
        System.out.println(" 1 2 3 \n 4 5 6 \n 7 8 9 \n");
        System.out.print("Enter the grid as an integer (1 - 9): ");
        String grid = stdin.readLine();

        while (true) {
            if (grid.equalsIgnoreCase("exit")) {
                return new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
            }
            try {
                Integer.parseInt(grid);
                break;
            } catch (NumberFormatException nfe) {
                System.out.println("That's not an integer!");
                System.out.print("Enter the grid as an integer (1 - 9): ");
            }
            grid = stdin.readLine();
        }

        coordinates[0] = Integer.parseInt(grid);
        coordinates[1] = -1;
        coordinates[2] = 4;

        return coordinates;
    }

    /**
     * Handles requesting user input for move in the Sudoku game with clear instructions and validation.
     */
    static int[] getValidatedMove() throws IOException {
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        String input;
        System.out.println("\n   Enter your move in the format: row column value");
        System.out.println("   Example: 3 4 7 (to place number 7 at row 3, column 4)");
        System.out.println("   Row and Column should be between 1 and 9, Value should be between 1 and 9.");
        System.out.println("==================================================\n");

        while (true) {
            System.out.print("Enter row, column, and value: \n");
            input = stdin.readLine();
            if (isValidInput(input)) {
                break;
            }
            System.out.println("\n Invalid input! Please enter three numbers separated by spaces (e.g., 3 4 7).");
        }

        String[] parts = input.split(" ");
        return new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2])};
    }

    /**
     * Validates the user input for move format correctness.
     */
    static boolean isValidInput(String input) {
        String[] parts = input.trim().split("\\s+");
        if (parts.length != 3) {
            System.out.println("❌ **Invalid format!** Please enter three numbers separated by spaces (e.g., 3 4 7).");
            return false;
        }

        try {
            Integer.parseInt(parts[0]);
            Integer.parseInt(parts[1]);
            Integer.parseInt(parts[2]);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("❌ **Invalid input!** All inputs must be Integers.");
            return false;
        }
    }

}

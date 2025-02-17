package server;

import buffers.RequestProtos.*;
import buffers.ResponseProtos.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import common.Player;

class SockBaseServer {
    static String logFilename = "logs.txt";

    private static final Object logLock = new Object();
    private static final ConcurrentHashMap<String, Player> players = new ConcurrentHashMap<>();


    // Please use these as given so it works with our test cases
    static String menuOptions = "\nWhat would you like to do? \n 1 - to see the leader board \n 2 - to enter a game \n 3 - quit the game";
    static String gameOptions = "\nChoose an action: \n (1-9) - Enter an int to specify the row you want to update \n c - Clear number \n r - New Board";


    ServerSocket serv = null;
    InputStream in = null;
    OutputStream out = null;
    Socket clientSocket = null;
    private final int id; // client id

    Game game; // current game

    private boolean inGame = false; // a game was started (you can decide if you want this
    private String name; // player name

    private int currentState =1; // I used something like this to keep track of where I am in the game, you can decide if you want that as well

    private static boolean grading = true; // if the grading board should be used

    public SockBaseServer(Socket sock, Game game, int id) {
        this.clientSocket = sock;
        this.game = game;
        this.id = id;
        try {
            in = clientSocket.getInputStream();
            out = clientSocket.getOutputStream();
        } catch (Exception e){
            System.out.println("Error in constructor: " + e);
        }
    }

    // Method to handle player login
    private void handleLogin(String playerName) {

        players.compute(playerName, (name, player) -> {
            if (player == null) {
                // New player, initialize with 1 login
                player = new Player(name, 0);
                player.setLoginCount(1);
            } else {
                player.incrementLoginCount(); // Increase login count
            }
            writeToLog(playerName, Message.CONNECT);
            return player;
        });

    }

    // Method to update player's score
    private void updatePlayerScore(String playerName, int newScore) {

        players.compute(playerName, (name, player) -> {
            if (player == null) {
                player = new Player(name, newScore); // First-time player
            } else {
                player.updateWins(newScore); // Overwrite only if new score is higher
            }
            writeToLog(playerName, Message.WIN);
            return player;
        });

    }



    /**
     * Received a request, starts to evaluate what it is and handles it, not complete
     */
    public void startGame() throws IOException {
        try {
            while (true) {
                // read the proto object and put into new object
                Request op = Request.parseDelimitedFrom(in);
                System.out.println("Got request: " + op.toString());
                Response response;

                boolean quit = false;

                // should handle all the other request types here, my advice is to put them in methods similar to nameRequest()
                switch (op.getOperationType()) {
                    case NAME:
                        if (op.getName().isBlank()) {
                            response = error(1, "name");
                        } else {
                            response = nameRequest(op);
                        }
                        break;
                    case START:
                        response = startGame(op); // not complete!
                        break;
                    case LEADERBOARD:  // Handle leaderboard option
                        response =   leaderboardRequest();
                        break;
                    case QUIT:  // Handle quit option
                        response = quit();
                        quit = true;
                        break;

                    case UPDATE:
                        response = processMove(op);
                        break;

                    default:
                        response = error(2, op.getOperationType().name());
                        break;
                }
                response.writeDelimitedTo(out);

                if (quit) {
                    return;
                }
            }
        } catch (SocketException se) {
            System.out.println("Client disconnected");
        } catch (Exception ex) {
            Response error = error(0, "Unexpected server error: " + ex.getMessage());
            error.writeDelimitedTo(out);
        }
        finally {
            System.out.println("Client ID " + id + " disconnected");
            this.inGame = false;
            exitAndClose(in, out, clientSocket);
        }
    }

    void exitAndClose(InputStream in, OutputStream out, Socket serverSock) throws IOException {
        if (in != null)   in.close();
        if (out != null)  out.close();
        if (serverSock != null) serverSock.close();
    }

    private Response leaderboardRequest() {
        Response.Builder response = Response.newBuilder()
                .setResponseType(Response.ResponseType.LEADERBOARD)
                .setMenuoptions(menuOptions)  // Show main menu again after displaying leaderboard
                .setNext(2);

        try {
            Logs.Builder logs = readLogFile(); // Handle exception inside try-catch

            if (logs.getLogList().isEmpty()) {
                response.setMessage("\n🏆 Leaderboard is empty! No players have scores yet.");
            } else {
                StringBuilder leaderboard = new StringBuilder("\n🏆 Leaderboard 🏆\n");


                for (String logEntry : logs.getLogList()) {  // Correct handling of Protobuf strings
                   leaderboard.append("• ").append(logEntry).append("\n");
                }




                response.setMessage(leaderboard.toString());
            }

        } catch (Exception e) {
            System.out.println("⚠️ Error reading leaderboard file: " + e.getMessage());
            response.setMessage("\n⚠️ Error retrieving leaderboard. Please try again.");
        }

        return response.build();
    }



    /**
     * Handles the name request and returns the appropriate response
     * @return Request.Builder holding the reponse back to Client as specified in Protocol
     */
    private Response nameRequest(Request op) throws IOException {
        name = op.getName().toUpperCase();

        writeToLog(name, Message.CONNECT);
        currentState = 2;

        System.out.println("Got a connection and a name: " + name);
        return Response.newBuilder()
                .setResponseType(Response.ResponseType.GREETING)
                .setMessage("Hello " + name + " and welcome to a simple game of Sudoku.")
                .setMenuoptions(menuOptions)
                .setNext(currentState)
                .build();
    }

    /**
     * Starts to handle start of a game after START request, is not complete of course, just shows how to get to the board
     */
    private Response startGame(Request op) throws IOException {

        if (!op.hasDifficulty()) {
            return error(1, "Difficulty level is missing.");
        }

        int difficulty = op.getDifficulty(); // Read difficulty from client request


        // Loop until a valid difficulty is received
        if (difficulty < 1 || difficulty > 20) {
            System.out.println("Invalid difficulty received: " + difficulty + ". Asking client for valid input.");
            // Send an error response asking for a valid difficulty
                    return Response.newBuilder()
                    .setResponseType(Response.ResponseType.ERROR)
                    .setErrorType(5)
                    .setMessage("\n⚠️ Error: Invalid difficulty! Please enter a number between 1-20.")
                    .setNext(4)  // Stay in the same menu
                    .build();

        }
        System.out.println("Starting game with difficulty: " + difficulty);
        this.inGame = false;
        this.game = new Game(); // Reinitialize game object
        game.newGame(grading, difficulty); // difficulty should be read from request!

        System.out.println(game.getDisplayBoard());

        return Response.newBuilder()
                .setResponseType(Response.ResponseType.START)
                .setMessage(game.getDisplayBoard())
                .setMenuoptions(gameOptions) //  Sends in-game menu
                .setNext(3)
                .build();
    }

    /**
     * Handles the quit request, might need adaptation
     * @return Request.Builder holding the reponse back to Client as specified in Protocol
     */
    private  Response quit() //throws IOException
    {
        System.out.println("Client " + name + " has chosen to quit the game.");
        this.inGame = false;
        return Response.newBuilder()
                .setResponseType(Response.ResponseType.BYE)
                .setMessage("Thank you for playing! goodbye.")
                .build();
    }

    /**
     * Start of handling errors, not fully done
     * @return Request.Builder holding the reponse back to Client as specified in Protocol
     */
    private Response error(int err, String field)    //throws IOException
    {
        String message = "";
        int type = err;
        Response.Builder response = Response.newBuilder();
       int nextStep = currentState;


        switch (err) {
            case 1:
                message = "\n⚠️ Error: A required field is missing or empty.";
                break;
            case 2:
                message = "\n⚠️ Error: Request not supported.";
                break;
            case 3:
                message = "\n⚠️ Error: Row or column out of bounds. Please enter values between 1-9.";
                nextStep = 3;
                break;
            case 4:
                message = "\n⚠️ Error: This request is not expected at this point.";
                break;
            case 5:
                message = "\n⚠️ Error: Difficulty is out of range. Please enter a value between 1-20.";
                break;
            case 6:
                message = "\n⚠️ Error: You cannot modify a pre-filled number!";
                break;
            case 7:
                message = "\n⚠️ Error: Invalid move! The number already exists in row, column, or grid.";
                break;
            default:
                message = "\n⚠️ Error: Unexpected issue occurred. Please try again.";
                type = 0;
                break;
        }

        response
                .setResponseType(Response.ResponseType.ERROR)
                .setErrorType(type)
                .setMessage(message)
                .setNext(currentState)
                .build();

        return response.build();
    }

    private Response processMove(Request op) {

        System.out.println("Processing move: Row=" + op.getRow() + ", Column=" + op.getColumn() + ", Value=" + op.getValue());

        // ✅ If the client is trying to quit, handle it separately
        if (op.getRow() == -1 && op.getColumn() == -1 && op.getValue() == -1) {
            return quit();
        }

        // Check if fields are missing
        if (!op.hasRow() || !op.hasColumn() || !op.hasValue()) {
            return error(1, "A required field is missing or empty.");
        }

        int row = op.getRow() - 1;  // Convert to 0-based index
        int col = op.getColumn() - 1;
        int value = op.getValue();

        // ✅ Step 1: Validate if the move is inside the board boundaries
        if (row < 0 || row >= 9 || col < 0 || col >= 9 || value < 1 || value > 9) {
            return Response.newBuilder()
                    .setResponseType(Response.ResponseType.ERROR)
                    .setErrorType(3)
                    .setMessage("\n⚠️ Error: Row or column out of bounds. Please enter values between 1-9.")
                    .setNext(3)  // ✅ Stay in the game
                    .build();
        }

        //  Step 2: Update board using `updateBoard()`
        int result = game.updateBoard(row, col, value, 0); // 0 means add number

        //  Step 3: Handle errors returned from `updateBoard()`
        switch (result) {
            case 1:
                return Response.newBuilder()
                        .setResponseType(Response.ResponseType.ERROR)
                        .setErrorType(6)
                        .setMessage("\n⚠️ Error: You cannot modify a pre-filled number!")
                        .setNext(3)  //  Stay in the game
                        .build();
            case 2:
                return Response.newBuilder()
                        .setResponseType(Response.ResponseType.ERROR)
                        .setErrorType(7)
                        .setMessage("\n⚠️ Error: The number already exists in the row!")
                        .setNext(3)  //  Stay in the game
                        .build();
            case 3:
                return Response.newBuilder()
                        .setResponseType(Response.ResponseType.ERROR)
                        .setErrorType(7)
                        .setMessage("\n⚠️ Error: The number already exists in the column!")
                        .setNext(3)  //  Stay in the game
                        .build();
            case 4:
                return Response.newBuilder()
                        .setResponseType(Response.ResponseType.ERROR)
                        .setErrorType(7)
                        .setMessage("\n⚠️ Error: The number already exists in the 3x3 grid!")
                        .setNext(3)  //  Stay in the game
                        .build();
        }

        //  Step 4: Check if the player has won
        if (game.checkWon()) {
            return Response.newBuilder()
                    .setResponseType(Response.ResponseType.WON)
                    .setMessage("🎉 Congratulations! You completed the Sudoku puzzle! 🎉")
                    .setMenuoptions(menuOptions) // Back to the main menu
                    .setNext(2)
                    .build();
        }

        //  Step 5: Send updated board
        return Response.newBuilder()
                .setResponseType(Response.ResponseType.PLAY)
                .setMessage(game.getDisplayBoard())
                .setMenuoptions(gameOptions) // In-game menu
                .setNext(3)  // ✅ Stay in the game
                .build();
    }




    /**
     * Writing a new entry to our log
     * @param name - Name of the person logging in
     * @param message - type Message from Protobuf which is the message to be written in the log (e.g. Connect)
     * @return String of the new hidden image
     */
    public synchronized void writeToLog(String name, Message message) {
        try {
            // read old log file
            Logs.Builder logs = readLogFile();


            Date date = java.util.Calendar.getInstance().getTime();
            // Ensure the player exists
            Player player = players.computeIfAbsent(name, k -> new Player(name, 0));

            if (message == Message.CONNECT) {
               player.incrementLoginCount();
            }


            // Remove any previous log entry of this player
            logs.getLogList().removeIf(logEntry -> logEntry.contains(" - " + name + " - "));

            // we are writing a new log entry to our log
            // add a new log entry to the log list of the Protobuf object


            // Create log format: "DATE - NAME - WINS: X - LOGINS: Y - ACTION"
            String logEntry = String.format("%s - %s - Wins: %d - Logins: %d - %s",
                    date, name, player.getWins(), player.getLoginCount(), message);

            // Add new entry
            logs.addLog(logEntry);


            // open log file
            FileOutputStream output = new FileOutputStream(logFilename);
            Logs logsObj = logs.build();

            // write to log file
            logsObj.writeTo(output);

        } catch(Exception e) {
            System.out.println("Issue while trying to save");
        }
    }


    public synchronized Logs.Builder readLogFile() throws Exception {
        synchronized (logLock) {
            Logs.Builder logs = Logs.newBuilder();

            try {
                // just read the file and put what is in it into the logs object
                return logs.mergeFrom(new FileInputStream(logFilename));
            } catch (FileNotFoundException e) {
                System.out.println(logFilename + ": File not found.  Creating a new file.");
                return logs;
            }
        }
    }






    public static void main (String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("Expected arguments: <port(int)> <delay(int)>");
            System.exit(1);
        }
        int port = 8000; // default port
        grading = Boolean.parseBoolean(args[1]);
        Socket clientSocket = null;
        ServerSocket socket = null;

        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException nfe) {
            System.out.println("[Port|sleepDelay] must be an integer");
            System.exit(2);
        }
        try {
            socket = new ServerSocket(port);
            System.out.println("Server started..");
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(2);
        }
        int id = 1;
        while (true) {
            try {
                clientSocket = socket.accept();
                System.out.println("Attempting to connect to client-" + id);
                Game game = new Game();
                final int clientId = id; // Create final copy of id
                id++; // Increment the original id

                SockBaseServer server = new SockBaseServer(clientSocket, game, clientId);


                // Run each client in a separate thread
                new Thread(() -> {
                    try {
                        server.startGame();
                    } catch (IOException e) {
                        System.out.println("Error handling client " + clientId);
                    }
                }).start();
               // server.startGame();
            } catch (Exception e) {
                System.out.println("Error in accepting client connection.");
            }
        }
    }
}

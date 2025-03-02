package example.grpcclient;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import service.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.google.protobuf.Empty; // needed to use Empty


/**
 * Client that requests `parrot` method from the `EchoServer`.
 */
public class Client {
  private final EchoGrpc.EchoBlockingStub blockingStub;
  private final JokeGrpc.JokeBlockingStub blockingStub2;
  private final RegistryGrpc.RegistryBlockingStub blockingStub3;
  private final RegistryGrpc.RegistryBlockingStub blockingStub4;
  private final WeightTrackerGrpc.WeightTrackerBlockingStub weightStub;
  private final FollowGrpc.FollowBlockingStub followStub;

  /** Construct client for accessing server using the existing channel. */
  public Client(Channel channel, Channel regChannel) {
    // 'channel' here is a Channel, not a ManagedChannel, so it is not this code's
    // responsibility to
    // shut it down.

    // Passing Channels to code makes code easier to test and makes it easier to
    // reuse Channels.
    blockingStub = EchoGrpc.newBlockingStub(channel);
    blockingStub2 = JokeGrpc.newBlockingStub(channel);
    blockingStub3 = RegistryGrpc.newBlockingStub(regChannel);
    blockingStub4 = RegistryGrpc.newBlockingStub(channel);
    weightStub = WeightTrackerGrpc.newBlockingStub(channel);  // newly added
    followStub = FollowGrpc.newBlockingStub(channel);
  }

  /** Construct client for accessing server using the existing channel. */
  public Client(Channel channel) {
    // 'channel' here is a Channel, not a ManagedChannel, so it is not this code's
    // responsibility to
    // shut it down.

    // Passing Channels to code makes code easier to test and makes it easier to
    // reuse Channels.
    blockingStub = EchoGrpc.newBlockingStub(channel);
    blockingStub2 = JokeGrpc.newBlockingStub(channel);
    blockingStub3 = null;
    blockingStub4 = null;
    weightStub = WeightTrackerGrpc.newBlockingStub(channel);   // newly added
    followStub = FollowGrpc.newBlockingStub(channel);
  }

  public void askServerToParrot(String message) {

    ClientRequest request = ClientRequest.newBuilder().setMessage(message).build();
    ServerResponse response;
    try {
      response = blockingStub.parrot(request);
    } catch (Exception e) {
      System.err.println("RPC failed: " + e.getMessage());
      return;
    }
    System.out.println("Received from server: " + response.getMessage());
  }

  public void askForJokes(int num) {
    JokeReq request = JokeReq.newBuilder().setNumber(num).build();
    JokeRes response;

    // just to show how to use the empty in the protobuf protocol
    Empty empt = Empty.newBuilder().build();

    try {
      response = blockingStub2.getJoke(request);
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }
    System.out.println("Your jokes: ");
    for (String joke : response.getJokeList()) {
      System.out.println("--- " + joke);
    }
  }

  public void setJoke(String joke) {
    JokeSetReq request = JokeSetReq.newBuilder().setJoke(joke).build();
    JokeSetRes response;

    try {
      response = blockingStub2.setJoke(request);
      System.out.println(response.getOk());
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }
  }

  public void getNodeServices() {
    GetServicesReq request = GetServicesReq.newBuilder().build();
    ServicesListRes response;
    try {
      response = blockingStub4.getServices(request);
      System.out.println(response.toString());
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }
  }

  public void getServices() {
    GetServicesReq request = GetServicesReq.newBuilder().build();
    ServicesListRes response;
    try {
      response = blockingStub3.getServices(request);
      System.out.println(response.toString());
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }
  }

  public void findServer(String name) {
    FindServerReq request = FindServerReq.newBuilder().setServiceName(name).build();
    SingleServerRes response;
    try {
      response = blockingStub3.findServer(request);
      System.out.println(response.toString());
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }
  }

  public void findServers(String name) {
    FindServersReq request = FindServersReq.newBuilder().setServiceName(name).build();
    ServerListRes response;
    try {
      response = blockingStub3.findServers(request);
      System.out.println(response.toString());
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }
  }



  /** Helper method to get a valid integer choice */
  private int getUserChoice(Scanner scanner, int min, int max) {
    int choice;
    while (true) {
     // System.out.print("Select an option: ");
      if (scanner.hasNextInt()) {
        choice = scanner.nextInt();
        if (choice >= min && choice <= max) break;
        else System.out.println("Invalid option! Please enter a number between " + min + " and " + max + ".");
      } else {
        System.out.println("Invalid input! Please enter a valid number.");
        scanner.next();
      }
    }
    return choice;
  }


  public void interactWithWeightTracker() {
    Scanner scanner = new Scanner(System.in);

    while (true) {
      System.out.println("\nWeight Tracker Options:");
      System.out.println("1. Add Weight");
      System.out.println("2. Get Weight History");
      System.out.println("3. Calculate BMI");
      System.out.println("4. Exit to Main Menu");
      System.out.print("Select an option: ");

      int choice = getUserChoice(scanner, 1, 4);

      if (choice == 1) {
        System.out.print("Enter your name: ");
        String name = scanner.next();
        double weight;

        // Ensure valid weight input (no negative values, no letters)
        while (true) {
          System.out.print("Enter weight (kg): ");
          if (scanner.hasNextDouble()) {
            weight = scanner.nextDouble();
            if (weight > 0) break; // Valid weight entered
            System.out.println("Weight cannot be negative! Please enter again.");
          } else {
            System.out.println("Invalid input! Please enter a numeric weight.");
            scanner.next(); // Clear invalid input
          }
        }

        Weight weightEntry = Weight.newBuilder()
                .setName(name.toLowerCase()) // Convert name to lowercase for uniformity
                .setWeightNum(weight)
                .build();

        AddWeightRequest request = AddWeightRequest.newBuilder()
                .setWeight(weightEntry)
                .build();

        AddWeightResponse response = weightStub.addWeight(request);
        System.out.println("Success: " + response.getIsSuccess());
      }

      else if (choice == 2) {
        System.out.print("Enter your name: ");
        String name = scanner.next().toLowerCase(); // Convert name to lowercase

        GetWeightRequest request = GetWeightRequest.newBuilder()
                .setName(name)
                .build();

        GetWeightResponse response = weightStub.getWeight(request);
        System.out.println("Success: " + response.getIsSuccess());
        if (response.getIsSuccess()) {
          response.getWeightList().forEach(w -> System.out.println("Weight: " + w.getWeightNum()));
        } else {
          System.out.println("Error: " + response.getError());
        }
      }

      else if (choice == 3) {
        double weight, height;
        String unit;

        // Validate weight input
        while (true) {
          System.out.print("Enter weight (kg or lbs): ");
          if (scanner.hasNextDouble()) {
            weight = scanner.nextDouble();
            if (weight > 0) break; // Valid input
            System.out.println("Weight cannot be negative! Please enter again.");
          } else {
            System.out.println("Invalid input! Please enter a numeric weight.");
            scanner.next();
          }
        }

        // Validate height input
        while (true) {
          System.out.print("Enter height (meters or inches): ");
          if (scanner.hasNextDouble()) {
            height = scanner.nextDouble();
            if (height > 0) break; // Valid input
            System.out.println("Height cannot be negative! Please enter again.");
          } else {
            System.out.println("Invalid input! Please enter a numeric height.");
            scanner.next();
          }
        }

        // Validate unit input
        while (true) {
          System.out.print("Enter unit (metric/imperial): ");
          unit = scanner.next().toLowerCase();
          if (unit.equals("metric") || unit.equals("imperial")) break;
          System.out.println("Invalid input! Please enter 'metric' or 'imperial'.");
        }

        BMIRequest request = BMIRequest.newBuilder()
                .setWeight(weight)
                .setHeight(height)
                .setUnits(unit)
                .build();

        BMIResponse response = weightStub.getBMI(request);
        if (response.getIsSuccess()) {
          System.out.printf("BMI: %.2f\n",  response.getBMI());
        } else {
          System.out.println("Error: " + response.getError());
        }
      }

      else if (choice == 4) {
        System.out.println("Exiting Weight Tracker and returning to Main Menu...");
        return; // Exit loop
      }
      else {
        System.out.println("Invalid option! Please enter a number between 1 and 4.");
      }
    }

  }

  public void interactWithFollowService() {
    Scanner scanner = new Scanner(System.in);
    while (true) {
      System.out.println("\nFollow Service Options:");
      System.out.println("1. Add User");
      System.out.println("2. Follow a User");
      System.out.println("3. View Following List");
      System.out.println("4. Exit");

      int choice = getUserChoice(scanner, 1, 4);

      if (choice == 1) {
        System.out.print("Enter username: ");
        String username = scanner.next().toLowerCase();
        UserReq request = UserReq.newBuilder().setName(username).build();
        try {
        UserRes response = followStub.addUser(request);
        System.out.println(response.getIsSuccess() ? "User added successfully!" : "Error: " + response.getError());
        } catch (Exception e) {
          System.out.println("Error: Failed to communicate with Follow Service. Please try again.");
        }
      }

      else if (choice == 2) {
        System.out.print("Enter your username: ");
        String username = scanner.next().toLowerCase();
        System.out.print("Enter username to follow: ");
        String followName = scanner.next().toLowerCase();

        // Ensure user exists before following
        UserReq checkRequest = UserReq.newBuilder().setName(followName).build();
        UserRes checkResponse = followStub.viewFollowing(checkRequest);

        if (!checkResponse.getIsSuccess()) {
          System.out.println("Error: The user you are trying to follow does not exist.");
          continue;
        }

        // Retrieve current following list for the user
        UserReq userRequest = UserReq.newBuilder().setName(username).build();
        UserRes followingResponse = followStub.viewFollowing(userRequest);

        if (followingResponse.getIsSuccess() && followingResponse.getFollowedUserList().contains(followName)) {
          System.out.println("User is already following " + followName + ".");
          return;  // Skip the follow request
        }

        UserReq request = UserReq.newBuilder().setName(username).setFollowName(followName).build();
        UserRes response = followStub.follow(request);

        System.out.println(response.getIsSuccess() ? username + " is now following " + followName : "Error: " + response.getError());
      }

      else if (choice == 3) {
        System.out.print("Enter your username: ");
        String username = scanner.next().toLowerCase();
        UserReq request = UserReq.newBuilder().setName(username).build();
        UserRes response = followStub.viewFollowing(request);
        if (response.getIsSuccess()) {
          System.out.println("Following List:");
          response.getFollowedUserList().forEach(System.out::println);
        } else {
          System.out.println("Error: " + response.getError());
        }
      }

      else if (choice == 4) {
        System.out.println("Exiting Follow Service and returning to Main Menu...");
        break;
      }
    }
  }


  private void runAutoMode() {
    System.out.println("\n🔹 DEBUG: Auto Mode = true");
    System.out.println("🏁 Running Auto-Test Mode...\n");

    // ============================
    // 🚀 TESTING FOLLOW SERVICE 🚀
    // ============================
    System.out.println("[TEST] Adding users: 'Alice' and 'Bob'");
    followStub.addUser(UserReq.newBuilder().setName("alice").build());
    followStub.addUser(UserReq.newBuilder().setName("bob").build());

    // Alice follows Bob
    System.out.println("[TEST] Alice follows Bob");
    UserReq followRequest = UserReq.newBuilder().setName("alice").setFollowName("bob").build();
    UserRes followResponse = followStub.follow(followRequest);
    System.out.println(followResponse.getIsSuccess() ? "Alice is now following Bob" : "Error: " + followResponse.getError());

    // View Alice's following list
    System.out.println("[TEST] View Alice's following list (Expected: Bob)");
    UserRes followingList = followStub.viewFollowing(UserReq.newBuilder().setName("alice").build());
    if (followingList.getIsSuccess()) {
      System.out.println("Following List: " + followingList.getFollowedUserList());
    } else {
      System.out.println("Error: " + followingList.getError());
    }

    // Alice tries to follow Eve (should fail)
    System.out.println("[ERROR TEST] Alice tries to follow 'Eve' (should fail)");
    followResponse = followStub.follow(UserReq.newBuilder().setName("alice").setFollowName("eve").build());
    System.out.println(followResponse.getIsSuccess() ? "Alice followed Eve" : "Error: " + followResponse.getError());

    // Alice tries to follow Bob again (should fail)
    System.out.println("[ERROR TEST] Alice tries to follow Bob again (should fail)");
    followingList = followStub.viewFollowing(UserReq.newBuilder().setName("alice").build());
    if (followingList.getFollowedUserList().contains("bob")) {
      System.out.println("User is already following Bob.");
    } else {
      followResponse = followStub.follow(UserReq.newBuilder().setName("alice").setFollowName("bob").build());
      System.out.println(followResponse.getIsSuccess() ? "Alice followed Bob again (Unexpected)" : "Error: " + followResponse.getError());
    }

    // ================================
    // ⚖️ TESTING WEIGHT TRACKER SERVICE ⚖️
    // ================================
    System.out.println("\n[TEST] Adding weight entry for 'Charlie'");
    Weight weightEntry = Weight.newBuilder().setName("charlie").setWeightNum(75.5).build();
    AddWeightRequest weightRequest = AddWeightRequest.newBuilder().setWeight(weightEntry).build();
    AddWeightResponse weightResponse = weightStub.addWeight(weightRequest);
    System.out.println(weightResponse.getIsSuccess() ? "Weight added for Charlie" : "Error: " + weightResponse.getError());

    // Fetch weight history for Charlie
    System.out.println("[TEST] Fetch weight history for 'Charlie'");
    GetWeightRequest getWeightRequest = GetWeightRequest.newBuilder().setName("charlie").build();
    GetWeightResponse getWeightResponse = weightStub.getWeight(getWeightRequest);
    if (getWeightResponse.getIsSuccess()) {
      System.out.println("Weight History for Charlie: " + getWeightResponse.getWeightList());
    } else {
      System.out.println("Error: " + getWeightResponse.getError());
    }

    // Fetch weight history for a non-existent user
    System.out.println("[ERROR TEST] Fetch weight history for 'David' (should fail)");
    getWeightRequest = GetWeightRequest.newBuilder().setName("david").build();
    getWeightResponse = weightStub.getWeight(getWeightRequest);
    System.out.println(getWeightResponse.getIsSuccess() ? "Unexpected Success" : "Error: No weight data found for user: david");

    // Attempting to add invalid weight
    System.out.println("[ERROR TEST] Attempting to add invalid weight for 'Emma' (negative weight, should fail)");
    weightEntry = Weight.newBuilder().setName("emma").setWeightNum(-50.0).build();
    weightRequest = AddWeightRequest.newBuilder().setWeight(weightEntry).build();
    weightResponse = weightStub.addWeight(weightRequest);
    System.out.println(weightResponse.getIsSuccess() ? "Unexpected Success" : "Error: Invalid weight value.");

    // ================================
    // 📊 TESTING BMI CALCULATION 📊
    // ================================
    System.out.println("\n[TEST] Calculate BMI for 'Charlie'");
    BMIRequest bmiRequest = BMIRequest.newBuilder()
            .setWeight(75.5)
            .setHeight(1.75)
            .setUnits("metric")
            .build();
    BMIResponse bmiResponse = weightStub.getBMI(bmiRequest);
    if (bmiResponse.getIsSuccess()) {
      System.out.printf("BMI for Charlie: %.2f\n", bmiResponse.getBMI());
    } else {
      System.out.println("Error: " + bmiResponse.getError());
    }

    // Invalid BMI calculation (zero height)
    System.out.println("[ERROR TEST] Calculate BMI with zero height (should fail)");
    bmiRequest = BMIRequest.newBuilder()
            .setWeight(75.5)
            .setHeight(0.0)
            .setUnits("metric")
            .build();
    bmiResponse = weightStub.getBMI(bmiRequest);
    System.out.println(bmiResponse.getIsSuccess() ? "Unexpected Success" : "Error: Invalid height for BMI calculation.");

    System.out.println("\n✅ Auto-Test Mode Complete.\n");
  }




  public static void main(String[] args) throws Exception {
    if (args.length < 6) {
      System.out
          .println("Expected arguments: <host(String)> <port(int)> <regHost(string)> <regPort(int)> <message(String)> <regOn(bool)>");
      System.exit(1);
    }
    int port = 9099;
    int regPort = 9003;
    String host = args[0];
    String regHost = args[2];
    String message = args[4];
    boolean autoMode = args.length > 6 && args[6].trim().equals("1");
    System.out.println("DEBUG: autoMode = " + autoMode);
    try {
      port = Integer.parseInt(args[1]);
      regPort = Integer.parseInt(args[3]);
    } catch (NumberFormatException nfe) {
      System.out.println("[Port] must be an integer");
      System.exit(2);
    }

    // Create a communication channel to the server (Node), known as a Channel. Channels
    // are thread-safe
    // and reusable. It is common to create channels at the beginning of your
    // application and reuse
    // them until the application shuts down.
    String target = host + ":" + port;
    ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
        // Channels are secure by default (via SSL/TLS). For the example we disable TLS
        // to avoid
        // needing certificates.
        .usePlaintext().build();

    String regTarget = regHost + ":" + regPort;
    ManagedChannel regChannel = ManagedChannelBuilder.forTarget(regTarget).usePlaintext().build();
    try {

      // ##############################################################################
      // ## Assume we know the port here from the service node it is basically set through Gradle
      // here.
      // In your version you should first contact the registry to check which services
      // are available and what the port
      // etc is.

      /**
       * Your client should start off with 
       * 1. contacting the Registry to check for the available services
       * 2. List the services in the terminal and the client can
       *    choose one (preferably through numbering) 
       * 3. Based on what the client chooses
       *    the terminal should ask for input, eg. a new sentence, a sorting array or
       *    whatever the request needs 
       * 4. The request should be sent to one of the
       *    available services (client should call the registry again and ask for a
       *    Server providing the chosen service) should send the request to this service and
       *    return the response in a good way to the client
       * 
       * You should make sure your client does not crash in case the service node
       * crashes or went offline.
       */

      // Just doing some hard coded calls to the service node without using the
      // registry
      // create client
      Client client = new Client(channel, regChannel);
      if (autoMode) {  // RUN AUTO MODE TEST CASES // UPDATED
        client.runAutoMode();
        return;  // Exit after running auto tests
      }
      else {


        Scanner scanner = new Scanner(System.in);
        while (true) {
          System.out.println("\nMain Menu:");
          System.out.println("1. Weight Tracker");
          System.out.println("2. Follow Service");
          System.out.println("3. Exit");
          System.out.print("Select an option: ");

          int option = client.getUserChoice(scanner, 1, 3);

          if (option == 1) client.interactWithWeightTracker();
          else if (option == 2) client.interactWithFollowService();
          else {
            System.out.println("Exiting Client...");
            break;
          }
        }
      }
     // client.interactWithWeightTracker(); // Calls WeightTracker menu

      // call the parrot service on the server
     // client.askServerToParrot(message);

      // ask the user for input how many jokes the user wants
    //  BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
     // Scanner reader = new Scanner(System.in);


      // Reading data using readLine
    //  System.out.println("How many jokes would you like?"); // NO ERROR handling of wrong input here.
     // String num = reader.readLine();

      // calling the joked service from the server with num from user input
    //  client.askForJokes(Integer.valueOf(num));

      // adding a joke to the server
     // client.setJoke("I made a pencil with two erasers. It was pointless.");

      // showing 6 joked
     // client.askForJokes(Integer.valueOf(6));

      // list all the services that are implemented on the node that this client is connected to

      System.out.println("Services on the connected node. (without registry)");
      client.getNodeServices(); // get all registered services 

      // ############### Contacting the registry just so you see how it can be done

      /*
      if (args[5].equals("true")) { 
        // Comment these last Service calls while in Activity 1 Task 1, they are not needed and wil throw issues without the Registry running
        // get thread's services
        client.getServices(); // get all registered services 

        // get parrot
        client.findServer("services.Echo/parrot"); // get ONE server that provides the parrot service
        
        // get all setJoke
        client.findServers("services.Joke/setJoke"); // get ALL servers that provide the setJoke service

        // get getJoke
        client.findServer("services.Joke/getJoke"); // get ALL servers that provide the getJoke service

        // does not exist
        client.findServer("random"); // shows the output if the server does not find a given service
      }

       */

      System.out.println("Exiting client...");
    } finally {
      // ManagedChannels use resources like threads and TCP connections. To prevent
      // leaking these
      // resources the channel should be shut down when it will no longer be used. If
      // it may be used
      // again leave it running.
      channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
      regChannel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
    }
  }



}

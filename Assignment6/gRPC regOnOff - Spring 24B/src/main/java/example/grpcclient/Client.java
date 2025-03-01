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

  public void interactWithWeightTracker() {
    Scanner scanner = new Scanner(System.in);

    while (true) {
      System.out.println("\nWeight Tracker Options:");
      System.out.println("1. Add Weight");
      System.out.println("2. Get Weight History");
      System.out.println("3. Calculate BMI");
      System.out.println("4. Exit");
      System.out.print("Select an option: ");

      int choice;
      while (!scanner.hasNextInt()) { // Validate integer input
        System.out.println("Invalid input! Please enter a number.");
        scanner.next(); // Clear invalid input
      }
      choice = scanner.nextInt();

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
        System.out.println("Exiting Weight Tracker...");
        break; // Exit loop
      }

      else {
        System.out.println("Invalid option! Please enter a number between 1 and 4.");
      }
    }

    scanner.close();
  }



  public static void main(String[] args) throws Exception {
    if (args.length != 6) {
      System.out
          .println("Expected arguments: <host(String)> <port(int)> <regHost(string)> <regPort(int)> <message(String)> <regOn(bool)>");
      System.exit(1);
    }
    int port = 9099;
    int regPort = 9003;
    String host = args[0];
    String regHost = args[2];
    String message = args[4];
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
      client.interactWithWeightTracker(); // Calls WeightTracker menu

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

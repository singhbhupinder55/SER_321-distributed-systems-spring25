package example.grpcclient;

import io.grpc.*;
import io.grpc.stub.StreamObserver;
import service.*;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Client2 {
    private final RegistryGrpc.RegistryBlockingStub registryStub;

    public Client2(Channel registryChannel) {
        this.registryStub = RegistryGrpc.newBlockingStub(registryChannel);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n🔹 Fetching available services...");
            GetServicesReq request = GetServicesReq.newBuilder().build();
            ServicesListRes response = registryStub.getServices(request);

            if (!response.getIsSuccess() || response.getServicesList().isEmpty()) {
                System.out.println("❌ No services available in the registry.");
                return;
            }

            // **🔹 Show only the relevant services**
            List<String> allServices = response.getServicesList();
            List<String> allowedServices = allServices.stream()
                    .filter(s -> s.contains("Follow") || s.contains("WeightTracker") || s.contains("ToDoList"))
                    .toList();

            if (allowedServices.isEmpty()) {
                System.out.println("❌ No valid services found. Ensure that Follow, WeightTracker, and ToDoList are registered.");
                return;
            }

            // **📌 Display filtered services**
            System.out.println("\n📌 Available Services:");
            for (int i = 0; i < allowedServices.size(); i++) {
                System.out.println((i + 1) + ". " + allowedServices.get(i));
            }

            // **🔹 Improved input validation**
            int choice;
            while (true) {
                System.out.print("\nSelect a service to call (enter number, or 0 to exit): ");
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    if (choice == 0) {
                        System.out.println("Exiting...");
                        return;
                    }
                    if (choice >= 1 && choice <= allowedServices.size()) {
                        break;
                    }
                } else {
                    scanner.next();
                }
                System.out.println("❌ Invalid choice. Please enter a valid number.");
            }

            String selectedService = allowedServices.get(choice - 1);

            // **🔹 Find an available server for the selected service**
            FindServerReq findServerReq = FindServerReq.newBuilder().setServiceName(selectedService).build();
            SingleServerRes serverRes = registryStub.findServer(findServerReq);

            if (!serverRes.getIsSuccess()) {
                System.out.println("❌ No server found for this service.");
                continue;
            }

            // **🔹 Connect to the dynamically found server**
            String serverHost = serverRes.getConnection().getUri();
            int serverPort = serverRes.getConnection().getPort();
            ManagedChannel serviceChannel = ManagedChannelBuilder.forAddress(serverHost, serverPort)
                    .usePlaintext()
                    .build();

            System.out.println("✅ Connected to service: " + selectedService + " at " + serverHost + ":" + serverPort);

            // **🔹 Call the selected service**
            if (selectedService.contains("ToDoList")) {
                interactWithToDoList(ToDoListGrpc.newBlockingStub(serviceChannel));
            } else if (selectedService.contains("WeightTracker")) {
                interactWithWeightTracker(WeightTrackerGrpc.newBlockingStub(serviceChannel));
            } else if (selectedService.contains("Follow")) {
                interactWithFollowService(FollowGrpc.newBlockingStub(serviceChannel));
            } else {
                System.out.println("❌ Unknown service selected.");
            }

            serviceChannel.shutdown();
        }
    }

    private int getUserChoice(Scanner scanner, int min, int max) {
        int choice;
        while (true) {
            System.out.print("Enter your choice: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice >= min && choice <= max) {
                    return choice;  // Valid input, return choice
                }
            } else {
                scanner.next();  // Discard invalid input
            }
            System.out.println("❌ Invalid input! Please enter a number between " + min + " and " + max + ".");
        }
    }



    // **🔹 Implemented Follow Service Interaction**
    private void interactWithFollowService(FollowGrpc.FollowBlockingStub followStub) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n👥 Follow Service Options:");
            System.out.println("1. Add User");
            System.out.println("2. Follow a User");
            System.out.println("3. View Following List");
            System.out.println("4. Exit");

            int choice = getUserChoice(scanner, 1, 4);
            scanner.nextLine();

            if (choice == 1) {
                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                UserReq request = UserReq.newBuilder().setName(username).build();
                UserRes response = followStub.addUser(request);
                System.out.println(response.getIsSuccess() ? "User added successfully!" : "Error: " + response.getError());
            } else if (choice == 2) {
                System.out.print("Enter your username: ");
                String username = scanner.nextLine();
                System.out.print("Enter username to follow: ");
                String followName = scanner.nextLine();

                UserReq request = UserReq.newBuilder().setName(username).setFollowName(followName).build();
                UserRes response = followStub.follow(request);
                System.out.println(response.getIsSuccess() ? username + " is now following " + followName : "Error: " + response.getError());
            } else if (choice == 3) {
                System.out.print("Enter your username: ");
                String username = scanner.nextLine();
                UserReq request = UserReq.newBuilder().setName(username).build();
                UserRes response = followStub.viewFollowing(request);
                if (response.getIsSuccess()) {
                    System.out.println("Following List:");
                    response.getFollowedUserList().forEach(System.out::println);
                } else {
                    System.out.println("Error: " + response.getError());
                }
            } else if (choice == 4) {
                System.out.println("Exiting Follow Service...");
                break;
            }
        }
    }

    // **🔹 Implemented ToDoList Service**
    private void interactWithToDoList(ToDoListGrpc.ToDoListBlockingStub todoStub) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n📋 To-Do List Options:");
        System.out.println("1. Add Task");
        System.out.println("2. View Tasks");
        System.out.println("3. Complete a Task");
        System.out.println("4. Exit");

        int choice = getUserChoice(scanner, 1, 4);
        scanner.nextLine();

        if (choice == 1) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter task description: ");
            String description = scanner.nextLine();

            TaskRequest request = TaskRequest.newBuilder()
                    .setUser(username)
                    .setTaskDescription(description)
                    .build();

            TaskResponse response = todoStub.addTask(request);
            System.out.println(response.getMessage());
        }
    }

    // **🔹 Implemented WeightTracker Service**
    private void interactWithWeightTracker(WeightTrackerGrpc.WeightTrackerBlockingStub weightStub) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n⚖️ Weight Tracker Options:");
        System.out.println("1. Add Weight Entry");
        System.out.println("2. Get Weight History");
        System.out.println("3. Exit");

        int choice = getUserChoice(scanner, 1, 3);
        scanner.nextLine();

        if (choice == 1) {
            System.out.print("Enter your name: ");
            String name = scanner.nextLine();
            System.out.print("Enter weight: ");
            double weight = scanner.nextDouble();

            Weight weightEntry = Weight.newBuilder().setName(name).setWeightNum(weight).build();
            AddWeightRequest request = AddWeightRequest.newBuilder().setWeight(weightEntry).build();
            AddWeightResponse response = weightStub.addWeight(request);

            System.out.println(response.getIsSuccess() ? "Weight added successfully!" : "Error: " + response.getError());
        }
    }

    public static void main(String[] args) {
        ManagedChannel regChannel = ManagedChannelBuilder.forAddress("localhost", 9002).usePlaintext().build();
        Client2 client2 = new Client2(regChannel);
        client2.run();
        regChannel.shutdown();
    }
}

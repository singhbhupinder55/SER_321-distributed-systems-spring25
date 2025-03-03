# GRPC Services and Registry
This project implements a distributed system using gRPC, where:
- A Registry Server allows service nodes to register and clients to discover available services.
- Multiple Service Nodes provide different functionalities such as Weight Tracking, Follow System, and To-Do List Management.
- A Client can query the registry, select a service dynamically, and interact with it.
- A second client (Client2) enhances dynamic service discovery by fetching available services from the registry and allowing users to choose which service to interact with.


## Features Implemented
Implemented two gRPC services from the provided .proto files:
- Weight Tracker Service (Tracks weight history and calculates BMI)
- Follow Service (Manages user relationships) 
- Designed and implemented a custom To-Do List gRPC Service 
- Dynamic service discovery using the Registry Server 
- Created Client2 to interact with the registry dynamically  
- Ensured robust error handling to prevent client crashes


## Run things locally without registry
 To run locally and without Registry which you should do for the beginning

First Terminal

    gradle runNode

Second Terminal

    gradle runClient

## Run things locally with registry

First terminal

    gradle runRegistryServer

Second terminal

    gradle runNode -PregOn=true 

Third Terminal

    gradle runClient -PregOn=true

### Running the Program with the Registry Server
This enables service discovery where nodes register and clients dynamically fetch available services.

Steps:
- Terminal 1: Start the Registry Server
```sh
gradle runRegistryServer
```
- Terminal 2: Start a Node and Register it
```sh
gradle runNode -PregOn=true
```
- Terminal 3: Start the Client with Registry Enabled
```sh
gradle runClient -PregOn=true
```
Terminal 4: Run Client2 for Dynamic Service Selection
```sh
gradle runClient2
```

### gradle runRegistryServer
Will run the Registry node on localhost (arguments are possible see gradle). This node will run and allows nodes to register themselves. 

The Server allows Protobuf, JSON and gRPC. We will only be using gRPC

### gradle runNode
Will run a node with an Echo and Joke service. The node registers itself on the Registry. You can change the host and port the node runs on and this will register accordingly with the Registry

### gradle runClient
Will run a client which will call the services from the node, it talks to the node directly not through the registry. At the end the client does some calls to the Registry to pull the services, this will be needed later.

### gradle runDiscovery
Will create a couple of threads with each running a node with services in JSON and Protobuf. This is just an example and not needed for assignment 6. 

### gradle testProtobufRegistration
Registers the protobuf nodes from runDiscovery and do some calls. 

### gradle testJSONRegistration
Registers the json nodes from runDiscovery and do some calls. 

### gradle test
Runs the test cases for Joke and Echo. It expects a new start of the server before running the tests!
First run
    gradle runNode
then in second terminal
    gradle test

To run in IDE:
- go about it like in the ProtoBuf assignment to get rid of errors
- all mains expect input, so if you want to run them in your IDE you need to provide the inputs for them, see build.gradle

## How to Use the Program
- Clients will display a list of available services.
- User selects a service to invoke.
- Client sends a request to the selected service.
- Server processes the request and returns a response.
- Client displays the response.
- For automated testing, use:
```sh
  gradle runClient -Phost=host -Pport=port -Pauto=1
```

- or 
- gradle runClient -Pauto=1

## Requirements Fulfilled
- Implemented two gRPC services from .proto files. 
- Designed and implemented a custom gRPC service. 
- Ensured the client dynamically discovers services via the registry. 
- Created Client2 to interact with the registry dynamically. 
- Ensured the system is robust and does not crash. 
- Included a structured and detailed README file.

## Screencast Demonstration

- [Assignment6](https://youtu.be/WsRofYuRr8M) 
- A video showcasing the project in action.
  

## Task 1: Starting Your Services Locally
###  Task 1 Description
Task 1 required us to analyze, understand, and run the given code, then implement additional gRPC services based on the provided .proto files.
- We successfully implemented:
  - Weight Tracker Service (Allows users to track and retrieve their weight data)
  - Follow Service (Manages user relationships, allowing users to follow each other)

### How Task 1 Works
The system follows this flow:
- The Node Server starts and runs the implemented gRPC services.
- The Client connects to the server and allows users to interact with available services.
- Users can track weight data or follow other users via a menu-based system.
- The Auto Mode (gradle runClient -Pauto=1) runs automated tests to verify functionality.


### Implemented Services
#### Weight Tracker Service
- Users can add weight entries with a timestamp.
- Retrieve weight history for a given user.
- Calculate BMI based on user input.
- Ensures error handling (e.g., invalid weight input, nonexistent user data).

#### Weight Tracker Commands:
1. Add Weight
2. Get Weight History
3. Calculate BMI
4. Exit to Main Menu

#### Follow Service
- Users can add themselves to the system.
- Follow another user (ensuring case-insensitive uniqueness).
- Retrieve list of users they are following.
- Prevents users from following a nonexistent user or following the same user twice.

#### Follow Service Commands:
1. Add User
2. Follow a User
3. View Following List
4. Exit to Main Menu


## Task 2 : Inventing your own service 
### Overview
This project implements a To-Do List Service using gRPC. The service allows users to:
- Add tasks to their personal list.
- View tasks assigned to them.
- Mark tasks as completed.
- Store tasks persistently using a JSON file. 
- The implementation includes proper error handling, user validation, and data persistence to ensure a smooth user experience.

### Features

1. Add a Task
 - Users can add tasks to their personal to-do list.
 - Tasks are assigned a unique ID starting from 1.
 - Tasks are saved persistently.

2. View Tasks
 - Users can retrieve their list of tasks.
 - If the user does not exist, an error message is displayed.
 - Tasks are displayed in a numbered format (starting from 1).

3. Mark Task as Completed
-  Users can mark a task as completed only if it’s not already marked.
 - If a user selects an already completed task, the system will notify them.
 - Users are shown their list of tasks before selecting one to complete.

4.  Data Persistence
 - Tasks are saved to a JSON file (tasks.json).
 - Upon restart, tasks are loaded from storage.
 - The persistence system ensures data is not lost between sessions.

### Testing Instructions
1. Run the server (gradle runServer).
``` sh
 gradle runNode
```
2. Run the client (gradle runClient).
``` sh
 gradle runClient
```
3. Navigate the menu options:
 - Add tasks
 - View tasks
 - Complete tasks
 - Verify persistence by restarting the client/server
4. Try edge cases (e.g., marking already completed tasks, viewing non-existent users, invalid inputs).


### Task 3.1: Registering Services Locally
This task involved modifying Client2 to dynamically discover and connect to services via the Registry.
- Steps Completed
 - Created Client2.java to interact with the Registry. 
 - Verified that the Registry receives and registers nodes correctly.
 - Modified Client2 to fetch, list, and connect to registered services.
 - Implemented robust error handling to prevent crashes.
Commands to Run Client2
```sh
gradle runClient2
```

### Summary
This To-Do List gRPC service implements a fully functional and persistent task manager. It fulfills all the necessary requirements by:
 - Supporting three distinct gRPC requests.
 - Handling error conditions properly.
 - Ensuring persistent data storage using a JSON file.
 - Providing a user-friendly interface with numbered tasks.











# GRPC Services and Registry

The following folder contains a Registry.jar which includes a Registering service where Nodes can register to allow clients to find them and use their implemented GRPC services. 

Some more detailed explanations will follow and please also check the build.gradle file


## Project Overview
This project implements a distributed system using gRPC. The system consists of:
- A Registry Server that allows nodes to register and discover services.
- Multiple Service Nodes that provide different functionalities.
- A Client that can interact with the registered services.

## Features Implemented
Two gRPC services from the provided .proto files.
- A custom-designed gRPC service.
- Dynamic service discovery using the Registry.
- A Client2 that queries the registry and dynamically selects services.
- Robust error handling to prevent crashes.


## Run things locally without registry
To run see also video. To run locally and without Registry which you should do for the beginning

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

## Requirements Fulfilled
- Implemented two gRPC services from .proto files. 
- Designed and implemented a custom gRPC service. 
- Ensured the client dynamically discovers services via the registry. 
- Created Client2 to interact with the registry dynamically. 
- Ensured the system is robust and does not crash. 
- Included a structured and detailed README file. 
- Screencast (to be added before submission).

## Screencast Demonstration

- [TO BE ADDED](saxasx) - A video showcasing the project in action.
- 
  
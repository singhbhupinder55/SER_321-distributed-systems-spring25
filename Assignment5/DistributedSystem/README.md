# Distributed System - SER 321 Assignment 5

## 1. How to Run the Program

The program consists of three components: **Leader, Client, and Nodes**.  
Each component can be run using Gradle tasks.

### **Run the Leader**
```sh
gradle runLeader
```

### **Run the Client**
```sh
gradle runClient
```

### **Run a Node**
```sh
gradle runNode
```

## Purpose & Functionality

This project implements a distributed computation system where:
- A Client sends a list of numbers and a delay time to the Leader.
- The Leader:
    - Computes the sum sequentially.
    - Splits the list among Nodes for parallel processing.
    - Verifies correctness using a simple consensus algorithm.
- Nodes compute the sum of their assigned numbers and return results to the Leader.

Note: The goal is to compare sequential vs. distributed computation performance.

##  Protocol Description
- Communication is implemented using sockets.
- The Leader distributes the workload among multiple Nodes.
- Nodes process the sum with a delay to simulate computation time.
- The Leader aggregates results and performs a consensus check.

## Workflow Overview
1. The Client collects user input (list of numbers + delay time).
2. The Client sends this data to the Leader.
3. The Leader:
   - Computes the sum sequentially.
   - Splits the list into equal parts and assigns them to Nodes.
   - Collects results from Nodes.
   - Verifies correctness using a consensus check.
4. The Leader sends the final sum and performance comparison back to the Client.
5. The Client displays the results.


## Requirements Fulfilled
- Gradle project with build.gradle configured correctly.
- Simple and intuitive user interactions.
- Program runs via Gradle tasks (runLeader, runClient, runNode).
- Implements distributed computation and consensus checking.
- Handles faulty nodes via Gradle flag (-pFault=1).

## Screencast Demonstration
A video demonstration of the project running will be provided, showcasing:
- Running the Leader, Client, and Nodes.
- Sending data from Client → Leader → Nodes.
- Performing sequential vs. distributed computation.
- Handling faulty nodes and consensus checking.

[Screencast Link](jsagibeiqcab9)

--

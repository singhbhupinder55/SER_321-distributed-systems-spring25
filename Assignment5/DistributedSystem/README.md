# Distributed System - SER 321 Assignment 5

## 1. How to Run the Program

This project implements a **Leader-Node-Client** distributed system using **Gradle and Java sockets**. It supports **fault tolerance, consensus verification, and performance comparison** between **sequential vs. distributed computation**.

Each component can be run using Gradle tasks.

### **🚀 Order of Execution**
To run the system correctly, **start the components in this order:**
1. **Start the Leader first**
2. **Start at least 3 Nodes**
3. **Start the Client last**

### **Run the Leader**
```sh
gradle runLeader
```
### **Run a Node**
```sh
gradle runNode
```
### **Run a Faulty Node**
```sh
gradle runNode -PFault=1
```
- (This makes a node intentionally return an incorrect sum.)

### **Run the Client**
```sh
gradle runClient
```


## 🔎 Purpose & Functionality
This project implements a distributed computation system where:
- A Client sends a list of numbers and a delay time to the Leader.
- The Leader:
    - Computes the sum sequentially.
    - Splits the list among Nodes for parallel processing.
    - Verifies correctness using a simple consensus algorithm.
- Nodes compute the sum of their assigned numbers and return results to the Leader.
- The Leader compares performance of sequential vs. distributed computation.
- Faulty nodes can be simulated to test consensus failure scenarios.

## 📡 Protocol Description
The system communicates using **socket-based messaging**.

### 📌 Leader Protocols
| **Message Type**          | **Description**                                   | **Sent By**       |
|---------------------------|---------------------------------------------------|-------------------|
| `"NODE"`                 | Node registers with the Leader                    | Node → Leader    |
| `"CLIENT numbers;delay"`  | Client sends numbers + delay                      | Client → Leader  |
| `"VERIFY"`               | Leader asks Nodes to verify results               | Leader → Nodes   |
| `"YES"`                  | Node confirms correct computation                 | Node → Leader    |
| `"NO"`                   | Node detects an incorrect computation             | Node → Leader    |

---

### 📌 Node Protocols
| **Message Type**         | **Description**                                   | **Sent By**      |
|--------------------------|---------------------------------------------------|------------------|
| `"numbers;delay"`       | Receive numbers & delay from Leader               | Leader → Node   |
| `"sum"`                | Send computed sum back to Leader                   | Node → Leader   |
| `"VERIFY"`             | Leader requests verification                        | Leader → Node   |
| `"YES"`                | Node agrees with computed sum                       | Node → Leader   |
| `"NO"`                 | Node disagrees with computed sum                    | Node → Leader   |

---

### 📌 Client Protocols
| **Message Type**          | **Description**                                   | **Sent By**       |
|---------------------------|---------------------------------------------------|-------------------|
| `"CLIENT numbers;delay"`  | Sends computation request                        | Client → Leader  |
| `"Sequential Sum: X"`     | Received sum from sequential computation         | Leader → Client  |
| `"Distributed Sum: Y"`    | Received sum from distributed computation        | Leader → Client  |
| `"Consensus: Passed"`     | All nodes agree on computation result            | Leader → Client  |
| `"Consensus: Failed"`     | One or more nodes disagreed                      | Leader → Client  |

## ⚙ 4. Workflow Overview

1. **Start the Leader** to manage nodes and computations.
2. **Start at least 3 Nodes**, which will register with the Leader.
3. **Start the Client**, which collects user input (numbers & delay).
4. **Client sends data to the Leader**.
5. **Leader performs the following steps**:
   - Computes the sum sequentially.
   - Splits the list and assigns portions to Nodes.
   - Sends the assigned portions to the Nodes.
   - Collects computed results from Nodes.
   - Runs a **Consensus Check** to verify correctness.
6. **Leader sends the final result** & performance comparison back to the Client.
7. **Client displays the results** & consensus status.


## Requirements Fulfilled
- Gradle project with build.gradle configured correctly.
- Simple and intuitive user interactions.
- Program runs via Gradle tasks (runLeader, runClient, runNode).
- Implements distributed computation and consensus checking.
- Handles faulty nodes via Gradle flag (-pFault=1).
- Parallel processing using multi-threading.

## 📊  Performance Analysis
The goal is to compare **Sequential Computation vs. Distributed Computation**.

### 📌 Test Results

| Test Case                 | Sequential Sum (Time) | Distributed Sum (Time) | Consensus  | Node Verifications |
|---------------------------|----------------------|----------------------|------------|--------------------|
| 5 Numbers, 50ms Delay     | 15.0 (267ms)        | 15.0 (184ms)        | ✅ Passed  | YES YES YES        |
| 10 Numbers, 100ms Delay   | 550.0 (1038ms)      | 550.0 (427ms)      | ✅ Passed  | YES YES YES        |
| 15 Numbers, 200ms Delay   | 120.0 (3050ms)      | 120.0 (1027ms)     | ✅ Passed  | YES YES YES        |
| Faulty Node Introduced    | 21.0 (320ms)        | -22.0 (135ms)      | ❌ Failed  | YES YES NO         |

### 📌 Observations
- **Sequential Computation** is significantly slower as list size increases.
- **Distributed Computation** performs faster due to **parallel processing**.
- **Faulty Node Case** correctly triggers **consensus failure**.



## Screencast Demonstration
A video demonstration of the project running will be provided, showcasing:
- Running the Leader, Client, and Nodes.
- Sending data from Client → Leader → Nodes.
- Performing sequential vs. distributed computation.
- Handling faulty nodes and consensus checking.

[Screencast Link](jsagibeiqcab9)




## 🚀  Example Runs

### ✅ Normal Case (No Faults)
Run the following commands in order:
```sh
gradle runLeader
gradle runNode
gradle runNode
gradle runNode
gradle runClient
```
#### 📌 Client Output:
📜 **Final Results from Leader:**
📌 Sequential Sum: 15.0 (Time: 267ms)
⚡ Distributed Sum: 15.0 (Time: 184ms)
✅ Consensus: Passed! All nodes agree.
📜 Node Verifications: YES YES YES

### ❌ Faulty Node Case
Run the following commands, introducing a faulty node:
```sh
gradle runLeader
gradle runNode
gradle runNode
gradle runNode -PFault=1
gradle runClient
```

#### 📌 Client Output:
📜 **Final Results from Leader:**
📌 Sequential Sum: 21.0 (Time: 320ms)
⚡ Distributed Sum: -22.0 (Time: 135ms)
📜 Consensus: Failed
📜 Node Verifications: YES YES NO

## 📌 Summary of Features

- ✅ Leader registers and manages nodes.
- ✅ Client sends computation requests and displays results.
- ✅ Nodes compute partial sums and return results.
- ✅ Leader performs sequential and distributed computation.
- ✅ Fault injection allows testing of faulty nodes.
- ✅ Consensus check ensures computation correctness.
- ✅ Multi-threaded execution enables parallel computation.
- ✅ Leader detects faulty nodes and informs the client.

--

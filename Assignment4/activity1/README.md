# Assignment 4 Activity 1
## Description
This assignment enhances a **simple single-threaded server** by:
- Implementing additional **operations** in the `Performer` class.
- **Converting it to a multi-threaded server** (Task 2).
- **Adding a thread pool to limit concurrent clients** (Task 3).

### Implemented Features:
**Operations added in `Performer`**:
- **Add:** Adds a new string to the shared list.
- **Display:** Shows a string at a specific index.
- **Count:** Returns the total number of strings.
- **Quit:** Disconnects the client.

**Multi-threading Enhancements**:
- **Task 2 (`ThreadedServer`)**: Unbounded multi-threaded server.
- **Task 3 (`ThreadPoolServer`)**: Thread-limited server with a **default cap of 4 clients**.

 **New Class - `ClientHandler`**:
- Created to manage **each client's connection**.
- Used in **both `ThreadedServer` and `ThreadPoolServer`**.
- Ensures **proper request handling** and communication between **client & server**.


## Protocol

### Requests
General Request Format:
```
{ 
   "selected": <int: 1=add, 3=display, 4=count,  0=quit>, 
   "data": <thing to send>
}
```
Fields:
 - selected <int>: The operation selected.
 - data <Depends on the operation>:
   - add <String>: The string to be added.
   - display <None>: Display List
   - count <None>: None.
   - quit <None>: None.

### Responses
General Success Response: 
```
{
   "type": <String: "add", "display", "count", "quit">, 
   "data": <thing to return> 
}
```
Error Response
```
{
   "type": "error", 
   "message": "<error string>"
}
```
Fields:
 - type <String>: Echoes original operation selected from request.
 - data <Depends on the operation>: The result returned by the server.
   - Add <String>: Returns the new list 
   - Display <String>: String from list at specified index
   - Count <int>: Number of elements (Strings) in the list
 
General Error Response: 
```
{
   "type": "error", 
   "message"": <error string> 
}
```

## How to run the program
### Terminal
Base Code, please use the following commands:
```
    For Server, run "gradle runServer -Pport=9099 -q --console=plain"
```
```   
    For Client, run "gradle runClient -Phost=localhost -Pport=9099 -q --console=plain"
```   

### **Default Gradle Commands**
| **Task** | **Gradle Command (Default Values)** |
|----------|----------------------------------|
| **Task 1 (Single-threaded Server)** | `gradle runTask1` |
| **Task 2 (Multi-threaded Server - Unbounded)** | `gradle runTask2` |
| **Task 3 (Multi-threaded Server - Bounded to 4 Clients)** | `gradle runTask3` |
| **Run Client (Default: `localhost`, port `8000`)** | `gradle runClient` |

---

### **Run with Parameters**
| **Task** | **Gradle Command with Custom Values** |
|----------|--------------------------------------|
| **Run Task 1 with custom port** | `gradle runTask1 -Pport=9099 -q --console=plain` |
| **Run Task 2 (Threaded Server) with custom port** | `gradle runTask2 -Pport=9099 -q --console=plain` |
| **Run Task 3 (ThreadPoolServer) with custom limits** | `gradle runTask3 -Pport=9099 -Pthreads=3 -q --console=plain` |
| **Run Client with a different server IP/port** | `gradle runClient -Phost=34.226.213.4 -Pport=9099 -q --console=plain` |

## **Screencast Activity 1**

🎥 **[Watch the Screencast Here](https://youtu.be/uBc_KfoQdBA)**

### **📌 Screencast Overview**
This screencast demonstrates the successful implementation of **Activity 1** by showcasing all **three tasks**:
1. **Task 1:** Single-threaded Server
2. **Task 2:** Multi-threaded Server (Unbounded)
3. **Task 3:** Multi-threaded Server (Bounded to 2 Clients)

### **🎬 What’s Covered in the Screencast?**

1️⃣ **Gradle File Overview**
- Show the **`build.gradle`** file and confirm the presence of:
   - **3 separate tasks** (`runTask1`, `runTask2`, `runTask3`).
   - **Default values** (`localhost`, port `8000`).
   - `runClient` uses **default values** if no arguments are passed.

2️⃣ **Task 1: Single-threaded Server**
- Start the **single-threaded server** using:
  ```bash
  gradle runTask1 -q --console=plain
  ```
- Open **one client**:
  ```bash
  gradle runClient -q --console=plain
  ```
- **Test operations** (`add`, `display`, `count`, `quit`) and verify correct **state updates**.
- **Quit** the client.

3️⃣ **Task 2: Multi-threaded Server (Unbounded)**
- Start the **multi-threaded server**:
  ```bash
  gradle runTask2 -q --console=plain
  ```
- Open **multiple clients simultaneously**:
  ```bash
  gradle runClient -q --console=plain
  ```
- Show that **each client operates independently** without affecting others.
- Demonstrate **thread-safe behavior**:
   - One client adds a string while another counts or displays the list.

4️⃣ **Task 3: Multi-threaded Server (Bounded to 4 Clients)**
- Start **ThreadPoolServer with a limit of 2 clients**:
  ```bash
  gradle runTask3 -Pthreads=2 -q --console=plain
  ```
- Open **2 clients** (all should connect successfully).
- Attempt to **connect a 3th client** (should be rejected with a message:  
  `"Server is at full capacity. Try again later."`).
- Verify that **existing clients still function correctly**.

5️⃣ **State Consistency Verification**
- Run operations from different clients to ensure **state integrity**:
   - **Client 1** adds a string.
   - **Client 2** counts elements (verifies the addition).
   - **Client 3** displays the updated list.
- Confirm **no data is lost or overwritten incorrectly** between clients.

6️⃣ **Final Recap & Summary**
- **All 3 tasks work as expected**.
- **Clients operate independently without interfering with each other**.
- **Thread safety and concurrency work properly**.
- **The bounded server correctly limits connections**.


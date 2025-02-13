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


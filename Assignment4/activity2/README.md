# Description 
This is a simple Sudoku game. Many players can play a separate game but see the same leaderboard. Player can type "exit" at any point during the game state to quit the game and disconnect the client from the
server.

## 🏆 Features:
- **Leaderboard:** Players compete for the highest score.  
- **Dynamic Board Generation:** Each game has a **randomly generated board**.  
- **Move Validation:** Players **input numbers** and get feedback if a move is **valid or invalid**.  
- **Clear Board Options:**  
  - `cb` → **Clear the entire board (-5 points penalty).**  
  - `r` → **Request a new board (-5 points penalty).**  
- **Scoring System:**  
  - **Correct moves** = Keep your score.  
  - **Invalid moves** = **Lose points.**  
  - **Clearing board / requesting new board** = **-5 points penalty.**  
- **Exit Anytime:** Players can **disconnect** anytime by typing `"exit"`


## How to run the program
The proto file can be compiled using
``gradle generateProto``  

This will also be done when building the project.  

You should see the compiled proto file in Java under build/generated/source/proto/main/java/buffers  

Now you can run the client and server, please follow these instructions to start:
* Please run `gradle runServer -Pport=port` and `gradle runClient -Phost=hostIP -Pport=port` together.
* There is a separate task `gradle runServerGrading -Pport=port` which has a given board that you can also use for testing
  * Solution as row col val
  * 1 8 8
    1 5 7
    2 6 2
    8 1 9
    8 4 2
    9 2 5
    9 5 1
* Can also be run using `gradle runServer` and `gradle runClient` for localhost and default port
* Recommended that you include the flag `-q --console=plain` to get the best gaming experience (limited output)
* Programs runs on hostIP
* Port and hostIP specification is optional.
* NOTE: If for some reason the .txt files are causing the server or client to crash please either delete them or clear their contents.
  * Hasn't happened to me but, I wanted to make a note just in case someone comes across this!


## Running the Server and Client
### Start the Server
- On your AWS instance or local machine, run:
```
gradle runServer -Pport=8000
```

- This starts the server on port 8000 (or any custom port you specify).
- A fixed board version for testing can be started with
```
gradle runServerGrading -Pport=8000
```

### Start the Client
- On your local machine or another system, connect to the server:
```
gradle runClient -Phost=34.226.213.4 -Pport=8000
```

-  If the client and server are on the same machine, use:

```
gradle runClient
```

- For best gaming experience, use:
```
gradle runClient -q --console=plain
```

## Screencast
A short 2-4 minute video showing how to:
-  Start the server & client
- Play the game (enter moves, clear board, request new board)
- View the leaderboard
- Handle valid/invalid inputs
###
Watch the full demo here:
[Screencast Link](https://www.youtube.com/watch?v=W7WEX8-8th8)


# Readme checklist:



# Requirements (If checked off then completed and includes debugging)
### Game Functionality Implemented:
- Multiplayer support (each player has a unique game session).
- Leaderboard with real-time updates (tracks wins and logins).
- Move validation (prevents invalid inputs).
- Clear board functionality (cb) with -5 points penalty.
- Request a new board (r) with -5 points penalty.
- Exit anytime (exit) without losing leaderboard progress.

### Running and Debugging Completed:

- Proto files compiled successfully.
- Server runs correctly on AWS & localhost.
- Client connects and communicates with the server.
- Game logic functions correctly with penalties and scoring.
- Proper exception handling for errors and invalid inputs.

## Additional Notes
- The Sudoku board follows standard rules where numbers 1-9 must not repeat in any row, column, or 3x3 grid.
- The difficulty level affects the number of pre-filled values, ensuring easy to hard game variations.
- The game penalizes invalid moves and clearing actions, making it more competitive.


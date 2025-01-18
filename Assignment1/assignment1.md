# GitHub Repository link :  

[ser321-spring25-A-bsingh55 - Assignment1 GitHub](https://github.com/singhbhupinder55/ser321-spring25-A-bsingh55/tree/master/Assignment1)

## Part 1 : Linux, Setup  
### Part 1.1 : Command line tasks  
#### System Used : Linux  

1: **mkdir cli_assignment** (This command creates a directory named cli_assignment.)  
2: **cd cli_assignment** (This command changes the current directory to the cli_assignment directory.)  
3: **touch stuff.txt** (This command creates an empty file named stuff.txt in the cli_assignment directory.)  
4: **cat > stuff.txt** (Adds multiple lines of text to the file. Press CTRL+D to stop adding text.)  
5: **wc -w stuff.txt and ww -l stuff. txt** (These commands count the number of words and lines in the file stuff.txt)  
6: **echo "additional test" >> stuff.txt** (This command appends more text to the stuff.txt file.)  
7: **mkdir draft** (This creates a new directory called draft.)  
8: **mv stuff.txt draft/** (Moves stuff.txt into the draft directory)  
9: **cd draft && touch .secret.txt** (Changes the current directory to draft and creates a hidden file called secret.txt)  
10: **cp -r draft final** (copies the draft directory to a new directory named final.)  
11: **mv draft draft.remove** (Renames the draft directory to draft.remove)  
12: **mv draft.remove final/** (Moves the draft.remove directory into the final directory.)  
13: **ls -l** (List all files and subdirectories along with their permissions in the cli_assgnment directory.)  
14: **zcat NASA_access_log_Aug95.gz** (Lists the contents of the NASA_access_log_Aug95.gz file without extracting it.)  
15: **guzip NASA_access_log_Aug95.gz** (Extracts the content of the NASA_access_log_Aug95.gz file)  
16: **mv NASA_access_log_Aug95.txt logs.txt** (Renames the extracted file to logs.txt)  
17: **mv logs.txt cli_assignment/** (Moves the logs.txt into the cli_assignment directory.)  
18: **head -n 100 logs.txt** (Reads the top 100 lines of the logs.txt)  
19: **head -n 100 logs.txt > logs_top_100.txt** (Creates a new file logs_top_100.txt containing the top 100 lines of logs.txt)  
20: **tail -n 100 logs.txt** (Reads the bottom 100 lines of the logs.txt file)  
21: **tail -n 100 logs.txt > logs_bottom_100.txt** (Creates annew file logs_bottom_100.txt containing the bottom 100 lines of logs.txt)  
22: **cat logs_top_100.txt logs_bottom_100.txt logs_snapshot.txt** (creates a new logs_snapshot.txt by concatenating logs_top_100.txt and logs_botton_100.txt)  
23: **echo "bsingh55: This is a great assignment, Jan 18th 2025" >> logs_snapshot.txt** (Appends your asurite and date to the logs_snapshot.txt file)  
24: **less logs.txt** (Reads the logs.txt file using the less command)  
25: **cut -d% -f1 marks.csv > student_names.txt** (Extracts the student names from the marks.csv file and saves them to student_names.txt)  
26: **cut -d% -f3 marks.csv | sort** (Extracts and sorts the marks from subject_3 in marks.csv)  
27: **awk -F% '{sum+=$2} END {print sum/NR}' marks.csv** (Calculates and prints the average marks from subject_2 in the marks.csv file.)  
28: **echo "average" > done.txt** (Saves the average marks from subject_2 into a new file done.txt)  
29: **mv done.txt final/** (Moves done.txt to the final directory)  
30: **mv final/done.txt final/average.txt** (Renames the file done.txt to average.txt in the final directory.)  



 
## 2.2 Running Examples
### Example 1: Running the Socket Server and Client  

I ran the example `JavaSimpleSock2` in the `Sockets` directory.  
The example demonstrates a TCP server-client communication where:  
- The server is set to accept up to 3 connections.  
- The client sends messages and numbers to the server.  

**Steps:**
1. The server (`SocketServer`) was started on AWS.  
2. The client (`SocketClient`) was run locally with the command:  
   `gradle SocketClient -PHost=34.226.213.4 -Pmessage="This is try for first connection" -Pnumber=1`.  
3. The server received and processed the messages sent by the client.  
4. I ran the client multiple times to simulate different messages and numbers.  

![Result Screenshot](file:///Users/bhupindersingh/Desktop/asu/assignment1/screenshorts/JavaSimpleSock2%20result.png)  
  
**Output Explanation:**
- The server successfully received connections and processed the messages.
- The client sent multiple messages as expected, and the server acknowledged each one.
- The final output shows the completion of all connections and messages processed.  
  



### Example 2: Running the Socket Server and Client 
### Example 3: Running the Socket Server and Client 

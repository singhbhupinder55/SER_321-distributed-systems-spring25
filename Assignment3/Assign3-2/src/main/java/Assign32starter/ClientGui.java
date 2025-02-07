package Assign32starter;

import java.awt.Dimension;

import org.json.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.WindowConstants;

/**
 * The ClientGui class is a GUI frontend that displays an image grid, an input text box,
 * a button, and a text area for status. 
 * 
 * Methods of Interest
 * ----------------------
 * show(boolean modal) - Shows the GUI frame with current state
 *     -> modal means that it opens GUI and suspends background processes. 
 * 		  Processing still happens in the GUI. If it is desired to continue processing in the 
 *        background, set modal to false.
 * newGame(int dimension) - Start a new game with a grid of dimension x dimension size
 * insertImage(String filename, int row, int col) - Inserts an image into the grid
 * appendOutput(String message) - Appends text to the output panel
 * submitClicked() - Button handler for the submit button in the output panel
 * 
 * Notes
 * -----------
 * > Does not show when created. show() must be called to show he GUI.
 * 
 */
public class ClientGui implements Assign32starter.OutputPanel.EventHandlers {
	JDialog frame;
	PicturePanel picPanel;
	OutputPanel outputPanel;
	String currentMess;

	Socket sock;
	OutputStream out;
	ObjectOutputStream os;
	BufferedReader bufferedReader;




	// TODO: SHOULD NOT BE HARDCODED change to spec
	String host = "localhost";
	int port = 9000;

	/**
	 * Construct dialog
	 * @throws IOException 
	 */
	public ClientGui(String host, int port) throws IOException {
		this.host = host; 
		this.port = port; 
	
		frame = new JDialog();
		frame.setLayout(new GridBagLayout());
		frame.setMinimumSize(new Dimension(500, 500));
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		// setup the top picture frame
		picPanel = new PicturePanel();
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 0.25;
		frame.add(picPanel, c);

		// setup the input, button, and output area
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.weighty = 0.75;
		c.weightx = 1;
		c.fill = GridBagConstraints.BOTH;
		outputPanel = new OutputPanel();
		outputPanel.addEventHandlers(this);
		frame.add(outputPanel, c);

		picPanel.newGame(1);
		insertImage("img/hi.png", 0, 0);

		open(); // opening server connection here
		currentMess = new JSONObject().put("type", "hello").toString(); // Initial message
		try {
			os.writeObject(currentMess);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String string = this.bufferedReader.readLine();
		System.out.println("Got a connection to server");
		JSONObject json = new JSONObject(string);
		outputPanel.appendOutput(json.getString("value")); // putting the message in the outputpanel
		currentMess = json.getString("type"); // Store next expected step

		insertImage(json.getString("image"), 0, 0); // Ensure server-sent image is displayed
		/// would put image in picture panel
		close(); //closing the connection to server

		// Now Client interaction only happens when the submit button is used, see "submitClicked()" method
	}

	/**
	 * Shows the current state in the GUI
	 * @param makeModal - true to make a modal window, false disables modal behavior
	 */
	public void show(boolean makeModal) {
		frame.pack();
		frame.setModal(makeModal);
		frame.setVisible(true);
	}

	/**
	 * Creates a new game and set the size of the grid 
	 * @param dimension - the size of the grid will be dimension x dimension
	 * No changes should be needed here
	 */
	public void newGame(int dimension) {
		picPanel.newGame(1);
		outputPanel.appendOutput("Started new game with a " + dimension + "x" + dimension + " board.");
	}

	/**
	 * Insert an image into the grid at position (col, row)
	 * 
	 * @param filename - filename relative to the root directory
	 * @param row - the row to insert into
	 * @param col - the column to insert into
	 * @return true if successful, false if an invalid coordinate was provided
	 * @throws IOException An error occured with your image file
	 */
	public boolean insertImage(String filename, int row, int col) throws IOException {

		System.out.println("Image insert");
		String error = "";
		try {
			// insert the image
			if (picPanel.insertImage(filename, row, col)) {
				// put status in output
				return true;
			}
			//error = "File(\"" + filename + "\") not found.";
		} catch(PicturePanel.InvalidCoordinateException e) {
			// put error in output
			error = e.toString();
		}
		outputPanel.appendOutput(error);
		return false;

	}

	/**
	 * Submit button handling
	 * 
	 * TODO: This is where your logic will go or where you will call appropriate methods you write. 
	 * Right now this method opens and closes the connection after every interaction, if you want to keep that or not is up to you. 
	 */
	@Override
	public void submitClicked() {
		try {
		open(); // opening a server connection again
		System.out.println("submit clicked ");

		// Pulls the input box text
		String input = outputPanel.getInputText();
			outputPanel.clearInputText(); // Clears the input field after submission

		// TODO evaluate the input from above and create a request for client. 

			// Create JSON message for the server
			JSONObject message = new JSONObject();

			if (currentMess.equals("request_name")) { // handling name only
				message.put("type", "name");
				message.put("value", input);

			} else if (currentMess.equals("request_age")) { // Expecting name & age
				message.put("type", "age");
				message.put("value", input);

			} else if (currentMess.equals("menu")) {
				message.put("type", "menu_choice");
				message.put("value", input);

			} else if (currentMess.equals("request_rounds")) {
					message.put("type", "rounds");
					message.put("value", input);

			}

			else if (currentMess.equals("start_game") || currentMess.equals("guess_response")) {
				message.put("type", "player_input");
				message.put("value", input);
			}


			else {
				message.put("type", "input");
				message.put("value", input);

			}

			System.out.println("Sending JSON to server: " + message.toString()); // Debugging log
			// Send JSON message to the server
			os.writeObject(message.toString());


			// wait for an answer and handle accordingly

			System.out.println("Waiting on response");
			String string = this.bufferedReader.readLine();
			JSONObject response = new JSONObject(string);

			// Store the latest server message type
			currentMess = response.getString("type");  // Now the client always knows what to expect next



			// Process response

			if (response.has("status") && response.getString("status").equals("fail")) {
				outputPanel.appendOutput("⚠️ Error: " + response.getString("message"));
				return;
			}

			if (response.getString("type").equals("error")) {
				outputPanel.appendOutput("Error: " + response.getString("message"));
				return;
			}

			// Handle "invalid_input" to let the user retry
			if (response.getString("type").equals("invalid_input")) {
				outputPanel.appendOutput(response.getString("value")); // Show error message
				return; // Do not change `currentMess`, so the client stays in the same game state
			}

			// Display response message
			if (response.has("value")) {
				outputPanel.appendOutput(response.getString("value"));
			}

			// Display score update
			if (response.getString("type").equals("score_update")) {
				int updatedScore = response.getInt("score");
				outputPanel.setPoints(updatedScore); // Update UI points label
			}

			if (response.has("image")) {
				insertImage(response.getString("image"), 0, 0);
			}



			// Handle "start_game" → Display image & hint
			if (response.getString("type").equals("start_game")) {
				outputPanel.appendOutput("Hint: " + response.getString("hint"));
				insertImage(response.getString("image"), 0, 0);

			}


			// Display the final score in the UI when the game ends
			if (response.getString("type").equals("game_over")) {
				outputPanel.appendOutput(response.getString("value")); //  Show final score
				currentMess = "menu";
			}

			if (currentMess.equals("quit")) {
				outputPanel.appendOutput("👋 Exiting the game...");
				System.out.println("Thanks for playing! 👋 the End");
				close();  // Close socket connection
				frame.dispose();  // Close GUI
				return;
			}


			close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			outputPanel.appendOutput("⚠️ Network Error: Unable to communicate with the server."); // Prevents crash on network failure
		}  catch (JSONException e) {
			outputPanel.appendOutput("⚠️ JSON Error: Invalid response received."); // Prevents crash on bad JSON
		} catch (Exception e) {
			outputPanel.appendOutput("⚠️ Unexpected Error: " + e.getMessage()); // Handles any unknown error
		}
	}

	/**
	 * Key listener for the input text box
	 * 
	 * Change the behavior to whatever you need
	 */
	@Override
	public void inputUpdated(String input) {
		if (input.equals("surprise")) {
			outputPanel.appendOutput("You found me!");
		}
	}

	public void open() throws UnknownHostException, IOException {
		try {
			this.sock = new Socket(host, port); // connect to host and socket

			// get output channel
			this.out = sock.getOutputStream();
			// create an object output writer (Java only)
			this.os = new ObjectOutputStream(out);
			this.bufferedReader = new BufferedReader(new InputStreamReader(sock.getInputStream()));

		} catch (UnknownHostException e) {
			outputPanel.appendOutput("⚠️ Error: Unknown host. Please check server address."); //  Now shows an error message instead of crashing
		} catch (IOException e) {
			outputPanel.appendOutput("⚠️ Error: Cannot connect to server. Is it running?"); //  Now handles connection failures properly
		}
	}
	
	public void close() {
        try {
            if (out != null)  out.close();
            if (bufferedReader != null)   bufferedReader.close(); 
            if (sock != null) sock.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public static void main(String[] args) throws IOException {
		// create the frame


		try {
			String host = "localhost";
			int port = 8888;


			ClientGui main = new ClientGui(host, port);
			main.show(true);


		} catch (Exception e) {e.printStackTrace();}



	}
}

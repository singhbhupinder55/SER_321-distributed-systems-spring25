package Assign32starter;
import java.net.*;
import java.util.Base64;
import java.util.Set;
import java.util.Stack;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import java.awt.image.BufferedImage;
import java.io.*;
import org.json.*;


/**
 * A class to demonstrate a simple client-server connection using sockets.
 * Ser321 Foundations of Distributed Software Systems
 */
public class SockServer {
	static Map<String, List<String>> imageSource = new HashMap<>(); // Stores multiple images per wonder
	private static HashMap<String, Integer> leaderboard = new HashMap<>(); // Player leaderboard
	private static HashMap<String, List<String>> hints = new HashMap<>(); // Hints for Wonders
	private static HashMap<String, String> correctAnswers = new HashMap<>(); // Stores correct answers
	private static HashMap<String, Integer> playerRounds = new HashMap<>(); // Tracks rounds per player
	private static HashMap<String, Integer> currentRound = new HashMap<>(); // Tracks the current round per player
	private static HashMap<String, Integer> hintIndex = new HashMap<>(); // Track hint progress
	private static HashMap<String, String> playerNames = new HashMap<>();

	private static final String LEADERBOARD_FILE = "leaderboard.json";



	public static void main (String args[]) {
		Socket sock;
		try {
			
			//opening the socket here, just hard coded since this is just a bas example
			ServerSocket serv = new ServerSocket(8888); // TODO, should not be hardcoded
			System.out.println("Server ready for connection");


			// Load leaderboard from file
			loadLeaderboard();

			// Initialize Wonders and Hints
			initializeHints();
			initializeImages();
			initializeAnswers();

			// read in one object, the message. we know a string was written only by knowing what the client sent. 
			// must cast the object from Object to desired type to be useful
			while(true) {
				sock = serv.accept(); // blocking wait

				// could totally use other input outpur streams here
				ObjectInputStream in = new ObjectInputStream(sock.getInputStream());
				PrintWriter outWrite = new PrintWriter(sock.getOutputStream(), true);


				JSONObject response = new JSONObject();

				try {

					String s = (String) in.readObject();
					JSONObject json = new JSONObject(s); // the requests that is received


					// Handle unknown commands robustly
					if (!json.has("type")) {
						response = createErrorResponse("Invalid request format: Missing 'type' field.");
					} else if (json.getString("type").equals("hello")) {

						System.out.println("- Got a hello");

						response.put("type", "request_name");
						response.put("status", "ok");
						response.put("value", "Hello, Please enter your name?");
						sendImg("img/hi.png", response); // calling a method that will manipulate the image and will make it send ready

					} else if (json.getString("type").equals("name")) {  // Handling only name first
						String playerName = json.getString("value").trim();
						playerNames.put(playerName, playerName);
						leaderboard.putIfAbsent(playerName, 0);
						saveLeaderboard();  // Ensures all players are saved

						System.out.println("Player name received: " + playerName); //  Added log

						response.put("type", "request_age");
						response.put("status", "ok");
						response.put("value", "Nice to meet you, " + playerName + "! Now, please enter your age ? (e.g. 25).\n");
						response.put("image", "img/questions.jpg");  // Attach the image

					} else if (json.getString("type").equals("age")) {
						try {
							int playerAge = Integer.parseInt(json.getString("value"));

							if (playerAge <= 0) {
								response = createErrorResponse("Invalid age. Please enter a valid positive number.");
								response.put("type", "request_age"); //  Loop back on invalid input


							} else if (playerAge > 150) {  // Prevent unrealistically high numbers
								response = createErrorResponse("⚠️ That age is too high. Please enter a realistic number.");
								response.put("type", "request_age");
							} else {
								String playerName = null;
								if (!playerNames.isEmpty()) {
									playerName = playerNames.entrySet().iterator().next().getKey(); // Get first name
								}

								System.out.println("Player " + (playerName != null ? playerName : "UNKNOWN") +
										" entered age: " + playerAge); // Debugging log

								response.put("type", "menu");
								response.put("status", "ok");
								response.put("value", "Welcome! 🎉\nWhat would you like to do?\n"
										+ "1. Play the Game\n"
										+ "2. View the Leaderboard\n"
										+ "3. Quit the Game\n"
										+ "Enter 1, 2, or 3 to choose.");
							}
						} catch (NumberFormatException e) {
							response.put("value", "⚠️ Invalid input for age. Please enter a number.");
							response.put("type", "request_age");

						}

					} else if (json.getString("type").equals("menu_choice")) {

						try {
							int choice = Integer.parseInt(json.getString("value")); // Convert input to integer
							String playerName = null;
							if (!playerNames.isEmpty()) {
								playerName = playerNames.entrySet().iterator().next().getKey(); // Get first player
							}

							if (choice < 1 || choice > 3) {
								response.put("value", "Error: Invalid Entry. Please enter a valid number between 1 and 3\n");
								response.put("type", "menu"); //  Loop back on invalid input

							} else {

								switch (choice) {
									case 1:
										System.out.println("Player " + (playerName != null ? playerName : "UNKNOWN") + " selected Play the Game");
										response.put("type", "request_rounds");
										response.put("status", "ok");
										response.put("value", "How many rounds would you like to play? Enter a number:");
										break;

									case 2:
										System.out.println("Player " + (playerName != null ? playerName : "UNKNOWN") + " selected View the Leaderboard");
										loadLeaderboard(); // Ensure the latest leaderboard is loaded
										if (leaderboard.isEmpty()) {
											response.put("type", "leaderboard");
											response.put("value", "📊 Leaderboard is empty. No players yet.");
										} else {
											StringBuilder leaderboardText = new StringBuilder("📊 Leaderboard:\n");
											for (Map.Entry<String, Integer> entry : leaderboard.entrySet()) {
												leaderboardText.append(entry.getKey()).append(": ").append(entry.getValue()).append(" points\n");
											}
											response.put("type", "leaderboard");
											response.put("value", leaderboardText.toString());
										}
										break;

									case 3:
										System.out.println("Player " + (playerName != null ? playerName : "UNKNOWN") + " selected Quit the Game");
										response.put("type", "quit");
										response.put("value", "Thanks for playing! 👋 The game will now exit.");
										break;

									default:
										response.put("type", "menu");
										response.put("message", "Invalid choice. Please enter 1, 2, or 3.\n");
								}
							}
						} catch (NumberFormatException e) {
							// Invalid number choice
							response.put("type", "menu");
							response.put("value", "Invalid choice. Please enter 1️⃣, 2️⃣, or 3️⃣:\n");
						}


						// / /////////////////// up this everything is striaght

					} //  Step 6a: Start Game - Request number of rounds
					else if (json.getString("type").equals("rounds")) {
						String playerName = null;

						if (!playerNames.isEmpty()) {
							playerName = playerNames.entrySet().iterator().next().getKey(); // Get first player
						}

						try {
							int rounds = Integer.parseInt(json.getString("value"));
							if (rounds <= 0) {
								response = createErrorResponse("Invalid number of rounds. Please enter a positive number.");
								response.put("type", "request_rounds");

							} else {
								playerRounds.put(playerName, rounds);  // Store player rounds
								currentRound.put(playerName, 1);
								hintIndex.put(playerName, 0);
								System.out.println("Player " + (playerName != null ? playerName : "UNKNOWN") + " will play " + rounds + " rounds.");

								String currentWonder = "Grand Canyon"; // Default to Grand Canyon at the start

								//  Print the answer on the server for easy grading
								System.out.println("Answer: " + currentWonder);

								// Start the game and send first hint
								response.put("type", "start_game");
								response.put("status", "ok");
								//response.put("value", "Game started! 🎮 Round 1 of " + rounds);
								response.put("value", "Game started! 🎮 Round 1 of " + rounds + "\n\n"
										+ "🔹 *Game Commands:*\n"
										+ "👉 Type 'skip' - Skip this Wonder and start the next round.\n"
										+ "👉 Type 'next' - Get another hint for the current Wonder.\n"
										+ "👉 Type 'remaining' - See how many hints are left.\n"
										+ "👉 Type 'exit' - Quit the game and return to the main menu.\n"
										+ "👉 Type 'restart' - To start the game from starting again." );

								response.put("hint", getHint(currentWonder));
								response.put("image", getRandomImage(currentWonder));

							}
						} catch (NumberFormatException e) {
							response = createErrorResponse("Invalid input for rounds. Please enter a number.");
							response.put("type", "request_rounds");


						}
					}
					else if (json.getString("type").equals("player_input")) {  // Handle all player inputs
						String input = json.getString("value").trim().toLowerCase();
						String playerName = playerNames.getOrDefault(playerNames.entrySet().iterator().next().getKey(), "UNKNOWN");

						if (!currentRound.containsKey(playerName) || !playerRounds.containsKey(playerName)) {
							response.put("type", "error");
							response.put("value", "⚠️ Error: No active game found. Please restart the game.");
						} else {
							int round = currentRound.get(playerName);
							int totalRounds = playerRounds.get(playerName);

							// Track player's current wonder instead of re-randomizing it each time
							correctAnswers.putIfAbsent(playerName, getRandomWonder());
							String currentWonder = correctAnswers.get(playerName);

							switch (input) {
								//  Restart the game at any time
								case "restart":
									playerRounds.put(playerName, 0);
									currentRound.put(playerName, 0);
									hintIndex.put(playerName, 0);
									correctAnswers.remove(playerName);

									response.put("type", "request_rounds");
									response.put("value", "🔄 Game restarted! How many rounds would you like to play? Enter a number:");
									break;

								// Handle "skip" - Move to the next round
								case "skip":
									round++;
									if (round > totalRounds) {
										response.put("type", "game_over");
										response.put("value", "Game over! 🎉 Check the leaderboard.");
									} else {
										currentRound.put(playerName, round);
										correctAnswers.put(playerName, getRandomWonder());
										currentWonder = correctAnswers.get(playerName);

										System.out.println("Answer: " + currentWonder);

										response.put("type", "start_game");
										response.put("value", "You skipped! Starting round " + round + " of " + totalRounds);
										response.put("hint", getHint(currentWonder));
										response.put("image", getRandomImage(currentWonder));
									}
									break;

								// Handle "next" - Provide another hint
								case "next":
									int hintPos = hintIndex.getOrDefault(playerName, 0);
									List<String> wonderHints = hints.get(currentWonder);

									if (hintPos < wonderHints.size()) {
										response.put("type", "hint");
										response.put("value", "🔹 Hint: " + wonderHints.get(hintPos));
										hintIndex.put(playerName, hintPos + 1);
									} else {
										response.put("type", "hint");
										response.put("value", "⚠️ No more hints available for this wonder.");
									}
									break;

								// Handle "remaining" - Show how many hints are left
								case "remaining":
									if (hints.containsKey(currentWonder)) {
										int remaining = hints.get(currentWonder).size() - hintIndex.getOrDefault(playerName, 0);
										response.put("type", "hints_left");
										response.put("value", "Hints remaining: " + Math.max(remaining, 0));
									} else {
										response.put("type", "error");
										response.put("value", "⚠️ Error: No hints available.");
									}
									break;

								// Handle "exit" - Return to menu
								case "exit":
									response.put("type", "menu");
									response.put("value", "Welcome! 🎉\nWhat would you like to do?\n"
											+ "1. Play the Game\n"
											+ "2. View the Leaderboard\n"
											+ "3. Quit the Game\n"
											+ "Enter 1, 2, or 3 to choose.");
									break;

								// Handle correct or incorrect guesses
								default:
									response.put("type", "guess_response");

									if (input.equalsIgnoreCase(currentWonder)) {  // Correct guess
										int remainingHints = hints.get(currentWonder).size();
										updateScore(playerName, remainingHints);

										int updatedScore = leaderboard.getOrDefault(playerName, 0);

										round++;
										if (round > totalRounds) {
											response.put("type", "game_over");
											response.put("value", "🎉 Correct! Game over! Check the leaderboard.");
										} else {
											currentRound.put(playerName, round);
											correctAnswers.put(playerName, getRandomWonder());
											currentWonder = correctAnswers.get(playerName);

											response.put("type", "start_game");
											response.put("value", "🎉 Correct! Starting round " + round + " of " + totalRounds);
											response.put("hint", getHint(currentWonder));
											response.put("image", getRandomImage(currentWonder));

											System.out.println("📢 Correct Answer for Next Round: " + currentWonder);
											System.out.println("📢 Player: " + playerName + " | Score Updated: " + updatedScore);
										}
									} else {
										response.put("type", "guess_response");
										response.put("value", "⚠️ Invalid input! Please enter a valid command or guess.\n"
												+ "🔹 *Game Commands:*\n"
												+ "👉 Type 'skip' - Skip this Wonder and start the next round.\n"
												+ "👉 Type 'next' - Get another hint for the current Wonder.\n"
												+ "👉 Type 'remaining' - See how many hints are left.\n"
												+ "👉 Type 'exit' - Quit the game and return to the main menu.\n"
												+ "👉 Your guess (e.g., 'Colosseum').\n"
												+ "\n🔄 Try again:");
									}

									break;
							}
						}
					}


					else {

						System.out.println("Unknown command received: " + json.getString("type"));


						response.put("value", "⚠️ Invalid command. Please try again!\n\n"
								+ "🔹 *Game Commands:*\n"
								+ "👉 'skip' - Skip this Wonder and start the next round.\n"
								+ "👉 'next' - Get another hint for the current Wonder.\n"
								+ "👉 'remaining' - See how many hints are left.\n"
								+ "👉 Your guess (e.g., 'Colosseum').\n");



					}
				} catch (JSONException e) {
					response = createErrorResponse("Invalid JSON format.");
				} catch (ClassCastException e) {
					response = createErrorResponse("Unexpected data format. Ensure JSON is sent correctly.");
				} catch (Exception e) {
					response = createErrorResponse("Server error. Try again.");
				}

				outWrite.println(response.toString());
			}

		} catch(Exception e) {
			//e.printStackTrace();
			System.out.println("⚠️ Network error: " + e.getMessage()); // Prevents server crash on network failure
		}
	}

	/* TODO this is for you to implement, I just put a place holder here */


	private static String getRandomWonder() {
		List<String> wonders = new ArrayList<>(correctAnswers.keySet());
		return wonders.get(new Random().nextInt(wonders.size()));
	}


	// New method for structured error handling
	private static JSONObject createErrorResponse(String message) {
		JSONObject response = new JSONObject();
		response.put("type", "error");
		response.put("status", "fail");
		response.put("message", message);
		return response;
	}

	private static void initializeHints() {
		hints.put("Grand Canyon", Arrays.asList(
				"A breathtaking natural wonder carved by the Colorado River over millions of years.",
				"One of the Seven Natural Wonders of the World, located in Arizona.",
				"Its rock formations reveal nearly two billion years of Earth's history.",
				"A popular spot for hiking, rafting, and stunning sunset views."
		));

		hints.put("Stonehenge", Arrays.asList(
				"A prehistoric monument in England, built over 5000 years ago.",
				"The purpose of this stone circle remains one of the world's greatest mysteries.",
				"Located in Wiltshire, England, and aligned with the solstices.",
				"Believed to have been constructed using ancient engineering techniques."
		));

		hints.put("Colosseum", Arrays.asList(
				"An ancient Roman arena where gladiators once fought for glory.",
				"One of the most famous landmarks in Rome, Italy.",
				"Originally known as the Flavian Amphitheatre.",
				"Could hold up to 50,000 spectators during its peak use."
		));
	}

	private static void initializeImages() {
		imageSource.put("Grand Canyon", Arrays.asList("img/GrandCanyon1.png", "img/GrandCanyon2.png", "img/GrandCanyon3.png", "img/GrandCanyon4.png"));
		imageSource.put("Stonehenge", Arrays.asList("img/Stonehenge1.png", "img/Stonehenge2.png", "img/Stonehenge3.png", "img/Stonehenge4.png"));
		imageSource.put("Colosseum", Arrays.asList("img/Colosseum1.png", "img/Colosseum2.png", "img/Colosseum3.png", "img/Colosseum4.png"));
	}
	private static void initializeAnswers() {
		correctAnswers.put("Grand Canyon", "Grand Canyon");
		correctAnswers.put("Stonehenge", "Stonehenge");
		correctAnswers.put("Colosseum", "Colosseum");
	}

	private static String getRandomImage(String wonder) {
		List<String> images = imageSource.get(wonder);
		if (images != null && !images.isEmpty()) {
			return images.get(new Random().nextInt(images.size()));
		}
		return "img/questions.jpg";
	}

	private static String getHint(String wonder) {
		List<String> wonderHints = hints.get(wonder);
		return (wonderHints != null && !wonderHints.isEmpty()) ? wonderHints.get(0) : "No hints available";
	}


	private static void updateScore(String playerName, int remainingHints) {
		int baseScore = 5; // Minimum points per correct answer
		int bonus = remainingHints * 5; // Bonus points based on hints remaining
		int earnedScore = baseScore + bonus; // Total score for this round

		// Accumulate the score instead of replacing it
		int totalScore = leaderboard.getOrDefault(playerName, 0) + earnedScore;
		leaderboard.put(playerName, totalScore);

		// Debugging: Print the updated score
		System.out.println("🔹 Player: " + playerName + " | Earned: " + earnedScore + " | Total Score: " + leaderboard.get(playerName));

		// Send updated score to the client
		JSONObject response = new JSONObject();
		response.put("type", "score_update");
		response.put("value", "🎉 Score updated! Total Score: " + totalScore);

		saveLeaderboard();  // Persist updated leaderboard
		}


	public static JSONObject sendImg(String filename, JSONObject obj) throws Exception {
		File file = new File(filename);

		if (!file.exists()) {
			obj.put("error", "Image file not found: " + filename);
			return obj;
		}

		try {
			BufferedImage image = ImageIO.read(file);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(image, "png", baos);
			byte[] imageBytes = baos.toByteArray();
			String encodedImage = Base64.getEncoder().encodeToString(imageBytes);

			obj.put("image", encodedImage);
		} catch (IOException e) {
			obj.put("error", "Error reading image: " + e.getMessage());
		}

		return obj;
	}

	// Save leaderboard to a file
	private static void saveLeaderboard() {
		try (FileWriter file = new FileWriter(LEADERBOARD_FILE)) {
			JSONObject leaderboardJson = new JSONObject(leaderboard);
			file.write(leaderboardJson.toString());
		} catch (IOException e) {
			System.out.println("Error saving leaderboard: " + e.getMessage());
		}
	}

	private static void loadLeaderboard() {
		File file = new File(LEADERBOARD_FILE);
		if (file.exists()) {
			try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
				StringBuilder jsonText = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null) {
					jsonText.append(line);
				}

				// Handle empty file case
				if (jsonText.toString().trim().isEmpty()) {
					leaderboard = new HashMap<>();
					return;
				}

				// Handle corrupted JSON case
				try {
					JSONObject leaderboardJson = new JSONObject(jsonText.toString());
					for (String key : leaderboardJson.keySet()) {
						leaderboard.put(key, leaderboardJson.getInt(key));
					}
				} catch (JSONException e) {
					System.out.println("Error: Corrupt leaderboard.json file. Resetting leaderboard.");
					leaderboard = new HashMap<>();
				}

			} catch (IOException e) {
				System.out.println("Error loading leaderboard: " + e.getMessage());
			}
		}
	}


}

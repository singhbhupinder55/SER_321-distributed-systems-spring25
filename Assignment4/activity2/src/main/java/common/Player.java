package common;

/**
 * Class: Player 
 * Description: Class that represents a Player, I only used it in my Client 
 * to sort the LeaderBoard list
 * You can change this class, decide to use it or not to use it, up to you.
 */

public class Player implements Comparable<Player> {

    private int wins;
    private String name;
    private int loginCount; // Track how often the player has logged in
    private int points;

    // constructor, getters, setters
    public Player(String name, int wins){
      this.wins = wins;
      this.name = name;
      this.loginCount = 0; // Initialize login count to 1 upon first creation
        this.points = 0; // Start every game at 0 points
    }

    public int getWins(){
      return wins;
    }

    public String getName() {return name;}

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }


    public int getLoginCount() {return loginCount;}

    // Update player's score; overwrite if the new score is higher
    public void updateWins(int newWins) {
        if (newWins > this.wins) {
            this.wins = newWins;
        }
    }

    // Increment login count
    public synchronized void incrementLoginCount() {
        this.loginCount++;
    }


    // override equals and hashCode
    @Override
    public int compareTo(Player player) {
        return Integer.compare(player.getWins(), this.wins);
                //player.getWins() - this.wins;
    }

    @Override
       public String toString() {
        return String.format("%s: %d wins, %d logins", name, wins, loginCount);
        //return ("\n" +this.wins + ": " + this.name);
       }

    // Add this method to update player points
    public void updatePoints(int delta) {
        this.points += delta;
    }

    // Add a getter for points
    public int getPoints() {
        return this.points;
    }
    public void resetPoints() {
        this.points = 0; //  Reset points when a new game starts
    }
}
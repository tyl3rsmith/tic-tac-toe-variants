/* This class represents a player and stores information such as their name, role, and number of wins. */

public class Player {
    private final String name;
    private int wins;
    private String role;
    private static int count;

    public Player(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("A name must be provided");
        }
        this.name = name;
        count++;
    }

    // if no name specified default name to player
    public Player() { this("player " + count); }

    public String getName() {
        return this.name;
    }

    public int getWins() { return wins; }

    public String getRole() { return role; }

    public void setWins(int wins) {
        if (wins < 0) {
            throw new IllegalArgumentException("Wins cannot be negative.");
        }

        this.wins = wins;
    }

    public void setRole(String role) { this.role = role; }

    public void addWin() { wins++; }
}
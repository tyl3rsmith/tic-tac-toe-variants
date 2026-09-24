/* This class represents a player */

public class Player {
    private String name;
    private int wins;
    private String role;
    private static int count;

    public Player(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("A name must be provided");
        }
        this.name = name;
        count++;
    }

    // if no name specified default name to player
    public Player() { this("player " + Integer.toString(count)); }

    public String getName() {
        return this.name;
    }

    public int getWins() { return wins; }

    public String getRole() { return role; }

    public void setName(String name) { this.name = name; }

    public void setWins(int wins) { this.wins = wins; }

    public void setRole(String role) { this.role = role; }
}
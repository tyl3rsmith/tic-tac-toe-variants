/* This class represents a player */

public class Player {
    private String name;
    private int wins;
    private String role;

    Player(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int getWins() { return wins; }

    public String getRole() { return role; }

    public void setName(String name) { this.name = name; }

    public void setWins(int wins) { this.wins = wins; }

    public void setRole(String role) { this.role = role; }
}
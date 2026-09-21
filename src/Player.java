/* This class represents a player */

public class Player {
    private String name;
    private int wins;

    Player(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int getWins() { return wins; }

    public void setName(String name) { this.name = name; }

    public void setWins(int wins) { this.wins = wins; }
}
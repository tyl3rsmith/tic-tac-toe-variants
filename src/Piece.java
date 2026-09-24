/* This class handles all piece state and behavior */

public class Piece {
    private char symbol;
    private Position position;

    public Piece(char symbol, Position position) {
        if (position == null) {
            throw new IllegalArgumentException("You must specify a position for the piece");
        }
        this.symbol = symbol;
        this.position = position;
    }

    // default to position (0, 0)
    public Piece(char symbol) {
        this(symbol, new Position());
    }

    // default to symbol X at (0, 0)
    public Piece() {
        this('X');
    }

    public char getSymbol() { return symbol; }

    public Position getPosition() { return position; }
}
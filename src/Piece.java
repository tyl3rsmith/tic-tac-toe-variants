/* This class represents a piece placed on the board. It stores the piece's symbol and position. */

public class Piece {
    private final char symbol;
    private final Position position;

    public Piece(char symbol, Position position) {
        // not allowing pieces to exist without an associated position
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

    public char getSymbol() {
        return symbol;
    }

    public Position getPosition() {
        return position;
    }
}
/* This class represents a row and column position. It is used by Piece and Board to track positions of pieces on the board. */

public class Position {
    private final int row;
    private final int col;

    public Position(int row, int col) {
        // non-negative positions only
        if (row < 0 || col < 0) {
            throw new IllegalArgumentException("Positions cannot be negative");
        }

        this.row = row;
        this.col = col;
    }

    // default to position (0, 0)
    public Position() {
        this(0, 0);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
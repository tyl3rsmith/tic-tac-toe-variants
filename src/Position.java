/* This class represents a row/column location in the board */

public class Position {
    private int row;
    private int col;

    public Position(int row, int col) {
        if (row < 0 || col < 0) {
            throw new IllegalArgumentException("Positions cannot be negative");
        }
        this.row = row;
        this.col = col;
    }

    // default to position (0, 0)
    public Position() { this(0, 0); }

    public int getRow() { return this.row; }

    public int getCol() {
        return this.col;
    }

    public void setRow(int row) {
        if (row < 0) {
            throw new IllegalArgumentException("Row cannot be negative");
        }
        this.row = row;
    }

    public void setCol(int col) {
        if (col < 0) {
            throw new IllegalArgumentException("Column cannot be negative");
        }
        this.col = col;
    }
}
/* This class represents the game board and manages the pieces placed on the board. It's responsible for updating,
resetting, validating, and checking whether the board is full. */

public class Board {
    private Piece[][] board;
    private int openSpots; // this will be helpful for checking win conditions
    private final int rows;
    private final int cols;

    public Board(int rows, int cols) {
        // can't have negative dimensions
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Board dimensions must be greater than 0.");
        }

        this.board = new Piece[rows][cols];
        this.openSpots = rows * cols;
        this.rows = rows;
        this.cols = cols;
    }

    // if not specified default to a 3x3 board
    public Board() {
        this(3, 3);
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Piece getPiece(int row, int col) {
        return board[row][col];
    }

    public boolean isValidMove(Position position) {
        int row = position.getRow();
        int col = position.getCol();

        // out of bounds
        if (row >= rows || row < 0 || col >= cols || col < 0) {
            return false;
        }

        // check if the spot is empty
        return board[row][col] == null;
    }

    public void updateBoard(Position position, Piece piece) {
        int row = position.getRow();
        int col = position.getCol();

        board[row][col] = piece;
        openSpots--;
    }

    public boolean isFull() {
        return openSpots == 0;
    }

    public void resetBoard() {
        board = new Piece[rows][cols];
        openSpots = rows * cols;
    }
}
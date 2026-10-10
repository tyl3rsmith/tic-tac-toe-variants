/* This class represents the game board and manages the pieces placed on the board. It's responsible for updating,
resetting, validating, and checking whether the board is full. */

public class Board {
    private BoardCell[][] board;
    private int openSpots; // this will be helpful for checking win conditions
    private final int rows;
    private final int cols;

    public Board(int rows, int cols) {
        // can't have negative dimensions
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Board dimensions must be greater than 0.");
        }
        this.openSpots = rows * cols;
        this.rows = rows;
        this.cols = cols;
        this.initializeBoard();
    }

    // if not specified default to a 3x3 board
    public Board() {
        this(3, 3);
    }

    // initializes each board cell
    private void initializeBoard() {
        board = new BoardCell[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                board[row][col] = new BoardCell(new Position(row, col));
            }
        }
    }

    // validate the position on the board
    public boolean isValidMove(Position position) {
        // safety check
        if (position == null) {
            return false;
        }

        int row = position.getRow();
        int col = position.getCol();

        // out of bounds
        if (row >= rows || row < 0 || col >= cols || col < 0) {
            return false;
        }

        // check if the spot is empty
        return board[row][col].isEmpty();
    }

    // sets the piece on the board as long as the position is valid
    public void updateBoard(Position position, Piece piece) {
        if (piece == null) {
            throw new IllegalArgumentException("A piece is required");
        }
        if (!isValidMove(position)) {
            throw new IllegalArgumentException("This move is invalid");
        }
        int row = position.getRow();
        int col = position.getCol();

        board[row][col].setPiece(piece);
        openSpots--;
    }

    // clears the board for reuse in the next round and updates the open spots
    public void resetBoard() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                board[row][col].clearCell();
            }
        }
        openSpots = rows * cols;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Piece getPiece(int row, int col) {
        return board[row][col].getPiece();
    }

    public boolean isFull() {
        return openSpots == 0;
    }
}
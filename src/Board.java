/* This class handles the board state and operations */

public class Board {
    private Piece[][] board;
    private int openSpots;
    private int rows;
    private int cols;

    public Board(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Board dimensions must be greater than 0.");
        }

        this.board = new Piece[rows][cols];
        this.openSpots = rows * cols;
        this.rows = rows;
        this.cols = cols;
    }

    // default to a 3x3 board
    public Board() {
        this(3, 3);
    }

    public Piece[][] getBoard() {
        return board;
    }

    public int getRows() { return rows; }

    public int getCols() { return cols; }

    public Piece getPiece(int row, int col) { return board[row][col]; }

    public void setBoard(Piece[][] board) {
        this.board = board;
    }

    public boolean isValidMove(Position position) {
        int row = position.getRow();
        int col = position.getCol();

        // out of bounds check
        if (row >= board.length || row < 0 || col >= board[0].length || col < 0) {
            return false;
        }

        // return false if spot is already occupied
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

    void resetBoard() {
        board = new Piece[board.length][board[0].length];
        openSpots = board.length * board[0].length;
    }
}
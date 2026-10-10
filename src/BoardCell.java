/* The BoardCell class represents one location on the board by managing the position of the cell and the piece there. */

public class BoardCell {
    private final Position position;
    private Piece piece;

    public BoardCell(Position position, Piece piece) {
        if (position == null) {
            throw new IllegalArgumentException("Please specify a position for the cell");
        }
        this.position = position;
        this.piece = piece;
    }

    // allowing null pieces at the start but later on games will require a piece be placed on the board
    public BoardCell(Position position) { this(position, null); }

    public void clearCell() { this.piece = null; }

    public boolean isEmpty() { return this.piece == null; }

    public Position getPosition() { return this.position; }

    public Piece getPiece() { return this.piece; }

    // associates a board cell with a piece
    public void setPiece(Piece piece) {
        if (piece == null) {
            throw new IllegalArgumentException("You need to specify a piece to be placed");
        }

        if (!isEmpty()) {
            throw new IllegalArgumentException("This cell is already occupied");
        }

        this.piece = piece;
    }
}

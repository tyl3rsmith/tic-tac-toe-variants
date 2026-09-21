/* This class handles all piece state and behavior */

public class Piece {
    private char symbol;
    private Position position;

    public Piece(char symbol, Position position) {
        this.symbol = symbol;
        this.position = position;
    }

    public char getSymbol() { return symbol; }

    public Position getPosition() { return position; }
}
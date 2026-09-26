/* This class is a subclass of Game and implements the Tic Tac Toe game, including board setup,
player turns,role assignment, win detection, and draw detection. */

public class TicTacToe extends Game {
    // class scales with board size
    private static final int MIN_BOARD_SIZE = 3;
    private static final int MAX_BOARD_SIZE = 10;

    public TicTacToe(Controller controller) {
        super(controller);
    }

    public TicTacToe() {
        super();
    }

    @Override
    public void start() {
        controller.displayWelcomeTicTacToe();

        setupGame();

        boolean playing = true;

        while (playing) {
            playTurn(); // take a turn

            if (checkWin()) {  // check if someone won
                playing = handleWin();
            } else if (board.isFull()) { // or if the board is full
                playing = handleDraw();
            } else {
                turn++; // keep playing change turns
            }
        }
    }

    @Override
    protected char chooseSymbol(Player player) {
        return player == player1 ? 'X' : 'O';
    }

    private void setupGame() {
        setupBoardAndPlayers(MIN_BOARD_SIZE, MAX_BOARD_SIZE);
    }

    private char getPlayerSymbol(Player player) {
        return player == player1 ? 'X' : 'O';
    }

    private boolean handleWin() {
        Player winner = getCurrentPlayer();

        winner.addWin();

        return endGame(winner.getName() + " wins!");
    }

    private boolean handleDraw() {
        return endGame("It's a draw!");
    }

    @Override
    protected boolean checkWin() {
        return checkRows() || checkColumns() || checkMainDiagonal() || checkAntiDiagonal();
    }

    private boolean checkRows() {
        for (int row = 0; row < board.getRows(); row++) {
            // any empty rows you cannot win on
            if (board.getPiece(row, 0) == null) {
                continue;
            }

            // fix one symbol and compare the rest in the row to it
            char symbol = board.getPiece(row, 0).getSymbol();
            boolean won = true;

            for (int col = 1; col < board.getCols(); col++) {
                // we cant win on this row if its an empty spot or a symbol mismatch
                if (board.getPiece(row, col) == null || board.getPiece(row, col).getSymbol() != symbol) {
                    won = false;
                    break;
                }
            }

            if (won) {
                return true;
            }
        }

        return false;
    }

    private boolean checkColumns() {
        for (int col = 0; col < board.getCols(); col++) {
            // any empty cols you cannot win on
            if (board.getPiece(0, col) == null) {
                continue;
            }

            // fix one symbol and compare the rest in the col to it
            char symbol = board.getPiece(0, col).getSymbol();
            boolean won = true;

            for (int row = 1; row < board.getRows(); row++) {
                // we cant win on this col if its an empty spot or a symbol mismatch
                if (board.getPiece(row, col) == null || board.getPiece(row, col).getSymbol() != symbol) {
                    won = false;
                    break;
                }
            }

            if (won) {
                return true;
            }
        }

        return false;
    }

    private boolean checkMainDiagonal() {
        if (board.getPiece(0, 0) == null) {
            return false;
        }

        char symbol = board.getPiece(0, 0).getSymbol();

        for (int i = 1; i < board.getRows(); i++) {
            // empty spot or mismatch
            if (board.getPiece(i, i) == null || board.getPiece(i, i).getSymbol() != symbol) {
                return false;
            }
        }

        return true;
    }

    private boolean checkAntiDiagonal() {
        if (board.getPiece(0, board.getCols() - 1) == null) {
            return false;
        }

        char symbol = board.getPiece(0, board.getCols() - 1).getSymbol();

        for (int i = 1; i < board.getRows(); i++) {
            int col = board.getCols() - 1 - i;

            // empty spot or mismatch
            if (board.getPiece(i, col) == null || board.getPiece(i, col).getSymbol() != symbol) {
                return false;
            }
        }

        return true;
    }
}
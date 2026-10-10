/* This class is a subclass of Game and implements the Order and Chaos game, including board setup,
role assignment, player turns, Order win detection, and Chaos win detection. */

public class OrderAndChaos extends Game {
    private static final int MIN_BOARD_SIZE = 6;
    private static final int MAX_BOARD_SIZE = 6; // can make this larger game will still work
    private static final int NUM_PLAYERS = 2;


    public OrderAndChaos(Controller controller) { super(controller); }

    public OrderAndChaos() { super(); }

    // displays welcome message, sets up the game, and calls play method
    @Override
    public void start() {
        controller.displayWelcomeOrderAndChaos();
        setupGame();
        play(true);
    }

    // continues playing turns until the game is won
    @Override
    public void play(boolean playing) {
        while (playing) {
            playTurn(); // take a turn

            if (checkWin()) {
                playing = handleOrderWin(); // check if order won
            } else if (!orderCanStillWin()) {
                playing = handleChaosWin(); // check if there's no more moves left, chaos wins
            } else if (board.isFull()) {
                playing = handleChaosWinFullBoard(); // full board chaos wins
            } else {
                turn++; // play the next turn
            }
        }

    }

    // gets symbol choice from the player
    @Override
    protected char getPlayerSymbol(Player player) { return controller.chooseSymbol(); }

    // sets up the game board with user specified dimension and num players default is 2 with the roles order and chaos
    private void setupGame() { setupBoardAndPlayers(MIN_BOARD_SIZE, MAX_BOARD_SIZE, OrderAndChaos.NUM_PLAYERS); assignRoles(); }

    // assigns roles order and chaos to the 2 players
    private void assignRoles() {
        String role1 = controller.chooseRole(players[0]);
        String role2; // the other person automatically gets whatever player 1 didn't pick

        if (role1.equals("Order")) {
            role2 = "Chaos";
        } else {
            role2 = "Order";
        }

        players[0].setRole(role1);
        players[1].setRole(role2);

        controller.displayMessgae(players[1].getName() + ", you have the role " + role2);
    }

    private Player getOrderPlayer() { return players[0].getRole().equals("Order") ? players[0] : players[1]; }

    private Player getChaosPlayer() { return players[0].getRole().equals("Chaos") ? players[0] : players[1]; }

    // determines if there's any valid positions left for order to win if not chaos wins
    private boolean orderCanStillWin() { return horizontalCanStillWin() || verticalCanStillWin() || mainDiagonalCanStillWin() || antiDiagonalCanStillWin(); }

    // checks the 4 possible directions a player can win in order and chaos
    @Override
    protected boolean checkWin() { return checkRows() || checkColumns() || checkMainDiagonals() || checkAntiDiagonals(); }

    private boolean handleOrderWin() {
        Player orderPlayer = getOrderPlayer();
        orderPlayer.addWin();
        return endGame(orderPlayer.getName() + ", Order wins!");
    }

    private boolean handleChaosWin() {
        Player chaosPlayer = getChaosPlayer();
        chaosPlayer.addWin();
        return endGame(chaosPlayer.getName() + ", Chaos wins!");
    }

    private boolean handleChaosWinFullBoard() {
        Player chaosPlayer = getChaosPlayer();
        chaosPlayer.addWin();
        return endGame(chaosPlayer.getName() + ", Chaos wins no more valid moves!");
    }

    private boolean checkRows() {
        for (int row = 0; row < board.getRows(); row++) {
            // we need n - 1 consecutive pieces in a row
            // could be from indices 0 to n - 2 or 1 to n - 1
            // we have a offset to try both sequences
            for (int offset = 0; offset <= 1; offset++) {
                // empty spot
                if (board.getPiece(row, offset) == null) {
                    continue;
                }

                // use this to compare to other pieces in the same row
                char symbol = board.getPiece(row, offset).getSymbol();
                boolean won = true;

                for (int col = offset; col < offset + board.getCols() - 1; col++) {
                    // mismatch or empty spot
                    if (board.getPiece(row, col) == null || board.getPiece(row, col).getSymbol() != symbol) {
                        won = false;
                        break;
                    }
                }

                if (won) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean checkColumns() {
        for (int col = 0; col < board.getCols(); col++) {
            // we need n - 1 consecutive pieces in a col
            // could be from indices 0 to n - 2 or 1 to n - 1
            // we have a offset to try both sequences
            for (int offset = 0; offset <= 1; offset++) {
                // empty spot
                if (board.getPiece(offset, col) == null) {
                    continue;
                }

                // use this to compare to other pieces in the same col
                char symbol = board.getPiece(offset, col).getSymbol();
                boolean won = true;

                for (int row = offset; row < offset + board.getRows() - 1; row++) {
                    // mismatch or empty spot
                    if (board.getPiece(row, col) == null || board.getPiece(row, col).getSymbol() != symbol) {
                        won = false;
                        break;
                    }
                }

                if (won) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean checkMainDiagonals() {
        // we need n - 1 consecutive pieces on the main diag
        // the starting row and column can only be 0 or 1.
        for (int startRow = 0; startRow <= 1; startRow++) {
            for (int startCol = 0; startCol <= 1; startCol++) {
                // empty spot
                if (board.getPiece(startRow, startCol) == null) {
                    continue;
                }

                // use this to compare to other pieces in the same diag
                char symbol = board.getPiece(startRow, startCol).getSymbol();
                boolean won = true;

                for (int i = 0; i < board.getRows() - 1; i++) {
                    int row = startRow + i;
                    int col = startCol + i;

                    // mismatched piece or empty spot
                    if (board.getPiece(row, col) == null || board.getPiece(row, col).getSymbol() != symbol) {
                        won = false;
                        break;
                    }
                }

                if (won) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean checkAntiDiagonals() {
        // same as before we need n - 1 consecutive pieces on the anti diag
        // the starting row can be 0 or 1 while the starting column can be n - 1 or n - 2
        for (int startRow = 0; startRow <= 1; startRow++) {
            for (int startCol = board.getCols() - 2; startCol < board.getCols(); startCol++) {

                // empty spot
                if (board.getPiece(startRow, startCol) == null) {
                    continue;
                }

                // use this to compare to other pieces in the same diag
                char symbol = board.getPiece(startRow, startCol).getSymbol();
                boolean won = true;

                for (int i = 0; i < board.getRows() - 1; i++) {
                    int row = startRow + i;
                    int col = startCol - i;

                    // mismatch or empty spot
                    if (board.getPiece(row, col) == null || board.getPiece(row, col).getSymbol() != symbol) {
                        won = false;
                        break;
                    }
                }

                if (won) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean horizontalCanStillWin() {
        int rows = board.getRows();
        int cols = board.getCols();
        int winLength = rows - 1; // need n - 1 consecutive pieces to win.

        for (int row = 0; row < rows; row++) {
            // for each row check every possible starting column for a sequence of length n - 1
            for (int start = 0; start <= cols - winLength; start++) {

                // tracking whether the sequence is with X's or O's
                boolean hasX = false;
                boolean hasO = false;

                // check each position within the current sequence
                for (int col = start; col < start + winLength; col++) {
                    Piece piece = board.getPiece(row, col);

                    // ignore any empty spots
                    // want to see which symbols appear in the sequence
                    if (piece != null) {
                        if (piece.getSymbol() == 'X') {
                            hasX = true;
                        } else {
                            hasO = true;
                        }
                    }
                }

                // if the sequence does not contain both X and O,
                // it is still possible to fill it with one symbol and create a winning sequence.
                if (!hasX || !hasO) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean verticalCanStillWin() {
        int rows = board.getRows();
        int cols = board.getCols();
        int winLength = rows - 1;

        for (int col = 0; col < cols; col++) {
            // for each col check every possible starting row for a sequence of length n - 1
            for (int start = 0; start <= rows - winLength; start++) {

                // tracking whether the sequence is with X's or O's
                boolean hasX = false;
                boolean hasO = false;

                // check each position within the current sequence
                for (int row = start; row < start + winLength; row++) {
                    Piece piece = board.getPiece(row, col);

                    // ignore any empty spots
                    // want to see which symbols appear in the sequence
                    if (piece != null) {
                        if (piece.getSymbol() == 'X') {
                            hasX = true;
                        } else {
                            hasO = true;
                        }
                    }
                }

                // if the sequence does not contain both X and O,
                // it is still possible to fill it with one symbol and create a winning sequence.
                if (!hasX || !hasO) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean mainDiagonalCanStillWin() {
        int rows = board.getRows();
        int cols = board.getCols();
        int winLength = rows - 1;

        for (int startRow = 0; startRow <= rows - winLength; startRow++) {
            // check every starting row and column in the main diagonal for a sequence of length n - 1
            for (int startCol = 0; startCol <= cols - winLength; startCol++) {

                // tracking whether the sequence is with X's or O's
                boolean hasX = false;
                boolean hasO = false;

                // check each position within the current sequence
                for (int i = 0; i < winLength; i++) {
                    int row = startRow + i;
                    int col = startCol + i;

                    Piece piece = board.getPiece(row, col);

                    // ignore any empty spots
                    // want to see which symbols appear in the sequence
                    if (piece != null) {
                        if (piece.getSymbol() == 'X') {
                            hasX = true;
                        } else {
                            hasO = true;
                        }
                    }
                }

                // if the sequence does not contain both X and O,
                // it is still possible to fill it with one symbol and create a winning sequence.
                if (!hasX || !hasO) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean antiDiagonalCanStillWin() {
        int rows = board.getRows();
        int cols = board.getCols();
        int winLength = rows - 1;

        for (int startRow = 0; startRow <= rows - winLength; startRow++) {
            // check every starting row and column in the anti diagonal for a sequence of length n - 1
            for (int startCol = winLength - 1; startCol < cols; startCol++) {

                // tracking whether the sequence is with X's or O's
                boolean hasX = false;
                boolean hasO = false;

                // check each position within the current sequence
                for (int i = 0; i < winLength; i++) {
                    int row = startRow + i;
                    int col = startCol - i;

                    Piece piece = board.getPiece(row, col);

                    // ignore any empty spots
                    // want to see which symbols appear in the sequence
                    if (piece != null) {
                        if (piece.getSymbol() == 'X') {
                            hasX = true;
                        } else {
                            hasO = true;
                        }
                    }
                }

                // if the sequence does not contain both X and O,
                // it is still possible to fill it with one symbol and create a winning sequence.
                if (!hasX || !hasO) {
                    return true;
                }
            }
        }

        return false;
    }
}

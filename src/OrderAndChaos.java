/* This class is a subclass of Game and implements the Order and Chaos game, including board setup,
role assignment, player turns, Order win detection, and Chaos win detection. */

public class OrderAndChaos extends Game {
    private static final int MIN_BOARD_SIZE = 6;
    private static final int MAX_BOARD_SIZE = 6; // can make this larger game will still work

    public OrderAndChaos(Controller controller) {
        super(controller);
    }

    public OrderAndChaos() {
        super();
    }

    @Override
    public void start() {
        controller.displayWelcomeOrderAndChaos();

        setupGame();

        boolean playing = true;

        while (playing) {
            playTurn(); // take a turn

            if (checkWin()) {
                playing = handleOrderWin(); // check if order won
            } else if (!orderCanStillWin()) {
                playing = handleChaosWin(); // check if there's no more moves left, chaos wins
            } else if (board.isFull()) {
                playing = handleChaosFullBoard(); // full board chaos wins
            } else {
                turn++; // play the next turn
            }
        }
    }

    private void assignRoles() {
        String role1 = controller.chooseRole(player1);
        String role2; // the other person automatically gets whatever player 1 didn't pick

        if (role1.equals("Order")) {
            role2 = "Chaos";
        } else {
            role2 = "Order";
        }

        player1.setRole(role1);
        player2.setRole(role2);

        System.out.println(player2.getName() + ", you have the role " + role2);
    }

    private void setupGame() {
        setupBoardAndPlayers(MIN_BOARD_SIZE, MAX_BOARD_SIZE);
        assignRoles();
    }

    @Override
    protected char chooseSymbol(Player player) {
        return controller.chooseSymbol();
    }

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

    private boolean handleChaosFullBoard() {
        Player chaosPlayer = getChaosPlayer();

        chaosPlayer.addWin();

        return endGame(chaosPlayer.getName() + ", Chaos wins no more valid moves!");
    }

    private Player getOrderPlayer() {
        return player1.getRole().equals("Order") ? player1 : player2;
    }

    private Player getChaosPlayer() {
        return player1.getRole().equals("Chaos") ? player1 : player2;
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

    private boolean orderCanStillWin() {
        return horizontalCanStillWin() || verticalCanStillWin() || mainDiagonalCanStillWin() || antiDiagonalCanStillWin();
    }

    @Override
    protected boolean checkWin() {
        return checkRows() || checkColumns() || checkMainDiagonals() || checkAntiDiagonals();
    }

    /*
    public void testChaosBoard() {
        board = new Board(10, 10);

        char[][] testBoard = {
                {'X','O','O','X','X','O','O','X','O','O'},
                {'X','X','X','O','O','X','O','X','X','X'},
                {'O','X','O','X','X','X','X','X','O','O'},
                {'X','X','O','O','X','O','O','O','X','X'},
                {'X','O','X','O','X','X','X','O','X','X'},
                {'O','X','O','X','X','O','X','O','O','X'},
                {'X','O','O','X','O','X','O','O','X','X'},
                {'X','X','O','X','O','X','O','O','O','O'},
                {'O','X','O','X','X','O','X','X','X','O'},
                {'X','X','O','X','X','X','O','O','O','X'}
        };

        // Put each Piece onto the board
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Position position = new Position(row, col);
                Piece piece = new Piece(testBoard[row][col], position);

                board.updateBoard(position, piece);
            }
        }

        controller.displayBoard(board);

        System.out.println("checkWin(): " + checkWin());
        System.out.println("orderCanStillWin(): " + orderCanStillWin());

        if (!checkWin() && !orderCanStillWin()) {
            System.out.println("chaos won in 10x10");
        }
    }
    */
}

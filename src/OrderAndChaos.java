public class OrderAndChaos extends Game {
    private Board board;
    private Player player1;
    private Player player2;
    private static final int MIN_BOARD_SIZE = 6;
    private static final int MAX_BOARD_SIZE = 10;

    public OrderAndChaos(Controller controller) {
        super(controller);
    }

    public OrderAndChaos() {
        super();
    }

    @Override
    public void start() {
        controller.displayWelcomeOrderAndChaos();
        int dimension = controller.askBoardDimensions(MIN_BOARD_SIZE, MAX_BOARD_SIZE);

        board = new Board(dimension, dimension);

        player1 = new Player(controller.getPlayerName(1));
        player2 = new Player(controller.getPlayerName(2));

        String role1 = controller.chooseRole(player1);

        String role2;
        if (role1.equals("Order")) {
            role2 = "Chaos";
            System.out.println(player2.getName() + ", you have the role Chaos");
        } else {
            role2 = "Order";
            System.out.println(player2.getName() + ", you have the role Order");
        }

        player1.setRole(role1);
        player2.setRole(role2);

        boolean playing = true;

        while (playing) {
            controller.displayBoard(board);
            Player currentPlayer = getCurrentPlayer();

            controller.displayTurn(currentPlayer.getName());
            this.makeMove(currentPlayer, controller.chooseSymbol());

            if (checkWin()) {
                // order wins
                if (player1.getRole().equals("Order")) {
                    player1.setWins(player1.getWins() + 1);
                    playing = endGame(player1.getName() + ", Order wins!");
                } else {
                    player2.setWins(player2.getWins() + 1);
                    playing = endGame(player2.getName() + ", Order wins!");
                }

                continue;
            } else if (!orderCanStillWin()) {
                // chaos wins
                if (player1.getRole().equals("Chaos")) {
                    player1.setWins(player1.getWins() + 1);
                    playing = endGame(player1.getName() + ", Chaos wins!");
                } else {
                    player2.setWins(player2.getWins() + 1);
                    playing = endGame(player2.getName() + ", Chaos wins!");
                }
                continue;

            } else if (board.isFull()) {
                // chaos wins
                if (player1.getRole().equals("Chaos")) {
                    player1.setWins(player1.getWins() + 1);
                    playing = endGame(player1.getName() + ", Chaos wins no more valid moves!");
                } else {
                    player2.setWins(player2.getWins() + 1);
                    playing = endGame(player2.getName() + ", Chaos wins no more valid moves!");
                }
                continue;
            }

            turn++;

        }
    }

    private boolean orderCanStillWin() {

        // Check horizontal groups
        for (int row = 0; row < 6; row++) {
            for (int start = 0; start <= 1; start++) {

                boolean hasX = false;
                boolean hasO = false;

                for (int col = start; col < start + 5; col++) {
                    Piece piece = board.getPiece(row, col);

                    if (piece != null) {
                        if (piece.getSymbol() == 'X') {
                            hasX = true;
                        } else {
                            hasO = true;
                        }
                    }
                }

                // Order can still win if this group
                // does not contain both X and O
                if (!hasX || !hasO) {
                    return true;
                }
            }
        }

        // Check vertical groups
        for (int col = 0; col < 6; col++) {
            for (int start = 0; start <= 1; start++) {

                boolean hasX = false;
                boolean hasO = false;

                for (int row = start; row < start + 5; row++) {
                    Piece piece = board.getPiece(row, col);

                    if (piece != null) {
                        if (piece.getSymbol() == 'X') {
                            hasX = true;
                        } else {
                            hasO = true;
                        }
                    }
                }

                // Order can still win if this group
                // does not contain both X and O
                if (!hasX || !hasO) {
                    return true;
                }
            }
        }

        // Check diagonal (Top-Left to Bottom-Right)
        for (int startRow = 0; startRow <= 1; startRow++) {
            for (int startCol = 0; startCol <= 1; startCol++) {

                boolean hasX = false;
                boolean hasO = false;

                for (int i = 0; i < 5; i++) {

                    int row = startRow + i;
                    int col = startCol + i;

                    Piece piece = board.getPiece(row, col);

                    if (piece != null) {
                        if (piece.getSymbol() == 'X') {
                            hasX = true;
                        } else {
                            hasO = true;
                        }
                    }
                }

                // Order can still win if this group
                // does not contain both X and O
                if (!hasX || !hasO) {
                    return true;
                }
            }
        }

        // Check diagonal (Top-Right to Bottom-Left)
        for (int startRow = 0; startRow <= 1; startRow++) {
            for (int startCol = 4; startCol < 6; startCol++) {

                boolean hasX = false;
                boolean hasO = false;

                for (int i = 0; i < 5; i++) {

                    int row = startRow + i;
                    int col = startCol - i;

                    Piece piece = board.getPiece(row, col);

                    if (piece != null) {
                        if (piece.getSymbol() == 'X') {
                            hasX = true;
                        } else {
                            hasO = true;
                        }
                    }
                }

                // Order can still win if this group
                // does not contain both X and O
                if (!hasX || !hasO) {
                    return true;
                }
            }
        }

        // Every possible group of 5 contains both X and O
        return false;
    }

    @Override
    protected boolean endGame(String message) {
        controller.displayBoard(board);
        System.out.println(message);
        controller.displayWins(player1, player2);

        boolean playAgain = controller.playAgain();

        if (playAgain) {
            board.resetBoard();
            turn = 0;
        }

        return playAgain;
    }

    @Override
    protected boolean checkWin() {
        // need n - 1 matching pieces to win
        int rows = board.getRows();
        int cols = board.getCols();

        // Check rows
        for (int r = 0; r < rows; r++) {

            // two ways to get n - 1
            // columns 0 to n - 2 and columns 1 to n - 1
            for (int offset = 0; offset <= 1; offset++) {
                if (board.getPiece(r, offset) == null) {
                    continue;
                }

                // use this to compare against all other pieces in the same row
                char symbol = board.getPiece(r, offset).getSymbol();
                boolean won = true;

                for (int c = offset; c < offset + cols - 1; c++) {
                    // empty position or the symbol doesn't match
                    if (board.getPiece(r, c) == null || board.getPiece(r, c).getSymbol() != symbol) {
                        won = false;
                        break;
                    }
                }

                if (won) {
                    return true;
                }
            }
        }

        // Check each column
        for (int c = 0; c < cols; c++) {

            // two ways to get n - 1
            // rows 0 to n - 2 and rows 1 to n - 1
            for (int offset = 0; offset <= 1; offset++) {
                if (board.getPiece(offset, c) == null) {
                    continue;
                }

                // use this to compare against all other pieces in the same col
                char symbol = board.getPiece(offset, c).getSymbol();
                boolean won = true;

                for (int r = offset; r < offset + rows - 1; r++) {
                    // empty position or the symbol doesn't match
                    if (board.getPiece(r, c) == null || board.getPiece(r, c).getSymbol() != symbol) {
                        won = false;
                        break;
                    }

                }
                if (won) {
                    return true;
                }
            }
        }

        // Check diagonal (Top-Left to Bottom-Right)
        for (int startRow = 0; startRow <= 1; startRow++) {
            for (int startCol = 0; startCol <= 1; startCol++) {
                if (board.getPiece(startRow, startCol) == null) {
                    continue;
                }

                // use this to compare against all other pieces in the same diag
                char symbol = board.getPiece(startRow, startCol).getSymbol();
                boolean won = true;

                // Check the n - 1 diagonal positions
                for (int i = 0; i < rows - 1; i++) {

                    int r = startRow + i;
                    int c = startCol + i;

                    if (board.getPiece(r, c) == null || board.getPiece(r, c).getSymbol() != symbol) {
                        won = false;
                        break;
                    }
                }

                if (won) {
                    return true;
                }
            }
        }

        // Check diagonal (Top-Right to Bottom-Left)
        for (int startRow = 0; startRow <= 1; startRow++) {
            for (int startCol = cols - 2; startCol < cols; startCol++) {
                if (board.getPiece(startRow, startCol) == null) {
                    continue;
                }

                // use this to compare against all other pieces in the same diag
                char symbol = board.getPiece(startRow, startCol).getSymbol();
                boolean won = true;

                // Check the 5 diagonal positions
                for (int i = 0; i < rows - 1; i++) {

                    int r = startRow + i;
                    int c = startCol - i;

                    if (board.getPiece(r, c) == null || board.getPiece(r, c).getSymbol() != symbol) {
                        won = false;
                        break;
                    }
                }

                // If we found 5 matching pieces
                if (won) {
                    return true;
                }
            }
        }
        return false;
    }

    private Player getCurrentPlayer () {
        if (turn % 2 == 0) {
            return player1;
        } else {
            return player2;
        }
    }

    private void makeMove (Player currentPlayer,char symbol) {
        Position position = controller.getMove(currentPlayer);

        while (!board.isValidMove(position)) {
            System.out.println("Invalid move. Please choose another position.");
            position = controller.getMove(currentPlayer);
        }

        Piece piece = new Piece(symbol, position);
        board.updateBoard(position, piece);
    }
}

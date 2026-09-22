public class OrderAndChaos extends Game {
    private Board board;
    private Player player1;
    private Player player2;

    public OrderAndChaos(Controller controller) {
        super(controller);
        board = new Board(6, 6);
    }

    @Override
    public void start() {
        controller.displayWelcomeOrderAndChaos();

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
        // Order has 5 in a row horizontally

        // Check each row
        for (int row = 0; row < 6; row++) {

            // There are two possible groups of 5:
            // columns 0-4 and columns 1-5
            for (int start = 0; start <= 1; start++) {

                boolean failed = false;

                // Check the 5 positions
                for (int col = start; col < start + 5; col++) {

                    // Empty spot means this group does not count
                    if (board.getPiece(row, col) == null) {
                        failed = true;
                        break;
                    }

                    // Make sure all pieces match
                    if (col > start && board.getPiece(row, col).getSymbol() != board.getPiece(row, col - 1).getSymbol()) {
                        failed = true;
                        break;
                    }
                }

                // If we found 5 matching pieces
                if (!failed) {
                    return true;
                }
            }
        }

        // Order has 5 in a row vertically

        // Check each column
        for (int col = 0; col < 6; col++) {

            // There are two possible groups of 5:
            // rows 0-4 and rows 1-5
            for (int start = 0; start <= 1; start++) {

                boolean failed = false;

                // check the 5 positions
                for (int row = start; row < start + 5; row++) {

                    // Empty spot means this group does not count
                    if (board.getPiece(row, col) == null) {
                        failed = true;
                        break;
                    }

                    // Make sure all pieces match
                    if (row > start && board.getPiece(row, col).getSymbol() != board.getPiece(row - 1, col).getSymbol()) {
                        failed = true;
                        break;
                    }
                }

                // If we found 5 matching pieces
                if (!failed) {
                    return true;
                }
            }
        }

        // Order has 5 in a row diagonally

        // Check diagonal (Top-Left to Bottom-Right)
        for (int startRow = 0; startRow <= 1; startRow++) {

            for (int startCol = 0; startCol <= 1; startCol++) {

                boolean failed = false;

                // Check the 5 diagonal positions
                for (int i = 0; i < 5; i++) {

                    int row = startRow + i;
                    int col = startCol + i;

                    if (board.getPiece(row, col) == null) {
                        failed = true;
                        break;
                    }

                    // Make sure all pieces match
                    if (i > 0 && board.getPiece(row, col).getSymbol() != board.getPiece(row - 1, col - 1).getSymbol()) {
                        failed = true;
                        break;
                    }
                }

                // If we found 5 matching pieces
                if (!failed) {
                    return true;
                }
            }
        }

        // Check diagonal (Top-Right to Bottom-Left)

        for (int startRow = 0; startRow <= 1; startRow++) {

            for (int startCol = 4; startCol < 6; startCol++) {

                boolean failed = false;

                // Check the 5 diagonal positions
                for (int i = 0; i < 5; i++) {

                    int row = startRow + i;
                    int col = startCol - i;

                    if (board.getPiece(row, col) == null) {
                        failed = true;
                        break;
                    }

                    // Make sure all pieces match
                    if (i > 0 && board.getPiece(row, col).getSymbol() != board.getPiece(row - 1, col + 1).getSymbol()) {
                        failed = true;
                        break;
                    }
                }

                // If we found 5 matching pieces
                if (!failed) {
                    return true;
                }
            }
        }

        return false;
    }

    private Player getCurrentPlayer() {
        if (turn % 2 == 0) {
            return player1;
        } else {
            return player2;
        }
    }

    private void makeMove(Player currentPlayer, char symbol) {
        Position position = controller.getMove(currentPlayer);

        while (!board.isValidMove(position)) {
            System.out.println("Invalid move. Please choose another position.");
            position = controller.getMove(currentPlayer);
        }

        Piece piece = new Piece(symbol, position);
        board.updateBoard(position, piece);
    }

}
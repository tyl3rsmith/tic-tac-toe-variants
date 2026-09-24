public class TicTacToe extends Game {
    private Board board;
    private Player player1;
    private Player player2;
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
        int dimension = controller.askBoardDimensions(MIN_BOARD_SIZE, MAX_BOARD_SIZE);

        board = new Board(dimension, dimension);

        player1 = new Player(controller.getPlayerName(1));
        player2 = new Player(controller.getPlayerName(2));

        boolean playing = true;

        while (playing) {
            controller.displayBoard(board);
            Player currentPlayer = getCurrentPlayer();

            controller.displayTurn(currentPlayer.getName());
            char symbol = currentPlayer == player1 ? 'X' : 'O';

            makeMove(currentPlayer, symbol);

            if (checkWin()) {
                currentPlayer.setWins(currentPlayer.getWins() + 1);
                playing = endGame(currentPlayer.getName() + " wins!");
                continue;
            }

            if (board.isFull()) {
                playing = endGame("It's a draw!");
                continue;
            }

            turn++;
        }
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
        int rows = board.getRows();
        int cols = board.getCols();

        // Check rows
        for (int r = 0; r < rows; r++) {
            if (board.getPiece(r, 0) == null) {
                continue;
            }

            // use this to compare against all other pieces in the same row
            char symbol = board.getPiece(r, 0).getSymbol();
            boolean won = true;

            for (int c = 1; c < cols; c++) {
                // empty position or the symbol doesn't match
                if (board.getPiece(r, c) == null || board.getPiece(r, c).getSymbol() != symbol) {
                    won = false;
                    break;
                }
            }

            if (won) { return true; }
        }

        // Check columns
        for (int c = 0; c < cols; c++) {
            if (board.getPiece(0, c) == null) {
                continue;
            }

            // use this to compare against all other pieces in the same col
            char symbol = board.getPiece(0, c).getSymbol();
            boolean won = true;

            for (int r = 1; r < rows; r++) {
                // empty position or the symbol doesn't match
                if (board.getPiece(r, c) == null || board.getPiece(r, c).getSymbol() != symbol) {
                    won = false;
                    break;
                }
            }

            if (won) { return true; }
        }

        // Check top-left to bottom-right diagonal
        if (rows == cols && board.getPiece(0, 0) != null) {
            // use this to compare against all other pieces in the same diag
            char symbol = board.getPiece(0, 0).getSymbol();
            boolean won = true;

            for (int i = 1; i < rows; i++) {
                // empty position or the symbol doesn't match
                if (board.getPiece(i, i) == null || board.getPiece(i, i).getSymbol() != symbol) {
                    won = false;
                    break;
                }
            }

            if (won) { return true; }
        }

        // Check top-right to bottom-left diagonal
        if (rows == cols && board.getPiece(0, cols - 1) != null) {
            // use this to compare against all other pieces in the same diag
            char symbol = board.getPiece(0, cols - 1).getSymbol();
            boolean won = true;

            for (int i = 1; i < rows; i++) {
                int col = cols - 1 - i;

                // empty position or the symbol doesn't match
                if (board.getPiece(i, col) == null || board.getPiece(i, col).getSymbol() != symbol) {
                    won = false;
                    break;
                }
            }

            if (won) { return true; }
        }

        return false;
    }
}

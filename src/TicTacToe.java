public class TicTacToe extends Game {
    private Board board;
    private Player player1;
    private Player player2;

    public TicTacToe(Controller controller) {
        super(controller);
        board = new Board(3, 3);
    }

    @Override
    public void start() {
        controller.displayWelcomeTicTacToe();

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

    private boolean endGame(String message) {
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
        // 1. Check Rows
        for (int i = 0; i < 3; i++) {
            if (board.getPiece(i, 0) != null &&
                    board.getPiece(i, 1) != null &&
                    board.getPiece(i, 2) != null &&
                    board.getPiece(i, 0).getSymbol() == board.getPiece(i, 1).getSymbol() &&
                    board.getPiece(i, 1).getSymbol() == board.getPiece(i, 2).getSymbol()) {

                return true;
            }
        }

        // 2. Check Columns
        for (int i = 0; i < 3; i++) {
            if (board.getPiece(0, i) != null &&
                    board.getPiece(1, i) != null &&
                    board.getPiece(2, i) != null &&
                    board.getPiece(0, i).getSymbol() == board.getPiece(1, i).getSymbol() &&
                    board.getPiece(1, i).getSymbol() == board.getPiece(2, i).getSymbol()) {

                return true;
            }
        }

        // 3. Check Diagonal (Top-Left to Bottom-Right)
        if (board.getPiece(0, 0) != null &&
                board.getPiece(1, 1) != null &&
                board.getPiece(2, 2) != null &&
                board.getPiece(0, 0).getSymbol() == board.getPiece(1, 1).getSymbol() &&
                board.getPiece(1, 1).getSymbol() == board.getPiece(2, 2).getSymbol()) {

            return true;
        }

        // 4. Check Anti-Diagonal (Top-Right to Bottom-Left)
        if (board.getPiece(0, 2) != null &&
                board.getPiece(1, 1) != null &&
                board.getPiece(2, 0) != null &&
                board.getPiece(0, 2).getSymbol() == board.getPiece(1, 1).getSymbol() &&
                board.getPiece(1, 1).getSymbol() == board.getPiece(2, 0).getSymbol()) {

            return true;
        }

        return false;
    }
}

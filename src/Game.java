/*  This is an abstract superclass that holds the common functionality and structure shared by the different games. */

public abstract class Game {
    protected Controller controller;
    protected Board board;
    protected Player player1;
    protected Player player2;
    protected int turn;

    public Game(Controller controller) {
        this.controller = controller;
    }

    public Game() {
        this.controller = new Controller();
    }

    public abstract void start();

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

    protected abstract boolean checkWin();

    // assuming for now there's only two players could refactor this later to include a numPlayers argument
    protected Player getCurrentPlayer() {
        return turn % 2 == 0 ? player1 : player2;
    }

    protected void makeMove(Player currentPlayer, char symbol) {
        Position position = controller.getMove(currentPlayer, board);

        while (!board.isValidMove(position)) {
            System.out.println("Invalid move. Please choose another position.");
            position = controller.getMove(currentPlayer, board);
        }

        Piece piece = new Piece(symbol, position);
        board.updateBoard(position, piece);
    }

    // same thing here could refactor to include a numPlayers argument
    protected void setupBoardAndPlayers(int minSize, int maxSize) {
        int dimension = controller.askBoardDimensions(minSize, maxSize);

        board = new Board(dimension, dimension);

        player1 = new Player(controller.getPlayerName(1));
        player2 = new Player(controller.getPlayerName(2));
    }

    protected void playTurn() {
        controller.displayBoard(board);

        Player currentPlayer = getCurrentPlayer();

        controller.displayTurn(currentPlayer.getName());

        makeMove(currentPlayer, chooseSymbol(currentPlayer));
    }

    protected abstract char chooseSymbol(Player player);
}
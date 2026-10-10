/*  This is an abstract superclass that holds the common functionality and structure shared by the different games. */

public abstract class Game {
    protected Controller controller;
    protected Board board;
    protected Player[] players;
    protected int turn = 0;

    public Game(Controller controller) { this.controller = controller; }

    public Game() { this.controller = new Controller(); }

    public abstract void start();

    public abstract void play(boolean playing);

    protected abstract boolean checkWin();

    protected abstract char getPlayerSymbol(Player player);

    protected void setupBoard(int dimension) { board = new Board(dimension, dimension); }

    protected Player getCurrentPlayer() { return players[turn % players.length]; }

    // sets up the players and assigns their names
    protected void setupPlayers(int numPlayers) {
        if (numPlayers <= 0) {
            throw new IllegalArgumentException("There must be at least one player.");
        }

        players = new Player[numPlayers];

        for (int i = 0; i < numPlayers; i++) {
            players[i] = new Player(controller.getPlayerName(i + 1));
        }
    }

    // high level board and player setup method
    protected void setupBoardAndPlayers(int minSize, int maxSize, int numPlayers) {
        int dimension = controller.askBoardDimensions(minSize, maxSize);

        setupBoard(dimension);
        setupPlayers(numPlayers);
    }

    // displays the end of the game and prompts if the players want to play again
    protected boolean endGame(String message) {
        controller.displayBoard(board);
        controller.displayMessgae(message);
        controller.displayWins(players);

        boolean playAgain = controller.playAgain();

        if (playAgain) {
            board.resetBoard();
            turn = 0;
        }

        return playAgain;
    }

    // waits for the player to make a move at a certain position on the board with their piece
    protected void makeMove(Player currentPlayer, char symbol) {
        Position position = controller.getMove(currentPlayer, board);

        while (!board.isValidMove(position)) {
            controller.displayMessgae("Invalid move. Please choose another position.");
            position = controller.getMove(currentPlayer, board);
        }

        Piece piece = new Piece(symbol, position);
        board.updateBoard(position, piece);
    }

    // shows the current board and waits for the current player to make a move
    protected void playTurn() {
        controller.displayBoard(board);
        Player currentPlayer = getCurrentPlayer();
        controller.displayTurn(currentPlayer.getName());
        makeMove(currentPlayer, getPlayerSymbol(currentPlayer));
    }
}
/* This class implements the Super Tic Tac Toe game by extending the Game abstract class and using composition
to manage nine smaller Tic Tac Toe games. It tracks each sub-board's status, enforces board selection rules,
and determines the winner of the overall game */

public class SuperTicTacToe extends Game {
    private static final int NUM_PLAYERS = 2;
    private static final int BOARD_SIZE = 3;
    private static final int NO_FORCED_BOARD = -1; // represents if a player has the option to choose the board they want to play in

    // status markers for each small board on the big board
    private static final char OPEN = ' '; // no winner yet can still play in this board
    private static final char FULL = '-'; // filled with no winner

    private TicTacToe[][] cells;
    private char[][] status; // OPEN, FULL, or the winning symbol ('X' / 'O') of each sub board
    private int nextBoard;   // index 0-8 of the board the next player is sent to, or NO_FORCED_BOARD


    public SuperTicTacToe(Controller controller) {
        this.cells = new TicTacToe[BOARD_SIZE][BOARD_SIZE];
        this.controller = controller;
    }

    public SuperTicTacToe() { this(new Controller()); }

    // displays welcome message, sets up the game, and calls play method
    @Override
    public void start() {
        controller.displayWelcomeSuperTicTacToe();
        setupPlayers(NUM_PLAYERS);
        resetGame();
        play(true);
    }

    // continues playing turns until the game is won or there's a draw
    @Override
    public void play(boolean playing) {
        while (playing) {
            playSuperTurn();

            if (checkWin()) {
                playing = handleWin();
            } else if (!hasOpenBoard()) {
                playing = handleDraw();
            } else {
                turn++;
            }
        }
    }

    // creates and returns a 2D array of Board objects by extracting the board from each Tic Tac Toe cell (i.e. 9 3x3 tic tac toe boards)
    // this is needed so the controller can print the boards
    private Board[][] getBoards() {
        Board[][] boards = new Board[BOARD_SIZE][BOARD_SIZE];

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                boards[row][col] = cells[row][col].board;
            }
        }
        return boards;
    }

    //  gets symbol from the player
    @Override
    protected char getPlayerSymbol(Player player) { return player == players[0] ? 'X' : 'O'; }

    private int chooseSubBoard(Player player) {
        if (nextBoard != NO_FORCED_BOARD) { // if nextBoard != -1 then the player is forced to play in some sub board
            if (isOpen(nextBoard)) { // check if the forced sub board is actually open
                controller.displayMessage(player.getName() + " must play in board " + (char) ('A' + nextBoard) + ".");
                return nextBoard;
            }
            // if we get here the board was won or full so the player is allowed to choose
            // (char) ('A' + nextBoard) returns A -> I depending on nextBoard value 0 -> 8
            controller.displayMessage("Board " + (char) ('A' + nextBoard) + " is already won or full, so you may choose any open board.");
        }

        int choice = controller.getSubBoardIndex(player); // player has a choice of which sub board they want to play in
        while (!isOpen(choice)) { // make sure player isn't picking an invalid board
            controller.displayMessage("Board: " + choice + " is already won or full please choose a different one.");
            choice = controller.getSubBoardIndex(player);
        }

        return choice;
    }

    // Checks whether the sub board at the given index is open
    // Converts the board index (0–8) into its corresponding row/col in the 2D status array.
    private boolean isOpen(int boardIndex) {
        int statusRow = boardIndex / BOARD_SIZE;
        int statusCol = boardIndex % BOARD_SIZE;
        return status[statusRow][statusCol] == OPEN;
    }

    // checks if any sub board is open
    private boolean hasOpenBoard() {
        for (int i = 0; i < BOARD_SIZE * BOARD_SIZE; i++) { // board indices are (0-8)
            if (isOpen(i)) {
                return true;
            }
        }
        return false;
    }

    // Shows the current board and waits for the current player to make a move
    private void playSuperTurn() {
        Player currentPlayer = getCurrentPlayer();

        controller.displaySuperBoard(getBoards(), status);
        controller.displayTurn(currentPlayer.getName());

        int boardIndex = chooseSubBoard(currentPlayer);
        int boardRow = boardIndex / BOARD_SIZE;
        int boardCol = boardIndex % BOARD_SIZE;
        TicTacToe target = cells[boardRow][boardCol]; // sub tic tac toe board the player picked to play in

        controller.displayMessage("Playing in board " + (char) ('A' + boardIndex) + ":");
        controller.displayBoard(target.board);

        // keep asking until the player picks an empty spot
        Position move = controller.getMove(currentPlayer, target.board);
        while (!target.placeSinglePiece(move, currentPlayer)) {
            controller.displayMessage("That spot is already taken please choose another position.");
            move = controller.getMove(currentPlayer, target.board);
        }

        // update the status of the small board that was just played in
        if (target.isBoardWon()) { // current player won in the target sub board
            status[boardRow][boardCol] = getPlayerSymbol(currentPlayer);
            controller.displayMessage(currentPlayer.getName() + " has won board " + (char) ('A' + boardIndex) + "!");
        } else if (target.isBoardFull()) { // target sub board is now full with no winner
            status[boardRow][boardCol] = FULL;
        }

        // the spot inside the small board decides where the opponent plays next
        // need to convert to super board index (0-8)
        nextBoard = move.getRow() * BOARD_SIZE + move.getCol();
    }

    // reset sub boards and their status'
    // reset turn to 0 and the next board
    private void resetGame() {
        cells = new TicTacToe[BOARD_SIZE][BOARD_SIZE];
        status = new char[BOARD_SIZE][BOARD_SIZE];

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                TicTacToe cell = new TicTacToe(controller);
                cell.setupBoard(3);
                cell.players = players;

                cells[row][col] = cell;
                status[row][col] = OPEN;
            }
        }

        nextBoard = NO_FORCED_BOARD;
        turn = 0;
    }

    // the big board is won when one player wins 3 sub boards in a row
    @Override
    protected boolean checkWin() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (lineWon(i, 0, i, 1, i, 2) || lineWon(0, i, 1, i, 2, i)) { // check rows and cols
                return true;
            }
        }

        return lineWon(0, 0, 1, 1, 2, 2) || lineWon(0, 2, 1, 1, 2, 0); // last case check both diagonals
    }

    // checks if all symbols in one line match
    private boolean lineWon(int r1, int c1, int r2, int c2, int r3, int c3) {
        char symbol = status[r1][c1]; // symbol to compare against

        return (symbol == 'X' || symbol == 'O') && symbol == status[r2][c2] && symbol == status[r3][c3];
    }

    private boolean handleWin() {
        Player winner = getCurrentPlayer();
        winner.addWin();
        return endSuperGame(winner.getName() + " wins Super Tic Tac Toe!");
    }

    private boolean handleDraw() {
        return endSuperGame("It's a draw!");
    }

    // separate version of end game for super tic tac toe since we have to reset all sub boards and their status'
    private boolean endSuperGame(String message) {
        controller.displaySuperBoard(getBoards(), status);
        controller.displayMessage(message);
        controller.displayWins(players);

        boolean playAgain = controller.playAgain();

        if (playAgain) {
            resetGame();
        }

        return playAgain;
    }
}

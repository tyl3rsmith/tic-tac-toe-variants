/* This is the main class that handles all user interaction. It's responsible for displaying menus,
game instructions, the board, player information, and prompts the users for game choices,
names, moves, etc. It doesn't handle any state just simply handles I/O. */

import java.util.Scanner;

public class Controller {
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        int gameChoice = getChoice();

        while (gameChoice != 0) { // 0 means player wants to exit
            Game game = getGame(gameChoice);
            game.start();

            gameChoice = switchGame();
        }

        this.displayBye();
    }

    private int getChoice() {
        System.out.println("===================================");
        System.out.println("      Welcome to Board Games!");
        System.out.println("===================================");
        System.out.println("Select a game to get started:");
        System.out.println("1. Tic Tac Toe");
        System.out.println("2. Order and Chaos");
        System.out.println("3. Super Tic Tac Toe");


        int choice = scanner.nextInt();
        while (choice != 1 && choice != 2 && choice != 3) {
            System.out.println("Please enter 1, 2, or 3:");
            System.out.println("1. Tic Tac Toe");
            System.out.println("2. Order and Chaos");
            System.out.println("3. Super Tic Tac Toe");
            choice = scanner.nextInt();
        }
        scanner.nextLine();
        return choice;
    }

    private Game getGame(int choice) {
        Game game;
        if (choice == 1) { // 1 -> tic-tac-toe
            game = new TicTacToe(this);
        } else if (choice == 2) { // 2 -> Order and Chaos
            game = new OrderAndChaos(this);
        } else { // 3 -> Super Tic Tac Toe
            game = new SuperTicTacToe(this);
        }
        return game;
    }

    public void displayWelcomeSuperTicTacToe() {
        System.out.println("===================================");
        System.out.println("   Welcome to Super Tic Tac Toe!");
        System.out.println("===================================");
        System.out.println("Rules:");
        System.out.println("1. The game consists of a larger 3x3 board containing nine smaller 3x3 tic-tac-toe boards.");
        System.out.println("2. Player 1 uses X and Player 2 uses O.");
        System.out.println("3. Player 1 may start in any of the nine small boards (A to I). After that, each player must play in the small board matching the position of the previous move inside its small board (e.g., a move in the top-left square sends the opponent to board A).");
        System.out.println("4. If a move wins a small board by normal tic-tac-toe rules, the entire small board is marked as won by that player on the larger board.");
        System.out.println("5. Once a small board is won or completely filled, no more moves can be played in it.");
        System.out.println("6. If a player is sent to a board that is already won or full, they may choose any other valid, open board to play in.");
        System.out.println("7. The game ends immediately when a player wins three small boards in a row, column, or diagonal on the larger board.");
        System.out.println("8. If there are no playable small boards remaining and no player has won the larger board, the game is a draw.");
    }

    public void displayWelcomeTicTacToe() {
        System.out.println("===================================");
        System.out.println("      Welcome to Tic Tac Toe!");
        System.out.println("===================================");
        System.out.println("Rules:");
        System.out.println("1. The board can be between 3x3 and 10x10.");
        System.out.println("2. Player 1 uses X and Player 2 uses O.");
        System.out.println("3. Players take turns placing their piece in an empty position.");
        System.out.println("4. Player 1 (X) goes first.");
        System.out.println("5. A player wins by filling an entire row, column, or diagonal with their pieces.");
        System.out.println("6. The game ends immediately when a player wins.");
        System.out.println("7. If the board is completely filled without a winner, the game is a draw.");
    }

    public int askBoardDimensions(int min, int max) {
        System.out.println("Please specify the board size between " + min + " and " + max + " (e.g. enter 3 for a 3x3 board): ");

        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Enter an integer: ");
            scanner.next();
        }

        int boardSize = scanner.nextInt();

        while (boardSize < min || boardSize > max) {
            System.out.println("Invalid board size. Please enter a size between " + min + " and " + max);

            boardSize = scanner.nextInt();
        }
        scanner.nextLine();

        return boardSize;
    }

    public void displayWelcomeOrderAndChaos() {
        System.out.println("===================================");
        System.out.println("    Welcome to Order and Chaos!");
        System.out.println("===================================");
        System.out.println("Rules:");
        System.out.println("1. The board is only 6x6");
        System.out.println("2. Two players take turns placing either X or O.");
        System.out.println("3. Players are assigned the roles Order and Chaos.");
        System.out.println("4. Order wins by getting N-1 matching pieces in a row.");
        System.out.println("5. A row can be horizontal, vertical, or diagonal.");
        System.out.println("6. Chaos wins if Order can no longer make N-1 matching pieces in a row.");
        System.out.println("7. Chaos also wins if the board becomes completely full.");
    }

    public String getPlayerName(int playerNumber) {
        System.out.println("Player " + playerNumber + ", please enter your name:");

        String playerName = scanner.nextLine().trim();

        while (playerName.isEmpty()) {
            System.out.println("Name cannot be empty. Please enter your name:");
            playerName = scanner.nextLine().trim();
        }

        return playerName;
    }

    public void displayBoard(Board board) {
        System.out.print("  ");

        for (int col = 0; col < board.getCols(); col++) {
            System.out.print(col + "   ");
        }

        System.out.println();

        for (int row = 0; row < board.getRows(); row++) {
            System.out.print(row + " ");

            for (int col = 0; col < board.getCols(); col++) {
                Piece piece = board.getPiece(row, col);

                if (piece == null) {
                    System.out.print(" ");
                } else {
                    System.out.print(piece.getSymbol());
                }

                if (col < board.getCols() - 1) {
                    System.out.print(" | ");
                }
            }

            System.out.println();

            if (row < board.getRows() - 1) {
                System.out.print("  ");

                for (int col = 0; col < board.getCols(); col++) {
                    System.out.print("---");

                    if (col < board.getCols() - 1) {
                        System.out.print("+");
                    }
                }

                System.out.println();
            }
        }
    }

    // asks for a small board A-I and returns its index 0-8 (A = 0, B = 1, ... I = 8)
    public int getSubBoardIndex(Player currentPlayer) {
        System.out.println(currentPlayer.getName() + ": Please select the board A to I that you want to play in:");

        String boardLabel = scanner.nextLine().trim().toUpperCase();
        while (!boardLabel.matches("[A-I]")) {
            System.out.println("Invalid input. Please enter a single character A to I");
            boardLabel = scanner.nextLine().trim().toUpperCase();
        }

        return boardLabel.charAt(0) - 'A';
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    // draws the nine small boards in a 3x3 layout. winners[r][c] is 'X', 'O', '-' (full, no winner) or ' ' (open)
    public void displaySuperBoard(Board[][] boards, char[][] winners) {
        System.out.println();

        for (int bigRow = 0; bigRow < 3; bigRow++) {
            StringBuilder header = new StringBuilder();

            for (int bigCol = 0; bigCol < 3; bigCol++) {
                char label = (char) ('A' + bigRow * 3 + bigCol);
                char state = winners[bigRow][bigCol];
                String tag = String.valueOf(label);

                if (state == '-') {
                    tag += " (full)";
                } else if (state != ' ') {
                    tag += " (" + state + " won)";
                }

                header.append(String.format("%-14s", tag));
            }

            System.out.println(header);

            for (int row = 0; row < 3; row++) {
                StringBuilder line = new StringBuilder();

                for (int bigCol = 0; bigCol < 3; bigCol++) {
                    for (int col = 0; col < 3; col++) {
                        Piece piece = boards[bigRow][bigCol].getPiece(row, col);
                        line.append(piece == null ? ' ' : piece.getSymbol());

                        if (col < 2) {
                            line.append(" | ");
                        }
                    }

                    line.append("     ");
                }

                System.out.println(line);

                if (row < 2) {
                    System.out.println("--+---+--     --+---+--     --+---+--");
                }
            }

            System.out.println();
        }
    }

    public Position getMove(Player currentPlayer, Board board) {
        System.out.println(currentPlayer.getName() + ": enter row and col numbers separated by a space (e.g., 0 2):");

        int row;
        int col;

        while (true) {
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter two integers.");
                scanner.next();
            }

            row = scanner.nextInt();

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter two integers.");
                scanner.next();
            }

            col = scanner.nextInt();
            scanner.nextLine();

            // cant have negative vals for row/col
            if (row < 0 || row >= board.getRows() || col < 0 || col >= board.getCols()) {

                System.out.println("Invalid position. Row and column must be between 0 and " + (board.getRows() - 1));
                System.out.println("Please enter another position:");
                continue;
            }

            return new Position(row, col);
        }
    }

    public void displayWins(Player[] players) {
        System.out.println("===================================");
        System.out.println("               Wins");
        System.out.println("===================================");

        for (Player player: players) {
            System.out.println(player.getName() + ": " + player.getWins());
        }
    }

    public boolean playAgain() {
        System.out.println("Would you like to play again? Enter (y/n)");

        String input = scanner.nextLine().trim().toLowerCase();

        while (!input.equals("y") && !input.equals("n")) {
            System.out.println("Please enter (y/n)");
            input = scanner.nextLine().trim().toLowerCase();
        }

        return input.equals("y");
    }

    private int switchGame() {
        System.out.println("===================================");
        System.out.println("Would you like to play another game?");
        System.out.println("===================================");
        System.out.println("0. No thank you!");
        System.out.println("1. Tic Tac Toe");
        System.out.println("2. Order and Chaos");
        System.out.println("3. Super Tic Tac Toe");

        int choice = scanner.nextInt();
        while (choice < 0 || choice > 3) {
            System.out.println("Please enter 0, 1, 2, or 3:");
            System.out.println("0. No thank you!");
            System.out.println("1. Tic Tac Toe");
            System.out.println("2. Order and Chaos");
            System.out.println("3. Super Tic Tac Toe");
            choice = scanner.nextInt();
        }
        scanner.nextLine();
        return choice;
    }

    private void displayBye() {
        System.out.println("Thanks for playing!");
    }

    public void displayTurn(String name) {
        System.out.println(name + "'s turn.");
    }

    public char chooseSymbol() {
        System.out.println("Select a symbol: X or O");

        String input = scanner.nextLine().trim().toUpperCase();

        while (!input.equals("X") && !input.equals("O")) {
            System.out.println("Please select X or O:");
            input = scanner.nextLine().trim().toUpperCase();
        }

        return input.charAt(0);
    }

    public String chooseRole(Player player) {
        System.out.println(player.getName() + ", choose your role:");
        System.out.println("1. Order");
        System.out.println("2. Chaos");

        int choice = scanner.nextInt();
        while (choice != 1 && choice != 2) {
            System.out.println("Please enter 1 or 2:");
            choice = scanner.nextInt();
        }
        scanner.nextLine();

        if (choice == 1) {
            System.out.println(player.getName() + ", you have the role Order");
            return "Order";
        } else {
            System.out.println(player.getName() + ", you have the role Chaos");
            return "Chaos";
        }
    }

    public void displayMessgae(String msg) { System.out.println(msg); }
}
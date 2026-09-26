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

        int choice = scanner.nextInt();
        while (choice != 1 && choice != 2) {
            System.out.println("Please enter 1 or 2:");
            System.out.println("1. Tic Tac Toe");
            System.out.println("2. Order and Chaos");
            choice = scanner.nextInt();
        }
        scanner.nextLine();
        return choice;
    }

    private Game getGame(int choice) {
        Game game;
        if (choice == 1) { // 1 -> tic-tac-toe
            game = new TicTacToe(this);
        } else { // 2 -> Order and Chaos
            game = new OrderAndChaos(this);
            // game.testChaosBoard();
        }
        return game;
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
        System.out.println("1. The board can be between 6x6 and 10x10.");
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

    public void displayWins(Player player1, Player player2) {
        System.out.println("===================================");
        System.out.println("               Wins");
        System.out.println("===================================");
        System.out.println(player1.getName() + ": " + player1.getWins());
        System.out.println(player2.getName() + ": " + player2.getWins());
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

        int choice = scanner.nextInt();
        while (choice != 0 && choice != 1 && choice != 2) {
            System.out.println("Please enter 0, 1, or 2:");
            System.out.println("0. No thank you!");
            System.out.println("1. Tic Tac Toe");
            System.out.println("2. Order and Chaos");
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
}
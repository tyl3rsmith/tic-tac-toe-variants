/* This class handles all user interactions */

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
        if (choice == 1) { // 1 -> Tic Tac Toe
            game = new TicTacToe(this);
        } else { // 2 -> Order and Chaos
            game = new OrderAndChaos(this);
        }
        return game;
    }

    public void displayWelcomeTicTacToe() {
        System.out.println("===================================");
        System.out.println("      Welcome to Tic Tac Toe!");
        System.out.println("===================================");
        System.out.println("Rules:");
        System.out.println("1. Two players take turns placing their piece.");
        System.out.println("2. Player 1 uses X and Player 2 uses O.");
        System.out.println("3. Get three of your pieces in a row to win.");
        System.out.println("4. A row can be horizontal, vertical, or diagonal.");
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
        System.out.println("1. Two players (Order and Chaos) take turns placing either X or O.");
        System.out.println("2. Order wins by getting five matching pieces in a row.");
        System.out.println("3. Chaos wins if the board is completely filled without Order getting five in a row.");
        System.out.println("4. A row can be horizontal, vertical, or diagonal.");
    }

    public String getPlayerName(int playerNumber) {
        System.out.println("Player " + playerNumber + ", please enter your name:");
        String playerName = scanner.nextLine();

        return playerName;
    }

    public void displayBoard(Board board) {
        Piece[][] pieces = board.getBoard();

        System.out.print("  ");
        for (int col = 0; col < pieces[0].length; col++) {
            System.out.print(col + "   ");
        }
        System.out.println();

        for (int row = 0; row < pieces.length; row++) {
            System.out.print(row + " ");

            for (int col = 0; col < pieces[row].length; col++) {
                if (pieces[row][col] == null) {
                    System.out.print(" ");
                } else {
                    System.out.print(pieces[row][col].getSymbol());
                }

                if (col < pieces[row].length - 1) {
                    System.out.print(" | ");
                }
            }

            System.out.println();

            if (row < pieces.length - 1) {
                System.out.print("  ");

                for (int col = 0; col < pieces[row].length; col++) {
                    System.out.print("---");

                    if (col < pieces[row].length - 1) {
                        System.out.print("+");
                    }
                }

                System.out.println();
            }
        }
    }

    public Position getMove(Player currentPlayer) {
        System.out.println(currentPlayer.getName() + ": enter row and col numbers separated by a space (e.g., 0 2):");
        int row = scanner.nextInt();
        int col = scanner.nextInt();
        scanner.nextLine();

        return new Position(row, col);
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
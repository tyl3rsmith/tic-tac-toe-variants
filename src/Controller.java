/* This class handles all user interactions */

import java.util.Scanner;

public class Controller {
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        int choice = getChoice();

        while (choice != 0) {
            Game game = getGame(choice);
            game.start();

            choice = switchGame();
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
        scanner.nextLine(); // consume leftover newline
        return choice;
    }

    private Game getGame(int choice) {
        Game game;
        if (choice == 1) {
            game = new TicTacToe(this);
        } else {
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
        return scanner.nextLine();
    }

    public void displayBoard(Board board) {
        Piece[][] pieces = board.getBoard();

        System.out.println("  0   1   2");

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
                System.out.println("  --+---+--");
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
        scanner.nextLine(); // consume leftover newline
        return choice;
    }

    private void displayBye() {
        System.out.println("Thanks for playing!");
    }

    public void displayTurn(String name) {
        System.out.println(name + "'s turn.");
    }
}
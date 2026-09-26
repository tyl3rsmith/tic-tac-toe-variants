# CS611-Assignment 2
## Tic-Tac-Toe and other Variants

Tyler Smith
tylerw1@bu.edu
U25811409

## Files

___
* **Main.java:** This class is the entry point for the program it creates the `Controller` and starts the game.

* **Controller.java:** This is the main class that handles all user interaction. It's responsible for displaying menus, game instructions, the board, player information, and prompts the users for game choices, names, moves, etc. It doesn't handle any state just simply handles I/O.

* **Game.java:** This is an abstract superclass that holds the common functionality and structure shared by the different games.

* **TicTacToe.java:** This class is a subclass of `Game` and implements the Tic Tac Toe game, including board setup, player turns, role assignment, win detection, and draw detection.

* **OrderAndChaos.java:** This class is a subclass of `Game` and implements the Order and Chaos game, including board setup, role assignment, player turns, Order win detection, and Chaos win detection.

* **Board.java:** This class represents the game board and manages the pieces placed on the board. It's responsible for updating, resetting, validating, and checking whether the board is full.

* **Piece.java:** This class represents a piece placed on the board. It stores the piece's symbol and position.

* **Player.java:** This class represents a player and stores information such as their name, role, and number of wins.

* **Position.java:** This class represents a row and column position. It is used by `Piece` and `Board` to track positions of pieces on the board.

## Notes

___
* The program is highly modular and extensible, with each class having a specific responsibility.

* Common game functionality is implemented through inheritance and reusable methods to reduce duplicated code between the two games.

* A significant amount of input validation was implemented to ensure that user input is handled safely and that invalid values do not cause unexpected behavior for the program.

* Tic Tac Toe supports boards from **3x3 through 10x10**, while Order and Chaos currently uses a **6x6 board**. However, the board size is controlled by class variables, so the supported Order and Chaos board size can easily scaled up or down. The rules are order just simply needs **N-1 consecutive pieces to win** where N is the board dimension.

* The win conditions are scalable and can be applied to boards of different sizes rather than relying on hard-coded board dimensions.

* Order and Chaos has additional win logic for Chaos. The program determines whether it is impossible for Order to win by checking all possible winning groups and determining whether each group can no longer contain the required matching pieces.

* All input and output is abstracted away inside the `Controller` class. This keeps user interaction separate from the game logic and makes the game classes more readable and easier to understand.


## How to compile and run

___
1. Navigate to the directory containing the .java files after unzipping the project
2. Compile all Java source files into the bin directory:
   javac *.java -d bin
3. Run the program:
   java -cp ./bin Main

## Input/Output Example

___
```text
Output:
===================================
      Welcome to Board Games!
===================================
Select a game to get started:
1. Tic Tac Toe
2. Order and Chaos

Input:
1

Output:
===================================
      Welcome to Tic Tac Toe!
===================================
Rules:
1. The board can be between 3x3 and 10x10.
2. Player 1 uses X and Player 2 uses O.
3. Players take turns placing their piece in an empty position.
4. Player 1 (X) goes first.
5. A player wins by filling an entire row, column, or diagonal with their pieces.
6. The game ends immediately when a player wins.
7. If the board is completely filled without a winner, the game is a draw.

Please specify the board size between 3 and 10 (e.g. enter 3 for a 3x3 board):

Input:
3

Output:
Player 1, please enter your name:

Input:
Tyler

Output:
Player 2, please enter your name:

Input:
John

Output:

  0   1   2
0   |   |  
  --+---+---
1   |   |  
  --+---+---
2   |   |  

Tyler's turn.
Tyler: enter row and col numbers separated by a space (e.g. 0 2):

Input:
0 0

Output:

  0   1   2
0 X |   |  
  --+---+---
1   |   |  
  --+---+---
2   |   |  

Johns's turn.
John: enter row and col numbers separated by a space (e.g. 0 2):

Input:
1 0

Output:

  0   1   2
0 X |   |  
  --+---+---
1 O |   |  
  --+---+---
2   |   |  

Tyler's turn.
Tyler: enter row and col numbers separated by a space (e.g. 0 2):

Input:
0 1

Output:

  0   1   2
0 X | X |  
  --+---+---
1 O |   |  
  --+---+---
2   |   |  


Johns's turn.
John: enter row and col numbers separated by a space (e.g. 0 2):

Input:
0 0

Output:
Invalid move. Please choose another position.
John: enter row and col numbers separated by a space (e.g. 0 2):

Input:
1 1

Output:

  0   1   2
0 X | X |  
  --+---+---
1 O | O |  
  --+---+---
2   |   |  


Tyler's turn.
Tyler: enter row and col numbers separated by a space (e.g. 0 2):

Input:
0 -2

Output:
Invalid position. Row and column must be between 0 and 2
Please enter another position:

Input:
0 2

Output:

  0   1   2
0 X | X | X
  --+---+---
1 O | O |  
  --+---+---
2   |   |  

Tyler wins!

Output:
===================================
               Wins
===================================
Tyler: 1
John: 0

Would you like to play again? Enter (y/n)

Input:
n

Output:
===================================
Would you like to play another game?
===================================
0. No thank you!
1. Tic Tac Toe
2. Order and Chaos

Input:
2

Output:
===================================
    Welcome to Order and Chaos!
===================================
Rules:
1. The board can be between 6x6 and 10x10.
2. Two players take turns placing either X or O.
3. Players are assigned the roles Order and Chaos.
4. Order wins by getting N-1 matching pieces in a row.
5. A row can be horizontal, vertical, or diagonal.
6. Chaos wins if Order can no longer make N-1 matching pieces in a row.
7. Chaos also wins if the board becomes completely full.

Please specify the board size between 6 and 6 (e.g. enter 3 for a 3x3 board):

Input:
6

Output:
Player 1, please enter your name:

Input:
Alice

Output:
Player 2, please enter your name:

Input:
Bob

Output:
Alice, choose your role:
1. Order
2. Chaos

Input:
1

Output:
Alice, you have the role Order
Bob, you have the role Chaos

  0   1   2   3   4   5
0   |   |   |   |   |  
  --+---+---+---+---+---
1   |   |   |   |   |  
  --+---+---+---+---+---
2   |   |   |   |   |  
  --+---+---+---+---+---
3   |   |   |   |   |  
  --+---+---+---+---+---
4   |   |   |   |   |  
  --+---+---+---+---+---
5   |   |   |   |   |  

Alice's turn.
Select a symbol: X or O

Input:
X

Output:
Alice: enter row and col numbers separated by a space (e.g. 0 2):

Input:
0 0

Output:

  0   1   2   3   4   5
0 X |   |   |   |   |  
  --+---+---+---+---+---
1   |   |   |   |   |  
  --+---+---+---+---+---
2   |   |   |   |   |  
  --+---+---+---+---+---
3   |   |   |   |   |  
  --+---+---+---+---+---
4   |   |   |   |   |  
  --+---+---+---+---+---
5   |   |   |   |   |  

Bob's turn.
Select a symbol: X or O

Input:
O

Output:
Bob: enter row and col numbers separated by a space (e.g. 0 2):

Input:
5 5

Output:

  0   1   2   3   4   5
0 X |   |   |   |   |  
  --+---+---+---+---+---
1   |   |   |   |   |  
  --+---+---+---+---+---
2   |   |   |   |   |  
  --+---+---+---+---+---
3   |   |   |   |   |  
  --+---+---+---+---+---
4   |   |   |   |   |  
  --+---+---+---+---+---
5   |   |   |   |   | O


The players continue taking turns placing X or O until Order wins or Chaos wins.
Fast Forwarding

Output:

  0   1   2   3   4   5
0 X | X | X | O |   |  
  --+---+---+---+---+---
1   | X | O | X |   |  
  --+---+---+---+---+---
2 O | O | X | O | X | X
  --+---+---+---+---+---
3   | X | X | O | O | X 
  --+---+---+---+---+---
4 X | O | X | O | O | O
  --+---+---+---+---+---
5 O | X | O | O | O | O

Alice's turn.
Select a symbol: X or O

Input:
X

Output:
Alice: enter row and col numbers separated by a space (e.g. 0 2):

Input:
0 4

Output:

  0   1   2   3   4   5
0 X | X | X | O | X |  
  --+---+---+---+---+---
1   | X | O | X |   |  
  --+---+---+---+---+---
2 O | O | X | O | X | X
  --+---+---+---+---+---
3   | X | X | O | O | X 
  --+---+---+---+---+---
4 X | O | X | O | O | O
  --+---+---+---+---+---
5 O | X | O | O | O | O

Output:
Alice, Order wins!
===================================
               Wins
===================================
Alice: 1
Bob: 0
Would you like to play again? Enter (y/n)

Input:
n

Output:
===================================
Would you like to play another game?
===================================
0. No thank you!
1. Tic Tac Toe
2. Order and Chaos

Input:
0

Output:
Thanks for playing!
```
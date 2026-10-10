# CS611-Assignment 2
## Tic-Tac-Toe and other Variants

Tyler Smith
tylerw1@bu.edu
U25811409

## Files

___
* **Main.java:** This class is the entry point of the program. It creates the `Controller` and starts the application.

* **Controller.java:** This class handles user interaction and input/output. It displays menus, game instructions, boards, player information, and prompts for game choices, player names, and moves. It does not manage the game state or game logic.

* **Game.java:** This abstract superclass defines the common structure and functionality shared by the different games, such as player setup and game flow.

* **TicTacToe.java:** This class extends `Game` and implements standard Tic Tac Toe, including board setup, player turns, role assignment, win detection, and draw detection.

* **OrderAndChaos.java:** This class extends `Game` and implements Order and Chaos, including board setup, role assignment, player turns, Order win detection, and Chaos win detection.

* **SuperTicTacToe.java:** This class extends `Game` and implements Super Tic Tac Toe using composition to manage nine smaller Tic Tac Toe boards. It tracks each sub-board's status, enforces board selection rules, and determines the winner of the overall game.

* **Board.java:** This class represents a game board and manages the pieces placed on it. It provides functionality for placing pieces, resetting the board, validating positions, and checking whether the board is full.

* **BoardCell.java:** This class represents a cell within a board. It stores the cell's position and manages the piece placed in that cell.

* **Piece.java:** This class represents a game piece. It stores the piece's symbol and position.

* **Player.java:** This class represents a player and stores information such as their name, role, and number of wins.

* **Position.java:** This class represents a position on a board using a row and column. It is used by `Piece` and `Board` to identify and track positions.


## Notes

___
* The program is highly modular and extensible, with each class having a specific responsibility through the use of encapsulation.

* Common game functionality is implemented through inheritance and reusable methods to reduce duplicated code between the two games.

* Polymorphism is supported through the Game superclass, allowing different game implementations to share a common structure while providing their own rules and behavior.

* A significant amount of input validation was implemented to ensure that user input is handled safely and that invalid values do not cause unexpected behavior for the program.

* Tic Tac Toe supports boards from **3x3 through 10x10**, while Order and Chaos currently uses a **6x6 board**. However, the board size is controlled by class variables, so the supported Order and Chaos board size can easily scaled up or down. The rules are order just simply needs **N-1 consecutive pieces to win** where N is the board dimension.

* The win conditions are scalable and can be applied to boards of different sizes rather than relying on hard-coded board dimensions.

* Order and Chaos has additional win logic for Chaos. The program determines whether it is impossible for Order to win by checking all possible winning groups and determining whether each group can no longer contain the required matching pieces.

* All input and output is abstracted away inside the `Controller` class. This keeps user interaction separate from the game logic and makes the game classes more readable and easier to understand.

* Composition is used in Super Tic Tac Toe to build the larger game from nine smaller Board objects. This allows each sub-board to reuse existing board functionality while the SuperTicTacToe class manages the overall game rules and state.

* Super Tic Tac Toe enforces forced-board rules. The next player's available sub-board is determined by the position of the previous move. If the required board is already closed, the player can choose another available board.


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
3. Super Tic Tac Toe

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
3. Super Tic Tac Toe

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
3. Super Tic Tac Toe

Input:
3

Output:
===================================
   Welcome to Super Tic Tac Toe!
===================================
Rules:
1. The game consists of a larger 3x3 board containing nine smaller 3x3 tic-tac-toe boards.
2. Player 1 uses X and Player 2 uses O.
3. Player 1 may start in any of the nine small boards (A to I). After that, each player must play in the small board matching the position of the previous move inside its small board (e.g., a move in the top-left square sends the opponent to board A).
4. If a move wins a small board by normal tic-tac-toe rules, the entire small board is marked as won by that player on the larger board.
5. Once a small board is won or completely filled, no more moves can be played in it.
6. If a player is sent to a board that is already won or full, they may choose any other valid, open board to play in.
7. The game ends immediately when a player wins three small boards in a row, column, or diagonal on the larger board.
8. If there are no playable small boards remaining and no player has won the larger board, the game is a draw.
Player 1, please enter your name:

Input:
Spongebob

Output:
Player 2, please enter your name:

Input:
Squidward

Ouput:
A             B             C             
  |   |         |   |         |   |       
--+---+--     --+---+--     --+---+--
  |   |         |   |         |   |       
--+---+--     --+---+--     --+---+--
  |   |         |   |         |   |       

D             E             F             
  |   |         |   |         |   |       
--+---+--     --+---+--     --+---+--
  |   |         |   |         |   |       
--+---+--     --+---+--     --+---+--
  |   |         |   |         |   |       

G             H             I             
  |   |         |   |         |   |       
--+---+--     --+---+--     --+---+--
  |   |         |   |         |   |       
--+---+--     --+---+--     --+---+--
  |   |         |   |         |   |       

Spongebob's turn.
Spongebob: Please select the board A to I that you want to play in:

Input:
Z

Output:
Invalid input. Please enter a single character A to I

Input:
A

Output:
Playing in board A:
  0   1   2   
0   |   |  
  ---+---+---
1   |   |  
  ---+---+---
2   |   |  
Spongebob: enter row and col numbers separated by a space (e.g., 0 2):

Input:
1 2

Output:
A             B             C             
  |   |         |   |         |   |       
--+---+--     --+---+--     --+---+--
  |   | X       |   |         |   |       
--+---+--     --+---+--     --+---+--
  |   |         |   |         |   |       

D             E             F             
  |   |         |   |         |   |       
--+---+--     --+---+--     --+---+--
  |   |         |   |         |   |       
--+---+--     --+---+--     --+---+--
  |   |         |   |         |   |       

G             H             I             
  |   |         |   |         |   |       
--+---+--     --+---+--     --+---+--
  |   |         |   |         |   |       
--+---+--     --+---+--     --+---+--
  |   |         |   |         |   |       

Squidward's turn.
Squidward must play in board F.
Playing in board F:
  0   1   2   
0   |   |  
  ---+---+---
1   |   |  
  ---+---+---
2   |   |  
Squidward: enter row and col numbers separated by a space (e.g., 0 2):

Input:
2 0

Output:
A             B             C             
  |   |         |   |         |   |       
--+---+--     --+---+--     --+---+--
  |   | X       |   |         |   |       
--+---+--     --+---+--     --+---+--
  |   |         |   |         |   |       

D             E             F             
  |   |         |   |         |   |       
--+---+--     --+---+--     --+---+--
  |   |         |   |         |   |       
--+---+--     --+---+--     --+---+--
  |   |         |   |       O |   |       

G             H             I             
  |   |         |   |         |   |       
--+---+--     --+---+--     --+---+--
  |   |         |   |         |   |       
--+---+--     --+---+--     --+---+--
  |   |         |   |         |   |       

Spongebob's turn.
Spongebob must play in board G.
Playing in board G:
  0   1   2   
0   |   |  
  ---+---+---
1   |   |  
  ---+---+---
2   |   |  
Spongebob: enter row and col numbers separated by a space (e.g., 0 2):

Input:
2 0

The players basically keep taking turns and they get assigned to boards to play in based on certain conditions such as if the board is full or already won
Fast Forwading a bit here

Output:
Spongebob's turn.
Spongebob must play in board D.
Playing in board D:
  0   1   2   
0   |   |  
  ---+---+---
1   | X |  
  ---+---+---
2   |   |  
Spongebob: enter row and col numbers separated by a space (e.g., 0 2):

Input:
1 2

Output:
A             B             C             
  |   |         |   |         |   |       
--+---+--     --+---+--     --+---+--
  |   | X       |   | O       |   |       
--+---+--     --+---+--     --+---+--
X |   |         |   |         |   |       

D             E             F (X won)     
  |   |         |   |         | X |       
--+---+--     --+---+--     --+---+--
  | X | X     O |   | O       | X |       
--+---+--     --+---+--     --+---+--
  |   |         |   |       O | X |       

G             H             I             
O |   |         |   |         |   |       
--+---+--     --+---+--     --+---+--
O |   |         |   |         |   | O     
--+---+--     --+---+--     --+---+--
X |   |         |   | O       |   | X     

Squidward's turn.
Board F is already won or full, so you may choose any open board.
Squidward: Please select the board A to I that you want to play in:

Input:
E

Output:
Playing in board E:
  0   1   2   
0   |   |  
  ---+---+---
1 O |   | O
  ---+---+---
2   |   |  
Squidward: enter row and col numbers separated by a space (e.g., 0 2):

Input:
1 1

Output:
Squidward has won board E!

A             B             C             
  |   |         |   |         |   |       
--+---+--     --+---+--     --+---+--
  |   | X       |   | O       |   |       
--+---+--     --+---+--     --+---+--
X |   |         |   |         |   |       

D             E (O won)     F (X won)     
  |   |         |   |         | X |       
--+---+--     --+---+--     --+---+--
  | X | X     O | O | O       | X |       
--+---+--     --+---+--     --+---+--
  |   |         |   |       O | X |       

G             H             I             
O |   |         |   |         |   |       
--+---+--     --+---+--     --+---+--
O |   |         |   |         |   | O     
--+---+--     --+---+--     --+---+--
X |   |         |   | O       |   | X     

Spongebob's turn.
Board E is already won or full, so you may choose any open board.
Spongebob: Please select the board A to I that you want to play in:

This demonstrates players what it looks like when players win a sub board and when they're able to pick freely which next board to play in
Fast Forwarding Again

This output shows what it looks like when there's a tie in a sub board and when a player wins

Output:
A (full)      B (O won)     C             
X | O | O       | X |       X | X |       
--+---+--     --+---+--     --+---+--
O | X | X     O | O | O       |   |       
--+---+--     --+---+--     --+---+--
X | O | O     X | X |         |   |       

D (X won)     E (O won)     F (X won)     
  |   |         |   |         | X |       
--+---+--     --+---+--     --+---+--
X | X | X     O | O | O       | X |       
--+---+--     --+---+--     --+---+--
  |   |         |   |       O | X |       

G (O won)     H (O won)     I             
O | O | O       |   |         |   |       
--+---+--     --+---+--     --+---+--
O | X |         |   |       X |   | O     
--+---+--     --+---+--     --+---+--
X | X | O     O | O | O     X |   | X           

Squidward wins Super Tic Tac Toe!
===================================
               Wins
===================================
Spongebob: 0
Squidward: 1
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
3. Super Tic Tac Toe

Input:
0

Output:
Thanks for playing!

If there was a draw we would've gotten an output like this:

Ouput:
A (full)      B (full)      C (full)            
X | O | O     O | X | O     X | X | O      
--+---+--     --+---+--     --+---+--
O | X | X     O | X | O     O | O | X    
--+---+--     --+---+--     --+---+--
X | O | O     X | O | X     X | X | O      

D (full)      E (full)      F (full)     
X | O | O     O | X | O     X | X | O      
--+---+--     --+---+--     --+---+--
O | X | X     O | X | O     O | O | X    
--+---+--     --+---+--     --+---+--
X | O | O     X | O | X     X | X | O        

G (full)      H (full)      I (full)          
X | O | O     O | X | O     X | X | O      
--+---+--     --+---+--     --+---+--
O | X | X     O | X | O     O | O | X    
--+---+--     --+---+--     --+---+--
X | O | O     X | O | X     X | X | O   


It's a draw!
===================================
               Wins
===================================
Spongebob: 0
Squidward: 0
```
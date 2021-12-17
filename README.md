# Chess

This is my implementation of Chess in Java. During the SARS-COV-2 pandemic, I began playing more chess. This lead me to want to build my own chess game, with the hopes of improving my Java skills.

## Table of Contents

* [Description](#description)
* [1 - Basic Logic](#1---basic-logic-)
* [2 - Enums](#2---enums-)
    + [ID](#id)
    + [COLOUR](#colour)
    + [BOARD](#board)
* [3 - Key Classes](#3---key-classes-)
    + [Coordinate](#coordinate)
    + [Piece](#piece)
    + [Pieces](#pieces)
    + [Move](#move)
* [4 - The UI](#4---the-ui-)
    + [Printing the Interface](#printing-the-interface)
    + [Handling movements](#handling-movements)
* [5 - Saving a Game](#5---saving-a-game-)
* [6 - To Do List](#6---to-do-list-)
* [7 - Pictures](#7---pictures)
    + [Terminal Interface](#terminal-interface)
    + [UI](#ui)

## Description

Initially, I thought I could only develop a terminal based game, as that was all that I maanged when I had made games before. However, feeling usnatisfied, I learnt Swing to develop a UI that definetely improved the game experience. Both implementations have the same underlying processes. To ensure the correct functioning of the whole project, I created JUNIT tests for most classes
 
 ## 1 - Basic Logic &#9823; 

 
 The logic for the terminal based game and the UI is essentially the same:
 
 1. Instantiate a _pieces_ class. This is the board of the game, and contains all of the pieces.
 2. Select a piece and select a coordinate. If the destination coordinate is a valid coordinate for your given piece, and its the piece's colour turn to move, then make a move.
* this is represented by changing _pieces_.
* the piece's key changes from its origin to its destination coordinate
* if a piece from the opposite colour was occupying the coordiante, that piece is eliminated
 3. Check if its check mate. If so, the game ends. Otherwise, it is the turn of the other colour.
 
 ## 2 - Enums &#9822;
 
 Enums were an integral part of this project, as I used them to represent important constants for the game. The enums I created were __ID__, __COLOUR__ and __BOARD__.
 
 ### ID
 
 Used as an identifier for a piece. The types of pieces are:
 
 * KING
 * QUEEN
 * ROOK
 * BISHOP
 * KNIGHT
 * PAWN
 
 This enum contained 2 `toString()` methods. One (`toString()`) is used to print the piece's letter for describing moves. For example, if a bishop moves to _e6_, such move would be described as _Be6_. The other one (`toFullString()`) is mainly used for testing and "human" printing purposes. It returns the full name of the String. For example, ` ID.KING.toFullString()` would return `"King"`.
 
 ### COLOUR
 
 Used as a colour identifier for a piece. These are:
 
 * B (a black piece)
 * W (a white piece)
 
 This enum also contains 2 `toString()` methods, albeit these are barely used (mainly only for tests). The most important method is the `not(COLOUR)` method. It is used to return the opposite colour to the argument it takes. Hence, `COLOUR.not(COLOUR.B) ` would return `COLOUR.W`. This is extremely useful, for example when handling the turn of play, or calculating when a move leads to check.
 
 ### BOARD
 
 Used to contain the dimensions of the board. These are determined by 4 constants:
 
 * FIRST_FILE('a')
 * LAST_FILE('h')
 * FIRST_RANK(1)
 * LAST_RANK(8)
 
 A file is used to represent a column, and is represented by a character from _a_ to _h_. A rank represents a row, and is represented by an integer from _1_ to _8_. __BOARD__ contains methods to access the values associated with these constants.
 
 ## 3 - Key Classes &#9821;
 
 There are 4 key classes that sustain this project: __Coordinate__, __Piece__, __Pieces__ and __Move__. The first 3 are used to create objects to represent the chess board and its pieces. They all contain getters, setters, alongside functionality to create deep copies of its instances. This is paramount, as will be explained later. The methods `toString()`, `equals(Object o)` and `hashCode()` have all been overridden.  The last class, __Move__ simply contains methods that are essential for the correct functioining of the project.
         
### Coordinate

Uses a char (file) and an int (rank) to determine a square within a board, according to Chess nomenclature. Includes functionality to ensure that the arguments provided represent a valid coordinate within the board.

### Piece

A class identifying the pieces of the game.  A piece is initialised with an ID (type of piece), a COLOUR (black or white) and its initial Coordinate within the board. It acts as a super class to the more specific pieces: King, Queen, Rook, Bishop, Knight, and Pawn. The most important method in __Piece__ have to do with the creation, updating and validation of the moves that a piece can move. We define _raw moves_ as those moves that a piece can make _independently_ of whether the King is in check of not. _Potential moves_ are the actual moves that a piece can make, taking checks into accounts.  __Piece__ contains abstract methods that are then individually defined within the children classes. For example, `getRawMoves(Pieces pieces)` is used to obtain the raw moves that are available to an individual piece. Since each piece moves differently, the details of `getRawMoves(Pieces pieces)` are defined individually. 

Perhaps the most important of all its methods is `removeOwnCheck(Pieces pieces)`. This method is used to take in the raw moves available to a piece, and then filter out all of the moves that are impossible; namely those that would either:

* lead to check
* not stop a check (i.e if a piece moves away from the King, leading to a check by the opposition)

In order to do this, we must create a deep copy of the board. From the raw moves of the piece, we make the piece execute the move within the copied board. We then check if that has lead to situation of check by the opposition. If it has, said move is deleted. Otherwise, it is maintained. This is a crucial process, as it allows the pieces to determine all of their moves, so checking whether the move provided by the user is legal becomes trivial. Moreover, for the UI, it allows us to display all the moves avaialbale to the given piece.
        

### Pieces

Contains a HashMap with Coordinate-Piece key-value pairs. It contains all the methods used to handling the positioning of the pieces throughout the game. For example, we can use it to find the King of a certain colour, determine which pieces lie on the same file or whether it is the end of the game (via check mate or a draw/stalemate). __Pieces__ also contains the method that executes the moves provided by the user. It is a particularly long method, which must check for all moves that constitute special cases, such as a King castling or a pawn queening/capturing in diagonal/en passant.

### Move

This class contains all the classes pertinent to the movement of the pieces. It contains functionality to, given a board (__Pieces__) and a piece determine which range of movement it has. We can determine available moves in vertical, diagonal and horizontal direction, alongside the moves available to a Knight. It is these methods that are used within a __Piece__ to determine the raw moves available to them. It must be noted that there are pieces, such as the King or the Pawn that have a special range of moves available to them. The handling of these moves is made directly within their classes.

## 4 - The UI &#9820;

To create the UI, I used the Swing package.

### Printing the Interface

The interface is mainly made through the superposition of JPanels. To create the chess board, I created a 2D array of JButtons, each of alternating colours. I used a nested for loop to print the whole chess board. I also used this to assign coordinates to each JButton square, which allowed me to display a picture representing a piece on top of the square. Then, with each move, I executed the for loop again, but with pieces updated to reflect the current game. I also created an area to the right of the board that contained a section to see the moves played, alongside a button for saving a game, and an area displaying the outcome of a game (a draw, stalemate or win).

### Handling movements

To make a move, the user needs to select a piece, and then select a destination square. I created a flag that would allow me to check whether the user has clicked twice, as this would represent a move. When the user clicks a square (JButton), I looped through the array of JButtons until I found the JButton that had been clicked. I then turned this information into a Coordinate, which then allowed me to find the Piece occupying the square. This then made it so the squares corresponding to the potential moves of the piece got illuminated. It also set the flag to true. Once there was a second click the program checked to see if the selected square corresponded to one of the potential moves of the piece. If so, the move was executed and the board was updated, resetting the flag. Otherwise, the potential moves of the selected piece would be shown.

## 5 - Saving a Game &#9819;

The FileIO class is used to handle game saving. In order to save a game, we create a txt file containing the moves, as per pgn (portable game notation) format (albeit without additional information, such as the date, location, players involved, etc ...). FileIO contains a method that handles the creation of Strings representing sets of moves. The user can then introduce the name of the file to be saved.

## 6 - To Do List &#9818;

I believe I have merely constructed the beginning of the project. To further improve it, I would like to (in order to feasability and ease):

- [ ] add functionality to parse pgn/txt files and load their games
- [ ] add functionality so that a user can drag a piece to move it (currently need 2 clicks)
- [ ] add a player vs player functionality (switching the position of the board to face the player)
- [ ] add a timer
- [ ] add an opening move handbook
- [ ] create an AI of varying difficulty (player vs computer)
- [ ] create an ML algorithm

## 7 - Pictures

### Terminal Interface

<p align="center">
  <img src="https://github.com/alv31415/My-Chess/blob/master/Game%20Pictures/Screenshot%202020-08-19%20at%2018.53.43.png"/>
</p>

### UI

The initial set up:

<p align = "center">
  <img src = "https://github.com/alv31415/My-Chess/blob/master/Game%20Pictures/Screenshot%202020-08-19%20at%2018.48.47.png">
 </p>
 
 Clicking on the Knight revels it has 2 potential moves (f3 & h3):
 
 <p align = "center">
   <img src = "https://github.com/alv31415/My-Chess/blob/master/Game%20Pictures/Screenshot%202020-08-19%20at%2018.49.04.png">
 </p>
 
 The Knight has moved, as shown in the game log to the right; the black pawn has 2 potential moves (d6 & d5):
 
 <p align = "center">
   <img src = "https://github.com/alv31415/My-Chess/blob/master/Game%20Pictures/Screenshot%202020-08-19%20at%2018.49.22.png">
 </p>
 
 We can choose to save the game under any name we choose (as long as it doesn't already exist!):
 
 <p align = "center">
   <img src = "https://github.com/alv31415/My-Chess/blob/master/Game%20Pictures/Screenshot%202020-08-19%20at%2018.49.44.png">
 </p>
 
 The black Pawn can now be promoted:
 
 <p align = "center">
   <img src = "https://github.com/alv31415/My-Chess/blob/master/Game%20Pictures/Screenshot%202020-08-19%20at%2018.50.31.png">
 </p>
 
 The white King is in check, so its movements are limited:
 
 <p align = "center">
   <img src = "https://github.com/alv31415/My-Chess/blob/master/Game%20Pictures/Screenshot%202020-08-19%20at%2018.50.53.png">
 </p>
 
 If we want to save the King, the white Bishop only has 1 square available:
 
 <p align = "center">
   <img src = "https://github.com/alv31415/My-Chess/blob/master/Game%20Pictures/Screenshot%202020-08-19%20at%2018.51.14.png">
 </p>


A situation of checkmate: 

 <p align = "center">
  <img src = "https://github.com/alv31415/My-Chess/blob/master/Game%20Pictures/Screenshot%202020-08-19%20at%2018.52.00.png">
 </p>

 

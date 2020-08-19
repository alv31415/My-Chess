/*
package backup;

import javax.swing.*;
import java.awt.*;

public class GUIBoard extends JFrame {

    public static Container contents;
    public static final int dimension = BOARD.LAST_RANK.getRankVal();
    public static final int firstRank = BOARD.FIRST_RANK.getRankVal();
    public static final char firstFile = BOARD.FIRST_FILE.getFileVal();
    public static final char lastFile = BOARD.LAST_FILE.getFileVal();
    public static final char charFile = (char) (firstFile - 1);
    public static COLOUR turn = COLOUR.W;

    public static GUITile[][] board = new GUITile[dimension][dimension];

    public static Color brown = new Color(150, 75, 0); //brown #964B00
    public static Color pastel = new Color(255, 222, 173); //navajorwhite #FFDEAD
    public static Color intermediate = new Color(255, 255, 153);

    public static int counter = 0;

    public static int numberOfTurns = 0;

    public static StringBuilder str = new StringBuilder();

    public static Piece movingPiece;

    public GUIBoard(Pieces p) {
        setTitle("CHESS23");
        contents = getContentPane();
        contents.setLayout(new GridLayout(dimension, dimension));

        paintBoard(p);

        setSize(800, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void increaseCounter() {
        counter++;
    }

    public static int getCounter() {
        return counter;
    }

    public static COLOUR getTurn() {
        return turn;
    }

    public static void setTurn() {
        turn = COLOUR.not(turn);
    }

    public static Piece getMovingPiece() {
        return movingPiece;
    }

    public static void setMovingPiece(Piece movingPiece) {
        GUIBoard.movingPiece = movingPiece;
    }

    public static int getNumberOfTurns() {
        return numberOfTurns;
    }

    public static void increaseNumberOfTurns() {
       numberOfTurns++;
    }

    public static void clearCounter() {
        counter = 0;
    }

    public static void paintBoard(Pieces p) {
        for (int rank = dimension; rank >= firstRank; rank--) {
            for (int file = 1; file <= dimension; file++) {
                char fileChar = (char) (charFile + file);
                Coordinate tileCoord = new Coordinate(fileChar, rank);
                board[rank - 1][file - 1] = new GUITile(tileCoord, p);
                board[rank - 1][file - 1].placeButton();
                contents.add(board[rank - 1][file - 1]);
            }
        }
    }

    public static void main(String[] args) {
        GUIBoard b = new GUIBoard(new Pieces());
    }

}
*/

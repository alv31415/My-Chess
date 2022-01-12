/*
package backup;

import javax.swing.*;
import java.awt.*;

public class chess23.GUIBoard extends JFrame {

    public static Container contents;
    public static final int dimension = chess23.BOARD.LAST_RANK.getRankVal();
    public static final int firstRank = chess23.BOARD.FIRST_RANK.getRankVal();
    public static final char firstFile = chess23.BOARD.FIRST_FILE.getFileVal();
    public static final char lastFile = chess23.BOARD.LAST_FILE.getFileVal();
    public static final char charFile = (char) (firstFile - 1);
    public static chess23.COLOUR turn = chess23.COLOUR.W;

    public static GUITile[][] board = new GUITile[dimension][dimension];

    public static Color brown = new Color(150, 75, 0); //brown #964B00
    public static Color pastel = new Color(255, 222, 173); //navajorwhite #FFDEAD
    public static Color intermediate = new Color(255, 255, 153);

    public static int counter = 0;

    public static int numberOfTurns = 0;

    public static StringBuilder str = new StringBuilder();

    public static chess23.Piece movingPiece;

    public chess23.GUIBoard(chess23.Pieces p) {
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

    public static chess23.COLOUR getTurn() {
        return turn;
    }

    public static void setTurn() {
        turn = chess23.COLOUR.not(turn);
    }

    public static chess23.Piece getMovingPiece() {
        return movingPiece;
    }

    public static void setMovingPiece(chess23.Piece movingPiece) {
        chess23.GUIBoard.movingPiece = movingPiece;
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

    public static void paintBoard(chess23.Pieces p) {
        for (int rank = dimension; rank >= firstRank; rank--) {
            for (int file = 1; file <= dimension; file++) {
                char fileChar = (char) (charFile + file);
                chess23.Coordinate tileCoord = new chess23.Coordinate(fileChar, rank);
                board[rank - 1][file - 1] = new GUITile(tileCoord, p);
                board[rank - 1][file - 1].placeButton();
                contents.add(board[rank - 1][file - 1]);
            }
        }
    }

    public static void main(String[] args) {
        chess23.GUIBoard b = new chess23.GUIBoard(new chess23.Pieces());
    }

}
*/

package chess23;

import javax.swing.*;
import java.util.ArrayList;

/*
1) Class Constructors
2) Overridden Methods
*/

/**
 * Class representing a bishop in chess.
 */
public class Queen extends Piece{

    /**
     * The icon representing the {@link Queen} in the GUI.
     */
    private ImageIcon icon;

    //________________________________________________Class Constructors________________________________________________

    /**
     * Class constructor for a {@link Queen}.
     * @param colour a {@link COLOUR}, representing the colour of the {@link Queen}.
     * @param ogCoord a {@link Coordinate}, representing the starting square of the {@link Bishop}.
     */
    public Queen(COLOUR colour, Coordinate ogCoord) {
        super(ID.QUEEN, colour, ogCoord);

        // instantiate the icon depending on the colour of the queen
        if (getColour() == COLOUR.B)
            this.icon = new ImageIcon("icons/BQueen.png");
        else if (getColour() == COLOUR.W)
            this.icon = new ImageIcon("icons/WQueen.png");
    }

    /**
     * Copy constructor for a {@link Queen}.
     * @param original the {@link Queen} we are copying.
     */
    public Queen(Queen original) {
        super(original);
        this.icon = this.getImageIcon();
    }

    //________________________________________________Overridden Methods________________________________________________

    /**
     * Produces a deep copy of the {@link Queen} instance.
     * @return a {@link Queen} produced as a deep copy of the instance.
     */
    @Override
    public Queen makeCopy() {
        return new Queen(this);
    }

    /**
     * Produces an {@link ArrayList} containing all the raw moves available
     * to the {@link Queen} instance.
     * @param pieces the {@link Pieces} used for playing.
     * @return an {@link ArrayList}, containing all the {@link Coordinate} squares
     *         reachable by the {@link Queen}.
     */
    @Override
    public ArrayList<Coordinate> getRawMoves(Pieces pieces) {

        // consider vertical, horizontal and diagonal moves
        ArrayList<Coordinate> front = Move.frontFree(pieces,this,dimension);
        ArrayList<Coordinate> right = Move.rightFree(pieces,this,dimension);
        ArrayList<Coordinate> back = Move.backFree(pieces,this,dimension);
        ArrayList<Coordinate> left = Move.leftFree(pieces,this,dimension);
        ArrayList<Coordinate> frontRDig = Move.frontRDigFree(pieces, this,dimension);
        ArrayList<Coordinate> backRDig = Move.backRDigFree(pieces, this, dimension);
        ArrayList<Coordinate> backLDig = Move.backLDigFree(pieces, this,dimension);
        ArrayList<Coordinate> frontLDig = Move.frontLDigFree(pieces, this, dimension);

        // join vertical and horizontal moves
        front.addAll(right);
        back.addAll(left);
        front.addAll(back);

        // join diagonal moves
        frontRDig.addAll(backRDig);
        backLDig.addAll(frontLDig);
        frontRDig.addAll(backLDig);

        // join all moves
        front.addAll(frontRDig);

        return front;

    }

    @Override
    public ImageIcon getImageIcon() {
        return icon;
    }

}

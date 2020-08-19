import javax.swing.*;
import java.util.ArrayList;

/*
1) Class Constructors
2) Overridden Methods
*/

public class Bishop extends Piece{

    private ImageIcon icon;

    //________________________________________________Class Constructors________________________________________________

    public Bishop(COLOUR colour, Coordinate OGcoord) {
        super(ID.BISHOP, colour, OGcoord);
        if (getColour() == COLOUR.B)
            icon = new ImageIcon("BBishop.png");
        else if (getColour() == COLOUR.W)
            icon = new ImageIcon("WBishop.png");
    }

    public Bishop(Bishop original) {
        super(original);
    }

    //________________________________________________Overridden Methods________________________________________________

    @Override
    public Bishop makeCopy() {
        return new Bishop(this);
    }

    /**
     * Produces an ArrayList containing all the raw moves available to a Bishop within a given board
     * @param pieces the board being played in
     * @return an ArrayList containing all the coordinates produced from the Move class (all the diagonals)
     */
    @Override
    public ArrayList<Coordinate> getRawMoves(Pieces pieces) {
        ArrayList<Coordinate> frontRDig = Move.frontRDigFree(pieces, this,dimension);
        ArrayList<Coordinate> backRDig = Move.backRDigFree(pieces, this, dimension);
        ArrayList<Coordinate> backLDig = Move.backLDigFree(pieces, this,dimension);
        ArrayList<Coordinate> frontLDig = Move.frontLDigFree(pieces, this, dimension);

        frontRDig.addAll(backRDig);
        backLDig.addAll(frontLDig);
        frontRDig.addAll(backLDig);

        return frontRDig;
    }

    @Override
    public ImageIcon getImageIcon() {
        return icon;
    }




}

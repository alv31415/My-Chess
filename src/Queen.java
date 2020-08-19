import javax.swing.*;
import java.util.ArrayList;

/*
1) Class Constructors
2) Overridden Methods
*/

public class Queen extends Piece{

    private ImageIcon icon;

    //________________________________________________Class Constructors________________________________________________

    public Queen(COLOUR colour, Coordinate OGcoord) {
        super(ID.QUEEN, colour, OGcoord);
        if (getColour() == COLOUR.B)
            icon = new ImageIcon("BQueen.png");
        else if (getColour() == COLOUR.W)
            icon = new ImageIcon("WQueen.png");
    }

    public Queen(Queen original) {
        super(original);
    }

    //________________________________________________Overridden Methods________________________________________________

    @Override
    public Queen makeCopy() {
        return new Queen(this);
    }

    /**
     * Produces an ArrayList containing all the raw moves available to a Queen within a given board
     * @param pieces the board being played in
     * @return an ArrayList containing all the coordinates produced from the Move class
     * (all the diagonals, all verticals and all horizontals)
     */

    @Override
    public ArrayList<Coordinate> getRawMoves(Pieces pieces) {

        ArrayList<Coordinate> front = Move.frontFree(pieces,this,dimension);
        ArrayList<Coordinate> right = Move.rightFree(pieces,this,dimension);
        ArrayList<Coordinate> back = Move.backFree(pieces,this,dimension);
        ArrayList<Coordinate> left = Move.leftFree(pieces,this,dimension);
        ArrayList<Coordinate> frontRDig = Move.frontRDigFree(pieces, this,dimension);
        ArrayList<Coordinate> backRDig = Move.backRDigFree(pieces, this, dimension);
        ArrayList<Coordinate> backLDig = Move.backLDigFree(pieces, this,dimension);
        ArrayList<Coordinate> frontLDig = Move.frontLDigFree(pieces, this, dimension);

        front.addAll(right);
        back.addAll(left);
        front.addAll(back);

        frontRDig.addAll(backRDig);
        backLDig.addAll(frontLDig);
        frontRDig.addAll(backLDig);

        front.addAll(frontRDig);

        return front;

    }

    @Override
    public ImageIcon getImageIcon() {
        return icon;
    }

}

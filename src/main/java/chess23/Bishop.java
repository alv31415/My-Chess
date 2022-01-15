package chess23;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Class representing a bishop in chess.
 */
public class Bishop extends Piece{

    /**
     * The icon representing the {@link Bishop} in the GUI.
     */
    private ImageIcon icon;

    //________________________________________________Class Constructors________________________________________________

    /**
     * Class constructor for a {@link Bishop}.
     * @param colour a {@link COLOUR}, representing the colour of the {@link Bishop}.
     * @param ogCoord a {@link Coordinate}, representing the starting square of the {@link Bishop}.
     */
    public Bishop(COLOUR colour, Coordinate ogCoord) {
        super(ID.BISHOP, colour, ogCoord);

        // instantiate the icon depending on the colour of the bishop
        if (this.getColour() == COLOUR.B)
            icon = new ImageIcon("icons/BBishop.png");
        else if (this.getColour() == COLOUR.W)
            icon = new ImageIcon("icons/WBishop.png");
    }

    /**
     * Copy constructor for a {@link Bishop}.
     * @param original the {@link Bishop} we are copying.
     */
    public Bishop(Bishop original) {
        super(original);
        this.icon = original.getImageIcon();
    }

    //________________________________________________Overridden Methods________________________________________________

    /**
     * Produces a deep copy of the {@link Bishop} instance.
     * @return a {@link Bishop} produced as a deep copy of the instance.
     */
    @Override
    public Bishop makeCopy() {
        return new Bishop(this);
    }

    /**
     * Produces an {@link ArrayList} containing all the raw moves available
     * to the {@link Bishop} instance.
     * @param pieces the {@link Pieces} used for playing.
     * @return an {@link ArrayList}, containing all the {@link Coordinate} squares
     *         reachable by the {@link Bishop}.
     */
    @Override
    public ArrayList<Coordinate> getRawMoves(Pieces pieces) {

        // only consider diagonal moves
        ArrayList<Coordinate> frontRDig = Move.frontRDigFree(pieces, this, BOARD.LAST_RANK.getRankVal());
        ArrayList<Coordinate> backRDig = Move.backRDigFree(pieces, this, BOARD.LAST_RANK.getRankVal());
        ArrayList<Coordinate> backLDig = Move.backLDigFree(pieces, this, BOARD.LAST_RANK.getRankVal());
        ArrayList<Coordinate> frontLDig = Move.frontLDigFree(pieces, this, BOARD.LAST_RANK.getRankVal());

        // join all possible moves
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

package chess23;

import javax.swing.*;
import java.util.ArrayList;

/*
1) Class Constructors
2) Overridden Methods
*/

/**
 * Class representing a knight in chess.
 */
public class Knight extends Piece{

    /**
     * The icon representing the {@link Knight} in the GUI.
     */
    private ImageIcon icon;

    //________________________________________________Class Constructors________________________________________________

    /**
     * Class constructor for a {@link Knight}.
     * @param colour a {@link COLOUR}, representing the colour of the {@link Bishop}.
     * @param ogCoord a {@link Coordinate}, representing the starting square of the {@link Bishop}.
     */
    public Knight(COLOUR colour, Coordinate ogCoord) {
        super(ID.KNIGHT, colour, ogCoord);

        // instantiate the icon depending on the colour of the knight
        if (getColour() == COLOUR.B)
            icon = new ImageIcon("icons/BKnight.png");
        else if (getColour() == COLOUR.W)
            icon = new ImageIcon("icons/WKnight.png");
    }

    /**
     * Copy constructor for a {@link Knight}.
     * @param original the {@link Knight} we are copying.
     */
    public Knight(Knight original) {
        super(original);
        icon = getImageIcon();
    }

    //________________________________________________Overridden Methods________________________________________________

    /**
     * Produces a deep copy of the {@link Knight} instance.
     * @return a {@link Knight} produced as a deep copy of the instance.
     */
    @Override
    public Knight makeCopy() {
        return new Knight(this);
    }

    /**
     * Produces an {@link ArrayList} containing all the raw moves available
     * to the {@link Knight} instance.
     * @param pieces the {@link Pieces} used for playing.
     * @return an {@link ArrayList}, containing all the {@link Coordinate} squares
     *         reachable by the {@link Knight}.
     */
    @Override
    public ArrayList<Coordinate> getRawMoves(Pieces pieces) {

        // consider all the knight moves
        ArrayList<Coordinate> front = Move.frontKnight(pieces,this);
        ArrayList<Coordinate> right = Move.backKnight(pieces,this);
        ArrayList<Coordinate> back = Move.rightKnight(pieces,this);
        ArrayList<Coordinate> left = Move.leftKnight(pieces,this);

        // join all possible moves
        front.addAll(right);
        back.addAll(left);
        front.addAll(back);

        return front;
    }

    @Override
    public ImageIcon getImageIcon() {
        return icon;
    }

}

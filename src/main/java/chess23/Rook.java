package chess23;

import javax.swing.*;
import java.util.ArrayList;

/*
1) Class Constructors
2) Getters & Setters
3) Overridden Methods
*/

/**
 * Class representing a rook in chess.
 */
public class Rook extends Piece{

    /**
     * {@link Coordinate} where the {@link Rook} will end up if it castles.
     */
    private Coordinate castleCoordRook;

    /**
     * The icon representing the {@link Rook} in the GUI.
     */
    private ImageIcon icon;

    //________________________________________________Class Constructors________________________________________________

    /**
     * Class constructor for a {@link Rook}.
     * @param colour a {@link COLOUR}, representing the colour of the {@link Rook}.
     * @param ogCoord a {@link Coordinate}, representing the starting square of the {@link Rook}.
     */
    public Rook(COLOUR colour, Coordinate ogCoord) {
        super(ID.ROOK, colour, ogCoord);

        // instantiate the icon depending on the colour of the bishop
        if (getColour() == COLOUR.B)
            this.icon = new ImageIcon("icons/BRook.png");
        else if (getColour() == COLOUR.W)
            this.icon = new ImageIcon("icons/WRook.png");
    }

    /**
     * Copy constructor for a {@link Rook}.
     * @param original the {@link Rook} we are copying.
     */
    public Rook (Rook original) {
        super(original);
        this.icon = this.getImageIcon();
        this.castleCoordRook = this.getCastleCoordRook();
    }

    //________________________________________________Overridden Methods________________________________________________

    /**
     * Produces a deep copy of the {@link Rook} instance.
     * @return a {@link Rook} produced as a deep copy of the instance.
     */
    @Override
    public Rook makeCopy() {
        return new Rook(this);
    }

    /**
     * Produces an {@link ArrayList} containing all the raw moves available
     * to the {@link Rook} instance.
     * @param pieces the {@link Pieces} used for playing.
     * @return an {@link ArrayList}, containing all the {@link Coordinate} squares
     *         reachable by the {@link Rook}.
     */
    @Override
    public ArrayList<Coordinate> getRawMoves(Pieces pieces) {

        // only consider vertical and horizontal moves
        ArrayList<Coordinate> front = Move.frontFree(pieces,this,dimension);
        ArrayList<Coordinate> right = Move.rightFree(pieces,this,dimension);
        ArrayList<Coordinate> back = Move.backFree(pieces,this,dimension);
        ArrayList<Coordinate> left = Move.leftFree(pieces,this,dimension);

        // join all moves
        front.addAll(right);
        back.addAll(left);
        front.addAll(back);

        return front;
    }

    @Override
    public ImageIcon getImageIcon() {
        return this.icon;
    }

    //________________________________________________Getters & Setters________________________________________________

    public Coordinate getCastleCoordRook() {
        return this.castleCoordRook;
    }

    public void setCastleCoordRook(Coordinate castleCoordRook) {
        this.castleCoordRook = castleCoordRook;
    }


}

package chess23;

import javax.swing.*;
import java.util.*;

/*
1) Class Constructors
2) Getters & Setters
3) Castling Validation Methods
4) Overridden Methods
*/

/**
 * Class representing a king in chess.
 */
public class King extends Piece{

    /**
     * The {@link Coordinate} to which the {@link King} goes after castling kingside.
     */
    private Coordinate castleCoordKingK;

    /**
     * The {@link Coordinate} to which the {@link King} goes after castling queenside.
     */
    private Coordinate castleCoordKingQ;

    /**
     * The {@link Coordinate} through which the {@link King} goes when castling kingside.
     * This is used to ensure that if we castle, we do so without being checked.
     */
    private Coordinate transitionCoordKingK;

    /**
     * The {@link Coordinate} through which the {@link King} goes when castling queenside.
     * This is used to ensure that if we castle, we do so without being checked.
     */
    private Coordinate transitionCoordKingQ;

    /**
     * The {@link Rook} on the kingside.
     */
    private Rook rookKing;

    /**
     * The {@link Rook} on the queenside.
     */
    private Rook rookQueen;

    /**
     * The icon representing the {@link King} in the GUI.
     */
    private ImageIcon icon;

    //________________________________________________Class Constructors________________________________________________

    /**
     * Class constructor for a {@link King}.
     * @param colour a {@link COLOUR}, representing the colour of the {@link King}.
     * @param ogCoord a {@link Coordinate}, representing the starting square of the {@link King}.
     */
    public King(COLOUR colour, Coordinate ogCoord) {
        super(ID.KING, colour, ogCoord);

        // instantiate the icon depending on the colour of the king
        if (this.getColour() == COLOUR.B)
            this.icon = new ImageIcon("icons/BKing.png");
        else if (this.getColour() == COLOUR.W)
            this.icon = new ImageIcon("icons/WKing.png");
    }

    /**
     * Copy constructor for a {@link King}.
     * @param original the {@link King} we are copying.
     */
    public King(King original) {
        super(original);
        this.icon = this.getImageIcon();
        this.castleCoordKingK = this.getCastleCoordKingK();
        this.castleCoordKingQ = this.getCastleCoordKingQ();
        this.rookKing = this.getRookKing();
        this.rookQueen = this.getRookQueen();
        this.transitionCoordKingK = this.getTransitionCoordKingK();
        this.transitionCoordKingQ = this.getTransitionCoordKingQ();
    }

    //________________________________________________Overridden Methods________________________________________________

    /**
     * Produces a deep copy of the {@link King} instance.
     * @return a {@link King} produced as a deep copy of the instance.
     */
    @Override
    public King makeCopy() {
        return new King(this);
    }

    /**
     * Produces an {@link ArrayList} containing all the raw moves available
     * to the {@link King} instance.
     * @param pieces the {@link Pieces} used for playing.
     * @return an {@link ArrayList}, containing all the {@link Coordinate} squares
     *         reachable by the {@link King}.
     */
    @Override
    public ArrayList<Coordinate> getRawMoves(Pieces pieces) {

        // consider vertical, horizontal and diagonal moves of a single step
        ArrayList<Coordinate> front = Move.frontFree(pieces,this, single);
        ArrayList<Coordinate> right = Move.rightFree(pieces,this, single);
        ArrayList<Coordinate> back = Move.backFree(pieces,this, single);
        ArrayList<Coordinate> left = Move.leftFree(pieces,this, single);
        ArrayList<Coordinate> frontRDig = Move.frontRDigFree(pieces, this, single);
        ArrayList<Coordinate> backRDig = Move.backRDigFree(pieces, this, single);
        ArrayList<Coordinate> backLDig = Move.backLDigFree(pieces, this, single);
        ArrayList<Coordinate> frontLDig = Move.frontLDigFree(pieces, this, single);

        // join all possible moves
        front.addAll(right);
        back.addAll(left);
        front.addAll(back);

        frontRDig.addAll(backRDig);
        backLDig.addAll(frontLDig);
        frontRDig.addAll(backLDig);

        front.addAll(frontRDig);

        // if the king can castle, add corresponding move
        if (canCastleKing(pieces))
            front.add(this.castleCoordKingK);
        if (canCastleQueen(pieces))
            front.add(this.castleCoordKingQ);

        return front;
    }

    @Override
    public ImageIcon getImageIcon() {
        return this.icon;
    }

    //________________________________________________Getters & Setters________________________________________________

    public Coordinate getCastleCoordKingK() {
        return this.castleCoordKingK;
    }

    public Coordinate getCastleCoordKingQ() {
        return this.castleCoordKingQ;
    }

    public Coordinate getTransitionCoordKingK() {
        return this.transitionCoordKingK;
    }

    public Coordinate getTransitionCoordKingQ() {
        return this.transitionCoordKingQ;
    }

    public Rook getRookKing() {
        return this.rookKing;
    }

    public Rook getRookQueen() {
        return this.rookQueen;
    }

    //________________________________________________Castling Validation Methods________________________________________________

    /**
     * Determines whether a {@link King} can castle kingside (short castling).
     * @param pieces the {@link Pieces} used for playing.
     * @return {@code true} if and only if we adhere to the principle "one may not castle out of, through, or into check.":
     * <ul>
     *     <li>the {@link King} isn't in check,</li>
     *     <li>there is a {@link Rook} on the kingside ({@code h1} for white, {@code h8} for black),</li>
     *     <li>there are no pieces between the {@link King} and the {@link Rook}</li>
     *     <li>neither the kingside {@link Rook} nor the {@link King} have moved</li>
     * </ul>
     * If the {@link King} can castle, then {@link #castleCoordKingK} and {@link #transitionCoordKingK} are updated. 
     * <br>
     * It also updates the moves of the kingside {@link Rook} to include castling.
     */
    public boolean canCastleKing (Pieces pieces) {

        // if the king is in check we can't castle
        if (pieces.isCheck(getColour()))
            return false;

        // otherwise, fetch all the pieces of the same colour as the king
        HashMap<Coordinate, Piece> colouredPieces = pieces.getColourPieces(getColour());

        // check if there is a rook on the kingside
        for (Piece piece : colouredPieces.values()) {
            if (piece.getName() == ID.ROOK && piece.getFile() == BOARD.LAST_FILE.getFileVal())
                this.rookKing = (Rook) piece;
        }

        // the (expected) number of squares from the kingside rook to the king
        int distanceRookKing = 2;

        ArrayList<Coordinate> castleCoords;

        // get the coordinate to which the kingside rook would move should it castle
        if (getColour() == COLOUR.B)
            castleCoords = Move.leftFree(pieces, this, this.dimension);
        else
            castleCoords = Move.rightFree(pieces, this, this.dimension);

        // determine if there is space for castling
        boolean isSpace = castleCoords.size() == distanceRookKing;

        // we can castle if there is a kingside rook,
        // neither the rook nor the king have moved,
        // and there is space between rook and king.
        // this doesn't consider the situation in which a transition square is being checked
        // by a enemy piece, but this is handled when refining the moves that the king can make later on
        boolean canCastle = this.rookKing != null &&
                !this.rookKing.getHasMoved() &&
                !getHasMoved() &&
                isSpace;

        // if the king can castle, update the coordinates for the rook and king
        if (canCastle) {
            this.castleCoordKingK = castleCoords.get(1);
            this.transitionCoordKingK = castleCoords.get(0);
            this.rookKing.setCastleCoordRook(castleCoords.get(0));
            return true;
        }

        return false;
    }

    /**
     * Determines whether a {@link King} can castle queenside (long castling).
     * @param pieces the {@link Pieces} used for playing.
     * @return {@code true} if and only if we adhere to the principle "one may not castle out of, through, or into check.":
     * <ul>
     *     <li>the {@link King} isn't in check,</li>
     *     <li>there is a {@link Rook} on the queenside ({@code a1} for white, {@code a8} for black),</li>
     *     <li>there are no pieces between the {@link King} and the {@link Rook}</li>
     *     <li>neither the queenside {@link Rook} nor the {@link King} have moved</li>
     * </ul>
     * If the {@link King} can castle, then {@link #castleCoordKingQ} and {@link #transitionCoordKingQ} are updated.
     * <br>
     * It also updates the moves of the queenside {@link Rook} to include castling.
     */
    public boolean canCastleQueen (Pieces pieces) {

        // if the king is in check we can't castle
        if (pieces.isCheck(getColour()))
            return false;

        // otherwise, fetch all the pieces of the same colour as the king
        HashMap<Coordinate,Piece> colouredPieces = pieces.getColourPieces(getColour());

        // check if there is a rook on the queenside
        for (Piece value : colouredPieces.values()) {
            if (value.getName() == ID.ROOK && value.getFile() == BOARD.FIRST_FILE.getFileVal())
                this.rookQueen = (Rook) value;
        }

        // the (expected) number of squares from the queenside rook to the king
        int distanceRookQueen = 3;

        ArrayList<Coordinate> castleCoords;

        // get the coordinate to which the queenside rook would move should it castle
        if (getColour() == COLOUR.W) {
            castleCoords = Move.leftFree(pieces, this, this.dimension);
        }
        else {
            castleCoords = Move.rightFree(pieces, this, this.dimension);
        }

        // determine if there is space for castling
        boolean isSpace = castleCoords.size() == distanceRookQueen;

        // we can castle if there is a queenside rook,
        // neither the rook nor the king have moved,
        // and there is space between rook and king.
        // this doesn't consider the situation in which a transition square is being checked
        // by a enemy piece, but this is handled when refining the moves that the king can make later on
        boolean canCastle = this.rookQueen != null &&
                !this.rookQueen.getHasMoved() &&
                !getHasMoved() &&
                isSpace;

        // if the king can castle, update the coordinates for the rook and king
        if (canCastle) {
            this.castleCoordKingQ = castleCoords.get(1);
            this.transitionCoordKingQ = castleCoords.get(0);
            this.rookQueen.setCastleCoordRook(castleCoords.get(0));
            return true;
        }
        return false;
    }


}
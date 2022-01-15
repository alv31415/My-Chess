package chess23;

import javax.swing.*;
import java.util.*;

/**
 * Superclass representing a generic chess piece.
 */
public abstract class Piece{

    /**
     * name is the ID of the piece. Determines whether it is a pawn, knight, bishop, rook, queen or king.
     * colour is the COLOUR of the piece. Determines whether it is black or white.
     * coords is the current coordinate that a piece is occupying within the board
     * OGcoord is the initial coordinate that a piece occupies when a board is created
     * pieceID is a unique String associated with each piece, created when a Piece is constructed
     * potentialMoves refers to all the possible coordinates that a piece can go to, given a certain board
     * dimension is the "size" of a board, which is 8 by default
     * single is the first rank, which is 1 by default
     * hasMoved determines whether a piece has moved at least once within a given board
     * emptyPiece is a default piece, used as a filler
     */

    /**
     * An {@link ID}, representing the type of the {@link Piece}.
     */
    private final ID name;

    /**
     * A {@link COLOUR}, representing the colour of the {@link Piece}.
     */
    private final COLOUR colour;

    /**
     * The {@link Coordinate} currently occupied by the instance.
     */
    private Coordinate coords;

    /**
     * The initial {@link Coordinate} on which the {@link Piece} starts.
     */
    private final Coordinate ogCoord;

    /**
     * A {@link HashSet} containing all the {@link Coordinate} squares to which the instance can move to.
     * Updated at each iteration of the game.
     */
    private HashSet<Coordinate> potentialMoves = new HashSet<>();

    /**
     * Flag used to determine if the instance has moved.
     */
    private boolean hasMoved = false;

    /**
     * A "dummy" {@link Piece} to be used as a placeholder.
     */
    public static Piece emptyPiece = new Rook(COLOUR.W, Coordinate.emptyCoordinate);

    //________________________________________________Class Constructors________________________________________________

    /**
     * Class constructor for a {@link Piece}.
     * @param colour a {@link COLOUR}, representing the colour of the {@link Piece}.
     * @param ogCoord a {@link Coordinate}, representing the starting square of the {@link Piece}.
     */
    public Piece (ID name, COLOUR colour, Coordinate ogCoord) {

        Objects.requireNonNull(name, "The piece must be correctly identified with an ID.");
        Objects.requireNonNull(colour, "The piece must be either white or black.");
        Objects.requireNonNull(ogCoord, "The piece must have an origin coordinate to be correctly initiallised.");

        this.name = name;
        this.colour = colour;
        this.ogCoord = ogCoord;
        this.coords = ogCoord;
    }

    /**
     * Copy constructor for a {@link Piece}.
     * @param original the {@link Piece} we are copying.
     */
    public Piece (Piece original) {
        Objects.requireNonNull(original,"You can't copy a null piece");
        this.name = original.name;
        this.colour = original.colour;
        this.ogCoord = new Coordinate(original.ogCoord);
        this.coords = new Coordinate(original.coords);
        this.potentialMoves = new HashSet<>();

        for (Coordinate coord : original.getPotentialMoves()) {
            this.potentialMoves.add(new Coordinate(coord));
        }

        this.hasMoved = original.hasMoved;
    }

    //________________________________________________toString() Methods________________________________________________

    @Override
    public String toString() { //standard PNG String
        return name.toString() + coords.toString();
    }

    public String toBoardString() { // for String in the TBIBoard
        if (name == ID.PAWN)
            return "p" + colour.toSmallString();
        else
            return name.toString() + colour.toSmallString();
    }

    public String toFancyString() { // for String that clarifies the piece's position
        return name.toFullString() + " is at " + coords.toString();
    }

    //________________________________________________Overridden Methods________________________________________________

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return name == piece.name &&
                colour == piece.colour &&
                ogCoord.equals(piece.ogCoord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, colour, ogCoord);
    }


    //________________________________________________Abstract Methods________________________________________________


    /**
     * Provides all the raw moves (all possible moves that a piece can make without considering potential checks)
     * for a given {@link Piece} within a certain board.
     * The raw moves are calculated individually within a given {@link Piece}.
     * @param pieces the {@link Pieces} used for playing.
     * @return an {@link ArrayList}, containing all the {@link Coordinate} squares reachable by the {@link Piece}.
     */
    public abstract ArrayList<Coordinate> getRawMoves(Pieces pieces);

    /**
     * @return the {@link ImageIcon} associated with a certain {@link Piece}.
     * Used to assign the icons for the {@link GuiBoard}.
     */
    public abstract ImageIcon getImageIcon();

    /**
     * Produces a deep copy of the {@link Piece} subclass instance.
     * @return a {@link Piece} produced as a deep copy of the subclass instance.
     */
    public abstract Piece makeCopy();

    //________________________________________________Getters & Setters________________________________________________

    public Coordinate getCoords() {
        return this.coords;
    }

    public char getFile() {
        return getCoords().getFile();
    }

    public int getRank() {
        return getCoords().getRank();
    }

    public COLOUR getColour() {
        return this.colour;
    }

    public ID getName() {
        return this.name;
    }

    public Coordinate getOgCoord() {
        return this.ogCoord;
    }

    public void setCoords(Coordinate coords) {
        this.coords = coords;
    }

    public boolean getHasMoved() {return this.hasMoved;}

    public void setHasMoved() {hasMoved = true;}

    public HashSet<Coordinate> getPotentialMoves() {
        return this.potentialMoves;
    }

    //________________________________________________Piece Movement Methods________________________________________________

    /**
     * Updates the {@link #potentialMoves} of the {@link Piece}.
     * @param someMoves an {@link ArrayList} of {@link Coordinate} to update the {@link #potentialMoves}.
     */
    public void addMoves(ArrayList<Coordinate> someMoves) {
        potentialMoves.addAll(someMoves);
    }

    /**
     * Removes all moves in {@link #potentialMoves}.
     */
    public void clearMoves() {
        potentialMoves.clear();
    }

    /**
     * Given a set of raw moves available to a {@link Piece}, it removes those moves ({@link Coordinate})
     * that would lead to check.
     * @param pieces the {@link Pieces} used for playing.
     * @return an {@link ArrayList} of {@link Coordinate} containing all the legal moves
     * that can be made by a {@link Piece} in {@code pieces}.
     */
    public ArrayList<Coordinate> removeOwnCheck(Pieces pieces) {

        King potentialKing = null;
        boolean removeKingCastle = false;
        boolean removeQueenCastle = false;

        // get the raw moves available to the piece
        ArrayList<Coordinate> potentials = this.getRawMoves(pieces);

        // if no moves are possible, then no potential checks to remove
        if (potentials.size() == 0)
            return potentials;

        // use an iterator to remove moves
        Iterator<Coordinate> it = potentials.iterator();

        // iterate until every move has been checked
        while (it.hasNext()) {
            Coordinate nextMove = it.next();

            // perform a copy of the game board
            Pieces p = new Pieces(pieces);

            // move the current piece in the simulated board
            p.pieceMove(nextMove, this.makeCopy());

            // get the coordinate of the king with the same colour as the piece
            Coordinate kingPosition = p.findKing(this.getColour());

            // get all moves that can be performed by the opposition
            HashSet<Coordinate> dangerMoves = p.allColouredRaws(COLOUR.not(getColour()));

            // if any of the opposition moves can target the king
            if (dangerMoves.contains(kingPosition)) {
                // if the current piece is a king, need to remove the possibility of castling
                if (this.getName() == ID.KING) {
                    potentialKing = (King) this;
                    // remove the move if it involved kingside castling
                    if (nextMove.equals(potentialKing.getTransitionCoordKingK())) {
                        it.remove();
                        removeKingCastle = true;

                    }
                    // remove the move if it involved queenside castling
                    else if (nextMove.equals(potentialKing.getTransitionCoordKingQ())) {
                        it.remove();
                        removeQueenCastle = true;
                    } else {
                        it.remove();
                    }
                }
                // remove the move
                else {
                    it.remove();
                }
            }
        }
        if (potentialKing != null) {
            if (removeKingCastle)
                potentials.remove(potentialKing.getCastleCoordKingK());
            if (removeQueenCastle)
                potentials.remove(potentialKing.getCastleCoordKingQ());
        }

        return potentials;
    }

    /**
     * Updates the potential moves for a piece
     * @param pieces an instance of Pieces containing the information of the current playing board
     */
    public void updatePotentialMoves(Pieces pieces) {this.addMoves(removeOwnCheck(pieces));
    }

    /**
     * Determines whether a coordinate represents a valid move for the current instance of a piece. It checks that
     * the coordinate provided is within the piece's potential moves, and ascertains that the piece moving is of the colour
     * of the current turn of play.
     * @param destination a Coordinate representing a move destination
     * @param colour the colour corresponding to the side that is meant to play
     * @return true if it is the piece's current turn & the move is within it's potential moves
     */
    public boolean isValidMove(Coordinate destination, COLOUR colour) {
        return getPotentialMoves().contains(destination) && getColour() == colour;
    }
}

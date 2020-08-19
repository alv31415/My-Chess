import javax.swing.*;
import java.util.*;

/*
1) Class Constructors
2) Getters & Setters
3) Piece Movement Methods
4) toString() Methods
5) Overridden Methods
6) Abstract Methods
*/

/**
 * The Piece class is used to represent a generic chess piece. It acts as a super class for the 6 distinct pieces from chess.
 * The Piece class is also used to determine all the moves that a given piece can make within a given board.
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
    private final ID name;
    private final COLOUR colour;
    private Coordinate coords;
    private final Coordinate OGcoord;
    private final String pieceID; //potentially remove
    private HashSet<Coordinate> potentialMoves = new HashSet<>();
    public int dimension = BOARD.LAST_RANK.getRankVal();
    public int single = BOARD.FIRST_RANK.getRankVal();
    private boolean hasMoved = false;
    public static Piece emptyPiece = new Rook(COLOUR.W,Coordinate.emptyCoordinate);

    //________________________________________________Class Constructors________________________________________________

    public Piece (ID name, COLOUR colour, Coordinate OGcoord) {

        Objects.requireNonNull(name, "The piece must be correctly identified with an ID.");
        Objects.requireNonNull(colour, "The piece must be either white or black.");
        Objects.requireNonNull(OGcoord, "The piece must have an origin coordinate to be correctly initiallised.");

        this.name = name;
        this.colour = colour;
        this.OGcoord = OGcoord;
        coords = OGcoord;
        pieceID = "*"+name.toString()+"*"+colour.toString()+"*"+OGcoord.getFile()+"*";
    }

    public Piece (Piece original) {
        Objects.requireNonNull(original,"You can't copy a null piece");
        this.name = original.name;
        this.colour = original.colour;
        this.OGcoord = new Coordinate(original.OGcoord);
        this.coords = new Coordinate(original.coords);
        this.pieceID = original.pieceID;
        this.potentialMoves = new HashSet<>();

        for (Coordinate coord : original.getPotentialMoves()) {
            this.potentialMoves.add(new Coordinate(coord));
        }

        this.dimension = original.dimension;
        this.single = original.single;
        this.hasMoved = original.hasMoved;
    }

    //________________________________________________Getters & Setters________________________________________________

    public Coordinate getCoords() {
        return coords;
    }

    public char getFile() {
        return getCoords().getFile();
    }

    public int getRank() {
        return getCoords().getRank();
    }

    public COLOUR getColour() {
        return colour;
    }

    public ID getName() {
        return name;
    }

    public Coordinate getOGcoord() {
        return OGcoord;
    }

    public String getPieceID() {
        return pieceID;
    }

    public void setCoords(Coordinate coords) {
        this.coords = coords;
    }

    public boolean getHasMoved() {return hasMoved;}

    public void setHasMoved() {hasMoved = true;}

    public void addMoves(ArrayList<Coordinate> someMoves) {
        potentialMoves.addAll(someMoves);
    }

    public void clearMoves() {
        potentialMoves.clear();
    }

    public HashSet<Coordinate> getPotentialMoves() {
        return potentialMoves;
    }

    //________________________________________________Piece Movement Methods________________________________________________

    /**
     * Given a set of raw moves available to a piece, it removes those moves (coordinates) that would lead to check,
     * and so, would be illegal moves.
     * It uses an iterator to iterate through all raw moves of a piece. Then, it creates a temporary board (copying the current one),
     * and moves the piece to the raw move provided. If this causes check, then it removes the coordinate from the raw moves.
     * The resulting ArrayList will be used to update the potential moves of the piece.
     * @param pieces an instance of Pieces containing the information of the current playing board
     * @return an ArrayList of coordinates containing all legal moves that can be made by a Piece in a given board
     */

    public ArrayList<Coordinate> removeOwnCheck(Pieces pieces) {

        King potentialKing = null;
        boolean removeKingCastle = false;
        boolean removeQueenCastle = false;

        ArrayList<Coordinate> potentials = getRawMoves(pieces);

        if (potentials.size() == 0)
            return potentials;

        Iterator<Coordinate> it = potentials.iterator();

        while (it.hasNext()) {
            Coordinate nextMove = it.next();
            Pieces p = new Pieces(pieces);
            p.pieceMove(nextMove, this.makeCopy());
            Coordinate kingPosition = p.findKing(getColour());
            HashSet<Coordinate> dangerMoves = p.allColouredRaws(COLOUR.not(getColour()));
            if (dangerMoves.contains(kingPosition))
                if (this.getName() == ID.KING) { // we need to remove the possibility of castling if the King can be put in check
                    potentialKing = (King) this;
                    if (nextMove.equals(potentialKing.getTransitionCoordKingK())) {
                        it.remove();
                        removeKingCastle = true;
                    }
                    else if (nextMove.equals(potentialKing.getTransitionCoordKingQ())) {
                        it.remove();
                        removeQueenCastle = true;
                    }
                    else
                        it.remove();
                }
                else
                    it.remove();
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
    public void updatePotentialMoves(Pieces pieces) {addMoves(removeOwnCheck(pieces));
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
                OGcoord.equals(piece.OGcoord) &&
                pieceID.equals(piece.pieceID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, colour, OGcoord, pieceID);
    }


    //________________________________________________Abstract Methods________________________________________________


    /**
     * Provides all the raw moves (all moves, without considering potential cehcks) possible for a given piece within a certain board
     * The raw moves are calculated individually within a given piece
     * @param pieces the board being played in
     * @return an ArrayList containing all the raw move coordinates
     */

    public abstract ArrayList<Coordinate> getRawMoves(Pieces pieces);

    /**
     * @return the ImageIcon associated with a certain piece. This is assigned when the Piece is instantiated.
     * Used to assign the icons for the GUIBoard
     */

    public abstract ImageIcon getImageIcon();

    /**
     * Creates an exact copy of the instance of the piece
     * This is used to create copies of HashMaps, and for calculating potential moves
     * @return a copy of the piece instance
     */

    public abstract Piece makeCopy();
}

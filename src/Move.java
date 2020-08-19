import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/*
1) Null Messages
2) Support Methods
3) Vertical Movement Methods
4) Horizontal Movement Methods
5) Diagonal Right Movement Methods
6) Diagonal Left Movement Methods
7) Vertical Knight Movement Methods
8) Horizontal Knight Movement Methods
9) Move Input Methods
*/

/**
 * The Move class contains all methods that are used to find all raw moves for a given piece.
 * The move calculating methods all have a Pieces, Piece and int argument.
 * Pieces is where the game is being played
 * Piece is the piece that is being moved
 * the int (known as limit) is how many "steps" in a given direction we want to lok for
 * We have generic methods for calculating vertical, horizontal and diagonal moves in all directions.
 * We also have individual methods for calculating the moves of a Knight
 * We use a "factor" (either 1 or -1) to calculate raw coordinates, depending on whether the piece being moved is white or black
 * (black moving forward is the same direction as white moving backward)
 * For diagonals, we also have a factorV (for vertical movement) and factorH (for horizontal movement)
 * We determine all coordinates in a given direction, beginning from the position of a piece.
 * We return an ArrayList containing al these valid coordinates in the direction
 * If the coordinate is occupied, we check whether it is occupied by a piece of the same colour.
 * If it is of the same colour, we return the ArrayList of all the previous coordinates
 * If it is of different colour, we include the coordinate in the ArrayList and return it.
 * If the coordinate isn't occupied, we add it and continue checking in the given direction
 */

public class Move {

    //________________________________________________Null Messages________________________________________________

    private static final String nullPieces = "You can't play with a null board.";
    private static final String nullCoord = "Board coordinates must not be null.";

    //________________________________________________Support Methods________________________________________________

    /**
     * Given a certain pieces HashMap and a given Coordinate, checks whether the coordinate is occupied by a piece of agiven colour
     * @param pieces the board on which the game is being played
     * @param destination the coordinate being considered
     * @param colour the colour being considered
     * @return true if the given coordinate is occupied by a piece of the opposite colour (that is provided as an argument)
     * @throws NullPointerException if the coordinate or the pieces HashMap are null
     */

    public static boolean isNotTileColour(Pieces pieces, Coordinate destination, COLOUR colour) {
        Objects.requireNonNull(pieces,nullPieces);
        Objects.requireNonNull(destination,nullCoord);

        return !(pieces.getPieces().get(destination).getColour() == (colour));
    }

    /**
     * Given a certain pieces HashMap and a given Coordinate, checks whether the coordinate is occupied by a piece
     * @param pieces the board on which the game is being played
     * @param destination the coordinate being considered
     * @return true if the coordinate is occupied by a piece
     * @throws NullPointerException if the coordinate or the pieces HashMap are null
     */

    public static boolean tileFull (Pieces pieces, Coordinate destination) {

        Objects.requireNonNull(pieces,nullPieces);
        Objects.requireNonNull(destination,nullCoord);

        return pieces.getPieces().get(destination) != null;
    }

    //________________________________________________Vertical Movement Methods________________________________________________

    // checks vertical moves directly in front (that is, moves along the same file)

    public static ArrayList<Coordinate> frontFree(Pieces pieces, Piece piece, int limit) {

        ArrayList<Coordinate> moves = new ArrayList<>();
        int factor;

        if (piece.getColour().equals(COLOUR.B))
            factor = -1;
        else
            factor = 1;

        for (int advance = 1; advance <= limit; advance++) {
            int change = factor*advance;
            Coordinate checkCoord = new Coordinate(piece.getFile(), piece.getRank() + change);
            if (Coordinate.inBoard(checkCoord)) {
                boolean occupiedTile = tileFull(pieces, checkCoord);
                if (occupiedTile && isNotTileColour(pieces, checkCoord, piece.getColour())) {
                    moves.add(checkCoord);
                    return moves;
                }
                else if (occupiedTile)
                    return moves;
                else
                    moves.add(checkCoord);
            }
        }

        return moves;
    }

    // checks vertical moves directly behind (that is, moves along the same file)

    public static ArrayList<Coordinate> backFree(Pieces pieces, Piece piece, int limit) {

        ArrayList<Coordinate> moves = new ArrayList<>();
        int factor;

        if (piece.getColour().equals(COLOUR.B))
            factor = 1;
        else
            factor = -1;

        for (int advance = 1; advance <= limit; advance++) {
            int change = factor*advance;
            Coordinate checkCoord = new Coordinate(piece.getFile(),piece.getRank()+change);
            if (Coordinate.inBoard(checkCoord)) {
                boolean occupiedTile = tileFull(pieces, checkCoord);
                if (occupiedTile && isNotTileColour(pieces, checkCoord, piece.getColour())) {
                    moves.add(checkCoord);
                    return moves;
                }
                else if (occupiedTile)
                    return moves;
                else
                    moves.add(checkCoord);
            }
        }
        return moves;
    }

    //________________________________________________Horizontal Movement Methods________________________________________________

    // checks horizontal moves directly to the right (that is, moves along the same rank)

    public static ArrayList<Coordinate> rightFree(Pieces pieces, Piece piece, int limit) {

        ArrayList<Coordinate> moves = new ArrayList<>();
        int factor;

        if (piece.getColour().equals(COLOUR.B))
            factor = -1;
        else
            factor = 1;

        for (int advance = 1; advance <= limit; advance++) {
            int change = factor*advance;
            Coordinate checkCoord = new Coordinate((char) (piece.getFile()+change),piece.getRank());
            if (Coordinate.inBoard(checkCoord)) {
                boolean occupiedTile = tileFull(pieces, checkCoord);
                if (occupiedTile && isNotTileColour(pieces, checkCoord, piece.getColour())) {
                    moves.add(checkCoord);
                    return moves;
                }
                else if (occupiedTile)
                    return moves;
                else
                    moves.add(checkCoord);
            }
        }
        return moves;
    }

    // checks horizontal moves directly to the left (that is, moves along the same rank)

    public static ArrayList<Coordinate> leftFree(Pieces pieces, Piece piece, int limit) {

        ArrayList<Coordinate> moves = new ArrayList<>();
        int factor;

        if (piece.getColour().equals(COLOUR.B))
            factor = 1;
        else
            factor = -1;

        for (int advance = 1; advance <= limit; advance++) {
            int change = factor*advance;
            Coordinate checkCoord = new Coordinate((char) (piece.getFile()+change),piece.getRank());
            if (Coordinate.inBoard(checkCoord)) {
                boolean occupiedTile = tileFull(pieces, checkCoord);
                if (occupiedTile && isNotTileColour(pieces, checkCoord, piece.getColour())) {
                    moves.add(checkCoord);
                    return moves;
                }
                else if (occupiedTile)
                    return moves;
                else
                    moves.add(checkCoord);
            }
        }
        return moves;
    }

    //________________________________________________Diagonal Right Movement Methods________________________________________________

    // checks diagonal moves to the front and right

    public static ArrayList<Coordinate> frontRDigFree(Pieces pieces, Piece piece, int limit) {

        ArrayList<Coordinate> moves = new ArrayList<>();
        int factorV;
        int factorH;

        if (piece.getColour().equals(COLOUR.B)) {
            factorV = -1;
            factorH = -1;
        }
        else {
            factorV = 1;
            factorH = 1;
        }

        for (int advance = 1; advance <= limit; advance++) {
            int changeV = factorV*advance;
            int changeH = factorH*advance;
            Coordinate checkCoord = new Coordinate((char) (piece.getFile()+changeV),piece.getRank()+changeH);
            if (Coordinate.inBoard(checkCoord)) {
                boolean occupiedTile = tileFull(pieces, checkCoord);
                if (occupiedTile && isNotTileColour(pieces, checkCoord, piece.getColour())) {
                    moves.add(checkCoord);
                    return moves;
                }
                else if (occupiedTile)
                    return moves;
                else if (!tileFull(pieces, checkCoord))
                    moves.add(checkCoord);
            }
        }
        return moves;
    }

    // checks diagonal moves to the back and right

    public static ArrayList<Coordinate> backRDigFree(Pieces pieces, Piece piece, int limit) {

        ArrayList<Coordinate> moves = new ArrayList<>();
        int factorV;
        int factorH;

        if (piece.getColour().equals(COLOUR.B)) {
            factorV = -1;
            factorH = 1;
        }
        else {
            factorV = 1;
            factorH = -1;
        }

        for (int advance = 1; advance <= limit; advance++) {
            int changeV = factorV*advance;
            int changeH = factorH*advance;
            Coordinate checkCoord = new Coordinate((char) (piece.getFile()+changeV),piece.getRank()+changeH);
            if (Coordinate.inBoard(checkCoord)) {
                boolean occupiedTile = tileFull(pieces, checkCoord);
                if (occupiedTile && isNotTileColour(pieces, checkCoord, piece.getColour())) {
                    moves.add(checkCoord);
                    return moves;
                }
                else if (occupiedTile)
                    return moves;
                else
                    moves.add(checkCoord);
            }
        }
        return moves;
    }

    //________________________________________________Diagonal Left Movement Methods________________________________________________

    // checks diagonal moves to the back and left

    public static ArrayList<Coordinate> backLDigFree(Pieces pieces, Piece piece, int limit) {

        ArrayList<Coordinate> moves = new ArrayList<>();
        int factorV;
        int factorH;

        if (piece.getColour().equals(COLOUR.B)) {
            factorV = 1;
            factorH = 1;
        }
        else {
            factorV = -1;
            factorH = -1;
        }

        for (int advance = 1; advance <= limit; advance++) {
            int changeV = factorV*advance;
            int changeH = factorH*advance;
            Coordinate checkCoord = new Coordinate((char) (piece.getFile()+changeV),piece.getRank()+changeH);
            if (Coordinate.inBoard(checkCoord)) {
                boolean occupiedTile = tileFull(pieces, checkCoord);
                if (occupiedTile && isNotTileColour(pieces, checkCoord, piece.getColour())) {
                    moves.add(checkCoord);
                    return moves;
                }
                else if (occupiedTile)
                    return moves;
                else
                    moves.add(checkCoord);
            }
        }
        return moves;
    }

    // checks diagonal moves to the front and left

    public static ArrayList<Coordinate> frontLDigFree(Pieces pieces, Piece piece, int limit) {

        ArrayList<Coordinate> moves = new ArrayList<>();
        int factorV;
        int factorH;

        if (piece.getColour().equals(COLOUR.B)) {
            factorV = 1;
            factorH = -1;
        }
        else {
            factorV = -1;
            factorH = 1;
        }

        for (int advance = 1; advance <= limit; advance++) {
            int changeV = factorV*advance;
            int changeH = factorH*advance;
            Coordinate checkCoord = new Coordinate((char) (piece.getFile()+changeV),piece.getRank()+changeH);
            if (Coordinate.inBoard(checkCoord)) {
                boolean occupiedTile = tileFull(pieces, checkCoord);
                if (occupiedTile && isNotTileColour(pieces, checkCoord, piece.getColour())) {
                    moves.add(checkCoord);
                    return moves;
                }
                else if (occupiedTile)
                    return moves;
                else
                    moves.add(checkCoord);
            }
        }
        return moves;
    }

    //________________________________________________Vertical Knight Movement Methods________________________________________________

    // checks Knight moves directly in front

    public static ArrayList<Coordinate> frontKnight(Pieces pieces, Piece piece) {

        ArrayList<Coordinate> moves = new ArrayList<>();
        int factor;
        int sideDistance = 1;

        if (piece.getColour().equals(COLOUR.B)) {
            factor = -1;
        }
        else {
            factor = 1;
        }

        int changeV = 2*factor;
        int newRank = piece.getRank() + changeV;

        //right and left taken with reference to white pieces
        Coordinate frontRight = new Coordinate((char) (piece.getFile() + sideDistance),newRank);
        Coordinate frontLeft = new Coordinate((char) (piece.getFile() - sideDistance),newRank);

        if (Coordinate.inBoard(frontLeft)) {
            boolean occupiedTile = tileFull(pieces, frontLeft);
            if (!occupiedTile || isNotTileColour(pieces, frontLeft, piece.getColour()))
                moves.add(frontLeft);
        }

        if (Coordinate.inBoard(frontRight)) {
            boolean occupiedTile = tileFull(pieces, frontRight);
            if (!occupiedTile || isNotTileColour(pieces, frontRight, piece.getColour()))
                moves.add(frontRight);
        }
        return moves;
    }

    // checks Knight moves directly behind

    public static ArrayList<Coordinate> backKnight(Pieces pieces, Piece piece) {

        ArrayList<Coordinate> moves = new ArrayList<>();
        int factor;
        int sideDistance = 1;

        if (piece.getColour().equals(COLOUR.B)) {
            factor = 1;
        }
        else {
            factor = -1;
        }

        int changeV = 2*factor;
        int newRank = piece.getRank() + changeV;

        //right and left taken with reference to white pieces
        Coordinate backRight = new Coordinate((char) (piece.getFile() + sideDistance),newRank);
        Coordinate backLeft = new Coordinate((char) (piece.getFile() - sideDistance),newRank);

        if (Coordinate.inBoard(backLeft)) {
            boolean occupiedTile = tileFull(pieces, backLeft);
            if (!occupiedTile || isNotTileColour(pieces, backLeft, piece.getColour()))
                moves.add(backLeft);
        }

        if (Coordinate.inBoard(backRight)) {
            boolean occupiedTile = tileFull(pieces, backRight);
            if (!occupiedTile || isNotTileColour(pieces, backRight, piece.getColour()))
                moves.add(backRight);
        }
        return moves;
    }

    //________________________________________________Horizontal Knight Movement Methods________________________________________________

    // checks Knight moves directly to the right

    public static ArrayList<Coordinate> rightKnight(Pieces pieces, Piece piece) {

        ArrayList<Coordinate> moves = new ArrayList<>();
        int factor;
        int sideDistance = 1;

        if (piece.getColour().equals(COLOUR.B)) {
            factor = -1;
        }
        else {
            factor = 1;
        }

        int changeH = 2*factor;
        char newFile = (char) (piece.getFile() + changeH);

        //right and left taken with reference to white pieces
        Coordinate rightTop = new Coordinate(newFile,piece.getRank() + sideDistance);
        Coordinate rightBottom = new Coordinate(newFile, piece.getRank() - sideDistance);

        if (Coordinate.inBoard(rightTop)) {
            boolean occupiedTile = tileFull(pieces, rightTop);
            if (!occupiedTile || isNotTileColour(pieces, rightTop, piece.getColour()))
                moves.add(rightTop);
        }

        if (Coordinate.inBoard(rightBottom)) {
            boolean occupiedTile = tileFull(pieces, rightBottom);
            if (!occupiedTile || isNotTileColour(pieces, rightBottom, piece.getColour()))
                moves.add(rightBottom);
        }
        return moves;
    }

    // checks Knight moves directly to the left

    public static ArrayList<Coordinate> leftKnight(Pieces pieces, Piece piece) {

        ArrayList<Coordinate> moves = new ArrayList<>();
        int factor;
        int sideDistance = 1;

        if (piece.getColour().equals(COLOUR.B)) {
            factor = 1;
        }
        else {
            factor = -1;
        }

        int changeH = 2*factor;
        char newFile = (char) (piece.getFile() + changeH);

        //right and left taken with reference to white pieces
        Coordinate leftTop = new Coordinate(newFile,piece.getRank() + sideDistance);
        Coordinate leftBottom = new Coordinate(newFile, piece.getRank() - sideDistance);

        if (Coordinate.inBoard(leftBottom)) {
            boolean occupiedTile = tileFull(pieces, leftBottom);
            if (!occupiedTile || isNotTileColour(pieces, leftBottom, piece.getColour()))
                moves.add(leftBottom);
        }

        if (Coordinate.inBoard(leftTop)) {
            boolean occupiedTile = tileFull(pieces, leftTop);
            if (!occupiedTile || isNotTileColour(pieces, leftTop, piece.getColour()))
                moves.add(leftTop);
        }
        return moves;
    }

    //________________________________________________Move Input Methods________________________________________________

    /**
     * Uses a scanner input to provide origin and destination coordinates for a move in a TBIBoard.
     * It checks that the inputted String is in the correct format,
     * and uses a while loop to continue asking for input until a valid format is provided
     * @param sc the scanner being used to get the input
     * @return a String array, containing two coordinates
     */

    public static String[] moveQuery(Scanner sc) {
        String[] move = new String[2];
        boolean validFormat = false;

        while (!validFormat) {
            System.out.println("Enter a piece and its destination. For example, to move a piece in g1 to h3, write \"g1 h3\".");
            String userInput = sc.nextLine();

            if (userInput.length() != 5 || !userInput.contains(" ")) {
                System.out.println("Move instructions provided in an incorrect format. Try again!");
            }
            else {
                move = userInput.split(" ");
                validFormat = true;
            }
        }
        return move;
    }

}

import java.util.*;

/*
1) Class Constructors
2) Collection Copying
3) Getters & Setters
4) Piece Related Methods
5) Board Distribution Related Methods
6) Game Logic Methods
7) Piece Movement Methods
8) Overridden Methods
*/

/**
 * The Pieces class is used to represent the board that is being played with.
 * It contains the position of all pieces, alongside all the methods that handle game logic,
 * such as checking when a game ends or handling the movement of a piece.
 */

public class Pieces {

    /**
     * pieces is a HashMap which uses a Coordinate-Piece key-value pair,
     * with the Coordinate representing the current position of the Piece
     *
     * previousPieces is a HashMap which uses a Coordinate-Piece key-value pair, used of a representation of the previous board
     * isCapture is a boolean value, used to determine whether a given move has led to a capture
     * isGUIGame is a boolean value, used to determine whether the current game being played is via a GUIBoard or a TBIBoard
     *
     * gameProgress is an ArrayList, containg HashMaps with Coordinate-Piece key-value pairs,
     * used to store the board's development as a game is played.
     * This is used to determine whether there is a draw by threefold repetition
     */

    private HashMap<Coordinate, Piece> pieces;
    private HashMap<Coordinate, Piece> previousPieces;
    private boolean isCapture;
    private boolean isGUIGame;
    private ArrayList<HashMap<Coordinate,Piece>> gameProgress = new ArrayList<>();

    //________________________________________________Class Constructors________________________________________________

    public Pieces() {
        pieces = Boards.getChessBoard();
        previousPieces = copyHashMap(pieces);
        gameProgress.add(copyHashMap(pieces));
        updatePotentials(); // when we instantiate Pieces, we immediately calculte the potential moves to begin the game
    }

    public Pieces(HashMap<Coordinate, Piece> newBoard) {
        pieces = newBoard;
        previousPieces = copyHashMap(pieces);
        gameProgress.add(copyHashMap(pieces));
        updatePotentials();
    }

    public Pieces (Pieces original) { // we create a copy constructor to help calculate potential moves
        this.pieces = copyHashMap(original.getPieces());
        this.previousPieces = original.previousPieces;
        this.isCapture = original.isCapture;
        this.isGUIGame = original.isGUIGame;
        this.gameProgress = copyArrayHash(original.getGameProgress());
    }

    //________________________________________________Collection Copying________________________________________________

    private HashMap<Coordinate, Piece> copyHashMap (HashMap<Coordinate, Piece> original) { // creates a copy of a pieces HashMap

        HashMap<Coordinate, Piece> copyMap = new HashMap<>();
        for (Coordinate key : original.keySet()) {
            Coordinate newKey = new Coordinate(key);
            Piece newPiece = original.get(key).makeCopy();
            copyMap.put(newKey,newPiece);
        }

        return copyMap;
    }

    private ArrayList<HashMap<Coordinate,Piece>> copyArrayHash (ArrayList<HashMap<Coordinate,Piece>> original) { // creates a copy of a gameProgress ArrayList
        ArrayList<HashMap<Coordinate,Piece>> copyList = new ArrayList<>();
        for (HashMap<Coordinate,Piece> game : original) {
            copyList.add(copyHashMap(game));
        }

        return copyList;
    }

    //________________________________________________Getters & Setters________________________________________________

    public HashMap<Coordinate, Piece> getPieces() {
        return pieces;
    }

    public void setPieces(HashMap<Coordinate,Piece> pieces) {this.pieces = pieces;}

    public boolean getIsCapture() {
        return isCapture;
    }

    public void setIsCapture(boolean captureStatus) {
        this.isCapture = captureStatus;
    }

    public HashMap<Coordinate, Piece> getPreviousPieces() {
        return previousPieces;
    }

    public void setPreviousPieces(HashMap<Coordinate, Piece> previousPieces) {
        this.previousPieces = copyHashMap(previousPieces);
    }

    public ArrayList<HashMap<Coordinate, Piece>> getGameProgress() {
        return gameProgress;
    }

    public void setGUIGame (boolean GUIStatus) {
        isGUIGame = GUIStatus;
    }

    //________________________________________________Piece Related Methods________________________________________________

    /**
     * Adds a Coordinate-Piece key-value pair to the pieces HashMap (used when handling a piece movement)
     * @param coordinate the destination coordinate for a given move
     * @param piece the piece making the move
     */

    public void addPiece(Coordinate coordinate, Piece piece) {
        pieces.put(coordinate,piece);
    }

    /**
     * Finds a given piece within the current instance of Pieces
     * @param piece the piece that is looked for
     * @return the Coordinate that a given piece occupies. If the piece isn't in the board, returns the emptyCoordinate
     * @throws NullPointerException if the piece provided isn't instantiated
     */

    public Coordinate findPiece(Piece piece) {

        Objects.requireNonNull(piece, "Provide an existing piece. It can't be null.");

        for (Coordinate key : pieces.keySet()) {
            if (pieces.get(key).equals(piece))
                return key;
        }
        System.err.println(piece.getName().toFullString() +" not found.");
        return Coordinate.emptyCoordinate;
    }

    /**
     * Finds a given king within the current instance of Pieces
     * @param colour the colour of the king that is being looked for
     * @return the Coordinate that the King occupies. If the King isn't in the board, returns the emptyCoordinate
     */

    public Coordinate findKing(COLOUR colour) {
        for (Coordinate key : pieces.keySet()) {
            Piece potentialKing = pieces.get(key);
            if (potentialKing.getName().equals(ID.KING) && (potentialKing.getColour() == colour))
                return key;
        }
        String pieceNotInBoard = "King not found. Assuming it isn't in board. Empty coordinate provided.";
        System.err.println(pieceNotInBoard);
        return Coordinate.emptyCoordinate;
    }

    /**
     * Returns a piece within the current instance of Pieces, given a Coordinate
     * @param coordinate the coordinate that is being looked for within the board
     * @return the Piece occupying the given coordinate. If there is no piece at the coordinate, returns the emptyPiece
     * @throws NullPointerException if the Coordinate provided isn't instantiated
     */

    public Piece getPiece(Coordinate coordinate) {

        Objects.requireNonNull(coordinate, "Provide an existing coordinate. It can't be null.");

        for (Coordinate key : pieces.keySet()) {
            if (key.equals(coordinate))
                return pieces.get(key);
        }
        System.err.println("There is no piece in this coordinate. Empty piece provided.");
        return Piece.emptyPiece;
    }

    //________________________________________________Board Distribution Related Methods________________________________________________

    /**
     * Provides a HashMap containing the coordinates and pieces of a given colour
     * @param colour the colour of the pieces of interest
     * @return a HashMap of the form of pieces, containing only those pieces of a given colour
     */

    public HashMap<Coordinate, Piece> getColourPieces(COLOUR colour) {
        HashMap<Coordinate,Piece> colours = new HashMap<>();
        for (Coordinate key : pieces.keySet()) {
            Piece piece = pieces.get(key);
            if (piece.getColour() == colour)
                colours.put(key,piece);
        }
        return colours;
    }

    /**
     * Provides a HashSet of all the potential moves of the pieces of a given colour
     * @param colour the colour of the pieces of interest
     * @return a HashSet of Coordinates, containing all the coordinates that pieces of a given colour can go to
     */

    public HashSet<Coordinate> allColouredPotentials (COLOUR colour) {
        HashSet<Coordinate> allMoves = new HashSet<>();
        HashMap<Coordinate, Piece> allColoured = getColourPieces(colour);
        for (Piece piece : allColoured.values()){
            allMoves.addAll(piece.getPotentialMoves());
        }
        return allMoves;
    }

    /**
     * Provides a HashSet of all the raw moves of the pieces of a given colour
     * @param colour the colour of the pieces of interest
     * @return a HashSet of Coordinates, containing all the raw coordinates that pieces of a given colour can go to
     */

    public HashSet<Coordinate> allColouredRaws (COLOUR colour) {
        HashSet<Coordinate> allMoves = new HashSet<>();
        HashMap<Coordinate, Piece> allColoured = getColourPieces(colour);
        for (Piece piece : allColoured.values()){
            allMoves.addAll(piece.getRawMoves(this));
        }
        return allMoves;
    }

    /**
     * For all pawns in the current board, updates the previous coordinate that they occupied after a move is made.
     * This is used to validate en passant captures.
     */

    public void updatePreviousMovePawns () {
        for (Piece potentialPawn : pieces.values()){
            if (potentialPawn.getName() == ID.PAWN) {
                Pawn pawn = (Pawn) potentialPawn;
                pawn.setPreviousCoordinate(pawn.getCoords());
            }
        }
    }

    /**
     * For a given piece, determines whether there is a piece in the same file of the same type (i.e same ID)
     * @param piece the piece that is being considered
     * @return true if and only if there is a piece, of the same colour, of the same type in the same file as the argument piece
     * within the given board
     */

    public boolean pieceInSameFile (Piece piece) {

        if (piece.getName() == ID.KING)
            return false;

        HashMap <Coordinate, Piece> coloured = getColourPieces(piece.getColour());
        for (Piece value : coloured.values()) {
            if (value.getName() == piece.getName() && value.getFile() == piece.getFile() && !value.equals(piece))
                return true;
        }
        return false;
    }

    /**
     * For a given piece, determines whether there is a piece in the same rank of the same type (i.e same ID)
     * @param piece the piece that is being considered
     * @return true if and only if there is a piece, of the same colour, of the same type in the same rank as the argument piece
     * within the given board
     */

    public boolean pieceInSameRank (Piece piece) {

        if (piece.getName() == ID.KING)
            return false;

        HashMap <Coordinate, Piece> coloured = getColourPieces(piece.getColour());
        for (Piece value : coloured.values()) {
            if (value.getName() == piece.getName() && value.getRank() == piece.getRank() && !value.equals(piece))
                return true;
        }
        return false;
    }

    /**
     * Determines whether there are 2 pieces of the same type within the board that can go to the same coordinate
     * @param coordinate the coordinate that is being considered
     * @param piece the piece that is being considered
     * @return true if and only if there is another piece of the same type and colour as the argument piece,
     * and if both can go to the given argument coordinate.
     * @throws AssertionError if the coordinate provided isn't within the argument piece's potential moves
     */

    public boolean pieceToSameCoordinate (Coordinate coordinate, Piece piece) {
        assert piece.getPotentialMoves().contains(coordinate);

        if (piece.getName() == ID.KING)
            return false;

        HashMap <Coordinate, Piece> coloured = getColourPieces(piece.getColour());
        for (Piece value : coloured.values()) {
            if (value.getName() == piece.getName() && value.getPotentialMoves().contains(coordinate) && !value.equals(piece))
                return true;
        }
        return false;
    }

    //________________________________________________Game Logic Methods________________________________________________

    /**
     * Determines whether there is check on the board.
     * is in the potential moves of the pieces of the opposite colour
     * @param colour the colour that could be in check
     * @return true if the king of the given colour is in check.
     * Calculated by considering whether the coordinate of the king of a given colour
     */

    public boolean isCheck(COLOUR colour) { //check against the given colour
        Coordinate kingPosition = findKing(colour);

        if (kingPosition.equals(Coordinate.emptyCoordinate))
            throw new IllegalArgumentException("There is no king in the board. This is an illegal game!");

        HashSet<Coordinate> dangerMoves = allColouredPotentials(COLOUR.not(colour));
        return (dangerMoves.contains(kingPosition));
    }

    /**
     * Determines whether there is mate on the board.
     * @param colour the colour that could be in mate
     * @return true if the King of the given colour is in check, and all the pieces of that colour have no possible moves (no potential moves)
     */

    public boolean isMate(COLOUR colour) {
        HashSet<Coordinate> allMoves = allColouredPotentials(colour);
        return isCheck(colour) && (allMoves.size() == 0);
    }

    /**
     * Determines whether there is a draw on the board.
     * @return true if:
     * there are only 2 kings in the board
     * there are only two kings and a bishop/knight
     * there are only two kings and two bishops of opposite sides but same diagonal colour (bishops can only move on black or white squares)
     * there is threefold repetition (the same position is repeated 3 times, at any time during the game).
     * This is ascertained by looping through gameProgress, and checking whether there are 3 equal HashMaps.
     */

    public boolean isDraw() {

        int n = gameProgress.size();

        boolean twoKings = !findKing(COLOUR.B).equals(Coordinate.emptyCoordinate) && !findKing(COLOUR.W).equals(Coordinate.emptyCoordinate);

        if (getPieces().size() == 2) // only 2 kings
            return twoKings;
        else if (getPieces().size() == 3) { // 2 kings and a bishop/knight
            int counter = 0;
            for (Piece piece : this.getPieces().values()) {
                if (piece.getName() == ID.BISHOP || piece.getName() == ID.KNIGHT)
                    counter++;
            }
            return twoKings && counter == 1;
        }
        else if (getPieces().size() == 4) { // 2 kings and 2 bishops with same diagonal colour
            int counterB = 0;
            Bishop bishopB = null;
            int counterW = 0;
            Bishop bishopW = null;
            for (Piece piece : this.getPieces().values()) {
                if (piece.getName() == ID.BISHOP) {
                    if (piece.getColour() == COLOUR.B) {
                        bishopB = (Bishop) piece;
                        counterB++;
                    }
                    else {
                        bishopW = (Bishop) piece;
                        counterW++;
                    }
                }
            }

            boolean sameColourBishops = counterB == 1 &&
                                        counterW == 1 &&
                                        bishopB.getOGcoord().getFile() != bishopW.getOGcoord().getFile();

            return twoKings && sameColourBishops;
        }
        else if (n >= 3){ // threefold repetition
            for (HashMap<Coordinate, Piece> currentGame : gameProgress) {
                int counter = 0;
                for (HashMap<Coordinate, Piece> checkGame : gameProgress) {
                    if (currentGame.equals(checkGame)) {
                        counter++;
                    }
                }
                if (counter == 3)
                    return true;
            }

        }

        return false;

    }

    /**
     * Determines whether there is a stalemate on the board.
     * @param colour the colour of the turn in which a potential stalemating move has been made
     * @return true if there isn't check in the board, but the pieces of the opposite colour have no potential moves
     */

    public boolean isStalemate(COLOUR colour) {
        HashSet<Coordinate> allMoves = allColouredPotentials(COLOUR.not(colour));
        return allMoves.size() == 0 && !isCheck(COLOUR.not(colour));

    }

    //________________________________________________Piece Movement Methods________________________________________________

    /**
     * Given a destination coordinate and a piece, moves the piece to that coordinate.
     * This is done by adding the piece and the coordinate to the pieces HashMap.
     * It sets the new coordinates for the pieces, sets "hasMoved" to true, and removes the previous coordinate of the piece
     * within the pieces HashMap.
     * @param coordinate the destination coordinate for a given move
     * @param piece the piece that is making the move
     */

    public void pieceMove (Coordinate coordinate, Piece piece) {
        Coordinate pieceCoord = findPiece(piece);
        addPiece(coordinate, piece);
        piece.setCoords(coordinate);
        piece.setHasMoved();
        pieces.remove(pieceCoord);
    }

    /**
     * Given a destination coordinate and a piece, changes the pieces HashMap accordingly.
     * It sets the previous pieces and determines whether there has been a capture.
     * After making the move, the potential moves of the new pieces are recalculated,
     * and the given pieces HashMap are added to gameProgress.
     * For all pieces except the King and the Pawn, pieceMove is used.
     * For King, we consider whether castling is possible. If so, the king and corresponding rook are moved.
     * For Pawn, we consider promotion (executing a promotion query) and en passant capture.
     * @param coordinate the destination coordinate for a given move
     * @param piece the piece that is making the move
     */

    public void makeMove (Coordinate coordinate, Piece piece) {

        if (piece.isValidMove(coordinate, piece.getColour())) {
            setPreviousPieces(this.getPieces());
            isCapture = Move.tileFull(this, coordinate) && Move.isNotTileColour(this,coordinate, piece.getColour());
            if (piece.getName() == ID.KING) {
                King castleKing = (King) piece;
                if (castleKing.canCastleQueen(this) && coordinate.equals(castleKing.getCastleCoordKingQ()) && !isCheck(castleKing.getColour())) {
                    assert !findPiece(castleKing.getRookQueen()).equals(Coordinate.emptyCoordinate);
                    pieceMove(coordinate,castleKing);
                    pieceMove(castleKing.getRookQueen().getCastleCoordRook(),castleKing.getRookQueen());
                }
                else if (castleKing.canCastleKing(this) && coordinate.equals(castleKing.getCastleCoordKingK()) && !isCheck(castleKing.getColour())) {
                    assert !findPiece(castleKing.getRookKing()).equals(Coordinate.emptyCoordinate);
                    pieceMove(coordinate,castleKing);
                    pieceMove(castleKing.getRookKing().getCastleCoordRook(),castleKing.getRookKing());
                }
                else {
                    pieceMove(coordinate, castleKing);
                }
            }
            else if (piece.getName() == ID.PAWN) {
                Pawn pawn = (Pawn) piece;

                updatePreviousMovePawns();
                if (Math.abs(coordinate.getRank() - pawn.getRank()) == 2)
                    pawn.setHasMovedTwo();

                if (pawn.canPromoteBlack(coordinate) || pawn.canPromoteWhite(coordinate)) {
                    Piece toPromote;

                    if (isGUIGame) {
                        pawn.GUIPromotionQuery(coordinate);
                        toPromote = pawn.getPromotedPiece();
                    }
                    else {
                        toPromote = pawn.promotionQuery(coordinate);
                    }
                    Coordinate pieceCoord = findPiece(piece);
                    addPiece(coordinate, toPromote);
                    pieces.remove(pieceCoord);
                }
                else if (pawn.getEnPassantLeft()) {
                    Coordinate left = Move.leftFree(this,pawn,1).get(0);
                    setIsCapture(true);
                    pieces.remove(left);
                    pieceMove(coordinate,pawn);
                }
                else if (pawn.getEnPassantRight()) {
                    Coordinate right = Move.rightFree(this,pawn,1).get(0);
                    setIsCapture(true);
                    pieces.remove(right);
                    pieceMove(coordinate,pawn);
                }
                else {
                    pieceMove(coordinate, pawn);
                }
            }
            else {
                pieceMove(coordinate, piece);
            }
        }
        else
            System.err.println(piece.getName().toFullString() + " to " + coordinate.toString() + " is an invalid move.");

        gameProgress.add(copyHashMap(pieces));
        updatePotentials();

    }

    /**
     * Given a new pieces HashMap, recalculates all potential moves of all the pieces in the HashMap
     */

    public void updatePotentials() {

        for (Piece value : pieces.values()) {
            value.clearMoves();
            value.updatePotentialMoves(this);
        }
    }

    //________________________________________________Overridden Methods________________________________________________

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        pieces.forEach((coord, piece) -> str.append(piece.getPieceID())
                                .append(" is at ")
                                .append(coord.toString())
                                .append("\n"));

        return str.toString();
        }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pieces pieces1 = (Pieces) o;
        return Objects.equals(pieces, pieces1.pieces);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieces);
    }
}

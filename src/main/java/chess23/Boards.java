package chess23;

import java.util.HashMap;

/**
 * Utility class used to provide prepared chess board representations.
 */
public class Boards {

    /**
     * @return a {@link HashMap} of {@link Coordinate} - {@link Piece} pairs,
     * representing the starting positions of a chess game.
     */
    public static HashMap<Coordinate, Piece> getChessBoard() {

        HashMap <Coordinate, Piece> chessPieces = new HashMap<>();

        int pawnBRank = 7;
        int bRank = 8;
        int pawnWRank = 2;
        int wRank = 1;

        //________________________________________________Black Pawns________________________________________________

        Pawn pawnBa = new Pawn(COLOUR.B, new Coordinate('a', pawnBRank));
        Pawn pawnBb = new Pawn(COLOUR.B, new Coordinate('b', pawnBRank));
        Pawn pawnBc = new Pawn(COLOUR.B, new Coordinate('c', pawnBRank));
        Pawn pawnBd = new Pawn(COLOUR.B, new Coordinate('d', pawnBRank));
        Pawn pawnBe = new Pawn(COLOUR.B, new Coordinate('e', pawnBRank));
        Pawn pawnBf = new Pawn(COLOUR.B, new Coordinate('f', pawnBRank));
        Pawn pawnBg = new Pawn(COLOUR.B, new Coordinate('g', pawnBRank));
        Pawn pawnBh = new Pawn(COLOUR.B, new Coordinate('h', pawnBRank));

        //________________________________________________Black Rooks________________________________________________

        Rook rookBa = new Rook(COLOUR.B, new Coordinate('a', bRank));
        Rook rookBh = new Rook(COLOUR.B, new Coordinate('h', bRank));

        //________________________________________________Black Knights________________________________________________

        Knight knightBb = new Knight(COLOUR.B, new Coordinate('b', bRank));
        Knight knightBg = new Knight(COLOUR.B, new Coordinate('g', bRank));

        //________________________________________________Black Bishops________________________________________________

        Bishop bishopBc = new Bishop(COLOUR.B, new Coordinate('c', bRank));
        Bishop bishopBf = new Bishop(COLOUR.B, new Coordinate('f', bRank));

        //________________________________________________Black Queen________________________________________________

        Queen queenB = new Queen(COLOUR.B, new Coordinate('d', bRank));

        //________________________________________________Black King________________________________________________

        King kingB = new King(COLOUR.B, new Coordinate('e', bRank));

        //________________________________________________White Pawns________________________________________________

        Pawn pawnWa = new Pawn(COLOUR.W, new Coordinate('a', pawnWRank));
        Pawn pawnWb = new Pawn(COLOUR.W, new Coordinate('b', pawnWRank));
        Pawn pawnWc = new Pawn(COLOUR.W, new Coordinate('c', pawnWRank));
        Pawn pawnWd = new Pawn(COLOUR.W, new Coordinate('d', pawnWRank));
        Pawn pawnWe = new Pawn(COLOUR.W, new Coordinate('e', pawnWRank));
        Pawn pawnWf = new Pawn(COLOUR.W, new Coordinate('f', pawnWRank));
        Pawn pawnWg = new Pawn(COLOUR.W, new Coordinate('g', pawnWRank));
        Pawn pawnWh = new Pawn(COLOUR.W, new Coordinate('h', pawnWRank));

        //________________________________________________White Rooks________________________________________________

        Rook rookWa = new Rook(COLOUR.W, new Coordinate('a', wRank));
        Rook rookWh = new Rook(COLOUR.W, new Coordinate('h', wRank));

        //________________________________________________White Knights________________________________________________

        Knight knightWb = new Knight(COLOUR.W, new Coordinate('b', wRank));
        Knight knightWg = new Knight(COLOUR.W, new Coordinate('g', wRank));

        //________________________________________________White Bishops________________________________________________

        Bishop bishopWc = new Bishop(COLOUR.W, new Coordinate('c', wRank));
        Bishop bishopWf = new Bishop(COLOUR.W, new Coordinate('f', wRank));

        //________________________________________________White Queen________________________________________________

        Queen queenW = new Queen(COLOUR.W, new Coordinate('d', wRank));

        //________________________________________________White King________________________________________________

        King kingW = new King(COLOUR.W, new Coordinate('e', wRank));

        //________________________________________________Place Black in HashMap________________________________________________

        chessPieces.put(pawnBa.getCoords(), pawnBa);
        chessPieces.put(pawnBb.getCoords(), pawnBb);
        chessPieces.put(pawnBc.getCoords(), pawnBc);
        chessPieces.put(pawnBd.getCoords(), pawnBd);
        chessPieces.put(pawnBe.getCoords(), pawnBe);
        chessPieces.put(pawnBf.getCoords(), pawnBf);
        chessPieces.put(pawnBg.getCoords(), pawnBg);
        chessPieces.put(pawnBh.getCoords(), pawnBh);

        chessPieces.put(rookBa.getCoords(), rookBa);
        chessPieces.put(rookBh.getCoords(), rookBh);

        chessPieces.put(knightBb.getCoords(), knightBb);
        chessPieces.put(knightBg.getCoords(), knightBg);

        chessPieces.put(bishopBc.getCoords(), bishopBc);
        chessPieces.put(bishopBf.getCoords(), bishopBf);

        chessPieces.put(queenB.getCoords(), queenB);

        chessPieces.put(kingB.getCoords(), kingB);

        //________________________________________________Place White in HashMap________________________________________________

        chessPieces.put(pawnWa.getCoords(), pawnWa);
        chessPieces.put(pawnWb.getCoords(), pawnWb);
        chessPieces.put(pawnWc.getCoords(), pawnWc);
        chessPieces.put(pawnWd.getCoords(), pawnWd);
        chessPieces.put(pawnWe.getCoords(), pawnWe);
        chessPieces.put(pawnWf.getCoords(), pawnWf);
        chessPieces.put(pawnWg.getCoords(), pawnWg);
        chessPieces.put(pawnWh.getCoords(), pawnWh);

        chessPieces.put(rookWa.getCoords(), rookWa);
        chessPieces.put(rookWh.getCoords(), rookWh);

        chessPieces.put(knightWb.getCoords(), knightWb);
        chessPieces.put(knightWg.getCoords(), knightWg);

        chessPieces.put(bishopWc.getCoords(), bishopWc);
        chessPieces.put(bishopWf.getCoords(), bishopWf);

        chessPieces.put(queenW.getCoords(), queenW);

        chessPieces.put(kingW.getCoords(), kingW);

        return chessPieces;
    }

    /**
     * @return a {@link HashMap} of {@link Coordinate} - {@link Piece} pairs,
     * representing a chess board which can be used for testing.
     */
    public static HashMap<Coordinate, Piece> getTestBoard() {

        HashMap <Coordinate, Piece> chessPieces = new HashMap<>();

        int pawnBRank = 7;
        int bRank = 8;
        int pawnWRank = 2;
        int wRank = 1;

        //________________________________________________Black Pawns________________________________________________

        Pawn pawnB = new Pawn(COLOUR.B, new Coordinate('g', pawnBRank));

        //________________________________________________Black Rooks________________________________________________

        Rook rookB = new Rook(COLOUR.B, new Coordinate('a', bRank));

        //________________________________________________Black Knights________________________________________________

        Knight knightB = new Knight(COLOUR.B, new Coordinate('h', 7));

        //________________________________________________Black Bishops________________________________________________


        //________________________________________________Black Queen________________________________________________

        Queen queenB = new Queen(COLOUR.B, new Coordinate('d', bRank));

        //________________________________________________Black King________________________________________________

        //________________________________________________White Pawns________________________________________________

        Pawn pawnWe = new Pawn(COLOUR.W, new Coordinate('e', pawnWRank));
        Pawn pawnWh = new Pawn(COLOUR.W, new Coordinate('h', pawnWRank));

        //________________________________________________White Rooks________________________________________________

        Rook rookWa = new Rook(COLOUR.W, new Coordinate('a', wRank));
        Rook rookWh = new Rook(COLOUR.W, new Coordinate('h', wRank));

        //________________________________________________White Knights________________________________________________

        //________________________________________________White Bishops________________________________________________

        Bishop bishopW = new Bishop(COLOUR.W, new Coordinate('f', 6));

        //________________________________________________White Queen________________________________________________

        Queen queenW = new Queen(COLOUR.W, new Coordinate('g', 5));

        //________________________________________________White King________________________________________________

        King kingW = new King(COLOUR.W, new Coordinate('e', wRank));

        //________________________________________________Place Black in HashMap________________________________________________


        chessPieces.put(pawnB.getCoords(), pawnB);

        chessPieces.put(rookB.getCoords(), rookB);

        chessPieces.put(knightB.getCoords(), knightB);

        chessPieces.put(queenB.getCoords(), queenB);

        //________________________________________________Place White in HashMap________________________________________________

        chessPieces.put(pawnWe.getCoords(), pawnWe);
        chessPieces.put(pawnWh.getCoords(), pawnWh);

        chessPieces.put(rookWa.getCoords(), rookWa);
        chessPieces.put(rookWh.getCoords(), rookWh);

        chessPieces.put(bishopW.getCoords(), bishopW);

        chessPieces.put(queenW.getCoords(), queenW);

        chessPieces.put(kingW.getCoords(), kingW);

        return chessPieces;
    }

    /**
     * @return a {@link HashMap} of {@link Coordinate} - {@link Piece} pairs,
     * representing a chess board with a check.
     */
    public static HashMap<Coordinate, Piece> getCheckIngBoard() {
        HashMap <Coordinate, Piece> chessPieces = new HashMap<>();

        int pawnBRank = 7;
        int bRank = 8;
        int pawnWRank = 2;
        int wRank = 1;

        //________________________________________________Black Pawns________________________________________________

        Pawn pawnB = new Pawn(COLOUR.B, new Coordinate('g', pawnBRank));

        //________________________________________________Black Rooks________________________________________________

        Rook rookBh = new Rook(COLOUR.B, new Coordinate('h', bRank));
        Rook rookB = new Rook(COLOUR.B, new Coordinate('a', bRank));

        //________________________________________________Black Knights________________________________________________

        Knight knightB = new Knight(COLOUR.B, new Coordinate('h', 7));

        //________________________________________________Black Bishops________________________________________________


        //________________________________________________Black Queen________________________________________________

        Queen queenB = new Queen(COLOUR.B, new Coordinate('d', bRank));

        //________________________________________________Black King________________________________________________

        King kingB = new King(COLOUR.B, new Coordinate('e', bRank));

        //________________________________________________White Pawns________________________________________________

        Pawn pawnWe = new Pawn(COLOUR.W, new Coordinate('e', pawnWRank));
        Pawn pawnWh = new Pawn(COLOUR.W, new Coordinate('h', pawnWRank));

        //________________________________________________White Rooks________________________________________________

        Rook rookWa = new Rook(COLOUR.W, new Coordinate('a', wRank));
        Rook rookWh = new Rook(COLOUR.W, new Coordinate('h', wRank));

        //________________________________________________White Knights________________________________________________

        //________________________________________________White Bishops________________________________________________

        Bishop bishopW = new Bishop(COLOUR.W, new Coordinate('f', 6));

        //________________________________________________White Queen________________________________________________

        Queen queenW = new Queen(COLOUR.W, new Coordinate('g', 5));

        //________________________________________________White King________________________________________________

        King kingW = new King(COLOUR.W, new Coordinate('e', wRank));

        //________________________________________________Place Black in HashMap________________________________________________


        chessPieces.put(pawnB.getCoords(), pawnB);

        chessPieces.put(rookB.getCoords(), rookB);
        chessPieces.put(rookBh.getCoords(),rookBh);

        chessPieces.put(knightB.getCoords(), knightB);

        chessPieces.put(queenB.getCoords(), queenB);

        chessPieces.put(kingB.getCoords(),kingB);

        //________________________________________________Place White in HashMap________________________________________________

        chessPieces.put(pawnWe.getCoords(), pawnWe);
        chessPieces.put(pawnWh.getCoords(), pawnWh);

        chessPieces.put(rookWa.getCoords(), rookWa);
        chessPieces.put(rookWh.getCoords(), rookWh);

        chessPieces.put(bishopW.getCoords(), bishopW);

        chessPieces.put(queenW.getCoords(), queenW);

        chessPieces.put(kingW.getCoords(), kingW);

        return chessPieces;
    }

    /**
     * @return a {@link HashMap} of {@link Coordinate} - {@link Piece} pairs,
     * representing a chess board in which a pawn can be promoted.
     */
    public static HashMap<Coordinate, Piece> getPromotingBoard() {
        HashMap <Coordinate, Piece> chessPieces = new HashMap<>();

        int pawnBRank = 7;
        int bRank = 8;
        int pawnWRank = 2;
        int wRank = 1;

        //________________________________________________Black Pawns________________________________________________

        Pawn pawnW = new Pawn(COLOUR.W, new Coordinate('g', pawnBRank));
        Pawn pawnB = new Pawn(COLOUR.B, new Coordinate('b',pawnWRank));

        //________________________________________________Black Rooks________________________________________________

        Rook rookBh = new Rook(COLOUR.B, new Coordinate('h', bRank));
        Rook rookB = new Rook(COLOUR.B, new Coordinate('a', bRank));

        //________________________________________________Black Knights________________________________________________

        Knight knightB = new Knight(COLOUR.B, new Coordinate('h', 7));

        //________________________________________________Black Bishops________________________________________________


        //________________________________________________Black Queen________________________________________________

        Queen queenB = new Queen(COLOUR.B, new Coordinate('d', bRank));

        //________________________________________________Black King________________________________________________

        King kingB = new King(COLOUR.B, new Coordinate('e', bRank));

        //________________________________________________White Pawns________________________________________________

        Pawn pawnWe = new Pawn(COLOUR.W, new Coordinate('e', pawnWRank));
        Pawn pawnWh = new Pawn(COLOUR.W, new Coordinate('h', pawnWRank));

        //________________________________________________White Rooks________________________________________________

        Rook rookWa = new Rook(COLOUR.W, new Coordinate('a', wRank));
        Rook rookWh = new Rook(COLOUR.W, new Coordinate('h', wRank));

        //________________________________________________White Knights________________________________________________

        //________________________________________________White Bishops________________________________________________

        Bishop bishopW = new Bishop(COLOUR.W, new Coordinate('f', 6));

        //________________________________________________White Queen________________________________________________

        Queen queenW = new Queen(COLOUR.W, new Coordinate('g', 5));

        //________________________________________________White King________________________________________________

        King kingW = new King(COLOUR.W, new Coordinate('e', wRank));

        //________________________________________________Place Black in HashMap________________________________________________


        chessPieces.put(pawnW.getCoords(), pawnW);
        chessPieces.put(pawnB.getCoords(), pawnB);

        chessPieces.put(rookB.getCoords(), rookB);
        chessPieces.put(rookBh.getCoords(),rookBh);

        chessPieces.put(knightB.getCoords(), knightB);

        chessPieces.put(queenB.getCoords(), queenB);

        chessPieces.put(kingB.getCoords(),kingB);

        //________________________________________________Place White in HashMap________________________________________________

        chessPieces.put(pawnWe.getCoords(), pawnWe);
        chessPieces.put(pawnWh.getCoords(), pawnWh);

        chessPieces.put(rookWa.getCoords(), rookWa);
        chessPieces.put(rookWh.getCoords(), rookWh);

        chessPieces.put(bishopW.getCoords(), bishopW);

        chessPieces.put(queenW.getCoords(), queenW);

        chessPieces.put(kingW.getCoords(), kingW);

        return chessPieces;
    }


    //________________________________________________Methods for displaying a chess board in CLI________________________________________________


    /**
     * Creates a {@link String} representing a horizontal line of the CLI chess board..
     * @return a {@link String} representing a horizontal line of the chess board.
     */
    private static String boardLine() {

        String unit = "|====";

        return "  " +
                unit.repeat(8) +
                "|";
    }

    /**
     * Generates the label for a rank of the CLI chess board.
     * @param n the number of the rank.
     * @param type whether the rank label is on the left ({@code L}) or right of the board.
     * @return the {@link String} label of the rank, appropriately spaced to be used in the CLI board.
     */
    private static String labelRank(int n, String type) {
        if (type.equals("L"))
            return n + " ";
        else
            return " " + n;
    }

    /**
     * Generates the labels for the files of the CLI chess board.
     * @return the {@link String} label of the files, appropriately spaced to be used in the CLI board.
     */
    private static String labelFile() {
        StringBuilder str = new StringBuilder();

        // we use spacing to account for the extra width of the rank labels
        str.append("   ");

        // include each character used in the file labels
        for (char file = BOARD.FIRST_FILE.getFileVal(); file <= BOARD.LAST_FILE.getFileVal(); file++) {
            str.append(" ").append(file).append("   ");
        }

        return str.toString();
    }

    /**
     * Given a game board, displays its CLI representation.
     * @param pieces @param pieces the {@link Pieces} used for playing.
     * @return a {@link String} representation of the chess board represented by {@code pieces}.
     */
    public static String displayBoard(Pieces pieces) {

        StringBuilder str = new StringBuilder();

        // add the top file labels
        str.append(labelFile()).append("\n");

        // add a board line
        str.append(boardLine()).append("\n");

        // iterate over each square, adding the corresponding piece to the string representation
        for (int rank = BOARD.LAST_RANK.getRankVal(); rank >= BOARD.FIRST_RANK.getRankVal(); rank--) {
            str.append(labelRank(rank,"L")).append("|");
            for (char file = BOARD.FIRST_FILE.getFileVal(); file <= BOARD.LAST_FILE.getFileVal(); file++) {
                Coordinate coord = new Coordinate(file,rank);
                Piece piece = pieces.getPieces().get(coord);
                str.append((piece != null)
                        ? (" " + piece.toBoardString() + " |")
                        : "    |");
            }

            // add the rank label after a row is completed
            str.append(labelRank(rank,"R")).append("\n");

            // add a board line after a row is completed
            str.append(boardLine()).append("\n");
        }

        // add the bottom file labels
        str.append(labelFile()).append("\n");

        return str.toString();
    }


}

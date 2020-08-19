package backup;

public class UnusedMethods {

    /*public static boolean coordInBoard (Coordinate coord) {
        char coordFile = coord.getFile();
        int coordRank = coord.getRank();
        return (coordFile >= BOARD.FIRST_FILE.getFileVal()
                && coordFile <= BOARD.LAST_FILE.getFileVal()
                && coordRank >= BOARD.FIRST_RANK.getRankVal()
                && coordRank <= BOARD.LAST_RANK.getRankVal());
    }*/

    /*public boolean pieceInBoard(Piece piece) {
        return pieces.get(piece.getCoords()) != null;
    }*/

    /*String illegalFile = "By definition, a file in chess is a character from " +
                              BOARD.FIRST_FILE.getFileVal() + " to " + BOARD.LAST_FILE.getFileVal();

        String illegalRank = "By definition, a rank in chess is an integer from " +
                BOARD.FIRST_RANK.getRankVal() + " to " + BOARD.LAST_RANK.getRankVal();

        if (upperFile < BOARD.FIRST_FILE.getFileVal() || upperFile > BOARD.LAST_FILE.getFileVal()) {
            throw new IllegalArgumentException(illegalFile);
        }

        if (rank < BOARD.FIRST_RANK.getRankVal() || rank > BOARD.LAST_RANK.getRankVal())
            throw new IllegalArgumentException(illegalRank);*/

}

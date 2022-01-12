package backup;

public class UnusedMethods {

    /*public static boolean coordInBoard (chess23.Coordinate coord) {
        char coordFile = coord.getFile();
        int coordRank = coord.getRank();
        return (coordFile >= chess23.BOARD.FIRST_FILE.getFileVal()
                && coordFile <= chess23.BOARD.LAST_FILE.getFileVal()
                && coordRank >= chess23.BOARD.FIRST_RANK.getRankVal()
                && coordRank <= chess23.BOARD.LAST_RANK.getRankVal());
    }*/

    /*public boolean pieceInBoard(chess23.Piece piece) {
        return pieces.get(piece.getCoords()) != null;
    }*/

    /*String illegalFile = "By definition, a file in chess is a character from " +
                              chess23.BOARD.FIRST_FILE.getFileVal() + " to " + chess23.BOARD.LAST_FILE.getFileVal();

        String illegalRank = "By definition, a rank in chess is an integer from " +
                chess23.BOARD.FIRST_RANK.getRankVal() + " to " + chess23.BOARD.LAST_RANK.getRankVal();

        if (upperFile < chess23.BOARD.FIRST_FILE.getFileVal() || upperFile > chess23.BOARD.LAST_FILE.getFileVal()) {
            throw new IllegalArgumentException(illegalFile);
        }

        if (rank < chess23.BOARD.FIRST_RANK.getRankVal() || rank > chess23.BOARD.LAST_RANK.getRankVal())
            throw new IllegalArgumentException(illegalRank);*/

}

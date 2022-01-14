package chess23;

/**
 * The BOARD enum contains information about the "limiting" constants of a chess board.
 * <br>
 * These are the first file ({@code a}), the last file ({@code h}),
 * the first rank ({@code 1}) and the last rank ({@code 8}).
 */
public enum BOARD {

    /**
     * The first file in the chess board.
     */
    FIRST_FILE('a'),

    /**
     * The last file in the chess board.
     */
    LAST_FILE('h'),

    /**
     * The first rank in the chess board.
     */
    FIRST_RANK(1),

    /**
     * The last rank in the chess board.
     */
    LAST_RANK(8);

    private char fileVal;
    private int rankVal;

    BOARD(char file) {
        fileVal = file;
    }

    BOARD(int rank) {
        rankVal = rank;
    }

    public char getFileVal() {
        return fileVal;
    }

    public int getRankVal() {
        return rankVal;
    }
}

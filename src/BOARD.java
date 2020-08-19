/**
 * The BOARD enum class contains information abou tthe "limiting" constants of a chess board
 * These are the first file ('a'), the last file ('h), the first rank (1) and the last rank (8)
 * Also contains methods to get these values
 */

public enum BOARD {

    FIRST_FILE('a'),
    LAST_FILE('h'),
    FIRST_RANK(1),
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

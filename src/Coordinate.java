import java.util.Objects;

/*
1) Class Constructors
2) Getters
3) Validating Method
4) Overridden Methods
*/

/**
 * The Coordinate class is used to determine the position of the pieces within the board.
 * A chess coordinate is determined by its file (column), represented as a lowercase character from a to h;
 * and its rank (row), represented as a number from 1 to 8.
 */

public class Coordinate {

    public char file; //column or letter
    public int rank; //row or number
    public static Coordinate emptyCoordinate = new Coordinate((char) 0,0); // a filler coordinate that isn't in the board

    //________________________________________________Class Constructors________________________________________________

    public Coordinate (char file, int rank) {

        this.file = Character.toLowerCase(file);
        this.rank = rank;
    }

    public Coordinate (Coordinate original) {
        file = original.file;
        rank = original.rank;
    }

    public Coordinate (String coordinate) {

        if (coordinate.length() == 2 && Character.isLetter(coordinate.charAt(0)) && Character.isDigit(coordinate.charAt(1))) {
            file = Character.toLowerCase(coordinate.charAt(0));
            rank = Character.getNumericValue(coordinate.charAt(1));
        }
        else {
            System.out.println("Invalid coordinate format. Empty coordinate provided.");
            file = 0;
            rank = 0;
        }
    }

    public Coordinate() {
        file = 0;
        rank = 0;
    }

    //________________________________________________Getters________________________________________________

    public char getFile() {
        return file;
    }

    public int getRank() {
        return rank;
    }

    //________________________________________________Validating Method________________________________________________

    /**
     * inBoard checks that an instance of a Coordinate is within the chess board
     * @param coord is the coordinate that is checked for validity within the board
     * @return true if the coordinate is in the board
     */

    public static boolean inBoard (Coordinate coord) {
        char coordFile = coord.getFile();
        int coordRank = coord.getRank();
        return (coordFile >= BOARD.FIRST_FILE.getFileVal()
                && coordFile <= BOARD.LAST_FILE.getFileVal()
                && coordRank >= BOARD.FIRST_RANK.getRankVal()
                && coordRank <= BOARD.LAST_RANK.getRankVal());
    }

    //________________________________________________Overridden Methods________________________________________________

    @Override
    public String toString() {
        return file + "" + rank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return file == that.file &&
                rank == that.rank;
    }

    @Override
    public int hashCode() {
        return Objects.hash(file, rank);
    }


}

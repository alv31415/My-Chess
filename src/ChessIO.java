import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Scanner;

/*
1) Save String Methods
2) Game Saving Method
*/

public class ChessIO {

    private static final String errorSave = "$$$";

    //________________________________________________Save String Methods________________________________________________

    /**
     * Produces a String corresponding to a move
     * @param pieces the board being played in
     * @param coordinate the destination coordinate of the moves
     * @param piece the piece making the move
     * @return a String representing a move, according to the PGN naming conventions
     */

    public static String moveString (Pieces pieces, Coordinate coordinate, Piece piece) {

        boolean isCastle = false;

        StringBuilder str = new StringBuilder();
        Pieces previousBoard = new Pieces(pieces.getPreviousPieces());
        Coordinate previousCoordinate = previousBoard.findPiece(piece);
        Piece previousPiece = previousBoard.getPiece(previousCoordinate);

        if (piece.getName() != ID.KING) {
            str.append(piece.getName().toString());
        }
        else {
            King king = (King) piece;
            King previousKing = (King) previousPiece;

            if (coordinate.equals(king.getCastleCoordKingQ()) && previousKing.canCastleQueen(previousBoard)) {
                str.append("O-O-O");
                isCastle = true;
            }
            else if (coordinate.equals(king.getCastleCoordKingK()) && previousKing.canCastleKing(previousBoard)) {
                str.append("O-O");
                isCastle = true;
            }
            else
                str.append(piece.getName().toString());
        }

        str.append(removeAmbiguous(previousBoard, coordinate, previousPiece));

        if (pieces.getIsCapture()) {
            if (piece.getName() == ID.PAWN) {
                assert piece instanceof Pawn;
                Pawn pawn = (Pawn) piece;
                str.append(pawn.getPreviousCoordinate().getFile()).append("x");
            }
            else
                str.append("x");
        }

        if (!isCastle) {
            str.append(coordinate.toString());
        }

        if (piece.getName() == ID.PAWN) {
            Pawn pawn = (Pawn) piece;
            if (pawn.canPromoteBlack(coordinate) || pawn.canPromoteWhite(coordinate))
                str.append("=").append(pawn.getPromotedPiece().getName().toString());
        }

        if (pieces.isMate(COLOUR.not(piece.getColour())))
            str.append("#");
        else if (pieces.isCheck(COLOUR.not(piece.getColour())))
            str.append("+");

        return str.toString();
    }

    /**
     * Produces a String to remove the ambiguity in printing a move,
     * should 2 pieces of the same colour and same type be capable of moving to the same coordinate
     *  @param pieces the board being played in
     *  @param coordinate the destination coordinate of the moves
     *  @param piece the piece making the move
     * @return the file of the piece, if there is another piece of the same colour and type in the same rank
     * the rank of the piece, if there is another piece of the same colour and type in the same file
     * otherwise, returns the empty String
     */

    public static String removeAmbiguous (Pieces pieces, Coordinate coordinate, Piece piece) {
        if (pieces.pieceToSameCoordinate(coordinate, piece)) {
            if (pieces.pieceInSameRank(piece))
                return piece.getFile() + "";
            else if (pieces.pieceInSameFile(piece))
                return piece.getRank() + "";
            else
                return "";
        }
        else
            return "";
    }

    /**
     * Uses a scanner to request a file to save a game
     * @param test_in the scanner being used to tae the input
     * @return a Path, containing the name of the save file
     */

    public static Path fileQuery(Scanner test_in) {

        System.out.print("Enter file path: ");
        String filePath = test_in.nextLine();
        return Paths.get(filePath);
    }

    /**
     * Checks that a String constitutes a valid file path, and returns a valid path if so.
     * @param filePath the path being checked
     * @return either a String with a valid ".txt" extension,
     * or errorSave ("$$$") should filePath be an invalid String for a file path
     */

    public static String toTxt (String filePath) {

        filePath = filePath.replaceAll("\\s","");

        if (filePath.length() == 0)
            return errorSave;

        int periodCheck = filePath.lastIndexOf(".");

        if (periodCheck == -1)
            return filePath + ".txt";
        else if (filePath.substring(periodCheck).length() == 4)
            return filePath;
        else
            return errorSave;
    }

    /**
     * @param potentialFile the path being checked
     * @return true if potentialFile is an incorrect file path
     */

    public static boolean isErrorSave (String potentialFile) {
        return errorSave.equals(potentialFile);
    }

    //________________________________________________Game Saving Methods________________________________________________

    /**
     * Saves a game to the provided file path
     * @param game a String containing the information of the game
     * @param saveFile the path for saving the game
     * @return
     */

    public static boolean saveGame(String game, Path saveFile) {

        Objects.requireNonNull(game,"You can't have a null game!");
        Objects.requireNonNull(saveFile,"You can't save a game to a null path!");

        String path = String.valueOf(saveFile);
        File gameFile = new File(path); // we create an instance of a file with the given path
        if (!gameFile.exists()) { // ensures that we don't overwrite an existing file
            try {
                FileWriter writer = new FileWriter(path);
                writer.write(game);
                writer.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}


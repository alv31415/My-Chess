package chess23;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Scanner;

/**
 * Utility class dedicated to handling the saving of a game.
 */
public class ChessIO {

    /**
     * A {@link String} used to represent a failed attempt at saving a game.
     */
    private static final String errorSave = "$$$";

    //________________________________________________Save String Methods________________________________________________

    /**
     * Produces a {@link String} corresponding to a move in PGN format.
     * @param pieces the {@link Pieces} used for playing.
     * @param coordinate the destination {@link Coordinate} of the move.
     * @param piece the {@link Piece} making the move.
     * @return a {@link String} representing a move, according to the PGN format.
     */
    public static String moveString (Pieces pieces, Coordinate coordinate, Piece piece) {

        boolean isCastle = false;

        // use a string builder to build the string representation
        StringBuilder str = new StringBuilder();

        // keep track of the previous state of the board
        Pieces previousBoard = new Pieces(pieces.getPreviousPieces());

        // find where the moveed piece was before making the move
        Coordinate previousCoordinate = previousBoard.findPiece(piece);
        Piece previousPiece = previousBoard.getPiece(previousCoordinate);

        // if the piece is not a king, we can just use its letter as the start of the string
        if (piece.getName() != ID.KING) {
            str.append(piece.getName().toString());
        }
        else {
            // otherwise, a move can be castling, so we can't just use a K
            King king = (King) piece;
            King previousKing = (King) previousPiece;

            // check if the king castled queenside or kingside
            if (coordinate.equals(king.getCastleCoordKingQ()) && previousKing.canCastleQueen(previousBoard)) {
                str.append("O-O-O");
                isCastle = true;
            }
            else if (coordinate.equals(king.getCastleCoordKingK()) && previousKing.canCastleKing(previousBoard)) {
                str.append("O-O");
                isCastle = true;
            }
            // otherwise just use K
            else
                str.append(piece.getName().toString());
        }

        // remove ambiguity from moves
        // for example, if 2 rooks are in rank 2, and the destination is g2,
        // we need to determine which of the 2 rooks moved
        str.append(removeAmbiguous(previousBoard, coordinate, previousPiece));

        // if there is a capture, we can indicate it with a x
        if (pieces.getIsCapture()) {
            // if it's a pawn which captures, we need to use the file on which the pawn was before capturing
            if (piece.getName() == ID.PAWN) {
                assert piece instanceof Pawn;
                Pawn pawn = (Pawn) piece;
                str.append(pawn.getPreviousCoordinate().getFile()).append("x");
            }
            // otherwise just include the x
            else
                str.append("x");
        }

        // if there is no castling, we can add the destination coordinate
        if (!isCastle) {
            str.append(coordinate.toString());
        }

        // include promotion symbol
        if (piece.getName() == ID.PAWN) {
            Pawn pawn = (Pawn) piece;
            if (pawn.canPromote(coordinate))
                str.append("=").append(pawn.getPromotedPiece().getName().toString());
        }

        // include symbols if there is a check or a checkmate
        if (pieces.isMate(COLOUR.not(piece.getColour())))
            str.append("#");
        else if (pieces.isCheck(COLOUR.not(piece.getColour())))
            str.append("+");

        return str.toString();
    }

    /**
     * Produces a {@link String} to remove the ambiguity when converting a move to PGN,
     * should 2 pieces of the same colour and same type be capable of moving to the same coordinate
     * @param pieces the {@link Pieces} used for playing before the (potentiallY) ambiguous move was played.
     * @param coordinate the destination {@link Coordinate} of the move.
     * @param piece the {@link Piece} making the move.
     * @return
     * <ul>
     *     <li>the file of the {@link Piece}, if there is another piece of the same colour and type in the same rank,</li>
     *     <li>the rank of the piece, if there is another piece of the same colour and type in the same file,</li>
     *     <li>or an empty {@link String} if there is no ambiguity.</li>
     * </ul>
     */
    public static String removeAmbiguous (Pieces pieces, Coordinate coordinate, Piece piece) {
        // check if there is any possibility of ambiguity (piece of same type in same rank/file)
        if (pieces.pieceToSameCoordinate(coordinate, piece)) {
            // if the ambiguous piece is in the same rank, remove ambiguity by returning the file
            if (pieces.pieceInSameRank(piece)) {
                return piece.getFile() + "";
            }
            // if the ambiguous piece is in the same file (or other ambiguity),
            // we remove the ambiguity by returining the rank
            else {
                return piece.getRank() + "";
            }
        }
        // if no ambiguity, just return the empty string
        else
            return "";
    }

    /**
     * Given a filepath to save a game, ensures that it is correctly formatted.
     * Otherwise, it invalidates the filepath.
     * @param filePath the filepath being checked.
     * @return either a {@link String} with a valid {@code .txt} or {@code .pgn} extension,
     * or if an invalid extension is given, returns {@link #errorSave}.
     */
    public static String toSaveableFormat(String filePath) {

        // replace all white spaces with underscores
        filePath = filePath.replaceAll("\\s","_");

        // if no file path is given, its an error
        if (filePath.length() == 0)
            return errorSave;

        // get the last occurrence of a period
        int periodCheck = filePath.lastIndexOf(".");

        // if no period appears, append the extension
        if (periodCheck == -1) {
            return filePath + ".txt";
        }
        // otherwise, check if the filepath contains a correct extension
        // if it doesnt, produce an error message
        else {
            String extension = filePath.substring(periodCheck);
            if (extension.equals(".txt") || extension.equals(".pgn")) {
                return filePath;
            }
            else {
                System.err.println("File extension " + extension + " is invalid. Please use .txt or .pgn instead.");
                return errorSave;
            }
        }
    }

    /**
     * Uses a {@link Scanner} to request a filename for saving a game.
     * @param sc the {@link Scanner} being used to take the input.
     * @return a {@link Path}, containing the filepath for saving the game.
     */
    public static Path fileQuery(Scanner sc) {

        System.out.print("Enter file path: ");
        String filePath = sc.nextLine();

        // ensure that the file to which we save the game has an appropriate extension
        return Paths.get(toSaveableFormat(filePath));
    }

    /**
     * Checks whether a filepath for saving is valid, based on whether it is equal to {@link #errorSave}.
     * @param potentialFile the path being checked.
     * @return {@code true} if {@code potentialFile} is an incorrect file path
     */
    public static boolean isErrorSave (String potentialFile) {
        return errorSave.equals(potentialFile);
    }

    //________________________________________________Game Saving Methods________________________________________________

    /**
     * Saves a game to the provided file path
     * @param game a {@link String} containing the information of the game in PGN format.
     * @param saveFile the path for saving the game.
     * @return {@code true} if saving was succesful.
     */
    public static boolean saveGame(String game, Path saveFile) {

        Objects.requireNonNull(game,"You can't have a null game!");
        Objects.requireNonNull(saveFile,"You can't save a game to a null path!");

        String path = String.valueOf(saveFile);

        // we create an instance of a file with the given path
        File gameFile = new File(path);

        // check if the file already exists
        // if it doesn't, it writes the game to it
        // otherwise, the game isn't saved
        if (!gameFile.exists()) {
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


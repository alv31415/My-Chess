/*
package backup;

import java.util.Objects;

public class Moves {

    public static String nullPieces = "You can't play with a null board.";
    public static String nullPiece = "You can only play with a non-null piece.";
    public static String nullCoord = "Board coordinates must not be null.";

    public static int[] getCoordDifference (Coordinate origin, Coordinate destination) {

        Objects.requireNonNull(origin,nullCoord);
        Objects.requireNonNull(destination,nullCoord);

        int[] coordDifference = new int[2];
        coordDifference[0] = destination.getFile() - origin.getFile();
        coordDifference[1] = destination.getRank() - origin.getRank();

        return coordDifference;
    }

    public static boolean validHorizontal(Pieces pieces, Piece piece, Coordinate destination){

        Objects.requireNonNull(pieces,nullPieces);
        Objects.requireNonNull(piece,nullPiece);
        Objects.requireNonNull(destination,nullCoord);

        if (piece.getName().equals(ID.PAWN) || piece.getName().equals(ID.KNIGHT) || piece.getName().equals(ID.BISHOP))
            return false;

        int[] coordDifference = getCoordDifference(piece.getCoords(), destination);
        int fileDifference = coordDifference[0];
        int rankDifference = coordDifference[1];

        //HORIZONTAL RIGHT
        if (rankDifference == 0 && fileDifference > 0) {
            for (int i = 1; i < fileDifference; i++) {
                if (tileFull(pieces, new Coordinate((char) (piece.getCoords().getFile() + i),piece.getCoords().getRank())))
                        return false;
            }
            return true;
        }
        //HORIZONTAL LEFT
        else if (rankDifference == 0 && fileDifference < 0) {
            for (int i = 1; i < Math.abs(fileDifference); i++ ) {
                if (tileFull(pieces, new Coordinate((char) (piece.getCoords().getFile() - i),piece.getCoords().getRank())))
                    return false;
            }
            return true;
        }
        return false;
    }

    public static boolean validVertical(Pieces pieces, Piece piece, Coordinate destination) {

        Objects.requireNonNull(pieces,nullPieces);
        Objects.requireNonNull(piece,nullPiece);
        Objects.requireNonNull(destination,nullCoord);

        if (piece.getName().equals(ID.KNIGHT) || piece.getName().equals(ID.BISHOP) || piece.getName().equals(ID.PAWN))
            return false;

        int[] coordDifference = getCoordDifference(piece.getCoords(), destination);
        int fileDifference = coordDifference[0];
        int rankDifference = coordDifference[1];

        //VERTICAL TOP
        if (rankDifference > 0 && fileDifference == 0) {
            for (int i = 1; i < rankDifference; i++) {
                if (tileFull(pieces, new Coordinate(piece.getCoords().getFile(),piece.getCoords().getRank() + i)))
                    return false;
            }
            return true;
        }
        //VERTICAL BOTTOM
        else if (rankDifference < 0 && fileDifference == 0) {
            for (int i = 1; i < Math.abs(rankDifference); i++ ) {
                if (tileFull(pieces, new Coordinate(piece.getCoords().getFile(),piece.getCoords().getRank() - i)))
                    return false;
            }
            return true;
        }
        return false;
    }

    public static boolean validDiagonal(Pieces pieces, Piece piece, Coordinate destination) {

        Objects.requireNonNull(pieces,nullPieces);
        Objects.requireNonNull(piece,nullPiece);
        Objects.requireNonNull(destination,nullCoord);

        if (piece.getName().equals(ID.KNIGHT) || piece.getName().equals(ID.ROOK) || piece.getName().equals(ID.PAWN))
            return false;

        int[] coordDifference = getCoordDifference(piece.getCoords(), destination);
        int fileDifference = coordDifference[0];
        int rankDifference = coordDifference[1];

        //DIAGONAL TOP RIGHT
        if (fileDifference > 0 && fileDifference == rankDifference) {
            for (int i = 1; i < fileDifference; i++) {
                if (tileFull(pieces, new Coordinate((char) (piece.getCoords().getFile() + i),piece.getCoords().getRank() + i)))
                    return false;
            }
            return true;
        }
        //DIAGONAL BOTTOM LEFT
        else if (fileDifference < 0 && fileDifference == rankDifference) {
            for (int i = 1; i < Math.abs(fileDifference); i++) {
                if (tileFull(pieces, new Coordinate((char) (piece.getCoords().getFile() - i),piece.getCoords().getRank() - i)))
                    return false;
            }
            return true;
        }
        //DIAGONAL BOTTOM RIGHT
        else if (fileDifference > 0 && fileDifference == -rankDifference) {
            for (int i = 1; i < fileDifference; i++) {
                if (tileFull(pieces, new Coordinate((char) (piece.getCoords().getFile() + i),piece.getCoords().getRank() - i)))
                    return false;
            }
            return true;
        }
        //DIAGONAL TOP LEFT
        else if (fileDifference < 0 && fileDifference == -rankDifference) {
            for (int i = 1; i < Math.abs(fileDifference); i++) {
                if (tileFull(pieces, new Coordinate((char) (piece.getCoords().getFile() - i),piece.getCoords().getRank() + i)))
                    return false;
            }
            return true;
        }
        return false;
    }

    public static boolean validKnight(Piece piece, Coordinate destination) {

        Objects.requireNonNull(piece,nullPiece);
        Objects.requireNonNull(destination,nullCoord);

        if (!piece.getName().equals(ID.KNIGHT))
            return false;

        int[] coordDifference = getCoordDifference(piece.getCoords(), destination);
        int fileDifference = Math.abs(coordDifference[0]);
        int rankDifference = Math.abs(coordDifference[1]);

        if (fileDifference == 2 && rankDifference == 1)
            return true;
        else return (fileDifference == 1 && rankDifference == 2);
    }

    public static boolean validPawn(Pieces pieces, Piece piece, Coordinate destination) {

        Objects.requireNonNull(pieces,nullPieces);
        Objects.requireNonNull(piece,nullPiece);
        Objects.requireNonNull(destination,nullCoord);

        if (!piece.getName().equals(ID.PAWN))
            return false;

        int[] coordDifference = getCoordDifference(piece.getCoords(), destination);
        int fileDifference = coordDifference[0];
        int rankDifference = coordDifference[1];

        // PAWNS CAN ONLY MOVE FORWARD 1 or 2 PLACES; CAN ONLY MOVE 1 TILE DIAGONALLY AT MOST
        if (Math.abs(rankDifference) <= 0
                || Math.abs(rankDifference) > 2
                || Math.abs(fileDifference) < 0
                ||Math.abs(fileDifference) > 1)
            return false;

        if (piece.getColour().equals(COLOUR.B)) {

            switch (rankDifference) {
                case 1:
                    if (Math.abs(fileDifference) == 1)
                        return tileFull(pieces,destination) && destHasDifferentColour(pieces, piece, destination);
                    else if (fileDifference == 0)
                        return !tileFull(pieces,destination);
                    else
                        return false;
                case 2:
                    return piece.getOGcoord().equals(destination)
                            && !tileFull(pieces, destination)
                            && !tileFull(pieces, new Coordinate(piece.getCoords().getFile(), piece.getCoords().getRank()+1))
                            && fileDifference == 0;
                default:
                    return false;
            }
        }
        else {
            switch (Math.abs(rankDifference)) {
                case 1:
                    if (Math.abs(fileDifference) == 1)
                        return tileFull(pieces,destination) && destHasDifferentColour(pieces, piece, destination);
                    else if (fileDifference == 0)
                        return !tileFull(pieces,destination);
                    else
                        return false;
                case 2:
                    return piece.getOGcoord().equals(destination)
                            && !tileFull(pieces, destination)
                            && !tileFull(pieces, new Coordinate(piece.getCoords().getFile(), piece.getCoords().getRank()-1))
                            && fileDifference == 0;
                default:
                    return false;
            }
        }
    }


    public static boolean destHasDifferentColour(Pieces pieces, Piece piece, Coordinate destination) {

        Objects.requireNonNull(pieces,nullPieces);
        Objects.requireNonNull(piece,nullPiece);
        Objects.requireNonNull(destination,nullCoord);

        if (tileFull(pieces,destination) && pieces.pieceInGame(piece))
            return pieces.getPieces().get(destination).getColour() != piece.getColour();

        return true;
    }



    public static boolean updatePieces (Pieces pieces, Piece piece, Coordinate destination) {

        Objects.requireNonNull(pieces,nullPieces);
        Objects.requireNonNull(piece,nullPiece);
        Objects.requireNonNull(destination,nullCoord);

        if (piece.isValidMove(pieces, destination)) {
            pieces.getEaten().add(pieces.getPieces().get(destination));
            pieces.getPieces().put(destination, piece);
            pieces.getPieces().remove(piece.getCoords());
            piece.setCoords(destination);
            return true;
        }
        return false;
    }



}
*/

/*
package backup;

import java.util.ArrayList;
import java.util.HashMap;

public class Pieces2 {

    public static HashMap<chess23.Coordinate, chess23.Piece> pieces;
    public static ArrayList <chess23.Piece> eaten;

    public static HashMap<chess23.Coordinate, chess23.Piece> makePlayerMap() {

        int pawnBRank = 7;
        int bRank = 8;
        int pawnWRank = 2;
        int wRank = 1;

        pieces = new HashMap<>();
        eaten = new ArrayList<>();

        //________________________________________________Black Pawns________________________________________________

        chess23.Pawn pawnBa = new chess23.Pawn(chess23.COLOUR.B, new chess23.Coordinate('a',pawnBRank));
        chess23.Pawn pawnBb = new chess23.Pawn(chess23.COLOUR.B, new chess23.Coordinate('b', pawnBRank));
        chess23.Pawn pawnBc = new chess23.Pawn(chess23.COLOUR.B, new chess23.Coordinate('c', pawnBRank));
        chess23.Pawn pawnBd = new chess23.Pawn(chess23.COLOUR.B, new chess23.Coordinate('d', pawnBRank));
        chess23.Pawn pawnBe = new chess23.Pawn(chess23.COLOUR.B, new chess23.Coordinate('e', pawnBRank));
        chess23.Pawn pawnBf = new chess23.Pawn(chess23.COLOUR.B, new chess23.Coordinate('f', pawnBRank));
        chess23.Pawn pawnBg = new chess23.Pawn(chess23.COLOUR.B, new chess23.Coordinate('g', pawnBRank));
        chess23.Pawn pawnBh = new chess23.Pawn(chess23.COLOUR.B, new chess23.Coordinate('h', pawnBRank));

        //________________________________________________Black Rooks________________________________________________

        chess23.Rook rookBa = new chess23.Rook(chess23.COLOUR.B, new chess23.Coordinate('a',bRank));
        chess23.Rook rookBh = new chess23.Rook(chess23.COLOUR.B, new chess23.Coordinate('h',bRank));

        //________________________________________________Black Knights________________________________________________

        chess23.Knight knightBb = new chess23.Knight(chess23.COLOUR.B, new chess23.Coordinate('b',bRank));
        chess23.Knight knightBg = new chess23.Knight(chess23.COLOUR.B, new chess23.Coordinate('g',bRank));

        //________________________________________________Black Bishops________________________________________________

        chess23.Bishop bishopBc = new chess23.Bishop(chess23.COLOUR.B, new chess23.Coordinate('c',bRank));
        chess23.Bishop bishopBf = new chess23.Bishop(chess23.COLOUR.B, new chess23.Coordinate('f',bRank));

        //________________________________________________Black Queen________________________________________________

        chess23.Queen queenB = new chess23.Queen(chess23.COLOUR.B, new chess23.Coordinate('e', bRank));

        //________________________________________________Black King________________________________________________

        chess23.King kingB = new chess23.King(chess23.COLOUR.B, new chess23.Coordinate('d', bRank));

        //________________________________________________White Pawns________________________________________________

        chess23.Pawn pawnWa = new chess23.Pawn(chess23.COLOUR.W, new chess23.Coordinate('a',pawnWRank));
        chess23.Pawn pawnWb = new chess23.Pawn(chess23.COLOUR.W, new chess23.Coordinate('b', pawnWRank));
        chess23.Pawn pawnWc = new chess23.Pawn(chess23.COLOUR.W, new chess23.Coordinate('c', pawnWRank));
        chess23.Pawn pawnWd = new chess23.Pawn(chess23.COLOUR.W, new chess23.Coordinate('d', pawnWRank));
        chess23.Pawn pawnWe = new chess23.Pawn(chess23.COLOUR.W, new chess23.Coordinate('e', pawnWRank));
        chess23.Pawn pawnWf = new chess23.Pawn(chess23.COLOUR.W, new chess23.Coordinate('f', pawnWRank));
        chess23.Pawn pawnWg = new chess23.Pawn(chess23.COLOUR.W, new chess23.Coordinate('g', pawnWRank));
        chess23.Pawn pawnWh = new chess23.Pawn(chess23.COLOUR.W, new chess23.Coordinate('h', pawnWRank));

        //________________________________________________White Rooks________________________________________________

        chess23.Rook rookWa = new chess23.Rook(chess23.COLOUR.W, new chess23.Coordinate('a',wRank));
        chess23.Rook rookWh = new chess23.Rook(chess23.COLOUR.W, new chess23.Coordinate('h',wRank));

        //________________________________________________White Knights________________________________________________

        chess23.Knight knightWb = new chess23.Knight(chess23.COLOUR.W, new chess23.Coordinate('b',wRank));
        chess23.Knight knightWg = new chess23.Knight(chess23.COLOUR.W, new chess23.Coordinate('g',wRank));

        //________________________________________________White Bishops________________________________________________

        chess23.Bishop bishopWc = new chess23.Bishop(chess23.COLOUR.W, new chess23.Coordinate('c',wRank));
        chess23.Bishop bishopWf = new chess23.Bishop(chess23.COLOUR.W, new chess23.Coordinate('f',wRank));

        //________________________________________________White Queen________________________________________________

        chess23.Queen queenW = new chess23.Queen(chess23.COLOUR.W, new chess23.Coordinate('d', wRank));

        //________________________________________________White King________________________________________________

        chess23.King kingW = new chess23.King(chess23.COLOUR.W, new chess23.Coordinate('e', wRank));

        //________________________________________________Place Black in HashMap________________________________________________

        pieces.put(pawnBa.getCoords(),pawnBa);
        pieces.put(pawnBb.getCoords(),pawnBb);
        pieces.put(pawnBc.getCoords(),pawnBc);
        pieces.put(pawnBd.getCoords(),pawnBd);
        pieces.put(pawnBe.getCoords(),pawnBe);
        pieces.put(pawnBf.getCoords(),pawnBf);
        pieces.put(pawnBg.getCoords(),pawnBg);
        pieces.put(pawnBh.getCoords(),pawnBh);

        pieces.put(rookBa.getCoords(),rookBa);
        pieces.put(rookBh.getCoords(),rookBh);

        pieces.put(knightBb.getCoords(),knightBb);
        pieces.put(knightBg.getCoords(),knightBg);

        pieces.put(bishopBc.getCoords(),bishopBc);
        pieces.put(bishopBf.getCoords(),bishopBf);

        pieces.put(queenB.getCoords(),queenB);

        pieces.put(kingB.getCoords(),kingB);

        //________________________________________________Place White in HashMap________________________________________________

        pieces.put(pawnWa.getCoords(),pawnWa);
        pieces.put(pawnWb.getCoords(),pawnWb);
        pieces.put(pawnWc.getCoords(),pawnWc);
        pieces.put(pawnWd.getCoords(),pawnWd);
        pieces.put(pawnWe.getCoords(),pawnWe);
        pieces.put(pawnWf.getCoords(),pawnWf);
        pieces.put(pawnWg.getCoords(),pawnWg);
        pieces.put(pawnWh.getCoords(),pawnWh);

        pieces.put(rookWa.getCoords(),rookWa);
        pieces.put(rookWh.getCoords(),rookWh);

        pieces.put(knightWb.getCoords(),knightWb);
        pieces.put(knightWg.getCoords(),knightWg);

        pieces.put(bishopWc.getCoords(),bishopWc);
        pieces.put(bishopWf.getCoords(),bishopWf);

        pieces.put(queenW.getCoords(),queenW);

        pieces.put(kingW.getCoords(),kingW);

        return pieces;
    }

    public void updatePieces (chess23.Piece piece, chess23.Coordinate destination) {
        if (piece.isValidMove(destination)) {
            eaten.add(pieces.get(destination));
            pieces.replace(destination, piece);
        }
    }

    /*public boolean isCheck(chess23.COLOUR colour) { //check against the given colour
        chess23.Coordinate kingPosition = findKing(colour);

        if (kingPosition.equals(chess23.Coordinate.emptyCoordinate))
            throw new IllegalArgumentException("There is no king in the board. This is an illegal game!");

        HashMap<chess23.Coordinate,chess23.Piece> colouredPieces = getColourPieces(chess23.COLOUR.not(colour));

        for (chess23.Piece value : colouredPieces.values()) {
            ArrayList<chess23.Coordinate> pieceMoves = value.getPotentialMoves();
            if (pieceMoves.contains(kingPosition))
                return true;
        }
        return false;
    }*/


   // public static void main(String[] args) {

/*chess23.Coordinate pawnBaOG = new chess23.Coordinate('a',7);
        chess23.Pawn pawnBa = new chess23.Pawn(chess23.COLOUR.B, pawnBaOG);
        System.out.println(pawnBa.toFancyString());
        System.out.println(pawnBa.OGcoord.toString());
        pawnBa.setCoords(new chess23.Coordinate('c', 8));
        System.out.println(pawnBa.toFancyString());
        System.out.println(pawnBa.OGcoord.toString());*//*


        HashMap<chess23.Coordinate, chess23.Piece> pieces = makePlayerMap();



        



    }
}
*/

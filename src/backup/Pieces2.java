/*
package backup;

import java.util.ArrayList;
import java.util.HashMap;

public class Pieces2 {

    public static HashMap<Coordinate, Piece> pieces;
    public static ArrayList <Piece> eaten;

    public static HashMap<Coordinate, Piece> makePlayerMap() {

        int pawnBRank = 7;
        int bRank = 8;
        int pawnWRank = 2;
        int wRank = 1;

        pieces = new HashMap<>();
        eaten = new ArrayList<>();

        //________________________________________________Black Pawns________________________________________________

        Pawn pawnBa = new Pawn(COLOUR.B, new Coordinate('a',pawnBRank));
        Pawn pawnBb = new Pawn(COLOUR.B, new Coordinate('b', pawnBRank));
        Pawn pawnBc = new Pawn(COLOUR.B, new Coordinate('c', pawnBRank));
        Pawn pawnBd = new Pawn(COLOUR.B, new Coordinate('d', pawnBRank));
        Pawn pawnBe = new Pawn(COLOUR.B, new Coordinate('e', pawnBRank));
        Pawn pawnBf = new Pawn(COLOUR.B, new Coordinate('f', pawnBRank));
        Pawn pawnBg = new Pawn(COLOUR.B, new Coordinate('g', pawnBRank));
        Pawn pawnBh = new Pawn(COLOUR.B, new Coordinate('h', pawnBRank));

        //________________________________________________Black Rooks________________________________________________

        Rook rookBa = new Rook(COLOUR.B, new Coordinate('a',bRank));
        Rook rookBh = new Rook(COLOUR.B, new Coordinate('h',bRank));

        //________________________________________________Black Knights________________________________________________

        Knight knightBb = new Knight(COLOUR.B, new Coordinate('b',bRank));
        Knight knightBg = new Knight(COLOUR.B, new Coordinate('g',bRank));

        //________________________________________________Black Bishops________________________________________________

        Bishop bishopBc = new Bishop(COLOUR.B, new Coordinate('c',bRank));
        Bishop bishopBf = new Bishop(COLOUR.B, new Coordinate('f',bRank));

        //________________________________________________Black Queen________________________________________________

        Queen queenB = new Queen(COLOUR.B, new Coordinate('e', bRank));

        //________________________________________________Black King________________________________________________

        King kingB = new King(COLOUR.B, new Coordinate('d', bRank));

        //________________________________________________White Pawns________________________________________________

        Pawn pawnWa = new Pawn(COLOUR.W, new Coordinate('a',pawnWRank));
        Pawn pawnWb = new Pawn(COLOUR.W, new Coordinate('b', pawnWRank));
        Pawn pawnWc = new Pawn(COLOUR.W, new Coordinate('c', pawnWRank));
        Pawn pawnWd = new Pawn(COLOUR.W, new Coordinate('d', pawnWRank));
        Pawn pawnWe = new Pawn(COLOUR.W, new Coordinate('e', pawnWRank));
        Pawn pawnWf = new Pawn(COLOUR.W, new Coordinate('f', pawnWRank));
        Pawn pawnWg = new Pawn(COLOUR.W, new Coordinate('g', pawnWRank));
        Pawn pawnWh = new Pawn(COLOUR.W, new Coordinate('h', pawnWRank));

        //________________________________________________White Rooks________________________________________________

        Rook rookWa = new Rook(COLOUR.W, new Coordinate('a',wRank));
        Rook rookWh = new Rook(COLOUR.W, new Coordinate('h',wRank));

        //________________________________________________White Knights________________________________________________

        Knight knightWb = new Knight(COLOUR.W, new Coordinate('b',wRank));
        Knight knightWg = new Knight(COLOUR.W, new Coordinate('g',wRank));

        //________________________________________________White Bishops________________________________________________

        Bishop bishopWc = new Bishop(COLOUR.W, new Coordinate('c',wRank));
        Bishop bishopWf = new Bishop(COLOUR.W, new Coordinate('f',wRank));

        //________________________________________________White Queen________________________________________________

        Queen queenW = new Queen(COLOUR.W, new Coordinate('d', wRank));

        //________________________________________________White King________________________________________________

        King kingW = new King(COLOUR.W, new Coordinate('e', wRank));

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

    public void updatePieces (Piece piece, Coordinate destination) {
        if (piece.isValidMove(destination)) {
            eaten.add(pieces.get(destination));
            pieces.replace(destination, piece);
        }
    }

    /*public boolean isCheck(COLOUR colour) { //check against the given colour
        Coordinate kingPosition = findKing(colour);

        if (kingPosition.equals(Coordinate.emptyCoordinate))
            throw new IllegalArgumentException("There is no king in the board. This is an illegal game!");

        HashMap<Coordinate,Piece> colouredPieces = getColourPieces(COLOUR.not(colour));

        for (Piece value : colouredPieces.values()) {
            ArrayList<Coordinate> pieceMoves = value.getPotentialMoves();
            if (pieceMoves.contains(kingPosition))
                return true;
        }
        return false;
    }*/


   // public static void main(String[] args) {

/*Coordinate pawnBaOG = new Coordinate('a',7);
        Pawn pawnBa = new Pawn(COLOUR.B, pawnBaOG);
        System.out.println(pawnBa.toFancyString());
        System.out.println(pawnBa.OGcoord.toString());
        pawnBa.setCoords(new Coordinate('c', 8));
        System.out.println(pawnBa.toFancyString());
        System.out.println(pawnBa.OGcoord.toString());*//*


        HashMap<Coordinate, Piece> pieces = makePlayerMap();



        



    }
}
*/

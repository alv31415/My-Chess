import org.junit.*;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.*;

public class IOTest {

    @Test
    public void testRemoveAmbiguous() {
        Pieces p = new Pieces(Boards.getCheckIngBoard());

        Knight knightB = (Knight) p.getPiece(new Coordinate('h',7));
        Queen queenB = (Queen) p.getPiece(new Coordinate('d',8));
        Rook rookWa = (Rook) p.getPiece(new Coordinate('a',1));
        Rook rookWh = (Rook) p.getPiece(new Coordinate('h',1));
        King kingW = (King) p.getPiece(new Coordinate('e',1));
        King kingB = (King) p.getPiece(new Coordinate('e',8));
        Rook rookBa = (Rook) p.getPiece(new Coordinate('a',8));
        Rook rookBh = (Rook) p.getPiece(new Coordinate('h',8));

        Coordinate bCoord = new Coordinate('f',6);
        Coordinate qCoord = new Coordinate('d',3);

        // horseB and queenB attack bishopW -> "" provided
        assertEquals("", ChessIO.removeAmbiguous(p,bCoord,knightB));
        assertEquals("", ChessIO.removeAmbiguous(p,bCoord,queenB));
        // queenB moves to d3
        p.makeMove(qCoord, queenB);
        // kingW side castle
        p.makeMove(new Coordinate('g',1), kingW);
        // rookWa moves to a4 & rookWh moves to f4
        p.makeMove(new Coordinate('a',3), rookWa);
        p.makeMove(new Coordinate('f',3), rookWh);
        // removeambiguous rookWa gives a & removeambiguous rookWh gives f when targetting qCoord, and "" otherwise
        assertEquals("a", ChessIO.removeAmbiguous(p,qCoord,rookWa));
        assertEquals("f", ChessIO.removeAmbiguous(p,qCoord,rookWh));
        assertEquals("", ChessIO.removeAmbiguous(p,new Coordinate('c',3),rookWa));
        assertEquals("", ChessIO.removeAmbiguous(p,new Coordinate('e',3),rookWh));
        // 2 knights attack from same file and different rank
        Knight knightBh = new Knight(COLOUR.B, new Coordinate('h',5));
        p.addPiece(knightBh.getOGcoord(), knightBh);
        p.updatePotentials();
        assertEquals("7", ChessIO.removeAmbiguous(p,bCoord,knightB));
        assertEquals("5", ChessIO.removeAmbiguous(p,bCoord,knightBh));
        assertEquals("", ChessIO.removeAmbiguous(p,new Coordinate('g',5),knightB));
        assertEquals("", ChessIO.removeAmbiguous(p,new Coordinate('g',3),knightBh));
        // 2 rooks attack same piece from different rank and file
        p.makeMove(new Coordinate('g',8), kingB);
        p.makeMove(new Coordinate('a',6), rookBa);
        assertEquals("", ChessIO.removeAmbiguous(p,bCoord,rookBa));
        assertEquals("", ChessIO.removeAmbiguous(p,bCoord,rookBh));
    }

    @Test
    public void testNormalMove() {
        Pieces p = new Pieces(Boards.getCheckIngBoard());
        Pawn pawnWe = (Pawn) p.getPiece(new Coordinate('e',2));
        Pawn pawnWh = (Pawn) p.getPiece(new Coordinate('h',2));
        Queen queenB = (Queen) p.getPiece(new Coordinate('d',8));
        Rook rookWa = (Rook) p.getPiece(new Coordinate('a',1));
        King kingB = (King) p.getPiece(new Coordinate('e',8));

        Coordinate coord1 = new Coordinate('e',3);
        p.makeMove(coord1, pawnWe);
        assertEquals("e3", ChessIO.moveString(p,coord1,pawnWe));

        Coordinate coord2 = new Coordinate('h',4);
        p.makeMove(coord2, pawnWh);
        assertEquals("h4", ChessIO.moveString(p,coord2,pawnWh));

        Coordinate coord3 = new Coordinate('b',6);
        p.makeMove(coord3, queenB);
        assertEquals("Qb6", ChessIO.moveString(p,coord3,queenB));

        Coordinate coord4 = new Coordinate('a',5);
        p.makeMove(coord4, rookWa);
        assertEquals("Ra5", ChessIO.moveString(p,coord4,rookWa));

        Coordinate coord5 = new Coordinate('f',8);
        p.makeMove(coord5, kingB);
        assertEquals("Kf8", ChessIO.moveString(p,coord5,kingB));
    }

    @Test
    public void testShortCastle() {

        Pieces p = new Pieces(Boards.getCheckIngBoard());
        King kingW = (King) p.getPiece(new Coordinate('e',1));
        King kingB = (King) p.getPiece(new Coordinate('e',8));

        Coordinate whiteShort = new Coordinate('g',1);
        Coordinate blackShort = new Coordinate('g',8);

        p.makeMove(whiteShort, kingW);
        assertEquals("O-O", ChessIO.moveString(p,whiteShort,kingW));

        p.makeMove(blackShort, kingB);
        assertEquals("O-O", ChessIO.moveString(p,blackShort,kingB));

    }

    @Test
    public void testLongCastle() {

        Pieces p = new Pieces(Boards.getCheckIngBoard());
        King kingW = (King) p.getPiece(new Coordinate('e',1));
        King kingB = (King) p.getPiece(new Coordinate('e',8));
        Queen queenB = (Queen) p.getPiece(new Coordinate('d',8));

        Coordinate whiteLong = new Coordinate('c',1);
        Coordinate blackLong = new Coordinate('c',8);

        p.makeMove(new Coordinate('e',7),queenB);
        p.makeMove(blackLong, kingB);
        assertEquals("O-O-O", ChessIO.moveString(p,blackLong,kingB));

        p.makeMove(whiteLong, kingW);
        assertEquals("O-O-O", ChessIO.moveString(p,whiteLong,kingW));
    }

    @Test
    public void testAmbiguousMoveCapture() {

        Pieces p = new Pieces(Boards.getCheckIngBoard());

        Knight knightB = (Knight) p.getPiece(new Coordinate('h',7));
        Queen queenB = (Queen) p.getPiece(new Coordinate('d',8));
        Rook rookWa = (Rook) p.getPiece(new Coordinate('a',1));
        Rook rookWh = (Rook) p.getPiece(new Coordinate('h',1));
        King kingW = (King) p.getPiece(new Coordinate('e',1));
        King kingB = (King) p.getPiece(new Coordinate('e',8));

        Coordinate bCoord = new Coordinate('f',6);
        Coordinate qCoord = new Coordinate('d',3);

        // queenB moves to d3
        p.makeMove(qCoord, queenB);
        // kingW side castle
        p.makeMove(new Coordinate('g',1), kingW);
        // rookWa moves to a4 & rookWh moves to f4
        p.makeMove(new Coordinate('a',3), rookWa);
        p.makeMove(new Coordinate('f',3), rookWh);
        p.makeMove(qCoord, rookWa);
        assertEquals("Raxd3", ChessIO.moveString(p,qCoord,rookWa));
        p.makeMove(new Coordinate('e',3),rookWh);
        assertEquals("Rfe3+", ChessIO.moveString(p,new Coordinate('e',3),rookWh));

        Knight knightBh = new Knight(COLOUR.B, new Coordinate('h',5));
        p.addPiece(knightBh.getOGcoord(), knightBh);
        p.updatePotentials();
        p.makeMove(new Coordinate(new Coordinate('f',8)),kingB);
        p.makeMove(bCoord, knightB);
        assertEquals("N7xf6", ChessIO.moveString(p,bCoord,knightB));
    }

    @Test
    public void testCheckAndMate() {

        Pieces p = new Pieces(Boards.getCheckIngBoard());

        Queen queenB = (Queen) p.getPiece(new Coordinate('d',8));
        Queen queenW = (Queen) p.getPiece(new Coordinate('g',5));
        Rook rookWa = (Rook) p.getPiece(new Coordinate('a',1));
        King kingB = (King) p.getPiece(new Coordinate('e',8));
        Rook rookBa = (Rook) p.getPiece(new Coordinate('a',8));
        Knight knightBh = (Knight) p.getPiece(new Coordinate('h',7));


        Coordinate queenCheck = new Coordinate('a',5);
        p.makeMove(queenCheck, queenB);
        assertEquals("Qa5+", ChessIO.moveString(p,queenCheck,queenB));

        p.makeMove(queenCheck, rookWa);
        assertEquals("Rxa5", ChessIO.moveString(p,queenCheck,rookWa));

        p.makeMove(queenCheck, rookBa);
        assertEquals("Rxa5", ChessIO.moveString(p,queenCheck,rookBa));

        p.makeMove(queenCheck, queenW);
        assertEquals("Qxa5", ChessIO.moveString(p,queenCheck,queenW));

        Coordinate queenCheck2 = new Coordinate('a',8);
        p.makeMove(queenCheck2,queenW);
        assertEquals("Qa8+", ChessIO.moveString(p,queenCheck2,queenW));

        Coordinate queenCheck3 = new Coordinate('d',8);
        p.makeMove(queenCheck3,queenW);
        assertEquals("Qd8+", ChessIO.moveString(p,queenCheck3,queenW));

        Coordinate kingSave = new Coordinate('f',7);
        p.makeMove(kingSave,kingB);
        assertEquals("Kf7", ChessIO.moveString(p,kingSave,kingB));

        Coordinate queenCheck4 = new Coordinate('e',7);
        p.makeMove(queenCheck4,queenW);
        assertEquals("Qe7+", ChessIO.moveString(p,queenCheck4,queenW));

        Coordinate kingSave2 = new Coordinate('g',8);
        p.makeMove(kingSave2,kingB);
        assertEquals("Kg8", ChessIO.moveString(p,kingSave2,kingB));

        p.makeMove(new Coordinate('g',5),knightBh);

        Coordinate queenMate = new Coordinate('g',7);
        p.makeMove(queenMate,queenW);
        assertEquals("Qxg7#", ChessIO.moveString(p,queenMate,queenW));

    }

    @Test
    public void testPromotion() {

        Pieces p = new Pieces(Boards.getPromotingBoard());

        Pawn pawnW = (Pawn) p.getPiece( new Coordinate('g', 7));
        Rook rookBh = (Rook) p.getPiece(new Coordinate('h',8));
        Pawn pawnB = (Pawn) p.getPiece(new Coordinate('b',2));
        Bishop bishopW = (Bishop) p.getPiece(new Coordinate('f',6));
        Pawn pawnWe = (Pawn) p.getPiece(new Coordinate('e', 2));

        Coordinate whitePromotion = new Coordinate('g',8);
        ByteArrayInputStream in = new ByteArrayInputStream("Q".getBytes());
        System.setIn(in);
        p.makeMove(whitePromotion, pawnW);
        assertEquals("g8=Q+", ChessIO.moveString(p,whitePromotion,pawnW));
        System.setIn(System.in);

        p.makeMove(whitePromotion, rookBh);
        assertEquals("Rxg8", ChessIO.moveString(p,whitePromotion,rookBh));

        Coordinate blackPromotion = new Coordinate('a',1);
        ByteArrayInputStream in2 = new ByteArrayInputStream("R".getBytes());
        System.setIn(in2);
        p.makeMove(blackPromotion, pawnB);
        assertEquals("bxa1=R+", ChessIO.moveString(p,blackPromotion,pawnB));
        System.setIn(System.in);

        p.makeMove(blackPromotion,bishopW);
        assertEquals("Bxa1", ChessIO.moveString(p,blackPromotion,bishopW));

        p.makeMove(new Coordinate('e',4), pawnWe);
        assertEquals("e4",ChessIO.moveString(p,new Coordinate('e',4),pawnWe));
    }



}

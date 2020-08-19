import org.junit.Test;
import static org.junit.Assert.*;

import java.util.*;

public class PiecesTest {

    Pieces p = new Pieces();
    HashMap<Coordinate, Piece> board = p.getPieces();

    @Test
    public void testCorrectPieces() {

        Coordinate whiteQueen = new Coordinate('D',1);
        Coordinate blackQueenKnight = new Coordinate('B',8);
        Coordinate whiteKingRook = new Coordinate('H',1);
        Coordinate blackPawnMiddle = new Coordinate('E',7);

        assertEquals(board.get(whiteQueen).getPieceID(), "*Q*White*d*");
        assertEquals(board.get(blackQueenKnight).getPieceID(), "*N*Black*b*");
        assertEquals(board.get(whiteKingRook).getPieceID(), "*R*White*h*");
        assertEquals(board.get(blackPawnMiddle).getPieceID(), "**Black*e*");
    }

    @Test
    public void notInBoard() {
        assertNull(board.get(new Coordinate('F', 5)));
        assertNull(board.get(new Coordinate('J', 9)));
    }

    @Test
    public void testFindPieceByPiece() {

        Rook rookWa = new Rook(COLOUR.W, new Coordinate('a',1));
        Knight knightBg = new Knight(COLOUR.B, new Coordinate('g',8));
        Pawn fakePiece = new Pawn (COLOUR.B, new Coordinate('c',4));

        assertEquals(p.findPiece(rookWa).toString(),"a1");
        assertEquals(p.findPiece(knightBg).toString(),"g8");
        assertEquals(Coordinate.emptyCoordinate,p.findPiece(fakePiece));
    }

    @Test
    public void testGetColouredPieces() {

        int pawnBRank = 7;
        int bRank = 8;
        int pawnWRank = 2;
        int wRank = 1;

        Pawn pawnB = new Pawn(COLOUR.B, new Coordinate('g', pawnBRank));
        Rook rookB = new Rook(COLOUR.B, new Coordinate('a', bRank));
        Knight knightB = new Knight(COLOUR.B, new Coordinate('h', 7));
        Queen queenB = new Queen(COLOUR.B, new Coordinate('d', bRank));
        Pawn pawnWe = new Pawn(COLOUR.W, new Coordinate('e', pawnWRank));
        Pawn pawnWh = new Pawn(COLOUR.W, new Coordinate('h', pawnWRank));
        Rook rookWa = new Rook(COLOUR.W, new Coordinate('a', wRank));
        Rook rookWh = new Rook(COLOUR.W, new Coordinate('h', wRank));
        Bishop bishopW = new Bishop(COLOUR.W, new Coordinate('f', 6));
        Queen queenW = new Queen(COLOUR.W, new Coordinate('g', 5));
        King kingW = new King(COLOUR.W, new Coordinate('e', wRank));

        Pieces pi = new Pieces(Boards.getTestBoard());
        HashMap<Coordinate, Piece> black = pi.getColourPieces(COLOUR.B);
        HashMap<Coordinate, Piece> white = pi.getColourPieces(COLOUR.W);

        ArrayList<Coordinate> expectedB = new ArrayList(Arrays.asList(pi.findPiece(pawnB),pi.findPiece(rookB),pi.findPiece(knightB),pi.findPiece(queenB)));
        Set<Coordinate> expectedBlack = new HashSet<>(expectedB);
        assertEquals(expectedBlack,black.keySet());

        ArrayList<Coordinate> expectedW = new ArrayList(Arrays.asList(pi.findPiece(pawnWe),
                pi.findPiece(pawnWh),pi.findPiece(rookWa),pi.findPiece(rookWh),pi.findPiece(bishopW),pi.findPiece(queenW),pi.findPiece(kingW)));
        Set<Coordinate> expectedWhite = new HashSet<>(expectedW);
        assertEquals(expectedWhite,white.keySet());

        expectedWhite.addAll(expectedBlack);

        assertEquals(expectedWhite,pi.getPieces().keySet());
    }

    @Test
    public void testFindKing() {
        // 2 kings
        assertEquals(p.findKing(COLOUR.B),new Coordinate('e',8));
        assertEquals(p.findKing(COLOUR.W),new Coordinate('e',1));
        // 1 king
        Pieces pi = new Pieces(Boards.getTestBoard());
        assertEquals(pi.findKing(COLOUR.W),new Coordinate('e',1));
        // no king
        assertEquals(pi.findKing(COLOUR.B),Coordinate.emptyCoordinate);
    }

    @Test
    public void testIsCheck() {

        Pieces pi = new Pieces (Boards.getCheckIngBoard());
        assertFalse(pi.isCheck(COLOUR.B));
        assertFalse(pi.isCheck(COLOUR.W));
        pi.makeMove(new Coordinate('d',1),pi.getPieces().get(new Coordinate('d',8)));
        assertTrue(pi.isCheck(COLOUR.W));
        assertFalse(pi.isCheck(COLOUR.B));
    }

    @Test
    public void testSameRank() {

        Pieces p = new Pieces(Boards.getCheckIngBoard());
        King kingB = (King) p.getPiece(new Coordinate('e',8));
        Queen queenB = (Queen) p.getPiece(new Coordinate('d',8));
        Rook rookW = (Rook) p.getPiece(new Coordinate('a',1));
        Pawn pawnW = (Pawn) p.getPiece(new Coordinate('e',2));
        assertFalse(p.pieceInSameRank(queenB));
        assertFalse(p.pieceInSameRank(kingB));
        assertTrue(p.pieceInSameRank(rookW));
        assertTrue(p.pieceInSameRank(pawnW));
    }

    @Test
    public void testSameFile() {

        Pieces p = new Pieces(Boards.getCheckIngBoard());
        King kingB = (King) p.getPiece(new Coordinate('e',8));
        Queen queenB = (Queen) p.getPiece(new Coordinate('d',8));
        Rook rookW = (Rook) p.getPiece(new Coordinate('a',1));
        Pawn pawnW = (Pawn) p.getPiece(new Coordinate('e',2));
        assertFalse(p.pieceInSameFile(queenB));
        assertFalse(p.pieceInSameFile(kingB));
        assertFalse(p.pieceInSameFile(rookW));
        assertFalse(p.pieceInSameFile(pawnW));

        Pieces pi = new Pieces(Boards.getChessBoard());
        Bishop bishopWc = (Bishop) pi.getPiece(new Coordinate('c',1));
        Pawn pawnWd = (Pawn) pi.getPiece(new Coordinate('d',2));
        pi.makeMove(new Coordinate('d',4),pawnWd);
        pi.makeMove(new Coordinate('f',4), bishopWc);
        assertTrue(p.pieceInSameFile(bishopWc));

    }

    @Test
    public void testToSameCoordinate() {

        Pieces p = new Pieces(Boards.getCheckIngBoard());
        King king = (King) p.getPiece(new Coordinate('e',1));
        Rook rookW = (Rook) p.getPiece(new Coordinate('h',1));

        p.makeMove(new Coordinate('g',1), king);
        assertTrue(p.pieceToSameCoordinate(new Coordinate('d',1),rookW));
        p.makeMove(new Coordinate('h',1), king);
        assertFalse(p.pieceToSameCoordinate(new Coordinate('g',1),rookW));
        assertTrue(p.pieceToSameCoordinate(new Coordinate('b',1),rookW));

    }

    @Test
    public void testIsCapture() {
        Pieces p = new Pieces(Boards.getCheckIngBoard());
        assertFalse(p.getIsCapture());

        Coordinate bCoord = new Coordinate('f',6);

        Pawn pawnB = (Pawn) p.getPiece(new Coordinate('g',7));
        Knight knightB = (Knight) p.getPiece(new Coordinate('h',7));
        Queen queenW = (Queen) p.getPiece(new Coordinate('g',5));
        Queen queenB = (Queen) p.getPiece(new Coordinate('d',8));
        Rook rookBa = (Rook) p.getPiece(new Coordinate('a',8));
        Rook rookWa = (Rook) p.getPiece(new Coordinate('a',1));

        p.makeMove(bCoord, pawnB);
        assertTrue(p.getIsCapture());
        p.makeMove(bCoord, queenW);
        assertTrue(p.getIsCapture());
        p.makeMove(new Coordinate('a',6), rookBa);
        assertFalse(p.getIsCapture());
        p.makeMove(bCoord, knightB);
        assertTrue(p.getIsCapture());
        p.makeMove(new Coordinate('a',6),rookWa);
        assertTrue(p.getIsCapture());
        p.makeMove(new Coordinate('d',7), queenB);
        assertFalse(p.getIsCapture());
    }

    @Test
    public void testIsDraw() {
        Pieces p = new Pieces();

        Coordinate ogB = new Coordinate('b',8);
        Coordinate ogW = new Coordinate('g',1);

        Knight knightB = (Knight) p.getPiece(ogB);
        Knight knightW = (Knight) p.getPiece(ogW);

        Coordinate coordB = new Coordinate('a',6);
        Coordinate coordW = new Coordinate('h',3);

        p.makeMove(coordW,knightW);
        p.makeMove(coordB,knightB);
        p.makeMove(ogW,knightW);
        p.makeMove(ogB,knightB);

        assertFalse(p.isDraw());

        p.makeMove(coordW,knightW);
        p.makeMove(coordB,knightB);
        p.makeMove(ogW,knightW);
        p.makeMove(ogB,knightB);

        assertTrue(p.isDraw());

        p.makeMove(coordW,knightW);
        assertTrue(p.isDraw());
        p.makeMove(coordB,knightB);
        assertTrue(p.isDraw());




    }


}

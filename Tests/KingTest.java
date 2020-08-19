import org.junit.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.Assert.*;

public class KingTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.err;

    @Before
    public void setUpStreams() {
        System.setErr(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setErr(originalOut);
    }

    @Test
    public void noCastle() {
        Pieces p = new Pieces();
        King kingW = (King) p.getPiece(new Coordinate('e',1));
        King kingB = (King) p.getPiece(new Coordinate('e',8));
        assertFalse(kingW.getHasMoved());
        assertFalse(kingB.getHasMoved());
        assertFalse(kingB.canCastleKing(p));
        assertFalse(kingB.canCastleQueen(p));
        assertFalse(kingW.canCastleKing(p));
        assertFalse(kingW.canCastleQueen(p));
        assertFalse(kingB.getRawMoves(p).contains(new Coordinate('g',8)));
        assertFalse(kingW.getRawMoves(p).contains(new Coordinate('c',1)));
        p.makeMove(new Coordinate('c',8),kingB);
        p.makeMove(new Coordinate('g',1),kingW);
        assertEquals("King to c8 is an invalid move.\nKing to g1 is an invalid move.\n", outContent.toString());
    }

    @Test
    public void correctCoordinates() {
        // set up board and pieces
        Pieces p = new Pieces(Boards.getCheckIngBoard());
        King kingW = (King) p.getPiece(new Coordinate('e',1));
        King kingB = (King) p.getPiece(new Coordinate('e',8));
        Rook rookWa = (Rook) p.getPiece(new Coordinate('a', 1));
        Rook rookWh = (Rook) p.getPiece(new Coordinate('h', 1));
        Rook rookBh = (Rook) p.getPiece(new Coordinate('h', 8));
        Rook rookB = (Rook) p.getPiece(new Coordinate('a', 8));
        // check that the white king potential moves consider castling
        HashSet<Coordinate> kingWMoves = new HashSet<>(Arrays.asList(new Coordinate('f',1),new Coordinate('f',2),
                new Coordinate('g',1),new Coordinate('c',1)));
        assertEquals(kingWMoves,kingW.getPotentialMoves() );
        for (Coordinate coord : kingW.getPotentialMoves()) {
            assertTrue(kingWMoves.contains(coord));
        }
        // check that the black king potential moves consider castling
        HashSet<Coordinate> kingBMoves = new HashSet<>(Arrays.asList(new Coordinate('f',8),
                new Coordinate('d',7),new Coordinate('f',7), new Coordinate('g',8)));
        assertEquals(kingBMoves,kingB.getPotentialMoves());
        for (Coordinate coord : kingB.getPotentialMoves()) {
            assertTrue(kingBMoves.contains(coord));
        }
        // check that each rook associated with castling is the correct one
        if (kingB.canCastleKing(p))
            assertEquals(rookBh,kingB.getRookKing());
        if (kingB.canCastleQueen(p))
            assertEquals(rookB,kingB.getRookQueen());
        if (kingW.canCastleKing(p))
            assertEquals(rookWh,kingW.getRookKing());
        if (kingW.canCastleQueen(p))
            assertEquals(rookWa,kingW.getRookQueen());
        assertFalse(kingB.canCastleQueen(p));
        // check that the rooks have a correct castling coordinate
        assertEquals(new Coordinate('d',1), rookWa.getCastleCoordRook());
        assertEquals(new Coordinate('f',1), rookWh.getCastleCoordRook());
        assertNull(rookB.getCastleCoordRook());
        assertEquals(new Coordinate('f',8), rookBh.getCastleCoordRook());
        // check that the kings have a correct castling coordinate
        assertEquals(new Coordinate('g',1), kingW.getCastleCoordKingK());
        assertEquals(new Coordinate('c',1), kingW.getCastleCoordKingQ());
        assertNull(kingB.getCastleCoordKingQ());
        assertEquals(new Coordinate('g',8), kingB.getCastleCoordKingK());
    }

    @Test
    public void movingRooks() {
        Pieces p = new Pieces(Boards.getCheckIngBoard());
        King kingW = (King) p.getPiece(new Coordinate('e',1));
        King kingB = (King) p.getPiece(new Coordinate('e',8));
        Rook rookWa = (Rook) p.getPiece(new Coordinate('a', 1));
        Rook rookWh = (Rook) p.getPiece(new Coordinate('h', 1));
        Rook rookBh = (Rook) p.getPiece(new Coordinate('h', 8));
        Rook rookB = (Rook) p.getPiece(new Coordinate('a', 8));
        p.makeMove(new Coordinate('a',1),rookB);
        assertFalse(kingW.canCastleQueen(p));
    }

    @Test
    public void canCastle() {
        Pieces p = new Pieces(Boards.getCheckIngBoard());
        King kingW = (King) p.getPiece(new Coordinate('e',1));
        King kingB = (King) p.getPiece(new Coordinate('e',8));
        assertFalse(kingW.getHasMoved());
        assertFalse(kingB.getHasMoved());
        assertTrue(kingB.canCastleKing(p));
        assertFalse(kingB.canCastleQueen(p));
        assertTrue(kingW.canCastleKing(p));
        assertTrue(kingW.canCastleQueen(p));
        assertTrue(kingB.getRawMoves(p).contains(new Coordinate('g',8)));
        assertTrue(kingW.getRawMoves(p).contains(new Coordinate('c',1)));
        p.makeMove(new Coordinate('f',8),kingB);
        assertFalse(kingB.canCastleKing(p));
        assertTrue(kingB.getRawMoves(p).contains(new Coordinate('g',8)));
        p.makeMove(new Coordinate('c',1),kingW);
        assertEquals(new Coordinate('c',1), p.findKing(COLOUR.W));
        assertEquals(new Coordinate('d',1), p.findPiece(new Rook(COLOUR.W, new Coordinate('a', 1))));
    }

    @Test
    public void testMoves() {
        Pieces p = new Pieces(Boards.getCheckIngBoard());

        King kingB = (King) p.getPiece(new Coordinate('e',8));
        HashSet<Coordinate> kingBMoves = new HashSet<>(Arrays.asList(new Coordinate('d',7), new Coordinate('f',7), new Coordinate('f',8), new Coordinate('g',8)));
        assertEquals(kingBMoves, kingB.getPotentialMoves());
        HashSet<Coordinate> kingBMoves2 = new HashSet<>(Arrays.asList(new Coordinate('e',6), new Coordinate('f',8), new Coordinate('g',8), new Coordinate('e',8)));
        p.makeMove(new Coordinate('f',7),kingB);
        assertEquals(kingBMoves2, kingB.getPotentialMoves());
        HashSet<Coordinate> kingBMoves3 = new HashSet<>(Arrays.asList(new Coordinate('f',7), new Coordinate('f',8)));
        p.makeMove(new Coordinate('g',8),kingB);
        assertEquals(kingBMoves3, kingB.getPotentialMoves());

        Queen queenW = (Queen) p.getPiece(new Coordinate('g',5));
        p.makeMove(new Coordinate('d',5), queenW);
        assertTrue(p.isCheck(COLOUR.B));
        HashSet<Coordinate> kingBMoves4 = new HashSet<>(Collections.singletonList(new Coordinate('f', 8)));
        assertEquals(kingBMoves4, kingB.getPotentialMoves());

    }
}

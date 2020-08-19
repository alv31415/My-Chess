import org.junit.*;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.Assert.*;

public class PawnTest {

    Pieces p = new Pieces(Boards.getPromotingBoard());

    @Test
    public void noPromotion() {
        Pawn pawn = (Pawn) p.getPiece(new Coordinate('h',2));
        p.makeMove(new Coordinate('h',4),pawn);
        assertEquals(ID.PAWN, pawn.getName());
        assertEquals("[h5]", pawn.getPotentialMoves().toString());
    }

    @Test
    public void promoteAndCheck() {
        Pawn pawn = (Pawn) p.getPiece(new Coordinate('g',7));
        ByteArrayInputStream in = new ByteArrayInputStream("Q".getBytes());
        System.setIn(in);
        p.makeMove(new Coordinate('g',8),pawn);
        assertTrue(p.isCheck(COLOUR.B));
        System.setIn(System.in);
    }

    @Test
    public void promoteNoCheck() {
        Pawn pawn = (Pawn) p.getPiece(new Coordinate('g',7));
        ByteArrayInputStream in = new ByteArrayInputStream("N".getBytes());
        System.setIn(in);
        p.makeMove(new Coordinate('g',8),pawn);
        assertFalse(p.isCheck(COLOUR.B));
        System.setIn(System.in);
    }

    @Test
    public void promoteAndEat() {
        Pawn pawn = (Pawn) p.getPiece(new Coordinate('b',2));
        ByteArrayInputStream in = new ByteArrayInputStream("R".getBytes());
        System.setIn(in);
        p.makeMove(new Coordinate('a',1),pawn);
        assertTrue(p.isCheck(COLOUR.W));
        System.setIn(System.in);
    }

    @Test
    public void enPassantSituations() {
        Pieces p = new Pieces(Boards.getCheckIngBoard());
        Pawn pawnWh = (Pawn) p.getPiece(new Coordinate('h',2));
        Pawn pawnWe = (Pawn) p.getPiece(new Coordinate('e',2));
        Pawn pawnB = (Pawn) p.getPiece(new Coordinate('g',7));
        p.makeMove(new Coordinate('h',3),pawnWh);
        assertFalse(pawnWh.getHasMovedTwo());
        assertTrue(pawnWh.getHasMoved());
        assertEquals(new Coordinate('h',2),pawnWh.getPreviousCoordinate());
        p.makeMove(new Coordinate('e',4), pawnWe);
        assertTrue(pawnWe.getHasMovedTwo());
        assertTrue(pawnWe.getHasMoved());
        assertEquals(new Coordinate('e',2),pawnWe.getPreviousCoordinate());
        p.makeMove(new Coordinate('e',5), pawnWe);
        assertEquals(new Coordinate('e',4),pawnWe.getPreviousCoordinate());
        assertFalse(pawnB.getHasMovedTwo());
        assertFalse(pawnB.getHasMoved());
        assertEquals(new Coordinate('g',7),pawnB.getPreviousCoordinate());
    }

    @Test
    public void capturesEnPassant() {
        Pieces p = new Pieces();
        Pawn pawnWh = (Pawn) p.getPiece(new Coordinate('h',2));
        Pawn pawnWe = (Pawn) p.getPiece(new Coordinate('e',2));
        Pawn pawnBg = (Pawn) p.getPiece(new Coordinate('g',7));
        Pawn pawnBd = (Pawn) p.getPiece(new Coordinate('d',7));
        Pawn pawnWa = (Pawn) p.getPiece(new Coordinate('a',2));
        Pawn pawnBb = (Pawn) p.getPiece(new Coordinate('b',7));

        assertFalse(pawnBg.getHasMovedTwo());
        assertFalse(pawnBd.getHasMovedTwo());
        assertFalse(pawnBb.getHasMovedTwo());
        assertFalse(pawnWa.getHasMovedTwo());
        assertFalse(pawnWh.getHasMovedTwo());
        assertFalse(pawnWe.getHasMovedTwo());
        // white captures en passant
        p.makeMove(new Coordinate('h',4), pawnWh);
        assertTrue(pawnWh.getHasMovedTwo());
        assertEquals(new ArrayList<Coordinate>(), pawnWh.enPassant(p));
        p.makeMove(new Coordinate('h',5), pawnWh);
        p.makeMove(new Coordinate('g',5), pawnBg);
        assertTrue(pawnBg.getHasMovedTwo());
        assertEquals(new ArrayList<>(Collections.singletonList(new Coordinate('g',6))), pawnWh.enPassant(p));
        assertEquals(new HashSet<>(Arrays.asList(new Coordinate('g', 6), new Coordinate('h',6))), pawnWh.getPotentialMoves());
        // black captures en passant
        p.makeMove(new Coordinate('d',5), pawnBd);
        assertTrue(pawnBd.getHasMovedTwo());
        assertEquals(new ArrayList<Coordinate>(), pawnBd.enPassant(p));
        p.makeMove(new Coordinate('d',4), pawnBd);
        p.makeMove(new Coordinate('e',4), pawnWe);
        assertTrue(pawnWe.getHasMovedTwo());
        assertEquals(new ArrayList<>(Collections.singletonList(new Coordinate('e',3))), pawnBd.enPassant(p));
        assertEquals(new HashSet<>(Arrays.asList(new Coordinate('e', 3), new Coordinate('d',3))), pawnBd.getPotentialMoves());
        // white can't capture en passant because black hasn't move 2 inmediately
        p.makeMove(new Coordinate('b',5), pawnBb);
        p.makeMove(new Coordinate('a',4), pawnWa);
        assertTrue(pawnWa.getHasMovedTwo());
        assertEquals(new ArrayList<Coordinate>(), pawnWa.enPassant(p));
        p.makeMove(new Coordinate('a',5), pawnWa);
        assertTrue(pawnBb.getHasMovedTwo());
        assertEquals(new ArrayList<>(), pawnWa.enPassant(p));
        assertEquals(new HashSet<>(Collections.singletonList(new Coordinate('a', 6))), pawnWa.getPotentialMoves());

    }


}

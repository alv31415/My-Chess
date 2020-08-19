import javafx.collections.ListChangeListener;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.Assert.*;

public class RawMoveTests {

    Pieces p = new Pieces(Boards.getCheckIngBoard());
    King kingB = (King) p.getPiece(new Coordinate('e',8));
    Queen queenB = (Queen) p.getPiece(new Coordinate('d',8));
    Rook rookBa = (Rook) p.getPiece(new Coordinate('a',8));
    Rook rookBh = (Rook) p.getPiece(new Coordinate('h',8));
    Knight knightB = (Knight) p.getPiece(new Coordinate('h',7));
    Pawn pawnB = (Pawn) p.getPiece(new Coordinate('g',7));

    King kingW = (King) p.getPiece(new Coordinate('e',1));
    Queen queenW = (Queen) p.getPiece(new Coordinate('g',5));
    Rook rookWa = (Rook) p.getPiece(new Coordinate('a',1));
    Rook rookWh = (Rook) p.getPiece(new Coordinate('h',1));
    Bishop bishopW = (Bishop) p.getPiece(new Coordinate('f',6));
    Pawn pawnWe = (Pawn) p.getPiece(new Coordinate('e',2));
    Pawn pawnWh = (Pawn) p.getPiece(new Coordinate('h',2));

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

    public static boolean equalArrayLists(ArrayList<Coordinate> arr1, ArrayList<Coordinate> arr2) {
        boolean firstEqualsSecond = true;
        boolean secondEqualsFirst = true;

        for (Coordinate value : arr2) {
            firstEqualsSecond &= arr1.contains(value);
        }

        for (Coordinate value : arr1) {
            secondEqualsFirst &= arr2.contains(value);
        }

        return firstEqualsSecond && secondEqualsFirst && arr1.size()==arr2.size();
    }

    @Test
    public void megaMoveTest() {

        //it isn't check
        assertFalse(p.isCheck(COLOUR.B));
        assertFalse(p.isCheck(COLOUR.W));
        HashSet<Coordinate> blackKingMoves = new HashSet<>(Arrays.asList(new Coordinate('d',7), new Coordinate('f',7), new Coordinate('f',8), new Coordinate('g',8)));
        HashSet<Coordinate> whiteKingMoves = new HashSet<>(Arrays.asList(new Coordinate('f',2), new Coordinate('f',1), new Coordinate('g',1), new Coordinate('c',1)));
        assertEquals(blackKingMoves,kingB.getPotentialMoves());
        assertEquals(whiteKingMoves,kingW.getPotentialMoves());

        //queenW takes pawn
        p.makeMove(new Coordinate('g',7), queenW);
        assertEquals(Coordinate.emptyCoordinate, p.findPiece(pawnB));
        assertEquals("Pawn not found.\n", outContent.toString());

        //king can't move
        blackKingMoves.clear();
        assertEquals(blackKingMoves,kingB.getPotentialMoves());
        assertFalse(p.isMate(COLOUR.B));

        //queenB checks
        p.makeMove(new Coordinate('a',5), queenB);
        assertTrue(p.isCheck(COLOUR.W));
        assertFalse(p.isMate(COLOUR.W));
        assertFalse(kingW.canCastleQueen(p));
        assertFalse(kingW.canCastleKing(p));
        whiteKingMoves.remove(new Coordinate('c',1));
        whiteKingMoves.remove(new Coordinate('d',2));
        whiteKingMoves.remove(new Coordinate('g',1));
        whiteKingMoves.add(new Coordinate('d',1));
        assertEquals(whiteKingMoves,kingW.getPotentialMoves());

        //bishop can only move to one place to defend
        HashSet<Coordinate> whiteBishopMoves = new HashSet<>(Collections.singletonList(new Coordinate('c', 3)));
        assertEquals(whiteBishopMoves, bishopW.getPotentialMoves());

        //rook can only save check by taking queen
        HashSet<Coordinate> whiteRookMoves = new HashSet<>(Collections.singletonList(new Coordinate('a',5)));
        assertEquals(whiteRookMoves, rookWa.getPotentialMoves());

        //white queen has no possible moves
        HashSet<Coordinate> whiteQueenMoves = new HashSet<>();
        assertEquals(whiteQueenMoves, queenW.getPotentialMoves());

        //rookWa takes queenB & it is no longer check
        p.makeMove(new Coordinate('a',5), rookWa);
        assertFalse(p.isCheck(COLOUR.W));

        //kingW can't castle king side
        assertFalse(kingW.canCastleQueen(p));
        assertTrue(kingW.canCastleKing(p));
        whiteKingMoves.add(new Coordinate('g',1));
        whiteKingMoves.add(new Coordinate('d',2));
        assertEquals(whiteKingMoves,kingW.getPotentialMoves());

        //rookBa takes rookWa
        p.makeMove(new Coordinate('a',5), rookBa);
        whiteBishopMoves.add(new Coordinate('e',5));
        whiteBishopMoves.add(new Coordinate('d',4));
        whiteBishopMoves.add(new Coordinate('b',2));
        whiteBishopMoves.add(new Coordinate('a',1));
        whiteBishopMoves.add(new Coordinate('g',5));
        whiteBishopMoves.add(new Coordinate('h',4));
        whiteBishopMoves.add(new Coordinate('e',7));
        whiteBishopMoves.add(new Coordinate('d',8));
        assertEquals(whiteBishopMoves, bishopW.getPotentialMoves());

        //kingW castles king side
        p.makeMove(new Coordinate('e',2),kingW);
        assertEquals("Pawn not found.\nKing to e2 is an invalid move.\n", outContent.toString());
        p.makeMove(new Coordinate('g',1),kingW);
        assertEquals(new Coordinate('f',1), p.findPiece(rookWh));
        assertEquals(new Coordinate('g',1), p.findPiece(kingW));

        //knightB takes bishopW
        p.makeMove(new Coordinate('f',6),knightB);
        assertEquals(Coordinate.emptyCoordinate,p.findPiece(bishopW));
        assertEquals("Pawn not found.\nKing to e2 is an invalid move.\nBishop not found.\n", outContent.toString());
        assertFalse(p.isCheck(COLOUR.B));

        //queenW takes knightB
        p.makeMove(new Coordinate('f',6),queenW);
        assertEquals(Coordinate.emptyCoordinate,p.findPiece(knightB));
        assertEquals("Pawn not found.\nKing to e2 is an invalid move.\nBishop not found.\nKnight not found.\n", outContent.toString());
        assertFalse(p.isCheck(COLOUR.B));

        //there are 8 pieces on board
        assertEquals(8,p.getPieces().size());

        //the only black pieces left are the king & the 2 rooks
        assertEquals(3,p.getColourPieces(COLOUR.B).size());

        //push pawnWh and promote to queen
        p.makeMove(new Coordinate('g',7),queenW);
        p.makeMove(new Coordinate('d',8), kingB);
        p.makeMove(new Coordinate('h',4),pawnWh);
        p.makeMove(new Coordinate('h',5), pawnWh);
        p.makeMove(new Coordinate('g',8), rookBh);
        p.makeMove(new Coordinate('h',6),pawnWh);
        p.makeMove(new Coordinate('c',8),kingB);
        p.makeMove(new Coordinate('h',7),pawnWh);
        p.makeMove(new Coordinate('b',8),kingB);
        ByteArrayInputStream in = new ByteArrayInputStream("Q".getBytes());
        System.setIn(in);
        p.makeMove(new Coordinate('g',8),pawnWh);
        System.out.println(Boards.displayBoard(p));

        //check mate
        assertTrue(p.isMate(COLOUR.B));

    }


}

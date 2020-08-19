/*
package backup;

import backup.Moves;
import org.junit.*;

import java.util.HashMap;

import static org.junit.Assert.*;


public class MovesTest {

    Pieces p = new Pieces();
    HashMap<Coordinate, Piece> pieces = p.getPieces();

    Coordinate coord1 = new Coordinate('a',1);
    Coordinate coord2 = new Coordinate('A',1);
    Coordinate coord3 = new Coordinate('c',7);
    Coordinate coord4 = new Coordinate('d',5);

    @Test
    public void testCoordDifference() {
        assertArrayEquals(Moves.getCoordDifference(coord1, coord2), new int[]{0, 0});
        assertArrayEquals(Moves.getCoordDifference(coord1, coord4), new int[]{3, 4});
        assertArrayEquals(Moves.getCoordDifference(coord3, coord2), new int[]{-2, -6});
    }

    @Test (expected = IllegalArgumentException.class)
    public void testCoordDifferenceIllegal() {

        Coordinate coord5 = new Coordinate('b',-3);
        Coordinate coord6 = new Coordinate('j',6);
        assertArrayEquals(Moves.getCoordDifference(coord5, coord6), new int[]{8, 9});
    }

    @Test
    public void testTileFull() {
        assertTrue(Moves.tileFull(p,coord1));
        assertTrue(Moves.tileFull(p,coord3));
        assertFalse(Moves.tileFull(p,coord4));
    }

    @Test
    public void testdestHasDifferentColour() {
        //tile with piece of same colour
        assertFalse(Moves.destHasDifferentColour(p,pieces.get(coord1),new Coordinate('b',1)));
        //tile with piece of different colour
        assertTrue(Moves.destHasDifferentColour(p,pieces.get(coord1),coord3));
        //tile with no other piece
        assertTrue(Moves.destHasDifferentColour(p,pieces.get(coord1),coord4));
    }

    @Test
    public void testValidVertical() {

        //pieces that can't move freely (vertically)

        // valid vertical moves

        // valid vertical moves but piece on the way

        // valid vertical with piece on destination

        // invalid (movement is 0)

    }

    @Test
    public void testValidHorizontal() {

        //pieces that can't move freely (horizontally)

        // valid horizontal moves

        // valid horizontal moves but piece on the way

        // valid horizontal with piece on destination

        // invalid (movement is 0)

    }

    @Test
    public void testValidDiagonal() {

        //pieces that can't move freely (diagonally)

        // valid diagonal moves

        // valid diagonal moves but piece on the way

        // valid diagonal with piece on destination

        // invalid (movement is 0)

    }

    @Test
    public void testValidKnight() {

        //non-knight pieces invalid

        // valid knight moves

        // valid knight moves with piece on the way

        // valid knight move with piece on destination

        // invalid (movement is 0)

    }

    @Test
    public void testValidPawn() {

        // moves 2 only at start

        // moves 1 forward

        // can't move backward

        // captures diagonally

        // can't move diagonally if there is another piece

    }






}
*/

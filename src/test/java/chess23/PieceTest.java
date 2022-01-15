import static org.junit.Assert.*;

import chess23.*;
import org.junit.Test;

import javax.swing.*;
import java.util.ArrayList;


public class PieceTest {

    Piece piece1 = new Piece(ID.KING, COLOUR.B, new Coordinate('a', 1)) {

        @Override
        public ArrayList<Coordinate> getRawMoves(Pieces pieces) {
            return null;
        }

        @Override
        public ImageIcon getImageIcon() {
            return null;
        }

        @Override
        public Piece makeCopy() {
            return null;
        }
    };
    @Test
    public void getters() {
        assertEquals(piece1.getCoords().toString(), "a1");
        assertEquals(piece1.getColour().toString(), "Black" );
        assertEquals(piece1.getName().toString(), "K");
        assertEquals(piece1.getOgCoord().toString(), "a1");
    }

    @Test
    public void setters() {
        piece1.setCoords(new Coordinate('b',2));
        assertEquals(piece1.getCoords().toString(), "b2");
        assertNotEquals(piece1.getCoords(), piece1.getOgCoord());
        assertEquals(piece1.getColour().toString(), "Black" );
        assertEquals(piece1.getName().toString(), "K");
        assertEquals(piece1.getOgCoord().toString(), "a1");
    }
    @Test
    public void toStringTest() {
        piece1.setCoords(new Coordinate('b',2));
        assertEquals(piece1.toString(), "Kb2");
        assertEquals(piece1.toFancyString(), "chess23.King is at b2");
        piece1.setCoords(new Coordinate('c',4));
        assertEquals(piece1.toString(), "Kc4");
        assertEquals(piece1.toFancyString(), "chess23.King is at c4");
    }







}

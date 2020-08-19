import org.junit.*;

import static org.junit.Assert.*;

public class CoordinateTest {

    Coordinate coord1 = new Coordinate('a',1);
    Coordinate coord2 = new Coordinate('A',1);
    Coordinate coord3 = new Coordinate('c',7);
    Coordinate coord4 = new Coordinate('d',5);


    @Test
    public void equalCoords() {
        assertEquals(coord1, coord2);
    }

    @Test
    public void diffCoords() {
        assertNotEquals(coord1, coord3);
    }

    @Test
    public void coordString() {
        assertEquals("a1", coord1.toString());
        assertEquals("d5", coord4.toString());
    }

    @Test
    public void getters() {
        assertEquals('c', coord3.getFile());
        assertEquals(5, coord4.getRank());
    }

    /*@Test (expected=IllegalArgumentException. class)
    public void invalidCoords() {
        new Coordinate('b',-3);
        new Coordinate('b',10);
        new Coordinate('z',-3);
        new Coordinate('a',0);
        new Coordinate('6','z');
    }*/


}

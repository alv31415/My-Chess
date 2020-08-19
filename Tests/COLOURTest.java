import org.junit.*;
import static org.junit.Assert.*;

public class COLOURTest {

    @Test
    public void colourToString() {
        assertEquals(COLOUR.B.toString(),"Black");
        assertEquals(COLOUR.W.toString(),"White");
        assertNotEquals(COLOUR.B.toString(),"White");
        assertNotEquals(COLOUR.W.toString(),"Green");
        // test enums are not equal
        assertNotEquals(COLOUR.B, COLOUR.W);
    }

}

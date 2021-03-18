

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

/**
 * 
 * @author nlao1
 *
 */
public class ConnectFourTest {
    private ConnectFour cf;

    @BeforeEach
    public void init() {
        cf = new ConnectFour();
    }

    @Test
    public void testInitEmpty() {
        for (int i = 0; i < cf.getWidth(); i++) {
            for (int j = 0; j < cf.getHeight(); j++) {
                assertEquals(0, cf.get(i, j));
            }
        }
    }

    @Test
    public void playerOneStarts() {
        assertEquals(1, cf.getCurrentPlayer());
    }

    @Test
    public void playerTwoSucceeds() {
        assertTrue(cf.place(0));
        assertEquals(2, cf.getCurrentPlayer());
    }

    @Test
    public void testPlacePiece() {
        assertTrue(cf.place(0));
        assertEquals(1, cf.get(0, 5));
    }

    @Test
    public void fullColumnPlaceFails() {
        for (int i = 0; i < cf.getHeight(); i++) {
            cf.set(0, i, 1);
        }
        assertFalse(cf.place(0));
        // tests that all elements of column 1 are non-empty
        for (int i = 0; i < cf.getHeight(); i++) {
            assertNotEquals(0, cf.get(0, i));
        }
    }

    @Test
    public void playerStaysWhenPlaceFails() {
        for (int i = 0; i < cf.getHeight(); i++) {
            assertTrue(cf.place(0));
        }
        assertEquals(1, cf.getCurrentPlayer());
        assertFalse(cf.place(0));
        assertEquals(1, cf.getCurrentPlayer());
    }

    @Test
    public void testHorizontalWin() {
        for (int i = 0; i < 4; i++) {
            assertEquals(0, cf.complete());
            cf.set(i, cf.getHeight() - 1, 1);
        }
        assertEquals(1, cf.get(0, 5));
        assertEquals(1, cf.get(1, 5));
        assertEquals(1, cf.get(2, 5));
        assertEquals(1, cf.get(3, 5));
        assertEquals(1, cf.complete());
    }

    @Test
    public void testVerticalWin() {
        for (int i = 0; i < 4; i++) {
            assertEquals(0, cf.complete());
            cf.set(0, cf.getHeight() - 1 - i, 2);
        }
        assertEquals(2, cf.complete());
    }

    @Test
    public void testUpRightDiagonalWin() {
        for (int i = 0; i < 4; i++) {
            assertEquals(0, cf.complete());
            cf.set(i, 5 - i, 1);
        }
        assertEquals(1, cf.complete());
    }

    @Test
    public void testDownRightDiagonalWin() {
        for (int i = 0; i < 4; i++) {
            assertEquals(0, cf.complete());
            cf.set(i, 2 + i, 2);
        }
        assertEquals(2, cf.complete());
    }

    @Test
    public void tieIfAllSlotsFilled() {
        for (int i = 0; i < 7; i++) {
            if (i % 2 == 0) {
                // red is 1, yellow is 2
                cf.set(i, 0, 1);
                cf.set(i, 1, 2);
                cf.set(i, 2, 1);
                cf.set(i, 3, 1);
                cf.set(i, 4, 2);
                cf.set(i, 5, 1);
            } else {
                cf.set(i, 0, 2);
                cf.set(i, 1, 1);
                cf.set(i, 2, 2);
                cf.set(i, 3, 2);
                cf.set(i, 4, 1);
                cf.set(i, 5, 2);
            }
        }
        assertEquals(3, cf.complete());
    }
}




import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.*;


/**
 * 
 * @author nlao1
 *
 */
public class CheckersTest {
    private Checkers c;
    
    @Test
    public void testInit() {
        c = new Checkers();
        assertEquals(Checkers.SIZE, c.getWidth());
        assertEquals(Checkers.SIZE, c.getHeight());
        char[][] expected = new char[][] {
            {'x', 'b', 'x', 'b', 'x', 'b', 'x', 'b'},
            {'b', 'x', 'b', 'x', 'b', 'x', 'b', 'x'},
            {'x', 'b', 'x', 'b', 'x', 'b', 'x', 'b'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'r', 'x', 'r', 'x', 'r', 'x', 'r', 'x'},
            {'x', 'r', 'x', 'r', 'x', 'r', 'x', 'r'},
            {'r', 'x', 'r', 'x', 'r', 'x', 'r', 'x'}
        };
        
        for (int i = 0; i < expected.length; i++) {
            for (int j = 0; j < expected[0].length; j++) {
                assertEquals(expected[i][j], c.get(i, j));
            }
        }
    }
    
    @Test
    public void testToStringInit() {
        c = new Checkers();
        String expected = 
                        "xbxbxbxb\n"
                        + "bxbxbxbx\n"
                        + "xbxbxbxb\n"
                        + "xxxxxxxx\n"
                        + "xxxxxxxx\n"
                        + "rxrxrxrx\n"
                        + "xrxrxrxr\n"
                        + "rxrxrxrx\n";
        assertEquals(expected, c.toString());
    }
    
    @Test
    public void noWinnerInit() {
        c = new Checkers();
        assertFalse(c.gameOver());
    }
    
    @Test
    public void initCustom() {
        char[][] data = new char[][] {
            {'x', 'b', 'x', 'b', 'x', 'b', 'x', 'b'},
            {'b', 'x', 'b', 'x', 'b', 'x', 'b', 'x'},
            {'x', 'b', 'x', 'b', 'x', 'b', 'x', 'b'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'r', 'x', 'r', 'x', 'r', 'x', 'r', 'x'},
            {'x', 'r', 'x', 'r', 'x', 'r', 'x', 'r'},
            {'r', 'x', 'r', 'x', 'r', 'x', 'r', 'x'}
        };
        c = new Checkers(data);
        
        Checkers expected = new Checkers();
        for (int i = 0; i < Checkers.SIZE; i++) {
            for (int j = 0; j < Checkers.SIZE; j++) {
                assertEquals(expected.get(i, j), c.get(i, j));
            }
        }
    }
    
    @Test
    public void testMoveRedEdge() {
        c = new Checkers();
        
        List<CheckersMove> moves = c.getPossibleMoves(5, 0);
        assertEquals(1, moves.size());
        CheckersMove move = moves.get(0);
        assertEquals(5, move.getStartRow());
        assertEquals(4, move.getEndRow());
        assertEquals(0, move.getStartCol());
        assertEquals(1, move.getEndCol());
    }
    
    @Test
    public void testMoveRedCenter() {
        c = new Checkers();
        
        List<CheckersMove> moves = c.getPossibleMoves(5, 2);
        assertEquals(2, moves.size());
    }
    
    @Test
    public void testMoveBlackEdge() {
        c = new Checkers();
        
        List<CheckersMove> moves = c.getPossibleMoves(2, 7);
        assertEquals(1, moves.size());
        CheckersMove move = moves.get(0);
        assertEquals(2, move.getStartRow());
        assertEquals(3, move.getEndRow());
        assertEquals(7, move.getStartCol());
        assertEquals(6, move.getEndCol());
    }
    
    @Test
    public void testMoveBlackCenter() {
        c = new Checkers();
        
        List<CheckersMove> moves = c.getPossibleMoves(2, 1);
        assertEquals(2, moves.size());
    }
    
    
    @Test
    public void testSingleSkipRed() {
        char[][] data = new char[][] {
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'b', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'r', 'x', 'x', 'b', 'x', 'b', 'x', 'b'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'}
        };
        c = new Checkers(data);
        List<CheckersMove> moves = c.getPossibleMoves(2, 0);
        assertEquals(1, moves.size());
        CheckersMove move = moves.get(0);
        assertTrue(move.isSkip());
        assertEquals(0, move.getEndRow());
        assertEquals(2, move.getEndCol());
    }
    
    @Test
    public void testSingleSkipBlack() {
        char[][] data = new char[][] {
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'b', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'b', 'x', 'r', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'}
        };
        c = new Checkers(data);
        List<CheckersMove> moves = c.getPossibleMoves(1, 2);
        assertEquals(1, moves.size());
        CheckersMove move = moves.get(0);
        assertTrue(move.isSkip());
        assertEquals(3, move.getEndRow());
        assertEquals(4, move.getEndCol());
    }
    
    @Test
    public void noMovesStuckPiece() {
        char[][] data = new char[][] {
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'r', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'r', 'x', 'x', 'b', 'x', 'b', 'x', 'b'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'}
        };
        c = new Checkers(data);
        assertEquals(0, c.getPossibleMoves(2, 0).size());
    }
    
    @Test
    public void executeChangesBoardNonSkip() {
        c = new Checkers();
        c.execute(c.getPossibleMoves(5, 0).get(0));
        assertEquals('x', c.get(5, 0));
        assertEquals('r', c.get(4, 1));
    }
    
    @Test
    public void testKingFourOptions() {
        char[][] data = new char[][] {
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'B', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'}
        };
        c = new Checkers(data);
        assertEquals(4, c.getPossibleMoves(2, 5).size());
    }
    
    @Test
    public void testKingSkipBackward() {
        char[][] data = new char[][] {
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'r', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'B', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'}
        };
        c = new Checkers(data);
        List<CheckersMove> possible = c.getPossibleMoves(2, 5);
        CheckersMove skip = null;
        for (CheckersMove move : possible) {
            if (move.isSkip()) {
                skip = move;
            }
        }
        //existence of a jump move
        assertNotEquals(null, skip);
        c.execute(skip);
        assertEquals('B', c.get(0, 3));
    }
    
    @Test
    public void testDoubleSkipRedSameDirection() {
        char[][] testData = new char[][] {
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'b', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'b', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'r', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'}
        };
        c = new Checkers(testData);
        List<CheckersMove> possible = c.getPossibleMoves(5, 0);
        CheckersMove skip = null;
        assertEquals(2, possible.size());
        for (CheckersMove move : possible) {
            if (move.getEndRow() == 1) {
                skip = move;
            }
        }
        //existence of a jump move
        assertNotEquals(null, skip);
        c.execute(skip);
        assertEquals('r', c.get(1, 4));
    }
    
    @Test
    public void testDoubleSkipRedSwitchDirection() {
        char[][] data = new char[][] {
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'b', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'b', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'r', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'}
        };
        c = new Checkers(data);
        List<CheckersMove> possible = c.getPossibleMoves(5, 0);
        CheckersMove skip = null;
        assertEquals(2, possible.size());
        for (CheckersMove move : possible) {
            if (move.getEndRow() == 1) {
                skip = move;
            }
        }
        //existence of a jump move
        assertNotEquals(null, skip);
        c.execute(skip);
        assertEquals('r', c.get(1, 0));
    }
    
    @Test
    public void testFalseDoubleSkipRedSwitchDirection() {
        char[][] data = new char[][] {
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'b', 'x', 'b', 'x', 'x', 'x'},
            {'x', 'r', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'}
        };
        c = new Checkers(data);
        assertEquals(2, c.getPossibleMoves(4, 1).size());
    }
    
    @Test
    public void testDoubleSkipBlackSameDirection() {
        char[][] data = new char[][] {
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'b', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'r', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'r', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'}
        };
        c = new Checkers(data);
        assertEquals(3, c.getPossibleMoves(1, 4).size());
    }
    
    @Test
    public void testDoubleSkipBlackSwitchDirection() {
        char[][] data = new char[][] {
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'b', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'r', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'r', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'}
        };
        c = new Checkers(data);
        assertEquals(3, c.getPossibleMoves(2, 3).size());
    }
    
    @Test
    public void testFalseDoubleSkipBlackSwitchDirection() {
        char[][] data = new char[][] {
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'b', 'x', 'x', 'x'},
            {'x', 'r', 'x', 'r', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'}
        };
        c = new Checkers(data);
        assertEquals(2, c.getPossibleMoves(1, 4).size());
    }
    
    @Test
    public void testDoubleSkipKing() {
        char[][] data = new char[][] {
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'B', 'x', 'x', 'x'},
            {'x', 'r', 'x', 'r', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'}
        };
        c = new Checkers(data);
        assertEquals(5, c.getPossibleMoves(1, 4).size());
        CheckersMove doubleskip = null;
        for (CheckersMove move : c.getPossibleMoves(1, 4)) {
            if (move.getEndRow() == 1 && move.getEndCol() == 0) {
                doubleskip = move;
            }
        }
        c.execute(doubleskip);
        assertEquals('B', c.get(1, 0));
        assertEquals(2, c.getPossibleMoves(1, 0).size());
    }
    
    @Test
    public void promotionRed() {
        char[][] data = new char[][] {
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'r', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'}
        };
        c = new Checkers(data);
        c.execute(c.getPossibleMoves(1, 0).get(0));
        assertEquals('R', c.get(0, 1));
    }
    
    @Test
    public void promotionBlack() {
        char[][] data = new char[][] {
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'b', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'}
        };
        c = new Checkers(data);
        c.execute(c.getPossibleMoves(6, 0).get(0));
        assertEquals('B', c.get(7, 1));
    }
    
    @Test
    public void kingOnEdge() {
        char[][] data = new char[][] {
            {'x', 'R', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
            {'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x'}
        };
        c = new Checkers(data);
        assertEquals(2, c.getPossibleMoves(0, 1).size());
    }
}

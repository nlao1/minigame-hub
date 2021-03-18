


/**
 * class that represents a move. Used for the recursive tree search.
 * @author nlao1
 */
public class CheckersMove {
    private final int startRow, startCol, endRow, endCol;
    private boolean isSkip;
    private final CheckersMove prev;


    public CheckersMove(int startRow, int startCol, int endRow, int endCol, boolean isJump,
                    CheckersMove prev) {
        super();
        this.startRow = startRow;
        this.startCol = startCol;
        this.endRow = endRow;
        this.endCol = endCol;
        this.isSkip = isJump;
        this.prev = prev;
    }

    /**
     * The jump information is enough, along with the start and end positions, to
     * determine which piece in the board was removed.
     * 
     * @return whether or note the move is a jump
     */
    public boolean isSkip() {
        return isSkip;
    }

    /**
     * A direct return is okay because we won't be modifying the lists with the
     * getter
     * 
     * @return a list of previous moves
     */
    public CheckersMove getPreviousMove() {
        return prev;
    }

    /**
     * 
     * @return the startRow
     */
    public int getStartRow() {
        return startRow;
    }

    /**
     * @return the startCol
     */
    public int getStartCol() {
        return startCol;
    }

    /**
     * @return the endRow
     */
    public int getEndRow() {
        return endRow;
    }

    /**
     * @return the endCol
     */
    public int getEndCol() {
        return endCol;
    }
}

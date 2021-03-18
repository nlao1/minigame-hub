

/**
 * This is a simple game of connect four.
 * @author nlao1
 *
 */
public class ConnectFour {
    /**
     * INVARIANT: the only integers stored in {@code data} are {0, 1, 2} where 0
     * represents an empty square, 1 is player 1 (red), and 2 is player 2 (yellow).
     * {@code data} is a column-centered array with indices increasing to the right
     * and down (similar to computer graphics). {@code data} is also a rectangular
     * 2D array.
     */
    private int[][] data;
    private int currentPlayer;

    /**
     * Creates a Connect 4 game with width 7 and height 6.
     */
    public ConnectFour() {
        reset();
    }

    /**
     * @return width of the board
     */
    public int getWidth() {
        return data.length;
    }

    /**
     * 
     * @return height of the board
     */
    public int getHeight() {
        return data[0].length;
    }

    /**
     * Returns the data at the specified x and y in the board
     * 
     * @param x number of the column to set (starts at 0) and increases right
     * @param y number of the row to set (starts at 0) and increases downward
     * @return 0 if the slot is empty, 1 if player 1 has a piece there, and if 2 if
     *         player 2 has a piece there
     */
    public int get(int x, int y) {
        return data[x][y];
    }

    /**
     * This method is for testing purposes only. It sets a specific position in the
     * board according to the parameters. This method "defies" gravity.
     * 
     * @param x      number of the column to set (starts at 0) and increases right
     * @param y      number of the row to set (starts at 0) and increases downward
     * @param player number of the player whose piece is going in this slot
     */
    public void set(int x, int y, int player) {
        data[x][y] = player;
    }

    /**
     * This method assumes that if there is a win, there is only one, and if there
     * are multiple winners then the behavior is undetermined.
     * 
     * @return 0 is the game is still in progress, 1 if player 1 wins, 2 is player 2
     *         wins, and 3 if the game is a tie
     */
    public int complete() {
        // horizontal win
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                int player = data[i][j];
                if (player != 0) {
                    // test if a horizontal win is possible
                    if (i <= getWidth() - 4) {
                        if (data[i + 1][j] == player && data[i + 2][j] == player
                                        && data[i + 3][j] == player) {
                            return player;
                        }
                        // test if down + right win is possible
                        if (j <= getHeight() - 4) {
                            if (data[i + 1][j + 1] == player && data[i + 2][j + 2] == player
                                            && data[i + 3][j + 3] == player) {
                                return player;
                            }
                        }
                        // test if up + right win is possible
                        if (j > 2) {
                            if (data[i + 1][j - 1] == player && data[i + 2][j - 2] == player
                                            && data[i + 3][j - 3] == player) {
                                return player;
                            }
                        }
                    }
                    // test if a vertical win is possible
                    if (j <= 2) {
                        if (data[i][j + 1] == player && data[i][j + 2] == player
                                        && data[i][j + 3] == player) {
                            return player;
                        }
                    }
                }
            }

        }
        if (boardFilled()) {
            return 3;
        }
        return 0;
    }

    private boolean boardFilled() {
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                if (data[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * This will be the method used by the player to place pieces in the board. It
     * takes into account <i> gravity </i> and whether or not the column is full
     * 
     * @param col the column to place the piece
     * @return a boolean that is true if a piece was successfully placed in the
     *         specified column and false otherwise
     */
    public boolean place(int col) {
        int player = currentPlayer;
        for (int i = getHeight() - 1; i >= 0; i--) {
            if (data[col][i] == 0) {
                data[col][i] = currentPlayer;
                currentPlayer = (currentPlayer == 1) ? 2 : 1;
                break;
            }
        }
        // if the player didn't change, that means there is no empty slots in the
        // column, so no
        // piece was placed.
        if (player == currentPlayer) {
            return false;
        }
        return true;
    }

    /**
     * @return the player whose turn it is currently
     */
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * resets the game state to an empty board
     */
    public void reset() {
        data = new int[7][6];
        currentPlayer = 1;
    }
}

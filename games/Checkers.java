


import java.util.*;

/**
 * This game of checkers does not account for wins where one of the players has
 * pieces but no possible moves.
 * 
 * @author nlao1
 *
 */
public class Checkers {
    /**
     * INVARIANT: The 2D array is rectangular. Each element in the 2D array is one
     * of the following: 'x', representing an empty space 'r', representing a red
     * piece 'b', representing a black piece 'R', representing a red king 'B',
     * representing a black king The 2D array is row-centric
     */
    private char[][] data;
    public static final int SIZE = 8;
    private int currentPlayer;

    /**
     * Creates a new Checkers game with default settings. The player to go first
     * will be red (1).
     */
    public Checkers() {
        reset();
    }

    /**
     * Used for testing purposes.
     * 
     * @param data a 2D array of {@code char}s with the same invariants as above.
     */
    public Checkers(char[][] data) {
        this.data = copyData(data);
        currentPlayer = 1;
    }

    /**
     * Returns a string for testing purposes
     * 
     * @return a string representing the board layout
     */
    public String toString() {
        String result = "";
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                result += data[i][j];
            }
            result += "\n";
        }
        return result;
    }

    /**
     * Simple getter.
     * 
     * @param i row, counted from the top
     * @param j column, counted from the left
     * @return char at the given indices in the 2D array
     */
    public char get(int row, int col) {
        return data[row][col];
    }

    /**
     * @return width of the board
     */
    public int getWidth() {
        return data[0].length;
    }

    /**
     * 
     * @return height of the board
     */
    public int getHeight() {
        return data.length;
    }

    /**
     * Returns a {@code List} of possible moves from a piece in the given slot. If
     * there is no piece in the given slot, then the method will throw a
     * {@code NoSuchElementException}. The {@code List} will contain {@code Move}
     * objects, which are defined in the class above. If the piece in that location
     * has no possible moves, then the method will return an empty {@code List}.
     * 
     * @param row row of the piece to move
     * @param col column of the piece to move
     * @return a list of possible moves, where each element is a 2D array
     *         representing a row and column
     */
    public List<CheckersMove> getPossibleMoves(int row, int col) {
        char piece = data[row][col];
        return getPossibleMoves(piece, row, col);
    }

    /**
     * Recursive implementation with extra parameters, the rest of the behavior is
     * the same. This method will be private because the player/tester does not need
     * piece type information nor the board.
     */
    private List<CheckersMove> getPossibleMoves(char pieceType, int row, int col) {
        // red pieces are on the bottom so they move "up" (subtract row)
        // vertical direction
        boolean isKing = Character.isUpperCase(pieceType);
        char team = Character.toLowerCase(pieceType);
        if (pieceType == 'x') {
            throw new NoSuchElementException("no piece found");
        }
        // arraylist because we're only accessing
        List<CheckersMove> result = new ArrayList<>();
        if (row - 1 >= 0 && (isKing || team == 'r')) {
            // test if the potential move will be in bounds horizontally (right)
            if (col + 1 < SIZE) {
                if (data[row - 1][col + 1] == 'x') {
                    result.add(new CheckersMove(row, col, row - 1, col + 1, false, null));
                }
            }
            // test if the potential move will be in bounds horizontally (left)
            if (col - 1 >= 0) {
                if (data[row - 1][col - 1] == 'x') {
                    result.add(new CheckersMove(row, col, row - 1, col - 1, false, null));
                }
            }
        }
        if (row + 1 < SIZE && (isKing || team == 'b')) {
            // test if the potential move will be in bounds horizontally (right)
            if (col + 1 < SIZE) {
                if (data[row + 1][col + 1] == 'x') {
                    result.add(new CheckersMove(row, col, row + 1, col + 1, false, null));
                }
            }
            // test if the potential move will be in bounds horizontally (left)
            if (col - 1 >= 0) {
                if (data[row + 1][col - 1] == 'x') {
                    result.add(new CheckersMove(row, col, row + 1, col - 1, false, null));
                }
            }
        }
        // test if the move will be in bounds vertically, and returns an empty list if
        // it won't be
        result.addAll(getSkips(data, team, isKing, row, col, null));
        return result;
    }

    private List<CheckersMove> getSkips(char[][] board, char team, boolean king, int row, int col,
                    CheckersMove prev) {
        // arraylist because we're only accessing
        List<CheckersMove> result = new ArrayList<>();
        int verticalDir = (team == 'b') ? 1 : -1;
        // piece 1 more square over is empty, meaning a jump is possible
        int right = col + 2;
        int left = col - 2;
        int destRow = row + 2 * verticalDir;
        if (inBounds(row + verticalDir, col + 1)) {
            char destRight = board[row + verticalDir][col + 1];
            if (destRight != team && destRight != 'x' && inBounds(destRow, right)
                            && board[destRow][right] == 'x') {
                CheckersMove jump = new CheckersMove(row, col, destRow, right, true, prev);
                result.add(jump);
                char[][] newBoard = copyData(data);
                execute(newBoard, jump);
                result.addAll(getSkips(newBoard, team, king, destRow, right, jump));
            }
        }
        if (inBounds(row + verticalDir, col - 1)) {
            char destLeft = board[row + verticalDir][col - 1];
            if (destLeft != team && destLeft != 'x' && inBounds(destRow, left)
                            && board[destRow][left] == 'x') {
                CheckersMove jump = new CheckersMove(row, col, destRow, left, true, prev);
                result.add(new CheckersMove(row, col, destRow, left, true, prev));
                char[][] newBoard = copyData(data);
                execute(newBoard, jump);
                result.addAll(getSkips(newBoard, team, king, destRow, left, jump));
            }
        }

        if (king) {
            verticalDir = -verticalDir;
            destRow = row + 2 * verticalDir;
            // copied from above, difference being it checks the other vertical direction
            if (inBounds(row + verticalDir, col + 1)) {
                char destRight = board[row + verticalDir][col + 1];
                if (destRight != team && destRight != 'x' && inBounds(destRow, right)
                                && board[destRow][right] == 'x') {
                    CheckersMove jump = new CheckersMove(row, col, destRow, right, true, prev);
                    result.add(jump);
                    char[][] newBoard = copyData(data);
                    execute(newBoard, jump);
                    result.addAll(getSkips(newBoard, team, king, destRow, right, jump));
                }
            }
            if (inBounds(row + verticalDir, col - 1)) {
                char destLeft = board[row + verticalDir][col - 1];
                if (destLeft != team && destLeft != 'x' && inBounds(destRow, left)
                                && board[destRow][left] == 'x') {
                    CheckersMove jump = new CheckersMove(row, col, destRow, left, true, prev);
                    result.add(new CheckersMove(row, col, destRow, left, true, prev));
                    char[][] newBoard = copyData(data);
                    execute(newBoard, jump);
                    result.addAll(getSkips(newBoard, team, king, destRow, left, jump));
                }
            }
        }
        return result;
    }

    /**
     * @return the player whose turn it is currently (it is assumed that red goes
     *         first). Red is 1, black is 2.
     */
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * @return true if a winner is determined.
     */
    public boolean gameOver() {
        return winner() != null;
    }

    /**
     * "Red" if red wins, "Black" if black wins, and null if there is no winner.
     * 
     * @return a string denoting the winner
     */
    public String winner() {
        if (canMove('r') && canMove('b')) {
            return null;
        } else {
            if (!canMove('r')) {
                return "Black";
            } else {
                return "Red";
            }
        }
    }

    public boolean inBounds(int row, int col) {
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE;
    }

    public void reset() {
        data = new char[SIZE][SIZE];
        currentPlayer = 1;
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                // top half of the board has black pieces, bottom half is red
                char player = (i < 3) ? 'b' : 'r';
                // even row (counting from 0 up) starts on the second column (index 1)
                // odd row starts on the first column
                // tests to see if it is not the middle two columns
                if ((i < 3 || i > 4) && (i % 2 == 0 && j % 2 == 1 || i % 2 == 1 && j % 2 == 0)) {
                    data[i][j] = player;
                } else {
                    data[i][j] = 'x';
                }
            }
        }
    }

    /**
     * This method modifies the board.
     * 
     * @param move
     */
    public void execute(CheckersMove move) {
        execute(data, move);
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
    }

    /**
     * 
     * @param move move to be executed
     */
    private void execute(char[][] board, CheckersMove move) {
        CheckersMove prev = move.getPreviousMove();
        if (prev != null) {
            execute(board, prev);
        }
        int startRow = move.getStartRow();
        int startCol = move.getStartCol();
        char piece = board[startRow][startCol];
        int endRow = move.getEndRow();
        int endCol = move.getEndCol();
        // remove it from the original location
        board[startRow][startCol] = 'x';
        // if the destination is not empty, something is wrong
        if (board[endRow][endCol] != 'x') {
            throw new IllegalArgumentException("data[row" + endRow + "][col" + endCol
                            + "] should be x, but " + board[endRow][endCol]);
        }
        prev = move.getPreviousMove();
        if (move.isSkip()) {
            board[(startRow + endRow) / 2][(startCol + endCol) / 2] = 'x';
        }
        if (endRow == 0 && Character.toLowerCase(piece) == 'r'
                        || endRow == 7 && Character.toLowerCase(piece) == 'b') {
            piece = Character.toUpperCase(piece);
        } 
        board[endRow][endCol] = piece;
    }

    public char[][] copyData(char[][] data) {
        char[][] newData = new char[data.length][data[0].length];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                newData[i][j] = data[i][j];
            }
        }
        return newData;
    }

    private boolean canMove(char team) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                if (Character.toLowerCase(data[i][j]) == team) {
                    if (getPossibleMoves(i, j).size() > 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

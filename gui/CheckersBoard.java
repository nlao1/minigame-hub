


import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.*;



@SuppressWarnings("serial")
/**
 * 
 * @author nlao1
 *
 */
public class CheckersBoard extends JPanel {

    private Checkers c;
    private JLabel status;

    public static final int SQUARE_SIZE = 60;
    public static final int BOARD_WIDTH = 8 * SQUARE_SIZE;
    public static final int BOARD_HEIGHT = 8 * SQUARE_SIZE;
    private int selectedRow, selectedColumn;
    private List<CheckersMove> possibleMoves = null;

    public CheckersBoard(JLabel status) {
        
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setFocusable(true);
        c = new Checkers();
        this.status = status;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point p = e.getPoint();
                int row = p.y / SQUARE_SIZE;
                int col = p.x / SQUARE_SIZE;
                char player = (c.getCurrentPlayer() == 1) ? 'r' : 'b';
                // if the piece selected belongs to the current player
                if (player == Character.toLowerCase(c.get(row, col))) {
                    selectedRow = row;
                    selectedColumn = col;
                    possibleMoves = c.getPossibleMoves(row, col);

                }
                if (selectedRow != -1 && selectedColumn != -1) {
                    for (CheckersMove move : possibleMoves) {
                        if (move.getEndRow() == row && move.getEndCol() == col) {
                            c.execute(move);
                            selectedRow = -1;
                            selectedColumn = -1;
                        }
                    }
                }
                updateStatus();
                repaint();
            }
        });
        // values that won't interfere with the board
        selectedRow = -1;
        selectedColumn = -1;
    }

    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus() {
        String statusText;
        if (c.getCurrentPlayer() == 1) {
            statusText = "Red's Turn";
        } else {
            statusText = "Black's Turn";
        }

        String winner = c.winner();
        if (c.gameOver()) {
            statusText = winner + " wins!";
            JOptionPane.showMessageDialog(this, statusText);
        }
        status.setText(statusText);
    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        c = new Checkers();
        status.setText("Red's Turn");
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    public void paintComponent(Graphics g) {
        // initializes board
        for (int i = 0; i < c.getHeight(); i++) {
            for (int j = 0; j < c.getWidth(); j++) {
                if ((i + j) % 2 == 0) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.BLACK);
                }
                g.fillRect(j * SQUARE_SIZE, i * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);

                char piece = c.get(i, j);
                if (piece != 'x') {
                    if (Character.toLowerCase(c.get(i, j)) == 'b') {
                        g.setColor(Color.BLACK);
                    } else if (Character.toLowerCase(c.get(i, j)) == 'r') {
                        g.setColor(Color.RED);
                    }
                    // piece
                    g.fillOval(j * SQUARE_SIZE + SQUARE_SIZE / 6, i * SQUARE_SIZE + SQUARE_SIZE / 6,
                                    2 * SQUARE_SIZE / 3, 2 * SQUARE_SIZE / 3);
                    g.setColor(Color.WHITE);
                    // outline
                    g.drawOval(j * SQUARE_SIZE + SQUARE_SIZE / 6, i * SQUARE_SIZE + SQUARE_SIZE / 6,
                                    2 * SQUARE_SIZE / 3, 2 * SQUARE_SIZE / 3);
                    // king
                    if (Character.isUpperCase(piece)) {
                        g.setColor(new Color(255, 223, 0));
                        g.fillOval(j * SQUARE_SIZE + 20, i * SQUARE_SIZE + 20, 20, 20);
                    }
                }
            }
        }
        // if a piece is selected
        if (selectedRow != -1 && selectedColumn != -1) {
            for (CheckersMove move : possibleMoves) {
                g.setColor(Color.GRAY);
                g.fillOval(move.getEndCol() * SQUARE_SIZE + SQUARE_SIZE / 3,
                                move.getEndRow() * SQUARE_SIZE + SQUARE_SIZE / 3, SQUARE_SIZE / 3,
                                SQUARE_SIZE / 3);
            }
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}

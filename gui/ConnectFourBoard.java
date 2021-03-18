


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


@SuppressWarnings("serial")
/**
 * 
 * @author nlao1
 *
 */
public class ConnectFourBoard extends JPanel {
    private ConnectFour cf;
    // space between pieces
    private static final int SPACER = 10;
    private static final int PIECE_WIDTH = 40;
    private static final int PIECE_HEIGHT = 40;
    private JLabel status;

    // 1 spacer for each of the sides and width of each piece
    public static final int BOARD_WIDTH = 8 * SPACER + 7 * PIECE_WIDTH;
    public static final int BOARD_HEIGHT = 7 * SPACER + 6 * PIECE_HEIGHT;

    public ConnectFourBoard(JLabel status) {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setFocusable(true);
        cf = new ConnectFour();
        this.status = status;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point p = e.getPoint();
                if (p.x >= 0 && p.x < BOARD_WIDTH && p.y > 0 && p.y < BOARD_HEIGHT) {
                    cf.place((int) ((p.getX() - SPACER) / (PIECE_WIDTH + SPACER)));
                }
                updateStatus();
                repaint();
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
        for (int x = 0; x < cf.getWidth(); x++) {
            for (int y = 0; y < cf.getHeight(); y++) {
                int piece = cf.get(x, y);
                if (piece == 0) {
                    g.setColor(Color.WHITE);
                } else if (piece == 1) {
                    g.setColor(new Color(200, 0, 0));
                } else {
                    g.setColor(Color.YELLOW);
                }
                g.fillOval(SPACER + x * (PIECE_WIDTH + SPACER),
                                SPACER + y * (PIECE_HEIGHT + SPACER), PIECE_WIDTH, PIECE_HEIGHT);
            }
        }

    }

    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus() {
        String statusText;
        if (cf.getCurrentPlayer() == 1) {
            statusText = "Red's Turn";
        } else {
            statusText = "Yellow's Turn";
        }

        int winner = cf.complete();
        if (winner != 0) {
            if (winner == 1) {
                statusText = "Red wins!!!";
            } else if (winner == 2) {
                statusText = "Yellow wins!!!";
            } else if (winner == 3) {
                statusText = "It's a tie.";
            }
            JOptionPane.showMessageDialog(this, statusText);
        }
        status.setText(statusText);
    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        cf.reset();
        status.setText("Red's Turn");
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}

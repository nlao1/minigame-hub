

import java.awt.BorderLayout;

import javax.swing.*;


/**
 * 
 * @author nlao1
 *
 */
public class Game implements Runnable {

    @Override
    public void run() {
        final JFrame frame = new JFrame("Select a Game");
        frame.setLayout(new BorderLayout());
        frame.setLocation(300, 300);
        frame.setResizable(true);

        final JPanel games = new JPanel();
        JButton cf = new JButton("ConnectFour");
        cf.addActionListener(e -> {
            this.connectFour();
        });
        JButton ch = new JButton("Checkers");
        ch.addActionListener(e -> {
            this.checkers();
        });

        games.add(cf);
        games.add(ch);

        final JPanel instructions = new JPanel();
        JLabel instr = new JLabel(
                        "Please select a game. The instructions for the games will be available "
                            + "when then game loads. There are plans in the future to add more "
                            + "games, but here are the ones that currently exist.");
        instructions.add(instr);
        frame.add(instructions, BorderLayout.PAGE_START);
        frame.add(games, BorderLayout.PAGE_END);

        // necessary things
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    /**
     * Main method run to start and run the game. Initializes the GUI elements
     * specified in Game and runs it. IMPORTANT: Do NOT delete! You MUST include
     * this in your final submission.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }

    public void connectFour() {
        // NOTE: the 'final' keyword denotes immutability even for local variables.

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("Connect Four");
        frame.setLocation(400, 400);
        frame.setResizable(false);
        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Setting up...");
        status_panel.add(status);

        // Game board
        final ConnectFourBoard board = new ConnectFourBoard(status);
        frame.add(board, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we define
        // it as an
        // anonymous inner class that is an instance of ActionListener with its
        // actionPerformed()
        // method overridden. When the button is pressed, actionPerformed() will be
        // called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(e -> {
            board.reset();
        });
        final JButton howto = new JButton("How to play");
        howto.addActionListener(e -> {
            JOptionPane.showMessageDialog(board, new JTextArea("Courtesy of Hasbro, \n"
                + "1) Determine which player goes first. This may be done using any random method,"
                + " such as flipping a coin. \r\n     It's common to let the youngest player "
                + "go first when playing with children."
                + "\r\n"
                + "2) The first player inserts a piece into the \"grid\". Only after the "
                + "piece has been placed can the second player make his or her move."
                + "\r\n"
                + "3) Turns continue to alternate between the first and second players until "
                + "someone gets four pieces of the same color lined up in a \r\n     row "
                + "(horizontally, vertically, or diagonally) "
                + "or the board is filled without any winning moves."));
        });
        control_panel.add(reset);
        control_panel.add(howto);
        // Put the frame on the screen

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

        // Start the game
        board.reset();
    }

    public void checkers() {
        final JFrame frame = new JFrame("Checkers");
        frame.setLocation(800, 400);
        frame.setResizable(false);
        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Setting up...");
        status_panel.add(status);

        // Game board
        final CheckersBoard board = new CheckersBoard(status);
        frame.add(board, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we define
        // it as an
        // anonymous inner class that is an instance of ActionListener with its
        // actionPerformed()
        // method overridden. When the button is pressed, actionPerformed() will be
        // called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(e -> {
            board.reset();
        });
        final JButton howto = new JButton("How to play");
        howto.addActionListener(e -> {
            JOptionPane.showMessageDialog(board, new JTextArea("Rules\r\n"
                + "Click on a square to select the checker in the square. Then click any of the "
                + "gray dots that show up to move to that selected square.\r\nA single checker can "
                + "move forward diagonally one space per turn. To capture "
                + "an opponent's checker, a player may jump over a diagonally adjacent "
                + "opponent's checker. \r\n"
                + "\r\n"
                + "The space on the other side of the opponent's checker must be "
                + "open. Multiple checkers can be captured if jumped consecutively "
                + "with the same checker.\r\n"
                + "\r\n"
                + "If a checker makes it to the other side of the board, it is kinged, "
                + "which is represented by a yellow circle in the center of the piece. \r\n"
                + "A kinged "
                + "checker can move forward or backward on the board.\r\n"
                + "\r\n"
                + "The first player to capture all the opponent's checkers wins. If a "
                + "player is unable to make a move, he/she loses the game.\r\n"
                + "\r\n"
                + "A single checker can only move and jump going forward (unless kinged). \r\n"
                + "This game of checkers is played without force skips, meaning that a player can "
                + "choose not to skip even if he/she has the option to."));
        });
        control_panel.add(reset);
        control_panel.add(howto);
        // Put the frame on the screen

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

        // Start the game
        board.reset();
    }
}

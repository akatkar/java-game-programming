package com.akatkar.game.tetris;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Tetris extends JFrame {
    private static final String HIGH_SCORE_KEY = "highScore";
    private static final Logger LOGGER = Logger.getLogger(Tetris.class.getName());

    private final ScoreBoard scoreBoard;
    private final NextShape nextShape;
    private final Preferences preferences;
    private long highScore;

    public Tetris() {
        preferences = Preferences.userNodeForPackage(Tetris.class);
        highScore = preferences.getLong(HIGH_SCORE_KEY, 0);
        scoreBoard = new ScoreBoard();
        scoreBoard.setHighScore(highScore);
        nextShape = new NextShape();
        initUI();
    }

    private void initUI() {
        BoardPanel board = new BoardPanel(this);
        JPanel content = new JPanel(new BorderLayout(GameStyle.GAP, GameStyle.GAP));
        content.setBackground(GameStyle.WINDOW_BACKGROUND);
        content.setBorder(new EmptyBorder(
                GameStyle.PADDING, GameStyle.PADDING, GameStyle.PADDING, GameStyle.PADDING));

        JPanel playArea = new JPanel(new BorderLayout(GameStyle.GAP, 0));
        playArea.setBackground(GameStyle.WINDOW_BACKGROUND);
        playArea.add(board, BorderLayout.CENTER);
        playArea.add(createSidePanel(), BorderLayout.EAST);

        content.add(playArea, BorderLayout.CENTER);
        content.add(createControls(), BorderLayout.SOUTH);

        setTitle("Tetris");
        setContentPane(content);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        board.start();
        board.requestFocusInWindow();
    }

    public ScoreBoard getStatusBar() {
        return scoreBoard;
    }

    public NextShape getNextShape() {
        return nextShape;
    }

    void updateHighScore(long score) {
        if (score <= highScore) {
            return;
        }

        highScore = score;
        scoreBoard.setHighScore(highScore);
        preferences.putLong(HIGH_SCORE_KEY, highScore);
        try {
            preferences.flush();
        } catch (BackingStoreException exception) {
            LOGGER.log(Level.SEVERE, "Unable to persist the high score", exception);
        }
    }

    private JPanel createSidePanel() {
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new javax.swing.BoxLayout(sidePanel, javax.swing.BoxLayout.Y_AXIS));
        sidePanel.setBackground(GameStyle.WINDOW_BACKGROUND);
        sidePanel.setPreferredSize(new Dimension(GameStyle.SIDEBAR_WIDTH,
                GameStyle.BOARD_HEIGHT * GameStyle.CELL_SIZE));

        JPanel nextCard = new JPanel(new BorderLayout(0, GameStyle.GAP / 2));
        nextCard.setBackground(GameStyle.CARD_BACKGROUND);
        nextCard.setBorder(createCardBorder());
        nextCard.setAlignmentX(CENTER_ALIGNMENT);
        nextCard.add(createCardTitle(), BorderLayout.NORTH);
        nextShape.setPreferredSize(new Dimension(GameStyle.CELL_SIZE * 3, GameStyle.CELL_SIZE * 3));
        nextCard.add(nextShape, BorderLayout.CENTER);
        int nextCardHeight = nextCard.getPreferredSize().height;
        nextCard.setMaximumSize(new Dimension(GameStyle.SIDEBAR_WIDTH, nextCardHeight));

        scoreBoard.setAlignmentX(CENTER_ALIGNMENT);
        int statsHeight = GameStyle.BOARD_HEIGHT * GameStyle.CELL_SIZE - nextCardHeight - GameStyle.GAP;
        scoreBoard.setPreferredSize(new Dimension(GameStyle.SIDEBAR_WIDTH, statsHeight));
        scoreBoard.setMaximumSize(new Dimension(GameStyle.SIDEBAR_WIDTH, statsHeight));

        sidePanel.add(nextCard);
        sidePanel.add(javax.swing.Box.createVerticalStrut(GameStyle.GAP));
        sidePanel.add(scoreBoard);
        return sidePanel;
    }

    private JPanel createControls() {
        JPanel controls = new JPanel(new java.awt.GridLayout(2, 1, 0, 3));
        controls.setBackground(GameStyle.PANEL_BACKGROUND);
        controls.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, GameStyle.BORDER),
                new EmptyBorder(GameStyle.PADDING / 2, 0, 0, 0)));

        JLabel movement = createControlLine("← / → Move     ↑ / ↓ Rotate     SPACE Hard drop");
        JLabel actions = createControlLine("D Soft drop     P Pause");
        controls.add(movement);
        controls.add(actions);
        return controls;
    }

    private static JLabel createControlLine(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        label.setForeground(GameStyle.MUTED_TEXT);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    private static JLabel createCardTitle() {
        JLabel label = new JLabel("NEXT");
        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
        label.setForeground(GameStyle.MUTED_TEXT);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    private static javax.swing.border.Border createCardBorder() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GameStyle.BORDER),
                new EmptyBorder(GameStyle.PADDING / 2, GameStyle.PADDING / 2,
                        GameStyle.PADDING / 2, GameStyle.PADDING / 2));
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(Tetris::new);
    }
}

package com.akatkar.game.tetris;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class BoardPanel extends JPanel {

    private static final int INITIAL_DELAY = 100;
    private static final int PERIOD_INTERVAL = 300;

    private Timer timer;
    private boolean isFallingFinished = false;
    private boolean isStarted = false;
    private boolean isPaused = false;
    private boolean gameOver = false;
    private int score = 0;
    private int linesCleared = 0;
    private Tetris parent;
    private ScoreBoard scoreBoard;
    private NextShape nextShape;
    private Shape curPiece;
    private Board board;

    public BoardPanel(Tetris parent) {
        initBoard(parent);
    }

    private void initBoard(Tetris parent) {
        this.parent = parent;
        setFocusable(true);
        setBackground(GameStyle.BOARD_BACKGROUND);
        setPreferredSize(new java.awt.Dimension(
                GameStyle.BOARD_WIDTH * GameStyle.CELL_SIZE,
                GameStyle.BOARD_HEIGHT * GameStyle.CELL_SIZE));
        scoreBoard = parent.getStatusBar();
        nextShape = parent.getNextShape();
        board = new Board(this, GameStyle.BOARD_WIDTH, GameStyle.BOARD_HEIGHT);
        addKeyListener(new TAdapter());
        timer = new Timer(PERIOD_INTERVAL, e -> {
            if (isStarted) {
                update();
                repaint();
            }
        });
        timer.setInitialDelay(INITIAL_DELAY);
    }

    public void start() {
        timer.stop();
        isFallingFinished = false;
        isPaused = false;
        gameOver = false;
        score = 0;
        linesCleared = 0;
        scoreBoard.clear();
        isStarted = true;
        board.clear();
        newPiece();
        if (isStarted) {
            timer.start();
        }
    }

    private void pause() {
        if (!isStarted) {
            return;
        }
        isPaused = !isPaused;
    }

    private void doDrawing(Graphics g) {
        g.setColor(GameStyle.BOARD_BACKGROUND);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(GameStyle.GRID_LINE);
        for (int x = 0; x <= GameStyle.BOARD_WIDTH; x++) {
            int pixelX = x * GameStyle.CELL_SIZE;
            g.drawLine(pixelX, 0, pixelX, getHeight());
        }
        for (int y = 0; y <= GameStyle.BOARD_HEIGHT; y++) {
            int pixelY = y * GameStyle.CELL_SIZE;
            g.drawLine(0, pixelY, getWidth(), pixelY);
        }
        g.setColor(GameStyle.BORDER);
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);

        board.draw(g);
        if (curPiece != null) {
            curPiece.draw(g);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
        if (gameOver) {
            drawGameOver((Graphics2D) g.create());
        }
    }

    private void dropDown() {
        curPiece.dropDown();
        if (!isStarted) {
            return;
        }
        removeFullRows();
        if (!isFallingFinished) {
            newPiece();
        }
    }

    private void moveDown() {
        if(!curPiece.moveDown()){
            if (!isStarted) {
                return;
            }
            removeFullRows();
            if (!isFallingFinished) {
                newPiece();
            }
        }
    }

    public void gameOver() {
        if (!isStarted) {
            return;
        }
        timer.stop();
        isStarted = false;
        gameOver = true;
        parent.updateHighScore(score);
        repaint();
    }

    private void drawGameOver(Graphics2D g) {
        try {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(new Color(GameStyle.WINDOW_BACKGROUND.getRed(),
                    GameStyle.WINDOW_BACKGROUND.getGreen(),
                    GameStyle.WINDOW_BACKGROUND.getBlue(), 210));
            g.fillRect(0, 0, getWidth(), getHeight());

            int panelWidth = getWidth() - GameStyle.PADDING * 2;
            int panelHeight = GameStyle.CELL_SIZE * 5;
            int panelX = (getWidth() - panelWidth) / 2;
            int panelY = (getHeight() - panelHeight) / 2;
            g.setColor(GameStyle.CARD_BACKGROUND);
            g.fillRoundRect(panelX, panelY, panelWidth, panelHeight, GameStyle.GAP, GameStyle.GAP);
            g.setColor(GameStyle.BORDER);
            g.drawRoundRect(panelX, panelY, panelWidth, panelHeight, GameStyle.GAP, GameStyle.GAP);

            g.setFont(new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.BOLD, 22));
            g.setColor(GameStyle.GAME_OVER);
            drawCenteredString(g, "GAME OVER", panelY + GameStyle.CELL_SIZE);

            g.setFont(new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.PLAIN, 14));
            g.setColor(GameStyle.PRIMARY_TEXT);
            drawCenteredString(g, "Final score: " + score, panelY + GameStyle.CELL_SIZE * 2);

            g.setFont(new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.PLAIN, 12));
            g.setColor(GameStyle.MUTED_TEXT);
            drawCenteredString(g, "Press ENTER or R to restart", panelY + GameStyle.CELL_SIZE * 3);
            drawCenteredString(g, "Press ESC to quit", panelY + GameStyle.CELL_SIZE * 4);
        } finally {
            g.dispose();
        }
    }

    private void drawCenteredString(Graphics2D g, String text, int baseline) {
        int textX = (getWidth() - g.getFontMetrics().stringWidth(text)) / 2;
        g.drawString(text, textX, baseline);
    }

    private void newPiece() {
        curPiece = new Shape(this, board, nextShape.getPiece());
        curPiece.moveDown();
    }

    private void removeFullRows() {
        int numFullRows = board.removeFullRows();
        if (numFullRows > 0) {
            linesCleared += numFullRows;
            score += numFullRows * numFullRows * GameStyle.BOARD_WIDTH;
            scoreBoard.setStats(score, linesCleared, linesCleared / 10 + 1);
            parent.updateHighScore(score);
            isFallingFinished = true;
            repaint();
        }
    }

    private void update() {
        if (isPaused || board.isClearingRows()) {
            return;
        }

        if (isFallingFinished) {
            isFallingFinished = false;
            newPiece();
        } else {
            moveDown();
        }
    }

    private class TAdapter extends KeyAdapter {

        private boolean isProcessing = false;

        @Override
        public void keyPressed(KeyEvent e) {
            if (!isStarted) {
                if (gameOver && (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_R)) {
                    start();
                } else if (gameOver && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    Window window = SwingUtilities.getWindowAncestor(BoardPanel.this);
                    if (window != null) {
                        window.dispose();
                    }
                }
                return;
            }

            if (isProcessing) {
                return;
            }

            try {
                isProcessing = true;
                int keycode = e.getKeyCode();

                if (keycode == KeyEvent.VK_P) {
                    pause();
                    return;
                }

                if (isPaused) {
                    return;
                }
                if (board.isClearingRows()) {
                    return;
                }

                switch (keycode) {
                    case KeyEvent.VK_LEFT -> {
                        if (curPiece.moveLeft())
                            repaint();
                    }
                    case KeyEvent.VK_RIGHT -> {
                        if (curPiece.moveRight())
                            repaint();
                    }
                    case KeyEvent.VK_DOWN -> {
                        curPiece.rotateRight();
                        repaint();
                    }
                    case KeyEvent.VK_UP -> {
                        curPiece.rotateLeft();
                        repaint();
                    }
                    case KeyEvent.VK_SPACE -> dropDown();
                    case KeyEvent.VK_D -> moveDown();
                    default -> {
                    }
                }
            } finally {
                isProcessing = false;
            }
        }
    }
}

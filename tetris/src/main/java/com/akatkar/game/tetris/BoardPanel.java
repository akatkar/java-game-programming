package com.akatkar.game.tetris;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BoardPanel extends JPanel {

    private final int BOARD_WIDTH = 10;
    private final int BOARD_HEIGHT = 22;
    private final int INITIAL_DELAY = 100;
    private final int PERIOD_INTERVAL = 300;

    private Timer timer;
    private boolean isFallingFinished = false;
    private boolean isStarted = false;
    private boolean isPaused = false;
    private int numRowsRemoved = 0;
    private JLabel statusbar;
    private Shape curPiece;
    private Board board;

    public BoardPanel(Tetris parent) {
        initBoard(parent);
    }

    private void initBoard(Tetris parent) {
        setFocusable(true);
        timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(), INITIAL_DELAY, PERIOD_INTERVAL);
        this.setBackground(Color.BLACK);
        statusbar = parent.getStatusBar();
        board = new Board(this, BOARD_WIDTH, BOARD_HEIGHT);
        addKeyListener(new TAdapter());
    }

    public void start() {
        isStarted = true;
        board.clear();
        newPiece();
    }

    private void pause() {
        if (!isStarted) {
            return;
        }
        isPaused = !isPaused;
        statusbar.setText(isPaused ? "paused" : String.valueOf(numRowsRemoved));
    }

    private void doDrawing(Graphics g) {
        board.draw(g);
        curPiece.draw(g);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    private void dropDown() {
        curPiece.dropDown();
        removeFullRows();
        if (!isFallingFinished) {
            newPiece();
        }
    }

    private void moveDown() {
        if(!curPiece.moveDown()){
            removeFullRows();
            if (!isFallingFinished) {
                newPiece();
            }
        }
    }

    private void newPiece() {
        curPiece = new Shape(this, board);
        if(!curPiece.moveDown()){
            timer.cancel();
            isStarted = false;
            statusbar.setText("Game over");
        }
    }

    private void removeFullRows() {
        int numFullRows = board.removeFullRows();
        if (numFullRows > 0) {
            numRowsRemoved += numFullRows;
            statusbar.setText(String.valueOf(numRowsRemoved));
            isFallingFinished = true;
            repaint();
        }
    }

    private void doGameCycle() {
        update();
        repaint();
    }

    private void update() {
        if (isPaused) {
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

        @Override
        public void keyPressed(KeyEvent e) {

            if (!isStarted) {
                return;
            }

            int keycode = e.getKeyCode();

            if (keycode == KeyEvent.VK_P) {
                pause();
                return;
            }

            if (isPaused) {
                return;
            }

            switch (keycode) {

                case KeyEvent.VK_LEFT:
                    if(curPiece.moveLeft())
                        repaint();
                    break;

                case KeyEvent.VK_RIGHT:
                    if(curPiece.moveRight())
                        repaint();
                    break;

                case KeyEvent.VK_DOWN:
                    curPiece.rotateRight();
                    repaint();
                    break;

                case KeyEvent.VK_UP:
                    curPiece.rotateLeft();
                    repaint();
                    break;

                case KeyEvent.VK_SPACE:
                    dropDown();
                    break;

                case KeyEvent.VK_D:
                    moveDown();
                    break;
            }
        }
    }

    private class ScheduleTask extends TimerTask {
        @Override
        public void run() {
            doGameCycle();
        }
    }
}

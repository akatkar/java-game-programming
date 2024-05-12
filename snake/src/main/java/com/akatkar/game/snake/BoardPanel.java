package com.akatkar.game.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

public class BoardPanel extends JPanel {

    private static final int BOARD_WIDTH = 40;
    private static final int BOARD_HEIGHT = 40;
    private static final int INITIAL_DELAY = 100;
    private static final int PERIOD_INTERVAL = 200;

    private boolean isStarted = false;
    private boolean isPaused = false;
    private JLabel statusBar;
    private Snake snake;
    private Feed feed;
    private Board board;

    public BoardPanel(SnakeGame parent) {
        initBoard(parent);
    }

    private void initBoard(SnakeGame parent) {
        setFocusable(true);
        this.setBackground(Color.BLACK);
        statusBar = parent.getStatusBar();
        board = new Board(this, BOARD_WIDTH, BOARD_HEIGHT);
        feed = new Feed(this, board);
        snake = new Snake(this, board, feed);
        new Wall(this, board, 5, 5, 30, Wall.Type.VERTICAL);
        new Wall(this, board, BOARD_WIDTH-5, 5, 30, Wall.Type.VERTICAL);
        new Wall(this, board, 5, 2, 31, Wall.Type.HORIZANTAL);
        new Wall(this, board, 5, BOARD_HEIGHT-3, 31, Wall.Type.HORIZANTAL);
        addKeyListener(new TAdapter());
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(), INITIAL_DELAY, PERIOD_INTERVAL);
    }

    public void start() {
        isStarted = true;
    }

    private void pause() {
        if (!isStarted) {
            return;
        }
        isPaused = !isPaused;
        statusBar.setText(isPaused ? "paused" : "");
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        board.draw(g);
    }

    private void update() {
        if (isPaused) {
            return;
        }
        if (snake != null) {
            snake.moveSnake();
            if (snake.isGameOver()) {
                statusBar.setText("Game Over");
                isStarted = false;
            }
        }
    }

    public void updateStatus(String s) {
        statusBar.setText(s);
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
                case KeyEvent.VK_LEFT -> snake.changeDirection(Direction.LEFT);
                case KeyEvent.VK_RIGHT -> snake.changeDirection(Direction.RIGHT);
                case KeyEvent.VK_DOWN -> snake.changeDirection(Direction.UP);
                case KeyEvent.VK_UP -> snake.changeDirection(Direction.DOWN);
                default -> {}
            }
        }
    }

    private class ScheduleTask extends TimerTask {
        @Override
        public void run() {
            update();
            repaint();
        }
    }
}

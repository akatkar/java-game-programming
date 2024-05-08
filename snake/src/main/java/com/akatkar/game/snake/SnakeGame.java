package com.akatkar.game.snake;

import javax.swing.*;
import java.awt.*;

public class SnakeGame extends JFrame {

    private JLabel statusBar;


    public SnakeGame() {

        initUI();
    }

    private void initUI() {
        statusBar = new JLabel(" 0");
        add(statusBar, BorderLayout.SOUTH);

        BoardPanel board = new BoardPanel(this);
        add(board);
        board.start();

        setTitle("Snake");
        setSize(800, 800);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public JLabel getStatusBar() {
        return statusBar;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            SnakeGame game = new SnakeGame();
            game.setVisible(true);
        });
    }
}

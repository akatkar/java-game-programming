package com.akatkar.game.tetris;

import java.awt.*;
import javax.swing.*;

public class Tetris extends JFrame {

    private ScoreBoard scoreBoard;
    private NextShape nextShape;

    public Tetris() {
        initUI();
    }

    private void initUI() {

        scoreBoard = new ScoreBoard();
        nextShape = new NextShape();
        NextShapePanel nextShapePanel = new NextShapePanel(nextShape);

        ToolPanel toolPanel = new ToolPanel(nextShapePanel, scoreBoard);
        add(toolPanel, BorderLayout.NORTH);

        BoardPanel board = new BoardPanel(this);
        add(board);
        board.start();

        setTitle("Tetris");
        setSize(400, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public ScoreBoard getStatusBar() {
        return scoreBoard;
    }

    public NextShape getNextShape() {
        return nextShape;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(Tetris::new);
    }

    private static class ToolPanel extends JPanel {
        ToolPanel(NextShapePanel nextShapePanel, ScoreBoard scoreBoard) {
            this.setLayout(new GridLayout(1, 2));
            this.add(nextShapePanel);
            this.add(scoreBoard);
        }
    }

    private static class NextShapePanel extends JPanel {

        NextShapePanel(NextShape nextShape) {
            this.setLayout(new GridLayout(1, 3));
            add(new EmptyPanel());
            add(nextShape);
            add(new EmptyPanel());
        }

        private static class EmptyPanel extends JPanel {
            public EmptyPanel() {
                setBackground(Color.BLACK);
            }
        }
    }

}

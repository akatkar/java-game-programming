package com.akatkar.game.tetris;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

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
        add(board, BorderLayout.CENTER);
        add(createStatusBar(), BorderLayout.SOUTH);
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

    private JPanel createStatusBar() {
        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBackground(Color.BLACK);
        statusBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY),
                new EmptyBorder(6, 8, 6, 8)));

        JLabel controls = new JLabel("← / → Move    ↑ / ↓ Rotate    SPACE Drop    D Soft drop    P Pause");
        controls.setForeground(Color.LIGHT_GRAY);
        controls.setHorizontalAlignment(SwingConstants.CENTER);
        statusBar.add(controls, BorderLayout.CENTER);
        return statusBar;
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

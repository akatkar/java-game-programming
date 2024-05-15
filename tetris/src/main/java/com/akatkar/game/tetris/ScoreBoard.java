package com.akatkar.game.tetris;

import java.awt.*;
import javax.swing.JPanel;
import javax.swing.JTextField;

class ScoreBoard extends JPanel {

    private JTextField display;

    ScoreBoard() {
        initComponents();
    }

    private void initComponents() {
        this.setLayout(new GridLayout(1, 1));

        display = new JTextField("0");
        display.setFont(new Font("Consolas", Font.PLAIN, 40));
        display.setFocusable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setMaximumSize(display.getPreferredSize());
        this.add(display);
    }

    void setValue(long value) {
        display.setText(Long.toString(value));
    }

    void clear() {
        display.setText("0");
    }
}

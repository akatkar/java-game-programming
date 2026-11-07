package com.akatkar.game.tetris;

import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

class ScoreBoard extends JPanel {
    private final JLabel scoreDisplay = createValueLabel();
    private final JLabel linesDisplay = createValueLabel();
    private final JLabel levelDisplay = createValueLabel();
    private final JLabel highScoreDisplay = createValueLabel();

    ScoreBoard() {
        setLayout(new GridLayout(4, 1, 0, GameStyle.GAP));
        setOpaque(false);
        add(createStatCard("SCORE", scoreDisplay, GameStyle.ACCENT));
        add(createStatCard("LINES", linesDisplay, GameStyle.PRIMARY_TEXT));
        add(createStatCard("LEVEL", levelDisplay, GameStyle.PRIMARY_TEXT));
        add(createStatCard("HIGH SCORE", highScoreDisplay, GameStyle.ACCENT));
    }

    void setStats(long score, int lines, int level) {
        scoreDisplay.setText(Long.toString(score));
        linesDisplay.setText(Integer.toString(lines));
        levelDisplay.setText(Integer.toString(level));
    }

    void setHighScore(long highScore) {
        highScoreDisplay.setText(Long.toString(highScore));
    }

    void clear() {
        setStats(0, 0, 1);
    }

    private static JPanel createStatCard(String title, JLabel value, java.awt.Color valueColor) {
        JPanel card = new JPanel(new GridLayout(2, 1, 0, 2));
        card.setBackground(GameStyle.CARD_BACKGROUND);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GameStyle.BORDER),
                new EmptyBorder(GameStyle.PADDING / 2, GameStyle.PADDING / 2,
                        GameStyle.PADDING / 2, GameStyle.PADDING / 2)));

        JLabel heading = new JLabel(title);
        heading.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
        heading.setForeground(GameStyle.MUTED_TEXT);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        value.setForeground(valueColor);
        card.add(heading);
        card.add(value);
        return card;
    }

    private static JLabel createValueLabel() {
        JLabel label = new JLabel("0");
        label.setFont(new Font(Font.MONOSPACED, Font.BOLD, 24));
        label.setForeground(GameStyle.PRIMARY_TEXT);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }
}

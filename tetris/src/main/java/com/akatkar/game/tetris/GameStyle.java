package com.akatkar.game.tetris;

import java.awt.Color;

final class GameStyle {
    static final int CELL_SIZE = 28;
    static final int BOARD_WIDTH = 10;
    static final int BOARD_HEIGHT = 20;
    static final int PADDING = 12;
    static final int GAP = 12;
    static final int SIDEBAR_WIDTH = CELL_SIZE * 5 + PADDING * 2;

    static final Color WINDOW_BACKGROUND = new Color(17, 19, 26);
    static final Color PANEL_BACKGROUND = new Color(27, 30, 39);
    static final Color CARD_BACKGROUND = new Color(34, 38, 49);
    static final Color BOARD_BACKGROUND = new Color(12, 14, 20);
    static final Color GRID_LINE = new Color(34, 38, 48);
    static final Color BORDER = new Color(57, 64, 79);
    static final Color MUTED_TEXT = new Color(157, 167, 187);
    static final Color PRIMARY_TEXT = new Color(237, 240, 248);
    static final Color ACCENT = new Color(100, 230, 190);
    static final Color GAME_OVER = new Color(239, 83, 80);

    static final Color PIECE_Z = new Color(239, 83, 80);
    static final Color PIECE_S = new Color(102, 187, 106);
    static final Color PIECE_LINE = new Color(38, 198, 218);
    static final Color PIECE_T = new Color(171, 71, 188);
    static final Color PIECE_SQUARE = new Color(255, 202, 40);
    static final Color PIECE_L = new Color(255, 167, 38);
    static final Color PIECE_MIRRORED_L = new Color(66, 165, 245);

    private GameStyle() {
    }
}

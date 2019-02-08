package com.akatkar.game.tetris;

import java.awt.Color;
import java.util.Random;

enum Piece {
    Z(Color.BLUE, new int[][]{{0, -1}, {0, 0}, {-1, 0}, {-1, 1}}),
    S(Color.MAGENTA, new int[][]{{0, -1}, {0, 0}, {1, 0}, {1, 1}}),
    LINE(Color.YELLOW, new int[][]{{0, -1}, {0, 0}, {0, 1}, {0, 2}}),
    T(Color.RED, new int[][]{{-1, 0}, {0, 0}, {1, 0}, {0, 1}}),
    SQUARE(Color.ORANGE, new int[][]{{0, 0}, {1, 0}, {0, 1}, {1, 1}}),
    L(Color.PINK, new int[][]{{-1, -1}, {0, -1}, {0, 0}, {0, 1}}),
    MIRRORED_L(Color.GREEN, new int[][]{{1, -1}, {0, -1}, {0, 0}, {0, 1}});

    private static final Piece[] values = Piece.values();
    private static final Random random = new Random();

    private final Color color;
    private final int[][] coordsTable;

    private Piece(Color color, int[][] coords) {
        this.color = color;
        this.coordsTable = coords;
    }

    public Color getColor() {
        return color;
    }

    public static Piece getRandom() {
        return values[random.nextInt(values().length)];
    }

    public int getCoordinate(int i, int axis){
        return coordsTable[i][axis];
    }
}

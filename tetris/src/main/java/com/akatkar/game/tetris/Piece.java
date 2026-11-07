package com.akatkar.game.tetris;

import java.awt.Color;
import java.util.Arrays;
import java.util.Random;

enum Piece {
    Z(GameStyle.PIECE_Z, new int[][]{{0, -1}, {0, 0}, {-1, 0}, {-1, 1}}),
    S(GameStyle.PIECE_S, new int[][]{{0, -1}, {0, 0}, {1, 0}, {1, 1}}),
    LINE(GameStyle.PIECE_LINE, new int[][]{{0, -1}, {0, 0}, {0, 1}, {0, 2}}),
    T(GameStyle.PIECE_T, new int[][]{{-1, 0}, {0, 0}, {1, 0}, {0, 1}}),
    SQUARE(GameStyle.PIECE_SQUARE, new int[][]{{0, 0}, {1, 0}, {0, 1}, {1, 1}}),
    L(GameStyle.PIECE_L, new int[][]{{-1, -1}, {0, -1}, {0, 0}, {0, 1}}),
    MIRRORED_L(GameStyle.PIECE_MIRRORED_L, new int[][]{{1, -1}, {0, -1}, {0, 0}, {0, 1}});

    private static final Piece[] values = Piece.values();
    private static final Random random = new Random();

    private final Color color;
    private final int[][] coordsTable;

    private final int width;
    private final int height;

    Piece(Color color, int[][] coords) {
        this.color = color;
        this.coordsTable = coords;
        this.width = getMaxX() - getMinX() + 1;
        this.height = getMaxY() - getMinY() + 1;
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMaxX() {
        return Arrays.stream(coordsTable)
                .mapToInt(x-> x[0])
                .max()
                .orElse(0);
    }

    public int getMaxY() {
        return Arrays.stream(coordsTable)
                .mapToInt(x -> x[1])
                .max()
                .orElse(0);
    }

    public int getMinX() {
        return Arrays.stream(coordsTable)
                .mapToInt(x -> x[0])
                .min()
                .orElse(0);
    }

    public int getMinY() {
        return Arrays.stream(coordsTable)
                .mapToInt(x -> x[1])
                .min()
                .orElse(0);
    }
}

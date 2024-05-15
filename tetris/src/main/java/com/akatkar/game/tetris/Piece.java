package com.akatkar.game.tetris;

import java.awt.Color;
import java.util.Arrays;
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

    private final int width;
    private final int height;
//    private final int maxY;
//    private final int minX;
//    private final int minY;

    private Piece(Color color, int[][] coords) {
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

    public static void main(String[] args) {
//        Piece piece = Piece.L;
//        for (int i = 0; i < piece.coordsTable.length ; i++) {
//            System.out.println(piece.getCoordinate(i, 0) +", "+ piece.getCoordinate(i, 1));
//        }
//
//        System.out.println(piece.getMinX() +", "+ piece.getMaxY());

        for (Piece p: values()) {
            int x = (6 - p.getWidth())/2;
            int y = (6 - p.getHeight())/2;
            System.out.println(p.name() + ":"+ p.width +", "+ p.height);
            System.out.println("x :"+ x);
            System.out.println("y :"+ y);
            System.out.println("=========");

        }
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

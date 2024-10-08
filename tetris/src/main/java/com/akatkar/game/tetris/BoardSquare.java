package com.akatkar.game.tetris;

import javax.swing.*;

public class BoardSquare extends Square {
    private final int x;
    private final int y;

    public BoardSquare(JPanel parent, Board board, int x, int y) {
        super(parent, board);
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }
}

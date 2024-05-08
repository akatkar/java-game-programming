package com.akatkar.game.snake;

import java.awt.*;

public class Wall {

    enum Type {VERTICAL, HORIZANTAL}

    public Wall(BoardPanel parent, Board board, int x, int y, int length, Type type) {
        if (type == Type.VERTICAL) {
            for (int i = 0; i < length; i++) {
                new WallSquare(parent, board, Color.GRAY, x, y + i);
            }
        } else {
            for (int i = 0; i < length; i++) {
                new WallSquare(parent, board, Color.GRAY, x + i, y);
            }
        }
    }

}

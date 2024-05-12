package com.akatkar.game.snake;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class WallSquare extends Square {
    private final int x;
    private final int y;

    public WallSquare(BoardPanel parent, Board board, Color color, int x, int y) {
        super(parent, board, color);
        this.x = x;
        this.y = y;
        board.setSquare(this);
    }
}

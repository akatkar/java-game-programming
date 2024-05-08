package com.akatkar.game.snake;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class SnakeSquare extends Square {

    private final int x;
    private final int y;

    public SnakeSquare(BoardPanel parent, Board board, Color color, int x, int y) {
        super(parent, board, color);
        this.x = x;
        this.y = y;
        board.setSquare(this);
    }

    public SnakeSquare growTo(int x, int y) {
        return new SnakeSquare(this.getParent(), this.getBoard(), this.getColor(), x, y);
    }

}

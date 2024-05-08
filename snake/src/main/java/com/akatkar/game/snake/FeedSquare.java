package com.akatkar.game.snake;

import lombok.Getter;

import java.awt.*;

@Getter
public class FeedSquare extends Square {
    private final int x;
    private final int y;

    public FeedSquare(BoardPanel parent, Board board, Color color, int x, int y) {
        super(parent, board, color);
        this.x = x;
        this.y = y;
        board.setSquare(this);
    }
}

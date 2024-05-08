package com.akatkar.game.snake;

import lombok.Getter;

@Getter
public class BoardSquare extends Square {
    private final int x;
    private final int y;

    public BoardSquare(BoardPanel parent, Board board, int x, int y) {
        super(parent, board);
        this.x = x;
        this.y = y;
    }
}

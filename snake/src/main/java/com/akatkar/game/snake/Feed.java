package com.akatkar.game.snake;

import java.awt.*;
import java.util.Random;

public class Feed {
    private static final Random random = new Random();
    private final BoardPanel parent;
    private final Board board;

    public Feed(BoardPanel parent, Board board) {
        this.parent = parent;
        this.board = board;
        this.createFeed();
    }
    public void createFeed() {
        for (int i = 0; i < 10; i++) {
            int x = random.nextInt(board.getWidth());
            int y = random.nextInt(board.getHeight());
            if (board.isValid(x, y)) {
                new FeedSquare(parent, board, Color.CYAN, x, y);
                return;
            }
        }
    }

    private Color getRandomColor() {
        return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }
}

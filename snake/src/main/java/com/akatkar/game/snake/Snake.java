package com.akatkar.game.snake;

import java.awt.*;
import java.util.Deque;
import java.util.LinkedList;

public class Snake {

    private static final int INITIAL_LEN = 10;

    private final BoardPanel parent;
    private final Board board;
    private final Deque<SnakeSquare> snakeBody = new LinkedList<>();
    private Direction currentDirection;
    private boolean gameOver;

    private final FeedEventListener feedEventListener;

    public Snake(BoardPanel parent, Board board, FeedEventListener feedEventListener) {
        this(parent, board, INITIAL_LEN, feedEventListener);
    }

    public Snake(BoardPanel parent, Board board, int length, FeedEventListener feedEventListener) {
        this.parent = parent;
        this.board = board;
        this.currentDirection = Direction.RIGHT;
        this.feedEventListener = feedEventListener;
        for (int i = 0; i < length; i++) {
            SnakeSquare body = new SnakeSquare(parent, board, Color.YELLOW,4+i, 4);
            snakeBody.add(body);
        }
    }

    public void moveSnake() {
        if (isGameOver()) {
            return;
        }
        SnakeSquare head = snakeBody.getLast();
        int nextX = head.getX() + currentDirection.getX();
        int nextY = head.getY() + currentDirection.getY();
        parent.updateStatus("Snake: "+ snakeBody.size());

        if (board.isHit(nextX, nextY)) {
            gameOver = true;
        } else if (board.hasFeed(nextX, nextY)) {
            SnakeSquare newHead = head.growTo(nextX, nextY);
            snakeBody.add(newHead);
            feedEventListener.onEatenEvent();
        } else {
            SnakeSquare square = snakeBody.removeFirst();
            square.moveTo(nextX, nextY);
            snakeBody.add(square);
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void changeDirection(Direction direction) {
        if (currentDirection.isNotOpposite(direction)) {
            currentDirection = direction;
        }
    }

}

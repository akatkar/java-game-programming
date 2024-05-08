package com.akatkar.game.snake;

import java.awt.*;
import java.util.Deque;
import java.util.LinkedList;

public class Snake {

    private static final int INITIAL_LEN = 4;

    private final BoardPanel parent;
    private final Board board;
    private final Deque<SnakeSquare> snakeBody = new LinkedList<>();
    private Direction currentDirection;
    private boolean gameOver;

    public Snake(BoardPanel parent, Board board) {
        this(parent, board, INITIAL_LEN);
    }

    public Snake(BoardPanel parent, Board board, int length) {
        this.parent = parent;
        this.board = board;
        this.currentDirection = Direction.RIGHT;
        for (int i = 0; i < length; i++) {
            SnakeSquare body = new SnakeSquare(parent, board, Color.YELLOW,4+i, 4);
            snakeBody.add(body);
        }
    }

    public boolean moveSnake() {
        if (isGameOver()) {
            return false;
        }
        SnakeSquare head = snakeBody.getLast();
        int nextX = head.getX() + currentDirection.getX();
        int nextY = head.getY() + currentDirection.getY();
        parent.updateStatus("Snake: "+ snakeBody.size());

        if (board.isHit(nextX, nextY)) {
            gameOver = true;
            return false;
        } else if (board.hasFeed(nextX, nextY)) {
            SnakeSquare newHead = head.growTo(nextX, nextY);
            snakeBody.add(newHead);
            return true;
        } else {
            SnakeSquare newHead = head.growTo(nextX, nextY);
            snakeBody.add(newHead);
            Square square = snakeBody.removeFirst();
            square.clear();
            return false;
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

package com.akatkar.game.snake;

import java.awt.*;
import java.util.Random;

class Board {
    private final Square[][] boardGrid;
    private final Square[][] emptyGrid;
    private final BoardPanel parent;
    public Board(BoardPanel parent, int width, int height) {
        this.parent = parent;
        this.boardGrid = new Square[height][width];
        this.emptyGrid = new Square[height][width];
        initBoard();
    }

    private void initBoard() {
        for (int row = 0; row < getHeight(); row++) {
            for (int col = 0; col < getWidth(); col++) {
                boardGrid[row][col] = new BoardSquare(parent, this, col, row);
                emptyGrid[row][col] = boardGrid[row][col];
            }
        }
    }

    public void clear(int x, int y) {
        boardGrid[y][x] = emptyGrid[y][x];
    }

    public void clear() {
        for (Square[] row : boardGrid) {
            for (Square square : row) {
                square.clear();
            }
        }
    }

    public void draw(Graphics g) {
        for (Square[] row : boardGrid) {
            for (Square square : row) {
                square.draw(g);
            }
        }
    }

    public void setSquare(Square square) {
        boardGrid[square.getY()][square.getX()] = square;
    }

    public boolean isHit(int x, int y) {
        return isHitBorder(x, y) || isHitItself(x, y) || isHitWall(x, y);
    }
    private boolean isHitBorder(int x, int y) {
        return (x < 0 || x >= boardGrid[0].length || y < 0 || y >= boardGrid.length);
    }

    private boolean isHitWall(int x, int y) {
        return boardGrid[y][x] instanceof WallSquare;
    }

    private boolean isHitItself(int x, int y) {
        return boardGrid[y][x] instanceof SnakeSquare;
    }

    public boolean hasFeed(int x, int y) {
        return boardGrid[y][x] instanceof FeedSquare;
    }

    public boolean isValid(int x, int y) {
        if (x < 0 || x >= boardGrid[0].length || y < 0 || y >= boardGrid.length) {
            return false;
        }
        return boardGrid[y][x].isEmpty();
    }

    public int getWidth() {
        return boardGrid[0].length;
    }

    public int getHeight() {
        return boardGrid.length;
    }

}

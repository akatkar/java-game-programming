package com.akatkar.game.tetris;

import java.awt.*;

class Board {
    private final Square[][] boardGrid;
    private final BoardPanel parent;

    public Board(BoardPanel parent, int width, int height) {
        this.parent = parent;
        this.boardGrid = new Square[height][width];
        initBoard();
    }

    private void initBoard() {
        for (int row = 0; row < getHeight(); row++) {
            for (int col = 0; col < getWidth(); col++) {
                boardGrid[row][col] = new BoardSquare(parent, this, col, row);
            }
        }
    }

    public void clear() {
        for (Square[] row : boardGrid) {
            for (Square square : row) {
                square.clear();
            }
        }
    }

    private boolean isRowFull(int row) {
        for (Square square : boardGrid[row]) {
            if (square.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private void removeRow(int row) {
        for (int y = row; y > 0; y--) {
            for (int x = 0; x < getWidth(); x++) {
                boardGrid[y][x].setColor(boardGrid[y - 1][x].getColor());
            }
        }
    }

    int removeFullRows() {
        int rowCount = 0;
        for (int row = 0; row < boardGrid.length; row++) {
            if (isRowFull(row)) {
                rowCount++;
                removeRow(row);
            }
        }
        return rowCount;
    }

    public void draw(Graphics g) {
        for (Square[] row : boardGrid) {
            for (Square square : row) {
                square.draw(g);
            }
        }
    }

    public void setSquare(Square square) {
        boardGrid[square.getY()][square.getX()].setColor(square.getColor());
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

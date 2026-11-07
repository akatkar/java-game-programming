package com.akatkar.game.tetris;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

class Board {
    private final Square[][] boardGrid;
    private final JPanel parent;
    private Timer rowClearTimer;
    private boolean[] clearingRows;
    private boolean hideClearingRows;
    private int clearAnimationFrames;

    public Board(JPanel parent, int width, int height) {
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
        if (rowClearTimer != null) {
            rowClearTimer.stop();
            rowClearTimer = null;
        }
        clearingRows = null;
        hideClearingRows = false;
        clearAnimationFrames = 0;
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

    int removeFullRows() {
        if (isClearingRows()) {
            return 0;
        }

        clearingRows = new boolean[getHeight()];
        int clearedRows = 0;
        for (int row = 0; row < getHeight(); row++) {
            if (isRowFull(row)) {
                clearingRows[row] = true;
                clearedRows++;
            }
        }

        if (clearedRows > 0) {
            hideClearingRows = false;
            clearAnimationFrames = 0;
            rowClearTimer = new Timer(100, event -> animateRowClear());
            rowClearTimer.setInitialDelay(10);
            rowClearTimer.start();
            repaint();
        } else {
            clearingRows = null;
        }
        return clearedRows;
    }

    boolean isClearingRows() {
        return rowClearTimer != null && rowClearTimer.isRunning();
    }

    private void animateRowClear() {
        hideClearingRows = !hideClearingRows;
        clearAnimationFrames++;
        if (clearAnimationFrames >= 6) {
            compactRows();
            rowClearTimer.stop();
            rowClearTimer = null;
            clearingRows = null;
            hideClearingRows = false;
        }
        repaint();
    }

    private void compactRows() {
        int writeRow = getHeight() - 1;
        for (int readRow = getHeight() - 1; readRow >= 0; readRow--) {
            if (clearingRows[readRow]) {
                continue;
            }

            if (writeRow != readRow) {
                for (int x = 0; x < getWidth(); x++) {
                    Square source = boardGrid[readRow][x];
                    Square destination = boardGrid[writeRow][x];
                    if (source.isEmpty()) {
                        destination.clear();
                    } else {
                        destination.setColor(source.getColor());
                    }
                }
            }
            writeRow--;
        }

        for (int row = writeRow; row >= 0; row--) {
            Arrays.stream(boardGrid[row]).forEach(Square::clear);
        }
    }

    public void draw(Graphics g) {
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                Square square = boardGrid[y][x];
                boolean rowIsBlinking = clearingRows != null && clearingRows[y];
                if (!square.isEmpty() && !(rowIsBlinking && hideClearingRows)) {
                    square.draw(g);
                }
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

    public void repaint() {
        parent.repaint();
    }
}

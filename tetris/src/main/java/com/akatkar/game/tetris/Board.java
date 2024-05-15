package com.akatkar.game.tetris;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Board {
    private final Square[][] boardGrid;
    private final JPanel parent;

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
        for (Square[] row : boardGrid) {
            for (Square square : row) {
                square.clear();
            }
        }
    }

    private boolean isRowFull(int row) {
        return Stream.of(boardGrid[row])
                .noneMatch(Square::isEmpty);
    }

    private List<Integer> findFullRows() {
        return IntStream.range(0, boardGrid.length)
                .filter(this::isRowFull)
                .boxed()
                .toList();
    }

    int removeFullRows() {
        List<Integer> fullRows = findFullRows();
        if (!fullRows.isEmpty()) {
            new RowRemover(fullRows);
        }
        return fullRows.size();
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

    public void repaint() {
        parent.repaint();
    }
    private class RowRemover extends TimerTask {
        Timer timer;
        List<Color[]> savedSquares;
        List<Integer> fullRows;

        boolean hideFlag = false;
        int count = 0;
        public RowRemover(List<Integer> fullRows) {
            this.fullRows = fullRows;
            this.savedSquares = fullRows.stream()
                    .map(this::copySquares)
                    .toList();
            timer = new Timer();
            timer.scheduleAtFixedRate(this, 10, 100);
        }

        private Color[] copySquares(int row) {
            Color[] squares = new Color[getWidth()];
            for (int x = 0; x < squares.length; x++) {
                squares[x] = boardGrid[row][x].getColor();
            }
            return squares;
        }

        private void hideRows() {
            for (int row : fullRows) {
                for (int x = 0; x < getWidth(); x++) {
                    boardGrid[row][x].setColor(Color.BLACK);
                }
            }
        }

        private void drawRows() {
            for (int i = 0; i < fullRows.size(); i++) {
                int row = fullRows.get(i);
                Color[] temp = savedSquares.get(i);
                for (int x = 0; x < getWidth(); x++) {
                    boardGrid[row][x].setColor(temp[x]);
                }
            }
        }

        private void removeRows() {
            for (int row : fullRows) {
                for (int y = row; y > 0; y--) {
                    for (int x = 0; x < getWidth(); x++) {
                        boardGrid[y][x].setColor(boardGrid[y - 1][x].getColor());
                    }
                }
            }
        }

        @Override
        public void run() {
            if (hideFlag) {
                hideRows();
            } else {
                drawRows();
            }
            hideFlag = !hideFlag;
            repaint();
            count++;
            if (count > 4) {
                removeRows();
                timer.cancel();
            }
        }
    }
}

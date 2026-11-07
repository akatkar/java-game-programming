package com.akatkar.game.tetris;

import javax.swing.*;
import java.awt.*;

class NextShape extends JPanel {

    private final Board board;
    private Piece piece;
    private PieceSquare[] squares;

    NextShape() {
        setBackground(GameStyle.CARD_BACKGROUND);
        setPreferredSize(new Dimension(GameStyle.CELL_SIZE * 5, GameStyle.CELL_SIZE * 5));
        board = new Board(this, 5, 5);
        newPiece();
    }

    private void doDrawing(Graphics g) {
        int cellSize = Math.max(1, Math.min(getWidth(), getHeight()) / 5);
        int pieceX = (getWidth() - piece.getWidth() * cellSize) / 2;
        int pieceY = (getHeight() - piece.getHeight() * cellSize) / 2;

        for (PieceSquare square: squares) {
            int squareX = pieceX + (square.getX() - piece.getMinX()) * cellSize;
            int squareY = pieceY + (square.getY() - piece.getMinY()) * cellSize;
            Square.drawCell(g, squareX, squareY, cellSize, square.getColor());
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    private void newPiece() {
        this.piece = Piece.getRandom();
        this.squares = PieceSquare.getSquares(this, board, piece);
    }

    public Piece getPiece() {
        Piece piece = this.piece;
        newPiece();
        repaint();
        return piece;
    }

}

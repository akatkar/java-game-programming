package com.akatkar.game.tetris;

import javax.swing.*;
import java.awt.*;

class NextShape extends JPanel {

    private final Board board;
    private Piece piece;
    private PieceSquare[] squares;
    private int x;
    private int y;

    NextShape() {
        this.setBackground(Color.BLACK);
        board = new Board(this, 5, 5);
        newPiece();
    }

    private void doDrawing(Graphics g) {
        board.draw(g);
        for (PieceSquare square: squares) {
            square.draw(g, x, y);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    public void draw() {
        repaint();
    }

    private void newPiece() {
        this.piece = Piece.getRandom();
        this.squares = PieceSquare.getSquares(this, board, piece);
        this.x = (5 - piece.getWidth())/2 - piece.getMinX();
        this.y = (5 - piece.getHeight())/2 - piece.getMinY();
    }

    public Piece getPiece() {
        Piece piece = this.piece;
        newPiece();
        repaint();
        return piece;
    }

}

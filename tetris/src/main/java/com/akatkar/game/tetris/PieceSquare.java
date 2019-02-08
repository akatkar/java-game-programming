package com.akatkar.game.tetris;

import java.awt.*;

public class PieceSquare extends Square {

    private int x;
    private int y;

    public static PieceSquare[] getSquares(BoardPanel parent, Board board, Piece piece){
        PieceSquare[] squares = new PieceSquare[4];
        for (int i = 0; i < squares.length ; i++) {
            squares[i] = new PieceSquare(parent, board, piece, i);
        }
        return squares;
    }

    private PieceSquare(BoardPanel parent, Board board, Piece piece, int count) {
        super(parent, board);
        this.x = piece.getCoordinate(count,0);
        this.y = piece.getCoordinate(count,1);
        this.setColor(piece.getColor());
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void draw(Graphics g, int a, int b){
        super.draw(g, a + x, b + y);
    }

    public void normalizeLocation(int x, int y){
        this.x += x;
        this.y += y;
    }

    public void rotateLeft(){
        int tempY = -x;
        x = y;
        y = tempY;
    }

    public void rotateRight(){
        int tempY = x;
        x = -y;
        y = tempY;
    }
}

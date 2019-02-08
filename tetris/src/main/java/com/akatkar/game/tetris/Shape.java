package com.akatkar.game.tetris;

import java.awt.*;
import java.util.Arrays;

class Shape {

    private final Board board;
    private final Piece piece;
    private final PieceSquare[] squares;
    private int curX;
    private int curY;

    Shape(BoardPanel parent, Board board) {
        this.board  = board;
        this.piece = Piece.getRandom();
        this.squares = PieceSquare.getSquares(parent, board, piece);
        curX = (board.getWidth() / 2);
        curY = -1 - (maxY() - minY()); // start with bottom of shape
    }

    private int maxX() {
        return Arrays.stream(squares)
                .mapToInt(s -> s.getX())
                .max()
                .getAsInt();
    }

    private int minX() {
        return Arrays.stream(squares)
                .mapToInt(s -> s.getX())
                .min()
                .getAsInt();
    }

    private int maxY() {
        return Arrays.stream(squares)
                .mapToInt(s -> s.getY())
                .max()
                .getAsInt();
    }

    private int minY() {
        return Arrays.stream(squares)
                .mapToInt(s -> s.getY())
                .min()
                .getAsInt();
    }

    void rotateLeft() {
        if (piece != Piece.SQUARE) {
            for (PieceSquare square: squares) {
                square.rotateLeft();
            }
        }
        if(!tryMove(curX, curY)){
            rotateRight();
        }
    }

    void rotateRight() {
        if (piece != Piece.SQUARE) {
            for (PieceSquare square: squares) {
                square.rotateRight();
            }
        }
        if(!tryMove(curX, curY)){
            rotateLeft();
        }
    }

    void draw(Graphics g){
        for (PieceSquare square: squares) {
            square.draw(g, curX, curY);
        }
    }

    boolean moveDown() {
        if (!tryMove(curX, curY + 1)) {
            landed();
            return false;
        }
        return true;
    }

    boolean moveLeft() {
        if(curX + minX() - 1 >= 0){
            return tryMove(curX - 1, curY);
        }
        return false;
    }

    boolean moveRight() {
        if(curX + maxX() + 1 < board.getWidth()){
            return tryMove(curX + 1, curY);
        }
        return false;
    }

    void dropDown() {
        int newY = curY;
        while (newY > 0) {
            if (!tryMove(curX, newY + 1)) {
                break;
            }
            ++newY;
        }
        landed();
    }

    boolean tryMove(int newX, int newY){
        for(Square square : squares){
            int x = newX + square.getX();
            int y = newY + square.getY();
            if( y > 0 && !board.isValid(x, y))
                return false;
        }
        curX = newX;
        curY = newY;
        return true;
    }

    void landed(){
        for (PieceSquare square: squares) {
            square.normalizeLocation(curX, curY);
            board.setSquare(square);
        }
    }
}

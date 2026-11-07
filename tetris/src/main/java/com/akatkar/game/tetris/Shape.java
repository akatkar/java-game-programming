package com.akatkar.game.tetris;

import javax.swing.*;
import java.awt.Graphics;
import java.util.stream.Stream;

class Shape {

    private final BoardPanel parent;
    private final Board board;
    private final Piece piece;
    private final PieceSquare[] squares;
    private int curX;
    private int curY;

    Shape(JPanel parent, Board board, Piece piece) {
        this.parent = (BoardPanel) parent;
        this.board  = board;
        this.piece = piece;
        this.squares = PieceSquare.getSquares(parent, board, piece);
        curX = (board.getWidth() / 2);
        curY = -1 - minY();
    }

    private int maxX() {
        return Stream.of(squares)
                .mapToInt(PieceSquare::getX)
                .max()
                .getAsInt();
    }

    private int minX() {
        return Stream.of(squares)
                .mapToInt(PieceSquare::getX)
                .min()
                .getAsInt();
    }

    private int minY() {
        return Stream.of(squares)
                .mapToInt(PieceSquare::getY)
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
            for (PieceSquare square: squares) {
                square.rotateRight();
            }
        }
    }

    void rotateRight() {
        if (piece != Piece.SQUARE) {
            for (PieceSquare square: squares) {
                square.rotateRight();
            }
        }
        if(!tryMove(curX, curY)){
            for (PieceSquare square: squares) {
                square.rotateLeft();
            }
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
        while (true) {
            if (!tryMove(curX, curY + 1)) {
                break;
            }
        }
        landed();
    }

     boolean tryMove(int newX, int newY){
        for(Square square : squares){
            int x = newX + square.getX();
            int y = newY + square.getY();
            if (x < 0 || x >= board.getWidth() || y >= board.getHeight()) {
                return false;
            }
            if (y >= 0 && !board.isValid(x, y))
                return false;
        }
        curX = newX;
        curY = newY;
        return true;
    }

    void landed(){
        for (PieceSquare square: squares) {
            int x = curX + square.getX();
            int y = curY + square.getY();
            if (!board.isValid(x, y)) {
                parent.gameOver();
                return;
            }
        }

        for (PieceSquare square: squares) {
            square.normalizeLocation(curX, curY);
            board.setSquare(square);
        }
    }
}

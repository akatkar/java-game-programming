package com.akatkar.game.tetris;

import javax.swing.*;
import java.awt.Graphics;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;

class Shape {

    private final Board board;
    private final Piece piece;
    private final PieceSquare[] squares;
    private int curX;
    private int curY;

    Shape(JPanel parent, Board board) {
        this(parent, board, Piece.getRandom());
    }

    Shape(JPanel parent, Board board, Piece piece) {
        this.board  = board;
        this.piece = piece;
        this.squares = PieceSquare.getSquares(parent, board, piece);
        curX = (board.getWidth() / 2);
        curY = -1 - (maxY() - minY()); // start with bottom of shape
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

    private int maxY() {
        return Stream.of(squares)
                .mapToInt(PieceSquare::getY)
                .max()
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

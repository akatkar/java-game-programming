package com.akatkar.game.snake;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public abstract class Square {

    private Color color;
    private final BoardPanel parent;
    private final Board board;

    public Square(BoardPanel parent, Board board) {
        this(parent, board, parent.getBackground());
    }

    public Square(BoardPanel parent, Board board, Color color) {
        this.parent = parent;
        this.board = board;
        this.color = color;
    }

    public void clear(){
        board.clear(getX(), this.getY());
    }

    public boolean isEmpty() {
        return color.equals(parent.getBackground());
    }
    private int squareWidth() {
        return (int) parent.getSize().getWidth() / board.getWidth();
    }

    private int squareHeight() {
        return (int) parent.getSize().getHeight() / board.getHeight();
    }

    public abstract int getX();

    public abstract int getY();

    public void draw(Graphics g) {
        draw(g, getX() , getY());
    }

    public void draw(Graphics g, int x, int y) {
        Dimension size = parent.getSize();
        int boardTop = (int) size.getHeight() - board.getHeight() * squareHeight();

        g.setColor(color);
        int rx = x * squareWidth();
        int ry = boardTop + y * squareHeight();

        g.fillRect(rx + 1, ry + 1, squareWidth() - 2, squareHeight() - 2);

        g.setColor(color.brighter());
        g.drawLine(rx, ry + squareHeight() - 1, rx, ry);
        g.drawLine(rx, ry, rx + squareWidth() - 1, ry);

        g.setColor(color.darker());
        g.drawLine(rx + 1, ry + squareHeight() - 1,rx + squareWidth() - 1, ry + squareHeight() - 1);
        g.drawLine(rx + squareWidth() - 1, ry + squareHeight()- 1,rx + squareWidth() - 1, ry + 1);
    }

    @Override
    public String toString() {
        return "{" + getX() + ',' + getY() + '}';
    }

}

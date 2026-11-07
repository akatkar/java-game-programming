package com.akatkar.game.tetris;


import javax.swing.*;
import java.awt.*;

public abstract class Square {

    private Color color;
    private final JPanel parent;

    public Square(JPanel parent, Board board) {
        this.parent = parent;
        this.color = parent.getBackground();
    }

    public void clear(){
        this.color = parent.getBackground();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isEmpty() {
        return color.equals(parent.getBackground());
    }
    public abstract int getX();

    public abstract int getY();

    public void draw(Graphics g) {
        draw(g, getX() , getY());
    }

    public void draw(Graphics g, int x, int y) {
        drawCell(g, x * GameStyle.CELL_SIZE, y * GameStyle.CELL_SIZE, GameStyle.CELL_SIZE, color);
    }

    static void drawCell(Graphics g, int x, int y, int cellSize, Color color) {
        g.setColor(color);
        g.fillRect(x + 1, y + 1, cellSize - 2, cellSize - 2);

        g.setColor(color.brighter());
        g.drawLine(x, y + cellSize - 1, x, y);
        g.drawLine(x, y, x + cellSize - 1, y);

        g.setColor(color.darker());
        g.drawLine(x + 1, y + cellSize - 1, x + cellSize - 1, y + cellSize - 1);
        g.drawLine(x + cellSize - 1, y + cellSize - 1, x + cellSize - 1, y + 1);
    }
}

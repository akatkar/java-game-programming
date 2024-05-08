//package com.akatkar.game.snake;
//
//import lombok.Getter;
//import lombok.Setter;
//
//@Getter
//@Setter
//public class Point {
//    private int x;
//    private int y;
//
//    public Point(int x, int y) {
//        this.x = x;
//        this.y = y;
//    }
//
//    public static Point of(int x, int y) {
//        return new Point(x, y);
//    }
//
//    public Point next(Direction direction) {
//        return add(direction.getDirectedPoint());
//    }
//
//    public Point add(Point other) {
//        return Point.of(x + other.x, y + other.y);
//    }
//
//    @Override
//    public String toString() {
//        return "{" + x + ',' + y + '}';
//    }
//
//}

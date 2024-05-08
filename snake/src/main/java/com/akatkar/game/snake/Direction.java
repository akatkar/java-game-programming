package com.akatkar.game.snake;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public enum Direction {

    RIGHT   (1, 0),
    LEFT    (-1, 0),
    UP      (0, 1),
    DOWN    (0, -1);


    private final int x;
    private final int y;

    private static final Map<Direction, Direction> OPPOSITE_MAP =
            Map.of(RIGHT, LEFT, LEFT, RIGHT, UP, DOWN, DOWN, UP);

    public boolean isNotOpposite(Direction other) {
        return OPPOSITE_MAP.get(this) != other;
    }

}

package com.akatkar.game.tetris;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javax.swing.JPanel;
import javax.swing.Timer;
import org.junit.jupiter.api.Test;

class BoardTest {
    private static final Color BLOCK_COLOR = new Color(40, 180, 220);

    @Test
    void doesNotStartAnAnimationWhenThereAreNoFullRows() {
        JPanel parent = new JPanel();
        parent.setBackground(GameStyle.BOARD_BACKGROUND);
        Board board = new Board(parent, 4, 6);

        assertEquals(0, board.removeFullRows());
        assertFalse(board.isClearingRows());
    }

    @Test
    void animatesAndRemovesFullRowsThenDropsRowsAboveThem() throws Exception {
        JPanel parent = new JPanel();
        parent.setBackground(GameStyle.BOARD_BACKGROUND);
        Board board = new Board(parent, 4, 6);

        setBlock(board, parent, 0, 2);
        fillRow(board, parent, 4);
        fillRow(board, parent, 5);

        assertEquals(2, board.removeFullRows());
        assertTrue(board.isClearingRows());
        assertEquals(0, board.removeFullRows(), "A second clear must not overlap the active animation");

        CountDownLatch animationFinished = new CountDownLatch(1);
        Timer[] completionPoller = new Timer[1];
        EventQueue.invokeAndWait(() -> {
            completionPoller[0] = new Timer(20, event -> {
                if (!board.isClearingRows()) {
                    completionPoller[0].stop();
                    animationFinished.countDown();
                }
            });
            completionPoller[0].start();
        });

        boolean finished = animationFinished.await(2, TimeUnit.SECONDS);
        EventQueue.invokeAndWait(() -> completionPoller[0].stop());
        assertTrue(finished, "The row-clear animation should finish promptly");
        EventQueue.invokeAndWait(() -> {
            assertFalse(board.isValid(0, 4), "The block above the cleared rows should drop to row 4");
            assertTrue(board.isValid(0, 2), "The block's original cell should be empty");
            assertTrue(board.isValid(1, 5), "Cleared rows should be empty after compaction");
        });
    }

    private static void fillRow(Board board, JPanel parent, int row) {
        for (int x = 0; x < board.getWidth(); x++) {
            setBlock(board, parent, x, row);
        }
    }

    private static void setBlock(Board board, JPanel parent, int x, int y) {
        BoardSquare square = new BoardSquare(parent, board, x, y);
        square.setColor(BLOCK_COLOR);
        board.setSquare(square);
    }
}

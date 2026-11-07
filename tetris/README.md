# Tetris

A desktop Tetris game built with Java 17 and Swing. It features a 10 × 20 play
field, a next-piece preview, score/line/level tracking, animated line clears,
and a high score saved between launches.

## Requirements

- Java 17 or later
- Maven

## Build and run

From the project directory:

```sh
mvn test
mvn package
java -cp target/classes com.akatkar.game.tetris.Tetris
```

`mvn test` runs the automated unit tests for board row clearing and compaction.

## Controls

| Key | Action |
| --- | --- |
| Left / Right arrows | Move the piece |
| Up / Down arrows | Rotate the piece |
| Space | Hard drop |
| D | Soft drop |
| P | Pause or resume |
| Enter / R | Restart after game over |
| Esc | Quit after game over |

## Scoring and high score

Clearing rows awards points based on the number cleared at once. The current
score, total cleared lines, and level are shown alongside the board. The high
score is updated when exceeded and stored using the Java Preferences API under
the current user's preferences.

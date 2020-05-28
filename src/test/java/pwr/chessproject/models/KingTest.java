package pwr.chessproject.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pwr.chessproject.game.Board;
import pwr.chessproject.models.functionalities.Movable;

import static org.junit.jupiter.api.Assertions.*;
import static pwr.chessproject.models.Figure.Player.Bottom;
import static pwr.chessproject.models.Figure.Player.Top;

@ExtendWith(FigureMoveExtension.class)
class KingTest {

    private final Board board;

    public KingTest(Board board) {
        this.board = board;
    }

    @Test
    void atLeftTopCorner () {
        int position = 0;
        board.grid[position] = new King(Top);
        Movable king = board.grid[position];
        assertAll(
                () -> assertFalse(king.canMove(position, -board.getColumns(), board)),
                () -> assertFalse(king.canMove(position, -board.getColumns()+1, board)),
                () -> assertTrue(king.canMove(position, 1, board)),
                () -> assertTrue(king.canMove(position, board.getColumns()+1, board)),
                () -> assertTrue(king.canMove(position, board.getColumns(), board)),
                () -> assertFalse(king.canMove(position, board.getColumns()-1, board)),
                () -> assertFalse(king.canMove(position, -1, board)),
                () -> assertFalse(king.canMove(position, -board.getColumns()-1, board))
        );
    }

    @Test
    void atRightTopCorner () {
        int position = board.getColumns()-1;
        board.grid[position] = new King(Top);
        Movable king = board.grid[position];
        Assertions.assertAll(
                () -> assertFalse(king.canMove(position, position-board.getColumns(), board)),
                () -> assertFalse(king.canMove(position, position-board.getColumns()+1, board)),
                () -> assertFalse(king.canMove(position, position+1, board)),
                () -> assertFalse(king.canMove(position, position+board.getColumns()+1, board)),
                () -> assertTrue(king.canMove(position, position+board.getColumns(), board)),
                () -> assertTrue(king.canMove(position, position+board.getColumns()-1, board)),
                () -> assertTrue(king.canMove(position, position-1, board)),
                () -> assertFalse(king.canMove(position, position-board.getColumns()-1, board))
        );
    }

    @Test
    void atRightBotCorner () {
        int position = board.getArea()-1;
        board.grid[position] = new King(Top);
        Movable king = board.grid[position];
        Assertions.assertAll(
                () -> assertTrue(king.canMove(position, position-board.getColumns(), board)),
                () -> assertFalse(king.canMove(position, position-board.getColumns()+1, board)),
                () -> assertFalse(king.canMove(position, position+1, board)),
                () -> assertFalse(king.canMove(position, position+board.getColumns()+1, board)),
                () -> assertFalse(king.canMove(position, position+board.getColumns(), board)),
                () -> assertFalse(king.canMove(position, position+board.getColumns()-1, board)),
                () -> assertTrue(king.canMove(position, position-1, board)),
                () -> assertTrue(king.canMove(position, position-board.getColumns()-1, board))
        );
    }

    @Test
    void atLeftBotCorner () {
        int position = board.getArea()-board.getColumns();
        board.grid[position] = new King(Top);
        Movable king = board.grid[position];
        Assertions.assertAll(
                () -> assertTrue(king.canMove(position, position-board.getColumns(), board)),
                () -> assertTrue(king.canMove(position, position-board.getColumns()+1, board)),
                () -> assertTrue(king.canMove(position, position+1, board)),
                () -> assertFalse(king.canMove(position, position+board.getColumns()+1, board)),
                () -> assertFalse(king.canMove(position, position+board.getColumns(), board)),
                () -> assertFalse(king.canMove(position, position+board.getColumns()-1, board)),
                () -> assertFalse(king.canMove(position, position-1, board)),
                () -> assertFalse(king.canMove(position, position-board.getColumns()-1, board))
        );
    }

    @Test
    void canKillOnlyEnemies() {

        board.grid[0] = new King(Top);
        Movable king = board.grid[0];

        //obstacle
        board.grid[1] = new Pawn(Bottom);
        board.grid[board.getColumns()] = new Pawn(Top);

        assertAll(
                () -> assertTrue(king.canMove(0, 1, board)),
                () -> assertFalse(king.canMove(0, board.getColumns(), board))
        );
    }
}
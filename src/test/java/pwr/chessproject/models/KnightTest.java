package pwr.chessproject.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pwr.chessproject.game.Board;
import pwr.chessproject.models.functionalities.Movable;

import static org.junit.jupiter.api.Assertions.*;
import static pwr.chessproject.models.Figure.Player.Bottom;
import static pwr.chessproject.models.Figure.Player.Top;

@ExtendWith(FigureMoveExtension.class)
class KnightTest {

    private final Board board;

    public KnightTest(Board board) {
        this.board = board;
    }

    @Test
    void atLeftTopCorner () {
        int position = 0;
        board.grid[position] = new Knight(Top);
        Movable knight = board.grid[position];
        assertAll(
                () -> assertFalse(knight.canMove(position, -2*board.getColumns()-1, board)),
                () -> assertFalse(knight.canMove(position, -2*board.getColumns()+1, board)),
                () -> assertFalse(knight.canMove(position, +2-board.getColumns(), board)),
                () -> assertTrue(knight.canMove(position, +2+board.getColumns(), board)),
                () -> assertFalse(knight.canMove(position, +2*board.getColumns()-1, board)),
                () -> assertTrue(knight.canMove(position, +2*board.getColumns()+1, board)),
                () -> assertFalse(knight.canMove(position, -2-board.getColumns(), board)),
                () -> assertFalse(knight.canMove(position, -2+board.getColumns(), board))
        );
    }

    @Test
    void atRightTopCorner () {
        int position = board.getColumns()-1;
        board.grid[position] = new Knight(Top);
        Movable knight = board.grid[position];
        assertAll(
                () -> assertFalse(knight.canMove(position, position-2*board.getColumns()-1, board)),
                () -> assertFalse(knight.canMove(position, position-2*board.getColumns()+1, board)),
                () -> assertFalse(knight.canMove(position, position+2-board.getColumns(), board)),
                () -> assertFalse(knight.canMove(position, position+2+board.getColumns(), board)),
                () -> assertTrue(knight.canMove(position, position+2*board.getColumns()-1, board)),
                () -> assertFalse(knight.canMove(position, position+2*board.getColumns()+1, board)),
                () -> assertFalse(knight.canMove(position, position-2-board.getColumns(), board)),
                () -> assertTrue(knight.canMove(position, position-2+board.getColumns(), board))
        );
    }

    @Test
    void atRightBotCorner () {
        int position = board.getArea()-1;
        board.grid[position] = new Knight(Top);
        Movable knight = board.grid[position];
        assertAll(
                () -> assertTrue(knight.canMove(position, position-2*board.getColumns()-1, board)),
                () -> assertFalse(knight.canMove(position, position-2*board.getColumns()+1, board)),
                () -> assertFalse(knight.canMove(position, position+2-board.getColumns(), board)),
                () -> assertFalse(knight.canMove(position, position+2+board.getColumns(), board)),
                () -> assertFalse(knight.canMove(position, position+2*board.getColumns()-1, board)),
                () -> assertFalse(knight.canMove(position, position+2*board.getColumns()+1, board)),
                () -> assertTrue(knight.canMove(position, position-2-board.getColumns(), board)),
                () -> assertFalse(knight.canMove(position, position-2+board.getColumns(), board))
        );
    }

    @Test
    void atLeftBotCorner () {
        int position = board.getArea()-board.getColumns();
        board.grid[position] = new Knight(Top);
        Movable knight = board.grid[position];
        assertAll(
                () -> assertFalse(knight.canMove(position, position-2*board.getColumns()-1, board)),
                () -> assertTrue(knight.canMove(position, position-2*board.getColumns()+1, board)),
                () -> assertTrue(knight.canMove(position, position+2-board.getColumns(), board)),
                () -> assertFalse(knight.canMove(position, position+2+board.getColumns(), board)),
                () -> assertFalse(knight.canMove(position, position+2*board.getColumns()-1, board)),
                () -> assertFalse(knight.canMove(position, position+2*board.getColumns()+1, board)),
                () -> assertFalse(knight.canMove(position, position-2-board.getColumns(), board)),
                () -> assertFalse(knight.canMove(position, position-2+board.getColumns(), board))
        );
    }

    @Test
    void atInnerSquare () {
        int position = board.getColumns()+1;
        board.grid[position] = new Knight(Top);
        Movable knight = board.grid[position];
        assertAll(
                () -> assertFalse(knight.canMove(position, position-2*board.getColumns()-1, board)),
                () -> assertFalse(knight.canMove(position, position-2*board.getColumns()+1, board)),
                () -> assertTrue(knight.canMove(position, position+2-board.getColumns(), board)),
                () -> assertTrue(knight.canMove(position, position+2+board.getColumns(), board)),
                () -> assertTrue(knight.canMove(position, position+2*board.getColumns()-1, board)),
                () -> assertTrue(knight.canMove(position, position+2*board.getColumns()+1, board)),
                () -> assertFalse(knight.canMove(position, position-2-board.getColumns(), board)),
                () -> assertFalse(knight.canMove(position, position-2+board.getColumns(), board))
        );
    }

    @Test
    void atFreePosition () {
        int position = (board.getRows()/2)*board.getColumns()+board.getColumns()/2;
        board.grid[position] = new Knight(Top);
        Movable knight = board.grid[position];
        assertAll(
                () -> assertTrue(knight.canMove(position, position-2*board.getColumns()-1, board)),
                () -> assertTrue(knight.canMove(position, position-2*board.getColumns()+1, board)),
                () -> assertTrue(knight.canMove(position, position+2-board.getColumns(), board)),
                () -> assertTrue(knight.canMove(position, position+2+board.getColumns(), board)),
                () -> assertTrue(knight.canMove(position, position+2*board.getColumns()-1, board)),
                () -> assertTrue(knight.canMove(position, position+2*board.getColumns()+1, board)),
                () -> assertTrue(knight.canMove(position, position-2-board.getColumns(), board)),
                () -> assertTrue(knight.canMove(position, position-2+board.getColumns(), board))
        );
    }

    @Test
    void canMoveThroughObstacles() {

        board.grid[0] = new Knight(Top);
        Movable knight = board.grid[0];

        //obstacle
        board.grid[board.getColumns()] = new Pawn(Top);
        board.grid[2*board.getColumns()] = new Pawn(Top);

        assertTrue(knight.canMove(0, 2*board.getColumns()+1, board));
    }

    @Test
    void canKillOnlyEnemies() {

        board.grid[0] = new Knight(Top);
        Movable knight = board.grid[0];

        //obstacle
        board.grid[2+board.getColumns()] = new Pawn(Bottom);
        board.grid[2*board.getColumns()+1] = new Pawn(Top);

        assertAll(
                () -> assertTrue(knight.canMove(0, 2+board.getColumns(), board)),
                () -> assertFalse(knight.canMove(0, 2*board.getColumns()+1, board))
        );
    }

}
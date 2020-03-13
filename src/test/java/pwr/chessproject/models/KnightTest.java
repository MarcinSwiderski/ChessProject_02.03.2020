package pwr.chessproject.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pwr.chessproject.models.functionalities.IMoveable;

import static org.junit.jupiter.api.Assertions.*;
import static pwr.chessproject.game.Board.*;
import static pwr.chessproject.game.Board.COLUMNS;
import static pwr.chessproject.models.Figure.Player.Bottom;
import static pwr.chessproject.models.Figure.Player.Top;

@ExtendWith(FigureMoveExtension.class)
class KnightTest {

    @Test
    void atLeftTopCorner () {
        int position = 0;
        Grid[position] = new Knight(Top);
        IMoveable knight = (IMoveable)Grid[position];
        assertAll(
                () -> assertFalse(knight.canMove(position, -2*COLUMNS-1)),
                () -> assertFalse(knight.canMove(position, -2*COLUMNS+1)),
                () -> assertFalse(knight.canMove(position, +2-COLUMNS)),
                () -> assertTrue(knight.canMove(position, +2+COLUMNS)),
                () -> assertFalse(knight.canMove(position, +2*COLUMNS-1)),
                () -> assertTrue(knight.canMove(position, +2*COLUMNS+1)),
                () -> assertFalse(knight.canMove(position, -2-COLUMNS)),
                () -> assertFalse(knight.canMove(position, -2+COLUMNS))
        );
    }

    @Test
    void atRightTopCorner () {
        int position = COLUMNS-1;
        Grid[position] = new Knight(Top);
        IMoveable knight = (IMoveable)Grid[position];
        assertAll(
                () -> assertFalse(knight.canMove(position, position-2*COLUMNS-1)),
                () -> assertFalse(knight.canMove(position, position-2*COLUMNS+1)),
                () -> assertFalse(knight.canMove(position, position+2-COLUMNS)),
                () -> assertFalse(knight.canMove(position, position+2+COLUMNS)),
                () -> assertTrue(knight.canMove(position, position+2*COLUMNS-1)),
                () -> assertFalse(knight.canMove(position, position+2*COLUMNS+1)),
                () -> assertFalse(knight.canMove(position, position-2-COLUMNS)),
                () -> assertTrue(knight.canMove(position, position-2+COLUMNS))
        );
    }

    @Test
    void atRightBotCorner () {
        int position = AREA-1;
        Grid[position] = new Knight(Top);
        IMoveable knight = (IMoveable)Grid[position];
        assertAll(
                () -> assertTrue(knight.canMove(position, position-2*COLUMNS-1)),
                () -> assertFalse(knight.canMove(position, position-2*COLUMNS+1)),
                () -> assertFalse(knight.canMove(position, position+2-COLUMNS)),
                () -> assertFalse(knight.canMove(position, position+2+COLUMNS)),
                () -> assertFalse(knight.canMove(position, position+2*COLUMNS-1)),
                () -> assertFalse(knight.canMove(position, position+2*COLUMNS+1)),
                () -> assertTrue(knight.canMove(position, position-2-COLUMNS)),
                () -> assertFalse(knight.canMove(position, position-2+COLUMNS))
        );
    }

    @Test
    void atLeftBotCorner () {
        int position = AREA-COLUMNS;
        Grid[position] = new Knight(Top);
        IMoveable knight = (IMoveable)Grid[position];
        assertAll(
                () -> assertFalse(knight.canMove(position, position-2*COLUMNS-1)),
                () -> assertTrue(knight.canMove(position, position-2*COLUMNS+1)),
                () -> assertTrue(knight.canMove(position, position+2-COLUMNS)),
                () -> assertFalse(knight.canMove(position, position+2+COLUMNS)),
                () -> assertFalse(knight.canMove(position, position+2*COLUMNS-1)),
                () -> assertFalse(knight.canMove(position, position+2*COLUMNS+1)),
                () -> assertFalse(knight.canMove(position, position-2-COLUMNS)),
                () -> assertFalse(knight.canMove(position, position-2+COLUMNS))
        );
    }

    @Test
    void atInnerSquare () {
        int position = COLUMNS+1;
        Grid[position] = new Knight(Top);
        IMoveable knight = (IMoveable)Grid[position];
        assertAll(
                () -> assertFalse(knight.canMove(position, position-2*COLUMNS-1)),
                () -> assertFalse(knight.canMove(position, position-2*COLUMNS+1)),
                () -> assertTrue(knight.canMove(position, position+2-COLUMNS)),
                () -> assertTrue(knight.canMove(position, position+2+COLUMNS)),
                () -> assertTrue(knight.canMove(position, position+2*COLUMNS-1)),
                () -> assertTrue(knight.canMove(position, position+2*COLUMNS+1)),
                () -> assertFalse(knight.canMove(position, position-2-COLUMNS)),
                () -> assertFalse(knight.canMove(position, position-2+COLUMNS))
        );
    }

    @Test
    void atFreePosition () {
        int position = (ROWS/2)*COLUMNS+COLUMNS/2;
        Grid[position] = new Knight(Top);
        IMoveable knight = (IMoveable)Grid[position];
        assertAll(
                () -> assertTrue(knight.canMove(position, position-2*COLUMNS-1)),
                () -> assertTrue(knight.canMove(position, position-2*COLUMNS+1)),
                () -> assertTrue(knight.canMove(position, position+2-COLUMNS)),
                () -> assertTrue(knight.canMove(position, position+2+COLUMNS)),
                () -> assertTrue(knight.canMove(position, position+2*COLUMNS-1)),
                () -> assertTrue(knight.canMove(position, position+2*COLUMNS+1)),
                () -> assertTrue(knight.canMove(position, position-2-COLUMNS)),
                () -> assertTrue(knight.canMove(position, position-2+COLUMNS))
        );
    }

    @Test
    void canMoveThroughObstacles() {

        Grid[0] = new Knight(Top);
        IMoveable knight = (IMoveable) Grid[0];

        //obstacle
        Grid[COLUMNS] = new Pawn(Top);
        Grid[2*COLUMNS] = new Pawn(Top);

        assertTrue(knight.canMove(0, 2*COLUMNS+1));
    }

    @Test
    void canKillOnlyEnemies() {

        Grid[0] = new Knight(Top);
        IMoveable knight = (IMoveable) Grid[0];

        //obstacle
        Grid[2+COLUMNS] = new Pawn(Bottom);
        Grid[2*COLUMNS+1] = new Pawn(Top);

        assertAll(
                () -> assertTrue(knight.canMove(0, 2+COLUMNS)),
                () -> assertFalse(knight.canMove(0, 2*COLUMNS+1))
        );
    }

}
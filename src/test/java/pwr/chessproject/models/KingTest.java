package pwr.chessproject.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pwr.chessproject.models.functionalities.IMoveable;

import static org.junit.jupiter.api.Assertions.*;
import static pwr.chessproject.board.Board.*;
import static pwr.chessproject.models.Figure.Player.Top;

@ExtendWith(FigureMoveExtension.class)
class KingTest {

    @Test
    void atLeftTopCorner () {
        int position = 0;
        Grid[position] = new King(Top);
        IMoveable king = (IMoveable)Grid[position];
        Assertions.assertAll(
                () -> assertFalse(king.canMove(position, -COLUMNS)),
                () -> assertFalse(king.canMove(position, -COLUMNS+1)),
                () -> assertTrue(king.canMove(position, 1)),
                () -> assertTrue(king.canMove(position, COLUMNS+1)),
                () -> assertTrue(king.canMove(position, COLUMNS)),
                () -> assertFalse(king.canMove(position, COLUMNS-1)),
                () -> assertFalse(king.canMove(position, -1)),
                () -> assertFalse(king.canMove(position, -COLUMNS-1))
        );
    }

    @Test
    void atRightTopCorner () {
        int position = COLUMNS-1;
        Grid[position] = new King(Top);
        IMoveable king = (IMoveable)Grid[position];
        Assertions.assertAll(
                () -> assertFalse(king.canMove(position, position-COLUMNS)),
                () -> assertFalse(king.canMove(position, position-COLUMNS+1)),
                () -> assertFalse(king.canMove(position, position+1)),
                () -> assertFalse(king.canMove(position, position+COLUMNS+1)),
                () -> assertTrue(king.canMove(position, position+COLUMNS)),
                () -> assertTrue(king.canMove(position, position+COLUMNS-1)),
                () -> assertTrue(king.canMove(position, position-1)),
                () -> assertFalse(king.canMove(position, position-COLUMNS-1))
        );
    }

    @Test
    void atRightBotCorner () {
        int position = AREA-1;
        Grid[position] = new King(Top);
        IMoveable king = (IMoveable)Grid[position];
        Assertions.assertAll(
                () -> assertTrue(king.canMove(position, position-COLUMNS)),
                () -> assertFalse(king.canMove(position, position-COLUMNS+1)),
                () -> assertFalse(king.canMove(position, position+1)),
                () -> assertFalse(king.canMove(position, position+COLUMNS+1)),
                () -> assertFalse(king.canMove(position, position+COLUMNS)),
                () -> assertFalse(king.canMove(position, position+COLUMNS-1)),
                () -> assertTrue(king.canMove(position, position-1)),
                () -> assertTrue(king.canMove(position, position-COLUMNS-1))
        );
    }

    @Test
    void atLeftBotCorner () {
        int position = AREA-COLUMNS;
        Grid[position] = new King(Top);
        IMoveable king = (IMoveable)Grid[position];
        Assertions.assertAll(
                () -> assertTrue(king.canMove(position, position-COLUMNS)),
                () -> assertTrue(king.canMove(position, position-COLUMNS+1)),
                () -> assertTrue(king.canMove(position, position+1)),
                () -> assertFalse(king.canMove(position, position+COLUMNS+1)),
                () -> assertFalse(king.canMove(position, position+COLUMNS)),
                () -> assertFalse(king.canMove(position, position+COLUMNS-1)),
                () -> assertFalse(king.canMove(position, position-1)),
                () -> assertFalse(king.canMove(position, position-COLUMNS-1))
        );
    }
}
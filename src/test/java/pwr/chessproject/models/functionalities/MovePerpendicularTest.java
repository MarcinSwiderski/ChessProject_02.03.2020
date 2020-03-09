package pwr.chessproject.models.functionalities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pwr.chessproject.models.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static pwr.chessproject.board.Board.*;
import static pwr.chessproject.models.Figure.Player.Top;

@ExtendWith(FigureMoveExtension.class)
class MovePerpendicularTest {

    private static Stream<Arguments> figureProvider () {
        return  Stream.of(
                Arguments.of(new Tower(Top)),
                Arguments.of(new Queen(Top))
        );
    }

    @ParameterizedTest(name = "{arguments} can move perpendicularly")
    @MethodSource("figureProvider")
    void canMovePerpendicular(IMoveable figure) {
        int position = (ROWS/2)*COLUMNS+COLUMNS/2;
        Grid[position] = (Figure)figure;
        Assertions.assertAll(
                () -> assertTrue(figure.canMove(position, position+1)),
                () -> assertTrue(figure.canMove(position, position-1)),
                () -> assertTrue(figure.canMove(position, position+COLUMNS)),
                () -> assertTrue(figure.canMove(position, position-COLUMNS))
        );
    }

    @ParameterizedTest(name = "{arguments} can move perpendicularly")
    @MethodSource("figureProvider")
    void canMoveOnlyPerpendicular(IMoveable figure) {
        if (figure instanceof Tower) {
            int position = COLUMNS*2-1;
            Grid[position] = (Figure)figure;
            Assertions.assertAll(
                    () -> assertFalse(figure.canMove(position, position+COLUMNS-1)),
                    () -> assertFalse(figure.canMove(position, position-COLUMNS-1)),
                    () -> assertFalse(figure.canMove(position, position+1)),
                    () -> assertFalse(figure.canMove(position, position-2*COLUMNS))
            );
        }

    }

    @ParameterizedTest(name = "{arguments} can not move through obstacles")
    @MethodSource("figureProvider")
    void canNotMoveThroughObstacles(IMoveable figure) {

        Grid[0] = (Figure) figure;

        //obstacle
        Grid[2] = new Pawn(Top);

        Assertions.assertAll(
                () -> assertFalse(figure.canMove(0, 2)),
                () -> assertFalse(figure.canMove(0, 3))
        );
    }
}
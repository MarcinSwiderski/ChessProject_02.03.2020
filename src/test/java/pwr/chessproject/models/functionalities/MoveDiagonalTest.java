package pwr.chessproject.models.functionalities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pwr.chessproject.models.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static pwr.chessproject.game.Board.*;
import static pwr.chessproject.models.Figure.Player.Top;

@ExtendWith(FigureMoveExtension.class)
class MoveDiagonalTest {
    private static Stream<Arguments> figureProvider () {
        return  Stream.of(
                Arguments.of(new Bishop(Top)),
                Arguments.of(new Queen(Top))
        );
    }

    @ParameterizedTest(name = "{arguments} can move perpendicularly")
    @MethodSource("figureProvider")
    void canMoveDiagonal(IMoveable figure) {
        int position = (ROWS/2)*COLUMNS+COLUMNS/2;
        Grid[position] = (Figure)figure;
        Assertions.assertAll(
                () -> assertTrue(figure.canMove(position, position+COLUMNS+1)),
                () -> assertTrue(figure.canMove(position, position-COLUMNS+1)),
                () -> assertTrue(figure.canMove(position, position+COLUMNS-1)),
                () -> assertTrue(figure.canMove(position, position-COLUMNS-1))
        );
    }

    @ParameterizedTest(name = "{arguments} can move perpendicularly")
    @MethodSource("figureProvider")
    void canMoveOnlyDiagonal(IMoveable figure) {
        if (figure instanceof Bishop) {
            int position = COLUMNS*3-1;
            Grid[position] = (Figure)figure;
            Assertions.assertAll(
                    () -> assertFalse(figure.canMove(position, position+COLUMNS+1)),
                    () -> assertFalse(figure.canMove(position, position-COLUMNS+1)),
                    () -> assertFalse(figure.canMove(position, position-1)),
                    () -> assertFalse(figure.canMove(position, position+1))
            );
        }
    }

    @ParameterizedTest(name = "{arguments} can not move through obstacles")
    @MethodSource("figureProvider")
    void canNotMoveThroughObstacles(IMoveable figure) {

        Grid[0] = (Figure) figure;

        //obstacle
        Grid[COLUMNS+1] = new Pawn(Top);

        Assertions.assertAll(
                () -> assertFalse(figure.canMove(0, COLUMNS+1)),
                () -> assertFalse(figure.canMove(0, 2*(COLUMNS+1)))
        );
    }

}
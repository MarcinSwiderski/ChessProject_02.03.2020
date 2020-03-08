package pwr.ChessProject.models.functionalities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import pwr.ChessProject.models.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static pwr.ChessProject.board.Board.Grid;
import static pwr.ChessProject.models.Figure.Player.Bottom;
import static pwr.ChessProject.models.Figure.Player.Top;

@ExtendWith(FigureMoveExtension.class)
class IMovePerpendicularTest {

    private static Stream<Arguments> figureProvider () {
        return  Stream.of(
                Arguments.of(new Tower(Top)),
                Arguments.of(new Queen(Top))
        );
    }

    @Test
    @ParameterizedTest(name = "{arguments} can move perpendicularly")
    @MethodSource("figureProvider")
    void canMovePerpendicular(IMoveable figure) {
        Grid[28] = (Figure)figure;
        Assertions.assertAll(
                () -> assertTrue(figure.canMove(28, 4)),
                () -> assertTrue(figure.canMove(28, 31)),
                () -> assertTrue(figure.canMove(28, 60)),
                () -> assertTrue(figure.canMove(28, 24))
        );
    }

    @Test
    @ParameterizedTest(name = "{arguments} can not move through obstacles")
    @MethodSource("figureProvider")
    void canNotMoveThroughObstacles(IMoveable figure) {

        Grid[28] = (Figure) figure;

        //obstacles
        Grid[26] = new Pawn(Top);

        IMoveable tower = (IMoveable)Grid[48];

        Assertions.assertAll(
                () -> assertFalse(tower.canMove(28, 25)),
                () -> assertFalse(tower.canMove(28, 26))
        );
    }
}
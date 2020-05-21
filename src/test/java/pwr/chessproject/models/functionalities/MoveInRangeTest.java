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
class MoveInRangeTest {

    private static Stream<Arguments> figureProvider () {
        return  Stream.of(
                Arguments.of(new Pawn(Top)),
                Arguments.of(new Tower(Top)),
                Arguments.of(new Knight(Top)),
                Arguments.of(new Bishop(Top)),
                Arguments.of(new Queen(Top)),
                Arguments.of(new King(Top))
        );
    }

    @ParameterizedTest(name = "{arguments} can not move outside of the board range")
    @MethodSource("figureProvider")
    void canNotMoveOutsideRange(Movable figure) {
        int position = AREA-1;
        Grid[position] = (Figure)figure;
        if (figure instanceof Bishop)
        Assertions.assertAll(
                () -> assertFalse(figure.canMove(position, position+COLUMNS-1)),
                () -> assertFalse(figure.canMove(position, position+COLUMNS+1)),
                () -> assertFalse(figure.canMove(position, position-COLUMNS+1))
        );
        else if (figure instanceof Knight)
            Assertions.assertAll(
                    () -> assertFalse(figure.canMove(position, position+2*COLUMNS-1)),
                    () -> assertFalse(figure.canMove(position, position+2*COLUMNS+1)),
                    () -> assertFalse(figure.canMove(position, position+2-COLUMNS)),
                    () -> assertFalse(figure.canMove(position, position+2+COLUMNS))
            );
        else
            assertFalse(figure.canMove(position, position+COLUMNS));
    }
}
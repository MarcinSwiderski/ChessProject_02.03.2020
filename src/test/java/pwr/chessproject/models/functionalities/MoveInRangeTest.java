package pwr.chessproject.models.functionalities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pwr.chessproject.game.Board;
import pwr.chessproject.models.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static pwr.chessproject.game.Board.*;
import static pwr.chessproject.models.Figure.Player.Top;

@ExtendWith(FigureMoveExtension.class)
class MoveInRangeTest {

    private Board board;

    public MoveInRangeTest(Board board) {
        this.board = board;
    }

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
        int position = board.getArea()-1;
        board.grid[position] = (Figure)figure;
        if (figure instanceof Bishop)
        assertAll(
                () -> assertFalse(figure.canMove(position, position+board.getColumns()-1, board)),
                () -> assertFalse(figure.canMove(position, position+board.getColumns()+1, board)),
                () -> assertFalse(figure.canMove(position, position-board.getColumns()+1, board))
        );
        else if (figure instanceof Knight)
            assertAll(
                    () -> assertFalse(figure.canMove(position, position+2*board.getColumns()-1, board)),
                    () -> assertFalse(figure.canMove(position, position+2*board.getColumns()+1, board)),
                    () -> assertFalse(figure.canMove(position, position+2-board.getColumns(), board)),
                    () -> assertFalse(figure.canMove(position, position+2+board.getColumns(), board))
            );
        else
            assertFalse(figure.canMove(position, position+board.getColumns(), board));
    }
}
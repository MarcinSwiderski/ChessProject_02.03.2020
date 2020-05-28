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
class MoveDiagonalTest {

    private final Board board;

    public MoveDiagonalTest(Board board) {
        this.board = board;
    }

    private static Stream<Arguments> figureProvider () {
        return  Stream.of(
                Arguments.of(new Bishop(Top)),
                Arguments.of(new Queen(Top))
        );
    }

    @ParameterizedTest(name = "{arguments} can move perpendicularly")
    @MethodSource("figureProvider")
    void canMoveDiagonal(Movable figure) {
        int position = (board.getRows()/2)*board.getColumns()+board.getColumns()/2;
        board.grid[position] = (Figure)figure;
        Assertions.assertAll(
                () -> assertTrue(figure.canMove(position, position+board.getColumns()+1, board)),
                () -> assertTrue(figure.canMove(position, position-board.getColumns()+1, board)),
                () -> assertTrue(figure.canMove(position, position+board.getColumns()-1, board)),
                () -> assertTrue(figure.canMove(position, position-board.getColumns()-1, board))
        );
    }

    @ParameterizedTest(name = "{arguments} can move perpendicularly")
    @MethodSource("figureProvider")
    void canMoveOnlyDiagonal(Movable figure) {
        if (figure instanceof Bishop) {
            int position = board.getColumns()*3-1;
            board.grid[position] = (Figure)figure;
            Assertions.assertAll(
                    () -> assertFalse(figure.canMove(position, position+board.getColumns()+1, board)),
                    () -> assertFalse(figure.canMove(position, position-board.getColumns()+1, board)),
                    () -> assertFalse(figure.canMove(position, position-1, board)),
                    () -> assertFalse(figure.canMove(position, position+1, board))
            );
        }
    }

    @ParameterizedTest(name = "{arguments} can not move through obstacles")
    @MethodSource("figureProvider")
    void canNotMoveThroughObstacles(Movable figure) {

        board.grid[0] = (Figure) figure;

        //obstacle
        board.grid[board.getColumns()+1] = new Pawn(Top);

        Assertions.assertAll(
                () -> assertFalse(figure.canMove(0, board.getColumns()+1, board)),
                () -> assertFalse(figure.canMove(0, 2*(board.getColumns()+1), board))
        );
    }

}
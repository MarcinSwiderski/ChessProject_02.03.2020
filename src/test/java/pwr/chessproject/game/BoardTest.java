package pwr.chessproject.game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pwr.chessproject.models.Bishop;
import pwr.chessproject.models.Figure;
import pwr.chessproject.models.King;
import pwr.chessproject.models.functionalities.NotMoveableException;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void creatingBoardSmallerThan3x3ThrowsException () {
        Exception exception = assertThrows(IllegalArgumentException.class, ()-> {
            BoardCreator boardCreator = new BoardCreator();
            Board board = boardCreator.customEmptyBoard(2, 3);
        });

        String expectedMessage = "Board is too small";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @ParameterizedTest
    @CsvSource({"8, 8", "20, 8", "15, 10"})
    void expectVariousBoardSizesNotToThrow (int rows, int columns) {
        BoardCreator boardCreator = new BoardCreator();
        Board board = boardCreator.customEmptyBoard(rows, columns);
    }

    @ParameterizedTest
    @CsvSource({"-1", "64"})
    void selectingFigureOutsideOfRangeThrowsException(int position) throws IOException {
        Board board = new BoardCreator().defaultBoard();
        Exception exception = assertThrows(IllegalArgumentException.class, ()-> board.checkPosition(position));

        String expectedMessage = "Can not select figure outside of the board";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    void selectBlankFieldThrowsException() throws IOException {
        Board board = new BoardCreator().defaultBoard();
        Exception exception = assertThrows(NullPointerException.class, ()-> board.checkPosition(32));

        String expectedMessage = "There is no figure at the selected position";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void moveFigureTest() {
        Board board = new BoardCreator().customEmptyBoard(10, 10);
        board.clearBoard();
        Bishop bishop = new Bishop(Figure.Player.Top);
        board.grid[0] = bishop;
        board.moveFigure(0, board.getArea()-1);
        assertSame(board.grid[board.getArea() - 1], bishop);
    }

    @Test
    void movingKingChangesKingPositionField() {
        Board board = new BoardCreator().customEmptyBoard(8,8);
        King king = new King(Figure.Player.Top);
        int startingPosition = board.KING_TOP_STARTING_POINT;
        board.grid[startingPosition] = king;
        board.moveFigure(startingPosition, startingPosition+1);
        Assertions.assertAll(
                () -> assertNull(board.grid[startingPosition]),
                () -> assertEquals(board.grid[startingPosition+1], king)
        );
    }

    @Test
    void deepCloneTest() throws IOException {
        Board board = new BoardCreator().defaultBoard();
        Board anotherBoard = board.clone();
        anotherBoard.setKingPosition(Figure.Player.Top, 50);
        Assertions.assertAll(
                () -> assertNotEquals(board, anotherBoard),
                () -> assertNotEquals(board.grid, anotherBoard.grid),
                () -> assertNotEquals(board.grid[0], anotherBoard.grid[0]),
                () -> assertNotEquals(board.kingPosition, anotherBoard.kingPosition)
        );
    }
}
package pwr.chessproject.game;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pwr.chessproject.models.Bishop;
import pwr.chessproject.models.Figure;
import pwr.chessproject.models.functionalities.NotMoveableException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void creatingBoardSmallerThan8x8ThrowsException () {
        Exception exception = assertThrows(IllegalArgumentException.class, ()-> {
            BoardCreator boardCreator = new BoardCreator();
            Board board = boardCreator.customEmptyBoard(7, 8);
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
    void moveFigureTest() throws NotMoveableException {
        Board board = new BoardCreator().customEmptyBoard(10, 10);
        board.clearBoard();
        Bishop bishop = new Bishop(Figure.Player.Top);
        board.grid[0] = bishop;
        board.moveFigure(0, board.getArea()-1);
        assertSame(board.grid[board.getArea() - 1], bishop);
    }
}
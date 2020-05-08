package pwr.chessproject.game;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pwr.chessproject.models.Bishop;
import pwr.chessproject.models.Figure;
import pwr.chessproject.models.functionalities.NotMoveableException;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void creatingBoardSmallerThan8x8ThrowsException () {
        Exception exception = assertThrows(IllegalArgumentException.class, ()-> {
            BoardCreator boardCreator = new BoardCreator();
            Board.Grid = boardCreator.customEmptyBoard(7, 8);
        });

        String expectedMessage = "Board is too small";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @ParameterizedTest
    @CsvSource({"8, 8", "20, 8", "15, 10"})
    void expectVariousBoardSizesNotToThrow (int rows, int columns) {
        BoardCreator boardCreator = new BoardCreator();
        Board.Grid = boardCreator.customEmptyBoard(rows, columns);
    }

    @ParameterizedTest
    @CsvSource({"-1", "64"})
    void selectingFigureOutsideOfRangeThrowsException(int position) {
        BoardCreator boardCreator = new BoardCreator();
        Board.Grid = boardCreator.constructDefaultBoard();
        Exception exception = assertThrows(IllegalArgumentException.class, ()-> Board.checkPosition(position));

        String expectedMessage = "Can not select figure outside of the board";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    void selectBlankFieldThrowsException() {
        BoardCreator boardCreator = new BoardCreator();
        Board.Grid = boardCreator.constructDefaultBoard();
        Exception exception = assertThrows(NullPointerException.class, ()-> Board.checkPosition(32));

        String expectedMessage = "There is no figure at the selected position";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void moveFigureTest() throws NotMoveableException {
        BoardCreator boardCreator = new BoardCreator();
        Board.Grid = boardCreator.customEmptyBoard(10, 10);
        Bishop bishop = new Bishop(Figure.Player.Top);
        Board.Grid[0] = bishop;
        Board.moveFigure(0, Board.AREA-1);
        assertSame(Board.Grid[Board.AREA - 1], bishop);
    }
}
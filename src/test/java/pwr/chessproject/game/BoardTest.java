package pwr.chessproject.game;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void creatingBoardSmallerThan8x8ThrowsException () {
        Exception exception = assertThrows(IllegalArgumentException.class, ()-> {
            Board board = new Board(7, 8);
        });

        String expectedMessage = "Board is too small";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @ParameterizedTest
    @CsvSource({"8, 8", "20, 8", "15, 10"})
    void expectVariousBoardSizesNotToThrow (int rows, int columns) {
        Board board = new Board(rows, columns);
    }

    @ParameterizedTest
    @CsvSource({"-1", "64"})
    void selectingFigureOutsideOfRangeThrowsException(int position) {
        Board board = new Board();
        Exception exception = assertThrows(IllegalArgumentException.class, ()-> board.checkPosition(position));

        String expectedMessage = "Can not select figure outside of the board";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    void selectBlankFieldThrowsException() {
        Board board = new Board();
        Exception exception = assertThrows(NullPointerException.class, ()-> board.checkPosition(32));

        String expectedMessage = "There is no figure at the selected position";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void moveFigureTest() {
    }
}
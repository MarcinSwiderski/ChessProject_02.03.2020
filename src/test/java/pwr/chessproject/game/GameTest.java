package pwr.chessproject.game;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import pwr.chessproject.models.Figure;
import pwr.chessproject.models.FigureMoveExtension;
import pwr.chessproject.models.King;
import pwr.chessproject.models.Tower;

import static org.junit.jupiter.api.Assertions.*;
import static pwr.chessproject.game.Board.*;
import static pwr.chessproject.models.Figure.Player.*;

class GameTest {

    private static Game game;

    @BeforeAll
    private static void setupGame () {
        Board board = new Board();
        board.clearBoard();
        game = new Game(board);
    }

    @BeforeEach
    public void whipeOutBoard() {
        Board board = new Board();
        board.clearBoard();
    }

    @Test
    void simulateMoveAndCheck() {
    }

    @Test
    void isChecked() {
        Grid[0] = new King(Top);
        Grid[AREA-1] = new King(Bottom);
        Grid[AREA-COLUMNS] = new Tower(Bottom);
        assertAll(
                () -> assertTrue(game.isChecked(0)),
                () -> assertFalse(game.isChecked(AREA-1))
        );
    }

    @Test
    void isCheckmated() {
    }
}
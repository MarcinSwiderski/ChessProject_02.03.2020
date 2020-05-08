package pwr.chessproject.game;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pwr.chessproject.models.Bishop;
import pwr.chessproject.models.King;
import pwr.chessproject.models.Pawn;
import pwr.chessproject.models.Tower;
import pwr.chessproject.models.functionalities.NotMoveableException;

import static org.junit.jupiter.api.Assertions.*;
import static pwr.chessproject.game.Board.*;
import static pwr.chessproject.models.Figure.Player.*;

class GameTest {

    private static Game game;

    @BeforeAll
    private static void setupGame () {
        BoardCreator boardCreator = new BoardCreator();
        Board.Grid = boardCreator.constructDefaultBoard();
        game = new Game();
    }

    @BeforeEach
    public void whipeOutBoard() {
        Board.clearBoard();
    }

    @Test
    void simulateMoveAndCheckReversesChanges() throws NotMoveableException {
        Grid[0] = new Pawn(Top);
        game.simulateMoveAndCheck(0, 2*COLUMNS, game::isChecked);
        assertTrue(((Pawn)Grid[0]).getFirstMoveIndicator());
    }

    @Test
    void isChecked() {
        Grid[0] = new King(Bottom);
        Grid[AREA-1] = new King(Top);
        Grid[AREA-COLUMNS] = new Tower(Top);
        assertAll(
                () -> assertTrue(game.isChecked(0)),
                () -> assertFalse(game.isChecked(AREA-1))
        );
    }

    @Test
    void isCheckmated() throws NotMoveableException {
        Grid[0] = new King(Bottom);
        Grid[COLUMNS-1] = new Tower(Top);
        Grid[AREA-COLUMNS] = new Tower(Top);
        Grid[2*COLUMNS+2] = new Bishop(Top);
        assertTrue(game.isCheckmated(0));
    }
}
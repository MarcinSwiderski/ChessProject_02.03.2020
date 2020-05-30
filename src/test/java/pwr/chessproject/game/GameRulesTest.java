package pwr.chessproject.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pwr.chessproject.models.functionalities.NotMoveableException;

import java.rmi.AccessException;

import static org.junit.jupiter.api.Assertions.*;

class GameRulesTest {

    ConsoleGame game;

    @BeforeEach
    void initializeGame() {
        game = new ConsoleGame(new BoardCreator().boardFromFile("test"));
    }

    @Test
    void canSelectFigure() {
        assertAll(
                () -> assertThrows(NullPointerException.class, () -> game.canSelectFigure(1)),
                () -> assertThrows(IllegalArgumentException.class, () -> game.canSelectFigure(64)),
                () -> assertThrows(AccessException.class, () -> game.canSelectFigure(4)),
                () -> assertDoesNotThrow(() -> game.canSelectFigure(7))
        );
    }

    @Test
    void isPlayerUnderCheck() {
        assertFalse(game.isPlayerUnderCheck());
        game.passTurn();
        assertTrue(game.isPlayerUnderCheck());
    }

    @Test
    void isMoveValid() {
        game.passTurn();
        assertAll(
                () -> assertThrows(NotMoveableException.class, () -> game.isMoveValid(4, 20)),
                () -> assertFalse(game.isMoveValid(4, 5)),
                () -> assertTrue(game.isMoveValid(4, 12))
        );
    }

    @Test
    void isPlayerCheckmated() {
        game = new ConsoleGame(new BoardCreator().boardFromFile("checkmate_test"));
        assertFalse(game.isPlayerCheckmated());
        game.passTurn();
        assertTrue(game.isPlayerCheckmated());
    }
}
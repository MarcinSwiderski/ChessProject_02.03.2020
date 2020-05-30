package pwr.chessproject.game;

import org.junit.jupiter.api.Test;
import pwr.chessproject.models.Figure;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GameStatusTest {

    @Test
    void passTurnTest() {
        ConsoleGame game = new ConsoleGame(new BoardCreator().customEmptyBoard(8,8));
        assertEquals(game.getPlayer(), Figure.Player.Bottom);
        game.passTurn();
        assertEquals(game.getPlayer(), Figure.Player.Top);
    }
}
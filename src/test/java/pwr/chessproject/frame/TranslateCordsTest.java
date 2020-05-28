package pwr.chessproject.frame;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pwr.chessproject.game.Board;
import pwr.chessproject.game.BoardCreator;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TranslateCordsTest {

    Board board = new BoardCreator().defaultBoard();

    TranslateCordsTest() throws IOException {
    }

    @Test
    void translateIntCordToString() {
        TranslateCords translateCords = new TranslateCords(board);
        Assertions.assertAll(
                () -> assertEquals(translateCords.translateIntCordToString(0), "A8"),
                () -> assertEquals(translateCords.translateIntCordToString(63), "H1")
        );
    }

    @Test
    void translateStringCordToInt() {
        TranslateCords translateCords = new TranslateCords(board);
        Assertions.assertAll(
                () -> assertEquals(translateCords.translateStringCordToInt("A8"), 0),
                () -> assertEquals(translateCords.translateStringCordToInt("H1"), 63),
                () -> assertEquals(translateCords.translateStringCordToInt("a8"), 0),
                () -> assertEquals(translateCords.translateStringCordToInt("h1"), 63)
        );
    }
}
package pwr.chessproject.models;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import pwr.chessproject.game.Board;

public class FigureMoveExtension implements AfterEachCallback, BeforeAllCallback {
    private Board board;

    @Override
    public void beforeAll(ExtensionContext context) {
        board = new Board();
        board.clearBoard();
    }

    @Override
    public void afterEach(ExtensionContext context) {
        board.clearBoard();
    }
}

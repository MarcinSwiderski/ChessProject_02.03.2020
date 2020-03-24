package pwr.chessproject.models;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import pwr.chessproject.game.Board;

public class FigureMoveExtension implements BeforeAllCallback, BeforeEachCallback {
    private Board board;

    @Override
    public void beforeAll(ExtensionContext context) {
        board = new Board();
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        board.clearBoard();
    }
}

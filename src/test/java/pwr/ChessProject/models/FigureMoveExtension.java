package pwr.ChessProject.models;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import pwr.ChessProject.board.Board;

import java.util.Optional;

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

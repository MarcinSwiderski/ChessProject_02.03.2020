package pwr.chessproject.models;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import pwr.chessproject.game.Board;
import pwr.chessproject.game.BoardCreator;

public class FigureMoveExtension implements BeforeAllCallback, BeforeEachCallback {

    @Override
    public void beforeAll(ExtensionContext context) {
        BoardCreator boardCreator = new BoardCreator();
        Board.Grid = boardCreator.constructDefaultBoard();;
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        Board.clearBoard();
    }
}

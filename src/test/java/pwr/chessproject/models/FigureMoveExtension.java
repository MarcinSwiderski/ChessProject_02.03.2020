package pwr.chessproject.models;

import org.junit.jupiter.api.extension.*;
import pwr.chessproject.game.Board;
import pwr.chessproject.game.BoardCreator;

import java.io.IOException;

public class FigureMoveExtension implements BeforeEachCallback, ParameterResolver {

    public Board board = new BoardCreator().defaultBoard();

    public FigureMoveExtension() throws IOException {
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        board.clearBoard();
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(Board.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return this.board;
    }
}

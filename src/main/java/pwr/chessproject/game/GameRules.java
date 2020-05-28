package pwr.chessproject.game;

import pwr.chessproject.models.Figure;
import pwr.chessproject.models.functionalities.Movable;
import pwr.chessproject.models.functionalities.NotMoveableException;

import java.rmi.AccessException;

public abstract class GameRules extends GameStatus {
    protected GameRules(Board board) {
        super(board);
    }

    protected void canSelectFigure(int position) throws NullPointerException, IllegalArgumentException, AccessException {
        board.checkPosition(position);
        if (board.grid[position].player != getPlayer()) {
            throw new AccessException("Can't select opponents figures.");
        }
    }

    protected Boolean isPlayerUnderCheck() {
        int kingPosition = board.getKingPosition(getPlayer());
        for (int position = 0; position < board.getArea(); position++) {
            Figure enemyFigure = board.grid[position];
            if (enemyFigure != null && enemyFigure.player == getOpponent() && ((Movable) enemyFigure).canMove(position, kingPosition, board))
                return true;
        }
        return false;
    }

    protected Boolean isMoveValid(int position, int target) throws NotMoveableException {
        boolean result;
        board.isMovePossible(position, target);
        Board beforeTheMove = board.clone();
        board.moveFigure(position, target);
        result = !isPlayerUnderCheck();
        board = beforeTheMove;
        return result;
    }

    protected Boolean isPlayerCheckmated() {
        if (isPlayerUnderCheck()) {
            for (int position = 0; position < board.getArea(); position++) {
                Figure friendlyFigure = board.grid[position];
                if (friendlyFigure == null || friendlyFigure.player != getPlayer())
                    continue;

                for (int target : friendlyFigure.getAvailableFields(position, board)) {
                    Board beforeTheMove = board.clone();
                    board.moveFigure(position, target);
                    if (!isPlayerUnderCheck()) {
                        board = beforeTheMove;
                        return false;
                    }
                    board = beforeTheMove;
                }
                return true;
            }
        }
        return false;
    }
}

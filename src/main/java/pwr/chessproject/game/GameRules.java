package pwr.chessproject.game;

import pwr.chessproject.models.Figure;
import pwr.chessproject.models.functionalities.Movable;
import pwr.chessproject.models.functionalities.NotMoveableException;

import java.rmi.AccessException;

/**
 * Implements logic for rules for moving on the board
 */
public abstract class GameRules extends GameStatus {
    protected GameRules(Board board) {
        super(board);
    }

    /**
     * Checks if it is possible to select figure at the selected position
     * @param position Position to check
     * @throws NullPointerException When there is no figure at the selected position
     * @throws IllegalArgumentException When position is outside of the board range
     * @throws AccessException When figure at the position belongs to another player
     */
    protected void canSelectFigure(int position) throws NullPointerException, IllegalArgumentException, AccessException {
        board.checkPosition(position);
        if (board.grid[position].player != getPlayer()) {
            throw new AccessException("Can't select opponents figures.");
        }
    }

    /**
     * Checks if any enemy figure can move into current player king position. Can not throw exception
     * @return True if player is under check, else false
     */
    protected Boolean isPlayerUnderCheck() {
        int kingPosition = board.getKingPosition(getPlayer());
        for (int position = 0; position < board.getArea(); position++) {
            Figure enemyFigure = board.grid[position];
            if (enemyFigure != null && enemyFigure.player == getOpponent() && ((Movable) enemyFigure).canMove(position, kingPosition, board))
                return true;
        }
        return false;
    }

    /**
     * Do not changes any state and throws an error if move is impossible for one of the reasons:
     * 1) position is not in range
     * 2) there is no figure at the selected position
     * 3) figure's rules does not allow it
     * Then returns true if player will not be under check after move and false otherwise
     * @param position The Figure current position
     * @param target   The target position to move to
     * @return True if move is possible else false
     * @throws NullPointerException When there is null at the selected position
     * @throws IllegalArgumentException When position is outside of the board
     * @throws NotMoveableException When figures movement rules do not allow to move it into specified target
     */
    protected Boolean isMoveValid(int position, int target) throws NotMoveableException, NullPointerException, IllegalArgumentException {
        boolean result;
        board.isMovePossible(position, target);
        Board beforeTheMove = board.clone();
        board.moveFigure(position, target);
        result = !isPlayerUnderCheck();
        board = beforeTheMove;
        return result;
    }

    /**
     * Checks if:
     * 1) player is under check
     * 2) player can move any of theirs figures in a way resolving in not being under check
     * @return True if player is checkmated, else false
     */
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

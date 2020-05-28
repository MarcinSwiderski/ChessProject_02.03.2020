package pwr.chessproject.models.functionalities;

import pwr.chessproject.game.Board;

import java.util.List;

/**
 * Default figure movement check functionality
 */
public interface Movable {
    /**
     * Checks if target position is even in the board
     * @param position The Figures current position
     * @param target The target position to move to
     * @return Value indicating if such movement is possible
     */
    boolean canMove(int position, int target, Board board);

    /**
     * Returns ArrayList&lt;Integer&gt; of fields that a figure can move into, including figures movement pattern and current board
     * @param position The Figures current position
     * @param board Current board on which figure exists
     * @return List of available fields
     */
    List<Integer> getAvailableFields(int position, Board board);
}

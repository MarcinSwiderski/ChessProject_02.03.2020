package pwr.chessproject.models.functionalities;

import pwr.chessproject.game.Board;

import java.util.List;

/**
 * Default figure movement check functionality
 */
public interface Movable {
    /**
     * Checks if you can move into target position
     * @param position The Figures current position
     * @param target The target position to move to
     * @param board Current board on which figure exists
     * @return Value indicating if you can move into target position
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

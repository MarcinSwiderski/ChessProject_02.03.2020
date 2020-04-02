package pwr.chessproject.models.functionalities;


import java.util.ArrayList;

/**
 * Default figure movement check functionality
 */
public interface IMoveable {
    /**
     * Checks if target position is even in the board
     * @param position The Figures current position
     * @param target The target position to move to
     * @return Value indicating if such movement is possible
     */
    boolean canMove(int position, int target);

    /**
     * Returns ArrayList<Integer> of fields that a figure can move into, including figures movement pattern and current board
     * @param position The Figures current position
     * @return List of available fields
     */
    ArrayList<Integer> getAvailableFields(int position);

}

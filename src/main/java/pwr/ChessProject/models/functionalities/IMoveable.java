package pwr.ChessProject.models.functionalities;

/**
 * Default figure movement check functionality
 */
public interface IMoveable {
    /**
     * Checks if target position is even in the board
     * @param position The Figure current position
     * @param target The target position to move to
     * @return Value indicating if such movement is possible
     */
    default boolean canMove(int position, int target) {
        return target >= 0 && target <= 63 && target != position;
    }
}

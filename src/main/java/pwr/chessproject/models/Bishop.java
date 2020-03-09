package pwr.chessproject.models;

import pwr.chessproject.models.functionalities.IMoveable;
import pwr.chessproject.models.functionalities.MovingStrategies;

public class Bishop extends Figure implements IMoveable {
    public Bishop(Player player) {
        super(player);
        this.figureType = FigureType.Bishop;
    }

    /**
     * Checks if you can move into target position
     * @param target The target position to move to
     * @return Value indicating if you can move into target position
     */
    @Override
    public boolean canMove(int position, int target) {
        return MovingStrategies.canMoveInRange(position, target) && MovingStrategies.canMoveDiagonal(position, target);
    }
}
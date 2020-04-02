package pwr.chessproject.models;

import pwr.chessproject.models.functionalities.IMoveable;
import pwr.chessproject.models.functionalities.MovingStrategies;

import java.util.ArrayList;

public class Tower extends Figure implements IMoveable {
    public Tower(Player player) {
        super(player);
        this.figureType = FigureType.Tower;
    }

    /**
     * Checks if you can move into target position
     * @param target The target position to move to
     * @return Value indicating if you can move into target position
     */
    @Override
    public boolean canMove(int position, int target) {
        return MovingStrategies.canMovePerpendicular(position, target);
    }

    @Override
    public ArrayList<Integer> getAvailableFields(int position) {
        return MovingStrategies.getFreePerpendicularFields(position);
    }
}
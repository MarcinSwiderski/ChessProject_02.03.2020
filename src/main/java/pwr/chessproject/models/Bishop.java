package pwr.chessproject.models;

import pwr.chessproject.models.functionalities.Movable;
import pwr.chessproject.models.functionalities.MovingStrategies;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Figure implements Movable {
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
        return MovingStrategies.canMoveDiagonal(position, target);
    }

    @Override
    public List<Integer> getAvailableFields(int position) {
        return MovingStrategies.getFreeDiagonalFields(position);
    }
}
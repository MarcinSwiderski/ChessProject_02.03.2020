package pwr.chessproject.models;

import pwr.chessproject.models.functionalities.Movable;
import pwr.chessproject.models.functionalities.MovingStrategies;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Figure implements Movable {
    public Queen(Player player) {
        super(player);
        this.figureType = FigureType.Queen;
    }

    /**
     * Checks if you can move into target position
     * @param target The target position to move to
     * @return Value indicating if you can move into target position
     */
    @Override
    public boolean canMove(int position, int target) {
        return MovingStrategies.canMovePerpendicular(position, target)  || MovingStrategies.canMoveDiagonal(position, target);
    }

    @Override
    public List<Integer> getAvailableFields(int position) {
        List<Integer> fields = new ArrayList<>();
        fields.addAll(MovingStrategies.getFreeDiagonalFields(position));
        fields.addAll(MovingStrategies.getFreePerpendicularFields(position));
        return fields;
    }
}
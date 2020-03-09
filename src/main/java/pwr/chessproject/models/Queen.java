package pwr.chessproject.models;

import pwr.chessproject.models.functionalities.IMoveDiagonal;
import pwr.chessproject.models.functionalities.IMovePerpendicular;

public class Queen extends Figure implements IMovePerpendicular, IMoveDiagonal {
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
        return IMovePerpendicular.super.canMove(position, target)  || IMoveDiagonal.super.canMove(position, target);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "player=" + player +
                '}';
    }
}
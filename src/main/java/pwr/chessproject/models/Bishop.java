package pwr.chessproject.models;

import pwr.chessproject.models.functionalities.IMoveDiagonal;

public class Bishop extends Figure implements IMoveDiagonal {
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
        return IMoveDiagonal.super.canMove(position, target);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "player=" + player +
                '}';
    }
}
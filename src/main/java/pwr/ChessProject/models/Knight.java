package pwr.ChessProject.models;

import pwr.ChessProject.models.functionalities.IMoveable;

public class Knight extends Figure implements IMoveable {
    public Knight(Player player) {
        super(player);
        this.figureType = FigureType.Knight;
    }

    /**
     * Checks if you can move into target position
     * @param target The target position to move to
     * @return Value indicating if you can move into target position
     */
    @Override
    public boolean canMove(int position, int target) {
        if (!IMoveable.super.canMove(position, target))
            return IMoveable.super.canMove(position, target);

        return false;
    }

    @Override
    public String toString() {
        return "Knight{" +
                "player=" + player +
                '}';
    }
}

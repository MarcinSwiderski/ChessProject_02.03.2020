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

        if (position == 0)
            return position + 10 == target || position + 17 == target;

        if (position == 7)
            return position + 6 == target || position + 15 == target;

        if (position == 56)
            return position - 15 == target || position - 6 == target;

        if (position == 63)
            return position - 10 == target || position - 17 == target;
        if (position == 1)
            return position + 15 == target || position + 10 == target || position + 17 == target;
        if (position > 1 && position < 6)
            return position + 6 == target || position+ 15 == target || position + 10 == target || position + 17 == target;
        if (position == 6)
            return position + 6 == target || position + 15 == target || position + 17 == target;

        if (position == 8)
            return position + 17 == target || position + 10 == target || position - 6 == target;

        if (position == 15)
            return position + 15 == target || position + 7 == target || position - 10 == target;

        if (position == 14)
            return position + 15 == target || position + 17 == target || position + 6 == target || position - 10 == target;

        if (position == 9)
            return position + 15 == target || position + 17 == target || position - 6 == target || position + 10 == target;

        if (position > 9 && position < 14)
            return position + 6 == target || position + 15 == target || position + 10 == target || position + 17 == target || position - 6 == target || position - 10 == target;

        return false;
    }

    @Override
    public String toString() {
        return "Knight{" +
                "player=" + player +
                '}';
    }
}

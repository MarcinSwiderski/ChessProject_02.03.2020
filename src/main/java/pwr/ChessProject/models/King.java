package pwr.ChessProject.models;

import pwr.ChessProject.board.Board;
import pwr.ChessProject.models.functionalities.IMoveable;

public class King extends Figure implements IMoveable {
    public King(Player player) {
        super(player);
        this.figureType = FigureType.King;
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

        if (position > 0 && position < 8)
            return position + 1 == target || position - 1 == target || position + 7 == target || position + 9 == target || position + 8 == target;

        if (position > 56 && position < 63)
            return position + 1 == target || position - 1 == target || position - 7 == target || position - 9 == target || position - 8 == target;

        if (position == 8 || position == 16 || position == 24 || position == 32 || position == 40 || position == 48)
            return position + 1 == target || position - 7 == target || position + 9 == target || position + 8 == target || position - 8 == target;

        if (position == 15 || position == 23 || position == 31 || position == 39 || position == 47 || position == 55)
            return position - 1 == target || position - 9 == target || position + 7 == target || position + 8 == target || position - 8 == target;

        if (position == 0)
            return position + 1 == target || position + 8 == target || position + 9 == target;

        if (position == 7)
            return position + 7 == target || position + 8 == target || position - 1 == target;

        if (position == 63)
            return position - 1 == target || position - 8 == target || position - 9 == target;

        if (position == 56)
            return position + 1 == target || position - 8 == target || position - 7 == target;

        return false;
    }

    @Override
    public String toString() {
        return "King{" +
                "player=" + player +
                '}';
    }
}
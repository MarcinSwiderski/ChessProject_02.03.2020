package pwr.ChessProject.models;

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

        if (player == Player.Top) {
            return position+8 == target || position+16 == target;
        }
        else {
            return position-8 == target || position-16 == target;
        }
    }

    @Override
    public String toString() {
        return "King{" +
                "player=" + player +
                '}';
    }
}
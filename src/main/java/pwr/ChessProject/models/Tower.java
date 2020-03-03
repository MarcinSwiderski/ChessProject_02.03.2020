package pwr.ChessProject.models;

import pwr.ChessProject.models.functionalities.IMoveDiagonal;

public class Tower extends Figure implements IMoveDiagonal {
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
        if (!IMoveDiagonal.super.canMove(position, target))
            return IMoveDiagonal.super.canMove(position, target);

        if (player == Player.Top) {
            return position+8 == target || position+16 == target;
        }
        else {
            return position-8 == target || position-16 == target;
        }
    }

    @Override
    public String toString() {
        return "Tower{" +
                "player=" + player +
                '}';
    }
}
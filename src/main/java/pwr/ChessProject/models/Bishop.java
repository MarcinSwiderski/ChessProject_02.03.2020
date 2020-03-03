package pwr.ChessProject.models;

import pwr.ChessProject.models.functionalities.IMovePerpendicular;

public class Bishop extends Figure implements IMovePerpendicular {
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
        if (!IMovePerpendicular.super.canMove(position, target))
            return IMovePerpendicular.super.canMove(position, target);

        if (player == Player.Top) {
            return position+8 == target || position+16 == target;
        }
        else {
            return position-8 == target || position-16 == target;
        }
    }

    @Override
    public String toString() {
        return "Bishop{" +
                "player=" + player +
                '}';
    }
}
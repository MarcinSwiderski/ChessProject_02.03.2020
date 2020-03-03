package pwr.ChessProject.models;

import pwr.ChessProject.models.functionalities.IMovable;

public class Pawn extends Figure implements IMovable {
    public Pawn(Player player) {
        super(player);
        this.figureType = FigureType.Pawn;
    }

    /**
     * Checks if you can move into target position
     * @param target The target position to move to
     * @return Value indicating if you can move into target position
     */
    @Override
    public boolean canMove(int position, int target) {
        if (!IMovable.super.canMove(position, target))
            return false;
        if (player == Player.Top) {
            return position+8 == target || position+16 == target;
        }
        else {
            return position-8 == target || position-16 == target;
        }
    }

    @Override
    public String toString() {
        return "Pawn{" +
                "player=" + player +
                '}';
    }
}

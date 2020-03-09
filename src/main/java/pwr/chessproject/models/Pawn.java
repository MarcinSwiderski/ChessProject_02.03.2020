package pwr.chessproject.models;

import static pwr.chessproject.board.Board.*;
import pwr.chessproject.models.functionalities.IMoveable;

public class Pawn extends Figure implements IMoveable {

    private boolean firstMove = true;

    public Pawn(Player player) {
        super(player);
        this.figureType = FigureType.Pawn;
    }

    public void afterFirstMove() {
        this.firstMove = false;
    }

    /**
     * Checks if you can move into target position
     * @param target The target position to move to
     * @return Value indicating if you can move into target position
     */
    @Override
    public boolean canMove(int position, int target) {
        if (!IMoveable.super.canMove(position, target))
            return false;
        
        if (!firstMove) {
            if (player == Player.Top) {
                return position + ROWS == target;
            }
            else {
                return position - ROWS == target;
            }
        }
        if (player == Player.Top) {
            return position+ ROWS == target || position+ 2*ROWS == target;
        }
        else {
            return position- ROWS == target || position- 2*ROWS == target;
        }

    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "player=" + player +
                '}';
    }
}

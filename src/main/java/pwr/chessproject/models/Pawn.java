package pwr.chessproject.models;

import static pwr.chessproject.game.Board.*;
import pwr.chessproject.models.functionalities.IMoveable;
import pwr.chessproject.models.functionalities.MovingStrategies;

public class Pawn extends Figure implements IMoveable {

    private boolean firstMoveIndicator = true;

    public Pawn(Player player) {
        super(player);
        this.figureType = FigureType.Pawn;
    }

    public void afterFirstMoveIndicator() {
        this.firstMoveIndicator = false;
    }
    public boolean getFirstMoveIndicator() {
        return firstMoveIndicator;
    }

    /**
     * Checks if you can move into target position
     * @param target The target position to move to
     * @return Value indicating if you can move into target position
     */
    @Override
    public boolean canMove(int position, int target) {
        if (!MovingStrategies.canMoveInRange(position, target))
            return false;

        //todo proper attacking

            if (player == Player.Top) {
                if (position + COLUMNS == target) {
                    Figure figureAtTarget = Grid[target];
                    return figureAtTarget == null || figureAtTarget.player == Player.Bottom;
                }
            }
            else {
                if (position - COLUMNS == target) {
                    Figure figureAtTarget = Grid[target];
                    return figureAtTarget == null || figureAtTarget.player == Player.Top;
                }
            }

        if (firstMoveIndicator) {
            if (player == Player.Top) {
                if (position+ 2*COLUMNS == target) {
                    Figure figureAtTarget = Grid[target];
                    return Grid[position+COLUMNS] == null && (figureAtTarget == null || figureAtTarget.player == Player.Bottom);
                }
            }
            else {
                if (position- 2*COLUMNS == target) {
                    Figure figureAtTarget = Grid[target];
                    return Grid[position-COLUMNS] == null && (figureAtTarget == null || figureAtTarget.player == Player.Top);
                }
            }
        }
        return false;
    }
}

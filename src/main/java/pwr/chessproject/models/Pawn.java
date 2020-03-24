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

            if (player == Player.Top) {
                if (position + COLUMNS == target)
                    return Grid[target] == null;
                else if (position + COLUMNS + 1  == target || position + COLUMNS - 1  == target) {
                    Figure targetFigure = Grid[target];
                    return targetFigure != null && targetFigure.player == Player.Bottom;
                }
            }
            else {
                if (position - COLUMNS == target)
                    return Grid[target] == null;
                else if (position - COLUMNS + 1  == target || position - COLUMNS - 1  == target) {
                    Figure targetFigure = Grid[target];
                    return targetFigure != null && targetFigure.player == Player.Top;
                }
            }

        if (firstMoveIndicator) {
            if (player == Player.Top) {
                if (position+ 2*COLUMNS == target)
                    return Grid[target] == null && Grid[position+COLUMNS] == null;
            }
            else {
                if (position- 2*COLUMNS == target)
                    return Grid[target] == null && Grid[position-COLUMNS] == null;
            }
        }
        return false;
    }
}

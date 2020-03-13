package pwr.chessproject.models;

import pwr.chessproject.models.functionalities.IMoveable;
import pwr.chessproject.models.functionalities.MovingStrategies;

import java.util.ArrayList;

import static pwr.chessproject.game.Board.*;

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
        if (!MovingStrategies.canMoveInRange(position, target))
            return false;

        /*if (!(Grid[position].player == player)) {
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
        }*/

        ArrayList<Integer> availableFields = new ArrayList<Integer>() {
            {
                add(position-COLUMNS);
                add(position-COLUMNS+1);
                add(position+1);
                add(position+COLUMNS+1);
                add(position+COLUMNS);
                add(position+COLUMNS-1);
                add(position-1);
                add(position-COLUMNS-1);
            }
        };

        if (position % COLUMNS == 0) {
            availableFields.remove(Integer.valueOf(position-COLUMNS-1));
            availableFields.remove(Integer.valueOf(position-1));
            availableFields.remove(Integer.valueOf(position+COLUMNS-1));
        }
        if (position % COLUMNS == COLUMNS-1) {
            availableFields.remove(Integer.valueOf(position+COLUMNS+1));
            availableFields.remove(Integer.valueOf(position+1));
            availableFields.remove(Integer.valueOf(position-COLUMNS+1));
        }
        if (position / COLUMNS == 0) {
            availableFields.remove(Integer.valueOf(position-COLUMNS-1));
            availableFields.remove(Integer.valueOf(position-COLUMNS));
            availableFields.remove(Integer.valueOf(position-COLUMNS+1));
        }
        if (position / COLUMNS == ROWS-1) {
            availableFields.remove(Integer.valueOf(position+COLUMNS-1));
            availableFields.remove(Integer.valueOf(position+COLUMNS));
            availableFields.remove(Integer.valueOf(position+COLUMNS+1));
        }

        if (availableFields.contains(target)) {
            if(Grid[target] != null) {
                return !(Grid[target].player == Grid[position].player);
            }
            else
                return true;
        }
        else
            return false;
    }
}
package pwr.chessproject.models;

import pwr.chessproject.models.functionalities.IMoveable;
import pwr.chessproject.models.functionalities.MovingStrategies;

import java.util.ArrayList;

import static pwr.chessproject.board.Board.*;

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
        if (!MovingStrategies.canMoveInRange(position, target))
            return false;

        ArrayList<Integer> availableFields = new ArrayList<Integer>() {
            {
                add(position-2*COLUMNS-1);
                add(position-2*COLUMNS+1);
                add(position+2-COLUMNS);
                add(position+2+COLUMNS);
                add(position+2*COLUMNS-1);
                add(position+2*COLUMNS+1);
                add(position-2-COLUMNS);
                add(position-2+COLUMNS);
            }
        };

        //left side
        if (position % COLUMNS <= 1) {
            availableFields.remove(Integer.valueOf(position-2+COLUMNS));
            availableFields.remove(Integer.valueOf(position-2-COLUMNS));
            if (position % COLUMNS == 0) {
                availableFields.remove(Integer.valueOf(position+2*COLUMNS-1));
                availableFields.remove(Integer.valueOf(position-2*COLUMNS-1));
            }
        }

        //Right side
        if (position % COLUMNS >= COLUMNS-2) {
            availableFields.remove(Integer.valueOf(position + 2 + COLUMNS));
            availableFields.remove(Integer.valueOf(position + 2 - COLUMNS));
            if (position % COLUMNS == COLUMNS-1) {
                availableFields.remove(Integer.valueOf(position + 2 * COLUMNS + 1));
                availableFields.remove(Integer.valueOf(position - 2 * COLUMNS + 1));
            }
        }

        //Top side
        if (position / COLUMNS <= 1) {
            availableFields.remove(Integer.valueOf(position - 2 * COLUMNS + 1));
            availableFields.remove(Integer.valueOf(position - 2 * COLUMNS - 1));
            if (position / COLUMNS == 0) {
                availableFields.remove(Integer.valueOf(position + 2 - COLUMNS));
                availableFields.remove(Integer.valueOf(position - 2 - COLUMNS));
            }
        }

        //Bot side
        if (position / COLUMNS >= ROWS-2) {
            availableFields.remove(Integer.valueOf(position + 2 * COLUMNS + 1));
            availableFields.remove(Integer.valueOf(position + 2 * COLUMNS - 1));
            if (position / COLUMNS == ROWS-1) {
                availableFields.remove(Integer.valueOf(position + 2 + COLUMNS));
                availableFields.remove(Integer.valueOf(position - 2 + COLUMNS));
            }
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

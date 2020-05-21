package pwr.chessproject.models;

import pwr.chessproject.models.functionalities.Movable;

import java.util.ArrayList;
import java.util.List;

import static pwr.chessproject.game.Board.*;

public class Knight extends Figure implements Movable {
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
        return getAvailableFields(position).contains(target);
    }

    @Override
    public List<Integer> getAvailableFields(int position) {
        List<Integer> availableFields = new ArrayList<Integer>() {
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

        availableFields.removeIf(field -> Grid[field] != null && Grid[field].player == player);

        return availableFields;
    }
}

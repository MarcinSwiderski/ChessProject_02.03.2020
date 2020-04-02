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
     * Maps every available field that king can move to
     * @param position Position to map
     * @return List of available fields
     */
    @Override
    public ArrayList<Integer> getAvailableFields(int position) {
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

        availableFields.removeIf(field -> Grid[field] != null && Grid[field].player == player);

        return availableFields;
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
}
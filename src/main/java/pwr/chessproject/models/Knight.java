package pwr.chessproject.models;

import pwr.chessproject.game.Board;

import java.util.ArrayList;
import java.util.List;

/**
 * Knight implementation moves using special +2 and then +1 pattern
 */
public class Knight extends Figure {


    public Knight(Player player) {
        super(player);
        this.figureType = FigureType.Knight;
    }

    /**
     * Maps every available field that knight can move to
     * @param position Position to map
     * @param board Current board on which figure exists
     * @return List of available fields
     */
    @Override
    public List<Integer> getAvailableFields(int position, Board board) {
        List<Integer> availableFields = new ArrayList<Integer>() {
            {
                add(position-2*board.getColumns()-1);
                add(position-2*board.getColumns()+1);
                add(position+2-board.getColumns());
                add(position+2+board.getColumns());
                add(position+2*board.getColumns()-1);
                add(position+2*board.getColumns()+1);
                add(position-2-board.getColumns());
                add(position-2+board.getColumns());
            }
        };

        //left side
        if (position % board.getColumns() <= 1) {
            availableFields.remove(Integer.valueOf(position-2+board.getColumns()));
            availableFields.remove(Integer.valueOf(position-2-board.getColumns()));
            if (position % board.getColumns() == 0) {
                availableFields.remove(Integer.valueOf(position+2*board.getColumns()-1));
                availableFields.remove(Integer.valueOf(position-2*board.getColumns()-1));
            }
        }

        //Right side
        if (position % board.getColumns() >= board.getColumns()-2) {
            availableFields.remove(Integer.valueOf(position + 2 + board.getColumns()));
            availableFields.remove(Integer.valueOf(position + 2 - board.getColumns()));
            if (position % board.getColumns() == board.getColumns()-1) {
                availableFields.remove(Integer.valueOf(position + 2 * board.getColumns() + 1));
                availableFields.remove(Integer.valueOf(position - 2 * board.getColumns() + 1));
            }
        }

        //Top side
        if (position / board.getColumns() <= 1) {
            availableFields.remove(Integer.valueOf(position - 2 * board.getColumns() + 1));
            availableFields.remove(Integer.valueOf(position - 2 * board.getColumns() - 1));
            if (position / board.getColumns() == 0) {
                availableFields.remove(Integer.valueOf(position + 2 - board.getColumns()));
                availableFields.remove(Integer.valueOf(position - 2 - board.getColumns()));
            }
        }

        //Bot side
        if (position / board.getColumns() >= board.getRows()-2) {
            availableFields.remove(Integer.valueOf(position + 2 * board.getColumns() + 1));
            availableFields.remove(Integer.valueOf(position + 2 * board.getColumns() - 1));
            if (position / board.getColumns() == board.getRows()-1) {
                availableFields.remove(Integer.valueOf(position + 2 + board.getColumns()));
                availableFields.remove(Integer.valueOf(position - 2 + board.getColumns()));
            }
        }

        availableFields.removeIf(field -> board.grid[field] != null && board.grid[field].player == player);

        return availableFields;
    }
}

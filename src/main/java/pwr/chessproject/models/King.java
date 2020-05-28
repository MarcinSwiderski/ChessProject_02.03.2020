package pwr.chessproject.models;

import pwr.chessproject.game.Board;

import java.util.ArrayList;
import java.util.List;

public class King extends Figure  {

    public King(Player player) {
        super(player);
        this.figureType = FigureType.King;
    }

    /**
     * Maps every available field that king can move to
     * @param position Position to map
     * @param board Current board on which figure exists
     * @return List of available fields
     */
    @Override
    public List<Integer> getAvailableFields(int position, Board board) {
        List<Integer> availableFields = new ArrayList<Integer>() {
            {
                add(position-board.getColumns());
                add(position-board.getColumns()+1);
                add(position+1);
                add(position+board.getColumns()+1);
                add(position+board.getColumns());
                add(position+board.getColumns()-1);
                add(position-1);
                add(position-board.getColumns()-1);
            }
        };

        if (position % board.getColumns() == 0) {
            availableFields.remove(Integer.valueOf(position-board.getColumns()-1));
            availableFields.remove(Integer.valueOf(position-1));
            availableFields.remove(Integer.valueOf(position+board.getColumns()-1));
        }
        if (position % board.getColumns() == board.getColumns()-1) {
            availableFields.remove(Integer.valueOf(position+board.getColumns()+1));
            availableFields.remove(Integer.valueOf(position+1));
            availableFields.remove(Integer.valueOf(position-board.getColumns()+1));
        }
        if (position / board.getColumns() == 0) {
            availableFields.remove(Integer.valueOf(position-board.getColumns()-1));
            availableFields.remove(Integer.valueOf(position-board.getColumns()));
            availableFields.remove(Integer.valueOf(position-board.getColumns()+1));
        }
        if (position / board.getColumns() == board.getRows()-1) {
            availableFields.remove(Integer.valueOf(position+board.getColumns()-1));
            availableFields.remove(Integer.valueOf(position+board.getColumns()));
            availableFields.remove(Integer.valueOf(position+board.getColumns()+1));
        }

        availableFields.removeIf(field -> board.grid[field] != null && board.grid[field].player == player);

        return availableFields;
    }
}
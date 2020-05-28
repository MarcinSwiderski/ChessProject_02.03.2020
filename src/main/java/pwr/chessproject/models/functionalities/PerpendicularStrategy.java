package pwr.chessproject.models.functionalities;

import pwr.chessproject.game.Board;
import pwr.chessproject.models.Figure;

import java.util.ArrayList;
import java.util.List;

public class PerpendicularStrategy {

    private final Board board;

    public PerpendicularStrategy(Board board) {
        this.board = board;
    }

    /**
     * @param position The position to evaluate
     * @return ArrayList&lt;Integer&gt; of perpendicular fields
     */
    public List<Integer> getFreePerpendicularFields(int position) {
        Figure.Player player =  board.grid[position].player;
        ArrayList<Integer> fields = new ArrayList<>();
        addTop(fields, position, player);
        addRight(fields, position, player);
        addBot(fields, position, player);
        addLeft(fields, position, player);

        return fields;
    }

    private void addTop (List<Integer> fields, int position, Figure.Player player) {
        for (int i = position-board.getColumns(); i >= 0 ; i -= board.getColumns()) {
            if ( board.grid[i] != null) {
                if ( board.grid[i].player != player)
                    fields.add(i);
                break;
            }
            fields.add(i);
        }
    }
    private void addRight (List<Integer> fields, int position, Figure.Player player) {
        for (int i = position+1; i % board.getColumns() != 0 && i < board.getArea(); i++) {
            if ( board.grid[i] != null) {
                if ( board.grid[i].player != player)
                    fields.add(i);
                break;
            }
            fields.add(i);
        }
    }
    private void addBot (List<Integer> fields, int position, Figure.Player player) {
        for (int i = position+board.getColumns(); i < board.getArea(); i += board.getColumns()) {
            if (board.grid[i] != null) {
                if (board.grid[i].player != player)
                    fields.add(i);
                break;
            }
            fields.add(i);
        }
    }
    private void addLeft (List<Integer> fields, int position, Figure.Player player) {
        for (int i = position-1; i % board.getColumns() != board.getColumns()-1 && i >= 0; i--) {
            if ( board.grid[i] != null) {
                if ( board.grid[i].player != player)
                    fields.add(i);
                break;
            }
            fields.add(i);
        }
    }
}

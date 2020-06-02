package pwr.chessproject.models.functionalities;

import pwr.chessproject.game.Board;
import pwr.chessproject.logger.Logger;
import pwr.chessproject.models.Figure;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides a perpendicular movement pattern depending on the board
 */
public class PerpendicularStrategy {

    private final Board board;

    /**
     * Constructs a strategy
     * @param board Current board used to define size of the board and state of the grid
     */
    public PerpendicularStrategy(Board board) {
        this.board = board;
    }

    /**
     * Returns list of fields on which figure could move if moving perpendicularly including moves that kill opponent's figures
     * @param position The position to evaluate
     * @return ArrayList&lt;Integer&gt; of diagonal fields
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

    /**
     * Ads free and killable fields on top from position
     * @param fields Reference to the list that will be updated
     * @param position Position to evaluate
     * @param player Player value of the figure at the position
     */
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
    /**
     * Ads free and killable fields on right from position
     * @param fields Reference to the list that will be updated
     * @param position Position to evaluate
     * @param player Player value of the figure at the position
     */
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
    /**
     * Ads free and killable fields on bottom from position
     * @param fields Reference to the list that will be updated
     * @param position Position to evaluate
     * @param player Player value of the figure at the position
     */
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
    /**
     * Ads free and killable fields on left from position
     * @param fields Reference to the list that will be updated
     * @param position Position to evaluate
     * @param player Player value of the figure at the position
     */
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

package pwr.chessproject.models.functionalities;

import pwr.chessproject.game.Board;
import pwr.chessproject.models.Figure;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides a diagonal movement pattern depending on the board
 */
public class DiagonalStrategy {

    private final Board board;

    /**
     * Constructs a strategy
     * @param board Current board used to define size of the board and state of the grid
     */
    public DiagonalStrategy(Board board) {
        this.board = board;
    }

    /**
     * Returns list of fields on which figure could move if moving diagonally including moves that kill opponent's figures
     * @param position The position to evaluate
     * @return ArrayList&lt;Integer&gt; of diagonal fields
     */
    public List<Integer> getFreeDiagonalFields(int position) {
        Figure.Player player = board.grid[position].player;
        ArrayList<Integer> fields = new ArrayList<>();
        addRightTop(fields, position, player);
        addRightBot(fields, position, player);
        addLeftBot(fields, position, player);
        addLeftTop(fields, position, player);

        return fields;
    }

    /**
     * Ads free and killable fields on right top from position
     * @param fields Reference to the list that will be updated
     * @param position Position to evaluate
     * @param player Player value of the figure at the position
     */
    private void addRightTop (List<Integer> fields, int position, Figure.Player player) {
        for (int i = position-board.getColumns()+1; i % board.getColumns() != 0 && i >= 0 ; i = i-board.getColumns()+1) {
            if ( board.grid[i] != null) {
                if ( board.grid[i].player != player)
                    fields.add(i);
                break;
            }
            fields.add(i);
        }
    }
    /**
     * Ads free and killable fields on right bottom from position
     * @param fields Reference to the list that will be updated
     * @param position Position to evaluate
     * @param player Player value of the figure at the position
     */
    private void addRightBot (List<Integer> fields, int position, Figure.Player player) {
        for (int i = position+board.getColumns()+1; i % board.getColumns() != 0 && i < board.getArea(); i = i+board.getColumns()+1) {
            if ( board.grid[i] != null) {
                if ( board.grid[i].player != player)
                    fields.add(i);
                break;
            }
            fields.add(i);
        }
    }
    /**
     * Ads free and killable fields on left top from position
     * @param fields Reference to the list that will be updated
     * @param position Position to evaluate
     * @param player Player value of the figure at the position
     */
    private void addLeftBot (ArrayList<Integer> fields, int position, Figure.Player player) {
        for (int i = position+board.getColumns()-1; i % board.getColumns() != board.getColumns()-1 && i < board.getArea(); i = i+board.getColumns()-1) {
            if ( board.grid[i] != null) {
                if ( board.grid[i].player != player)
                    fields.add(i);
                break;
            }
            fields.add(i);
        }
    }
    /**
     * Ads free and killable fields on left bottom from position
     * @param fields Reference to the list that will be updated
     * @param position Position to evaluate
     * @param player Player value of the figure at the position
     */
    private void addLeftTop (List<Integer> fields, int position, Figure.Player player) {
        for (int i = position-board.getColumns()-1; i % board.getColumns() != board.getColumns()-1 && i >= 0; i = i-board.getColumns()-1) {
            if ( board.grid[i] != null) {
                if ( board.grid[i].player != player)
                    fields.add(i);
                break;
            }
            fields.add(i);
        }
    }
}

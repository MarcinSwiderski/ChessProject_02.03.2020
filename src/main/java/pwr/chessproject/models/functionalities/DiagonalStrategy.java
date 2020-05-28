package pwr.chessproject.models.functionalities;

import pwr.chessproject.game.Board;
import pwr.chessproject.models.Figure;

import java.util.ArrayList;
import java.util.List;

public class DiagonalStrategy {

    private final Board board;

    public DiagonalStrategy(Board board) {
        this.board = board;
    }

    /**
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

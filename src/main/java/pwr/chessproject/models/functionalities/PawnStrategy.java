package pwr.chessproject.models.functionalities;

import pwr.chessproject.game.Board;
import pwr.chessproject.models.Figure;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides a pawn-like movement pattern depending on the board
 */
public class PawnStrategy {

    private final Board board;

    /**
     * Constructs a strategy
     * @param board Current board used to define size of the board and state of the grid
     */
    public PawnStrategy(Board board) {
        this.board = board;
    }

    /**
     * Returns list of fields on which figure could move if moving like a pawn including moves that kill opponent's figures and first move bonus
     * @param position The position to evaluate
     * @param firstMoveIndicator Value indicating whether or not evaluate first move bonus
     * @return ArrayList&lt;Integer&gt; of diagonal fields
     */
    public List<Integer> getAvailableFields(int position, Boolean firstMoveIndicator) {
        Figure.Player player = board.grid[position].player;
        List<Integer> fields = new ArrayList<>();

        if (player == Figure.Player.Top && position + board.getColumns() < board.getArea()) {
            if (position % board.getColumns() != 0 && board.grid[position + board.getColumns() - 1] != null && board.grid[position + board.getColumns() - 1].player != player)
                fields.add(position + board.getColumns() - 1);
            if (board.grid[position + board.getColumns()] == null)
                fields.add(position + board.getColumns());
            if (position % board.getColumns() != board.getColumns() - 1 && board.grid[position + board.getColumns() + 1] != null && board.grid[position + board.getColumns() + 1].player != player)
                fields.add(position + board.getColumns() + 1);
        } else if (player == Figure.Player.Bottom && position - board.getColumns() >= 0) {
            if (position % board.getColumns() != 0 && board.grid[position - board.getColumns() - 1] != null && board.grid[position - board.getColumns() - 1].player != player)
                fields.add(position - board.getColumns() - 1);
            if (board.grid[position - board.getColumns()] == null)
                fields.add(position - board.getColumns());
            if (position % board.getColumns() != board.getColumns() - 1 && board.grid[position - board.getColumns() + 1] != null && board.grid[position - board.getColumns() + 1].player != player)
                fields.add(position - board.getColumns() + 1);
        }

        if (firstMoveIndicator) {
            if (player == Figure.Player.Top && position + 2 * board.getColumns() < board.getArea()) {
                if (board.grid[position + board.getColumns()] == null && board.grid[position + 2 * board.getColumns()] == null)
                    fields.add(position + 2 * board.getColumns());
            }
            if (player == Figure.Player.Bottom && position - 2 * board.getColumns() < board.getArea())
                if (board.grid[position - board.getColumns()] == null && board.grid[position - 2 * board.getColumns()] == null)
                    fields.add(position - 2 * board.getColumns());
        }

        return fields;
    }
}

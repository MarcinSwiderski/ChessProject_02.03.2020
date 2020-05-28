package pwr.chessproject.models.functionalities;

import pwr.chessproject.game.Board;
import pwr.chessproject.models.Figure;

import java.util.ArrayList;
import java.util.List;

public class PawnStrategy {

    private final Board board;

    public PawnStrategy(Board board) {
        this.board = board;
    }

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

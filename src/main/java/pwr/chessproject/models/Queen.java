package pwr.chessproject.models;

import pwr.chessproject.game.Board;
import pwr.chessproject.models.functionalities.DiagonalStrategy;
import pwr.chessproject.models.functionalities.PerpendicularStrategy;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Figure {
    public Queen(Player player) {
        super(player);
        this.figureType = FigureType.Queen;
    }

    /**
     * Returns ArrayList&lt;Integer&gt; of fields on perpendicular and diagonal lines crossing at specified position that a figure can move into, includes current board
     * @param position The Figures current position
     * @param board Current board on which figure exists
     * @return List of available fields
     */
    @Override
    public List<Integer> getAvailableFields(int position, Board board) {
        PerpendicularStrategy perpendicularStrategy = new PerpendicularStrategy(board);
        DiagonalStrategy diagonalStrategy = new DiagonalStrategy(board);
        List<Integer> fields = new ArrayList<>();
        fields.addAll(diagonalStrategy.getFreeDiagonalFields(position));
        fields.addAll(perpendicularStrategy.getFreePerpendicularFields(position));
        return fields;
    }
}
package pwr.chessproject.models;

import pwr.chessproject.game.Board;
import pwr.chessproject.models.functionalities.DiagonalStrategy;

import java.util.List;

/**
 * Bishop implementation moves only diagonally
 */
public class Bishop extends Figure {
    public Bishop(Player player) {
        super(player);
        this.figureType = FigureType.Bishop;
    }

    /**
     * Returns ArrayList&lt;Integer&gt; of fields on diagonal lines crossing at specified position that a figure can move into, includes current board
     * @param position The Figures current position
     * @param board Current board on which figure exists
     * @return List of available fields
     */
    @Override
    public List<Integer> getAvailableFields(int position, Board board) {
        DiagonalStrategy strategy = new DiagonalStrategy(board);
        return strategy.getFreeDiagonalFields(position);
    }
}
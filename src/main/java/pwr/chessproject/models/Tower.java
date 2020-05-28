package pwr.chessproject.models;

import pwr.chessproject.game.Board;
import pwr.chessproject.models.functionalities.PerpendicularStrategy;

import java.util.List;

public class Tower extends Figure {
    public Tower(Player player) {
        super(player);
        this.figureType = FigureType.Tower;
    }

    /**
     * Returns ArrayList&lt;Integer&gt; of fields on perpendicular lines crossing at specified position that a figure can move into, includes current board
     * @param position The Figures current position
     * @param board Current board on which figure exists
     * @return List of available fields
     */
    @Override
    public List<Integer> getAvailableFields(int position, Board board) {
        PerpendicularStrategy strategy = new PerpendicularStrategy(board);
        return strategy.getFreePerpendicularFields(position);
    }
}
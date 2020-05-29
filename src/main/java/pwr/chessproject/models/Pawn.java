package pwr.chessproject.models;

import pwr.chessproject.game.Board;
import pwr.chessproject.models.functionalities.PawnStrategy;

import java.util.List;

/**
 * Pawn implementation moves one forward (or two if that's the first move) depending on the player value
 */
public class Pawn extends Figure  {

    /**
     * Indicates whether or not the pawn has ever moved
     */
    private boolean firstMoveIndicator = true;

    public Pawn(Player player) {
        super(player);
        this.figureType = FigureType.Pawn;
    }

    /**
     * Changes firstMoveIndicator to false. There is no way to reverse it
     */
    public void afterFirstMoveIndicator() {
        this.firstMoveIndicator = false;
    }
    public boolean getFirstMoveIndicator() {
        return firstMoveIndicator;
    }

    /**
     * Maps every available field that pawn can move to
     * @param position Position to map
     * @param board Current board on which figure exists
     * @return List of available fields
     */
    @Override
    public List<Integer> getAvailableFields(int position, Board board) {
        PawnStrategy pawnStrategy = new PawnStrategy(board);
        return pawnStrategy.getAvailableFields(position, firstMoveIndicator);
    }
}

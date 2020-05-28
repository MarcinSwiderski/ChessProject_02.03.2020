package pwr.chessproject.models;

import pwr.chessproject.game.Board;
import pwr.chessproject.models.functionalities.PawnStrategy;

import java.util.List;

public class Pawn extends Figure  {

    private boolean firstMoveIndicator = true;

    public Pawn(Player player) {
        super(player);
        this.figureType = FigureType.Pawn;
    }

    public void afterFirstMoveIndicator() {
        this.firstMoveIndicator = false;
    }
    public boolean getFirstMoveIndicator() {
        return firstMoveIndicator;
    }

    @Override
    public List<Integer> getAvailableFields(int position, Board board) {
        PawnStrategy pawnStrategy = new PawnStrategy(board);
        return pawnStrategy.getAvailableFields(position, firstMoveIndicator);
    }

    @Override
    public Figure clone() throws CloneNotSupportedException {
        Pawn pawn = (Pawn)super.clone();
        if (!this.getFirstMoveIndicator())
            pawn.afterFirstMoveIndicator();
        return pawn;
    }
}

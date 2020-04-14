package pwr.chessproject.models;

import static pwr.chessproject.game.Board.*;
import pwr.chessproject.models.functionalities.IMoveable;
import pwr.chessproject.models.functionalities.MovingStrategies;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class Pawn extends Figure implements IMoveable {

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

    /**
     * Checks if you can move into target position
     * @param target The target position to move to
     * @return Value indicating if you can move into target position
     */
    @Override
    public boolean canMove(int position, int target) {
        return getAvailableFields(position).contains(target);
    }

    @Override
    public ArrayList<Integer> getAvailableFields(int position) {
        Figure.Player player = Grid[position].player;
        ArrayList<Integer> fields = new ArrayList<>();

        if (player == Player.Top && position + COLUMNS < AREA) {
            if (position % COLUMNS != 0 && Grid[position + COLUMNS - 1] != null && Grid[position + COLUMNS - 1].player != player)
                fields.add(position + COLUMNS - 1);
            if (Grid[position + COLUMNS] == null)
                fields.add(position + COLUMNS);
            if (position % COLUMNS != COLUMNS - 1 && Grid[position + COLUMNS + 1] != null && Grid[position + COLUMNS + 1].player != player)
                fields.add(position + COLUMNS + 1);
        } else if (player == Player.Bottom && position - COLUMNS >= 0) {
            if (position % COLUMNS != 0 && Grid[position - COLUMNS - 1] != null && Grid[position - COLUMNS - 1].player != player)
                fields.add(position - COLUMNS - 1);
            if (Grid[position - COLUMNS] == null)
                fields.add(position - COLUMNS);
            if (position % COLUMNS != COLUMNS - 1 && Grid[position - COLUMNS + 1] != null && Grid[position - COLUMNS + 1].player != player)
                fields.add(position - COLUMNS + 1);
        }

        if (firstMoveIndicator) {
            if (player == Player.Top && position + 2 * COLUMNS < AREA) {
                if (Grid[position + COLUMNS] == null && Grid[position + 2 * COLUMNS] == null)
                    fields.add(position + 2 * COLUMNS);
            }
            if (player == Player.Bottom && position - 2 * COLUMNS < AREA)
                if (Grid[position - COLUMNS] == null && Grid[position - 2 * COLUMNS] == null)
                    fields.add(position - 2 * COLUMNS);
        }

        return fields;
    }
}

package pwr.chessproject.models.functionalities;

import pwr.chessproject.models.Figure;

import java.util.ArrayList;
import java.util.List;

import static pwr.chessproject.game.Board.*;

/**
 * Contains common moving strategies
 */
public abstract class MovingStrategies {

    /**
     * Checks if target position is even in the board
     * @param position The Figure current position
     * @param target The target position to move to
     * @return Value indicating if such movement is possible
     */
    @Deprecated
    public static boolean canMoveInRange(int position, int target) {
        return target >= 0 && target <= AREA - 1 && target != position;
    }

    /**
     * Checks if target position is on one of two diagonal lines crossing each other in selected figure position
     * @param position The Figure current position
     * @return Value indicating if such movement is possible
     */
    public static boolean canMoveDiagonal(int position, int target) {
        return getFreeDiagonalFields(position).contains(target);
    }


    /**
     * @param position The position to evaluate
     * @return ArrayList&lt;Integer&gt; of unobstructed diagonal fields
     */
    public static List<Integer> getFreeDiagonalFields(int position) {
        Figure.Player player = Grid[position].player;
        ArrayList<Integer> fields = new ArrayList<>();
        addRightTop(fields, position, player);
        addRightBot(fields, position, player);
        addLeftBot(fields, position, player);
        addLeftTop(fields, position, player);

        return fields;
    }

    private static void addRightTop (List<Integer> fields, int position, Figure.Player player) {
        for (int i = position-COLUMNS+1; i % COLUMNS != 0 && i >= 0 ; i = i-COLUMNS+1) {
            if (Grid[i] != null) {
                if (Grid[i].player != player)
                    fields.add(i);
                break;
            }
            fields.add(i);
        }
    }
    private static void addRightBot (List<Integer> fields, int position, Figure.Player player) {
        for (int i = position+COLUMNS+1; i % COLUMNS != 0 && i < AREA; i = i+COLUMNS+1) {
            if (Grid[i] != null) {
                if (Grid[i].player != player)
                    fields.add(i);
                break;
            }
            fields.add(i);
        }
    }
    private static void addLeftBot (ArrayList<Integer> fields, int position, Figure.Player player) {
        for (int i = position+COLUMNS-1; i % COLUMNS != COLUMNS-1 && i < AREA; i = i+COLUMNS-1) {
            if (Grid[i] != null) {
                if (Grid[i].player != player)
                    fields.add(i);
                break;
            }
            fields.add(i);
        }
    }
    private static void addLeftTop (List<Integer> fields, int position, Figure.Player player) {
        for (int i = position-COLUMNS-1; i % COLUMNS != COLUMNS-1 && i >= 0; i = i-COLUMNS-1) {
            if (Grid[i] != null) {
                if (Grid[i].player != player)
                    fields.add(i);
                break;
            }
            fields.add(i);
        }
    }

    /**
     * Checks if target position is on one of two perpendicular lines crossing each other in selected figure position
     * @param position The Figure current position
     * @param target The target position to move to
     * @return Value indicating if such movement is possible
     */
    public static boolean canMovePerpendicular(int position, int target) {
        return getFreePerpendicularFields(position).contains(target);
    }

    /**
     * @param position The position to evaluate
     * @return ArrayList&lt;Integer&gt; of unobstructed perpendicular fields
     */
    public static List<Integer> getFreePerpendicularFields(int position) {
        Figure.Player player = Grid[position].player;
        ArrayList<Integer> fields = new ArrayList<>();
        addTop(fields, position, player);
        addRight(fields, position, player);
        addBot(fields, position, player);
        addLeft(fields, position, player);

        return fields;
    }

    private static void addTop (List<Integer> fields, int position, Figure.Player player) {
        for (int i = position-COLUMNS; i >= 0 ; i -= COLUMNS) {
            if (Grid[i] != null) {
                if (Grid[i].player != player)
                    fields.add(i);
                break;
            }
            fields.add(i);
        }
    }
    private static void addRight (List<Integer> fields, int position, Figure.Player player) {
        for (int i = position+1; i % COLUMNS != 0 && i < AREA; i++) {
            if (Grid[i] != null) {
                if (Grid[i].player != player)
                    fields.add(i);
                break;
            }
            fields.add(i);
        }
    }
    private static void addBot (List<Integer> fields, int position, Figure.Player player) {
        for (int i = position+COLUMNS; i < AREA; i += COLUMNS) {
            if (Grid[i] != null) {
                if (Grid[i].player != player)
                    fields.add(i);
                break;
            }
            fields.add(i);
        }
    }
    private static void addLeft (List<Integer> fields, int position, Figure.Player player) {
        for (int i = position-1; i % COLUMNS != COLUMNS-1 && i >= 0; i--) {
            if (Grid[i] != null) {
                if (Grid[i].player != player)
                    fields.add(i);
                break;
            }
            fields.add(i);
        }
    }

}

package pwr.chessproject.models.functionalities;

import pwr.chessproject.models.Figure;

import static pwr.chessproject.board.Board.*;

/**
 * Contains common moving strategies
 */
public final class MovingStrategies {

    /**
     * Checks if target position is even in the board
     * @param position The Figure current position
     * @param target The target position to move to
     * @return Value indicating if such movement is possible
     */
    public static boolean canMoveInRange(int position, int target) {
        return target >= 0 && target <= AREA - 1 && target != position;
    }

    /**
     * Checks if target position is on one of two diagonal lines crossing each other in selected figure position
     * @param position The Figure current position
     * @param target The target position to move to
     * @return Value indicating if such movement is possible
     */
    public static boolean canMoveDiagonal(int position, int target) {

        Figure.Player player = Grid[position].player;

        //P1(x1,y1) and P2(x2,y2) defines a line containing movement vector
        int x1 = position / ROWS;
        int y1 = position % COLUMNS;
        int x2 = target / ROWS;
        int y2 = target % COLUMNS;
        //preventing dividing by '0'
        if (x1 == x2)
            return false;

        //calculating 'a', parameter of 'y = ax + b'
        double a = (double)(y1-y2)/(double)(x1-x2);

        if (a == 1) {
            if(target < position)
                while(target != position) {
                    position -= COLUMNS+1;
                    if (Grid[position] == null)
                        continue;
                    else {
                        if (Grid[position].player == player)
                            return false;
                        else
                            return target == position;
                    }
                }
            else // target > position
                while(target != position) {
                    position += COLUMNS+1;
                    if (Grid[position] == null)
                        continue;
                    else {
                        if (Grid[position].player == player)
                            return false;
                        else
                            return target == position;
                    }
                }
            return true;
        }
        else if (a == -1) {
            if(target < position)
                while(target != position) {
                    position -= COLUMNS-1;
                    if (Grid[position] == null)
                        continue;
                    else {
                        if (Grid[position].player == player)
                            return false;
                        else
                            return target == position;
                    }
                }
            else // target > position
                while(target != position) {
                    position += COLUMNS-1;
                    if (Grid[position] == null)
                        continue;
                    else {
                        if (Grid[position].player == player)
                            return false;
                        else
                            return target == position;
                    }
                }
            return true;
        }
        else
            return false;
    }

    /**
     * Checks if target position is on one of two perpendicular lines crossing each other in selected figure position
     * @param position The Figure current position
     * @param target The target position to move to
     * @return Value indicating if such movement is possible
     */
    public static boolean canMovePerpendicular(int position, int target) {
        Figure.Player player = Grid[position].player;
        //same column
        if (target % 8 == position % 8) {
            if(target < position)
                while(target != position) {
                    position -= 8;
                    if (Grid[position] == null)
                        continue;
                    else {
                        if (Grid[position].player == player)
                            return false;
                        else
                            return target == position;
                    }
                }
            else // target > position
                while(target != position) {
                    position += 8;
                    if (Grid[position] == null)
                        continue;
                    else {
                        if (Grid[position].player == player)
                            return false;
                        else
                            return target == position;
                    }
                }
            return true;
        }
        else if (target / 8 == position / 8) {
            if(target < position)
                while(target != position) {
                    position -= 1;
                    if (Grid[position] == null)
                        continue;
                    else {
                        if (Grid[position].player == player)
                            return false;
                        else
                            return target == position;
                    }
                }
            else // target > position
                while(target != position) {
                    position += 1;
                    if (Grid[position] == null)
                        continue;
                    else {
                        if (Grid[position].player == player)
                            return false;
                        else
                            return target == position;
                    }
                }
            return true;
        }
        else
            return false;
    }
}

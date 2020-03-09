package pwr.chessproject.models.functionalities;

import pwr.chessproject.models.Figure;

import static pwr.chessproject.board.Board.*;

/**
 * Diagonal figure movement check functionality
 */
public interface IMoveDiagonal extends IMoveable {

    /**
     * Checks if target position is on one of two diagonal lines crossing each other in selected figure position
     * @param position The Figure current position
     * @param target The target position to move to
     * @return Value indicating if such movement is possible
     */
    @Override
    default boolean canMove(int position, int target) {
        if (!IMoveable.super.canMove(position, target))
            return false;
        Figure.Player player = Grid[position].player;

        //P1(x1,y1) and P2(x2,y2) defines a line containing movement vector
        int x1 = position / 8;
        int y1 = position % 8;
        int x2 = target / 8;
        int y2 = target % 8;
        //preventing dividing by '0'
        if (x1 == x2)
            return false;

        //calculating 'a', parameter of 'y = ax + b'
        double a = (double)(y1-y2)/(double)(x1-x2);

        if (a == 1) {
            if(target < position)
                while(target != position) {
                    position -= 9;
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
                    position += 9;
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
                    position -= 7;
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
                    position += 7;
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

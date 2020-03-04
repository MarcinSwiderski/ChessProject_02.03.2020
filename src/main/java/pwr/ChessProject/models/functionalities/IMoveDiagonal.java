package pwr.ChessProject.models.functionalities;

import pwr.ChessProject.board.Board;
import pwr.ChessProject.models.Figure;

public interface IMoveDiagonal extends IMoveable {

    @Override
    default boolean canMove(int target, int position) {
        if (!IMoveable.super.canMove(position, target))
            return false;
        Figure.Player player = Board.Grid[position].player;

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
                    if (Board.Grid[position] == null)
                        continue;
                    else
                        return false;
                }
            else // target > position
                while(target != position) {
                    position += 9;
                    if (Board.Grid[position] == null)
                        continue;
                    else
                        return false;
                }
            return true;
        }
        else if (a == -1) {
            if(target < position)
                while(target != position) {
                    position -= 7;
                    if (Board.Grid[position] == null)
                        continue;
                    else
                        return false;
                }
            else // target > position
                while(target != position) {
                    position += 7;
                    if (Board.Grid[position] == null)
                        continue;
                    else
                        return false;
                }
            return true;
        }
        else
            return false;
    }
}

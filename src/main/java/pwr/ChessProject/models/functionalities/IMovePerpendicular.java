package pwr.ChessProject.models.functionalities;

import pwr.ChessProject.board.Board;
import pwr.ChessProject.models.Figure;

public interface IMovePerpendicular extends IMoveable {

    @Override
    default boolean canMove(int target, int position) {
        if (!IMoveable.super.canMove(position, target))
            return false;
        Figure.Player player = Board.Grid[position].player;
        if (target % 8 == position % 8)
            if(target < position)
                while(target != position) {
                    position -= 8;
                    if (Board.Grid[position] == null)
                        continue;
                    else
                        return false;
                }
            else // target > position
                while(target != position) {
                    position += 8;
                    if (Board.Grid[position] == null)
                        continue;
                    else
                        return false;
                }
        if (target / 8 == position / 8)
            if(target < position)
                while(target != position) {
                    position -= 1;
                    if (Board.Grid[position] == null)
                        continue;
                    else
                        return false;
                }
            else // target > position
                while(target != position) {
                    position += 1;
                    if (Board.Grid[position] == null)
                        continue;
                    else
                        return false;
                }
        return true;
    }
}

package pwr.ChessProject.models.functionalities;

import pwr.ChessProject.board.Board;
import pwr.ChessProject.models.Figure;

/**
 * Up and down or left and right figure movement check functionality
 */
public interface IMovePerpendicular extends IMoveable {
    /**
     * Checks if target position is on one of two perpendicular lines crossing each other in selected figure position
     * @param position The Figure current position
     * @param target The target position to move to
     * @return Value indicating if such movement is possible
     */
    @Override
    default boolean canMove(int position, int target) {
        if (!IMoveable.super.canMove(position, target))
            return false;
        Figure.Player player = Board.Grid[position].player;
        if (target % 8 == position % 8) {
            if(target < position)
                while(target != position) {
                    position -= 8;
                    if (Board.Grid[position] == null)
                        continue;
                    else {
                        if (Board.Grid[position].player == player)
                            return false;
                        else
                            return target == position;
                    }
                }
            else // target > position
                while(target != position) {
                    position += 8;
                    if (Board.Grid[position] == null)
                        continue;
                    else {
                        if (Board.Grid[position].player == player)
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
                    if (Board.Grid[position] == null)
                        continue;
                    else {
                        if (Board.Grid[position].player == player)
                            return false;
                        else
                            return target == position;
                    }
                }
            else // target > position
                while(target != position) {
                    position += 1;
                    if (Board.Grid[position] == null)
                        continue;
                    else {
                        if (Board.Grid[position].player == player)
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

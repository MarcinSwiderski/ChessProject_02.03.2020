package pwr.ChessProject.models.functionalities;

public interface IMoveDiagonal extends IMoveable {

    @Override
    default boolean canMove(int target, int position) {
        if (!IMoveable.super.canMove(position, target))
            return false;
        return true;
    }
}

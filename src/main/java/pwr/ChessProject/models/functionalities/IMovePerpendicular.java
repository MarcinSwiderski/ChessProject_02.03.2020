package pwr.ChessProject.models.functionalities;

public interface IMovePerpendicular extends IMovable {

    @Override
    default boolean canMove(int target, int position) {
        if (!IMovable.super.canMove(position, target))
            return false;
        return true;
    }
}

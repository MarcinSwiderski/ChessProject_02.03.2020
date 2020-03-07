package pwr.ChessProject.models;

import pwr.ChessProject.models.functionalities.IMoveable;

public class Knight extends Figure implements IMoveable {
    public Knight(Player player) {
        super(player);
        this.figureType = FigureType.Knight;
    }

    /**
     * Checks if you can move into target position
     * @param target The target position to move to
     * @return Value indicating if you can move into target position
     */
    @Override
    public boolean canMove(int position, int target) {
        if (!IMoveable.super.canMove(position, target))
            return IMoveable.super.canMove(position, target);


        // Nie wiem jak zrobiłeś tablicę, nie widzę zmiennej X i Y,
        // Piszę same komen
        /*
        switch(position)
        {

            case (10):
                return position + 6 == target || position + 15 == target || position + 10 == target || position + 17 == target || position - 6 == target || position - 10 == target || position - 15 == target || position - 17 == target;
            case ( 5 / " X = 1, Y (3,6) "  ):
                return position - 15 == target || position - 6 == target || position + 10 == target || position + 17 == target;

            case ( 8" X = 2, Y (3,6) "  ):
                return position + 15 == target || position + 10 == target || position + 17 == target || position - 6 == target || position - 15 == target || position - 17 == target;
            break;
            case ( 7" X = 8 , Y (3,6) "  ):
                return position + 15 == target || position + 6 == target || position - 10 == target || position - 17 == target;
            break;
            case ( 6 " X = 8 " ):

                break;
        }

         */
        return true;
    }


    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "player=" + player +
                '}';
    }
}

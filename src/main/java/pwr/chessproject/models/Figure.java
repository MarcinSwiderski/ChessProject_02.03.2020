package pwr.chessproject.models;

import pwr.chessproject.game.Board;
import pwr.chessproject.models.functionalities.Movable;

/**
 * Abstract class containing universal information about every figure
 */
public abstract class Figure implements Cloneable, Movable {
    public enum FigureType {
        Pawn, Tower, Knight, Bishop, Queen, King
    }
    public enum Player {
        Bottom, Top
    }

    public Player player;
    public FigureType figureType;

    /**
     * Constructor to call for every figure, setting info about player
     * @param player The Player enum type
     */
    public Figure(Player player) {
        this.player = player;
    }

    /**
     * Checks if you can move into target position
     * @param position The Figures current position
     * @param target The target position to move to
     * @param board Current board on which figure exists
     * @return Value indicating if you can move into target position
     */
    @Override
    public boolean canMove(int position, int target, Board board) {
        return this.getAvailableFields(position, board).contains(target);
    }

    @Override
    public Figure clone() throws CloneNotSupportedException {
        return (Figure) super.clone();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "player=" + player +
                '}';
    }
}

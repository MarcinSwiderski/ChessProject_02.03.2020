package pwr.chessproject.models;

/**
 * Abstract class containing universal information about every figure
 */
public abstract class Figure {
    public enum FigureType {
        Pawn, Tower, Knight, Bishop, Queen, King
    }
    public enum Player {
        Bottom, Top
    }

    public Player player;
    public FigureType figureType;

    /**
     * Constructor to call for every figure setting info about player and starting position
     * @param player The Player enum type
     */
    public Figure(Player player) {
        this.player = player;
    }
}

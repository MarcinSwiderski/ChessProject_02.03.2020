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

    @Override
    public Figure clone() {
        Figure figure;
        switch (this.figureType) {
            case King: figure = new King(player);
                break;
            case Queen: figure = new Queen(player);
                break;
            case Bishop: figure = new Bishop(player);
                break;
            case Knight: figure = new Knight(player);
                break;
            case Tower: figure = new Tower(player);
                break;
            case Pawn: figure = new Pawn(player);
                break;
            default:
                figure = null;
        }
        if (this instanceof Pawn)
            if (!((Pawn) this).getFirstMoveIndicator())
                ((Pawn)figure).afterFirstMoveIndicator();

         return figure;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "player=" + player +
                '}';
    }
}

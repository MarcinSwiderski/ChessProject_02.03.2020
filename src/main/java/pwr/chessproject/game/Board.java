package pwr.chessproject.game;

import pwr.chessproject.frame.TranslateCords;
import pwr.chessproject.models.Figure;
import pwr.chessproject.models.Figure.Player;
import pwr.chessproject.models.King;
import pwr.chessproject.models.Pawn;
import pwr.chessproject.models.functionalities.Movable;
import pwr.chessproject.models.functionalities.NotMoveableException;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;

import static pwr.chessproject.models.Figure.Player.Bottom;
import static pwr.chessproject.models.Figure.Player.Top;

public class Board implements Cloneable {
    private final int rows;
    private final int columns;
    private final int area;
    public final int KING_TOP_STARTING_POINT = 4;
    public final int KING_BOT_STARTING_POINT = 60;

    /**
     * Contains information about kings current positions, allowing for dynamic game situation check
     */
    private Map<Player, Integer> kingPosition = new Hashtable<Player, Integer>() {
        {
            put(Top, KING_TOP_STARTING_POINT);
            put(Bottom, KING_BOT_STARTING_POINT);
        }
    };

    public Figure[] grid;

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.area = rows * columns;
        this.grid = new Figure[area];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getArea() {
        return area;
    }

    int getKingPosition(Player player) {
        return kingPosition.get(player);
    }

    void setKingPosition(Player player, int target) {
        this.kingPosition.replace(player, target);
    }

    /**
     * Fills the Grid with null's
     */
    public void clearBoard() {
        Arrays.fill(grid, null);
    }

    /**
     * Checks if position is in range and whether there is a figure
     * @param position The Figure current position
     * @throws NullPointerException     - When there is null at the selected position
     * @throws IllegalArgumentException - When position is outside of the board
     */
    public void checkPosition(int position) throws NullPointerException, IllegalArgumentException {
        if (position < 0 || position > area - 1)
            throw new IllegalArgumentException("Can not select figure outside of the board");
        if (grid[position] == null) {
            TranslateCords translateCords = new TranslateCords(this);
            throw new NullPointerException("There is no figure at the selected position: " + translateCords.translateIntCordToString(position));
        }
    }

    /**
     * Moves figure from position to target if figures rules allow it
     *
     * @param position The Figure current position
     * @param target   The target position to move to
     * @throws NotMoveableException     When figures movement rules do not allow to move it into specified target
     * @throws NullPointerException     When there is null at the selected position
     * @throws IllegalArgumentException When position is outside of the board
     */
    public void checkAndMove(int position, int target) throws NotMoveableException, NullPointerException, IllegalArgumentException {
        checkPosition(position);
        Movable selectedFigure = grid[position];
        if (selectedFigure.canMove(position, target, this)) {
            moveFigure(position, target);
        } else {
            TranslateCords translateCords = new TranslateCords(this);
            throw new NotMoveableException(translateCords.translateIntCordToString(position), translateCords.translateIntCordToString(target), grid[position]);
        }
    }

    /**
     * Tries to moves figure from position to target regardless figures movement rules; takes care of special pawn's and king's rules
     *
     * @param position The Figure current position
     * @param target   The target position to move to
     * @throws NullPointerException     When there is null at the selected position
     * @throws IllegalArgumentException When position is outside of the board
     */
    public void moveFigure(int position, int target) throws NullPointerException, IllegalArgumentException {
        Figure selectedFigure = grid[position];
        grid[target] = grid[position];
        grid[position] = null;
        if (selectedFigure instanceof Pawn) {
            Pawn pawn = (Pawn) selectedFigure;
            if (pawn.getFirstMoveIndicator())
                pawn.afterFirstMoveIndicator();
        } else if (selectedFigure instanceof King)
            setKingPosition(selectedFigure.player, target);
    }

    /**
     * Do not changes any state and returns value indicating whether or not move is possible
     * @param position The Figure current position
     * @param target   The target position to move to
     * @throws NullPointerException     - When there is null at the selected position
     * @throws IllegalArgumentException - When position is outside of the board
     */
    public void isMovePossible(int position, int target) throws NullPointerException, IllegalArgumentException, NotMoveableException {
            checkPosition(position);
            Movable selectedFigure = grid[position];
            if (!selectedFigure.canMove(position, target, this)) {
                TranslateCords translateCords = new TranslateCords(this);
                throw new NotMoveableException(translateCords.translateIntCordToString(position), translateCords.translateIntCordToString(target));
            }
    }

    @Override
    public String toString() {
        StringBuilder grid = new StringBuilder();
        int currentPosition;
        String color;
        for (int row = -1; row < rows + 1; row++) {
            for (int column = -1; column < columns + 1; column++) {
                if ((row == -1 || row == rows) && column != -1 && column != columns)
                    grid.append("  ").append(((char) (65 + column))).append("  ");
                else if ((column == -1 || column == columns) && row != -1 && row != rows)
                    grid.append("  ").append(rows - row).append("  ");
                else if (column == -1 || column == columns)
                    grid.append("-----");
                else {
                    currentPosition = row * columns + column;
                    if (this.grid[currentPosition] == null)
                        grid.append("_____");
                    else {
                        color = this.grid[currentPosition].player == Player.Top ? ConsoleColors.BLUE : ConsoleColors.RED;
                        grid.append(color);
                        grid.append(this.grid[currentPosition].getClass().getSimpleName()).append(this.grid[currentPosition].player.toString().toCharArray()[0]);
                        grid.append(ConsoleColors.BLACK);
                    }
                }
                grid.append("\t");
            }
            grid.append("\n\n");
        }
        return grid.toString();
    }

    @Override
    public Board clone() {
        Board board = null;
        try {
            board = (Board) super.clone();
            board.grid = new Figure[area];
            for (int i = 0; i < this.area; i++) {
                if (this.grid[i] != null)
                    board.grid[i] = this.grid[i].clone();
            }
            board.kingPosition = new Hashtable<Player, Integer>();
            board.kingPosition.put(Top, this.getKingPosition(Top));
            board.kingPosition.put(Bottom, this.getKingPosition(Bottom));
        } catch (CloneNotSupportedException ignored) { }
        return board;
    }

    /**
     * Writes Grid to console, includes fields info: 'row:column:index'
     */
    @Deprecated
    public void writeGridContent() {
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                //System.out.println(row + ":" + column + "\t");
                System.out.print(grid[row * columns + column].toString() + row + ":" + column + ":" + (row * columns + column) + " \t");
            }
            System.out.println();
        }
    }
}

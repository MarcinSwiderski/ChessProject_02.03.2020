package pwr.chessproject.game;

import pwr.chessproject.frame.TranslateCords;
import pwr.chessproject.models.Figure;
import pwr.chessproject.models.Pawn;
import pwr.chessproject.models.functionalities.IMoveable;
import pwr.chessproject.models.functionalities.NotMoveableException;

import java.util.Arrays;

/**
 * One and only, global, static board
 */
public abstract class Board {
    public static int ROWS  = 8;
    public static int COLUMNS = 8;
    public static int AREA = ROWS*COLUMNS;

    public static Figure[] Grid;

    /**
     * Fills the static Grid with null's
     */
    public static void clearBoard() {
        Arrays.fill(Grid, null);
    }

    /**
     * Checks if position is in range and whether there is a figure
     * @param position The Figure current position
     * @throws NullPointerException - When there is null at the selected position
     * @throws IllegalArgumentException - When position is outside of the board
     */
    public static void checkPosition(int position) throws NullPointerException, IllegalArgumentException {
        if (position < 0 || position > AREA - 1 )
            throw new IllegalArgumentException("Can not select figure outside of the board");
        if (Board.Grid[position] == null)
            throw new NullPointerException("There is no figure at the selected position: " + TranslateCords.translateIntCordToString(position));
    }

    /**
     * Moves figure from position to target if figures rules allow it
     * @param position The Figure current position
     * @param target The target position to move to
     * @throws NotMoveableException - When figures movement rules do not allow to move it into specified target
     * @throws NullPointerException - When there is null at the selected position
     * @throws IllegalArgumentException - When position is outside of the board
     */
    public static void moveFigure(int position, int target) throws NotMoveableException, NullPointerException, IllegalArgumentException {
        checkPosition(position);
        IMoveable selectedFigure = (IMoveable)Grid[position];
        if (selectedFigure.canMove(position, target)) {
            forceMoveFigure(position, target);
        }
        else throw new NotMoveableException(position, target, Board.Grid[position]);
    }

    /**
     * Tries to moves figure from position to target regardless figures movement rules
     * @param position The Figure current position
     * @param target The target position to move to
     * @throws NullPointerException - When there is null at the selected position
     * @throws IllegalArgumentException - When position is outside of the board
     */
    public static void forceMoveFigure(int position, int target) throws NullPointerException, IllegalArgumentException {
        checkPosition(position);
        IMoveable selectedFigure = (IMoveable)Grid[position];
        Board.Grid[target] = Board.Grid[position];
        Board.Grid[position] = null;
        if (selectedFigure instanceof Pawn) {
            Pawn pawn = (Pawn)selectedFigure;
            if (pawn.getFirstMoveIndicator())
                pawn.afterFirstMoveIndicator();
        }
    }

    public static String getGrid() {
        StringBuilder grid = new StringBuilder();
        int currentPosition;
        String color;
        for (int row = -1; row < ROWS + 1; row++) {
            for (int column = -1; column < COLUMNS + 1; column++) {
                if ((row == -1 || row == ROWS) && column != -1 && column != COLUMNS)
                        grid.append("  ").append(((char)(65+column))).append("  ");
                else if ((column == -1 || column == COLUMNS) && row != -1 && row != ROWS)
                        grid.append("  ").append(ROWS-row).append("  ");
                else if (column == -1 || column == COLUMNS)
                    grid.append("-----");
                else {
                    currentPosition = row*COLUMNS+column;
                    if (Board.Grid[currentPosition] == null)
                        grid.append("_____");
                    else {
                        color = Grid[currentPosition].player == Figure.Player.Top ? ConsoleColors.BLUE : ConsoleColors.RED;
                        grid.append(color);
                        grid.append(Board.Grid[currentPosition].getClass().getSimpleName()).append(Board.Grid[currentPosition].player.toString().toCharArray()[0]);
                        grid.append(ConsoleColors.BLACK);
                    }
                }
                grid.append("\t");
            }
            grid.append("\n\n");
        }
        return grid.toString();
    }

    /**
     * Writes Grid to console, includes fields info: 'row:column:index'
     */
    @Deprecated
    public static void writeGridContent() {
        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < COLUMNS; column++) {
                //System.out.println(row + ":" + column + "\t");
                System.out.print(Grid[row*COLUMNS+column] + String.valueOf(row) + ":" + column + ":" + (row * COLUMNS + column) + " \t");
            }
            System.out.println();
        }
    }
}

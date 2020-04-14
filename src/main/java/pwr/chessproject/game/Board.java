package pwr.chessproject.game;

import pwr.chessproject.frame.TranslateCords;
import pwr.chessproject.models.*;
import pwr.chessproject.models.functionalities.IMoveable;
import pwr.chessproject.models.functionalities.NotMoveableException;

import java.util.Arrays;

public class Board {
    public static int ROWS  = 8;
    public static int COLUMNS = 8;
    public static int AREA = ROWS*COLUMNS;

    public static Figure[] Grid;

    /**
     * Creates default 8x8 board and sets figures
     */
    public Board() {
        Grid = new Figure[AREA];
        constructDefaultBoard();
    }

    /**
     * Creates custom board with specified size
     * @param rows Number of rows
     * @param columns Number of columns
     * @throws IllegalArgumentException - When specified parameters are below 8
     */
    public Board(int rows, int columns) throws IllegalArgumentException {
        if (rows < 8 || columns < 8)
            throw new IllegalArgumentException("Board is too small");
        else if (rows == 8 && columns == 8) {
            Grid = new Figure[AREA];
            constructDefaultBoard();
        }
        else {
            ROWS = rows;
            COLUMNS = columns;
            AREA = ROWS*COLUMNS;
            Grid = new Figure[AREA];
        }
    }

    public Board(int rows, int columns, Figure.FigureType[] savedBoard, Figure.Player[] savedPlayer) throws IllegalArgumentException {
        if (rows < 8 || columns < 8)
            throw new IllegalArgumentException("Board is too small");
        else {
            ROWS = rows;
            COLUMNS = columns;
            AREA = rows*columns;
            this.constructCustomBoard(savedBoard, savedPlayer);
        }
        Grid = new Figure[AREA];
    }

    private void constructCustomBoard(Figure.FigureType[] savedBoard, Figure.Player[] savedPlayer) throws IllegalArgumentException {
        for (int i = 0; i < AREA; i++) {
            switch (savedBoard[i]) {
                case King:
                    Grid[i] = new King(savedPlayer[i]);
                    break;
                case Queen:
                    Grid[i] = new Queen(savedPlayer[i]);
                    break;
                case Tower:
                    Grid[i] = new Tower(savedPlayer[i]);
                    break;
                case Bishop:
                    Grid[i] = new Bishop(savedPlayer[i]);
                    break;
                case Knight:
                    Grid[i] = new Knight(savedPlayer[i]);
                    break;
                case Pawn:
                    Grid[i] = new Pawn(savedPlayer[i]);
                    break;
                default:
                    Grid[i] = null;
            }
        }
    }

    /**
     * Initializes Grid with default 8x8 board and sets figures
     */
    private void constructDefaultBoard() {
        int currentPosition;
        Figure.Player player;
        for (int row = 0; row < ROWS; row++) {
            player = row < 4 ? Figure.Player.Top : Figure.Player.Bottom;
            for (int column = 0; column < COLUMNS; column++) {
                currentPosition = row*COLUMNS+column;
                if (row == 1 || row == 6)
                    Grid[currentPosition] = new Pawn(player);
                else if (currentPosition == 0 || currentPosition == 7 || currentPosition == 56 || currentPosition == 63)
                    Grid[currentPosition] = new Tower(player);
                else if (currentPosition == 1 || currentPosition == 6 || currentPosition == 57 || currentPosition == 62)
                    Grid[currentPosition] = new Knight(player);
                else if (currentPosition == 2 || currentPosition == 5 || currentPosition == 58 || currentPosition == 61)
                    Grid[currentPosition] = new Bishop(player);
                else if (currentPosition == 3 || currentPosition == 60)
                    Grid[currentPosition] = new King(player);
                else if (currentPosition == 4 || currentPosition == 59)
                    Grid[currentPosition] = new Queen(player);
                else
                    Grid[currentPosition] = null;
            }
        }
    }

    /**
     * Fills the static Grid with null's
     */
    public void clearBoard() {
        Arrays.fill(Grid, null);
    }

    /**
     * Checks if position is in range and whether there is a figure
     * @param position The Figure current position
     * @throws NullPointerException - When there is null at the selected position
     * @throws IllegalArgumentException - When position is outside of the board
     */
    public void checkPosition(int position) throws NullPointerException, IllegalArgumentException {
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
     */
    public void moveFigure(int position, int target) throws NotMoveableException, NullPointerException, IllegalArgumentException {
        checkPosition(position);
        IMoveable selectedFigure = (IMoveable)Grid[position];
        if (selectedFigure.canMove(position, target)) {
            Board.Grid[target] = Board.Grid[position];
            Board.Grid[position] = null;
            if (selectedFigure instanceof Pawn) {
                Pawn pawn = (Pawn)selectedFigure;
                if (pawn.getFirstMoveIndicator())
                    pawn.afterFirstMoveIndicator();
            }
        }
        else throw new NotMoveableException(position, target, Board.Grid[position]);
    }

    /**
     * Moves figure from position to target regardless figures movement rules
     * @param position The Figure current position
     * @param target The target position to move to
     * @throws NullPointerException - When there is null at the selected position
     * @throws IllegalArgumentException - When position is outside of the board
     */
    public void forceMoveFigure(int position, int target) throws NullPointerException, IllegalArgumentException {
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

    @Override
    public String toString() {
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
            grid.append("\n");
        }
        return grid.toString();
    }

    /**
     * Writes Grid to console, includes fields info: 'row:column:index'
     */
    @Deprecated
    public void writeGridContent() {
        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < COLUMNS; column++) {
                //System.out.println(row + ":" + column + "\t");
                System.out.print(Grid[row*COLUMNS+column] + String.valueOf(row) + ":" + column + ":" + (row * COLUMNS + column) + " \t");
            }
            System.out.println();
        }
    }
}

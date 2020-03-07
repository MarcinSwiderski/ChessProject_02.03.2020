package pwr.ChessProject.board;

import org.w3c.dom.ranges.RangeException;
import pwr.ChessProject.models.*;
import pwr.ChessProject.models.functionalities.IMoveable;
import pwr.ChessProject.models.functionalities.NotMoveableException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public final class Board {
    private static final int NUMBER_OF_FIELDS = 64;
    public static Figure[] Grid = new Figure[NUMBER_OF_FIELDS];

    public Board() {
        int currentPosition;
        Figure.Player player;
        for (int row = 0; row < 8; row++) {
            player = row < 4 ? Figure.Player.Top : Figure.Player.Bottom;
            for (int column = 0; column < 8; column++) {
                currentPosition = row*8+column;
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

    public void moveFigure(int position, int target) throws NotMoveableException, NullPointerException, IllegalArgumentException {
        if (position < 0 || position > 63)
            throw new IllegalArgumentException("Can not select figure outside of the board");
        if (Board.Grid[position] == null)
            throw new NullPointerException("There is no figure at the selected position: " + position);
        IMoveable selectedFigure = (IMoveable)Grid[position];
        if (selectedFigure.canMove(position, target)) {
            Board.Grid[target] = Board.Grid[position];
            Board.Grid[position] = null;
        }
        else throw new NotMoveableException(position, target, Board.Grid[position]);
    }

    private String translateIntCordToString(int cord) {
        char x = (char)(cord % 8 + 63);
        char y = (char)(cord / 8 + 48);
        throw new NotImplementedException();
    }

    private int translateStringCordToInt(String cord) {
        throw new NotImplementedException();
    }

    @Override
    public String toString() {
        StringBuilder grid = new StringBuilder();
        int currentPosition;
        for (int row = -1; row < 9; row++) {
            for (int column = -1; column < 9; column++) {
                if ((row == -1 || row == 8) && column != -1 && column != 8)
                        grid.append("  ").append(((char)(65+column))).append("  ");
                else if ((column == -1 || column == 8) && row != -1 && row != 8)
                        grid.append("  ").append(row + 1).append("  ");
                else if (column == -1 || column == 8)
                    grid.append("-----");
                else {
                    currentPosition = row*8+column;
                    grid.append(Board.Grid[currentPosition] == null ? "_____" : Board.Grid[currentPosition].getClass().getSimpleName());
                }
                grid.append("\t");
            }
            grid.append("\n");
        }
        return grid.toString();
    }

    public void writeGridContent() {
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                System.out.print(Grid[row*8+column] + String.valueOf(row) + ":" + column + ":" + (row * 8 + column) + " \t");
            }
            System.out.println();
        }
    }
}

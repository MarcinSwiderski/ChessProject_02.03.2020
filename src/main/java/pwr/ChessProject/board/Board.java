package pwr.ChessProject.board;

import pwr.ChessProject.models.Pawn;
import pwr.ChessProject.models.Figure;

public final class Board {
    private static final int NUMBER_OF_FIELDS = 64;
    public static Figure[] Grid = new Figure[NUMBER_OF_FIELDS];

    public Board() {
        int currentPosition;
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                currentPosition = row*8+column;
                if (row==6)
                    Grid[currentPosition] = new Pawn(Figure.Player.Bottom);
                else if (row == 1)
                    Grid[currentPosition] = new Pawn(Figure.Player.Top);
                else
                    Grid[currentPosition] = null;
            }
        }
    }

    public void moveFigure() {

    }

    public void writeGrid() {
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                System.out.print(Grid[row*8+column] + String.valueOf(row) + ":" + String.valueOf(column) + ":" + String.valueOf(row*8+column) + " ");
            }
            System.out.println();
        }
    }
}

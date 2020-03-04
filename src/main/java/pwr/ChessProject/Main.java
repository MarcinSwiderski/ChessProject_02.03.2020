package pwr.ChessProject;

import pwr.ChessProject.board.Board;
import pwr.ChessProject.models.functionalities.IMoveable;

public class Main {
    public static void main(String[] p ) {
        Board board = new Board();
        board.writeGrid();
        //IMoveable selectedFigure = (IMoveable)Board.Grid[48];
        //System.out.println(selectedFigure.canMove(48, 31));

    }
}

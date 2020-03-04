package pwr.ChessProject;

import pwr.ChessProject.board.Board;
import pwr.ChessProject.models.functionalities.IMoveable;

public class Main {
    public static void main(String[] p ) {
        Board board = new Board();
        board.writeGridContent();
        System.out.println(board.toString());
        int select = 27;
        //IMoveable selectedFigure = (IMoveable)Board.Grid[select];
        //System.out.println(selectedFigure.toString());
        //System.out.println(selectedFigure.canMove(select, 11));

    }
}

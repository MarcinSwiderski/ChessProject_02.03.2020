package pwr.ChessProject;

import pwr.ChessProject.board.Board;
import pwr.ChessProject.models.functionalities.IMoveable;
import pwr.ChessProject.models.functionalities.NotMoveableException;

import java.util.Scanner;

public class Main {
    public static void main(String[] p ) {
        Board board = new Board();
        int position, target;
        board.writeGridContent();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println(board);
            System.out.println("Select a figure: ");
            position  = scanner.nextInt();
            System.out.println("Select a target: ");
            target = scanner.nextInt();
            try {
                board.moveFigure(position, target);
            }
            catch (NullPointerException | IllegalArgumentException | NotMoveableException ex) {
                System.out.println(ex);
            }
        }
    }
}

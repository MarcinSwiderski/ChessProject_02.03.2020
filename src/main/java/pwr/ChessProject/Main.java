package pwr.ChessProject;

import pwr.ChessProject.Frame.TranslateCords;
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
            try {
                System.out.println("Select a figure: ");
                position  = TranslateCords.translateStringCordToInt(scanner.next().trim().toUpperCase());
                System.out.println("Select a target: ");
                target = TranslateCords.translateStringCordToInt(scanner.next().trim().toUpperCase());
                board.moveFigure(position, target);
            }
            catch (NullPointerException | IllegalArgumentException | NotMoveableException ex) {
                System.out.println(ex);
            }
        }
    }
}

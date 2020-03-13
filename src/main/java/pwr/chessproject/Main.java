package pwr.chessproject;

import pwr.chessproject.board.Board;
import pwr.chessproject.frame.TranslateCords;
import pwr.chessproject.models.functionalities.NotMoveableException;

import java.util.Scanner;

public class Main {
    public static void main(String[] p ) {

        Board board = new Board();

        int position, target;

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println(board);
            try {
                System.out.println("Select a figure: ");
                position  = TranslateCords.translateStringCordToInt(scanner.next().trim().toUpperCase());
                System.out.println("Select a target: ");
                target = TranslateCords.translateStringCordToInt(scanner.next().trim().toUpperCase());
                board.moveFigure(position, target);
                throw new Exception("xd");
            }
            catch (NullPointerException | IllegalArgumentException | NotMoveableException ex) {
                System.out.println(ex);
            }
            catch (Exception ex) {
                System.out.println(ex);
                System.out.println("Press any key and enter to close");
                scanner.next();
                break;
            }
        }
    }
}

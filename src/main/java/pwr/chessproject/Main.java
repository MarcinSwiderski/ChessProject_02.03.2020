package pwr.chessproject;

import pwr.chessproject.board.Board;
import pwr.chessproject.frame.TranslateCords;
import pwr.chessproject.models.functionalities.NotMoveableException;

import java.util.Scanner;

public class Main {
    public static void main(String[] p ) {

        Board board = new Board(8,8);
        board.writeGridContent();
        System.out.println(board);

        int position, target;
        //board.clearBoard();

        /*Scanner scanner = new Scanner(System.in);

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
        }*/
    }
}

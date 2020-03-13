package pwr.chessproject;

import pwr.chessproject.game.Board;
import pwr.chessproject.frame.TranslateCords;
import pwr.chessproject.game.Game;
import pwr.chessproject.models.functionalities.NotMoveableException;

import javax.naming.OperationNotSupportedException;
import java.util.Scanner;

public class Main {
    public static void main(String[] p ) {

        Board board = new Board();
        Game game = new Game(board);

        game.startGame();

    }
}

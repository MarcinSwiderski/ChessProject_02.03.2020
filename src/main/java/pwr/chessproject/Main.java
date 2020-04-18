package pwr.chessproject;

import pwr.chessproject.game.Board;
import pwr.chessproject.game.Game;
import pwr.chessproject.logger.Logger;

import java.util.Scanner;

public class Main {
    public static void main(String[] p ) {
        Logger.debug("Mode: "+Logger.getMode());
        Logger.release("Let's play some chess\n\t1 - Two player hot-seat\n\t2 - Single player game against https://github.com/anzemur/chess-api\n\t0 - Close");
        Scanner scanner = new Scanner(System.in);
        int userResponse = scanner.nextInt();
        if (userResponse == 0)
            System.exit(0);
        else if (userResponse == 1) {
            try {
                Board board = new Board();
                Game game = new Game(board);
                game.startHotSeatGame();
            }
            catch (Exception ex) {
                Logger.release(ex.getMessage());
                System.exit(1);
            }
        }
        else if (userResponse == 2) {
            Board board = new Board();
            Game game = new Game(board);
            try {
                game.startGameAgainstVI();
            } catch (Exception ex) {
                Logger.release(ex.getMessage());
                Logger.debug(ex.toString());
                System.exit(1);
            }
        }

    }
}
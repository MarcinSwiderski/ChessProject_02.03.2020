package pwr.chessproject;

import pwr.chessproject.game.Board;
import pwr.chessproject.game.BoardCreator;
import pwr.chessproject.game.Game;
import pwr.chessproject.logger.Logger;
import pwr.chessproject.logger.LoggerConfiguration;

import java.util.Scanner;

public class Main {
    public static void main(String[] p ) {
        LoggerConfiguration configuration = new LoggerConfiguration();
        Logger.setMode(configuration.configureLogMode(p));
        Logger.debug("Mode: " + Logger.getMode());
        Logger.release("Let's play some chess\n\t1 - Two player hot-seat\n\t2 - Single player game against https://github.com/anzemur/chess-api\n\t0 - Close");
        Scanner scanner = new Scanner(System.in);
        int userResponse = scanner.nextInt();
        if (userResponse == 0)
            System.exit(0);
        else {
            BoardCreator boardCreator = new BoardCreator();
            Board.Grid = boardCreator.constructDefaultBoard();
            Game game = new Game();
            try {
                if (userResponse == 1) {
                    game.startHotSeatGame();
                } else if (userResponse == 2) {
                    game.startGameAgainstVI();
                }
            } catch (Exception ex) {
                Logger.release(ex.getMessage());
                Logger.debug(ex.toString());
                System.exit(1);
            }
        }
    }
}
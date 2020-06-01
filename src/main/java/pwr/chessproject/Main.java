package pwr.chessproject;

import pwr.chessproject.game.*;
import pwr.chessproject.logger.Logger;
import pwr.chessproject.logger.LoggerConfiguration;
import pwr.chessproject.models.Figure;
import pwr.chessproject.models.King;

import java.io.IOException;
import java.util.Scanner;


/**
 * Starting point class
 */
public class Main {
    public static void main(String[] p ) throws IOException {
        LoggerConfiguration configuration = new LoggerConfiguration();
        Logger.setMode(configuration.configureLogMode(p));
        Logger.debug("Mode: " + Logger.getMode());
        Logger.release("Let's play some chess\n\t1 - Two player hot-seat\n\t2 - Single player game against https://github.com/anzemur/chess-api\n\t0 - Close");
        Scanner scanner = new Scanner(System.in);
        int userResponse = scanner.nextInt();
        if (userResponse == 0)
            System.exit(0);
        else {
            try {
                Game game;
                if (userResponse == 1)
                    game = new ConsoleGame(new BoardCreator().defaultBoard());
                else if (userResponse == 2)
                    game = new ConsoleSinglePlayerGame(new BoardCreator().defaultBoard());
                else
                    throw new Exception("Unrecognized answer.");
                game.startGame();
            }
            catch (Exception ex) {
                Logger.release(ex.getMessage());
                Logger.debug(ex.toString());
                System.exit(1);
            }
        }
    }
}
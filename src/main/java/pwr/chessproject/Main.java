package pwr.chessproject;

import pwr.chessproject.game.*;
import pwr.chessproject.logger.Logger;
import pwr.chessproject.logger.LoggerConfiguration;
import pwr.chessproject.models.Pawn;
import pwr.chessproject.models.functionalities.NotMoveableException;

import java.util.Scanner;
//import pwr.chessproject.game.Game;


public class Main {
    public static void main(String[] p ) throws NotMoveableException {
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
                /*Game game = new Game();
                if (userResponse == 1) {
                    game.startHotSeatGame();
                } else if (userResponse == 2) {
                    game.startGameAgainstVI();
                }*/
            }
            catch (Exception ex) {
                Logger.release(ex.getMessage());
                Logger.debug(ex.toString());
                System.exit(1);
            }
        }
        /*GameComponent gameComponent = DaggerGameComponent.builder().build();
        Board board = gameComponent.getBoard();
        System.out.println(board.hashCode());
        try {
            board.moveFigure(0, 1);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        board.moveFigure(TranslateCords.translateStringCordToInt("a2"), TranslateCords.translateStringCordToInt("a4"));
        System.out.println(board);
        board.clearBoard();
        System.out.println(board.hashCode());
        System.out.println(MovingStrategies.board.hashCode());
        Pawn pawn = gameComponent.getPawn();
        System.out.println(pawn);
        System.out.println(pawn.board.hashCode());*/
    }
}
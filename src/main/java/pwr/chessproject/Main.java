package pwr.chessproject;

import okhttp3.*;
import pwr.chessproject.api.models.MovePlayerResponse;
import pwr.chessproject.game.Board;
import pwr.chessproject.game.Game;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] p ) {
        System.out.println("Let's play some chess\n\t1 - Two player hot-seat\n\t2 - Single player game against https://github.com/anzemur/chess-api\n\t0 - Close");
        Scanner scanner = new Scanner(System.in);
            switch (scanner.nextInt()) {
                case 0: System.exit(0);
                case 1: {
                    Board board = new Board();
                    Game game = new Game(board);
                    try { game.startHotSeatGame(); } catch (Exception ex) { System.out.println(ex.getMessage()); System.exit(1); }
                }
                case 2: {
                    Board board = new Board();
                    Game game = new Game(board);
                    try { game.startGameAgainstVI(); } catch (Exception ex) { System.out.println(ex.getMessage()+"\n"+ex.getCause()+"\n"+ex.fillInStackTrace()); System.exit(1); }
                }
            }
    }
}
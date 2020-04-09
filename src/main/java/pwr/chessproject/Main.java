package pwr.chessproject;

import okhttp3.*;
import okio.BufferedSink;
import pwr.chessproject.api.models.MovePlayerResponse;
import pwr.chessproject.game.Board;
import pwr.chessproject.game.Game;
import pwr.chessproject.logger.Logger;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.IOException;
import java.net.*;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] p ) {
        Logger.debug("Mode: "+Logger.getMode());
        Logger.release("Let's play some chess\n\t1 - Two player hot-seat\n\t2 - Single player game against https://github.com/anzemur/chess-api\n\t0 - Close");
        Scanner scanner = new Scanner(System.in);
            switch (scanner.nextInt()) {
                case 0: System.exit(0);
                case 1: {
                    Board board = new Board();
                    Game game = new Game(board);
                    try { game.startHotSeatGame(); } catch (Exception ex) { Logger.release(ex.getMessage()); System.exit(1); }
                }
                case 2: {
                    Board board = new Board();
                    Game game = new Game(board);
                    try {
                        game.startGameAgainstVI();
                    }
                    catch (Exception ex) {
                        Logger.release(ex.getMessage());
                        Logger.debug(ex.toString());
                        System.exit(1);
                    }
                }
            }
        /*try {
            String hostname = "127.0.0.1";
            int port = 8080;
            Proxy proxy = new Proxy(Proxy.Type.HTTP,
                    new InetSocketAddress(hostname, port));

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.DAYS)
                    .writeTimeout(10, TimeUnit.DAYS)
                    .readTimeout(10, TimeUnit.DAYS)
                    .build();

            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            RequestBody body = RequestBody.create(mediaType, "position=&game_id=5e8ef93e3bd97400144176e8");
            Request request = new Request.Builder()
                    .url("http://chess-api-chess.herokuapp.com/api/v1/chess/one/moves")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .build();
            Response response = client.newCall(request).execute();

            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            RequestBody body = RequestBody.create(mediaType, "from=a3&to=a4&game_id=5e8ef93e3bd97400144176e8");
            Request request = new Request.Builder()
                    .url("http://chess-api-chess.herokuapp.com/api/v1/chess/one/move/player")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .build();
            Response response = client.newCall(request).execute();

            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            RequestBody body = RequestBody.create(mediaType, "game_id=5e8ef93e3bd97400144176e8");
            Request request = new Request.Builder()
                    .url("http://chess-api-chess.herokuapp.com/api/v1/chess/one/move/ai")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .build();
            Response response = client.newCall(request).execute();
            System.out.println(response.body().string());
        }
        catch (Exception ex) {
            System.out.println(ex);
        }*/
    }
}
package pwr.chessproject.api.requests;

import okhttp3.*;
import pwr.chessproject.api.models.CreateNewGameResponse;
import pwr.chessproject.api.models.MovePlayerResponse;
import pwr.chessproject.api.models.MoveVIResponse;
import pwr.chessproject.logger.Logger;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.IOException;

/**
 * Realizes 3 operations (creating new game, sending players move, requesting VI's move) from https://github.com/anzemur/chess-api
 */
public class API {

    private final OkHttpClient client;
    private String game_id;
    private final Jsonb jsonb;

    public API() {
        client = new OkHttpClient().newBuilder().build();
        jsonb = JsonbBuilder.create();
    }

    /**
     * Sends a http request to 'http://chess-api-chess.herokuapp.com/api/v1/chess/one' to request new game_id
     * @return Java model of JSON response containing game_id
     * @throws IOException - when http response code isn't 200
     */
    public CreateNewGameResponse createNewGame() throws IOException {
        Logger.debug("Requesting for a new game...");
        Request request = new Request.Builder()
                .url("http://chess-api-chess.herokuapp.com/api/v1/chess/one")
                .method("GET", null)
                .build();
        Response rawResponse = client.newCall(request).execute();
        ResponseBody responseBody = rawResponse.body();
        String content = responseBody.string();

        CreateNewGameResponse response = jsonb.fromJson(content, CreateNewGameResponse.class);
        this.game_id = response.getGame_id();

        Logger.debug("Response received: \n\t%"+content+"%");
        Logger.debug("Game id: "+response.getGame_id());
        Logger.debug("Request id: "+response.get_id());
        return response;
    }


    /**
     * Sends a http request to 'http://chess-api-chess.herokuapp.com/api/v1/chess/one/move/player' with current game_id to update server board with players move
     * @param from The Figure current position
     * @param to The target position to move to
     * @return Java model of JSON response
     * @throws IOException - when http response code isn't 200 or move could not be executed in server
     */
    public MovePlayerResponse movePlayer(String from, String to) throws IOException {
        Logger.debug("Requesting for a player move...");
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        String tempBody = "from=" + from.toLowerCase() + "&to=" + to.toLowerCase() + "&game_id=" + this.game_id;
        RequestBody body = RequestBody.create(mediaType, tempBody);
        Logger.debug("Connection string: http://chess-api-chess.herokuapp.com/api/v1/chess/one/move/player");
        Logger.debug("Request body: "+tempBody);
        Request request = new Request.Builder()
                .url("http://chess-api-chess.herokuapp.com/api/v1/chess/one/move/player")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response rawResponse = client.newCall(request).execute();
        ResponseBody responseBody = rawResponse.body();
        String content = responseBody.string();

        MovePlayerResponse response = jsonb.fromJson(content, MovePlayerResponse.class);

        Logger.debug("Response received: \n\t%"+content+"%");

        if (response.getStatus().contains("error"))
            throw new IOException("Error during sending player's move to VI\n"+response.getStatus());

        return response;
    }

    /**
     * Sends a http request to 'http://chess-api-chess.herokuapp.com/api/v1/chess/one/move/ai' with current game_id to request VI's move
     * @return Java model of JSON response containing VI's move
     * @throws IOException - when http response code isn't 200 or move could not be executed in server
     */
    public MoveVIResponse moveVI() throws IOException {
        Logger.debug("Requesting for a VI move...");
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        String tempBody = "game_id=" + this.game_id;
        RequestBody body = RequestBody.create(mediaType, tempBody);
        Logger.debug("Connection string: http://chess-api-chess.herokuapp.com/api/v1/chess/one/move/ai");
        Logger.debug("Request body: "+tempBody);
        Request request = new Request.Builder()
                .url("http://chess-api-chess.herokuapp.com/api/v1/chess/one/move/ai")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response rawResponse = client.newCall(request).execute();
        ResponseBody responseBody = rawResponse.body();
        String content = responseBody.string();

        MoveVIResponse response = jsonb.fromJson(content, MoveVIResponse.class);

        if (response.getStatus().contains("error"))
            throw new IOException("Error during VI's move\n"+response.getStatus());

        Logger.debug("Response received: \n\t%"+content+"%");

        return response;
    }
}

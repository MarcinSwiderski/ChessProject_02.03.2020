package pwr.chessproject.api.requests;

import okhttp3.*;
import pwr.chessproject.api.models.CreateNewGameResponse;
import pwr.chessproject.api.models.MovePlayerResponse;
import pwr.chessproject.api.models.MoveVIResponse;
import pwr.chessproject.logger.Logger;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.IOException;

public class API {

    private OkHttpClient client;
    private String game_id;
    private Jsonb jsonb;

    public API() {
        this.client = new OkHttpClient().newBuilder().build();
        jsonb = JsonbBuilder.create();
    }

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

        Logger.debug("Response received: \n%"+content+"%");
        Logger.debug("Game id: "+response.getGame_id());
        Logger.debug("Request id: "+response.get_id());
        return response;
    }

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

        Logger.debug("Response received: \n%"+content+"%");

        if (response.getStatus().contains("error"))
            throw new IOException("Error during sending player's move to VI\n"+response.getStatus());

        return response;
    }

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

        Logger.debug("Response received: \n%"+content+"%");

        return response;
    }

    public void resetBoard() throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "game_id="+this.game_id);
        Request request = new Request.Builder()
                .url("http://chess-api-chess.herokuapp.com/api/v1/chess/two/reset")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();
    }
}

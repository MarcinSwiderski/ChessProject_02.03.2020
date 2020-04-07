package pwr.chessproject.api.requests;

import okhttp3.*;
import pwr.chessproject.api.models.CreateNewGameResponse;
import pwr.chessproject.api.models.MovePlayerResponse;
import pwr.chessproject.api.models.MoveVIResponse;

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
        Request request = new Request.Builder()
                .url("http://chess-api-chess.herokuapp.com/api/v1/chess/one")
                .method("GET", null)
                .build();
        Response rawResponse = client.newCall(request).execute();

        CreateNewGameResponse response = jsonb.fromJson(rawResponse.body().string(), CreateNewGameResponse.class);
        this.game_id = response.getGame_id();

        return response;
    }

    public MovePlayerResponse movePlayer(String from, String to) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "from=" + from.toLowerCase() + "&to=" + to.toLowerCase() + "&game_id=" + this.game_id);
        Request request = new Request.Builder()
                .url("http://chess-api-chess.herokuapp.com/api/v1/chess/one/move/player")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response rawResponse = client.newCall(request).execute();

        MovePlayerResponse response = jsonb.fromJson(rawResponse.body().string(), MovePlayerResponse.class);

        if (response.getStatus().contains("error"))
            throw new IOException("Error during sending player's move to VI\n"+response.getStatus());

        return response;
    }

    public MoveVIResponse moveVI() throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "game_id="+this.game_id);
        Request request = new Request.Builder()
                .url("http://chess-api-chess.herokuapp.com/api/v1/chess/one/move/ai")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response rawResponse = client.newCall(request).execute();

        MoveVIResponse response = jsonb.fromJson(rawResponse.body().string(), MoveVIResponse.class);

        if (response.getStatus().contains("error"))
            throw new IOException("Error during VI's move\n"+response.getStatus());

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

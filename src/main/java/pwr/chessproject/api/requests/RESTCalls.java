package pwr.chessproject.api.requests;

import okhttp3.*;
import pwr.chessproject.logger.Logger;

import java.io.IOException;

/**
 * Low level functionality for making http requests
 */
class RESTCalls {
    private final OkHttpClient client;

    RESTCalls() {
        client = new OkHttpClient().newBuilder().build();
    }

    /**
     * Sends a GET request to 'http://chess-api-chess.herokuapp.com/api/v1/chess/one'
     * @return Response body
     * @throws IOException When request failed due to some program independent reasons
     */
    String GETCreateNewGame() throws IOException {
        Request request = new Request.Builder()
                .url("http://chess-api-chess.herokuapp.com/api/v1/chess/one")
                .method("GET", null)
                .build();
        Response rawResponse = client.newCall(request).execute();
        return rawResponse.body().string();
    }


    /**
     * Sends a POST request to 'http://chess-api-chess.herokuapp.com/api/v1/chess/one/move/player' with current game_id to update server board with players move
     * @param gameId game_id body parameter
     * @param from 'from' body parameter
     * @param to 'to' body parameter
     * @return Response body
     * @throws IOException When request failed due to some program independent reasons
     */
    String POSTMovePlayer(String gameId, String from, String to) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        String tempBody = "from=" + from.toLowerCase() + "&to=" + to.toLowerCase() + "&game_id=" + gameId;
        RequestBody body = RequestBody.create(mediaType, tempBody);
        Logger.debug("Connection string: http://chess-api-chess.herokuapp.com/api/v1/chess/one/move/player");
        Logger.debug("Request body: "+tempBody);
        Request request = new Request.Builder()
                .url("http://chess-api-chess.herokuapp.com/api/v1/chess/one/move/player")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response rawResponse = client.newCall(request).execute();
        return rawResponse.body().string();
    }

    /**
     * Sends a POST request to 'http://chess-api-chess.herokuapp.com/api/v1/chess/one/move/ai'
     * @param gameId game_id body parameter
     * @return Response body
     * @throws IOException When request failed due to some program independent reasons
     */
    String POSTMoveVI(String gameId) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        String tempBody = "game_id=" + gameId;
        RequestBody body = RequestBody.create(mediaType, tempBody);
        Logger.debug("Connection string: http://chess-api-chess.herokuapp.com/api/v1/chess/one/move/ai");
        Logger.debug("Request body: "+tempBody);
        Request request = new Request.Builder()
                .url("http://chess-api-chess.herokuapp.com/api/v1/chess/one/move/ai")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response rawResponse = client.newCall(request).execute();
        return rawResponse.body().string();
    }
}

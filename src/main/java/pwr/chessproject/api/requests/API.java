package pwr.chessproject.api.requests;

import okhttp3.*;
import pwr.chessproject.api.models.CreateNewGameResponse;
import pwr.chessproject.api.models.MovePlayerResponse;
import pwr.chessproject.api.models.MoveVIResponse;
import pwr.chessproject.logger.Logger;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.naming.OperationNotSupportedException;
import java.io.IOException;

/**
 * Realizes 3 operations (creating new game, sending players move, requesting VI's move) from https://github.com/anzemur/chess-api
 */
public class API {

    private final RESTCalls calls;
    private final Jsonb jsonb;
    private final String URL = "http://chess-api-chess.herokuapp.com/api/v1/chess/one";

    public API() {
        calls = new RESTCalls();
        jsonb = JsonbBuilder.create();
    }

    /**
     * Sends a http request to URL to request new game id
     * @return Java model of JSON response containing game id
     * @throws IOException - when request failed due to some program independent reasons
     */
    public CreateNewGameResponse createNewGame() throws IOException {
        Logger.debug("Requesting for a new game...");

        String content = calls.GETCreateNewGame();
        CreateNewGameResponse response = jsonb.fromJson(content, CreateNewGameResponse.class);

        Logger.debug("Response received: \n\t%"+content+"%");
        Logger.debug("Game id: "+response.getGame_id());
        Logger.debug("Request id: "+response.get_id());
        return response;
    }


    /**
     * Sends a http request to URL with current game_id to update server board with players move
     * @param gameId Current game id
     * @param position The Figure current position
     * @param target The target position to move to
     * @return Java model of JSON response
     * @throws IOException - when request failed due to some program independent reasons
     * @throws OperationNotSupportedException When server succeed's to inform us that there is an error. Most probably there is a problem wit game id
     */
    public MovePlayerResponse movePlayer(String gameId, String position, String target) throws IOException, OperationNotSupportedException {
        Logger.debug("Requesting for a player move...");

        String content = calls.POSTMovePlayer(gameId, position, target);
        MovePlayerResponse response = jsonb.fromJson(content, MovePlayerResponse.class);

        Logger.debug("Response received: \n\t%"+content+"%");

        if (response.getStatus().contains("error"))
            throw new OperationNotSupportedException("Error during sending player's move to VI\n"+response.getStatus());

        return response;
    }

    /**
     * Sends a http request to URL with current game id to request VI's move
     * @param gameId Current game id
     * @return Java model of JSON response containing VI's move
     * @throws IOException - when request failed due to some program independent reasons
     * @throws OperationNotSupportedException When server succeed's to inform us that there is an error. Most probably there is a problem wit game id
     */
    public MoveVIResponse moveVI(String gameId) throws IOException, OperationNotSupportedException {
        Logger.debug("Requesting for a VI move...");

        String content = calls.POSTMoveVI(gameId);
        MoveVIResponse response = jsonb.fromJson(content, MoveVIResponse.class);

        Logger.debug("Response received: \n\t%"+content+"%");

        if (response.getStatus().contains("error"))
            throw new OperationNotSupportedException("Error during VI's move\n"+response.getStatus());

        return response;
    }
}

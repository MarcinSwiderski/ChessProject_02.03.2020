package pwr.chessproject.game;

import pwr.chessproject.api.models.CreateNewGameResponse;
import pwr.chessproject.api.models.MovePlayerResponse;
import pwr.chessproject.api.models.MoveVIResponse;
import pwr.chessproject.api.requests.API;
import pwr.chessproject.logger.Logger;
import pwr.chessproject.models.Figure;

import javax.naming.OperationNotSupportedException;
import java.io.IOException;
import java.rmi.UnexpectedException;

public class ConsoleSinglePlayerGame extends ConsoleGame {

    private final API api;
    private String gameId;

    public ConsoleSinglePlayerGame(Board board) {
        super(board);
        api = new API();
    }

    private CreateNewGameResponse initializeGame() {
        try {
            CreateNewGameResponse createNewGameResponse = api.createNewGame();
            VIsays(createNewGameResponse.getStatus());
            this.gameId = createNewGameResponse.getGame_id();
            return createNewGameResponse;
        } catch (IOException e) {
            Logger.debug(e);
            Logger.release(e);
            endGame("Error connecting to VI.");
        }
        return null;
    }

    private MovePlayerResponse movePlayer() {
        try {
            MovePlayerResponse movePlayerResponse = api.movePlayer(gameId, translateCords.translateIntCordToString(position), translateCords.translateIntCordToString(target));
            VIsays(movePlayerResponse.getStatus());
            return movePlayerResponse;
        } catch (IOException | OperationNotSupportedException e) {
            Logger.debug(e);
            Logger.release(e.getMessage());
            endGame("Error sending players move to VI.");
        }
        return null;
    }

    private MoveVIResponse moveVI() {
        try {
            MoveVIResponse moveVIResponse = api.moveVI(gameId);
            VIsays(moveVIResponse.getStatus());
            board.moveFigure(translateCords.translateStringCordToInt(moveVIResponse.getFrom()), translateCords.translateStringCordToInt(moveVIResponse.getTo()));
            return moveVIResponse;
        } catch (IOException | OperationNotSupportedException e) {
            Logger.debug(e);
            Logger.release(e.getMessage());
            endGame("Error requesting VI's move.");
        }
        return null;
    }

    @Override
    public void startGame()  {
        initializeGame();
        while (!isPlayerCheckmated()) {
            if (getPlayer().equals(Figure.Player.Bottom)) {
                Logger.release(board);
                setStatus("Your turn");
                executeTurn();
            }
            else {
                Logger.debug("VI's turn");
                movePlayer();
                moveVI();
                passTurn();
            }
        }

        if (getOpponent().equals(Figure.Player.Bottom))
            endGame("You win against VI. Impressive!");
        else
            endGame("VI crashes you as expected.");
    }

    private void VIsays(String message) {
        Logger.release("VI: " + message);
    }
}

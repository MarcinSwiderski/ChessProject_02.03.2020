package pwr.chessproject.game;

import pwr.chessproject.models.Figure;

public interface Game {
    Figure.Player getPlayer();
    Figure.Player getOpponent();
    void startGame();
    void endGame(String reason);
    String getStatus();
}
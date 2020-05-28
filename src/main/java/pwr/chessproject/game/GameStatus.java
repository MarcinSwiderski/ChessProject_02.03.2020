package pwr.chessproject.game;

import pwr.chessproject.logger.Logger;
import pwr.chessproject.models.Figure;

abstract class GameStatus implements Game {

    protected Board board;
    private Figure.Player currentPlayer;
    private String status;

    protected GameStatus(Board board) {
        this.board = board;
        this.currentPlayer = Figure.Player.Bottom;
        setStatus("Game created");
    }

    @Override
    public String getStatus() {
        return this.status;
    }

    protected void setStatus(String status) {
        if (!status.equals(this.status)) {
            this.status = status;
            Logger.release(status);
        }
    }

    @Override
    public Figure.Player getPlayer() {
        return currentPlayer;
    }

    @Override
    public Figure.Player getOpponent() {
        return currentPlayer.equals(Figure.Player.Top) ? Figure.Player.Bottom : Figure.Player.Top;
    }

    protected void passTurn() {
        currentPlayer = getOpponent();
    }
}

package pwr.chessproject.game;

import pwr.chessproject.logger.Logger;
import pwr.chessproject.models.Figure;

/**
 * Implements logic of game status values like current player, passing turn and having a board
 */
abstract class GameStatus implements Game {

    protected Board board;
    private Figure.Player currentPlayer;
    private String status;

    /**
     * Actualizes default first player and sets status of game creation
     * @param board Board for the game
     */
    protected GameStatus(Board board) {
        this.board = board;
        this.currentPlayer = Figure.Player.Bottom;
        setStatus("Game created");
    }

    @Override
    public String getStatus() {
        return this.status;
    }

    /**
     * Prints and sets the new status
     * @param status New status
     */
    protected void setStatus(String status) {
        if (!status.equals(this.status)) {
            this.status = status;
            Logger.release(status);
        }
    }

    public Figure.Player getPlayer() {
        return currentPlayer;
    }

    public Figure.Player getOpponent() {
        return currentPlayer.equals(Figure.Player.Top) ? Figure.Player.Bottom : Figure.Player.Top;
    }

    /**
     * Simply changes current player
     */
    protected void passTurn() {
        currentPlayer = getOpponent();
    }
}

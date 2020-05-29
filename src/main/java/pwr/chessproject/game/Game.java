package pwr.chessproject.game;

/**
 * Interface for managing game client
 */
public interface Game {
    /**
     * Starts a game that lasts until it ends or an unpredicted error occurs
     */
    void startGame();

    /**
     * System.exit() the process
     * @param reason Reason for ending a game
     */
    void endGame(String reason);

    /**
     * Gets the latest status of the game
     * @return Latest status
     */
    String getStatus();
}
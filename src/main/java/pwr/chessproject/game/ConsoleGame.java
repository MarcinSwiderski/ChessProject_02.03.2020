package pwr.chessproject.game;

import pwr.chessproject.frame.TranslateCords;
import pwr.chessproject.logger.Logger;
import pwr.chessproject.models.functionalities.NotMoveableException;

import java.rmi.AccessException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Implementation of two player game in console
 */
public class ConsoleGame extends GameRules {

    protected final Scanner scanner;
    protected final TranslateCords translateCords;

    /**
     * User feedback that will resolve in closing program
     */
    private final List<String> closeWords = new ArrayList<String>();
    /**
     * User feedback that will resolve in going back
     */
    private final List<String> goBackWords = new ArrayList<String>();

    protected int position;
    protected int target;

    public ConsoleGame(Board board) {
        super(board);
        initializeList(new String[]{"close", "exit", "wyjdź", "wyjdz", "stop", "^C"}, closeWords);
        initializeList(new String[]{"q", "quit", "cofnij", String.valueOf((char)8)}, goBackWords);
        scanner = new Scanner(System.in);
        translateCords = new TranslateCords(this.board);
    }

    /**
     * Ads provided words into list
     * @param words Key words to add
     * @param list Reference to the list to update
     */
    private void initializeList(String[] words, List<String> list) {
        list.addAll(Arrays.asList(words));
    }

    /**
     * Request selecting figure from player
     * @return This instance
     */
    private ConsoleGame select() {
        Logger.release("Select a figure to move\t\t(" + Arrays.toString(closeWords.toArray()) + " to close)");
        String userFeedback = scanner.next().trim().toLowerCase();
        if (closeWords.contains(userFeedback)) {
            endGame("Closed by user");
            System.exit(0);
        }
        else if (goBackWords.contains(userFeedback))
            Logger.release("Can not go back");
        else {
            try {
                int position = translateCords.translateStringCordToInt(userFeedback);
                super.canSelectFigure(position);
                this.position = position;
                return this;
            } catch (AccessException | IllegalArgumentException | NullPointerException e) {
                Logger.debug(e);
                Logger.answer(e.getMessage());
            }
        }
        select();
        return this;
    }

    /**
     * Requests a move from player
     * @return This instance
     * @throws GoBackException When player wants to go back to choosing figure to move
     */
    private ConsoleGame move() throws GoBackException {
        Logger.release("Select a target to move\t\t(\"" + Arrays.toString(closeWords.toArray()) + " to close, " + Arrays.toString(goBackWords.toArray()) +" to go back)");
        String userFeedback = scanner.next().trim().toLowerCase();
        if (closeWords.contains(userFeedback)) {
            endGame("Closed by user");
            System.exit(0);
        }
        else if (goBackWords.contains(userFeedback))
            throw new GoBackException();
        else {
            try {
                int target = translateCords.translateStringCordToInt(userFeedback);
                if (isMoveValid(this.position, target)) {
                    board.moveFigure(this.position, target);
                    this.target = target;
                    return this;
                }
                else
                    throw new NotMoveableException("Can not move " +board.grid[position].figureType  + " from " + translateCords.translateIntCordToString(position) + " to " + translateCords.translateIntCordToString(target) + " becouse of check");
            } catch (IllegalArgumentException | NullPointerException | NotMoveableException e) {
                Logger.debug(e);
                Logger.answer(e.getMessage());
            }
        }
        move();
        return this;
    }

    /**
     * Fully executes one move of one player
     */
    protected void executeTurn() {
        if (isPlayerUnderCheck())
            Logger.release("CHECK!");

        try {
            select().move().passTurn();
            Logger.debug("Player moved from " + translateCords.translateIntCordToString(position) + " to " + translateCords.translateIntCordToString(target));
        } catch (GoBackException e) {
            executeTurn();
        }
    }

    @Override
    public void startGame() {
        while (!isPlayerCheckmated()) {
            Logger.release(board);
            setStatus(getPlayer() + " turn");
            executeTurn();
        }
        setStatus(getOpponent() + " wins!");
    }

    @Override
    public void endGame(String reason) {
        setStatus(reason);
        Logger.release("Game ended");
        System.exit(1);
    }
}

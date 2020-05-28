package pwr.chessproject.game;

import pwr.chessproject.frame.TranslateCords;
import pwr.chessproject.logger.Logger;
import pwr.chessproject.models.functionalities.NotMoveableException;

import java.rmi.AccessException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ConsoleGame extends GameRules {

    private final Scanner scanner = new Scanner(System.in);
    private final TranslateCords translateCords = new TranslateCords(this.board);

    private final List<String> closeWords = new ArrayList<String>();
    private final List<String> goBackWords = new ArrayList<String>();

    private int position;

    public ConsoleGame(Board board) {
        super(board);
        initializeList(new String[]{"close", "exit", "wyjdź", "wyjdz", "stop", "^C"}, closeWords);
        initializeList(new String[]{"q", "quit", "cofnij", String.valueOf((char)8)}, goBackWords);
    }

    private void initializeList(String[] words, List<String> list) {
        list.addAll(Arrays.asList(words));
    }

    private ConsoleGame select() {
        Logger.release("Select a figure to move\t\t('exit' to close)");
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
                Logger.answear(e.getMessage());
            }
        }
        select();
        return this;
    }

    private ConsoleGame move() throws GoBackException {
        Logger.release("Select a target to move\t\t('exit' to close, 'q' to go back)");
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
                    return this;
                }
                else
                    throw new NotMoveableException("Can not move " +board.grid[position].figureType  + " from " + translateCords.translateIntCordToString(position) + " to " + translateCords.translateIntCordToString(target) + " becouse of check");
            } catch (IllegalArgumentException | NullPointerException | NotMoveableException e) {
                Logger.debug(e);
                Logger.answear(e.getMessage());
            }
        }
        move();
        return this;
    }

    private void executeTurn() {
        if (isPlayerUnderCheck())
            Logger.release("CHECK!");

        try {
            select().move().passTurn();
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
        endGame(getOpponent() + " wins!");
    }

    @Override
    public void endGame(String reason) {
        setStatus(reason);
        Logger.release("Game ended");
    }
}

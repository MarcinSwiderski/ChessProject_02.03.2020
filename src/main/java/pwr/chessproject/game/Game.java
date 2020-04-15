package pwr.chessproject.game;

import pwr.chessproject.api.models.CreateNewGameResponse;
import pwr.chessproject.api.models.MoveVIResponse;
import pwr.chessproject.api.requests.API;
import pwr.chessproject.logger.Logger;
import pwr.chessproject.models.Figure;
import pwr.chessproject.models.King;
import pwr.chessproject.models.Pawn;
import pwr.chessproject.models.functionalities.IMoveable;
import pwr.chessproject.models.functionalities.NotMoveableException;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Scanner;

import static pwr.chessproject.frame.TranslateCords.translateIntCordToString;
import static pwr.chessproject.frame.TranslateCords.translateStringCordToInt;
import static pwr.chessproject.game.Board.AREA;
import static pwr.chessproject.game.Board.Grid;
import static pwr.chessproject.models.Figure.Player.Bottom;
import static pwr.chessproject.models.Figure.Player.Top;

public final class Game {
    Board board;

    /**
     * Contain information about kings current positions allowing for dynamic game situation check
     */
    Hashtable<Figure.Player, Integer> kingPosition = new Hashtable<Figure.Player, Integer>() {
        {
            put(Top, 3);
            put(Bottom, 59);
        }
    };

    Figure.Player currentPlayer = Bottom;

    public Game(Board board) {
        this.board = board;
    }

    /**
     * Starts 'hot-seat' game in IDE output. Game lasts until someone wins
     */
    public void startHotSeatGame() {
        int position, target;

        Scanner scanner = new Scanner(System.in);

        while (true) {
            Logger.release(board);
            try {
                if (isChecked(kingPosition.get(currentPlayer))) {
                    Logger.release("Check!");
                    if (isCheckmated(this.kingPosition.get(currentPlayer)))
                        break;
                }

                Logger.release(currentPlayer + " turn: ");
                Logger.release("Select a figure: ");
                position = translateStringCordToInt(scanner.next().trim().toUpperCase());
                board.checkPosition(position);
                if (Grid[position].player != currentPlayer) {
                    Logger.release("You have to select " + currentPlayer + " player figure");
                    continue;
                }
                Logger.release("Select a target: ");
                target = translateStringCordToInt(scanner.next().trim().toUpperCase());
                if (simulateMoveAndCheck(position, target, this::isChecked)) {
                    Logger.release("You can not move into " + translateIntCordToString(target) + " because of check");
                    continue;
                }
                board.moveFigure(position, target);
                if (Grid[target] instanceof King)
                    kingPosition.replace(currentPlayer, target);

                passTurn();
            } catch (NullPointerException | IllegalArgumentException | NotMoveableException | ClassCastException ex) {
                Logger.release(ex.getMessage());
                Logger.debug(ex.toString());
                continue;
            }
        }

        Logger.release(currentPlayer + " LOOSES");
    }

    /**
     * Starts single player game against https://github.com/anzemur/chess-api in IDE output
     */
    public void startGameAgainstVI() {
        int position, target;
        Scanner scanner = new Scanner(System.in);
        Figure.Player opponent;
        API api = new API();

        //Registering new game
        try {
            CreateNewGameResponse response = api.createNewGame();
            Logger.release("VI says:\t"+response.getStatus());
        } catch (IOException ex) {
            Logger.debug(ex);
            Logger.release("Critical error during initial connecting to VI");
            System.exit(1);
        }

        System.out.println("You start as a bottom player");
        while (true) {
            Logger.release(board);
            try {
                if (isChecked(kingPosition.get(currentPlayer)))
                    Logger.release("Check!");
                Logger.release("Your turn");
                Logger.release("Select a figure: ");
                position = translateStringCordToInt(scanner.next().trim().toUpperCase());
                board.checkPosition(position);
                if (Grid[position].player != currentPlayer) {
                    Logger.release("You have to select your figure");
                    continue;
                }
                Logger.release("Select a target: ");
                target = translateStringCordToInt(scanner.next().trim().toUpperCase());
                if (simulateMoveAndCheck(position, target, this::isChecked)) {
                    Logger.release("You can not move into " + translateIntCordToString(target) + " because of check");
                    continue;
                }
                board.moveFigure(position, target);
                if (Grid[target] instanceof King)
                    kingPosition.replace(currentPlayer, target);

                opponent =  this.currentPlayer == Top ? Bottom : Top;
                if (isCheckmated(this.kingPosition.get(opponent)))
                    break;
                passTurn();
                api.movePlayer(translateIntCordToString(position), translateIntCordToString(target));
                MoveVIResponse moveVIResponse = api.moveVI();
                position = translateStringCordToInt(moveVIResponse.getFrom());
                target = translateStringCordToInt(moveVIResponse.getTo());
                board.forceMoveFigure(position, target);
                if (Grid[target] instanceof King)
                    kingPosition.replace(currentPlayer, target);
                Logger.release("VI moved from " + moveVIResponse.getFrom() + " to " + moveVIResponse.getTo());
                opponent =  this.currentPlayer == Top ? Bottom : Top;
                if (isCheckmated(this.kingPosition.get(opponent)))
                    break;
                passTurn();
            } catch (IOException | NullPointerException | IllegalArgumentException | NotMoveableException ex) {
                Logger.release(ex.getMessage());
                Logger.debug(ex.toString());
                continue;
            }
        }
        if (this.currentPlayer == Bottom)
            Logger.release("YOU WIN!!!");
        else
            Logger.release("YOU LOOSE :<");
    }

    /**
     * Simulates move on real Grid then returns callback function result
     * @param position The Figure current position
     * @param target The target position to move to
     * @param toCheck Callback which result will be returned
     * @return Boolean result of the callback
     * @throws NotMoveableException - When simulated move is not valid
     */
    boolean simulateMoveAndCheck(int position, int target, IToCheck toCheck) throws NotMoveableException {
        Figure selectedFigure = Grid[position].clone();
        Figure targetFigure;
        if (Grid[target] == null )
            targetFigure = null;
        else
            targetFigure = Grid[target].clone();
        int oldKingPosition = this.kingPosition.get(currentPlayer);

        board.moveFigure(position, target);
        if (selectedFigure instanceof King)
            this.kingPosition.replace(currentPlayer, target);

        boolean isActionValid = toCheck.action(this.kingPosition.get(currentPlayer));

        Grid[position] = selectedFigure;
        Grid[target] = targetFigure;
        this.kingPosition.replace(currentPlayer, oldKingPosition);
        return isActionValid;
    }

    /**
     * Checks whether specified position is under check in current board
     * @param kingPosition Position to check
     * @return True when checked, else false
     */
    boolean isChecked(int kingPosition) {
        Figure.Player opponent =  this.currentPlayer == Top ? Bottom : Top;
        Figure figure;
        for (int i = 0; i < AREA; i++) {
            figure = Grid[i];
            if (figure != null && figure.player == opponent) {
                if (((IMoveable)figure).canMove(i, kingPosition))
                    return true;
            }
        }
        return false;
    }

    /**
     * Iterate through all possible movements of all existing figures to verify if king at parameter position have any option to not be checked
     * @param kingPosition Position to check
     * @return True when checked-mated, else false
     */
    boolean isCheckmated (int kingPosition) throws NotMoveableException {
        if (!isChecked(kingPosition))
            return false;

        Figure.Player player = Grid[kingPosition].player;

        Boolean isCheckmated = false;
        IMoveable figure;
        for (int position = 0; position < AREA; position++) {
            if (Grid[position] != null && Grid[position].player == player) {
                figure = (IMoveable) Grid[position];
                for (int target: figure.getAvailableFields(position)) {
                    if (!simulateMoveAndCheck(position, target, this::isChecked))
                        return false;
                }
            }
        }
        return true;
    }

    /**
     * Simply reverses this.currentPlayer
     */
    void passTurn() {
        this.currentPlayer = this.currentPlayer == Top ? Bottom : Top;
    }

    interface IToCheck {
        boolean action(int newKingPosition);
    }
}

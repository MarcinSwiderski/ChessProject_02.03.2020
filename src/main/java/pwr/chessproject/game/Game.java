package pwr.chessproject.game;

import pwr.chessproject.api.models.CreateNewGameResponse;
import pwr.chessproject.api.models.MovePlayerResponse;
import pwr.chessproject.api.models.MoveVIResponse;
import pwr.chessproject.api.requests.API;
import pwr.chessproject.frame.TranslateCords;
import pwr.chessproject.models.Figure;
import pwr.chessproject.models.King;
import pwr.chessproject.models.functionalities.IMoveable;
import pwr.chessproject.models.functionalities.NotMoveableException;

import java.io.IOException;
import java.util.*;

import static pwr.chessproject.frame.TranslateCords.*;
import static pwr.chessproject.game.Board.*;
import static pwr.chessproject.models.Figure.Player.*;

public final class Game {
    Board board;

    Hashtable<Figure.Player, Integer> kingPosition = new Hashtable<Figure.Player, Integer>() {
        {
            put(Top, 3);
            put(Bottom, AREA-4);
        }
    };

    Figure.Player currentPlayer = Bottom;

    public Game(Board board) {
        this.board = board;
    }

    /**
     * Starts 'hot-seat' game in IDE output
     */
    public void startHotSeatGame() {
        int position, target;

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println(board);
            try {
                if (isChecked(kingPosition.get(currentPlayer)))
                    System.out.println("Check!");
                System.out.println(currentPlayer + " turn: ");
                System.out.println("Select a figure: ");
                position = translateStringCordToInt(scanner.next().trim().toUpperCase());
                board.checkPosition(position);
                if (Grid[position].player != currentPlayer) {
                    System.out.println("You have to select " + currentPlayer + " player figure");
                    continue;
                }
                System.out.println("Select a target: ");
                target = translateStringCordToInt(scanner.next().trim().toUpperCase());
                if (simulateMoveAndCheck(position, target, () -> isChecked(kingPosition.get(currentPlayer)))) {
                    System.out.println("You can not move into " + translateIntCordToString(target) + " because of check");
                    continue;
                }
                board.moveFigure(position, target);
                if (Grid[target] instanceof King)
                    kingPosition.replace(currentPlayer, target);

                Figure.Player opponent =  this.currentPlayer == Top ? Bottom : Top;
                if (isCheckmated(this.kingPosition.get(opponent)))
                    break;
                passTurn();
            } catch (NullPointerException | IllegalArgumentException | NotMoveableException | ClassCastException ex) {
                System.out.println(ex.getMessage());
            }
        }

        System.out.println(currentPlayer + " WINS");
    }

    /**
     * Starts single player game against https://github.com/anzemur/chess-api in IDE output
     */
    public void startGameAgainstVI() {
        int position, target;
        Scanner scanner = new Scanner(System.in);
        API api = new API();
        try {
            CreateNewGameResponse response = api.createNewGame();
            System.out.println("VI says:\t"+response.getStatus());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Critical error during initial connecting to VI");
            System.exit(1);
        }
        System.out.println("You start as a bottom player");
        while (true) {
            System.out.println(board);
            try {
                if (isChecked(kingPosition.get(currentPlayer)))
                    System.out.println("Check!");
                System.out.println("Your turn");
                System.out.println("Select a figure: ");
                position = translateStringCordToInt(scanner.next().trim().toUpperCase());
                board.checkPosition(position);
                if (Grid[position].player != currentPlayer) {
                    System.out.println("You have to select your figure");
                    continue;
                }
                System.out.println("Select a target: ");
                target = translateStringCordToInt(scanner.next().trim().toUpperCase());
                if (simulateMoveAndCheck(position, target, () -> isChecked(kingPosition.get(currentPlayer)))) {
                    System.out.println("You can not move into " + translateIntCordToString(target) + " because of check");
                    continue;
                }
                board.moveFigure(position, target);
                if (Grid[target] instanceof King)
                    kingPosition.replace(currentPlayer, target);

                Figure.Player opponent =  this.currentPlayer == Top ? Bottom : Top;
                if (isCheckmated(this.kingPosition.get(opponent)))
                    break;
                passTurn();
                api.movePlayer(TranslateCords.translateIntCordToString(position), TranslateCords.translateIntCordToString(target));
                MoveVIResponse moveVIResponse = api.moveVI();
                position = TranslateCords.translateStringCordToInt(moveVIResponse.getFrom());
                target = TranslateCords.translateStringCordToInt(moveVIResponse.getTo());
                board.moveFigure(position, target);
                System.out.println("VI moved from " + moveVIResponse.getFrom() + " to " + moveVIResponse.getTo());
                passTurn();
            } catch (IOException | NullPointerException | IllegalArgumentException | NotMoveableException | ClassCastException ex) {
                System.out.println(ex.getMessage());
            }
        }
        if (this.currentPlayer == Bottom)
            System.out.println("YOU WIN!!!");
        else
            System.out.println("YOU LOOSE :<");
    }

        /*Call call = client.newCall(request);

        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(response.body().string());
            }
        });*/

    /**
     * Simulates move on real Grid and returns callback function result
     * @param position The Figure current position
     * @param target The target position to move to
     * @param toCheck Callback which result will be returned
     * @return Boolean result of the callback
     * @throws NotMoveableException When simulated move is not valid
     */
    boolean simulateMoveAndCheck(int position, int target, IToCheck toCheck) throws NotMoveableException {
        Figure selectedFigure = Grid[position];
        Figure targetFigure = Grid[target];
        int kingPosition = this.kingPosition.get(currentPlayer);
        if (!((IMoveable)selectedFigure).canMove(position, target))
            throw new NotMoveableException(position, target, Board.Grid[position]);
        Grid[position] = null;
        Grid[target] = selectedFigure;
        if (Grid[target] instanceof King)
            this.kingPosition.replace(currentPlayer, target);
        boolean isActionValid = toCheck.action();
        Grid[position] = selectedFigure;
        Grid[target] = targetFigure;
        this.kingPosition.replace(currentPlayer, kingPosition);
        return isActionValid;
    }

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

    boolean isCheckmated (int kingPosition) throws NotMoveableException, ClassCastException {
        if (!isChecked(kingPosition))
            return false;

        IMoveable figure;
        for (int position = 0; position < AREA; position++) {
            if (Grid[position] != null) {
                figure = (IMoveable) Grid[position];
                for (int target: figure.getAvailableFields(position)) {
                    if (simulateMoveAndCheck(position, target, () -> isChecked(kingPosition)))
                        return false;
                }
            }
        }
        return true;
    }

    void passTurn() {
        this.currentPlayer = this.currentPlayer == Top ? Bottom : Top;
    }

    private interface IToCheck {
        boolean action();
    }
}

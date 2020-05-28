//package pwr.chessproject.game;
//
//import pwr.chessproject.api.models.CreateNewGameResponse;
//import pwr.chessproject.api.models.MoveVIResponse;
//import pwr.chessproject.api.requests.API;
//import pwr.chessproject.logger.Logger;
//import pwr.chessproject.models.Figure;
//import pwr.chessproject.models.King;
//import pwr.chessproject.models.functionalities.Movable;
//import pwr.chessproject.models.functionalities.NotMoveableException;
//
//import javax.inject.Inject;
//import javax.naming.OperationNotSupportedException;
//import java.io.IOException;
//import java.rmi.UnexpectedException;
//import java.util.Scanner;
//
//import static pwr.chessproject.frame.TranslateCords;
//import static pwr.chessproject.models.Figure.Player.Bottom;
//import static pwr.chessproject.models.Figure.Player.Top;
//
///**
// * This class is responsible for interactive game experience
// */
//public final class Game {
//
//    @Inject
//    Board board;
//
//    Figure.Player currentPlayer = Bottom;
//
//    /**
//     * Starts 'hot-seat' game in the standard output. Game lasts until someone wins
//     */
//    public void startHotSeatGame() {
//        int position, target;
//
//        Scanner scanner = new Scanner(System.in);
//
//        while (true) {
//            Logger.release(board);
//            try {
//                if (isChecked(board.getKingPosition(currentPlayer), getOpponent())) {
//                    Logger.release("Check!");
//                    if (isCheckmated(board.getKingPosition(currentPlayer)))
//                        break;
//                }
//
//                Logger.release(currentPlayer + " turn: ");
//                Logger.release("Select a figure: ");
//                position = translateStringCordToInt(scanner.next().trim().toUpperCase());
//                board.checkPosition(position);
//                if (board.grid[position].player != currentPlayer) {
//                    Logger.release("You have to select " + currentPlayer + " player figure");
//                    continue;
//                }
//                Logger.release("Select a target: ");
//                target = translateStringCordToInt(scanner.next().trim().toUpperCase());
//                if (simulateMoveAndCheck(position, target, this::isChecked)) {
//                    Logger.release("You can not move into " + translateIntCordToString(target) + " because of check");
//                    continue;
//                }
//                board.moveFigure(position, target);
//                if (board.grid[target] instanceof King)
//                    board.setKingPosition(currentPlayer, target);
//
//                passTurn();
//            } catch (NullPointerException | IllegalArgumentException | NotMoveableException ex) {
//                Logger.release(ex.getMessage());
//                Logger.debug(ex.toString());
//                continue;
//            } catch (UnexpectedException ex) {
//                Logger.release(ex.getMessage() + "\n" + "Ending the game :\\");
//                Logger.debug(ex);
//                return;
//            }
//        }
//
//        Logger.release(currentPlayer + " LOOSES");
//    }
//
//    /**
//     * Starts single player game against https://github.com/anzemur/chess-api in IDE output
//     */
//    public void startGameAgainstVI() {
//        int position, target;
//        Scanner scanner = new Scanner(System.in);
//        Figure.Player opponent;
//        API api = new API();
//        String gameId;
//
//        //Registering new game
//        try {
//            CreateNewGameResponse response = api.createNewGame();
//            gameId = response.getGame_id();
//            Logger.release("VI: You can't beat me you mere mortal. Hahahaha...");
//        } catch (IOException ex) {
//            Logger.debug(ex);
//            Logger.release("Critical error by the server side during initial connection to VI");
//            return;
//        }
//
//        Logger.release("You start as a bottom player");
//        while (true) {
//            Logger.release(board);
//            try {
//                if (isChecked(board.getKingPosition(currentPlayer), getOpponent()))
//                    Logger.release("Check!");
//                Logger.release("Your turn");
//                Logger.release("Select a figure: ");
//                position = translateStringCordToInt(scanner.next().trim().toUpperCase());
//                board.checkPosition(position);
//                if (board.grid[position].player != currentPlayer) {
//                    Logger.release("You have to select your figure");
//                    continue;
//                }
//                Logger.release("Select a target: ");
//                target = translateStringCordToInt(scanner.next().trim().toUpperCase());
//                if (simulateMoveAndCheck(position, target, this::isChecked)) {
//                    Logger.release("You can not move into " + translateIntCordToString(target) + " because of check");
//                    continue;
//                }
//                board.moveFigure(position, target);
//                if (board.grid[target] instanceof King)
//                    kingPosition.replace(currentPlayer, target);
//
//                opponent =  this.currentPlayer == Top ? Bottom : Top;
//                if (isCheckmated(this.kingPosition.get(opponent)))
//                    break;
//                passTurn();
//                api.movePlayer(gameId, translateIntCordToString(position), translateIntCordToString(target));
//                MoveVIResponse moveVIResponse = api.moveVI(gameId);
//                position = translateStringCordToInt(moveVIResponse.getFrom());
//                target = translateStringCordToInt(moveVIResponse.getTo());
//                Board.forceMoveFigure(position, target);
//                if (board.grid[target] instanceof King)
//                    kingPosition.replace(currentPlayer, target);
//                Logger.release("VI moved from " + moveVIResponse.getFrom() + " to " + moveVIResponse.getTo());
//                opponent =  this.currentPlayer == Top ? Bottom : Top;
//                if (isCheckmated(this.kingPosition.get(opponent)))
//                    break;
//                passTurn();
//            } catch (NullPointerException | IllegalArgumentException | NotMoveableException ex) {
//                Logger.release(ex.getMessage());
//                Logger.debug(ex);
//                continue;
//            } catch (IOException | OperationNotSupportedException ex) {
//                Logger.release(ex.getMessage() + "\n" + "Ending the game :\\");
//                Logger.debug(ex);
//                return;
//            }
//        }
//        if (this.currentPlayer == Bottom)
//            Logger.release("YOU WIN!!!");
//        else
//            Logger.release("YOU LOOSE :<");
//    }
//
//    /**
//     * Simulates move on real Grid then returns callback function result
//     * @param position The Figure current position
//     * @param target The target position to move to
//     * @param toCheck Callback which result will be returned
//     * @return Boolean result of the callback
//     * @throws NotMoveableException - When simulated move is not valid
//     */
//    boolean simulateMoveAndCheck(int position, int target, IToCheck toCheck) throws NotMoveableException, UnexpectedException {
//        Figure selectedFigure;
//        Figure targetFigure;
//        try {
//            selectedFigure = Grid[position].clone();
//            if (Grid[target] == null )
//                targetFigure = null;
//            else
//                targetFigure = Grid[target].clone();
//        } catch (CloneNotSupportedException ex) {
//            Logger.debug(ex);
//            Logger.release("Unexpected error during validating a move. Program needs to be shut down.");
//            System.exit(1);
//            return false;
//        }
//
//
//        int oldKingPosition = this.kingPosition.get(currentPlayer);
//
//        Board.moveFigure(position, target);
//        if (selectedFigure instanceof King)
//            this.kingPosition.replace(currentPlayer, target);
//
//        boolean isActionValid = toCheck.action(this.kingPosition.get(currentPlayer));
//
//        Grid[position] = selectedFigure;
//        Grid[target] = targetFigure;
//        this.kingPosition.replace(currentPlayer, oldKingPosition);
//        return isActionValid;
//    }
//
//    /**
//     * Checks whether specified position is under check from specified opponent in current board
//     * @param positionToVerify Position to check
//     * @param opponent Determines players whose figure can move into positionToVerify
//     * @return True when checked, else false
//     */
//    boolean isChecked(int positionToVerify, Figure.Player opponent) throws UnexpectedException {
//        Figure figure;
//        for (int i = 0; i < AREA; i++) {
//            figure = Grid[i];
//            if (figure != null && figure.player == opponent) {
//                if (((Movable)figure).canMove(i, kingPosition))
//                    return true;
//            }
//        }
//        return false;
//    }
//
//    /**
//     * Iterate through all possible movements of all current players figures to verify if king at parameter position have any option to not be checked
//     * @param kingPosition Position to check
//     * @return True when checked-mated, else false
//     */
//    boolean isCheckmated (int kingPosition) throws NotMoveableException, UnexpectedException {
//        if (!(Grid[kingPosition] instanceof King))
//            throw new UnexpectedException("Field does not contain king.");
//        if (!isChecked(kingPosition))
//            return false;
//
//        Figure.Player player = Grid[kingPosition].player;
//
//        Movable figure;
//        for (int position = 0; position < AREA; position++) {
//            if (Grid[position] != null && Grid[position].player == player) {
//                figure = (Movable) Grid[position];
//                for (int target: figure.getAvailableFields(position)) {
//                    if (!simulateMoveAndCheck(position, target, this::isChecked))
//                        return false;
//                }
//            }
//        }
//        return true;
//    }
//
//    /**
//     * Simply reverses this.currentPlayer
//     */
//    void passTurn() {
//        this.currentPlayer = this.currentPlayer == Top ? Bottom : Top;
//    }
//
//    Figure.Player getOpponent() {
//        return this.currentPlayer == Top ? Bottom : Top;
//    }
//
//    Figure.Player getCurrentPlayer() {
//        return this.currentPlayer;
//    }
//
//    private interface IToCheck {
//        boolean action(int newKingPosition) throws UnexpectedException;
//    }
//}

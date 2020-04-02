package pwr.chessproject.game;

import pwr.chessproject.models.Figure;
import pwr.chessproject.models.King;
import pwr.chessproject.models.Pawn;
import pwr.chessproject.models.functionalities.IMoveable;
import pwr.chessproject.models.functionalities.NotMoveableException;

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

    Figure.Player currentPlayer = Top;

    public Game(Board board) {
        this.board = board;
    }

    public void startGame() {
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

                if (isCheckmated(this.kingPosition.get(currentPlayer)))
                    break;
                passTurn();
            } catch (NullPointerException | IllegalArgumentException | NotMoveableException | ClassCastException ex) {
                System.out.println(ex.getMessage());
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                System.out.println("Press any key and enter to close");
                scanner.next();
                break;
            }
        }

        System.out.println(this.kingPosition.get(currentPlayer).toString().toUpperCase() + " PLAYER LOST");
    }

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

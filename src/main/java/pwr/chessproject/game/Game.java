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
                System.out.println(kingPosition.get(currentPlayer));
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
                passTurn();
            } catch (NullPointerException | IllegalArgumentException | NotMoveableException ex) {
                System.out.println(ex.getMessage());
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                System.out.println("Press any key and enter to close");
                scanner.next();
                break;
            }
        }
    }

    boolean simulateMoveAndCheck(int position, int target, IToCheck toCheck) throws NotMoveableException {
        Figure selectedFigure = Grid[position];
        Figure targetFigure = Grid[target];
        if (!((IMoveable)selectedFigure).canMove(position, target))
            throw new NotMoveableException(position, target, Board.Grid[position]);
        Grid[position] = null;
        Grid[target] = selectedFigure;
        boolean isActionValid = toCheck.action();
        Grid[position] = selectedFigure;
        Grid[target] = targetFigure;
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

    boolean isCheckmated (int kingPosition) {
        //todo
        if (!isChecked(kingPosition))
            return false;
        King king;
        if (Grid[kingPosition] instanceof King) {
            king = (King) Grid[kingPosition];
        }
        else throw new ClassCastException("Could not find the King");
        for (int target: king.getAvailableFields(kingPosition)) {
            if (king.canMove(kingPosition, target) && !isChecked(target))
                return false;
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

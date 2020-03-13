package pwr.chessproject.game;

import pwr.chessproject.frame.TranslateCords;
import pwr.chessproject.models.Figure;
import pwr.chessproject.models.functionalities.IMoveable;
import pwr.chessproject.models.functionalities.NotMoveableException;

import javax.naming.OperationNotSupportedException;
import java.util.*;

import static pwr.chessproject.game.Board.*;
import static pwr.chessproject.models.Figure.Player.*;

public class Game {
    private Board board;

    private Hashtable<Figure.Player, Integer> kingPosition = new Hashtable<Figure.Player, Integer>() {
        {
            put(Top, 3);
            put(Bottom, 4);
        }
    };

    private Figure.Player currentPlayer = Top;

    public Game(Board board) {
        this.board = board;
    }

    public void startGame() {
        int position, target;

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println(board);

            try {
                System.out.println(currentPlayer + " turn: ");
                System.out.println("Select a figure: ");
                position = TranslateCords.translateStringCordToInt(scanner.next().trim().toUpperCase());
                board.checkPosition(position);
                if (Grid[position].player != currentPlayer) {
                    System.out.println("You have to select " + currentPlayer + " player figure");
                    continue;
                }
                else if (isMate(kingPosition.get(currentPlayer)) && Grid[position].figureType != Figure.FigureType.King) {
                    System.out.println("You are under mate. You have to select king");
                    continue;
                }
                System.out.println("Select a target: ");
                target = TranslateCords.translateStringCordToInt(scanner.next().trim().toUpperCase());
                if (isMate(kingPosition.get(currentPlayer)) && isMate(target)) {
                    System.out.println("You can not move into " + TranslateCords.translateIntCordToString(target) + " because of mate");
                    continue;
                }
                board.moveFigure(position, target);
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

    private boolean isMate (int kingPosition) {
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

    private boolean isGameFinished (int kingPosition) {
        //todo winning conditions
        Figure.Player opponent =  this.currentPlayer == Top ? Bottom : Top;
        Figure figure;
        for (int i = 0; i < AREA; i++) {
            figure = Grid[i];
            if (figure != null && figure.player == opponent) {
                if (((IMoveable)figure).canMove(i, kingPosition))
                    return false;
            }
        }
        return true;
    }

    private void passTurn() {
        this.currentPlayer = this.currentPlayer == Top ? Bottom : Top;
    }

}

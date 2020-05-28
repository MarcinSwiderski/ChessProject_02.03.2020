package pwr.chessproject.models;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pwr.chessproject.game.Board;
import pwr.chessproject.models.functionalities.Movable;

import static org.junit.jupiter.api.Assertions.*;
import static pwr.chessproject.game.Board.*;
import static pwr.chessproject.models.Figure.Player.Bottom;
import static pwr.chessproject.models.Figure.Player.Top;

@ExtendWith(FigureMoveExtension.class)
class PawnTest {

    private final Board board;

    public PawnTest(Board board) {
        this.board = board;
    }

    @Test
    void bottomCanMoveOnlyOneOrTwoUpwards() {
        int botPosition = board.getArea()-1-board.getColumns();
        board.grid[botPosition] = new Pawn(Bottom);
        Movable pawn = board.grid[botPosition];
        Assertions.assertAll(
                () -> assertTrue(pawn.canMove(botPosition, botPosition-board.getColumns(), board)),
                () -> assertTrue(pawn.canMove(botPosition, botPosition-2*board.getColumns(), board)),
                () -> assertFalse(pawn.canMove(botPosition, botPosition, board)),
                () -> assertFalse(pawn.canMove(botPosition, botPosition+1, board)),
                () -> assertFalse(pawn.canMove(botPosition, botPosition-1, board)),
                () -> assertFalse(pawn.canMove(botPosition, botPosition-3*board.getColumns(), board)),
                () -> assertFalse(pawn.canMove(botPosition, botPosition+board.getColumns(), board))
        );
    }

    @Test
    void topCanMoveOnlyOneOrTwoDownwards() {
        int topPosition = 1 + board.getColumns();
        board.grid[topPosition] = new Pawn(Top);
        Movable pawn = board.grid[topPosition];
        Assertions.assertAll(
                () -> assertTrue(pawn.canMove(topPosition, topPosition+board.getColumns(), board)),
                () -> assertTrue(pawn.canMove(topPosition, topPosition+2*board.getColumns(), board)),
                () -> assertFalse(pawn.canMove(topPosition, topPosition, board)),
                () -> assertFalse(pawn.canMove(topPosition, topPosition+1, board)),
                () -> assertFalse(pawn.canMove(topPosition, topPosition-1, board)),
                () -> assertFalse(pawn.canMove(topPosition, topPosition+3*board.getColumns(), board)),
                () -> assertFalse(pawn.canMove(topPosition, topPosition-board.getColumns(), board))
        );
    }

    @Test
    void pawnCanNotMoveThroughObstacles() {

        board.grid[0] = new Pawn(Top);
        board.grid[1] = new Pawn(Top);

        //obstacles
        board.grid[board.getColumns()] = new Pawn(Top);
        board.grid[2*board.getColumns()] = new Pawn(Top);
        board.grid[1+board.getColumns()] = new Pawn(Top);

        Movable pawn1 = board.grid[0];
        Movable pawn2 = board.grid[1];

        Assertions.assertAll(
                () -> assertFalse(pawn1.canMove(0, board.getColumns(), board)),
                () -> assertFalse(pawn1.canMove(0, 2*board.getColumns(), board)),
                () -> assertFalse(pawn2.canMove(1, 1+2*board.getColumns(), board))
                );
    }

    @Test
    void pawnCanKill() {
        board.grid[1] = new Pawn(Top);
        board.grid[board.getColumns()] = new Pawn(Bottom);
        board.grid[board.getColumns()+2] = new Pawn(Bottom);
        Movable pawn = board.grid[1];
        Assertions.assertAll(
                () -> assertTrue(pawn.canMove(1, board.getColumns(), board)),
                () -> assertTrue(pawn.canMove(1, board.getColumns()+2, board))
        );
    }

    @Test
    void pawnCanMoveTwoOnlyOnce() {
        board.grid[0] = new Pawn(Top);
        Pawn pawn = (Pawn) board.grid[0];
        pawn.afterFirstMoveIndicator();
        assertFalse(pawn.canMove(0, 2*board.getColumns(), board));
    }

}
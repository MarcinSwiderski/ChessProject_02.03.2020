package pwr.ChessProject.models;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pwr.ChessProject.models.functionalities.IMoveable;

import static org.junit.jupiter.api.Assertions.*;
import static pwr.ChessProject.board.Board.Grid;
import static pwr.ChessProject.models.Figure.Player.Bottom;
import static pwr.ChessProject.models.Figure.Player.Top;

@ExtendWith(FigureMoveExtension.class)
class PawnTest {

    @Test
    void bottomCanMoveOnlyOneOrTwoUpwards() {
        Grid[48] = new Pawn(Bottom);
        IMoveable pawn = (IMoveable)Grid[48];
        Assertions.assertAll(
                () -> assertTrue(pawn.canMove(48, 40)),
                () -> assertTrue(pawn.canMove(48, 32)),
                () -> assertFalse(pawn.canMove(48, 56)),
                () -> assertFalse(pawn.canMove(48, 57)),
                () -> assertFalse(pawn.canMove(48, 49)),
                () -> assertFalse(pawn.canMove(48, 41)),
                () -> assertFalse(pawn.canMove(48, 24)),
                () -> assertFalse(pawn.canMove(48, 48))
        );
    }

    @Test
    void topCanMoveOnlyOneOrTwoDownwards() {
        Grid[8] = new Pawn(Top);
        IMoveable pawn = (IMoveable)Grid[8];
        Assertions.assertAll(
                () -> assertTrue(pawn.canMove(8, 16)),
                () -> assertTrue(pawn.canMove(8, 24)),
                () -> assertFalse(pawn.canMove(8, 0)),
                () -> assertFalse(pawn.canMove(8, 1)),
                () -> assertFalse(pawn.canMove(8, 9)),
                () -> assertFalse(pawn.canMove(8, 17)),
                () -> assertFalse(pawn.canMove(8, 32)),
                () -> assertFalse(pawn.canMove(8, 8))
                );
    }

    @Test
    void pawnCanNotMoveThroughObstacles() {

        Grid[48] = new Pawn(Bottom);
        Grid[49] = new Pawn(Bottom);

        //obstacles
        Grid[40] = new Pawn(Bottom);
        Grid[32] = new Pawn(Bottom);
        Grid[41] = new Pawn(Top);

        IMoveable pawn1 = (IMoveable)Grid[48];
        IMoveable pawn2 = (IMoveable)Grid[49];

        Assertions.assertAll(
                () -> assertFalse(pawn1.canMove(48, 40)),
                () -> assertFalse(pawn1.canMove(48, 32)),
                () -> assertFalse(pawn2.canMove(49, 33))
                );
    }

    @Test
    void pawnCanKill() {
        Grid[8] = new Pawn(Top);
        Grid[16] = new Pawn(Bottom);
        IMoveable pawn = (IMoveable)Grid[8];
        Assertions.assertAll(
                () -> assertTrue(pawn.canMove(8, 16)),
                () -> assertFalse(pawn.canMove(8, 24))
        );
    }

    @Test
    void pawnCanMoveTwoOnlyOnce() {
        Grid[8] = new Pawn(Top);
        Pawn pawn = (Pawn) Grid[8];
        pawn.afterFirstMove();
        assertFalse(pawn.canMove(8, 24));
    }

}
package pwr.chessproject.models;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pwr.chessproject.models.functionalities.Movable;

import static org.junit.jupiter.api.Assertions.*;
import static pwr.chessproject.game.Board.*;
import static pwr.chessproject.models.Figure.Player.Bottom;
import static pwr.chessproject.models.Figure.Player.Top;

@ExtendWith(FigureMoveExtension.class)
class PawnTest {

    @Test
    void bottomCanMoveOnlyOneOrTwoUpwards() {
        int botPosition = AREA-1-COLUMNS;
        Grid[botPosition] = new Pawn(Bottom);
        Movable pawn = (Movable)Grid[botPosition];
        Assertions.assertAll(
                () -> assertTrue(pawn.canMove(botPosition, botPosition-COLUMNS)),
                () -> assertTrue(pawn.canMove(botPosition, botPosition-2*COLUMNS)),
                () -> assertFalse(pawn.canMove(botPosition, botPosition)),
                () -> assertFalse(pawn.canMove(botPosition, botPosition+1)),
                () -> assertFalse(pawn.canMove(botPosition, botPosition-1)),
                () -> assertFalse(pawn.canMove(botPosition, botPosition-3*COLUMNS)),
                () -> assertFalse(pawn.canMove(botPosition, botPosition+COLUMNS))
        );
    }

    @Test
    void topCanMoveOnlyOneOrTwoDownwards() {
        int topPosition = 1 + COLUMNS;
        Grid[topPosition] = new Pawn(Top);
        Movable pawn = (Movable)Grid[topPosition];
        Assertions.assertAll(
                () -> assertTrue(pawn.canMove(topPosition, topPosition+COLUMNS)),
                () -> assertTrue(pawn.canMove(topPosition, topPosition+2*COLUMNS)),
                () -> assertFalse(pawn.canMove(topPosition, topPosition)),
                () -> assertFalse(pawn.canMove(topPosition, topPosition+1)),
                () -> assertFalse(pawn.canMove(topPosition, topPosition-1)),
                () -> assertFalse(pawn.canMove(topPosition, topPosition+3*COLUMNS)),
                () -> assertFalse(pawn.canMove(topPosition, topPosition-COLUMNS))
        );
    }

    @Test
    void pawnCanNotMoveThroughObstacles() {

        Grid[0] = new Pawn(Top);
        Grid[1] = new Pawn(Top);

        //obstacles
        Grid[COLUMNS] = new Pawn(Top);
        Grid[2*COLUMNS] = new Pawn(Top);
        Grid[1+COLUMNS] = new Pawn(Top);

        Movable pawn1 = (Movable)Grid[0];
        Movable pawn2 = (Movable)Grid[1];

        Assertions.assertAll(
                () -> assertFalse(pawn1.canMove(0, COLUMNS)),
                () -> assertFalse(pawn1.canMove(0, 2*COLUMNS)),
                () -> assertFalse(pawn2.canMove(1, 1+2*COLUMNS))
                );
    }

    @Test
    void pawnCanKill() {
        Grid[1] = new Pawn(Top);
        Grid[COLUMNS] = new Pawn(Bottom);
        Grid[COLUMNS+2] = new Pawn(Bottom);
        Movable pawn = (Movable)Grid[1];
        Assertions.assertAll(
                () -> assertTrue(pawn.canMove(1, COLUMNS)),
                () -> assertTrue(pawn.canMove(1, COLUMNS+2))
        );
    }

    @Test
    void pawnCanMoveTwoOnlyOnce() {
        Grid[0] = new Pawn(Top);
        Pawn pawn = (Pawn) Grid[0];
        pawn.afterFirstMoveIndicator();
        assertFalse(pawn.canMove(0, 2*COLUMNS));
    }

}
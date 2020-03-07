package pwr.ChessProject.models.functionalities;

import pwr.ChessProject.models.Figure;

public class NotMoveableException extends Exception {
    public NotMoveableException() {
    }

    public NotMoveableException(int position, int target, Figure selectedFigure) {
        super("Can not move " + selectedFigure.figureType + " from " + position + " to " + target);
    }

    public NotMoveableException(int position, int target) {
        super("Can not move figure from " + position + " to " + target);
    }
}

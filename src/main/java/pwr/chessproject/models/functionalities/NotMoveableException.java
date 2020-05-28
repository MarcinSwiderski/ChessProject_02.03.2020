package pwr.chessproject.models.functionalities;

import pwr.chessproject.models.Figure;

/**
 * Exception thrown when a figure can not move
 */
public class NotMoveableException extends Exception {

    public NotMoveableException() {
        super("Figure's movement rules do not allow such move");
    }

    /**
     * @param position The Figure current position
     * @param target The target position to which movement is impossible
     * @param selectedFigure The Figure to move
     */
    public NotMoveableException(String position, String target, Figure selectedFigure) {
        super(String.format("Can not move %s from %s to %s", selectedFigure.figureType, position, target));
    }

    /**
     * @param position The Figure current position
     * @param target The target position to which movement is impossible
     */
    public NotMoveableException(String position, String target) {
        super(String.format("Can not move figure from %s to %s", position, target));
    }

    public NotMoveableException(String message) {
        super(message);
    }
}

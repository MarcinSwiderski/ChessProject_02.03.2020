package pwr.chessproject.models.functionalities;

import pwr.chessproject.frame.TranslateCords;
import pwr.chessproject.models.Figure;

/**
 * Exception thrown when a figure can not move
 */
public class NotMoveableException extends Exception {
    public NotMoveableException() {
        super("Can not move figure");
    }

    /**
     * @param position The Figure current position
     * @param target The target position to which movement is impossible
     * @param selectedFigure The Figure to move
     */
    public NotMoveableException(int position, int target, Figure selectedFigure) {
        super(String.format("Can not move %s from %s to %s", selectedFigure.figureType, TranslateCords.translateIntCordToString(position), TranslateCords.translateIntCordToString(target)));
    }

    /**
     * @param position The Figure current position
     * @param target The target position to which movement is impossible
     */
    public NotMoveableException(int position, int target) {
        super(String.format("Can not move figure from %s to %s", TranslateCords.translateIntCordToString(position), TranslateCords.translateIntCordToString(target)));
    }
}

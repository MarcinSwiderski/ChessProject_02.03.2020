package pwr.ChessProject.models.functionalities;

import pwr.ChessProject.Frame.TranslateCords;
import pwr.ChessProject.models.Figure;

public class NotMoveableException extends Exception {
    public NotMoveableException() {
    }

    public NotMoveableException(int position, int target, Figure selectedFigure) {
        super("Can not move " + selectedFigure.figureType + " from " + TranslateCords.translateIntCordToString(position) + " to " + TranslateCords.translateIntCordToString(target));
    }

    public NotMoveableException(int position, int target) {
        super("Can not move figure from " + TranslateCords.translateIntCordToString(position) + " to " + TranslateCords.translateIntCordToString(target));
    }
}

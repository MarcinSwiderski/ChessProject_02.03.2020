package pwr.chessproject.frame;

import pwr.chessproject.game.Board;

public class TranslateCords {

    private Board board;

    public TranslateCords(Board board) {
        this.board = board;
    }

    public String translateIntCordToString(int cord) throws IllegalArgumentException {
        if (cord < 0 || cord > board.getArea() - 1)
            throw new IllegalArgumentException("Can not select figure outside of the board");
        char row = (char)((board.getRows()-cord/ board.getRows()) + 48);
        char column = (char)(cord%board.getColumns() + 65);
        return String.valueOf(column) + row;
    }

    public int translateStringCordToInt(String cords) throws IllegalArgumentException {
        if (cords.length() < 2)
            throw new IllegalArgumentException("Not enough cords in: '" + cords + "'");
        char[] arrayOfCords = cords.toUpperCase().toCharArray();
        int column = arrayOfCords[0] - 65;
        int row = arrayOfCords[1] - 48;
        if (column < 0 || column > board.getColumns())
            throw new IllegalArgumentException(arrayOfCords[0] + " is not in range ");
        if (row < 0 || row > board.getRows())
            throw new IllegalArgumentException(arrayOfCords[1] + " is not in range ");
        return (board.getArea() - row*board.getRows()) + column;
    }
}

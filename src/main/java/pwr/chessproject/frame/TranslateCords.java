package pwr.chessproject.frame;

import static pwr.chessproject.game.Board.*;

public class TranslateCords {

    public static String translateIntCordToString(int cord) throws IllegalArgumentException {
        if (cord < 0 || cord > AREA - 1)
            throw new IllegalArgumentException("Can not select figure outside of the board");
        char row = (char)((ROWS-cord/ ROWS) + 48);
        char column = (char)(cord%COLUMNS + 65);
        return String.valueOf(column) + row;
    }

    public static int translateStringCordToInt(String cords) throws IllegalArgumentException {
        if (cords.length() != 2)
            throw new IllegalArgumentException("Not enough or too many cords in: '" + cords + "'");
        char[] arrayOfCords = cords.toUpperCase().toCharArray();
        int column = arrayOfCords[0] - 65;
        int row = arrayOfCords[1] - 48;
        if (column < 0 || column > COLUMNS)
            throw new IllegalArgumentException(arrayOfCords[0] + " is not in range ");
        if (row < 0 || row > ROWS)
            throw new IllegalArgumentException(arrayOfCords[1] + " is not in range ");
        return (AREA - row*ROWS) + column;
    }
}

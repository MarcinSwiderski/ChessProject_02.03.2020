package pwr.ChessProject.Frame;

public class TranslateCords {

    public static String translateIntCordToString(int cord) throws IllegalArgumentException {
        if (cord < 0 || cord > 63)
            throw new IllegalArgumentException("Can not select figure outside of the board");
        char row = (char)((8-cord/8) + 48);
        char column = (char)(cord%8 + 65);
        return String.valueOf(column) + row;
    }

    public static int translateStringCordToInt(String cords) throws IllegalArgumentException {
        if (cords.length() != 2)
            throw new IllegalArgumentException("Not enough or too many cords in: '" + cords + "'");
        char[] arrayOfCords = cords.toCharArray();
        int column = arrayOfCords[0] - 65;
        int row = arrayOfCords[1] - 48;
        if (column < 0 || column > 8)
            throw new IllegalArgumentException(arrayOfCords[0] + " is not in range A-H");
        if (row < 0 || row > 8)
            throw new IllegalArgumentException(arrayOfCords[1] + " is not in range 1-8");
        return (64 - row*8) + column;
    }
}

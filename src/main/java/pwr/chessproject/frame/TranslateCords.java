package pwr.chessproject.frame;

import pwr.chessproject.game.Board;

/**
 * Class used to translate chess real coordinates into one-dimensional logic representation (and vice versa) on the specified board
 */
public class TranslateCords {

    private final Board board;

    /**
     * Constructs default translator of the specified board
     * @param board Board which translations will base on. Only it size matters
     * @throws IllegalArgumentException When provided board or it's grid is uninitialized
     */
    public TranslateCords(Board board) {
        if (board == null || board.grid == null)
            throw new IllegalArgumentException("Can not translate from uninitialized board.");
        if (board.getRows() > 9 || board.getColumns() > 9)
            throw new UnsupportedOperationException("The thing does not works for 2 digit numbers right now...");
        this.board = board;
    }

    /**
     * Translate numeric representation into real board set of coordinates
     * @param cord Logical numeric representation of position on the board
     * @return Real board cords
     * @throws IllegalArgumentException When cord is too small or too big
     */
    public String translateIntCordToString(int cord) throws IllegalArgumentException {
        if (cord < 0 || cord > board.getArea() - 1)
            throw new IllegalArgumentException("Can not select figure outside of the board");
        char row = (char)((board.getRows()-cord/ board.getRows()) + 48);
        char column = (char)(cord%board.getColumns() + 65);
        return String.valueOf(column) + row;
    }

    /**
     * Translate real board set of coordinates into numeric representation
     * @param cords Real board set of coordinates of position on the board
     * @return Numeric representation
     * @throws IllegalArgumentException When cords do not fit the board
     */
    public int translateStringCordToInt(String cords) throws IllegalArgumentException {
        if (cords.length() < 2)
            throw new IllegalArgumentException("Bad cords in: '" + cords + "'");
        char[] arrayOfCords = cords.toUpperCase().toCharArray();
        int column = arrayOfCords[0] - 65;
        int row = arrayOfCords[1] - 48;
        if (column < 0 || column > board.getColumns())
            throw new IllegalArgumentException("Column cord is not in range ");
        if (row < 0 || row > board.getRows())
            throw new IllegalArgumentException("Row cord is not in range ");
        return (board.getArea() - row*board.getRows()) + column;
    }
}

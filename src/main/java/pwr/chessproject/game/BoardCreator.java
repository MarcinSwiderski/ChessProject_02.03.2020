package pwr.chessproject.game;

import java.io.IOException;

public class BoardCreator {

    /**
     * Creates new custom board with specified grid size
     * @param rows Number of rows
     * @param columns Number of columns
     * @throws IllegalArgumentException - When specified parameters are below 8
     */
    public Board customEmptyBoard(int rows, int columns) throws IllegalArgumentException {
        if (rows < 8 || columns < 8)
            throw new IllegalArgumentException("Board is too small");
        else
            return new Board(rows, columns);
    }

    /**
     * Creates new board and initializes it with default figures setup from ~/Boards/default.board
     * @return Board with nicely positioned figures
     */
    public Board defaultBoard() throws IOException {
        BoardLoader boardLoader = new BoardLoader();
        return boardLoader.getBoardFromFile("default");

        //Manual step by step board initializing
        /*Board board = new Board(8, 8);
        int currentPosition;
        Figure.Player player;
        for (int row = 0; row < board.getRows(); row++) {
            player = row < 4 ? Figure.Player.Top : Figure.Player.Bottom;
            for (int column = 0; column < board.getColumns(); column++) {
                currentPosition = row*board.getColumns()+column;
                if (row == 1 || row == 6)
                    board.grid[currentPosition] = new Pawn(player);
                else if (currentPosition == 0 || currentPosition == 7 || currentPosition == 56 || currentPosition == 63)
                    board.grid[currentPosition] = new Tower(player);
                else if (currentPosition == 1 || currentPosition == 6 || currentPosition == 57 || currentPosition == 62)
                    board.grid[currentPosition] = new Knight(player);
                else if (currentPosition == 2 || currentPosition == 5 || currentPosition == 58 || currentPosition == 61)
                    board.grid[currentPosition] = new Bishop(player);
                else if (currentPosition == 4 || currentPosition == 60)
                    board.grid[currentPosition] = new King(player);
                else if (currentPosition == 3 || currentPosition == 59)
                    board.grid[currentPosition] = new Queen(player);
                else
                    board.grid[currentPosition] = null;
            }
        }
        return board;*/
    }
}



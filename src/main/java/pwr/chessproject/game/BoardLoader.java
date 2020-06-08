package pwr.chessproject.game;

import pwr.chessproject.models.*;

import java.io.*;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * Loads and safes boards from into files
 */
public class BoardLoader {

    /**
     * Converts board provided as a parameter into text and saves it in provided file
     * @param board A board to save
     * @param file File to save into
     */
    private void gridToFile(Board board, File file) throws IOException {
        FileWriter writer = new FileWriter(file);
        writer.write(board.getRows() + "\t" + board.getColumns() + "\n");
        for (int row = 0; row*board.getColumns() < board.grid.length ; row++) {
            for (int column = 0; column < board.getColumns(); column++) {
                Figure figure = board.grid[row*board.getColumns() + column];
                if (figure != null)
                    writer.append(figure.toString());
                else
                    writer.append("null");
                writer.append("\t");
            }
            writer.append("\n");
        }
        writer.close();
    }

    /**
     * Saves board into Board/[name].board file with provided name
     * @param board A board to save
     * @param name Name of the file that will be created without extension
     * @throws IOException - When file already exists
     * @throws NullPointerException - When board is uninitialized
     */
    public void saveBoard(Board board, String name) throws IOException, NullPointerException {
        if (board == null || board.grid == null)
            throw new NullPointerException("Board is uninitialized");

        File file = new File(Path.of("Boards", name+".board").toString());

        if (file.createNewFile()) {
            gridToFile(board, file);
        }
        else
            throw new IOException("File already exists.");
    }

    /**
     * Reads file content and returns equivalent board
     * @param stream *.board file containing board setup
     * @return Grid base on provided file
     * @throws NumberFormatException When board file is corrupted
     */
    private Board fileToBoard(InputStream stream) throws NumberFormatException {
        Scanner scanner = new Scanner(stream);

        int rows = Integer.parseInt(scanner.next());
        int columns = Integer.parseInt(scanner.next());

        Board board = new Board(rows, columns);

        String word;
        Figure figure;
        Figure.Player player;
        for (int currentPosition = 0; currentPosition < board.getArea(); currentPosition++) {
            word = scanner.next();
            if (word.contains(Figure.Player.Bottom.toString()))
                player = Figure.Player.Bottom;
            else
                player = Figure.Player.Top;

            if (word.contains(Figure.FigureType.King.toString())) {
                figure = new King(player);
                board.setKingPosition(player, currentPosition);
            }
            else if (word.contains(Figure.FigureType.Queen.toString()))
                figure = new Queen(player);
            else if (word.contains(Figure.FigureType.Bishop.toString()))
                figure = new Bishop(player);
            else if (word.contains(Figure.FigureType.Knight.toString()))
                figure = new Knight(player);
            else if (word.contains(Figure.FigureType.Tower.toString()))
                figure = new Tower(player);
            else if (word.contains(Figure.FigureType.Pawn.toString()))
                figure = new Pawn(player);
            else
                figure = null;
            board.grid[currentPosition] = figure;
        }
        return board;
    }

    /**
     * Finds a file in Boards folder in program directory and returns board it represents
     * @param name Name of the file without .board extension
     * @return Representation of the board saved in specified file
     * @throws FileNotFoundException When specified name does not correspond to any file
     */
    public Board getBoardFromInsideFile(String name) throws IOException {
        InputStream inputStream;
        try {
            inputStream = this.getClass().getResourceAsStream("/Boards/"+name+".board");
        } catch (Exception ex) {
            throw new FileNotFoundException("No such file: "+name);
        }

        return fileToBoard(inputStream);
    }
}

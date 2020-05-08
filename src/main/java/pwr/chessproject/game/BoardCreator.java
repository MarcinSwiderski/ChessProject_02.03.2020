package pwr.chessproject.game;

import pwr.chessproject.models.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

import static pwr.chessproject.game.Board.*;

public class BoardCreator {

    /**
     * Creates custom board with specified size and initializes it with figures if size is 8x8.
     * Updates global ROWS, COLUMNS and AREA
     * @param rows Number of rows
     * @param columns Number of columns
     * @throws IllegalArgumentException - When specified parameters are below 8
     */
    public Figure[] customEmptyBoard(int rows, int columns) throws IllegalArgumentException {
        if (rows < 8 || columns < 8)
            throw new IllegalArgumentException("Board is too small");
        else if (rows == 8 && columns == 8) {
            return constructDefaultBoard();
        }
        else {
            ROWS = rows;
            COLUMNS = columns;
            AREA = ROWS*COLUMNS;
            return new Figure[AREA];
        }
    }

    /**
     * Initializes Grid with default 8x8 board and sets figures
     * @return Grid with nicely positioned figures
     * @throws IllegalArgumentException When board is not 8x8
     */
    public Figure[] constructDefaultBoard() throws IllegalArgumentException {
        ROWS = 8;
        COLUMNS = 8;
        AREA = ROWS*COLUMNS;
        Figure[] grid = new Figure[AREA];
        int currentPosition;
        Figure.Player player;
        for (int row = 0; row < ROWS; row++) {
            player = row < 4 ? Figure.Player.Top : Figure.Player.Bottom;
            for (int column = 0; column < COLUMNS; column++) {
                currentPosition = row*COLUMNS+column;
                if (row == 1 || row == 6)
                    grid[currentPosition] = new Pawn(player);
                else if (currentPosition == 0 || currentPosition == 7 || currentPosition == 56 || currentPosition == 63)
                    grid[currentPosition] = new Tower(player);
                else if (currentPosition == 1 || currentPosition == 6 || currentPosition == 57 || currentPosition == 62)
                    grid[currentPosition] = new Knight(player);
                else if (currentPosition == 2 || currentPosition == 5 || currentPosition == 58 || currentPosition == 61)
                    grid[currentPosition] = new Bishop(player);
                else if (currentPosition == 4 || currentPosition == 60)
                    grid[currentPosition] = new King(player);
                else if (currentPosition == 3 || currentPosition == 59)
                    grid[currentPosition] = new Queen(player);
                else
                    grid[currentPosition] = null;
            }
        }
        return grid;
    }

    /**
     * Converts grid provided as a parameter into text and saves it in provided file
     * @param Grid A grid to save
     * @param file File to save into
     */
    private void gridToFile(Figure[] Grid, File file) throws IOException {
        FileWriter writer = new FileWriter(file);
        writer.write(ROWS + "\t" + COLUMNS + "\n");
        for (int row = 0; row*COLUMNS < Grid.length ; row++) {
            for (int column = 0; column < COLUMNS; column++) {
                Figure figure = Grid[row*COLUMNS + column];
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
     * Saves board into ~/Board/*.board file with provided name
     * @param name Name of the file that will be created without extension
     * @throws IOException - When file already exists
     * @throws NullPointerException - When board is uninitialized
     */
    public void saveCurrentBoard(String name) throws IOException, NullPointerException {
        if (Grid == null)
            throw new NullPointerException("Board is uninitialized");

        File file = new File(Path.of("Boards", name+".board").toString());

        if (file.createNewFile()) {
            gridToFile(Grid, file);
        }
        else
            throw new IOException("File already exists.");
    }

    /**
     * Reads file content and returns equivalent grid; sets global ROWS, COLUMNS and AREA to match board from the file
     * @param file *.board containing board setup
     * @return Grid base on provided file
     */
    private Figure[] fileToGrid(File file) throws IOException {
        Scanner scanner = new Scanner(file);
        ROWS = Integer.parseInt(scanner.next());
        COLUMNS = Integer.parseInt(scanner.next());
        AREA = ROWS*COLUMNS;
        Figure[] grid = new Figure[AREA];

        String word;
        Figure figure;
        Figure.Player player;
        for (int currentPosition = 0; currentPosition < AREA; currentPosition++) {
            word = scanner.next();
            if (word.contains(Figure.Player.Bottom.toString()))
                player = Figure.Player.Bottom;
            else
                player = Figure.Player.Top;

            if (word.contains(Figure.FigureType.King.toString()))
                figure = new King(player);
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
            grid[currentPosition] = figure;
        }
        return grid;
    }

    /**
     * Finds a file in Boards folder and initializes Grid with it
     * @param name Name of the file without .board extension
     * @return Representation of the board saved in specified file
     */
    public Figure[] getBoardFromFile(String name) throws IOException {
        File file = new File(Path.of("Boards", name+".board").toString());

        if (file.createNewFile())
            throw new FileNotFoundException("No such file: "+name);
        else
            return fileToGrid(file);
    }
}

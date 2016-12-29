package ge.tsu.handwritin_recognition.model;

import java.io.Serializable;

public class InputData implements Serializable {

    public static final long serialVersionUID = 24636377L;

    private static final int MAX_WIDTH = 100;

    private static final int MAX_HEIGHT = 100;

    private int width;

    private int height;

    private boolean grid[][];

    private Character letter;

    public InputData() {
        width = MAX_WIDTH;
        height = MAX_HEIGHT;
        grid = new boolean[height][width];
    }

    public InputData(int height, int width) {
        this.width = width;
        this.height = height;
        grid = new boolean[height][width];
    }

    public InputData(int height, int width, boolean[][] grid) {
        this.width = width;
        this.height = height;
        this.grid = grid;
    }

    public InputData(int height, int width, boolean[][] grid, Character letter) {
        this.width = width;
        this.height = height;
        this.grid = grid;
        this.letter = letter;
    }

    public InputData(int height, int width, char letter) {
        this.width = width;
        this.height = height;
        this.letter = letter;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean[][] getGrid() {
        return grid;
    }

    public void setGrid(boolean[][] grid) {
        this.grid = grid;
    }

    public Character getLetter() {
        return letter;
    }

    public void setLetter(Character letter) {
        this.letter = letter;
    }

    @Override
    public String toString() {
        String info = "";
        info += "სიმაღლე: " + height + System.lineSeparator();
        info += "სიგრძე: " + width + System.lineSeparator();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                info += (grid[i][j] ? 1 : 0) + " ";
            }
            info += System.lineSeparator();
        }
        if (letter != (char) 0) {
            info += "სწორი პასუხი: " + letter + System.lineSeparator();
        }
        return info;
    }
}

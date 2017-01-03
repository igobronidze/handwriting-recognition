package ge.tsu.handwriting_recognition.model;

import java.io.Serializable;
import java.util.Arrays;

public class NormalizedData implements Serializable {

    private int width;

    private int height;

    private boolean grid[][];

    private Character letter;

    private CharSequence charSequence;

    private String trainingSetGeneration;

    public NormalizedData() {
    }

    public NormalizedData(int width, int height, boolean[][] grid, Character letter, CharSequence charSequence, String trainingSetGeneration) {
        this.width = width;
        this.height = height;
        this.grid = grid;
        this.letter = letter;
        this.charSequence = charSequence;
        this.trainingSetGeneration = trainingSetGeneration;
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

    public CharSequence getCharSequence() {
        return charSequence;
    }

    public void setCharSequence(CharSequence charSequence) {
        this.charSequence = charSequence;
    }

    public String getTrainingSetGeneration() {
        return trainingSetGeneration;
    }

    public void setTrainingSetGeneration(String trainingSetGeneration) {
        this.trainingSetGeneration = trainingSetGeneration;
    }

    @Override
    public String toString() {
        return "NormalizedData{" +
                "width=" + width +
                ", height=" + height +
                ", grid=" + Arrays.toString(grid) +
                ", letter=" + letter +
                ", charSequence=" + charSequence +
                ", trainingSetGeneration='" + trainingSetGeneration + '\'' +
                '}';
    }
}

package ge.tsu.handwriting_recognition.neural_network.neural;

public class TestResult {

    private float squaredError;

    private float percentageOfCorrects;

    private float diffBetweenAnsAndBest;

    public float getSquaredError() {
        return squaredError;
    }

    public void setSquaredError(float squaredError) {
        this.squaredError = squaredError;
    }

    public float getPercentageOfCorrects() {
        return percentageOfCorrects;
    }

    public void setPercentageOfCorrects(float percentageOfCorrects) {
        this.percentageOfCorrects = percentageOfCorrects;
    }

    public float getDiffBetweenAnsAndBest() {
        return diffBetweenAnsAndBest;
    }

    public void setDiffBetweenAnsAndBest(float diffBetweenAnsAndBest) {
        this.diffBetweenAnsAndBest = diffBetweenAnsAndBest;
    }
}

package ge.tsu.handwriting_recognition.neural_network.neural;

import java.util.ArrayList;
import java.util.List;

public class TrainingData {

    private List<Float> input = new ArrayList<>();

    private List<Float> output = new ArrayList<>();

    public TrainingData(List<Float> input, List<Float> output) {
        this.input.clear();
        this.input.addAll(input);
        this.output.clear();
        this.output.addAll(output);
    }

    public List<Float> getInput() {
        return input;
    }

    public void setInput(List<Float> input) {
        this.input = input;
    }

    public List<Float> getOutput() {
        return output;
    }

    public void setOutput(List<Float> output) {
        this.output = output;
    }
}

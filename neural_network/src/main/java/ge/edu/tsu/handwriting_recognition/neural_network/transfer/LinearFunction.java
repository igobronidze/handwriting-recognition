package ge.edu.tsu.handwriting_recognition.neural_network.transfer;

public class LinearFunction implements TransferFunction {

    @Override
    public float transfer(float value) {
        return value;
    }
}

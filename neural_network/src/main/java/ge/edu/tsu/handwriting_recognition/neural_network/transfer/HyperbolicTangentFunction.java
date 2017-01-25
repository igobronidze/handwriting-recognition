package ge.edu.tsu.handwriting_recognition.neural_network.transfer;

public class HyperbolicTangentFunction implements TransferFunction {

    @Override
    public float transfer(float value) {
        return (float)(Math.exp(value) - Math.exp(-value)) / (float)(Math.exp(value) + Math.exp(-value));
    }
}

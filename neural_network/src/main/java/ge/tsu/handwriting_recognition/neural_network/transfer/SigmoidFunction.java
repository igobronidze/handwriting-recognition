package ge.tsu.handwriting_recognition.neural_network.transfer;

public class SigmoidFunction implements TransferFunction {

    public static final long serialVersionUID = 35453747373L;

    @Override
    public float transfer(float value) {
        return (float)(1.0 / (1.0 + Math.exp(-1 * value)));
    }
}

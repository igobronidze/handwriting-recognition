package ge.edu.tsu.handwriting_recognition.neural_network.transfer;

public class UnitStepFunction implements TransferFunction {

    @Override
    public float transfer(float value) {
        if (value < 0) {
            return 0;
        } else if (value == 0) {
            return 0.5f;
        } else {
            return 1;
        }
    }
}

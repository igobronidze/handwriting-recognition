package ge.tsu.handwriting_recognition.neural_network.main;

import ge.tsu.handwriting_recognition.neural_network.transfer.SigmoidFunction;
import ge.tsu.handwriting_recognition.neural_network.transfer.TransferFunction;

public class NeuralNetworkParameter {

    public static final float weightMinValue = -0.5f;

    public static final float weightMaxValue = 0.5f;

    public static final float biasMinValue = -0.5f;

    public static final float biasMaxValue = 0.5f;

    public static final TransferFunction transferFunction = new SigmoidFunction();

    public static float learningRate = 0.2f;

    public static int neuronCounter = 0;

    public static int connectionCounter = 0;
}

package ge.tsu.handwriting_recognition.neural_network.neural;

import ge.tsu.handwriting_recognition.neural_network.transfer.SigmoidFunction;
import ge.tsu.handwriting_recognition.neural_network.transfer.TransferFunction;
import ge.tsu.handwriting_recognition.neural_network.transfer.TransferFunctionType;

import java.io.Serializable;

public class NeuralNetworkParameter implements Serializable {

    private float weightMinValue = -0.5f;

    private float weightMaxValue = 0.5f;

    private float biasMinValue = -0.5f;

    private float biasMaxValue = 0.5f;

    private TransferFunction transferFunction = new SigmoidFunction();

    private float learningRate = 0.2f;

    public float getWeightMinValue() {
        return weightMinValue;
    }

    public void setWeightMinValue(float weightMinValue) {
        this.weightMinValue = weightMinValue;
    }

    public float getWeightMaxValue() {
        return weightMaxValue;
    }

    public void setWeightMaxValue(float weightMaxValue) {
        this.weightMaxValue = weightMaxValue;
    }

    public float getBiasMinValue() {
        return biasMinValue;
    }

    public void setBiasMinValue(float biasMinValue) {
        this.biasMinValue = biasMinValue;
    }

    public float getBiasMaxValue() {
        return biasMaxValue;
    }

    public void setBiasMaxValue(float biasMaxValue) {
        this.biasMaxValue = biasMaxValue;
    }

    public TransferFunction getTransferFunction() {
        return transferFunction;
    }

    public void setTransferFunction(TransferFunction transferFunction) {
        this.transferFunction = transferFunction;
    }

    public float getLearningRate() {
        return learningRate;
    }

    public void setLearningRate(float learningRate) {
        this.learningRate = learningRate;
    }

    public void setTransferFunction(TransferFunctionType type) {
        switch (type) {
            case SIGMOID:
                transferFunction = new SigmoidFunction();
        }
    }
}

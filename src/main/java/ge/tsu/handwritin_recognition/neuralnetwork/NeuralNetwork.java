package ge.tsu.handwritin_recognition.neuralnetwork;

import ge.tsu.handwritin_recognition.systemsetting.SystemParameter;
import org.neuroph.core.data.DataSet;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.util.TransferFunctionType;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {

    public static void trainNeural(DataSet trainingSet, int inputNeuralNumber) {
        List<Integer> layers = new ArrayList<>();
        layers.add(inputNeuralNumber);
        for (int x : SystemParameter.neuralInHiddenLayers) {
            layers.add(x);
        }
        layers.add(SystemParameter.charsSet.getNumberOfChars());
        MultiLayerPerceptron perceptron = new MultiLayerPerceptron(layers, TransferFunctionType.TANH);
        perceptron.learn(trainingSet);
        perceptron.save(SystemParameter.neuralNetworkPath);
    }

}

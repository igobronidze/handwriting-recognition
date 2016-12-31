package ge.tsu.handwritin_recognition.neuralnetwork;

import ge.tsu.handwritin_recognition.model.NormalizedData;
import ge.tsu.handwritin_recognition.service.NormalizedDataService;
import ge.tsu.handwritin_recognition.service.NormalizedDataServiceImpl;
import ge.tsu.handwritin_recognition.systemsetting.SystemParameter;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.util.TransferFunctionType;

import java.util.ArrayList;
import java.util.List;

public class MyNeuralNetwork {

    private static NormalizedDataService normalizedDataService;

    public static void trainNeural(List<NormalizedData> normalizedDataList) {
        NormalizedData example;
        if (normalizedDataList.isEmpty()) {
            return;
        } else {
            example = normalizedDataList.get(0);
        }
        normalizedDataService = new NormalizedDataServiceImpl();
        List<Integer> layers = new ArrayList<>();
        layers.add(example.getHeight() * example.getWidth());
        for (int x : SystemParameter.neuralInHiddenLayers) {
            layers.add(x);
        }
        layers.add(SystemParameter.CHARACTERS_SET.getNumberOfChars());
        DataSet trainingSet = new DataSet(example.getHeight() * example.getWidth(), SystemParameter.CHARACTERS_SET.getNumberOfChars());
        List<Integer> randomList = new ArrayList<>();
        for (int i = 0; i < SystemParameter.CHARACTERS_SET.getNumberOfChars(); i++) {
            randomList.add(i);
        }
        int min = Math.min(SystemParameter.numberOfDataSetRowInEachTraining, normalizedDataList.size());
        for (int i = 0; i < min; i++) {
            trainingSet.addRow(normalizedDataService.getDataSetRow(normalizedDataList.get(randomList.get(i))));
        }
        MultiLayerPerceptron perceptron = new MultiLayerPerceptron(layers, TransferFunctionType.TANH);
        perceptron.learn(trainingSet);
        perceptron.save(SystemParameter.neuralNetworkPath);
    }

    public static char guessCharacter(NormalizedData normalizedData) {
        NormalizedDataService normalizedDataService = new NormalizedDataServiceImpl();
        NeuralNetwork neuralNetwork = NeuralNetwork.createFromFile(SystemParameter.neuralNetworkPath);
        DataSetRow dataSetRow = normalizedDataService.getDataSetRow(normalizedData);
        neuralNetwork.setInput(dataSetRow.getInput());
        neuralNetwork.calculate();
        double[] networkOutput = neuralNetwork.getOutput();
        int ans = 0;
        for (int i = 1; i < SystemParameter.CHARACTERS_SET.getNumberOfChars(); i++) {
            if (networkOutput[i] > networkOutput[ans]) {
                ans = i;
            }
        }
        return (char)(ans + SystemParameter.CHARACTERS_SET.getFirstCharASCI());
    }
}

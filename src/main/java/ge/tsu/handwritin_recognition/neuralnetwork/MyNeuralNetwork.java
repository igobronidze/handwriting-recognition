package ge.tsu.handwritin_recognition.neuralnetwork;

import ge.tsu.handwritin_recognition.model.InputData;
import ge.tsu.handwritin_recognition.service.InputDataService;
import ge.tsu.handwritin_recognition.service.InputDataServiceImpl;
import ge.tsu.handwritin_recognition.systemsetting.SystemParameter;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.util.TransferFunctionType;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class MyNeuralNetwork {

    private static InputDataService inputDataService;

    public static void trainNeural(List<InputData> inputDataList) {
        InputData example;
        if (inputDataList.isEmpty()) {
            return;
        } else {
            example = inputDataList.get(0);
        }
        inputDataService = new InputDataServiceImpl();
        List<Integer> layers = new ArrayList<>();
        layers.add(example.getHeight() * example.getWidth());
        for (int x : SystemParameter.neuralInHiddenLayers) {
            layers.add(x);
        }
        layers.add(SystemParameter.charsSet.getNumberOfChars());
        DataSet trainingSet = new DataSet(example.getHeight() * example.getWidth(), SystemParameter.charsSet.getNumberOfChars());
        List<Integer> randomList = new ArrayList<>();
        for (int i = 0; i < SystemParameter.charsSet.getNumberOfChars(); i++) {
            randomList.add(i);
        }
        for (int i = 0; i < SystemParameter.numberOfDataSetRowInOneTraining; i++) {
            trainingSet.addRow(inputDataService.getDataSetRow(inputDataList.get(randomList.get(i))));
        }
        MultiLayerPerceptron perceptron = new MultiLayerPerceptron(layers, TransferFunctionType.TANH);
        perceptron.learn(trainingSet);
        perceptron.save(SystemParameter.neuralNetworkPath);
    }

    public static void guessCharacter(InputData inputData) {
        InputDataService inputDataService = new InputDataServiceImpl();
        NeuralNetwork neuralNetwork = NeuralNetwork.createFromFile(SystemParameter.neuralNetworkPath);
        DataSetRow dataSetRow = inputDataService.getDataSetRow(inputData);
        neuralNetwork.setInput(dataSetRow.getInput());
        neuralNetwork.calculate();
        double[] networkOutput = neuralNetwork.getOutput();
        int ans = 0;
        for (int i = 1; i < SystemParameter.charsSet.getNumberOfChars(); i++) {
            if (networkOutput[i] > networkOutput[ans]) {
                ans = i;
            }
        }
        System.out.println((char)(ans + SystemParameter.charsSet.getFirstCharASCI()));
    }
}

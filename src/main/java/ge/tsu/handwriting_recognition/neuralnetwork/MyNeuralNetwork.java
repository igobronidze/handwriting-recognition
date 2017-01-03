package ge.tsu.handwriting_recognition.neuralnetwork;

import ge.tsu.handwriting_recognition.console.utils.StringUtils;
import ge.tsu.handwriting_recognition.model.CharSequence;
import ge.tsu.handwriting_recognition.model.NormalizedData;
import ge.tsu.handwriting_recognition.service.NormalizedDataService;
import ge.tsu.handwriting_recognition.service.NormalizedDataServiceImpl;
import ge.tsu.handwriting_recognition.service.SystemParameterService;
import ge.tsu.handwriting_recognition.service.SystemParameterServiceImpl;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.util.TransferFunctionType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyNeuralNetwork {

    private static SystemParameterService systemParameterService = new SystemParameterServiceImpl();

    private static NormalizedDataService normalizedDataService;

    private static char firstSymbolInCharSequence = systemParameterService.getSystemParameterValue("firstSymbolInCharSequence", "ა").charAt(0);

    private static char lastSymbolInCharSequence = systemParameterService.getSystemParameterValue("lastSymbolInCharSequence", "ა").charAt(0);

    private static CharSequence charSequence = new CharSequence(firstSymbolInCharSequence, lastSymbolInCharSequence);

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
        for (int x : StringUtils.getIntegerListFromString(systemParameterService.getSystemParameterValue("neuralInHiddenLayers", ""), ",")) {
            layers.add(x);
        }
        layers.add(charSequence.getNumberOfChars());
        DataSet trainingSet = new DataSet(example.getHeight() * example.getWidth(), charSequence.getNumberOfChars());
        List<Integer> randomList = new ArrayList<>();
        for (int i = 0; i < normalizedDataList.size(); i++) {
            randomList.add(i);
        }
        Collections.shuffle(randomList);
        int min = Math.min(Integer.parseInt(systemParameterService.getSystemParameterValue("numberOfDataSetRowInEachTraining", "2000000000")), normalizedDataList.size());
        for (int i = 0; i < min; i++) {
            System.out.println(normalizedDataList.get(randomList.get(i)).getLetter());
            trainingSet.addRow(normalizedDataService.getDataSetRow(normalizedDataList.get(randomList.get(i))));
        }
        MultiLayerPerceptron perceptron = null;
        try {
            perceptron = (MultiLayerPerceptron) NeuralNetwork.createFromFile(systemParameterService.getSystemParameterValue("neuralNetworkPath",
                    "D:\\sg\\handwriting_recognition\\network\\network.nnet"));
        } catch (Exception ex) {
            perceptron = new MultiLayerPerceptron(layers, TransferFunctionType.SIGMOID);
        }
        perceptron.learn(trainingSet);
        perceptron.save(systemParameterService.getSystemParameterValue("neuralNetworkPath", "D:\\sg\\handwriting_recognition\\network\\network.nnet"));
    }

    public static char guessCharacter(NormalizedData normalizedData) {
        NormalizedDataService normalizedDataService = new NormalizedDataServiceImpl();
        NeuralNetwork neuralNetwork = NeuralNetwork.createFromFile(systemParameterService.getSystemParameterValue("neuralNetworkPath",
                "D:\\sg\\handwriting_recognition\\network\\network.nnet"));
        DataSetRow dataSetRow = normalizedDataService.getDataSetRow(normalizedData);
        neuralNetwork.setInput(dataSetRow.getInput());
        neuralNetwork.calculate();
        double[] networkOutput = neuralNetwork.getOutput();
        int ans = 0;
        for (int i = 1; i < charSequence.getNumberOfChars(); i++) {
            if (networkOutput[i] > networkOutput[ans]) {
                ans = i;
            }
        }
        return (char)(ans + charSequence.getFirstCharASCI());
    }
}

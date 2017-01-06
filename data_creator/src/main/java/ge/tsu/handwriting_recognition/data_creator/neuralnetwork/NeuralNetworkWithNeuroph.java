package ge.tsu.handwriting_recognition.data_creator.neuralnetwork;

import ge.tsu.handwriting_recognition.data_creator.console.utils.StringUtils;
import ge.tsu.handwriting_recognition.data_creator.model.CharSequence;
import ge.tsu.handwriting_recognition.data_creator.model.NormalizedData;
import ge.tsu.handwriting_recognition.data_creator.service.NormalizedDataService;
import ge.tsu.handwriting_recognition.data_creator.service.NormalizedDataServiceImpl;
import ge.tsu.handwriting_recognition.data_creator.service.SystemParameterService;
import ge.tsu.handwriting_recognition.data_creator.service.SystemParameterServiceImpl;
import org.apache.commons.lang3.NotImplementedException;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.util.TransferFunctionType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NeuralNetworkWithNeuroph implements INeuralNetwork {

    private SystemParameterService systemParameterService = new SystemParameterServiceImpl();

    private NormalizedDataService normalizedDataService = new NormalizedDataServiceImpl();

    private char firstSymbolInCharSequence = systemParameterService.getSystemParameterValue("firstSymbolInCharSequence", "ა").charAt(0);

    private char lastSymbolInCharSequence = systemParameterService.getSystemParameterValue("lastSymbolInCharSequence", "ა").charAt(0);

    private CharSequence charSequence = new CharSequence(firstSymbolInCharSequence, lastSymbolInCharSequence);

    private String neuralNetworkPath = systemParameterService.getSystemParameterValue("neuralNetworkPath", "D:\\sg\\handwriting_recognition\\network\\network.nnet");

    @Override
    public void trainNeural(int width, int height, String generation) {
        List<NormalizedData> normalizedDataList = normalizedDataService.getNormalizedDatas(width, height, charSequence, generation);
        List<Integer> layers = new ArrayList<>();
        layers.add(width * height);
        for (int x : StringUtils.getIntegerListFromString(systemParameterService.getSystemParameterValue("neuralInHiddenLayers", ""), ",")) {
            layers.add(x);
        }
        layers.add(charSequence.getNumberOfChars());
        DataSet trainingSet = new DataSet(width * height, charSequence.getNumberOfChars());
        List<Integer> randomList = new ArrayList<>();
        for (int i = 0; i < normalizedDataList.size(); i++) {
            randomList.add(i);
        }
        Collections.shuffle(randomList);
        int min = Math.min(Integer.parseInt(systemParameterService.getSystemParameterValue("numberOfTrainingDataInOneIteration", "2000000000")), normalizedDataList.size());
        for (int i = 0; i < min; i++) {
            trainingSet.addRow(normalizedDataService.getDataSetRow(normalizedDataList.get(randomList.get(i))));
        }
        MultiLayerPerceptron perceptron = null;
        try {
            perceptron = (MultiLayerPerceptron) NeuralNetwork.createFromFile(neuralNetworkPath);
        } catch (Exception ex) {
            perceptron = new MultiLayerPerceptron(layers, TransferFunctionType.SIGMOID);
        }
        perceptron.learn(trainingSet);
        perceptron.save(neuralNetworkPath);
    }

    @Override
    public Character guessCharacter(NormalizedData normalizedData) {
        NeuralNetwork neuralNetwork = NeuralNetwork.createFromFile(neuralNetworkPath);
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

    @Override
    public float test(int width, int height, String generation) {
        throw new NotImplementedException("Not yet :D");
    }
}

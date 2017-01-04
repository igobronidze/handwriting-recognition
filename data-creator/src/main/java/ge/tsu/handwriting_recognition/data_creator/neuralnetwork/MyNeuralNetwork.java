package ge.tsu.handwriting_recognition.data_creator.neuralnetwork;

import ge.tsu.handwriting_recognition.data_creator.console.utils.StringUtils;
import ge.tsu.handwriting_recognition.data_creator.model.CharSequence;
import ge.tsu.handwriting_recognition.data_creator.model.NormalizedData;
import ge.tsu.handwriting_recognition.data_creator.service.NormalizedDataService;
import ge.tsu.handwriting_recognition.data_creator.service.NormalizedDataServiceImpl;
import ge.tsu.handwriting_recognition.data_creator.service.SystemParameterService;
import ge.tsu.handwriting_recognition.data_creator.service.SystemParameterServiceImpl;
import ge.tsu.handwriting_recognition.neural_network.exception.NeuralNetworkException;
import ge.tsu.handwriting_recognition.neural_network.neural.NeuralNetwork;
import ge.tsu.handwriting_recognition.neural_network.neural.TrainingData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyNeuralNetwork implements INeuralNetwork {

    private SystemParameterService systemParameterService = new SystemParameterServiceImpl();

    private NormalizedDataService normalizedDataService = new NormalizedDataServiceImpl();

    private char firstSymbolInCharSequence = systemParameterService.getSystemParameterValue("firstSymbolInCharSequence", "ა").charAt(0);

    private char lastSymbolInCharSequence = systemParameterService.getSystemParameterValue("lastSymbolInCharSequence", "ა").charAt(0);

    private CharSequence charSequence = new CharSequence(firstSymbolInCharSequence, lastSymbolInCharSequence);

    private String neuralNetworkPath = systemParameterService.getSystemParameterValue("neuralNetworkPath", "D:\\sg\\handwriting_recognition\\network\\network.nnet");

    @Override
    public void trainNeural(int width, int height, String generation) {
        try {
            List<NormalizedData> normalizedDataList = normalizedDataService.getNormalizedDatas(width, height, charSequence, generation);
            List<Integer> layers = new ArrayList<>();
            layers.add(width * height);
            for (int x : StringUtils.getIntegerListFromString(systemParameterService.getSystemParameterValue("neuralInHiddenLayers", ""), ",")) {
                layers.add(x);
            }
            layers.add(charSequence.getNumberOfChars());
            List<Integer> randomList = new ArrayList<>();
            for (int i = 0; i < normalizedDataList.size(); i++) {
                randomList.add(i);
            }
            Collections.shuffle(randomList);
            int min = Math.min(Integer.parseInt(systemParameterService.getSystemParameterValue("numberOfDataSetRowInEachTraining", "2000000000")), normalizedDataList.size());
            NeuralNetwork neuralNetwork;
            try {
                neuralNetwork = NeuralNetwork.load(neuralNetworkPath);
            } catch (Exception ex) {
                neuralNetwork = new NeuralNetwork(layers);
            }
            for (int i = 0; i < min; i++) {
                neuralNetwork.addTrainingData(normalizedDataService.getTrainingData(normalizedDataList.get(randomList.get(i))));
            }
            for (int i = 0; i < 5000; i++) {
                neuralNetwork.train();
            }
            NeuralNetwork.save(neuralNetworkPath, neuralNetwork);
        } catch (NeuralNetworkException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public char guessCharacter(NormalizedData normalizedData) {
        try {
            NeuralNetwork neuralNetwork = NeuralNetwork.load(neuralNetworkPath);
            TrainingData trainingData = normalizedDataService.getTrainingData(normalizedData);
            List<Float> output = neuralNetwork.getOutputActivation(trainingData);
            int ans = 0;
            for (int i = 1; i < charSequence.getNumberOfChars(); i++) {
                if (output.get(i) > output.get(ans)) {
                    ans = i;
                }
            }
            return (char) (ans + charSequence.getFirstCharASCI());
        } catch (NeuralNetworkException ex) {
            System.out.println(ex.getMessage());
        }
        return '`';
    }
}

package ge.tsu.handwriting_recognition.data_creator.neuralnetwork;

import ge.tsu.handwriting_recognition.data_creator.console.utils.StringUtils;
import ge.tsu.handwriting_recognition.data_creator.model.CharSequence;
import ge.tsu.handwriting_recognition.data_creator.model.NetworkInfo;
import ge.tsu.handwriting_recognition.data_creator.model.NormalizedData;
import ge.tsu.handwriting_recognition.data_creator.model.TestingInfo;
import ge.tsu.handwriting_recognition.data_creator.service.*;
import ge.tsu.handwriting_recognition.neural_network.exception.NeuralNetworkException;
import ge.tsu.handwriting_recognition.neural_network.neural.NeuralNetwork;
import ge.tsu.handwriting_recognition.neural_network.neural.NeuralNetworkParameter;
import ge.tsu.handwriting_recognition.neural_network.neural.TrainingData;
import ge.tsu.handwriting_recognition.neural_network.transfer.TransferFunctionType;

import java.util.ArrayList;
import java.util.List;

public class MyNeuralNetwork implements INeuralNetwork {

    private SystemParameterService systemParameterService = new SystemParameterServiceImpl();

    private NormalizedDataService normalizedDataService = new NormalizedDataServiceImpl();

    private NetworkInfoService networkInfoService = new NetworkInfoServiceImpl();

    private TestingInfoService testingInfoService = new TestingInfoServiceImpl();

    private char firstSymbolInCharSequence = systemParameterService.getSystemParameterValue("firstSymbolInCharSequence", "ა").charAt(0);

    private char lastSymbolInCharSequence = systemParameterService.getSystemParameterValue("lastSymbolInCharSequence", "ა").charAt(0);

    private CharSequence charSequence = new CharSequence(firstSymbolInCharSequence, lastSymbolInCharSequence);

    private String neuralNetworkDirectory = systemParameterService.getSystemParameterValue("neuralNetworkDirectory", "D:\\sg\\handwriting_recognition\\network");

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
            NeuralNetwork neuralNetwork = new NeuralNetwork(layers);
            setNeuralNetworkParameters(neuralNetwork);
            for (int i = 0; i < normalizedDataList.size(); i++) {
                neuralNetwork.addTrainingData(normalizedDataService.getTrainingData(normalizedDataList.get(i)));
            }
            long trainingDuration = neuralNetwork.train();
            int id = networkInfoService.addNetworkInfo(getNetworkInfo(neuralNetwork, trainingDuration, width, height, generation));
            NeuralNetwork.save(neuralNetworkDirectory + "\\" + id + "_" + generation + "_" + width + "_" + height + ".nnet", neuralNetwork);
        } catch (NeuralNetworkException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public Character guessCharacter(NormalizedData normalizedData) {
        try {
            NeuralNetwork neuralNetwork = NeuralNetwork.load(neuralNetworkDirectory);
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
        return null;
    }

    @Override
    public float test(int width, int height, String generation, String path, int networkId) {
        List<NormalizedData> normalizedDataList = normalizedDataService.getNormalizedDatas(width, height, charSequence, generation);
        try {
            NeuralNetwork neuralNetwork = NeuralNetwork.load(path);
            List<TrainingData> trainingDataList = new ArrayList<>();
            for (NormalizedData normalizedData : normalizedDataList) {
                trainingDataList.add(normalizedDataService.getTrainingData(normalizedData));
            }
            float error = neuralNetwork.test(trainingDataList);
            TestingInfo testingInfo = new TestingInfo();
            testingInfo.setError(error);
            testingInfo.setNumberOfTest(normalizedDataList.size());
            testingInfo.setNetworkId(networkId);
            testingInfo.setGeneration(generation);
            testingInfoService.addTestingInfo(testingInfo);
            return error;
        } catch (Exception ex) {
            System.out.println("Neural network don's exist");
        }
        return -1;
    }

    private void setNeuralNetworkParameters(NeuralNetwork neuralNetwork) {
        NeuralNetworkParameter parameter = neuralNetwork.getNeuralNetworkParameter();
        parameter.setWeightMinValue(Float.parseFloat(systemParameterService.getSystemParameterValue("weightMinValue", "-0.5")));
        parameter.setWeightMaxValue(Float.parseFloat(systemParameterService.getSystemParameterValue("weightMaxValue", "0.5")));
        parameter.setBiasMinValue(Float.parseFloat(systemParameterService.getSystemParameterValue("biasMinValue", "-0.5")));
        parameter.setBiasMaxValue(Float.parseFloat(systemParameterService.getSystemParameterValue("biasMaxValue", "0.5")));
        parameter.setTransferFunctionType(TransferFunctionType.valueOf(systemParameterService.getSystemParameterValue("transferFunction", "SIGMOID")));
        parameter.setLearningRate(Float.parseFloat(systemParameterService.getSystemParameterValue("learningRate", "0.2")));
        parameter.setMinError(Float.parseFloat(systemParameterService.getSystemParameterValue("minError", "0.05")));
        parameter.setTrainingMaxIteration(Integer.parseInt(systemParameterService.getSystemParameterValue("trainingMaxIteration", "100000")));
        parameter.setNumberOfTrainingDataInOneIteration(Integer.parseInt(systemParameterService.getSystemParameterValue("numberOfTrainingDataInOneIteration", "100")));
    }

    private NetworkInfo getNetworkInfo(NeuralNetwork neuralNetwork, long trainingDuration, int width, int height, String generation) {
        NeuralNetworkParameter parameter = neuralNetwork.getNeuralNetworkParameter();
        NetworkInfo networkInfo = new NetworkInfo();
        networkInfo.setWidth(width);
        networkInfo.setHeight(height);
        networkInfo.setGeneration(generation);
        networkInfo.setNumberOfData(neuralNetwork.getTrainingDataList().size());
        networkInfo.setTrainingDuration(trainingDuration);
        networkInfo.setWeightMinValue(parameter.getWeightMinValue());
        networkInfo.setWeightMaxValue(parameter.getWeightMaxValue());
        networkInfo.setBiasMinValue(parameter.getBiasMinValue());
        networkInfo.setBiasMaxValue(parameter.getBiasMaxValue());
        networkInfo.setTransferFunctionType(parameter.getTransferFunctionType());
        networkInfo.setLearningRate(parameter.getLearningRate());
        networkInfo.setMinError(parameter.getMinError());
        networkInfo.setTrainingMaxIteration(parameter.getTrainingMaxIteration());
        networkInfo.setNumberOfTrainingDataInOneIteration(parameter.getNumberOfTrainingDataInOneIteration());
        networkInfo.setTestingInfoList(new ArrayList<>());
        networkInfo.setCharSequence(charSequence.getFirstSymbol() + "-" + charSequence.getLastSymbol());
        String hiddenLayer = "";
        for (int i = 1; i < neuralNetwork.getLayersSize().size() - 1; i++) {
            hiddenLayer += neuralNetwork.getLayersSize().get(i) + ",";
        }
        if (hiddenLayer.isEmpty()) {
            networkInfo.setHiddenLayer(hiddenLayer);
        } else {
            networkInfo.setHiddenLayer(hiddenLayer.substring(0, hiddenLayer.length() - 1));
        }
        return networkInfo;
    }
}

package ge.edu.tsu.handwriting_recognition.control_panel.server.manager.neuralnetwork;

import ge.edu.tsu.handwriting_recognition.control_panel.model.data.NormalizedData;
import ge.edu.tsu.handwriting_recognition.control_panel.model.info.CharSequence;
import ge.edu.tsu.handwriting_recognition.control_panel.model.sysparam.Parameter;
import ge.edu.tsu.handwriting_recognition.control_panel.server.dao.NormalizedDataDAO;
import ge.edu.tsu.handwriting_recognition.control_panel.server.dao.NormalizedDataDAOImpl;
import ge.edu.tsu.handwriting_recognition.control_panel.server.manager.NormalizedDataManager;
import ge.edu.tsu.handwriting_recognition.control_panel.server.manager.SystemParameterManager;
import org.apache.commons.lang3.NotImplementedException;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.util.TransferFunctionType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Deprecated
public class NeurophNeuralNetworkManager implements NeuralNetworkManager {

    private SystemParameterManager systemParameterManager = new SystemParameterManager();

    private NormalizedDataManager normalizedDataManager = new NormalizedDataManager();

    private NormalizedDataDAO normalizedDataDAO = new NormalizedDataDAOImpl();

    private Parameter firstSymbolInCharSequenceParameter = new Parameter("firstSymbolInCharSequence", "ა");

    private Parameter lastSymbolInCharSequenceParameter = new Parameter("lastSymbolInCharSequence", "ჰ");

    private Parameter neuralInHiddenLayersParameter = new Parameter("neuralInHiddenLayers", "");

    private Parameter neuralNetworkPathParameter = new Parameter("neuralNetworkPath", "D:\\sg\\handwriting_recognition\\network\\network.nnet");

    private Parameter numberOfTrainingDataInOneIterationParameter = new Parameter("numberOfTrainingDataInOneIteration", "100");

    private CharSequence charSequence;

    public NeurophNeuralNetworkManager() {
        char firstSymbolInCharSequence = systemParameterManager.getParameterValue(firstSymbolInCharSequenceParameter).charAt(0);
        char lastSymbolInCharSequence = systemParameterManager.getParameterValue(lastSymbolInCharSequenceParameter).charAt(0);
        charSequence = new CharSequence(firstSymbolInCharSequence, lastSymbolInCharSequence);
    }

    @Override
    public void trainNeural(int width, int height, String generation) {
        List<NormalizedData> normalizedDataList = normalizedDataDAO.getNormalizedDatas(width, height, charSequence, generation);
        List<Integer> layers = new ArrayList<>();
        layers.add(width * height);
        for (int x : systemParameterManager.getIntegerListParameterValue(neuralInHiddenLayersParameter)) {
            layers.add(x);
        }
        layers.add(charSequence.getNumberOfChars());
        DataSet trainingSet = new DataSet(width * height, charSequence.getNumberOfChars());
        List<Integer> randomList = new ArrayList<>();
        for (int i = 0; i < normalizedDataList.size(); i++) {
            randomList.add(i);
        }
        Collections.shuffle(randomList);
        int min = Math.min(systemParameterManager.getIntegerParameterValue(numberOfTrainingDataInOneIterationParameter), normalizedDataList.size());
        for (int i = 0; i < min; i++) {
            trainingSet.addRow(normalizedDataManager.getDataSetRow(normalizedDataList.get(randomList.get(i)), charSequence));
        }
        MultiLayerPerceptron perceptron = null;
        try {
            perceptron = (MultiLayerPerceptron) NeuralNetwork.createFromFile(systemParameterManager.getParameterValue(neuralNetworkPathParameter));
        } catch (Exception ex) {
            perceptron = new MultiLayerPerceptron(layers, TransferFunctionType.SIGMOID);
        }
        perceptron.learn(trainingSet);
        perceptron.save(systemParameterManager.getParameterValue(neuralNetworkPathParameter));
    }

    @Override
    public Character guessCharacter(NormalizedData normalizedData, String networkPath) {
        NeuralNetwork neuralNetwork = NeuralNetwork.createFromFile(systemParameterManager.getParameterValue(neuralNetworkPathParameter));
        DataSetRow dataSetRow = normalizedDataManager.getDataSetRow(normalizedData, charSequence);
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
    public float test(int width, int height, String generation, String path, int networkId) {
        throw new NotImplementedException("Not yet :D");
    }
}

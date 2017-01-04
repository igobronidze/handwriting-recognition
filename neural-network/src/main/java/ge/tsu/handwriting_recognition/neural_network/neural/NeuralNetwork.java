package ge.tsu.handwriting_recognition.neural_network.neural;

import ge.tsu.handwriting_recognition.neural_network.exception.NeuralNetworkException;
import ge.tsu.handwriting_recognition.neural_network.util.Randomizer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork implements Serializable {

    public static final long serialVersionUID = 25245248L;

    private List<Integer> layersSize;

    private List<Neuron> inputNeurons = new ArrayList<>();

    private List<Neuron> hiddenNeurons = new ArrayList<>();

    private List<Neuron> outputNeurons = new ArrayList<>();

    private List<TrainingData> trainingDataList = new ArrayList<>();

    public List<Integer> getLayersSize() {
        return layersSize;
    }

    public void setLayersSize(List<Integer> layersSize) {
        this.layersSize = layersSize;
    }

    public List<Neuron> getInputNeurons() {
        return inputNeurons;
    }

    public void setInputNeurons(List<Neuron> inputNeurons) {
        this.inputNeurons = inputNeurons;
    }

    public List<Neuron> getHiddenNeurons() {
        return hiddenNeurons;
    }

    public void setHiddenNeurons(List<Neuron> hiddenNeurons) {
        this.hiddenNeurons = hiddenNeurons;
    }

    public List<Neuron> getOutputNeurons() {
        return outputNeurons;
    }

    public void setOutputNeurons(List<Neuron> outputNeurons) {
        this.outputNeurons = outputNeurons;
    }

    public void create(List<Integer> layersSize) throws NeuralNetworkException {
        this.layersSize = layersSize;
        if (layersSize.size() < 2) {
            throw new NeuralNetworkException("layers must be more than 1");
        }
        List<Neuron> lastLayerNeurons = new ArrayList<>();
        List<Neuron> tmpNeurons = new ArrayList<>();
        for (int i = 0; i < layersSize.get(0); i++) {
            Neuron neuron = Randomizer.getRandomNeuron();
            inputNeurons.add(neuron);
            tmpNeurons.add(neuron);
        }
        lastLayerNeurons.clear();
        lastLayerNeurons.addAll(tmpNeurons);
        tmpNeurons.clear();
        for (int i = 1; i < layersSize.size(); i++) {
            for (int j = 0; j < layersSize.get(i); j++) {
                Neuron neuron = Randomizer.getRandomNeuron();
                if (i == layersSize.size() - 1) {
                    outputNeurons.add(neuron);
                } else {
                    hiddenNeurons.add(neuron);
                }
                tmpNeurons.add(neuron);
                for (int k = 0; k < lastLayerNeurons.size(); k++) {
                    Connection connection = Randomizer.getRandomConnection(lastLayerNeurons.get(k), neuron);
                    neuron.getInConnections().add(connection);
                    lastLayerNeurons.get(k).getOutConnections().add(connection);
                    connection.setLeftNeuron(lastLayerNeurons.get(k));
                    connection.setRightNeuron(neuron);
                }
            }
            lastLayerNeurons.clear();
            lastLayerNeurons.addAll(tmpNeurons);
            tmpNeurons.clear();
        }
    }

    public void addTrainingData(TrainingData trainingData) throws NeuralNetworkException {
        if (trainingData.getInput().size() != layersSize.get(0)) {
            throw new NeuralNetworkException("Training datas input size and neural network input neurons size must be equal");
        }
        if (trainingData.getOutput().size() != layersSize.get(layersSize.size() - 1)) {
            throw new NeuralNetworkException("Training datas output size and neural network output neurons size must be equal");
        }
        this.trainingDataList.add(trainingData);
    }

    public void train() {
        for (TrainingData trainingData : trainingDataList) {
            Activation.activate(this, trainingData);
            for (Neuron neuron : outputNeurons) {
                System.out.print(neuron.getActivationValue() + " ");
            }
            System.out.println();
            Backpropagation.backpropagation(this, trainingData);
        }
    }

//    public void test() {
//        for (TrainingData trainingData : trainingDataList) {
//            Activation.activate(this, trainingData);
//            for (Neuron neuron : outputNeurons) {
//                System.out.print(neuron.getActivationValue() + " ");
//            }
//            System.out.println();
//            Backpropagation.backpropagation(this, trainingData);
//        }
//    }
}

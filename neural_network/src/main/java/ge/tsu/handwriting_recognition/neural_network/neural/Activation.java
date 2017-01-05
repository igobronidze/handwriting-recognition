package ge.tsu.handwriting_recognition.neural_network.neural;

import java.util.ArrayList;
import java.util.List;

public class Activation {

    public static void activate(NeuralNetwork neuralNetwork, TrainingData trainingData) {
        List<Neuron> inputNeurons = neuralNetwork.getInputNeurons();
        List<Neuron> outputNeurons = neuralNetwork.getOutputNeurons();
        List<Neuron> hiddenNeurons = neuralNetwork.getHiddenNeurons();
        List<Float> input = trainingData.getInput();
        for (int i = 0; i < inputNeurons.size(); i++) {
            inputNeurons.get(i).setActivationValue(input.get(i));
        }
        List<Neuron> tmpNeurons = new ArrayList<>();
        tmpNeurons.addAll(hiddenNeurons);
        tmpNeurons.addAll(outputNeurons);
        for (Neuron neuron : tmpNeurons) {
            Float sum = 0.0f;
            for (Connection connection : neuron.getInConnections()) {
                sum += connection.getWeight() * connection.getLeftNeuron().getActivationValue();
            }
            sum += neuron.getBias();
            neuron.setActivationValue(neuralNetwork.getNeuralNetworkParameter().getTransferFunction().transfer(sum));
        }
    }

    public static float squaredError(NeuralNetwork neuralNetwork, TrainingData trainingData) {
        List<Neuron> outputNeurons = neuralNetwork.getOutputNeurons();
        List<Float> output = trainingData.getOutput();
        float error = 0.0f;
        for (int i = 0; i < output.size(); i++) {
            error += Math.pow(output.get(i) - outputNeurons.get(i).getActivationValue(), 2) / 2.0;
        }
        return error;
    }
}

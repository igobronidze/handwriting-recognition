package ge.tsu.handwriting_recognition.neural_network.util;

import ge.tsu.handwriting_recognition.neural_network.neural.Connection;
import ge.tsu.handwriting_recognition.neural_network.neural.NeuralNetwork;
import ge.tsu.handwriting_recognition.neural_network.neural.Neuron;

import java.util.Random;

public class Randomizer {

    private static Random random = new Random();

    public static Neuron getRandomNeuron(NeuralNetwork neuralNetwork) {
        float bias = neuralNetwork.getNeuralNetworkParameter().getBiasMinValue() + (neuralNetwork.getNeuralNetworkParameter().getBiasMaxValue()
                - neuralNetwork.getNeuralNetworkParameter().getBiasMinValue()) * random.nextFloat();
        Neuron neuron = new Neuron();
        neuron.setBias(bias);
        return neuron;
    }

    public static Connection getRandomConnection(NeuralNetwork neuralNetwork, Neuron leftNeuron, Neuron rightNeuron) {
        float weight = neuralNetwork.getNeuralNetworkParameter().getWeightMinValue() + (neuralNetwork.getNeuralNetworkParameter().getWeightMaxValue()
                - neuralNetwork.getNeuralNetworkParameter().getWeightMinValue()) * random.nextFloat();
        Connection connection = new Connection();
        connection.setLeftNeuron(leftNeuron);
        connection.setRightNeuron(rightNeuron);
        connection.setWeight(weight);
        return connection;
    }
}

package ge.tsu.handwriting_recognition.neural_network.util;

import ge.tsu.handwriting_recognition.neural_network.main.NeuralNetworkParameter;
import ge.tsu.handwriting_recognition.neural_network.neural.Connection;
import ge.tsu.handwriting_recognition.neural_network.neural.Neuron;

import java.util.Random;

public class Randomizer {

    private static Random random = new Random();

    public static Neuron getRandomNeuron() {
        float bias = NeuralNetworkParameter.biasMinValue + (NeuralNetworkParameter.biasMaxValue - NeuralNetworkParameter.biasMinValue) * random.nextFloat();
        Neuron neuron = new Neuron();
        neuron.setBias(bias);
        neuron.setId(++NeuralNetworkParameter.neuronCounter);
        return neuron;
    }

    public static Connection getRandomConnection(Neuron leftNeuron, Neuron rightNeuron) {
        float weight = NeuralNetworkParameter.weightMinValue + (NeuralNetworkParameter.weightMaxValue - NeuralNetworkParameter.weightMinValue) * random.nextFloat();
        Connection connection = new Connection();
        connection.setLeftNeuron(leftNeuron);
        connection.setRightNeuron(rightNeuron);
        connection.setWeight(weight);
        connection.setId(++NeuralNetworkParameter.connectionCounter);
        return connection;
    }
}

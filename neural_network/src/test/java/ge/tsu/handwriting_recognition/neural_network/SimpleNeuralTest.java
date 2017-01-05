package ge.tsu.handwriting_recognition.neural_network;

import ge.tsu.handwriting_recognition.neural_network.exception.NeuralNetworkException;
import ge.tsu.handwriting_recognition.neural_network.neural.NeuralNetwork;
import ge.tsu.handwriting_recognition.neural_network.neural.TrainingData;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleNeuralTest {

    @Test
    @Ignore
    public void neuralNetworkXORTest() throws NeuralNetworkException, InterruptedException{
        List<Integer> sizes = new ArrayList<>(Arrays.asList(2, 5, 5, 1));
        NeuralNetwork neuralNetwork = new NeuralNetwork(sizes);

        List<Float> input1 = new ArrayList<>(Arrays.asList(0.0f, 0.0f));
        List<Float> output1 = new ArrayList<>(Arrays.asList(0.0f));
        TrainingData trainingData1 = new TrainingData(input1, output1);
        neuralNetwork.addTrainingData(trainingData1);

        List<Float> input2 = new ArrayList<>(Arrays.asList(0.0f, 1.0f));
        List<Float> output2 = new ArrayList<>(Arrays.asList(0.0f));
        TrainingData trainingData2 = new TrainingData(input2, output2);
        neuralNetwork.addTrainingData(trainingData2);

        List<Float> input3 = new ArrayList<>(Arrays.asList(1.0f, 0.0f));
        List<Float> output3 = new ArrayList<>(Arrays.asList(0.0f));
        TrainingData trainingData3 = new TrainingData(input3, output3);
        neuralNetwork.addTrainingData(trainingData3);

        List<Float> input4 = new ArrayList<>(Arrays.asList(1.0f, 1.0f));
        List<Float> output4 = new ArrayList<>(Arrays.asList(1.0f));
        TrainingData trainingData4 = new TrainingData(input4, output4);
        neuralNetwork.addTrainingData(trainingData4);

        neuralNetwork.train();

        neuralNetwork.save("D:\\sg\\network.nnet", neuralNetwork);
        NeuralNetwork myNetwork = NeuralNetwork.load("D:\\sg\\network.nnet");
        System.out.println(myNetwork.getOutputActivation(trainingData1));
    }
}

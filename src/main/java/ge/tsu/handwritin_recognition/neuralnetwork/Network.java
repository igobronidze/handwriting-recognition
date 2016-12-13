package ge.tsu.handwritin_recognition.neuralnetwork;

import java.util.List;

public class Network {

    // ფენების რაოდენობა ქსელში
    private int layersNumber;

    // თითოეულ ფენაში ნეირონების რაოდენობა
    private List<Integer> sizes;

    // დამხმარე წონა bias
    private List<double[]> biases;

    // წონები
    private List<double[][]> weights;

    public Network(List<Integer> sizes) {
        this.layersNumber = sizes.size();
        this.sizes = sizes;
        for (int i = 1; i < layersNumber; i++) {
            double[] bias = new double[sizes.get(i)];
            for (int j = 0; j < sizes.get(i); j++) {
                bias[j] = Math.random();
            }
            this.biases.add(bias);
            double[][] weight = new double[sizes.get(i - 1)][sizes.get(i)];
            for (int j = 0; j < sizes.get(i - 1); j++) {
                for (int p = 0; p < sizes.get(i); p++) {
                    weight[j][p] = Math.random();
                }
            }
            this.weights.add(weight);
        }
    }
}

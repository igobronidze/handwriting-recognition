package ge.tsu.handwriting_recognition.neural_network.neural;

import java.io.Serializable;

public class Connection implements Serializable {

    public static final long serialVersionUID = 134252L;

    private int id;

    private float weight;

    private transient Neuron leftNeuron;

    private transient Neuron rightNeuron;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public Neuron getLeftNeuron() {
        return leftNeuron;
    }

    public void setLeftNeuron(Neuron leftNeuron) {
        this.leftNeuron = leftNeuron;
    }

    public Neuron getRightNeuron() {
        return rightNeuron;
    }

    public void setRightNeuron(Neuron rightNeuron) {
        this.rightNeuron = rightNeuron;
    }
}

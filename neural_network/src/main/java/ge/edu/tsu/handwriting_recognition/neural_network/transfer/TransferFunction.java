package ge.edu.tsu.handwriting_recognition.neural_network.transfer;

import java.io.Serializable;

public interface TransferFunction extends Serializable {

    long serialVersionUID = 35453747373L;

    float transfer(float value);
}

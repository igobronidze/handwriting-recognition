package ge.tsu.handwriting_recognition.neural_network.transfer;

import java.io.Serializable;

public interface TransferFunction extends Serializable {

    float transfer(float value);
}

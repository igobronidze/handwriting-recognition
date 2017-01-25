package ge.edu.tsu.handwriting_recognition.control_panel.server.manager.neuralnetwork;

import ge.edu.tsu.handwriting_recognition.control_panel.model.data.NormalizedData;

public interface NeuralNetworkManager {

    void trainNeural(int width, int height, String generation);

    Character guessCharacter(NormalizedData normalizedData, String networkPath);

    float test(int width, int height, String generation, String path, int networkId);
}

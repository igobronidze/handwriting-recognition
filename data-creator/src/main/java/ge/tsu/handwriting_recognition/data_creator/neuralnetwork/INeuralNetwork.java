package ge.tsu.handwriting_recognition.data_creator.neuralnetwork;

import ge.tsu.handwriting_recognition.data_creator.model.NormalizedData;

public interface INeuralNetwork {

    void trainNeural(int width, int height, String generation);

    char guessCharacter(NormalizedData normalizedData);
}

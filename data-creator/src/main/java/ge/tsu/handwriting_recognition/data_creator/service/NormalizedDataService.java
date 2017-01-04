package ge.tsu.handwriting_recognition.data_creator.service;

import ge.tsu.handwriting_recognition.data_creator.model.CharSequence;
import ge.tsu.handwriting_recognition.data_creator.model.NormalizedData;
import ge.tsu.handwriting_recognition.neural_network.neural.TrainingData;
import org.neuroph.core.data.DataSetRow;

import java.util.List;

public interface NormalizedDataService {

    DataSetRow getDataSetRow(NormalizedData normalizedData);

    TrainingData getTrainingData(NormalizedData normalizedData);

    void addNormalizedData(NormalizedData normalizedData);

    List<NormalizedData> getNormalizedDatas(Integer width, Integer height, CharSequence charSequence, String generation);
}

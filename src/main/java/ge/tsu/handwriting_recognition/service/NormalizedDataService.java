package ge.tsu.handwriting_recognition.service;

import ge.tsu.handwriting_recognition.model.CharSequence;
import ge.tsu.handwriting_recognition.model.NormalizedData;
import org.neuroph.core.data.DataSetRow;

import java.util.List;

public interface NormalizedDataService {

    DataSetRow getDataSetRow(NormalizedData normalizedData);

    void addNormalizedData(NormalizedData normalizedData);

    List<NormalizedData> getNormalizedDatas(Integer width, Integer height, CharSequence charSequence, String generation);
}

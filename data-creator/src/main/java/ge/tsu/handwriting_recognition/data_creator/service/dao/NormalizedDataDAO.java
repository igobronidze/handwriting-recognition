package ge.tsu.handwriting_recognition.data_creator.service.dao;

import ge.tsu.handwriting_recognition.data_creator.model.CharSequence;
import ge.tsu.handwriting_recognition.data_creator.model.NormalizedData;

import java.util.List;

public interface NormalizedDataDAO {

    void addNormalizedData(NormalizedData normalizedData);

    List<NormalizedData> getNormalizedDatas(Integer width, Integer height, CharSequence charSequence, String generation);
}

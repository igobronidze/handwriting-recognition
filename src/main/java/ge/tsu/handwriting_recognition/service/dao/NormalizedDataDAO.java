package ge.tsu.handwriting_recognition.service.dao;

import ge.tsu.handwriting_recognition.model.CharSequence;
import ge.tsu.handwriting_recognition.model.NormalizedData;

import java.util.List;

public interface NormalizedDataDAO {

    void addNormalizedData(NormalizedData normalizedData);

    List<NormalizedData> getNormalizedDatas(Integer width, Integer height, CharSequence charSequence, String generation);
}

package ge.edu.tsu.handwriting_recognition.control_panel.service.normalizeddata;

import ge.edu.tsu.handwriting_recognition.control_panel.model.data.NormalizedData;
import ge.edu.tsu.handwriting_recognition.control_panel.model.info.CharSequence;

import java.util.List;

public interface NormalizedDataService {

    void addNormalizedData(NormalizedData normalizedData);

    List<NormalizedData> getNormalizedDatas(Integer width, Integer height, CharSequence charSequence, String generation);
}

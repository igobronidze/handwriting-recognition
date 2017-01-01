package ge.tsu.handwriting_recognition.service;

import ge.tsu.handwriting_recognition.model.NormalizedData;
import org.neuroph.core.data.DataSetRow;

import java.util.List;

public interface NormalizedDataService {

    DataSetRow getDataSetRow(NormalizedData normalizedData);

    List<NormalizedData> getInputDatas(String folderName);
}
package ge.tsu.handwritin_recognition.service;

import ge.tsu.handwritin_recognition.model.InputData;
import org.neuroph.core.data.DataSetRow;

public interface InputDataService {

    DataSetRow getDataSetRow(InputData inputData);
}

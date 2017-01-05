package ge.tsu.handwriting_recognition.data_creator.service.dao;

import ge.tsu.handwriting_recognition.data_creator.model.SystemParameter;
import ge.tsu.handwriting_recognition.data_creator.service.exception.HandwritingRecognitionException;

import java.util.List;

public interface SystemParameterDAO {

    void addSystemParameter(SystemParameter systemParameter) throws HandwritingRecognitionException;

    void editSystemParameter(SystemParameter systemParameter) throws HandwritingRecognitionException;

    void deleteSystemParameter(String key) throws HandwritingRecognitionException;

    List<SystemParameter> getSystemParameters(String key);

    String getSystemParameterValue(String key);
}

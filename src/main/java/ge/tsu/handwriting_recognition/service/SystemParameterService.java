package ge.tsu.handwriting_recognition.service;

import ge.tsu.handwriting_recognition.model.SystemParameter;
import ge.tsu.handwriting_recognition.service.exception.HandwritingRecognitionException;

import java.util.List;

public interface SystemParameterService {

    void addSystemParameter(SystemParameter systemParameter) throws HandwritingRecognitionException;

    void editSystemParameter(SystemParameter systemParameter) throws HandwritingRecognitionException;

    void deleteSystemParameter(String key) throws HandwritingRecognitionException;

    List<SystemParameter> getSystemParameters(String key);

    String getSystemParameterValue(String key, String defaultValue);
}

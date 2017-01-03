package ge.tsu.handwriting_recognition.data_creator.service;

import ge.tsu.handwriting_recognition.data_creator.model.SystemParameter;
import ge.tsu.handwriting_recognition.data_creator.service.dao.SystemParameterDAO;
import ge.tsu.handwriting_recognition.data_creator.service.dao.SystemParameterDAOImpl;
import ge.tsu.handwriting_recognition.data_creator.service.exception.HandwritingRecognitionException;

import java.util.List;

public class SystemParameterServiceImpl implements SystemParameterService {

    private SystemParameterDAO systemParameterDAO = new SystemParameterDAOImpl();

    @Override
    public void addSystemParameter(SystemParameter systemParameter) throws HandwritingRecognitionException {
        systemParameterDAO.addSystemParameter(systemParameter);
    }

    @Override
    public void editSystemParameter(SystemParameter systemParameter) throws HandwritingRecognitionException {
        systemParameterDAO.editSystemParameter(systemParameter);
    }

    @Override
    public void deleteSystemParameter(String key) throws HandwritingRecognitionException {
        systemParameterDAO.deleteSystemParameter(key);
    }

    @Override
    public List<SystemParameter> getSystemParameters(String key) {
        return systemParameterDAO.getSystemParameters(key);
    }

    @Override
    public String getSystemParameterValue(String key, String defaultValue) {
        String value = systemParameterDAO.getSystemParameterValue(key);
        if (value != null) {
            return value;
        } else {
            return defaultValue;
        }
    }
}

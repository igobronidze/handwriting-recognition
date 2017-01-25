package ge.edu.tsu.handwriting_recognition.control_panel.server.caching;

import ge.edu.tsu.handwriting_recognition.control_panel.model.sysparam.Parameter;
import ge.edu.tsu.handwriting_recognition.control_panel.model.sysparam.SystemParameter;
import ge.edu.tsu.handwriting_recognition.control_panel.server.dao.SystemParameterDAO;
import ge.edu.tsu.handwriting_recognition.control_panel.server.dao.SystemParameterDAOImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CachedSystemParameter {

    private static SystemParameterDAO systemParameterDAO = new SystemParameterDAOImpl();

    private static Map<String,String> cachedParameters;

    public static String getParameterValue(Parameter parameter) {
        if (cachedParameters == null) {
            fillParameters();
        }
        String value = cachedParameters.get(parameter.getKey());
        if (value != null) {
            return value;
        } else {
            return parameter.getDefaultValue();
        }
    }

    public static void deleteParameter(String key) {
        if (cachedParameters != null) {
            fillParameters();
        }
        cachedParameters.remove(key);
    }

    public static void editOrAddParameter(SystemParameter systemParameter) {
        if (cachedParameters != null) {
            fillParameters();
        }
        cachedParameters.put(systemParameter.getKey(), systemParameter.getValue());
    }

    private static void fillParameters() {
        cachedParameters = new HashMap<>();
        List<SystemParameter> systemParameterList = systemParameterDAO.getSystemParameters(null);
        for (SystemParameter systemParameter : systemParameterList) {
            cachedParameters.put(systemParameter.getKey(), systemParameter.getValue());
        }
    }
}

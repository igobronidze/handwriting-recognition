package ge.edu.tsu.handwriting_recognition.control_panel.service.systemparameter;

import ge.edu.tsu.handwriting_recognition.control_panel.model.exception.ControlPanelException;
import ge.edu.tsu.handwriting_recognition.control_panel.model.sysparam.Parameter;
import ge.edu.tsu.handwriting_recognition.control_panel.model.sysparam.SystemParameter;

import java.util.List;

public interface SystemParameterService {

    void addSystemParameter(SystemParameter systemParameter) throws ControlPanelException;

    void editSystemParameter(SystemParameter systemParameter) throws ControlPanelException;

    void deleteSystemParameter(String key) throws ControlPanelException;

    List<SystemParameter> getSystemParameters(String key);

    String getParameterValue(Parameter parameter);

    Integer getIntegerParameterValue(Parameter parameter);

    Float getFloatParameterValue(Parameter parameter);

    Long getLongParameterValue(Parameter parameter);

    List<Integer> getIntegerListParameterValue(Parameter parameter);
}

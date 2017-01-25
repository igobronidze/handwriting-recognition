package ge.edu.tsu.handwriting_recognition.control_panel.server.dao;

import ge.edu.tsu.handwriting_recognition.control_panel.model.exception.ControlPanelException;
import ge.edu.tsu.handwriting_recognition.control_panel.model.sysparam.SystemParameter;

import java.util.List;

public interface SystemParameterDAO {

    void addSystemParameter(SystemParameter systemParameter) throws ControlPanelException;

    void editSystemParameter(SystemParameter systemParameter) throws ControlPanelException;

    void deleteSystemParameter(String key) throws ControlPanelException;

    List<SystemParameter> getSystemParameters(String key);

    String getSystemParameterValue(String key);
}

package ge.edu.tsu.handwriting_recognition.control_panel.console.ui.propeties;

import ge.edu.tsu.handwriting_recognition.control_panel.model.sysparam.SystemParameter;
import javafx.beans.property.SimpleStringProperty;

public class SystemParameterProperty {
    private final SimpleStringProperty key;

    private final SimpleStringProperty value;

    public SystemParameterProperty(SystemParameter systemParameter) {
        this.key = new SimpleStringProperty(systemParameter.getKey());
        this.value = new SimpleStringProperty(systemParameter.getValue());
    }

    public String getKey() {
        return key.getValue();
    }

    public void setKey(String key) {
        this.key.setValue(key);
    }

    public String getValue() {
        return value.getValue();
    }

    public void setValue(String value) {
        this.value.setValue(value);
    }
}

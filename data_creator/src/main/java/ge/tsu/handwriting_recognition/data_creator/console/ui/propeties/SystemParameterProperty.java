package ge.tsu.handwriting_recognition.data_creator.console.ui.propeties;

import ge.tsu.handwriting_recognition.data_creator.model.SystemParameter;
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

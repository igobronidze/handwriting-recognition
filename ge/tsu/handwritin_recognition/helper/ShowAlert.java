package ge.tsu.handwritin_recognition.helper;

import javafx.scene.control.Alert;

public class ShowAlert {

    public static void showSimpleAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(text);
        alert.showAndWait();
    }

    public static void showWarning(String text) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(text);
        alert.showAndWait();
    }

}

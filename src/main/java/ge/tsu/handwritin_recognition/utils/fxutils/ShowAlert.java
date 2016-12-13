package ge.tsu.handwritin_recognition.utils.fxutils;

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

    public static void showWarning(String text, Exception ex) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(text);
        alert.setContentText(ex.getMessage());
        alert.showAndWait();
    }
}

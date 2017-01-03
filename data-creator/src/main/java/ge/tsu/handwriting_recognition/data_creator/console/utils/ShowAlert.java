package ge.tsu.handwriting_recognition.data_creator.console.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;

public class ShowAlert {

    private static Alert alert;

    public static void showSimpleAlert(String text) {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(text);
        addStyleToAlert();
        alert.showAndWait();
    }

    public static void showWarning(String text) {
        alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(text);
        addStyleToAlert();
        alert.showAndWait();
    }

    public static void showWarning(String text, Exception ex) {
        alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(text);
        alert.setContentText(ex.getMessage());
        addStyleToAlert();
        alert.showAndWait();
    }

    private static void addStyleToAlert() {
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-font-family: sylfaen");
    }
}

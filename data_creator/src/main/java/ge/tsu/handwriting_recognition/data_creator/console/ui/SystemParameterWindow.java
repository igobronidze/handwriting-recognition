package ge.tsu.handwriting_recognition.data_creator.console.ui;

import ge.tsu.handwriting_recognition.data_creator.console.resources.Messages;
import ge.tsu.handwriting_recognition.data_creator.console.ui.propeties.SystemParameterProperty;
import ge.tsu.handwriting_recognition.data_creator.console.utils.ShowAlert;
import ge.tsu.handwriting_recognition.data_creator.console.utils.StageUtils;
import ge.tsu.handwriting_recognition.data_creator.model.SystemParameter;
import ge.tsu.handwriting_recognition.data_creator.service.SystemParameterService;
import ge.tsu.handwriting_recognition.data_creator.service.SystemParameterServiceImpl;
import ge.tsu.handwriting_recognition.data_creator.service.exception.HandwritingRecognitionException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SystemParameterWindow extends Stage {

    private SystemParameterService systemParameterService = new SystemParameterServiceImpl();

    private BorderPane root;

    private HBox topPane;

    private HBox bottomPane;

    private TableView<SystemParameterProperty> table;

    private TextField keyTextField;

    private TextField valueTextField;

    public SystemParameterWindow() {
        this.setTitle(Messages.get("systemParameter"));
        root = new BorderPane();
        initTopPane();
        initTableView();
        initBottomPane();
        this.setScene(new Scene(root));
        StageUtils.setMaxSize(this);
    }

    private void initTableView() {
        table = new TableView<>();
        table.setStyle("-fx-font-family: sylfaen");

        TableColumn keyColumn = new TableColumn(Messages.get("key"));
        keyColumn.prefWidthProperty().bind(this.widthProperty().divide(2).subtract(10));
        keyColumn.setCellValueFactory(new PropertyValueFactory<SystemParameterProperty, String>("key"));

        TableColumn valueColumn = new TableColumn(Messages.get("value"));
        valueColumn.prefWidthProperty().bind(this.widthProperty().divide(2).subtract(10));
        valueColumn.setCellValueFactory(new PropertyValueFactory<SystemParameterProperty, String>("value"));

        loadSystemParameterData(null);
        table.getColumns().addAll(keyColumn, valueColumn);

        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SystemParameterProperty>(){
            @Override
            public void changed(ObservableValue observableValue, SystemParameterProperty oldValue, SystemParameterProperty newValue) {
                if (newValue != null) {
                    keyTextField.setText(newValue.getKey());
                    valueTextField.setText(newValue.getValue());
                }
            }
        });

        root.setCenter(table);
    }

    private void initTopPane() {
        topPane = new HBox();
        topPane.setSpacing(5);
        topPane.setPadding(new Insets(10, 10, 10, 10));
        TextField textField = new TextField();
        textField.setPromptText(Messages.get("key"));
        textField.setStyle("-fx-font-family: sylfaen");
        Button button = new Button(Messages.get("search"));
        button.setStyle("-fx-font-family: sylfaen");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (textField.getText() == null || textField.getText().isEmpty()) {
                    loadSystemParameterData(null);
                } else {
                    loadSystemParameterData(textField.getText());
                }
            }
        });
        topPane.getChildren().addAll(textField, button);
        root.setTop(topPane);
    }

    private void initBottomPane() {
        bottomPane = new HBox();
        bottomPane.setSpacing(5);
        bottomPane.setPadding(new Insets(10, 10, 10, 10));
        keyTextField = new TextField();
        keyTextField.setStyle("-fx-font-family: sylfaen");
        keyTextField.setPromptText(Messages.get("key"));
        valueTextField = new TextField();
        valueTextField.setStyle("-fx-font-family: sylfaen");
        valueTextField.setPromptText(Messages.get("value"));
        Button addButton = new Button(Messages.get("add"));
        addButton.setStyle("-fx-font-family: sylfaen");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (keyTextField.getText() == null || keyTextField.getText().isEmpty()
                        || valueTextField.getText() == null || valueTextField.getText().isEmpty()) {
                    ShowAlert.showWarning(Messages.get("pleaseFillAllField"));
                } else {
                    try {
                        SystemParameter systemParameter = new SystemParameter(keyTextField.getText(), valueTextField.getText());
                        systemParameterService.addSystemParameter(systemParameter);
                        keyTextField.setText("");
                        valueTextField.setText("");
                        loadSystemParameterData(null);
                    } catch (HandwritingRecognitionException ex) {
                        ShowAlert.showWarning(Messages.get(ex.getMessage()));
                    }
                }
            }
        });
        Button editButton = new Button(Messages.get("edit"));
        editButton.setStyle("-fx-font-family: sylfaen");
        editButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (keyTextField.getText() == null || keyTextField.getText().isEmpty()
                        || valueTextField.getText() == null || valueTextField.getText().isEmpty()) {
                    ShowAlert.showWarning(Messages.get("pleaseFillAllField"));
                } else {
                    try {
                        SystemParameter systemParameter = new SystemParameter(keyTextField.getText(), valueTextField.getText());
                        systemParameterService.editSystemParameter(systemParameter);
                        keyTextField.setText("");
                        valueTextField.setText("");
                        loadSystemParameterData(null);
                    } catch (HandwritingRecognitionException ex) {
                        ShowAlert.showWarning(Messages.get(ex.getMessage()));
                    }
                }
            }
        });
        Button deleteButton = new Button(Messages.get("delete"));
        deleteButton.setStyle("-fx-font-family: sylfaen");
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText(Messages.get("confirmDeleteOperation"));

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    if (keyTextField.getText() == null || keyTextField.getText().isEmpty()) {
                        ShowAlert.showWarning(Messages.get("pleaseFillAllField"));
                    } else {
                        try {
                            String key = keyTextField.getText();
                            systemParameterService.deleteSystemParameter(key);
                            keyTextField.setText("");
                            valueTextField.setText("");
                            loadSystemParameterData(null);
                        } catch (HandwritingRecognitionException ex) {
                            ShowAlert.showWarning(Messages.get(ex.getMessage()));
                        }
                    }
                }
            }
        });
        bottomPane.getChildren().addAll(keyTextField, valueTextField, addButton, editButton, deleteButton);
        root.setBottom(bottomPane);
    }

    private void loadSystemParameterData(String key) {
        List<SystemParameter> systemParameterList = systemParameterService.getSystemParameters(key);
        List<SystemParameterProperty> systemParameterPropertyList = new ArrayList<>();
        for (SystemParameter systemParameter : systemParameterList) {
            systemParameterPropertyList.add(new SystemParameterProperty(systemParameter));
        }
        final ObservableList<SystemParameterProperty> data = FXCollections.observableArrayList(systemParameterPropertyList);
        table.setItems(data);
    }
}

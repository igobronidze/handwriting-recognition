package ge.tsu.handwriting_recognition.data_creator.console.ui;

import ge.tsu.handwriting_recognition.data_creator.console.resources.Messages;
import ge.tsu.handwriting_recognition.data_creator.console.utils.ShowAlert;
import ge.tsu.handwriting_recognition.data_creator.console.utils.StageUtils;
import ge.tsu.handwriting_recognition.data_creator.neuralnetwork.INeuralNetwork;
import ge.tsu.handwriting_recognition.data_creator.neuralnetwork.MyNeuralNetwork;
import ge.tsu.handwriting_recognition.data_creator.service.SystemParameterService;
import ge.tsu.handwriting_recognition.data_creator.service.SystemParameterServiceImpl;
import ge.tsu.handwriting_recognition.neural_network.exception.NeuralNetworkException;
import ge.tsu.handwriting_recognition.neural_network.neural.NeuralNetwork;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NeuralNetworkControlWindow extends Stage {

    private INeuralNetwork neuralNetwork = new MyNeuralNetwork();

    private SystemParameterService systemParameterService = new SystemParameterServiceImpl();

    private int normalizedDataDefaultWidth = Integer.parseInt(systemParameterService.getSystemParameterValue("normalizedDataDefaultWidth", "21"));

    private int normalizedDataDefaultHeight = Integer.parseInt(systemParameterService.getSystemParameterValue("normalizedDataDefaultHeight", "31"));

    private String neuralNetworkDirectory = systemParameterService.getSystemParameterValue("neuralNetworkDirectory", "D:\\sg\\handwriting_recognition\\network");

    private VBox root;

    private Button testButton;

    private ComboBox networkComboBox;

    public NeuralNetworkControlWindow() {
        this.setTitle(Messages.get("neuralNetworkControl"));
        root = new VBox();
        initTrainPane();
        initTestPane();
        initGrid();
        this.setScene(new Scene(root));
        StageUtils.setMaxSize(this);
    }

    private void initTestPane() {
        Label generationLabel = new Label(Messages.get("generation") + ":");
        generationLabel.setStyle("-fx-font-family: sylfaen");
        TextField generationTextField = new TextField();
        generationTextField.setStyle("-fx-font-family: sylfaen");
        generationTextField.setPrefWidth(130);
        Label heightLabel = new Label(Messages.get("height") + ":");
        heightLabel.setStyle("-fx-font-family: sylfaen");
        TextField heightField = new TextField();
        heightField.setText(Integer.toString(normalizedDataDefaultHeight));
        heightField.setPrefWidth(80);
        Label widthLabel = new Label(Messages.get("width") + ":");
        widthLabel.setStyle("-fx-font-family: sylfaen");
        TextField widthField = new TextField();
        widthField.setText(Integer.toString(normalizedDataDefaultWidth));
        widthField.setPrefWidth(80);
        Label networkLabel = new Label(Messages.get("neuralNetwork"));
        networkLabel.setStyle("-fx-font-family: sylfaen");
        testButton = new Button(Messages.get("test"));
        testButton.setStyle("-fx-font-family: sylfaen");
        networkComboBox = new ComboBox();
        networkComboBox.setPrefWidth(170);
        networkComboBox.setStyle("-fx-font-family: sylfaen");
        realoadNetworkComboBox();
        testButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int width = normalizedDataDefaultWidth, height = normalizedDataDefaultHeight;
                try {
                    width = Integer.parseInt(widthField.getText());
                    height = Integer.parseInt(heightField.getText());
                } catch (Exception ex) {
                    ShowAlert.showWarning(Messages.get("pleaseFillAllField"));
                }
                if (generationTextField.getText() == null || generationTextField.getText().isEmpty()) {
                    ShowAlert.showWarning(Messages.get("pleaseFillAllField"));
                    return;
                }
                String networkName = networkComboBox.getValue().toString();
                int id = Integer.parseInt(networkName.split("_")[0]);
                float error = neuralNetwork.test(width, height, generationTextField.getText(), neuralNetworkDirectory + "\\" + networkName, id);
                ShowAlert.showSimpleAlert("" + error);
            }
        });
        HBox hBox = new HBox(10);
        hBox.setPadding(new Insets(15, 15, 15, 15));
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.getChildren().addAll(heightLabel, heightField, widthLabel, widthField, generationLabel, generationTextField,
                networkLabel, networkComboBox, testButton);
        root.getChildren().add(hBox);
    }

    private void initTrainPane() {
        Label generationLabel = new Label(Messages.get("generation") + ":");
        generationLabel.setStyle("-fx-font-family: sylfaen");
        TextField generationTextField = new TextField();
        generationTextField.setStyle("-fx-font-family: sylfaen");
        generationTextField.setPrefWidth(130);
        Label heightLabel = new Label(Messages.get("height") + ":");
        heightLabel.setStyle("-fx-font-family: sylfaen");
        TextField heightField = new TextField();
        heightField.setText(Integer.toString(normalizedDataDefaultHeight));
        heightField.setPrefWidth(80);
        Label widthLabel = new Label(Messages.get("width") + ":");
        widthLabel.setStyle("-fx-font-family: sylfaen");
        TextField widthField = new TextField();
        widthField.setText(Integer.toString(normalizedDataDefaultWidth));
        widthField.setPrefWidth(80);
        Button trainButton = new Button(Messages.get("train"));
        trainButton.setStyle("-fx-font-family: sylfaen");
        trainButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int width = normalizedDataDefaultWidth, height = normalizedDataDefaultHeight;
                try {
                    width = Integer.parseInt(widthField.getText());
                    height = Integer.parseInt(heightField.getText());
                } catch (Exception ex) {
                    ShowAlert.showWarning(Messages.get("pleaseFillAllField"));
                }
                if (generationTextField.getText() == null || generationTextField.getText().isEmpty()) {
                    ShowAlert.showWarning(Messages.get("pleaseFillAllField"));
                    return;
                }
                neuralNetwork.trainNeural(width, height, generationTextField.getText());
                NeuralNetworkControlWindow.this.realoadNetworkComboBox();
            }
        });
        HBox hBox = new HBox(10);
        hBox.setPadding(new Insets(15, 15, 15, 15));
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.getChildren().addAll(heightLabel, heightField, widthLabel, widthField, generationLabel, generationTextField, trainButton);
        root.getChildren().add(hBox);
    }

    private void initGrid() {

    }

    private List<String> getNetworkNames() {
        List<String> networks = new ArrayList<>();
        File folder = new File(neuralNetworkDirectory);
        for (File file : folder.listFiles()) {
            networks.add(file.getName());
        }
        return networks;
    }

    private void realoadNetworkComboBox() {
        networkComboBox.setItems(FXCollections.observableArrayList(getNetworkNames()));
        if (networkComboBox.getItems().size() != 0) {
            networkComboBox.setValue(networkComboBox.getItems().get(0));
            testButton.setDisable(false);
        } else {
            testButton.setDisable(true);
        }
    }
}

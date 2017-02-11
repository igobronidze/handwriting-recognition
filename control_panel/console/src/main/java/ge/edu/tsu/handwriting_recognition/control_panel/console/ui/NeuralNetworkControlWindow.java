package ge.edu.tsu.handwriting_recognition.control_panel.console.ui;

import ge.edu.tsu.handwriting_recognition.control_panel.console.resources.Messages;
import ge.edu.tsu.handwriting_recognition.control_panel.console.ui.propeties.NetworkInfoProperty;
import ge.edu.tsu.handwriting_recognition.control_panel.console.utils.ShowAlert;
import ge.edu.tsu.handwriting_recognition.control_panel.console.utils.StageUtils;
import ge.edu.tsu.handwriting_recognition.control_panel.model.network.NetworkInfo;
import ge.edu.tsu.handwriting_recognition.control_panel.model.network.TestingInfo;
import ge.edu.tsu.handwriting_recognition.control_panel.model.sysparam.Parameter;
import ge.edu.tsu.handwriting_recognition.control_panel.service.networkinfo.NetworkInfoService;
import ge.edu.tsu.handwriting_recognition.control_panel.service.networkinfo.NetworkInfoServiceImpl;
import ge.edu.tsu.handwriting_recognition.control_panel.service.neuralnetwork.NeuralNetworkManagerType;
import ge.edu.tsu.handwriting_recognition.control_panel.service.neuralnetwork.NeuralNetworkService;
import ge.edu.tsu.handwriting_recognition.control_panel.service.neuralnetwork.NeuralNetworkServiceImpl;
import ge.edu.tsu.handwriting_recognition.control_panel.service.systemparameter.SystemParameterService;
import ge.edu.tsu.handwriting_recognition.control_panel.service.systemparameter.SystemParameterServiceImpl;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NeuralNetworkControlWindow extends Stage {

    private SystemParameterService systemParameterService = new SystemParameterServiceImpl();

    private NetworkInfoService networkInfoService = new NetworkInfoServiceImpl();

    private Parameter normalizedDataDefaultWidthParameter = new Parameter("normalizedDataDefaultWidth", "21");

    private Parameter normalizedDataDefaultHeightParameter = new Parameter("normalizedDataDefaultHeight", "31");

    private Parameter neuralNetworkDirectoryParameter = new Parameter("neuralNetworkDirectory", "D:\\sg\\handwriting_recognition\\network");

    private VBox root;

    private Button testButton;

    private ComboBox networkComboBox;

    private TableView<NetworkInfoProperty> table;

    private Integer selectedNetworkId;

    private String selectedNetworkName;

    private List<TestingInfo> testingInfoList;

    public NeuralNetworkControlWindow() {
        this.setTitle(Messages.get("neuralNetworkControl"));
        root = new VBox();
        initTrainPane();
        initTestPane();
        initMainPane();
        initButtomPane();
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
        heightField.setText(systemParameterService.getParameterValue(normalizedDataDefaultHeightParameter));
        heightField.setPrefWidth(80);
        Label widthLabel = new Label(Messages.get("width") + ":");
        widthLabel.setStyle("-fx-font-family: sylfaen");
        TextField widthField = new TextField();
        widthField.setText(systemParameterService.getParameterValue(normalizedDataDefaultWidthParameter));
        widthField.setPrefWidth(80);
        Label networkLabel = new Label(Messages.get("neuralNetwork"));
        networkLabel.setStyle("-fx-font-family: sylfaen");
        testButton = new Button(Messages.get("test"));
        testButton.setStyle("-fx-font-family: sylfaen");
        networkComboBox = new ComboBox();
        networkComboBox.setPrefWidth(170);
        networkComboBox.setStyle("-fx-font-family: sylfaen");
        reloadNetworkComboBox();
        testButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int width = systemParameterService.getIntegerParameterValue(normalizedDataDefaultWidthParameter);
                int height = systemParameterService.getIntegerParameterValue(normalizedDataDefaultHeightParameter);
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
                NeuralNetworkService neuralNetworkService = new NeuralNetworkServiceImpl(NeuralNetworkManagerType.MY_NEURAL_NETWORK);
                float error = neuralNetworkService.test(width, height, generationTextField.getText(), systemParameterService.getParameterValue(neuralNetworkDirectoryParameter) + "\\" + networkName, id);
                ShowAlert.showSimpleAlert("" + error);
                loadNetworkInfoData();
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
        heightField.setText(systemParameterService.getParameterValue(normalizedDataDefaultHeightParameter));
        heightField.setPrefWidth(80);
        Label widthLabel = new Label(Messages.get("width") + ":");
        widthLabel.setStyle("-fx-font-family: sylfaen");
        TextField widthField = new TextField();
        widthField.setText(systemParameterService.getParameterValue(normalizedDataDefaultWidthParameter));
        widthField.setPrefWidth(80);
        Button trainButton = new Button(Messages.get("train"));
        trainButton.setStyle("-fx-font-family: sylfaen");
        trainButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int width = systemParameterService.getIntegerParameterValue(normalizedDataDefaultWidthParameter);
                int height = systemParameterService.getIntegerParameterValue(normalizedDataDefaultHeightParameter);
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
                NeuralNetworkService neuralNetworkService = new NeuralNetworkServiceImpl(NeuralNetworkManagerType.MY_NEURAL_NETWORK);
                neuralNetworkService.trainNeural(width, height, generationTextField.getText());
                NeuralNetworkControlWindow.this.reloadNetworkComboBox();
                loadNetworkInfoData();
            }
        });
        HBox hBox = new HBox(10);
        hBox.setPadding(new Insets(15, 15, 15, 15));
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.getChildren().addAll(heightLabel, heightField, widthLabel, widthField, generationLabel, generationTextField, trainButton);
        root.getChildren().add(hBox);
    }

    @SuppressWarnings("unchecked")
    private void initMainPane() {
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10, 10, 10, 10));
        table = new TableView<>();
        table.setStyle("-fx-font-family: sylfaen;");
        TableColumn idColumn = new TableColumn(Messages.get("id"));
        idColumn.setPrefWidth(25);
        idColumn.setCellValueFactory(new PropertyValueFactory<NetworkInfoProperty, String>("id"));
        TableColumn widthColumn = new TableColumn(Messages.get("width"));
        widthColumn.setPrefWidth(45);
        widthColumn.setCellValueFactory(new PropertyValueFactory<NetworkInfoProperty, String>("width"));
        TableColumn heightColumn = new TableColumn(Messages.get("height"));
        heightColumn.setPrefWidth(55);
        heightColumn.setCellValueFactory(new PropertyValueFactory<NetworkInfoProperty, String>("height"));
        TableColumn generationColumn = new TableColumn(Messages.get("generation"));
        generationColumn.setPrefWidth(60);
        generationColumn.setCellValueFactory(new PropertyValueFactory<NetworkInfoProperty, String>("generation"));
        TableColumn numberOfDataColumn = new TableColumn(Messages.get("numberOfData"));
        numberOfDataColumn.setPrefWidth(70);
        numberOfDataColumn.setCellValueFactory(new PropertyValueFactory<NetworkInfoProperty, String>("numberOfData"));
        TableColumn trainingDurationColumn = new TableColumn(Messages.get("trainingDuration"));
        trainingDurationColumn.setPrefWidth(80);
        trainingDurationColumn.setCellValueFactory(new PropertyValueFactory<NetworkInfoProperty, String>("trainingDuration"));
        TableColumn weightMinValueColumn = new TableColumn(Messages.get("weightMinValue"));
        weightMinValueColumn.setPrefWidth(60);
        weightMinValueColumn.setCellValueFactory(new PropertyValueFactory<NetworkInfoProperty, String>("weightMinValue"));
        TableColumn weightMaxValueColumn = new TableColumn(Messages.get("weightMaxValue"));
        weightMaxValueColumn.setPrefWidth(60);
        weightMaxValueColumn.setCellValueFactory(new PropertyValueFactory<NetworkInfoProperty, String>("weightMaxValue"));
        TableColumn biasMinValueColumn = new TableColumn(Messages.get("biasMinValue"));
        biasMinValueColumn.setPrefWidth(60);
        biasMinValueColumn.setCellValueFactory(new PropertyValueFactory<NetworkInfoProperty, String>("biasMinValue"));
        TableColumn biasMaxValueColumn = new TableColumn(Messages.get("biasMaxValue"));
        biasMaxValueColumn.setPrefWidth(60);
        biasMaxValueColumn.setCellValueFactory(new PropertyValueFactory<NetworkInfoProperty, String>("biasMaxValue"));
        TableColumn transferFunctionTypeColumn = new TableColumn(Messages.get("transferFunctionType"));
        transferFunctionTypeColumn.setPrefWidth(80);
        transferFunctionTypeColumn.setCellValueFactory(new PropertyValueFactory<NetworkInfoProperty, String>("transferFunctionType"));
        TableColumn learningRateColumn = new TableColumn(Messages.get("learningRate"));
        learningRateColumn.setPrefWidth(75);
        learningRateColumn.setCellValueFactory(new PropertyValueFactory<NetworkInfoProperty, String>("learningRate"));
        TableColumn minErrorColumn = new TableColumn(Messages.get("minError"));
        minErrorColumn.setPrefWidth(70);
        minErrorColumn.setCellValueFactory(new PropertyValueFactory<NetworkInfoProperty, String>("minError"));
        TableColumn trainingMaxIterationColumn = new TableColumn(Messages.get("trainingMaxIteration"));
        trainingMaxIterationColumn.setPrefWidth(70);
        trainingMaxIterationColumn.setCellValueFactory(new PropertyValueFactory<NetworkInfoProperty, String>("trainingMaxIteration"));
        TableColumn numberOfTrainingDataInOneIterationColumn = new TableColumn(Messages.get("numberOfTrainingDataInOneIteration"));
        numberOfTrainingDataInOneIterationColumn.setPrefWidth(77);
        numberOfTrainingDataInOneIterationColumn.setCellValueFactory(new PropertyValueFactory<NetworkInfoProperty, String>("numberOfTrainingDataInOneIteration"));
        TableColumn bestSquaredErrorColumn = new TableColumn(Messages.get("bestSquaredError"));
        bestSquaredErrorColumn.setPrefWidth(95);
        bestSquaredErrorColumn.setCellValueFactory(new PropertyValueFactory<NetworkInfoProperty, String>("bestSquaredError"));
        TableColumn bestPercentageOfCorrectsColumn = new TableColumn(Messages.get("bestPercentageOfCorrects"));
        bestPercentageOfCorrectsColumn.setPrefWidth(95);
        bestPercentageOfCorrectsColumn.setCellValueFactory(new PropertyValueFactory<NetworkInfoProperty, String>("bestPercentageOfCorrects"));
        TableColumn bestDiffBetweenAnsAndBestColumn = new TableColumn(Messages.get("bestDiffBetweenAnsAndBest"));
        bestDiffBetweenAnsAndBestColumn.setPrefWidth(95);
        bestDiffBetweenAnsAndBestColumn.setCellValueFactory(new PropertyValueFactory<NetworkInfoProperty, String>("bestDiffBetweenAnsAndBest"));
        TableColumn bestNormalizedGeneralErrorColumn = new TableColumn(Messages.get("bestNormalizedGeneralError"));
        bestNormalizedGeneralErrorColumn.setPrefWidth(95);
        bestNormalizedGeneralErrorColumn.setCellValueFactory(new PropertyValueFactory<NetworkInfoProperty, String>("bestNormalizedGeneralError"));
        TableColumn charSequenceColumn = new TableColumn(Messages.get("charSequence"));
        charSequenceColumn.setPrefWidth(60);
        charSequenceColumn.setCellValueFactory(new PropertyValueFactory<NetworkInfoProperty, String>("charSequence"));
        TableColumn hiddenLayerColumn = new TableColumn(Messages.get("hiddenLayer"));
        hiddenLayerColumn.setPrefWidth(100);
        hiddenLayerColumn.setCellValueFactory(new PropertyValueFactory<NetworkInfoProperty, String>("hiddenLayer"));

        table.getColumns().addAll(idColumn, widthColumn, heightColumn, generationColumn, numberOfDataColumn, trainingDurationColumn, weightMinValueColumn, weightMaxValueColumn, biasMinValueColumn,
                biasMaxValueColumn, transferFunctionTypeColumn, learningRateColumn, minErrorColumn, trainingMaxIterationColumn, numberOfTrainingDataInOneIterationColumn, bestSquaredErrorColumn,
                bestPercentageOfCorrectsColumn, bestDiffBetweenAnsAndBestColumn, bestNormalizedGeneralErrorColumn, charSequenceColumn, hiddenLayerColumn);
        loadNetworkInfoData();

        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<NetworkInfoProperty>(){
            @Override
            public void changed(ObservableValue<? extends NetworkInfoProperty> observable, NetworkInfoProperty oldValue, NetworkInfoProperty newValue) {
                if (newValue != null) {
                    selectedNetworkId = newValue.getId();
                    selectedNetworkName =  newValue.getId() + "_" + newValue.getGeneration() + "_" + newValue.getWidth() + "_" + newValue.getHeight() + ".nnet";
                    testingInfoList = newValue.getTestingInfoList();
                } else {
                    selectedNetworkId = null;
                    selectedNetworkName = null;
                    testingInfoList = null;
                }
            }
        });

        vBox.getChildren().add(table);
        root.getChildren().add(vBox);
    }

    private void initButtomPane() {
        CheckBox fileCheckBox = new CheckBox(Messages.get("deleteFromFile"));
        fileCheckBox.setStyle("-fx-font-family: sylfaen");
        fileCheckBox.setSelected(true);
        CheckBox databaseCheckBox = new CheckBox(Messages.get("deleteFromDatabase"));
        databaseCheckBox.setStyle("-fx-font-family: sylfaen");
        databaseCheckBox.setSelected(true);
        Button deleteButton = new Button(Messages.get("delete"));
        deleteButton.setStyle("-fx-font-family: sylfaen");
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (fileCheckBox.isSelected() || databaseCheckBox.isSelected()) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText(Messages.get("confirmDeleteOperation"));

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        if (selectedNetworkId != null) {
                            if (fileCheckBox.isSelected() && selectedNetworkName != null) {
                                File file = new File(systemParameterService.getParameterValue(neuralNetworkDirectoryParameter) + "\\" + selectedNetworkName);
                                file.delete();
                            }
                            if (databaseCheckBox.isSelected()) {
                                networkInfoService.deleteNetworkInfo(selectedNetworkId);
                                loadNetworkInfoData();
                            }
                        }
                    }
                }
            }
        });
        Button testInfoButton = new Button(Messages.get("testInfo"));
        testInfoButton.setStyle("-fx-font-family: sylfaen");
        testInfoButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // TODO[sg] დასამუშავებელია ფანჯარა რომლეშიც იქნება მთლიანი მონაცემები ცდომილებაზე
//                if (testingInfoList != null) {
//                    String text = "";
//                    for (int i = 0; i < testingInfoList.size(); i++) {
//                        TestingInfo testingInfo = testingInfoList.get(i);
//                        text += (i + 1) + ". " + Messages.get("generation") + " - " + testingInfo.getGeneration();
//                        text += "    " + Messages.get("numberOfData") + " - " + testingInfo.getNumberOfTest();
//                        text += "    " + Messages.get("averageError") + " - " + (testingInfo.getError() / testingInfo.getNumberOfTest()) + System.lineSeparator();
//                    }
//                    ShowAlert.showSimpleAlert(text);
//                }
            }
        });
        HBox hBox = new HBox(10);
        hBox.setPadding(new Insets(15, 15, 15, 15));
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.getChildren().addAll(fileCheckBox, databaseCheckBox, deleteButton, testInfoButton);
        root.getChildren().add(hBox);
    }

    private List<String> getNetworkNames() {
        List<String> networks = new ArrayList<>();
        File folder = new File(systemParameterService.getParameterValue(neuralNetworkDirectoryParameter));
        for (File file : folder.listFiles()) {
            networks.add(file.getName());
        }
        return networks;
    }

    private void reloadNetworkComboBox() {
        networkComboBox.setItems(FXCollections.observableArrayList(getNetworkNames()));
        if (networkComboBox.getItems().size() != 0) {
            networkComboBox.setValue(networkComboBox.getItems().get(0));
            testButton.setDisable(false);
        } else {
            testButton.setDisable(true);
        }
    }

    private void loadNetworkInfoData() {
        List<NetworkInfo> networkInfoList = networkInfoService.getNetworkInfoList(null, null);
        List<NetworkInfoProperty> networkInfoPropertyList = new ArrayList<>();
        for (NetworkInfo networkInfo : networkInfoList) {
            networkInfoPropertyList.add(new NetworkInfoProperty(networkInfo));
        }
        final ObservableList<NetworkInfoProperty> data = FXCollections.observableArrayList(networkInfoPropertyList);
        table.setItems(data);
    }
}

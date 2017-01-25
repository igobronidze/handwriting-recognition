package ge.edu.tsu.handwriting_recognition.control_panel.console.ui;

import ge.edu.tsu.handwriting_recognition.control_panel.console.resources.Messages;
import ge.edu.tsu.handwriting_recognition.control_panel.console.utils.ShowAlert;
import ge.edu.tsu.handwriting_recognition.control_panel.model.data.NormalizedData;
import ge.edu.tsu.handwriting_recognition.control_panel.model.info.CharSequence;
import ge.edu.tsu.handwriting_recognition.control_panel.model.sysparam.Parameter;
import ge.edu.tsu.handwriting_recognition.control_panel.service.imageprocessing.ImageProcessingService;
import ge.edu.tsu.handwriting_recognition.control_panel.service.imageprocessing.ImageProcessingServiceImpl;
import ge.edu.tsu.handwriting_recognition.control_panel.service.neuralnetwork.NeuralNetworkManagerType;
import ge.edu.tsu.handwriting_recognition.control_panel.service.neuralnetwork.NeuralNetworkService;
import ge.edu.tsu.handwriting_recognition.control_panel.service.neuralnetwork.NeuralNetworkServiceImpl;
import ge.edu.tsu.handwriting_recognition.control_panel.service.normalizeddata.NormalizedDataService;
import ge.edu.tsu.handwriting_recognition.control_panel.service.normalizeddata.NormalizedDataServiceImpl;
import ge.edu.tsu.handwriting_recognition.control_panel.service.systemparameter.SystemParameterService;
import ge.edu.tsu.handwriting_recognition.control_panel.service.systemparameter.SystemParameterServiceImpl;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataCreatorWithDraw extends Stage {

    private SystemParameterService systemParameterService = new SystemParameterServiceImpl();

    private NormalizedDataService normalizedDataService = new NormalizedDataServiceImpl();

    private ImageProcessingService imageProcessingService = new ImageProcessingServiceImpl();

    private Parameter createdImagesDirectoryParameter = new Parameter("createdImagesDirectory", "D:\\sg\\handwriting_recognition\\images\\created_images");

    private Parameter firstSymbolInCharSequenceParameter = new Parameter("firstSymbolInCharSequence", "ა");

    private Parameter lastSymbolInCharSequenceParameter = new Parameter("lastSymbolInCharSequence", "ჰ");

    private Parameter normalizedDataDefaultWidthParameter = new Parameter("normalizedDataDefaultWidth", "21");

    private Parameter normalizedDataDefaultHeightParameter = new Parameter("normalizedDataDefaultHeight", "31");

    private Parameter neuralNetworkDirectoryParameter = new Parameter("neuralNetworkDirectory", "D:\\sg\\handwriting_recognition\\network");

    private final int CANVAS_BACKGROUND_COLOR = -1;

    private final int CANVAS_WIDTH = 400;

    private final int CANVAS_HEIGHT = 350;

    private BorderPane root;

    private Canvas canvas;

    private TextField answerField;

    private TextField widthField;

    private TextField heightField;

    private TextField generationTextField;

    private ComboBox directoriesComboBox;

    private ComboBox networkComboBox;

    private Button guessButton;

    public DataCreatorWithDraw() {
        this.setTitle(Messages.get("dataCreatorWithDraw"));
        root = new BorderPane();
        initTopPane();
        initMainPane();
        initBottomPane();
        this.setScene(new Scene(root));
        this.setWidth(740);
        this.setHeight(570);
    }

    private void initTopPane() {
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(15, 0, 0, 0));

        directoriesComboBox = new ComboBox();
        directoriesComboBox.setStyle("-fx-font-family: sylfaen");
        directoriesComboBox.setItems(FXCollections.observableArrayList(getInnerDirectories()));
        if (directoriesComboBox.getItems().size() != 0) {
            directoriesComboBox.setValue(directoriesComboBox.getItems().get(0));
        }
        directoriesComboBox.setPrefWidth(150);
        Button button = new Button(Messages.get("saveInDirectory"));
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (answerField.getText() == null || answerField.getText().length() != 1) {
                    ShowAlert.showWarning(Messages.get("irrelevantAnswer"));
                    return;
                }
                try {
                    WritableImage writableImage = new WritableImage(CANVAS_WIDTH, CANVAS_HEIGHT);
                    canvas.snapshot(null, writableImage);
                    RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                    File file = new File(systemParameterService.getParameterValue(createdImagesDirectoryParameter) + "\\" + directoriesComboBox.getValue() + "\\" +
                            answerField.getText() + "_" + renderedImage.hashCode() + ".png");
                    ImageIO.write(renderedImage, "png", file);
                    clearCanvas();
                    answerField.setText("");
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
        button.setStyle("-fx-font-family: sylfaen");

        hBox.getChildren().addAll(directoriesComboBox, button);
        root.setTop(hBox);
    }

    private List<String> getInnerDirectories() {
        List<String> directories = new ArrayList<>();
        File folder = new File(systemParameterService.getParameterValue(createdImagesDirectoryParameter));
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                directories.add(file.getName());
            }
        }
        return directories;
    }

    private void initMainPane() {
        canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        AnchorPane pane = new AnchorPane();
        pane.setPrefWidth(CANVAS_WIDTH + 10);
        pane.setMaxWidth(CANVAS_WIDTH + 10);
        pane.setMaxHeight(CANVAS_HEIGHT + 10);
        pane.setPrefHeight(CANVAS_HEIGHT + 10);
        pane.setStyle("-fx-border-color: black; -fx-border-style: solid; -fx-border-width: 6px; -fx-background-color: white");
        GraphicsContext gc = canvas.getGraphicsContext2D();
        canvas.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                for (int i = -4; i <= 4; i++) {
                    for (int j = -4; j <= 4; j++) {
                        gc.getPixelWriter().setColor((int)event.getX() + i, (int)event.getY() + j, Color.BLACK);
                    }
                }
            }
        });
        pane.getChildren().addAll(canvas);
        pane.setTopAnchor(canvas, 3.0);
        pane.setLeftAnchor(canvas, 3.0);

        Button button = new Button(Messages.get("clear"));
        button.setStyle("-fx-font-family: sylfaen");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                clearCanvas();
            }
        });

        HBox hBox = new HBox();
        hBox.setSpacing(20);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(pane, button);

        root.setCenter(hBox);
    }

    private void initBottomPane() {
        FlowPane flowPane = new FlowPane();
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setPadding(new Insets(0, 0, 15, 0));
        flowPane.setHgap(10);
        flowPane.setVgap(10);
        Label answerLabel = new Label(Messages.get("answer") + ":");
        answerLabel.setStyle("-fx-font-family: sylfaen");
        answerField = new TextField();
        answerField.setStyle("-fx-font-family: sylfaen");
        answerField.setPrefWidth(130);
        Label generationLabel = new Label(Messages.get("generation") + ":");
        generationLabel.setStyle("-fx-font-family: sylfaen");
        generationTextField = new TextField();
        generationTextField.setStyle("-fx-font-family: sylfaen");
        generationTextField.setPrefWidth(130);
        Label heightLabel = new Label(Messages.get("height") + ":");
        heightLabel.setStyle("-fx-font-family: sylfaen");
        heightField = new TextField();
        heightField.setText(systemParameterService.getParameterValue(normalizedDataDefaultHeightParameter));
        heightField.setPrefWidth(80);
        Label widthLabel = new Label(Messages.get("width") + ":");
        widthLabel.setStyle("-fx-font-family: sylfaen");
        widthField = new TextField();
        widthField.setText(systemParameterService.getParameterValue(normalizedDataDefaultWidthParameter));
        widthField.setPrefWidth(80);
        Button saveFromDirectoryButton = new Button(Messages.get("saveFromDirectory"));
        saveFromDirectoryButton.setStyle("-fx-font-family: sylfaen");
        saveFromDirectoryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (generationTextField.getText() == null || generationTextField.getText().isEmpty()) {
                    ShowAlert.showWarning(Messages.get("pleaseFillAllField"));
                    return;
                }
                for (NormalizedData normalizedData : getNormalizedDataFromDirectory(systemParameterService.getParameterValue(createdImagesDirectoryParameter) + "\\" + directoriesComboBox.getValue())) {
                    normalizedDataService.addNormalizedData(normalizedData);
                }
            }
        });
        Button saveButton = new Button(Messages.get("save"));
        saveButton.setStyle("-fx-font-family: sylfaen");
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (answerField.getText() == null || answerField.getText().length() != 1) {
                    ShowAlert.showWarning(Messages.get("irrelevantAnswer"));
                    return;
                }
                if (generationTextField.getText() == null || generationTextField.getText().isEmpty()) {
                    ShowAlert.showWarning(Messages.get("pleaseFillAllField"));
                    return;
                }
                normalizedDataService.addNormalizedData(getNormalizedDataFromBoard());
                answerField.setText("");
                clearCanvas();
            }
        });
        guessButton = new Button(Messages.get("guess"));
        guessButton.setStyle("-fx-font-family: sylfaen");
        networkComboBox = new ComboBox();
        networkComboBox.setPrefWidth(170);
        networkComboBox.setStyle("-fx-font-family: sylfaen");
        reloadNetworkComboBox();
        guessButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                NeuralNetworkService neuralNetworkService = new NeuralNetworkServiceImpl(NeuralNetworkManagerType.MY_NEURAL_NETWORK);
                char ans = neuralNetworkService.guessCharacter(getNormalizedDataFromBoard(), systemParameterService.getParameterValue(neuralNetworkDirectoryParameter) + "\\" + networkComboBox.getValue());
                ShowAlert.showSimpleAlert("" + ans);
            }
        });
        flowPane.getChildren().addAll(answerLabel, answerField, generationLabel, generationTextField);
        flowPane.getChildren().addAll(heightLabel, heightField, widthLabel, widthField);
        flowPane.getChildren().addAll(saveFromDirectoryButton, saveButton, networkComboBox, guessButton);
        root.setBottom(flowPane);
    }

    private void clearCanvas() {
        canvas.getGraphicsContext2D().clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
    }

    private NormalizedData getNormalizedDataFromBoard() {
        int width = systemParameterService.getIntegerParameterValue(normalizedDataDefaultWidthParameter);
        int height = systemParameterService.getIntegerParameterValue(normalizedDataDefaultHeightParameter);
        try {
            width = Integer.parseInt(widthField.getText());
            height = Integer.parseInt(heightField.getText());
        } catch (Exception ex) {
            ShowAlert.showWarning(Messages.get("pleaseFillAllField"));
        }
        WritableImage writableImage = new WritableImage(CANVAS_WIDTH, CANVAS_HEIGHT);
        canvas.snapshot(null, writableImage);
        BufferedImage image = SwingFXUtils.fromFXImage(writableImage, null);
        char ans = (answerField.getText() == null | answerField.getText().isEmpty()) ? ' ' : answerField.getText().charAt(0);
        char firstSymbolInCharSequence = systemParameterService.getParameterValue(firstSymbolInCharSequenceParameter).charAt(0);
        char lastSymbolInCharSequence = systemParameterService.getParameterValue(lastSymbolInCharSequenceParameter).charAt(0);
        CharSequence charSequence = new CharSequence(firstSymbolInCharSequence, lastSymbolInCharSequence);
        NormalizedData normalizedData = new NormalizedData(width, height, imageProcessingService.getFloatArrayFromImage(
                imageProcessingService.cutCornerUnusedParts(image, CANVAS_BACKGROUND_COLOR), width, height, CANVAS_BACKGROUND_COLOR),
                ans, charSequence, generationTextField.getText());
        return normalizedData;
    }

    private List<NormalizedData> getNormalizedDataFromDirectory(String path) {
        List<NormalizedData> normalizedDataList = new ArrayList<>();
        int width = systemParameterService.getIntegerParameterValue(normalizedDataDefaultWidthParameter);
        int height = systemParameterService.getIntegerParameterValue(normalizedDataDefaultHeightParameter);
        try {
            width = Integer.parseInt(widthField.getText());
            height = Integer.parseInt(heightField.getText());
        } catch (Exception ex) {
            ShowAlert.showWarning(Messages.get("pleaseFillAllField"));
            return new ArrayList<>();
        }
        File folder = new File(path);
        char firstSymbolInCharSequence = systemParameterService.getParameterValue(firstSymbolInCharSequenceParameter).charAt(0);
        char lastSymbolInCharSequence = systemParameterService.getParameterValue(lastSymbolInCharSequenceParameter).charAt(0);
        CharSequence charSequence = new CharSequence(firstSymbolInCharSequence, lastSymbolInCharSequence);
        for (File file : folder.listFiles()) {
            if (file.isFile()) {
                try {
                    BufferedImage image = ImageIO.read(file);
                    NormalizedData normalizedData = new NormalizedData(width, height, imageProcessingService.getFloatArrayFromImage(
                            imageProcessingService.cutCornerUnusedParts(image, CANVAS_BACKGROUND_COLOR), width, height, CANVAS_BACKGROUND_COLOR),
                            file.getName().charAt(0), charSequence, generationTextField.getText());
                    normalizedDataList.add(normalizedData);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        return normalizedDataList;
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
            guessButton.setDisable(false);
        } else {
            guessButton.setDisable(true);
        }
    }
}

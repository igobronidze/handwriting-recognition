package ge.tsu.handwriting_recognition.data_creator.console.ui;

import ge.tsu.handwriting_recognition.data_creator.console.resources.Messages;
import ge.tsu.handwriting_recognition.data_creator.console.utils.ShowAlert;
import ge.tsu.handwriting_recognition.data_creator.service.SystemParameterService;
import ge.tsu.handwriting_recognition.data_creator.service.SystemParameterServiceImpl;
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
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataCreatorWithDraw extends Stage {

    private SystemParameterService systemParameterService = new SystemParameterServiceImpl();

    private String createdImagesDirectory = systemParameterService.getSystemParameterValue("createdImagesDirectory",
            "D:\\sg\\handwriting_recognition\\images\\created_images");

    private final int CANVAS_WIDTH = 400;

    private final int CANVAS_HEIGHT = 350;

    private BorderPane root;

    private Canvas canvas;

    private TextField answerField;

    public DataCreatorWithDraw() {
        this.setTitle(Messages.get("dataCreatorWithDraw"));
        root = new BorderPane();
        initTopPane();
        initMainPane();
        initBottomPane();
        this.setScene(new Scene(root));
        this.setWidth(750);
        this.setHeight(550);
    }

    private void initTopPane() {
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(15, 0, 0, 0));

        ComboBox comboBox = new ComboBox();
        comboBox.setStyle("-fx-font-family: sylfaen");
        comboBox.setItems(FXCollections.observableArrayList(getInnerDirectories()));
        if (comboBox.getItems().size() != 0) {
            comboBox.setValue(comboBox.getItems().get(0));
        }
        comboBox.setPrefWidth(150);
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
                    File file = new File(createdImagesDirectory + "\\" + comboBox.getValue() + "\\" + answerField.getText() + "_" + canvas.hashCode() + ".png");
                    ImageIO.write(renderedImage, "png", file);
                    answerField.setText("");
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
        button.setStyle("-fx-font-family: sylfaen");

        hBox.getChildren().addAll(comboBox, button);
        root.setTop(hBox);
    }

    private List<String> getInnerDirectories() {
        List<String> directories = new ArrayList<>();
        File folder = new File(createdImagesDirectory);
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
        HBox bottomPane = new HBox();
        bottomPane.setAlignment(Pos.CENTER);
        bottomPane.setPadding(new Insets(0, 0, 15, 0));
        bottomPane.setSpacing(10);
        Label answerLabel = new Label(Messages.get("answer") + ":");
        answerLabel.setStyle("-fx-font-family: sylfaen");
        answerField = new TextField();
        answerField.setStyle("-fx-font-family: sylfaen");
        Label generationLabel = new Label(Messages.get("generation") + ":");
        generationLabel.setStyle("-fx-font-family: sylfaen");
        TextField generationTextField = new TextField();
        generationTextField.setStyle("-fx-font-family: sylfaen");
        generationTextField.setPrefWidth(130);
        Button saveButton = new Button(Messages.get("save"));
        saveButton.setStyle("-fx-font-family: sylfaen");
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        Button guessButton = new Button(Messages.get("guess"));
        guessButton.setStyle("-fx-font-family: sylfaen");
        guessButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        Button trainButton = new Button(Messages.get("train"));
        trainButton.setStyle("-fx-font-family: sylfaen");
        trainButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        bottomPane.getChildren().addAll(answerLabel, answerField);
        bottomPane.getChildren().addAll(generationLabel, generationTextField, saveButton, trainButton, guessButton);
        root.setBottom(bottomPane);
    }

    private void clearCanvas() {
        canvas.getGraphicsContext2D().clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
    }
}

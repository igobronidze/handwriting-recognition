package ge.tsu.handwriting_recognition.data_creator.console.ui;

import ge.tsu.handwriting_recognition.data_creator.console.resources.Messages;
import ge.tsu.handwriting_recognition.data_creator.console.utils.StageUtils;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ControlPanel extends Application {

    private VBox root;

    private Label titleLabel;

    private FlowPane flowPane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(Messages.get("controlPanel"));
        initUI();
        primaryStage.setScene(new Scene(root));
        StageUtils.setMaxSize(primaryStage);
        primaryStage.show();
    }

    private void initUI() {
        root = new VBox();
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(30);
        root.setPadding(new Insets(20, 20, 20, 20));

        titleLabel = new Label(Messages.get("projectControlPanel"));
        titleLabel.setStyle("-fx-font-family: sylfaen; -fx-font-size: 25; -fx-text-fill: blue");
        root.getChildren().add(titleLabel);

        flowPane = new FlowPane();
        flowPane.setHgap(10);
        flowPane.setVgap(10);
        addButton(new DataCreatorWithGrid(), Messages.get("dataCreatorWithGrid"));
        addButton(new SystemParameterWindow(), Messages.get("systemParameter"));
        root.getChildren().addAll(flowPane);
    }

    private void addButton(Stage stage, String text) {
        Button button = new Button(text);
        button.setStyle("-fx-font-family: sylfaen; -fx-font-size: 20; -fx-text-fill: brown");
        button.setPrefWidth(350);
        button.setPrefHeight(60);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.show();
            }
        });
        flowPane.getChildren().addAll(button);
    }
}

package ge.tsu.handwriting_recognition.data_creator.console.ui;import ge.tsu.handwriting_recognition.data_creator.console.resources.Messages;import ge.tsu.handwriting_recognition.data_creator.console.utils.ShowAlert;import ge.tsu.handwriting_recognition.data_creator.console.utils.StageUtils;import ge.tsu.handwriting_recognition.data_creator.model.CharSequence;import ge.tsu.handwriting_recognition.data_creator.model.NormalizedData;import ge.tsu.handwriting_recognition.data_creator.neuralnetwork.INeuralNetwork;import ge.tsu.handwriting_recognition.data_creator.neuralnetwork.MyNeuralNetwork;import ge.tsu.handwriting_recognition.data_creator.neuralnetwork.NeuralNetworkWithNeuroph;import ge.tsu.handwriting_recognition.data_creator.service.NormalizedDataService;import ge.tsu.handwriting_recognition.data_creator.service.NormalizedDataServiceImpl;import ge.tsu.handwriting_recognition.data_creator.service.SystemParameterService;import ge.tsu.handwriting_recognition.data_creator.service.SystemParameterServiceImpl;import javafx.event.ActionEvent;import javafx.event.EventHandler;import javafx.geometry.Insets;import javafx.geometry.Pos;import javafx.scene.Scene;import javafx.scene.control.Button;import javafx.scene.control.Label;import javafx.scene.control.TextField;import javafx.scene.layout.BorderPane;import javafx.scene.layout.GridPane;import javafx.scene.layout.HBox;import javafx.stage.Stage;public class DataCreatorWithGrid extends Stage {    private SystemParameterService systemParameterService = new SystemParameterServiceImpl();    private NormalizedDataService normalizedDataService = new NormalizedDataServiceImpl();    private int testDataCreatorGridDefaultWidth = Integer.parseInt(systemParameterService.getSystemParameterValue(            "testDataCreatorGridDefaultWidth", "11"));    private int testDataCreatorGridDefaultHeight = Integer.parseInt(systemParameterService.getSystemParameterValue(            "testDataCreatorGridDefaultHeight", "11"));    private int testDataCreatorGridMaxWidth = Integer.parseInt(systemParameterService.getSystemParameterValue(            "testDataCreatorGridMaxWidth", "100"));    private int testDataCreatorGridMaxHeight = Integer.parseInt(systemParameterService.getSystemParameterValue(            "testDataCreatorGridMaxHeight", "100"));    private char firstSymbolInCharSequence = systemParameterService.getSystemParameterValue("firstSymbolInCharSequence", "ა").charAt(0);    private char lastSymbolInCharSequence = systemParameterService.getSystemParameterValue("lastSymbolInCharSequence", "ა").charAt(0);    private INeuralNetwork neuralNetwork = new MyNeuralNetwork();    private CharSequence charSequence = new CharSequence(firstSymbolInCharSequence, lastSymbolInCharSequence);    private BorderPane root;    private TextField widthField;    private TextField heightField;    private TextField generationTextField;    private TextField answerField;    private boolean[][] coloredMatrix = new boolean[testDataCreatorGridMaxWidth][testDataCreatorGridMaxHeight];    public DataCreatorWithGrid() {        this.setTitle(Messages.get("dataCreatorWithGrid"));        root = new BorderPane();        initTopPane();        initBottomPane();        reloadCenterPane(testDataCreatorGridDefaultWidth, testDataCreatorGridDefaultHeight);        this.setScene(new Scene(root));        StageUtils.setMaxSize(this);    }    private void reloadCenterPane(int width, int height) {        GridPane centerPane = new GridPane();        centerPane.setAlignment(Pos.CENTER);        int btnWidth = Integer.parseInt(systemParameterService.getSystemParameterValue("testDataCreatorWindowWidth", "900")) / width;        int btnHeight = (Integer.parseInt(systemParameterService.getSystemParameterValue("testDataCreatorWindowHeight", "650"))  - 90) / height;        int min = Math.min(btnWidth, btnHeight);        for (int i = 0; i < height; i++) {            for (int j = 0; j < width; j++) {                Button btn = new Button();                btn.setPrefHeight(min);                btn.setPrefWidth(min);                final int ii = i;                final int jj = j;                btn.setOnAction(new EventHandler<ActionEvent>() {                    @Override                    public void handle(ActionEvent event) {                        String color = coloredMatrix[ii][jj] ? "#e6ffff" : "#001a1a";                        btn.setStyle( "-fx-color: " + color + ";");                        coloredMatrix[ii][jj] = !coloredMatrix[ii][jj];                    }                });                btn.setStyle( "-fx-color: #e6ffff;");                coloredMatrix[i][j] = false;                centerPane.add(btn, j, i);            }        }        root.setCenter(centerPane);    }    private void initBottomPane() {        HBox bottomPane = new HBox();        bottomPane.setAlignment(Pos.CENTER);        bottomPane.setPadding(new Insets(0, 0, 10, 0));        bottomPane.setSpacing(10);        Label answerLabel = new Label(Messages.get("answer") + ":");        answerLabel.setStyle("-fx-font-family: sylfaen");        answerField = new TextField();        answerField.setStyle("-fx-font-family: sylfaen");        Label generationLabel = new Label(Messages.get("generation") + ":");        generationLabel.setStyle("-fx-font-family: sylfaen");        generationTextField = new TextField();        generationTextField.setStyle("-fx-font-family: sylfaen");        generationTextField.setPrefWidth(130);        Button saveButton = new Button(Messages.get("save"));        saveButton.setStyle("-fx-font-family: sylfaen");        saveButton.setOnAction(new EventHandler<ActionEvent>() {            @Override            public void handle(ActionEvent event) {                saveAction();            }        });        Button guessButton = new Button(Messages.get("guess"));        guessButton.setStyle("-fx-font-family: sylfaen");        guessButton.setOnAction(new EventHandler<ActionEvent>() {            @Override            public void handle(ActionEvent event) {                char ans = neuralNetwork.guessCharacter(new NormalizedData(getHeightFromField(), getWidthFromField(), getDataFromGrid()));                ShowAlert.showSimpleAlert("" + ans);            }        });        Button trainButton = new Button(Messages.get("train"));        trainButton.setStyle("-fx-font-family: sylfaen");        trainButton.setOnAction(new EventHandler<ActionEvent>() {            @Override            public void handle(ActionEvent event) {                if (generationTextField.getText() == null || generationTextField.getText().isEmpty()) {                    ShowAlert.showWarning(Messages.get("pleaseFillAllField"));                    return;                }                neuralNetwork.trainNeural(getWidthFromField(), getHeightFromField(), generationTextField.getText());            }        });        bottomPane.getChildren().addAll(answerLabel, answerField);        bottomPane.getChildren().addAll(generationLabel, generationTextField, saveButton, trainButton, guessButton);        root.setBottom(bottomPane);    }    private void saveAction() {        if (answerField.getText() == null || answerField.getText().length() != 1) {            ShowAlert.showWarning(Messages.get("irrelevantAnswer"));            return;        }        if (generationTextField.getText() == null || generationTextField.getText().isEmpty()) {            ShowAlert.showWarning(Messages.get("pleaseFillAllField"));            return;        }        NormalizedData normalizedData = new NormalizedData(getWidthFromField(), getHeightFromField(), getDataFromGrid(), answerField.getText().charAt(0), charSequence, generationTextField.getText());        normalizedDataService.addNormalizedData(normalizedData);        answerField.setText("");        reloadCenterPane(getWidthFromField(), getHeightFromField());    }    private void initTopPane() {        HBox topPane = new HBox();        topPane.setAlignment(Pos.CENTER);        topPane.setSpacing(10);        topPane.setPadding(new Insets(10, 0, 0, 0));        Label heightLabel = new Label(Messages.get("height") + ":");        heightLabel.setStyle("-fx-font-family: sylfaen");        heightField = new TextField();        heightField.setText(Integer.toString(testDataCreatorGridDefaultHeight));        topPane.getChildren().addAll(heightLabel, heightField);        Label widthLabel = new Label(Messages.get("width") + ":");        widthLabel.setStyle("-fx-font-family: sylfaen");        widthField = new TextField();        widthField.setText(Integer.toString(testDataCreatorGridDefaultWidth));        widthField.setOnAction(new EventHandler<ActionEvent>() {            @Override            public void handle(ActionEvent event) {                reloadCenterPane(getWidthFromField(), getHeightFromField());            }        });        topPane.getChildren().addAll(widthLabel, widthField);        Button chooseButton = new Button(Messages.get("create"));        chooseButton.setStyle("-fx-font-family: sylfaen");        chooseButton.setOnAction(new EventHandler<ActionEvent>() {            @Override            public void handle(ActionEvent event) {                reloadCenterPane(getWidthFromField(), getHeightFromField());            }        });        topPane.getChildren().add(chooseButton);        root.setTop(topPane);    }    private int getWidthFromField() {        try {            int width = Integer.parseInt(widthField.getText());            if (width <=0 || width > testDataCreatorGridMaxWidth) {                widthField.setText(testDataCreatorGridDefaultWidth + "");                return testDataCreatorGridDefaultWidth;            }            return width;        } catch (NumberFormatException ex) {            widthField.setText(testDataCreatorGridDefaultWidth + "");            return testDataCreatorGridDefaultWidth;        }    }    private int getHeightFromField() {        try {            int height = Integer.parseInt(heightField.getText());            if (height <=0 || height > testDataCreatorGridMaxHeight) {                heightField.setText(testDataCreatorGridDefaultHeight + "");                return testDataCreatorGridDefaultHeight;            }            return height;        } catch (NumberFormatException ex) {            heightField.setText(testDataCreatorGridDefaultHeight + "");            return testDataCreatorGridDefaultHeight;        }    }    private Float[] getDataFromGrid() {        int width = getWidthFromField();        int height = getHeightFromField();        Float[] data = new Float[width * height];        for (int i = 0; i < height; i++) {            for (int j = 0; j < width; j++) {                data[i * width + j] = (coloredMatrix[i][j] ? 1.0f : 0.0f);            }        }        return data;    }}
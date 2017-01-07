package ge.tsu.handwriting_recognition.data_creator.console.ui.propeties;

import ge.tsu.handwriting_recognition.data_creator.model.NetworkInfo;
import ge.tsu.handwriting_recognition.data_creator.model.TestingInfo;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.List;

public class NetworkInfoProperty {

    private SimpleIntegerProperty id;

    private SimpleIntegerProperty width;

    private SimpleIntegerProperty height;

    private SimpleStringProperty generation;

    private SimpleIntegerProperty numberOfData;

    private SimpleLongProperty trainingDuration;

    private SimpleFloatProperty weightMinValue;

    private SimpleFloatProperty weightMaxValue;

    private SimpleFloatProperty biasMinValue;

    private SimpleFloatProperty biasMaxValue;

    private SimpleStringProperty transferFunctionType;

    private SimpleFloatProperty learningRate;

    private SimpleFloatProperty minError;

    private SimpleLongProperty trainingMaxIteration;

    private SimpleLongProperty numberOfTrainingDataInOneIteration;

    private SimpleFloatProperty bestError;

    private SimpleStringProperty charSequence;

    private SimpleStringProperty hiddenLayer;

    public NetworkInfoProperty(NetworkInfo networkInfo) {
        this.id = new SimpleIntegerProperty(networkInfo.getId());
        this.width = new SimpleIntegerProperty(networkInfo.getWidth());
        this.height = new SimpleIntegerProperty(networkInfo.getHeight());
        this.generation = new SimpleStringProperty(networkInfo.getGeneration());
        this.numberOfData = new SimpleIntegerProperty(networkInfo.getNumberOfData());
        this.trainingDuration = new SimpleLongProperty(networkInfo.getTrainingDuration());
        this.weightMinValue = new SimpleFloatProperty(networkInfo.getWeightMinValue());
        this.weightMaxValue = new SimpleFloatProperty(networkInfo.getWeightMaxValue());
        this.biasMinValue = new SimpleFloatProperty(networkInfo.getBiasMinValue());
        this.biasMaxValue = new SimpleFloatProperty(networkInfo.getBiasMaxValue());
        this.transferFunctionType = new SimpleStringProperty(networkInfo.getTransferFunctionType().name());
        this.learningRate = new SimpleFloatProperty(networkInfo.getLearningRate());
        this.minError = new SimpleFloatProperty(networkInfo.getMinError());
        this.trainingMaxIteration = new SimpleLongProperty(networkInfo.getTrainingMaxIteration());
        this.numberOfTrainingDataInOneIteration = new SimpleLongProperty(networkInfo.getNumberOfTrainingDataInOneIteration());
        List<TestingInfo> testingInfoList = networkInfo.getTestingInfoList();
        if (testingInfoList == null || testingInfoList.isEmpty()) {
            bestError = new SimpleFloatProperty(-1f);
        } else {
            float min = testingInfoList.get(0).getError();
            for (TestingInfo testingInfo : testingInfoList) {
                if (testingInfo.getError() < min) {
                    min = testingInfo.getError();
                }
            }
            bestError = new SimpleFloatProperty(min);
        }
        this.charSequence = new SimpleStringProperty(networkInfo.getCharSequence());
        this.hiddenLayer = new SimpleStringProperty(networkInfo.getHiddenLayer());
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getWidth() {
        return width.get();
    }

    public SimpleIntegerProperty widthProperty() {
        return width;
    }

    public void setWidth(int width) {
        this.width.set(width);
    }

    public int getHeight() {
        return height.get();
    }

    public SimpleIntegerProperty heightProperty() {
        return height;
    }

    public void setHeight(int height) {
        this.height.set(height);
    }

    public String getGeneration() {
        return generation.get();
    }

    public SimpleStringProperty generationProperty() {
        return generation;
    }

    public void setGeneration(String generation) {
        this.generation.set(generation);
    }

    public int getNumberOfData() {
        return numberOfData.get();
    }

    public SimpleIntegerProperty numberOfDataProperty() {
        return numberOfData;
    }

    public void setNumberOfData(int numberOfData) {
        this.numberOfData.set(numberOfData);
    }

    public long getTrainingDuration() {
        return trainingDuration.get();
    }

    public SimpleLongProperty trainingDurationProperty() {
        return trainingDuration;
    }

    public void setTrainingDuration(long trainingDuration) {
        this.trainingDuration.set(trainingDuration);
    }

    public float getWeightMinValue() {
        return weightMinValue.get();
    }

    public SimpleFloatProperty weightMinValueProperty() {
        return weightMinValue;
    }

    public void setWeightMinValue(float weightMinValue) {
        this.weightMinValue.set(weightMinValue);
    }

    public float getWeightMaxValue() {
        return weightMaxValue.get();
    }

    public SimpleFloatProperty weightMaxValueProperty() {
        return weightMaxValue;
    }

    public void setWeightMaxValue(float weightMaxValue) {
        this.weightMaxValue.set(weightMaxValue);
    }

    public float getBiasMinValue() {
        return biasMinValue.get();
    }

    public SimpleFloatProperty biasMinValueProperty() {
        return biasMinValue;
    }

    public void setBiasMinValue(float biasMinValue) {
        this.biasMinValue.set(biasMinValue);
    }

    public float getBiasMaxValue() {
        return biasMaxValue.get();
    }

    public SimpleFloatProperty biasMaxValueProperty() {
        return biasMaxValue;
    }

    public void setBiasMaxValue(float biasMaxValue) {
        this.biasMaxValue.set(biasMaxValue);
    }

    public String getTransferFunctionType() {
        return transferFunctionType.get();
    }

    public SimpleStringProperty transferFunctionTypeProperty() {
        return transferFunctionType;
    }

    public void setTransferFunctionType(String transferFunctionType) {
        this.transferFunctionType.set(transferFunctionType);
    }

    public float getLearningRate() {
        return learningRate.get();
    }

    public SimpleFloatProperty learningRateProperty() {
        return learningRate;
    }

    public void setLearningRate(float learningRate) {
        this.learningRate.set(learningRate);
    }

    public float getMinError() {
        return minError.get();
    }

    public SimpleFloatProperty minErrorProperty() {
        return minError;
    }

    public void setMinError(float minError) {
        this.minError.set(minError);
    }

    public long getTrainingMaxIteration() {
        return trainingMaxIteration.get();
    }

    public SimpleLongProperty trainingMaxIterationProperty() {
        return trainingMaxIteration;
    }

    public void setTrainingMaxIteration(long trainingMaxIteration) {
        this.trainingMaxIteration.set(trainingMaxIteration);
    }

    public long getNumberOfTrainingDataInOneIteration() {
        return numberOfTrainingDataInOneIteration.get();
    }

    public SimpleLongProperty numberOfTrainingDataInOneIterationProperty() {
        return numberOfTrainingDataInOneIteration;
    }

    public void setNumberOfTrainingDataInOneIteration(long numberOfTrainingDataInOneIteration) {
        this.numberOfTrainingDataInOneIteration.set(numberOfTrainingDataInOneIteration);
    }

    public float getBestError() {
        return bestError.get();
    }

    public SimpleFloatProperty bestErrorProperty() {
        return bestError;
    }

    public void setBestError(float bestError) {
        this.bestError.set(bestError);
    }
}

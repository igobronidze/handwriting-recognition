package ge.edu.tsu.handwriting_recognition.control_panel.console.ui.propeties;

import ge.edu.tsu.handwriting_recognition.control_panel.console.utils.DateUtils;
import ge.edu.tsu.handwriting_recognition.control_panel.model.info.NetworkInfo;
import ge.edu.tsu.handwriting_recognition.control_panel.model.info.TestingInfo;
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

    private SimpleStringProperty trainingDuration;

    private SimpleFloatProperty weightMinValue;

    private SimpleFloatProperty weightMaxValue;

    private SimpleFloatProperty biasMinValue;

    private SimpleFloatProperty biasMaxValue;

    private SimpleStringProperty transferFunctionType;

    private SimpleFloatProperty learningRate;

    private SimpleFloatProperty minError;

    private SimpleLongProperty trainingMaxIteration;

    private SimpleLongProperty numberOfTrainingDataInOneIteration;

    private SimpleStringProperty charSequence;

    private SimpleStringProperty hiddenLayer;

    private List<TestingInfo> testingInfoList;

    private SimpleFloatProperty bestSquaredError;

    private SimpleFloatProperty bestPercentageOfCorrects;

    private SimpleFloatProperty bestDiffBetweenAnsAndBest;

    private SimpleFloatProperty bestNormalizedGeneralError;

    public NetworkInfoProperty(NetworkInfo networkInfo) {
        this.id = new SimpleIntegerProperty(networkInfo.getId());
        this.width = new SimpleIntegerProperty(networkInfo.getWidth());
        this.height = new SimpleIntegerProperty(networkInfo.getHeight());
        this.generation = new SimpleStringProperty(networkInfo.getGeneration());
        this.numberOfData = new SimpleIntegerProperty(networkInfo.getNumberOfData());
        this.trainingDuration = new SimpleStringProperty(DateUtils.getFullDateFromSecond(networkInfo.getTrainingDuration() / 100));
        this.weightMinValue = new SimpleFloatProperty(networkInfo.getWeightMinValue());
        this.weightMaxValue = new SimpleFloatProperty(networkInfo.getWeightMaxValue());
        this.biasMinValue = new SimpleFloatProperty(networkInfo.getBiasMinValue());
        this.biasMaxValue = new SimpleFloatProperty(networkInfo.getBiasMaxValue());
        this.transferFunctionType = new SimpleStringProperty(networkInfo.getTransferFunction().name());
        this.learningRate = new SimpleFloatProperty(networkInfo.getLearningRate());
        this.minError = new SimpleFloatProperty(networkInfo.getMinError());
        this.trainingMaxIteration = new SimpleLongProperty(networkInfo.getTrainingMaxIteration());
        this.numberOfTrainingDataInOneIteration = new SimpleLongProperty(networkInfo.getNumberOfTrainingDataInOneIteration());
        this.charSequence = new SimpleStringProperty(networkInfo.getCharSequence());
        this.hiddenLayer = new SimpleStringProperty(networkInfo.getHiddenLayer());
        this.testingInfoList = networkInfo.getTestingInfoList();
        List<TestingInfo> testingInfoList = networkInfo.getTestingInfoList();
        if (testingInfoList == null || testingInfoList.isEmpty()) {
            bestSquaredError = new SimpleFloatProperty(-1);
            bestPercentageOfCorrects = new SimpleFloatProperty(-1);
            bestDiffBetweenAnsAndBest = new SimpleFloatProperty(-1);
            bestNormalizedGeneralError = new SimpleFloatProperty(-1);
        } else {
            TestingInfo testingInfo = testingInfoList.get(0);
            int size = testingInfo.getNumberOfTest();
            float minSquaredError = testingInfo.getSquaredError() / size;
            float maxPercentageOfCorrects = testingInfo.getPercentageOfCorrects();
            float maxDiffBetweenAnsAndBest = testingInfo.getDiffBetweenAnsAndBest() / size;
            float minNormalizedGeneralError = testingInfo.getNormalizedGeneralError();
            for (TestingInfo info : testingInfoList) {
                size = info.getNumberOfTest();
                minSquaredError = Math.min(minSquaredError, info.getSquaredError() / size);
                maxPercentageOfCorrects = Math.max(maxPercentageOfCorrects, info.getPercentageOfCorrects());
                maxDiffBetweenAnsAndBest = Math.max(maxDiffBetweenAnsAndBest, info.getDiffBetweenAnsAndBest() / size);
                minNormalizedGeneralError = Math.min(minNormalizedGeneralError, info.getNormalizedGeneralError());
            }
            bestSquaredError = new SimpleFloatProperty(minSquaredError);
            bestPercentageOfCorrects = new SimpleFloatProperty(maxPercentageOfCorrects);
            bestDiffBetweenAnsAndBest = new SimpleFloatProperty(maxDiffBetweenAnsAndBest);
            bestNormalizedGeneralError = new SimpleFloatProperty(minNormalizedGeneralError);
        }
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

    public String getTrainingDuration() {
        return trainingDuration.get();
    }

    public SimpleStringProperty trainingDurationProperty() {
        return trainingDuration;
    }

    public void setTrainingDuration(String trainingDuration) {
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

    public String getCharSequence() {
        return charSequence.get();
    }

    public SimpleStringProperty charSequenceProperty() {
        return charSequence;
    }

    public void setCharSequence(String charSequence) {
        this.charSequence.set(charSequence);
    }

    public String getHiddenLayer() {
        return hiddenLayer.get();
    }

    public SimpleStringProperty hiddenLayerProperty() {
        return hiddenLayer;
    }

    public void setHiddenLayer(String hiddenLayer) {
        this.hiddenLayer.set(hiddenLayer);
    }

    public List<TestingInfo> getTestingInfoList() {
        return testingInfoList;
    }

    public void setTestingInfoList(List<TestingInfo> testingInfoList) {
        this.testingInfoList = testingInfoList;
    }

    public float getBestSquaredError() {
        return bestSquaredError.get();
    }

    public SimpleFloatProperty bestSquaredErrorProperty() {
        return bestSquaredError;
    }

    public void setBestSquaredError(float bestSquaredError) {
        this.bestSquaredError.set(bestSquaredError);
    }

    public float getBestPercentageOfCorrects() {
        return bestPercentageOfCorrects.get();
    }

    public SimpleFloatProperty bestPercentageOfCorrectsProperty() {
        return bestPercentageOfCorrects;
    }

    public void setBestPercentageOfCorrects(float bestPercentageOfCorrects) {
        this.bestPercentageOfCorrects.set(bestPercentageOfCorrects);
    }

    public float getBestDiffBetweenAnsAndBest() {
        return bestDiffBetweenAnsAndBest.get();
    }

    public SimpleFloatProperty bestDiffBetweenAnsAndBestProperty() {
        return bestDiffBetweenAnsAndBest;
    }

    public void setBestDiffBetweenAnsAndBest(float bestDiffBetweenAnsAndBest) {
        this.bestDiffBetweenAnsAndBest.set(bestDiffBetweenAnsAndBest);
    }

    public float getBestNormalizedGeneralError() {
        return bestNormalizedGeneralError.get();
    }

    public SimpleFloatProperty bestNormalizedGeneralErrorProperty() {
        return bestNormalizedGeneralError;
    }

    public void setBestNormalizedGeneralError(float bestNormalizedGeneralError) {
        this.bestNormalizedGeneralError.set(bestNormalizedGeneralError);
    }
}

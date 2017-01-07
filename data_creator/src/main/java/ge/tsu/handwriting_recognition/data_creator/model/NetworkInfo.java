package ge.tsu.handwriting_recognition.data_creator.model;

import ge.tsu.handwriting_recognition.neural_network.transfer.TransferFunctionType;

import java.util.List;

public class NetworkInfo {

    private int id;

    private int width;

    private int height;

    private String generation;

    private int numberOfData;

    private long trainingDuration;

    private float weightMinValue;

    private float weightMaxValue;

    private float biasMinValue;

    private float biasMaxValue;

    private TransferFunctionType transferFunctionType;

    private float learningRate;

    private float minError;

    private long trainingMaxIteration;

    private long numberOfTrainingDataInOneIteration;

    private List<TestingInfo> testingInfoList;

    private String charSequence;

    private String hiddenLayer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getGeneration() {
        return generation;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
    }

    public int getNumberOfData() {
        return numberOfData;
    }

    public void setNumberOfData(int numberOfData) {
        this.numberOfData = numberOfData;
    }

    public long getTrainingDuration() {
        return trainingDuration;
    }

    public void setTrainingDuration(long trainingDuration) {
        this.trainingDuration = trainingDuration;
    }

    public float getWeightMinValue() {
        return weightMinValue;
    }

    public void setWeightMinValue(float weightMinValue) {
        this.weightMinValue = weightMinValue;
    }

    public float getWeightMaxValue() {
        return weightMaxValue;
    }

    public void setWeightMaxValue(float weightMaxValue) {
        this.weightMaxValue = weightMaxValue;
    }

    public float getBiasMinValue() {
        return biasMinValue;
    }

    public void setBiasMinValue(float biasMinValue) {
        this.biasMinValue = biasMinValue;
    }

    public float getBiasMaxValue() {
        return biasMaxValue;
    }

    public void setBiasMaxValue(float biasMaxValue) {
        this.biasMaxValue = biasMaxValue;
    }

    public TransferFunctionType getTransferFunctionType() {
        return transferFunctionType;
    }

    public void setTransferFunctionType(TransferFunctionType transferFunctionType) {
        this.transferFunctionType = transferFunctionType;
    }

    public float getLearningRate() {
        return learningRate;
    }

    public void setLearningRate(float learningRate) {
        this.learningRate = learningRate;
    }

    public float getMinError() {
        return minError;
    }

    public void setMinError(float minError) {
        this.minError = minError;
    }

    public long getTrainingMaxIteration() {
        return trainingMaxIteration;
    }

    public void setTrainingMaxIteration(long trainingMaxIteration) {
        this.trainingMaxIteration = trainingMaxIteration;
    }

    public long getNumberOfTrainingDataInOneIteration() {
        return numberOfTrainingDataInOneIteration;
    }

    public void setNumberOfTrainingDataInOneIteration(long numberOfTrainingDataInOneIteration) {
        this.numberOfTrainingDataInOneIteration = numberOfTrainingDataInOneIteration;
    }

    public List<TestingInfo> getTestingInfoList() {
        return testingInfoList;
    }

    public void setTestingInfoList(List<TestingInfo> testingInfoList) {
        this.testingInfoList = testingInfoList;
    }

    public String getCharSequence() {
        return charSequence;
    }

    public void setCharSequence(String charSequence) {
        this.charSequence = charSequence;
    }

    public String getHiddenLayer() {
        return hiddenLayer;
    }

    public void setHiddenLayer(String hiddenLayer) {
        this.hiddenLayer = hiddenLayer;
    }
}

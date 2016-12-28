package ge.tsu.handwritin_recognition.service;

import ge.tsu.handwritin_recognition.model.InputData;
import ge.tsu.handwritin_recognition.systemsetting.SystemParameter;
import org.neuroph.core.data.DataSetRow;

public class InputDataServiceImpl implements InputDataService {

    @Override
    public DataSetRow getDataSetRow(InputData inputData) {
        double[] input = new double[inputData.getHeight() * inputData.getWidth()];
        for (int i = 0; i < inputData.getHeight(); i++) {
            for (int j = 0; j < inputData.getWidth(); j++) {
                input[i * inputData.getHeight() + j] = inputData.getGrid()[i][j] ? 1 : 0;
            }
        }
        double[] ans = new double[SystemParameter.charsSet.getNumberOfChars()];
        ans[inputData.getLetter() - SystemParameter.charsSet.getFirstCharASCI()] = 1;
        return new DataSetRow(input, ans);
    }
}

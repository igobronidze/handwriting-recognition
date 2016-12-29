package ge.tsu.handwritin_recognition.service;

import ge.tsu.handwritin_recognition.model.InputData;
import ge.tsu.handwritin_recognition.systemsetting.SystemParameter;
import org.neuroph.core.data.DataSetRow;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

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
        if (inputData.getLetter() != null) {
            ans[inputData.getLetter() - SystemParameter.charsSet.getFirstCharASCI()] = 1;
        }
        return new DataSetRow(input, ans);
    }

    @Override
    public List<InputData> getInputDatas(String folderName) {
        List<InputData> inputDataList = new ArrayList<>();
        String path = SystemParameter.testDataPath + "/" + folderName;
        File folder = new File(path);
        for (File file : folder.listFiles()) {
            if (!file.isDirectory()) {
                try {
                    ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
                    InputData inputData = (InputData)in.readObject();
                    inputDataList.add(inputData);
                } catch (IOException | ClassNotFoundException ex) {
                    System.out.println("Something wrong on file " + file.getAbsolutePath());
                }
            }
        }
        return inputDataList;
    }
}

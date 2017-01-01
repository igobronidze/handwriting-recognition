package ge.tsu.handwriting_recognition.service;

import ge.tsu.handwriting_recognition.model.NormalizedData;
import ge.tsu.handwriting_recognition.systemsetting.SystemParameter;
import org.neuroph.core.data.DataSetRow;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class NormalizedDataServiceImpl implements NormalizedDataService {

    @Override
    public DataSetRow getDataSetRow(NormalizedData normalizedData) {
        double[] input = new double[normalizedData.getHeight() * normalizedData.getWidth()];
        for (int i = 0; i < normalizedData.getHeight(); i++) {
            for (int j = 0; j < normalizedData.getWidth(); j++) {
                input[i * normalizedData.getHeight() + j] = normalizedData.getGrid()[i][j] ? 1 : 0;
            }
        }
        double[] ans = new double[SystemParameter.CHARACTERS_SET.getNumberOfChars()];
        if (normalizedData.getLetter() != null) {
            ans[normalizedData.getLetter() - SystemParameter.CHARACTERS_SET.getFirstCharASCI()] = 1;
        }
        return new DataSetRow(input, ans);
    }

    @Override
    public List<NormalizedData> getInputDatas(String folderName) {
        List<NormalizedData> normalizedDataList = new ArrayList<>();
        String path = SystemParameter.testDataPath + "/" + folderName;
        File folder = new File(path);
        for (File file : folder.listFiles()) {
            if (!file.isDirectory()) {
                try {
                    ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
                    NormalizedData normalizedData = (NormalizedData)in.readObject();
                    normalizedDataList.add(normalizedData);
                } catch (IOException | ClassNotFoundException ex) {
                    System.out.println("Something wrong on file " + file.getAbsolutePath());
                    System.out.println(ex.getMessage());
                }
            }
        }
        return normalizedDataList;
    }
}

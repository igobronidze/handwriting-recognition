package ge.tsu.handwriting_recognition.service;

import ge.tsu.handwriting_recognition.model.CharSequence;
import ge.tsu.handwriting_recognition.model.NormalizedData;
import ge.tsu.handwriting_recognition.service.dao.NormalizedDataDAO;
import ge.tsu.handwriting_recognition.service.dao.NormalizedDataDAOImpl;
import org.neuroph.core.data.DataSetRow;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class NormalizedDataServiceImpl implements NormalizedDataService {

    private SystemParameterService systemParameterService = new SystemParameterServiceImpl();

    private NormalizedDataDAO normalizedDataDAO = new NormalizedDataDAOImpl();

    private char firstSymbolInCharSequence = systemParameterService.getSystemParameterValue("firstSymbolInCharSequence", "ა").charAt(0);

    private char lastSymbolInCharSequence = systemParameterService.getSystemParameterValue("lastSymbolInCharSequence", "ჰ").charAt(0);

    private CharSequence charSequence = new CharSequence(firstSymbolInCharSequence, lastSymbolInCharSequence);

    @Override
    public DataSetRow getDataSetRow(NormalizedData normalizedData) {
        double[] input = new double[normalizedData.getHeight() * normalizedData.getWidth()];
        for (int i = 0; i < normalizedData.getHeight(); i++) {
            for (int j = 0; j < normalizedData.getWidth(); j++) {
                input[i * normalizedData.getHeight() + j] = normalizedData.getGrid()[i][j] ? 1 : 0;
            }
        }
        double[] ans = new double[charSequence.getNumberOfChars()];
        if (normalizedData.getLetter() != null) {
            ans[normalizedData.getLetter() - charSequence.getFirstCharASCI()] = 1;
        }
        return new DataSetRow(input, ans);
    }

    @Override
    public void addNormalizedData(NormalizedData normalizedData) {
        normalizedDataDAO.addNormalizedData(normalizedData);
    }

    @Override
    public List<NormalizedData> getNormalizedDatas(Integer widht, Integer height, CharSequence charSequence, String generation) {
        return normalizedDataDAO.getNormalizedDatas(widht, height, charSequence, generation);
    }
}

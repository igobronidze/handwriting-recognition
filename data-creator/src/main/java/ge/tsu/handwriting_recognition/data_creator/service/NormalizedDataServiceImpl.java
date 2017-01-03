package ge.tsu.handwriting_recognition.data_creator.service;

import ge.tsu.handwriting_recognition.data_creator.model.CharSequence;
import ge.tsu.handwriting_recognition.data_creator.model.NormalizedData;
import ge.tsu.handwriting_recognition.data_creator.service.dao.NormalizedDataDAO;
import ge.tsu.handwriting_recognition.data_creator.service.dao.NormalizedDataDAOImpl;
import org.neuroph.core.data.DataSetRow;

import java.util.List;

public class NormalizedDataServiceImpl implements NormalizedDataService {

    private SystemParameterService systemParameterService = new SystemParameterServiceImpl();

    private NormalizedDataDAO normalizedDataDAO = new NormalizedDataDAOImpl();

    private char firstSymbolInCharSequence = systemParameterService.getSystemParameterValue("firstSymbolInCharSequence", "ა").charAt(0);

    private char lastSymbolInCharSequence = systemParameterService.getSystemParameterValue("lastSymbolInCharSequence", "ჰ").charAt(0);

    private CharSequence charSequence = new CharSequence(firstSymbolInCharSequence, lastSymbolInCharSequence);

    @Override
    public DataSetRow getDataSetRow(NormalizedData normalizedData) {
        Float[] data = normalizedData.getData();
        double[] input = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            input[i] = data[i];
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

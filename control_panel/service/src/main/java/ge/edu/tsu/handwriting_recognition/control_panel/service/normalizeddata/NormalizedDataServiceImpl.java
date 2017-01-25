package ge.edu.tsu.handwriting_recognition.control_panel.service.normalizeddata;

import ge.edu.tsu.handwriting_recognition.control_panel.model.data.NormalizedData;
import ge.edu.tsu.handwriting_recognition.control_panel.model.info.CharSequence;
import ge.edu.tsu.handwriting_recognition.control_panel.server.dao.NormalizedDataDAO;
import ge.edu.tsu.handwriting_recognition.control_panel.server.dao.NormalizedDataDAOImpl;

import java.util.List;

public class NormalizedDataServiceImpl implements NormalizedDataService {

    private NormalizedDataDAO normalizedDataDAO = new NormalizedDataDAOImpl();

    @Override
    public void addNormalizedData(NormalizedData normalizedData) {
        normalizedDataDAO.addNormalizedData(normalizedData);
    }

    @Override
    public List<NormalizedData> getNormalizedDatas(Integer widht, Integer height, CharSequence charSequence, String generation) {
        return normalizedDataDAO.getNormalizedDatas(widht, height, charSequence, generation);
    }
}
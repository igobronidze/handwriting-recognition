package ge.tsu.handwriting_recognition;

import ge.tsu.handwriting_recognition.model.CharSequence;
import ge.tsu.handwriting_recognition.model.NormalizedData;
import ge.tsu.handwriting_recognition.service.dao.NormalizedDataDAO;
import ge.tsu.handwriting_recognition.service.dao.NormalizedDataDAOImpl;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class NormalizedDataTest {

    private NormalizedDataDAO normalizedDataDAO = new NormalizedDataDAOImpl();

    private NormalizedData normalizedData;

    @Before
    public void init() {
        normalizedData = new NormalizedData();
        normalizedData.setHeight(2);
        normalizedData.setWidth(4);
        normalizedData.setCharSequence(new CharSequence('A', 'Z'));
        normalizedData.setGrid(new boolean[][] {{true, true, false, true},{false, true, false, false}});
        normalizedData.setLetter('K');
        normalizedData.setTrainingSetGeneration("testGeneration");
    }

    @Test
    @Ignore
    public void testAddNormalizedData() {
        normalizedDataDAO.addNormalizedData(normalizedData);
        System.out.println(normalizedDataDAO.getNormalizedDatas(4, 2, new CharSequence('A', 'Z'), "testGeneration"));
    }

}

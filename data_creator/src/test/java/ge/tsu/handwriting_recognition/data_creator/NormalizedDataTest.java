package ge.tsu.handwriting_recognition.data_creator;

import ge.tsu.handwriting_recognition.data_creator.model.CharSequence;
import ge.tsu.handwriting_recognition.data_creator.model.NormalizedData;
import ge.tsu.handwriting_recognition.data_creator.neuralnetwork.INeuralNetwork;
import ge.tsu.handwriting_recognition.data_creator.neuralnetwork.MyNeuralNetwork;
import ge.tsu.handwriting_recognition.data_creator.service.dao.NormalizedDataDAO;
import ge.tsu.handwriting_recognition.data_creator.service.dao.NormalizedDataDAOImpl;
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
        normalizedData.setData(new Float[] {1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f});
        normalizedData.setLetter('K');
        normalizedData.setTrainingSetGeneration("testGeneration");
    }

    @Test
    @Ignore
    public void testAddNormalizedData() {
//        normalizedDataDAO.addNormalizedData(normalizedData);
//        System.out.println(normalizedDataDAO.getNormalizedDatas(4, 2, new CharSequence('A', 'Z'), "testGeneration"));
//        INeuralNetwork neuralNetwork = new MyNeuralNetwork();
//        System.out.println(neuralNetwork.test(21, 31, "test_geo", ""));
    }

}

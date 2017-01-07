package ge.tsu.handwriting_recognition.data_creator.service;

import ge.tsu.handwriting_recognition.data_creator.model.TestingInfo;
import ge.tsu.handwriting_recognition.data_creator.service.dao.TestingInfoDAO;
import ge.tsu.handwriting_recognition.data_creator.service.dao.TestingInfoDAOImpl;

import java.util.List;

public class TestingInfoServiceImpl implements TestingInfoService {

    private TestingInfoDAO testingInfoDAO = new TestingInfoDAOImpl();

    @Override
    public void addTestingInfo(TestingInfo testingInfo) {
        testingInfoDAO.addTestingInfo(testingInfo);
    }

    @Override
    public List<TestingInfo> getTestingInfoListByNetworkId(int networkId) {
        return testingInfoDAO.getTestingInfoListByNetworkId(networkId);
    }
}

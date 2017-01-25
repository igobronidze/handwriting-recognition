package ge.edu.tsu.handwriting_recognition.control_panel.server.dao;

import ge.edu.tsu.handwriting_recognition.control_panel.model.info.TestingInfo;

import java.util.List;

public interface TestingInfoDAO {

    void addTestingInfo(TestingInfo testingInfo);

    List<TestingInfo> getTestingInfoListByNetworkId(int networkId);
}

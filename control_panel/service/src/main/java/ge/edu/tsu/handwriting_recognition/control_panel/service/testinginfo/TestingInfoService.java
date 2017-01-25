package ge.edu.tsu.handwriting_recognition.control_panel.service.testinginfo;

import ge.edu.tsu.handwriting_recognition.control_panel.model.info.TestingInfo;

import java.util.List;

public interface TestingInfoService {

    void addTestingInfo(TestingInfo testingInfo);

    List<TestingInfo> getTestingInfoListByNetworkId(int networkId);
}

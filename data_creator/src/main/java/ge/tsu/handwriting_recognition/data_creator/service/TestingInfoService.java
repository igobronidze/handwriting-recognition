package ge.tsu.handwriting_recognition.data_creator.service;

import ge.tsu.handwriting_recognition.data_creator.model.TestingInfo;

import java.util.List;

public interface TestingInfoService {

    void addTestingInfo(TestingInfo testingInfo);

    List<TestingInfo> getTestingInfoListByNetworkId(int networkId);
}

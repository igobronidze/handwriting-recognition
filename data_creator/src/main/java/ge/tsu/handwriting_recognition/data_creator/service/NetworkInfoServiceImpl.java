package ge.tsu.handwriting_recognition.data_creator.service;

import ge.tsu.handwriting_recognition.data_creator.model.NetworkInfo;
import ge.tsu.handwriting_recognition.data_creator.service.dao.NetworkInfoDAO;
import ge.tsu.handwriting_recognition.data_creator.service.dao.NetworkInfoDAOImpl;
import ge.tsu.handwriting_recognition.data_creator.service.dao.TestingInfoDAO;
import ge.tsu.handwriting_recognition.data_creator.service.dao.TestingInfoDAOImpl;

import java.util.List;

public class NetworkInfoServiceImpl implements NetworkInfoService {

    private NetworkInfoDAO networkInfoDAO = new NetworkInfoDAOImpl();

    private TestingInfoDAO testingInfoDAO = new TestingInfoDAOImpl();

    @Override
    public int addNetworkInfo(NetworkInfo networkInfo) {
        return networkInfoDAO.addNetworkInfo(networkInfo);
    }

    @Override
    public List<NetworkInfo> getNetworkInfoList(Integer id, String generation) {
        List<NetworkInfo> networkInfoList = networkInfoDAO.getNetworkInfoList(id, generation);
        for (NetworkInfo networkInfo : networkInfoList) {
            networkInfo.setTestingInfoList(testingInfoDAO.getTestingInfoListByNetworkId(networkInfo.getId()));
        }
        return networkInfoList;
    }
}

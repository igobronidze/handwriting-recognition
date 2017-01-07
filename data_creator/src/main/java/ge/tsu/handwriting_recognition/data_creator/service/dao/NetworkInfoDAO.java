package ge.tsu.handwriting_recognition.data_creator.service.dao;

import ge.tsu.handwriting_recognition.data_creator.model.NetworkInfo;

import java.util.List;

public interface NetworkInfoDAO {

    int addNetworkInfo(NetworkInfo networkInfo);

    List<NetworkInfo> getNetworkInfoList(Integer id, String generation);
}

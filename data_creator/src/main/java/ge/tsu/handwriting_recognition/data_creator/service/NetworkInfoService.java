package ge.tsu.handwriting_recognition.data_creator.service;

import ge.tsu.handwriting_recognition.data_creator.model.NetworkInfo;

import java.util.List;

public interface NetworkInfoService {

    int addNetworkInfo(NetworkInfo networkInfo);

    List<NetworkInfo> getNetworkInfoList(Integer id, String generation);

    void deleteNetworkInfo(int id);
}

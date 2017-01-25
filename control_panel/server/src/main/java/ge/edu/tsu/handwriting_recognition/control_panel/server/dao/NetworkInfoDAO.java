package ge.edu.tsu.handwriting_recognition.control_panel.server.dao;

import ge.edu.tsu.handwriting_recognition.control_panel.model.info.NetworkInfo;

import java.util.List;

public interface NetworkInfoDAO {

    int addNetworkInfo(NetworkInfo networkInfo);

    List<NetworkInfo> getNetworkInfoList(Integer id, String generation);

    void deleteNetworkInfo(int id);
}

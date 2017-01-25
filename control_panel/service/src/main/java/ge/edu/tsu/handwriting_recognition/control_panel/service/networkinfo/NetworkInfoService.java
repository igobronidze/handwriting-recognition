package ge.edu.tsu.handwriting_recognition.control_panel.service.networkinfo;

import ge.edu.tsu.handwriting_recognition.control_panel.model.info.NetworkInfo;

import java.util.List;

public interface NetworkInfoService {

    int addNetworkInfo(NetworkInfo networkInfo);

    List<NetworkInfo> getNetworkInfoList(Integer id, String generation);

    void deleteNetworkInfo(int id);
}

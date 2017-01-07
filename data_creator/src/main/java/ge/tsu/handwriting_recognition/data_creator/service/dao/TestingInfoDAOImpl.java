package ge.tsu.handwriting_recognition.data_creator.service.dao;

import ge.tsu.handwriting_recognition.data_creator.model.TestingInfo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestingInfoDAOImpl implements TestingInfoDAO {

    private PreparedStatement pstmt;

    @Override
    public void addTestingInfo(TestingInfo testingInfo) {
        try {
            String sql = "INSERT INTO testing_info (number_of_test, error, network_id, generation) VALUES (?,?,?,?);";
            pstmt = DatabaseUtil.getConnection().prepareStatement(sql);
            pstmt.setInt(1, testingInfo.getNumberOfTest());
            pstmt.setFloat(2, testingInfo.getError());
            pstmt.setInt(3, testingInfo.getNetworkId());
            pstmt.setString(4, testingInfo.getGeneration());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            DatabaseUtil.closeConnection();
        }
    }

    @Override
    public List<TestingInfo> getTestingInfoListByNetworkId(int networkId) {
        List<TestingInfo> testingInfoList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM testing_info WHERE network_id = ?";
            pstmt = DatabaseUtil.getConnection().prepareStatement(sql);
            pstmt.setInt(1, networkId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                TestingInfo testingInfo = new TestingInfo();
                testingInfo.setNetworkId(rs.getInt("network_id"));
                testingInfo.setError(rs.getFloat("error"));
                testingInfo.setNumberOfTest(rs.getInt("number_of_test"));
                testingInfo.setGeneration(rs.getString("generation"));
                testingInfoList.add(testingInfo);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            DatabaseUtil.closeConnection();
        }
        return testingInfoList;
    }
}

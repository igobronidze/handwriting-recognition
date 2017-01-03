package ge.tsu.handwriting_recognition.data_creator.service.dao;

import ge.tsu.handwriting_recognition.data_creator.model.SystemParameter;
import ge.tsu.handwriting_recognition.data_creator.service.exception.HandwritingRecognitionException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SystemParameterDAOImpl implements SystemParameterDAO {

    private PreparedStatement pstmt;

    @Override
    public void addSystemParameter(SystemParameter systemParameter) throws HandwritingRecognitionException {
        try {
            String uniqueSQL = "SELECT COUNT(*) FROM system_parameter WHERE key = ?";
            pstmt = DatabaseUtil.getConnection().prepareStatement(uniqueSQL);
            pstmt.setString(1, systemParameter.getKey());
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                throw new HandwritingRecognitionException("keyMustBeUnique");
            }
            String sql = "INSERT INTO system_parameter (key, value) VALUES (?,?);";
            pstmt = DatabaseUtil.getConnection().prepareStatement(sql);
            pstmt.setString(1, systemParameter.getKey());
            pstmt.setString(2, systemParameter.getValue());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            DatabaseUtil.closeConnection();
        }
    }

    @Override
    public void editSystemParameter(SystemParameter systemParameter) throws HandwritingRecognitionException {
        try {
            String sql = "UPDATE system_parameter SET value = ? WHERE key = ?";
            pstmt = DatabaseUtil.getConnection().prepareStatement(sql);
            pstmt.setString(1, systemParameter.getValue());
            pstmt.setString(2, systemParameter.getKey());
            int count = pstmt.executeUpdate();
            if (count < 1) {
                throw new HandwritingRecognitionException("keyNotExist");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            DatabaseUtil.closeConnection();
        }
    }

    @Override
    public void deleteSystemParameter(String key) throws HandwritingRecognitionException {
        try {
            String sql = "DELETE FROM system_parameter WHERE key = ?";
            pstmt = DatabaseUtil.getConnection().prepareStatement(sql);
            pstmt.setString(1, key);
            int count = pstmt.executeUpdate();
            if (count < 1) {
                throw new HandwritingRecognitionException("keyNotExist");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            DatabaseUtil.closeConnection();
        }
    }

    @Override
    public List<SystemParameter> getSystemParameters(String key) {
        List<SystemParameter> systemParameterList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM system_parameter ";
            if (key != null && !key.isEmpty()) {
                sql += "WHERE key LIKE '%" + key + "%'";
            }
            pstmt = DatabaseUtil.getConnection().prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String k = rs.getString("key");
                String value = rs.getString("value");
                SystemParameter systemParameter = new SystemParameter(id, k, value);
                systemParameterList.add(systemParameter);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            DatabaseUtil.closeConnection();
        }
        return systemParameterList;
    }

    @Override
    public String getSystemParameterValue(String key) {
        try {
            String sql = "SELECT value FROM system_parameter WHERE key = ?";
            pstmt = DatabaseUtil.getConnection().prepareStatement(sql);
            pstmt.setString(1, key);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String value = rs.getString("value");
                return value;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            DatabaseUtil.closeConnection();
        }
        return null;
    }
}

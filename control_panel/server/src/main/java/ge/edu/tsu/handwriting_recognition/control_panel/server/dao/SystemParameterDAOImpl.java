package ge.edu.tsu.handwriting_recognition.control_panel.server.dao;


import ge.edu.tsu.handwriting_recognition.control_panel.model.exception.ControlPanelException;
import ge.edu.tsu.handwriting_recognition.control_panel.model.sysparam.SysParamType;
import ge.edu.tsu.handwriting_recognition.control_panel.model.sysparam.SystemParameter;
import ge.edu.tsu.handwriting_recognition.control_panel.server.caching.CachedSystemParameter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SystemParameterDAOImpl implements SystemParameterDAO {

    private PreparedStatement pstmt;

    @Override
    public void addSystemParameter(SystemParameter systemParameter) throws ControlPanelException {
        try {
            String uniqueSQL = "SELECT COUNT(*) FROM system_parameter WHERE key = ?";
            pstmt = DatabaseUtil.getConnection().prepareStatement(uniqueSQL);
            pstmt.setString(1, systemParameter.getKey());
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                throw new ControlPanelException("keyMustBeUnique");
            }
            String sql = "INSERT INTO system_parameter (key, value, type) VALUES (?,?,?);";
            pstmt = DatabaseUtil.getConnection().prepareStatement(sql);
            pstmt.setString(1, systemParameter.getKey());
            pstmt.setString(2, systemParameter.getValue());
            pstmt.setString(3, systemParameter.getType().name());
            pstmt.executeUpdate();
            CachedSystemParameter.editOrAddParameter(systemParameter);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            DatabaseUtil.closeConnection();
        }
    }

    @Override
    public void editSystemParameter(SystemParameter systemParameter) throws ControlPanelException {
        try {
            String sql = "UPDATE system_parameter SET value = ? WHERE key = ?";
            pstmt = DatabaseUtil.getConnection().prepareStatement(sql);
            pstmt.setString(1, systemParameter.getValue());
            pstmt.setString(2, systemParameter.getKey());
            int count = pstmt.executeUpdate();
            if (count < 1) {
                throw new ControlPanelException("keyNotExist");
            }
            CachedSystemParameter.editOrAddParameter(systemParameter);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            DatabaseUtil.closeConnection();
        }
    }

    @Override
    public void deleteSystemParameter(String key) throws ControlPanelException {
        try {
            String sql = "DELETE FROM system_parameter WHERE key = ?";
            pstmt = DatabaseUtil.getConnection().prepareStatement(sql);
            pstmt.setString(1, key);
            int count = pstmt.executeUpdate();
            if (count < 1) {
                throw new ControlPanelException("keyNotExist");
            }
            CachedSystemParameter.deleteParameter(key);
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
                String type = rs.getString("type");
                SystemParameter systemParameter = new SystemParameter(id, k, value, SysParamType.valueOf(type));
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

package ge.edu.tsu.handwriting_recognition.control_panel.server.dao;


import ge.edu.tsu.handwriting_recognition.control_panel.model.network.NormalizedData;
import ge.edu.tsu.handwriting_recognition.control_panel.model.network.CharSequence;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NormalizedDataDAOImpl implements NormalizedDataDAO {

    private PreparedStatement pstmt;

    @Override
    public void addNormalizedData(NormalizedData normalizedData) {
        try {
            String sql = "INSERT INTO normalized_data (width, height, letter, first_symbol, last_symbol, generation, data)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?)";
            pstmt = DatabaseUtil.getConnection().prepareStatement(sql);
            pstmt.setInt(1, normalizedData.getWidth());
            pstmt.setInt(2, normalizedData.getHeight());
            pstmt.setString(3, "" + normalizedData.getLetter());
            pstmt.setString(4, "" + normalizedData.getCharSequence().getFirstSymbol());
            pstmt.setString(5, "" + normalizedData.getCharSequence().getLastSymbol());
            pstmt.setString(6, normalizedData.getTrainingSetGeneration());
            pstmt.setArray(7, DatabaseUtil.getConnection().createArrayOf("float4", normalizedData.getData()));
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            DatabaseUtil.closeConnection();
        }
    }

    @Override
    public List<NormalizedData> getNormalizedDatas(Integer width, Integer height, CharSequence charSequence, String generation) {
        List<NormalizedData> normalizedDataList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM normalized_data WHERE 1=1 ";
            if (width != null) {
                sql += "AND width = '" + width + "' ";
            }
            if (height != null) {
                sql += "AND height = '" + height + "' ";
            }
            if (charSequence != null) {
                sql += "AND first_symbol = '" + charSequence.getFirstSymbol() + "' AND last_symbol = '" + charSequence.getLastSymbol() + "' ";
            }
            if (generation != null) {
                sql += "AND generation = '" + generation + "' ";
            }
            pstmt = DatabaseUtil.getConnection().prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Integer wid = rs.getInt("width");
                Integer heig = rs.getInt("height");
                String letterString = rs.getString("letter");
                Character letter = letterString == null || letterString.isEmpty() ? null : letterString.charAt(0);
                String firstSymbolString = rs.getString("first_symbol");
                Character firSym = firstSymbolString == null || firstSymbolString.isEmpty() ? null : firstSymbolString.charAt(0);
                String lastSymbolString = rs.getString("last_symbol");
                Character lasSym = lastSymbolString == null || lastSymbolString.isEmpty() ? null : lastSymbolString.charAt(0);
                CharSequence chSeq = null;
                if (firSym != null && lasSym != null) {
                    chSeq = new CharSequence(firSym, lasSym);
                }
                String gen = rs.getString("generation");
                Array sqlArray = rs.getArray("data");
                Float[] data = (Float[])sqlArray.getArray();
                NormalizedData normalizedData = new NormalizedData(wid, heig, data, letter, chSeq, gen);
                normalizedDataList.add(normalizedData);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            DatabaseUtil.closeConnection();
        }
        return normalizedDataList;
    }
}

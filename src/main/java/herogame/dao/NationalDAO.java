package herogame.dao;

import herogame.model.National;
import herogame.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NationalDAO {
    public List<National> getAllNationals() {
        List<National> nationals = new ArrayList<>();
        String sql = "SELECT * FROM National";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                National national = new National();
                national.setNationalId(rs.getInt("NationalId"));
                national.setNationalName(rs.getString("NationalName"));
                nationals.add(national);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nationals;
    }

    public void addNational(National national) {
        String sql = "INSERT INTO National (NationalName) VALUES (?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, national.getNationalName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteNational(int nationalId) {
        String sql = "DELETE FROM National WHERE NationalId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, nationalId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
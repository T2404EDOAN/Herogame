package herogame.dao;

import herogame.model.Player;
import herogame.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDAO {
    public List<Player> getAllPlayers() {
        List<Player> players = new ArrayList<>();
        String sql = "SELECT p.*, n.NationalName FROM Player p " +
                "JOIN National n ON p.NationalId = n.NationalId";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Player player = new Player();
                player.setPlayerId(rs.getInt("PlayerId"));
                player.setNationalId(rs.getInt("NationalId"));
                player.setPlayerName(rs.getString("PlayerName"));
                player.setHighScore(rs.getInt("HighScore"));
                player.setLevel(rs.getInt("Level"));
                player.setNationalName(rs.getString("NationalName"));
                players.add(player);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }

    public void addPlayer(Player player) {
        String sql = "INSERT INTO Player (NationalId, PlayerName, HighScore, Level) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, player.getNationalId());
            pstmt.setString(2, player.getPlayerName());
            pstmt.setInt(3, player.getHighScore());
            pstmt.setInt(4, player.getLevel());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePlayer(int playerId) {
        String sql = "DELETE FROM Player WHERE PlayerId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, playerId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Player> findPlayersByName(String name) {
        List<Player> players = new ArrayList<>();
        String sql = "SELECT p.*, n.NationalName FROM Player p " +
                "JOIN National n ON p.NationalId = n.NationalId " +
                "WHERE PlayerName LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + name + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Player player = new Player();
                player.setPlayerId(rs.getInt("PlayerId"));
                player.setNationalId(rs.getInt("NationalId"));
                player.setPlayerName(rs.getString("PlayerName"));
                player.setHighScore(rs.getInt("HighScore"));
                player.setLevel(rs.getInt("Level"));
                player.setNationalName(rs.getString("NationalName"));
                players.add(player);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }

    public List<Player> getTop10Players() {
        List<Player> players = new ArrayList<>();
        String sql = "SELECT p.*, n.NationalName FROM Player p " +
                "JOIN National n ON p.NationalId = n.NationalId " +
                "ORDER BY HighScore DESC LIMIT 10";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Player player = new Player();
                player.setPlayerId(rs.getInt("PlayerId"));
                player.setNationalId(rs.getInt("NationalId"));
                player.setPlayerName(rs.getString("PlayerName"));
                player.setHighScore(rs.getInt("HighScore"));
                player.setLevel(rs.getInt("Level"));
                player.setNationalName(rs.getString("NationalName"));
                players.add(player);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }
}
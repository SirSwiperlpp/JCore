package de.sirswiperlpp.jcore.Provider;

import de.sirswiperlpp.jcore.SQL.MySQL;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class BanPROV
{

    public static void createBanTable() throws SQLException
    {
        PreparedStatement ps = MySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS bantable (player_name VARCHAR(100), UUID VARCHAR(100), REASON TEXT)");
        ps.executeUpdate();
    }

    public static String getBannedPlayer(String playername) throws SQLException
    {
        try {
            String query = "SELECT UUID FROM bantable WHERE player_name = ?";
            try (PreparedStatement statement = MySQL.getConnection().prepareStatement(query)) {
                statement.setString(1, playername);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("UUID");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQLException: " + e.getMessage());
        }
        return "";
    }

    public static String getBanReason(String playername) throws SQLException
    {
        try {
            String query = "SELECT REASON FROM bantable WHERE player_name = ?";
            try (PreparedStatement statement = MySQL.getConnection().prepareStatement(query)) {
                statement.setString(1, playername);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("REASON");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQLException: " + e.getMessage());
        }
        return "";
    }

    public static void BanPlayer(String pname, String uuid, String reason)
    {
        try {
            String query = "INSERT IGNORE INTO bantable (player_name, UUID, REASON) values (?,?,?)";
            try (PreparedStatement statement = MySQL.getConnection().prepareStatement(query)) {
                statement.setString(1, pname);
                statement.setString(2, uuid);
                statement.setString(3, reason);
                statement.executeUpdate();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

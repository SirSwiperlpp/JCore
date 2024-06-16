package de.sirswiperlpp.jcore.Provider;

import de.sirswiperlpp.jcore.SQL.MySQL;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PlayerPROV
{

    public static void createPlayerTable() throws SQLException
    {
        PreparedStatement ps = MySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS ptable (player_name VARCHAR(100), UUID VARCHAR(100))");
        ps.executeUpdate();
    }

    public static String getPlayer(String playername) throws SQLException
    {
        try {
            String query = "SELECT UUID FROM ptable WHERE player_name = ?";
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

    public static void insertPlayer(Player player) throws SQLException
    {
        try {
            String query = "INSERT IGNORE INTO ptable (player_name, UUID) values (?,?)";
            try (PreparedStatement statement = MySQL.getConnection().prepareStatement(query)) {
                statement.setString(1, player.getName());
                statement.setString(2, String.valueOf(player.getUniqueId()));

                statement.executeUpdate();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

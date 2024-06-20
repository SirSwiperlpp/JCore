package de.sirswiperlpp.jcore.Provider;

import de.sirswiperlpp.jcore.SQL.MySQL;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EcoPROV
{

    public static void createEcoTable() throws SQLException
    {
        PreparedStatement ps = MySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS EcoTable (player_name VARCHAR(100) PRIMARY KEY, money INT)");
        ps.executeUpdate();
    }

    public static int getMoney(String playername) throws SQLException
    {
        try {
            String query = "SELECT money FROM EcoTable WHERE player_name = ?";
            try (PreparedStatement statement = MySQL.getConnection().prepareStatement(query)) {
                statement.setString(1, playername);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("money");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQLException: " + e.getMessage());
        }
        return -1;
    }

    public static void updatePlayer(Player player, int updateValue) throws SQLException
    {
        try {
            String query = "UPDATE EcoTable SET money = ? WHERE player_name = ?";
            try (PreparedStatement statement = MySQL.getConnection().prepareStatement(query)) {
                statement.setInt(1,updateValue);
                statement.setString(2, player.getName());

                statement.executeUpdate();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void regiPlayer(Player player)
    {
        try {
            String query = "INSERT IGNORE INTO EcoTable (player_name, money) values (?,?)";
            try (PreparedStatement statement = MySQL.getConnection().prepareStatement(query)) {
                statement.setString(1, player.getName());
                statement.setInt(2, 1000);

                statement.executeUpdate();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

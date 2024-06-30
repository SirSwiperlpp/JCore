package de.sirswiperlpp.jcore.Provider;

import de.sirswiperlpp.jcore.SQL.MySQL;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HomePROV
{

    public static void createHomeTable() throws SQLException
    {
        PreparedStatement ps = MySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS HomeTable (player_name VARCHAR(100), homename TEXT, X TEXT, Y TEXT, Z TEXT, world TEXT)");
        ps.executeUpdate();
    }

    public static void insertHome(Player player, String homename) throws SQLException
    {
        try {
            String query = "INSERT INTO HomeTable VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = MySQL.getConnection().prepareStatement(query)) {
                statement.setString(1, player.getName());
                statement.setString(2, homename);
                statement.setString(3, String.valueOf(player.getLocation().getX()));
                statement.setString(4, String.valueOf(player.getLocation().getY()));
                statement.setString(5, String.valueOf(player.getLocation().getZ()));
                statement.setString(6, player.getWorld().getName());

                statement.executeUpdate();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getX(String homename, String playername) throws SQLException
    {
        try {
            String query = "SELECT X FROM HomeTable WHERE homename = ? AND player_name = ?";
            try (PreparedStatement statement = MySQL.getConnection().prepareStatement(query)) {
                statement.setString(1, homename);
                statement.setString(2, playername);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("X");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQLException: " + e.getMessage());
        }
        return "NF";
    }

    public static String getY(String homename, String playername) throws SQLException
    {
        try {
            String query = "SELECT Y FROM HomeTable WHERE homename = ? AND player_name = ?";
            try (PreparedStatement statement = MySQL.getConnection().prepareStatement(query)) {
                statement.setString(1, homename);
                statement.setString(2, playername);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("Y");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQLException: " + e.getMessage());
        }
        return "NF";
    }

    public static String getZ(String homename, String playername) throws SQLException
    {
        try {
            String query = "SELECT Z FROM HomeTable WHERE homename = ? AND player_name = ?";
            try (PreparedStatement statement = MySQL.getConnection().prepareStatement(query)) {
                statement.setString(1, homename);
                statement.setString(2, playername);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("Z");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQLException: " + e.getMessage());
        }
        return "NF";
    }

    public static String getWorld(String homename, String playername) throws SQLException
    {
        try {
            String query = "SELECT world FROM HomeTable WHERE homename = ? AND player_name = ?";
            try (PreparedStatement statement = MySQL.getConnection().prepareStatement(query)) {
                statement.setString(1, homename);
                statement.setString(2, playername);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("world");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQLException: " + e.getMessage());
        }
        return "NF";
    }

    public static String getHomeOwner(String homename, String playername) throws SQLException
    {
        try {
            String query = "SELECT player_name FROM HomeTable WHERE homename = ? AND player_name = ?";
            try (PreparedStatement statement = MySQL.getConnection().prepareStatement(query)) {
                statement.setString(1, homename);
                statement.setString(2, playername);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("player_name");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQLException: " + e.getMessage());
        }
        return "NF";
    }

    public static String getHome(Player player, String homename) throws SQLException
    {
        try {
            String query = "SELECT homename FROM HomeTable WHERE player_name = ? AND homename = ?";
            try (PreparedStatement statement = MySQL.getConnection().prepareStatement(query)) {
                statement.setString(1, player.getName());
                statement.setString(2, homename);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("homename");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQLException: " + e.getMessage());
        }
        return "NF";
    }


    public static List<String> getHomesFromOwner(String playerName) throws SQLException {
        List<String> homes = new ArrayList<>();
        String query = "SELECT homename FROM HomeTable WHERE player_name = ?";

        Connection connection = null;

        try {
            connection = MySQL.getConnection();
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, playerName);

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        homes.add(resultSet.getString("homename"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQLException: " + e.getMessage());
        }

        return homes;
    }

    public static void removeHome(String homename, String playername) throws SQLException
    {
        try {
            String query = "DELETE FROM HomeTable WHERE homename = ? AND player_name = ?";
            try (PreparedStatement statement = MySQL.getConnection().prepareStatement(query)) {
                statement.setString(1, homename);
                statement.setString(2, playername);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQLException: " + e.getMessage());
        }
    }



}

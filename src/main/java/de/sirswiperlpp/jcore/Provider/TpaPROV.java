package de.sirswiperlpp.jcore.Provider;

import de.sirswiperlpp.jcore.SQL.MySQL;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TpaPROV
{
    public static void createPlayerTable() throws SQLException
    {
        PreparedStatement ps = MySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS pendingRQ (pending_for VARCHAR(100), pending_from VARCHAR(100))");
        ps.executeUpdate();
    }

    public static String getRQST(String playername) throws SQLException
    {
        try {
            String query = "SELECT pending_from FROM pendingRQ WHERE pending_for = ?";
            try (PreparedStatement statement = MySQL.getConnection().prepareStatement(query)) {
                statement.setString(1, playername);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("pending_from");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQLException: " + e.getMessage());
        }
        return "";
    }

    public static void regiRQST(String targetname, String sendername) throws SQLException
    {
        try {
            String query = "INSERT IGNORE INTO pendingRQ (pending_for, pending_from) values (?,?)";
            try (PreparedStatement statement = MySQL.getConnection().prepareStatement(query)) {
                statement.setString(1,targetname);
                statement.setString(2, sendername);

                statement.executeUpdate();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeRQST(String targetname) throws SQLException
    {
        try {
            String query = "DELETE FROM pendingRQ WHERE pending_for = ?";
            try (PreparedStatement statement = MySQL.getConnection().prepareStatement(query)) {
                statement.setString(1,targetname);

                statement.executeUpdate();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package de.sirswiperlpp.jcore.Provider;

import de.sirswiperlpp.jcore.SQL.JobSQL;
import de.sirswiperlpp.jcore.SQL.MySQL;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JobPROV
{

    public static void createJobTable() throws SQLException
    {
        PreparedStatement ps = MySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS JobTable (player_name VARCHAR(100) PRIMARY KEY, c_job VARCHAR(100))");
        ps.executeUpdate();
    }

    public static void createXPTable() throws SQLException
    {
        PreparedStatement ps = JobSQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS j_xpTable (player_name VARCHAR(100), job VARCHAR(100), c_xp int, m_xp int, c_lvl int)");
        ps.executeUpdate();
    }

    public static void insertPlayer(Player player)
    {
        try {
            String query = "INSERT IGNORE INTO JobTable (player_name, c_job) values (?,?)";
            try (PreparedStatement statement = MySQL.getConnection().prepareStatement(query)) {
                statement.setString(1, player.getName());
                statement.setString(2, "Arbeitslos");
                statement.executeUpdate();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getCJob(Player player)
    {
        try {
            String query = "SELECT c_job FROM JobTable WHERE player_name = ?";
            try (PreparedStatement statement = MySQL.getConnection().prepareStatement(query)) {
                statement.setString(1, player.getName());

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("c_job");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQLException: " + e.getMessage());
        }
        return "§cNOT_FOUND";
    }

    public static void updatePlayer(Player player, String newjob)
    {
        try {
            String query = "UPDATE JobTable SET c_job = ? WHERE player_name = ?";
            try (PreparedStatement statement = MySQL.getConnection().prepareStatement(query)) {
                statement.setString(1, newjob);
                statement.setString(2, player.getName());
                statement.executeUpdate();

            }
        } catch (SQLException e) {
            player.sendMessage("§c'updatePlayer' konnte nicht ausgeführt werden! §8|§7 SQLException");
            e.printStackTrace();
        }
    }

    public static void createXPEntry(Player player, String job)
    {
        try {
            String query = "INSERT IGNORE INTO j_xpTable (player_name, job, c_xp, m_xp, c_lvl) values (?,?,?,?,?)";
            try (PreparedStatement statement = JobSQL.getConnection().prepareStatement(query)) {
                statement.setString(1, player.getName());
                statement.setString(2, job);
                statement.setInt(3, 0);
                statement.setInt(4, 1000);
                statement.setInt(5, 1);
                statement.executeUpdate();

            }
        } catch (SQLException e) {
            player.sendMessage("§c'createXPEntry' konnte nicht ausgeführt werden! §8|§7 SQLException");
            e.printStackTrace();
        }
    }

    public static int get_c_lvl(Player player, String Job)
    {
        try {
            String query = "SELECT c_lvl FROM j_xpTable WHERE player_name = ? AND job = ?";
            try (PreparedStatement statement = JobSQL.getConnection().prepareStatement(query)) {
                statement.setString(1, player.getName());
                statement.setString(2, Job);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("c_lvl");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQLException: " + e.getMessage());
        }
        return -1;
    }

    public static int getCXP(Player player, String p_job)
    {
        try {
            String query = "SELECT c_xp FROM j_xpTable WHERE player_name = ? AND job = ?";
            try (PreparedStatement statement = JobSQL.getConnection().prepareStatement(query)) {
                statement.setString(1, player.getName());
                statement.setString(2, p_job);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("c_xp");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQLException: " + e.getMessage());
        }
        return -1;
    }

    public static void updateCXP(Player player, int xp, String p_job)
    {
        try {
            String query = "UPDATE j_xpTable SET c_xp = ? WHERE player_name = ? AND job = ?";
            try (PreparedStatement statement = JobSQL.getConnection().prepareStatement(query)) {
                statement.setInt(1, xp);
                statement.setString(2, player.getName());
                statement.setString(3, p_job);
                statement.executeUpdate();

            }
        } catch (SQLException e) {
            player.sendMessage("§cSTOP! the Current Connection to the Database is faulty, XP arnt saved!");
            e.printStackTrace();
        }
    }

    public static int getMXP(Player player, String p_job)
    {
        try {
            String query = "SELECT m_xp FROM j_xpTable WHERE player_name = ? AND job = ?";
            try (PreparedStatement statement = JobSQL.getConnection().prepareStatement(query)) {
                statement.setString(1, player.getName());
                statement.setString(2, p_job);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("m_xp");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQLException: " + e.getMessage());
        }
        return -1;
    }

    public static void updateMXP(Player player, int xp, String p_job)
    {
        try {
            String query = "UPDATE j_xpTable SET m_xp = ? WHERE player_name = ? AND job = ?";
            try (PreparedStatement statement = JobSQL.getConnection().prepareStatement(query)) {
                statement.setInt(1, xp);
                statement.setString(2, player.getName());
                statement.setString(3, p_job);
                statement.executeUpdate();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateLVL(Player player, int lvl, String p_job)
    {
        try {
            String query = "UPDATE j_xpTable SET c_lvl = ? WHERE player_name = ? AND job = ?";
            try (PreparedStatement statement = JobSQL.getConnection().prepareStatement(query)) {
                statement.setInt(1, lvl);
                statement.setString(2, player.getName());
                statement.setString(3, p_job);
                statement.executeUpdate();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

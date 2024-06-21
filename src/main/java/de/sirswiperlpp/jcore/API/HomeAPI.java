package de.sirswiperlpp.jcore.API;

import de.sirswiperlpp.jcore.Lang.Language;
import de.sirswiperlpp.jcore.Main.Main;
import de.sirswiperlpp.jcore.Provider.HomePROV;
import de.sirswiperlpp.jcore.SQL.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeAPI
{

    static Language language = new Language(new File(Main.getInstance().getDataFolder(), "lang.ini"));

    public static void registerHome(Player player, String Homename) throws SQLException
    {

        if (HomeAPI.countHomes(player.getName()) == 3)
        {
            player.sendMessage(language.get("prefix") + language.get("max.homes"));
            return;
        }

        if (Objects.equals(HomePROV.getHomeOwner(Homename), player.getName()))
        {
            HomePROV.removeHome(Homename);
            HomePROV.insertHome(player, Homename);
            player.sendMessage(language.get("prefix") + language.translateString("recreated", Homename, String.valueOf(HomeAPI.countHomes(player.getName())), "3"));
            return;
        }

        HomePROV.insertHome(player, Homename);
        player.sendMessage(language.get("prefix") + language.translateString("created", Homename, String.valueOf(HomeAPI.countHomes(player.getName())), "3"));
    }

    public static void unregisterHome(Player player, String Homename) throws SQLException {
        String HomeOwner = HomePROV.getHomeOwner(Homename);

        if (Objects.equals(HomeOwner, player.getName()))
        {
            HomePROV.removeHome(Homename);
            player.sendMessage(language.get("prefix") + language.translateString("removed", Homename, String.valueOf(HomeAPI.countHomes(player.getName())), "3"));
        }
    }

    public static void teleportToHome(Player player, String homename)
    {
        try {
            String locX = HomePROV.getX(homename);
            String locY = HomePROV.getY(homename);
            String locZ = HomePROV.getZ(homename);
            String locWorld = HomePROV.getWorld(homename);
            String homeowner = HomePROV.getHomeOwner(homename);

            double x = Double.parseDouble(locX);
            double y = Double.parseDouble(locY);
            double z = Double.parseDouble(locZ);
            World world = Bukkit.getWorld(locWorld);

            Location location = new Location(world, x, y, z);

            if (Objects.equals(homeowner, player.getName()))
            {
                player.teleport(location);
                player.sendMessage(language.get("prefix") + language.translateString("h.tpd", homename));
                return;
            } else {
                player.sendMessage(language.get("prefix") + language.get("tp.canceled"));
                return;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int countHomes(String playerName) {
        String query = "SELECT COUNT(*) AS home_count FROM HomeTable WHERE player_name = ?";
        int homeCount = 0;

        Connection connection = null;

        try {
            connection = MySQL.getConnection();
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, playerName);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        homeCount = resultSet.getInt("home_count");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQLException: " + e.getMessage());
        }

        return homeCount;
    }

}

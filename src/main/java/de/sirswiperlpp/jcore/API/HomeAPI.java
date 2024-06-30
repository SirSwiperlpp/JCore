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
        String home = HomePROV.getHome(player, Homename);

        if (HomeAPI.countHomes(player.getName()) == 3)
        {
            player.sendMessage(language.get("prefix") + language.get("max.homes"));
            return;
        }

        if (Objects.equals(HomePROV.getHomeOwner(home, player.getName()), player.getName()))
        {
            HomePROV.removeHome(Homename, player.getName());
            HomePROV.insertHome(player, Homename);
            player.sendMessage(language.get("prefix") + language.translateString("recreated", Homename, String.valueOf(HomeAPI.countHomes(player.getName())), "3"));
            return;
        }

        HomePROV.insertHome(player, Homename);
        player.sendMessage(language.get("prefix") + language.translateString("created", Homename, String.valueOf(HomeAPI.countHomes(player.getName())), "3"));
    }

    public static void unregisterHome(Player player, String Homename) throws SQLException {
        String HomeOwner = HomePROV.getHomeOwner(Homename, player.getName());

        if (Objects.equals(HomeOwner, player.getName()))
        {
            HomePROV.removeHome(Homename, player.getName());
            player.sendMessage(language.get("prefix") + language.translateString("removed", Homename, String.valueOf(HomeAPI.countHomes(player.getName())), "3"));
        }
    }

    public static void teleportToHome(Player player, String homename)
    {
        try {
            System.out.println("Getting Data..");
            String home = HomePROV.getHome(player, homename);
            System.out.println("Got Home!");
            String locX = HomePROV.getX(home, player.getName());
            System.out.println("Got X!");
            String locY = HomePROV.getY(home, player.getName());
            System.out.println("Got Y!");
            String locZ = HomePROV.getZ(home, player.getName());
            System.out.println("Got XZ");
            String locWorld = HomePROV.getWorld(home, player.getName());
            System.out.println("Got World!");
            String homeowner = HomePROV.getHomeOwner(home, player.getName());
            System.out.println("Got homeowner!");

            if (Objects.equals(locX, "NF"))
            {
                player.sendMessage(language.get("prefix") + language.translateString("no.home"));
                return;
            }
            if (Objects.equals(locY, "NF"))
            {
                player.sendMessage(language.get("prefix") + language.translateString("no.home"));
                return;
            }
            if (Objects.equals(locZ, "NF"))
            {
                player.sendMessage(language.get("prefix") + language.translateString("no.home"));
                return;
            }
            if (Objects.equals(locWorld, "NF"))
            {
                player.sendMessage(language.get("prefix") + language.translateString("no.home"));
                return;
            }
            double x = Double.parseDouble(locX);
            System.out.println("X EQ DOUBLE");
            double y = Double.parseDouble(locY);
            System.out.println("Y EQ DOUBLE");
            double z = Double.parseDouble(locZ);
            System.out.println("Z EQ DOUBLE");
            World world = Bukkit.getWorld(locWorld);
            System.out.println("world is now BukkitWorld");

            Location location = new Location(world, x, y, z);

            System.out.println("is Home EQ Home?");
            if (!Objects.equals(home, homename))
            {
                System.out.println("no home");
                player.sendMessage(language.get("prefix") + language.get("no.home"));
                return;
            }

            System.out.println("Owner?");
            if (Objects.equals(homeowner, player.getName()))
            {
                System.out.println("yee, ready for Tp?");
                player.teleport(location);
                System.out.println("Tped!");
                player.sendMessage(language.get("prefix") + language.translateString("h.tpd", homename));
                System.out.println("Task Done!");
                return;
            } else {
                System.out.println("Nope!");
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

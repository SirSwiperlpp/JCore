package de.sirswiperlpp.jcore.API;

import de.sirswiperlpp.jcore.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BasicAPI
{

    public static String getPlayerPing(Player player)
    {

        int playerPing = player.getPing();

        if (playerPing >= 200)
            return "§4" + playerPing;

        if (playerPing >= 100)
            return "§c" + playerPing;

        if (playerPing >= 50)
            return "§a" + playerPing;

        return "§a" + playerPing;
    }

    public static String getFullPlaytime(Main plugin, Player player)
    {
        String playtimeM = String.valueOf(PlaytimeAPI.getPlayerMinutes(plugin, player.getUniqueId()));
        String playtimeH = String.valueOf(PlaytimeAPI.getPlayerHours(plugin, player.getUniqueId()));
        return "§a" + playtimeH + "h und " + playtimeM + "min";
    }

}

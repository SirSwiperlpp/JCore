package de.sirswiperlpp.jcore.API;

import de.sirswiperlpp.jcore.Provider.PlayerPROV;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.Objects;

public class PlayerAPI
{

    public static boolean checkForPlayer(Player player) throws SQLException
    {
        if (!Objects.equals(PlayerPROV.getPlayer(player.getName()), player.getUniqueId().toString()))
        {
            addPlayer(player);
            return false;
        }
        return true;
    }

    private static void addPlayer(Player player) throws SQLException
    {
        PlayerPROV.insertPlayer(player);
    }

}

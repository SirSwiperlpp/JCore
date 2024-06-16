package de.sirswiperlpp.jcore.Listener;

import de.sirswiperlpp.jcore.API.PlayerAPI;
import de.sirswiperlpp.jcore.Lang.Language;
import de.sirswiperlpp.jcore.Main.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.sql.SQLException;

public class PlayerListener implements Listener
{

    static Language language = new Language(new File(Main.getInstance().getDataFolder(), "lang.ini"));

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws SQLException {
        Player player = event.getPlayer();

        if (!PlayerAPI.checkForPlayer(player))
        {
            event.setJoinMessage(language.translateString("join.firsttime", player.getName()));
            player.sendTitle(language.get("join.title"), language.translateString("join.title.sub", player.getName()), 5, 10, 5);
        }
        event.setJoinMessage("§8[§a+§8] §5" + player.getName());
        player.sendTitle(language.get("join.title"), language.translateString("join.title.sub", player.getName()), 5, 10, 5);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        event.setQuitMessage("§8[§4-§8] §5" + player.getName());
    }


}

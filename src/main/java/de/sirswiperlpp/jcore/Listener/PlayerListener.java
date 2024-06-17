package de.sirswiperlpp.jcore.Listener;

import de.sirswiperlpp.jcore.API.PlayerAPI;
import de.sirswiperlpp.jcore.Lang.Language;
import de.sirswiperlpp.jcore.Main.Main;
import de.sirswiperlpp.jcore.Provider.BanPROV;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.sql.SQLException;
import java.util.Objects;

public class PlayerListener implements Listener
{

    static Language language = new Language(new File(Main.getInstance().getDataFolder(), "lang.ini"));

    @EventHandler
    public void playerLogin(PlayerLoginEvent event) throws SQLException {
        Player player = event.getPlayer();

        if (Objects.equals(BanPROV.getBannedPlayer(player.getName()), player.getUniqueId().toString()))
        {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "§cDu bist Gebannt!\nGrund: §7Community Ausschluss");
        }
    }

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

    @EventHandler
    public void chatEvent(AsyncPlayerChatEvent event)
    {
        Player player = event.getPlayer();
        String message = event.getMessage();

        if (player.getName().equals("Jalmo12"))
        {
            event.setFormat(language.translateString("chat.format.owner", player.getName(), message));
            return;
        }

        if (player.getName().equals("SirSwiperlpp"))
        {
            event.setFormat(language.translateString("chat.format.dev", player.getName(), message));
            return;
        }

        event.setFormat(language.translateString("chat.format", player.getName(), message));
    }


}

package de.sirswiperlpp.jcore.Tasks;

import de.sirswiperlpp.jcore.API.BasicAPI;
import de.sirswiperlpp.jcore.Main.Main;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TabHeaderTask extends BukkitRunnable
{

    private Main plugin;

    public TabHeaderTask(Main plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setPlayerListHeaderFooter("\n  §x§9§2§5§C§E§0✘§x§9§0§5§9§D§EW§x§8§D§5§7§D§Ci§x§8§B§5§4§D§Al§x§8§9§5§2§D§7l§x§8§7§5§0§D§5k§x§8§5§4§D§D§3o§x§8§3§4§B§D§1m§x§8§1§4§8§C§Em§x§7§F§4§6§C§Ce§x§7§C§4§3§C§An §x§7§8§3§F§C§5a§x§7§6§3§C§C§3u§x§7§4§3§A§C§1f §x§7§0§3§5§B§CJ§x§6§E§3§3§B§Aa§x§6§B§3§0§B§8l§x§6§9§2§E§B§6m§x§6§7§2§B§B§3o§x§6§5§2§9§B§11§x§6§3§2§6§A§F2§x§6§1§2§4§A§D.§x§5§F§2§2§A§Ad§x§5§D§1§F§A§8e§x§5§A§1§D§A§6!§x§5§8§1§A§A§4✘\n       §bDiscord: §7dc.jalmo12.de      \n"
                    , "\n§7Dein Ping: " + BasicAPI.getPlayerPing(player) + "\n§7Deine Playtime: " + BasicAPI.getFullPlaytime(plugin, player));
        }
    }

}

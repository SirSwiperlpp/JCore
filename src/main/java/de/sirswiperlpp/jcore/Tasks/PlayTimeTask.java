package de.sirswiperlpp.jcore.Tasks;

import de.sirswiperlpp.jcore.Main.Main;
import de.sirswiperlpp.jcore.Util.PlaytimeManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayTimeTask extends BukkitRunnable
{

    private final Main plugin;
    private final PlaytimeManager playTimeManager;

    public PlayTimeTask(Main plugin, PlaytimeManager playTimeManager) {
        this.plugin = plugin;
        this.playTimeManager = playTimeManager;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            playTimeManager.incrementPlayTime(player.getUniqueId());
        }
        playTimeManager.saveAllPlayTimes();
    }

}

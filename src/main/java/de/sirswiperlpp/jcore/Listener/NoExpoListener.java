package de.sirswiperlpp.jcore.Listener;

import de.sirswiperlpp.jcore.Main.Main;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class NoExpoListener implements Listener
{

    private Main plugin;

    public NoExpoListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        Location spawn = plugin.getServer().getWorlds().get(0).getSpawnLocation();
        if (isInZone(event.getLocation(), spawn)) {
            event.setCancelled(true);
        }
    }

    private boolean isInZone(Location loc, Location center) {
        double distance = loc.distance(center);
        return distance <= 128;
    }

}

package de.sirswiperlpp.jcore.Listener;

import de.sirswiperlpp.jcore.Main.Main;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class NoBuildListener implements Listener
{

    private final Main plugin;

    public NoBuildListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getPlayer().isOp()) {
            return; // Allow ops to break blocks
        }

        Location spawn = plugin.getServer().getWorlds().get(0).getSpawnLocation();
        if (isInZone(event.getBlock().getLocation(), spawn)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getPlayer().isOp()) {
            return; // Allow ops to place blocks
        }

        Location spawn = plugin.getServer().getWorlds().get(0).getSpawnLocation();
        if (isInZone(event.getBlock().getLocation(), spawn)) {
            event.setCancelled(true);
        }
    }

    private boolean isInZone(Location loc, Location center) {
        double distance = loc.distance(center);
        return distance <= 128;
    }

}

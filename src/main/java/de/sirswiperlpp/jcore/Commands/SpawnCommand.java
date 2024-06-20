package de.sirswiperlpp.jcore.Commands;

import de.sirswiperlpp.jcore.Lang.Language;
import de.sirswiperlpp.jcore.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Objects;

public class SpawnCommand implements CommandExecutor
{

    static Language language = new Language(new File(Main.getInstance().getDataFolder(), "lang.ini"));

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (!(commandSender instanceof Player))
        {
            commandSender.sendMessage("You must be a player to use this command.");
            return true;
        }

        final Player player = (Player) commandSender;

        Location loc = Objects.requireNonNull(Bukkit.getWorld("world")).getSpawnLocation();
        player.teleport(loc);
        player.sendMessage(language.get("prefix") + language.get("teleported"));

        return true;
    }
}

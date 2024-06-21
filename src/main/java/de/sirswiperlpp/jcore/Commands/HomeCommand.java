package de.sirswiperlpp.jcore.Commands;

import de.sirswiperlpp.jcore.API.HomeAPI;
import de.sirswiperlpp.jcore.Lang.Language;
import de.sirswiperlpp.jcore.Main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class HomeCommand implements CommandExecutor {

    static Language language = new Language(new File(Main.getInstance().getDataFolder(), "lang.ini"));

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (!(commandSender instanceof Player))
        {
            commandSender.sendMessage("You must be a player to use this command!");
            return true;
        }

        Player player = (Player) commandSender;

        if (strings.length == 0)
        {
            commandSender.sendMessage(language.get("prefix" + "use: §c/home [home]"));
            return true;
        }
        HomeAPI.teleportToHome(player, strings[0]);
        return true;
    }
}

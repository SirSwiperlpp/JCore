package de.sirswiperlpp.jcore.Commands;

import de.sirswiperlpp.jcore.Lang.Language;
import de.sirswiperlpp.jcore.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class KickCommand implements CommandExecutor {

    static Language language = new Language(new File(Main.getInstance().getDataFolder(), "lang.ini"));

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {

        Player player = (Player) sender;

        if (!player.hasPermission("jcore.admin.kick"))
        {
            player.sendMessage(language.get("prefix") + language.get("no.perms"));
            for (Player onlineplayer : Bukkit.getOnlinePlayers()) {
                if (onlineplayer.isOp()) {
                    onlineplayer.sendMessage(language.get("prefix") + language.translateString("sus.using.cmd.k", player.getName()));
                    continue;
                }
            }
            return true;
        }

        if (strings.length != 2)
        {
            player.sendMessage(language.get("prefix") + "use: §c/kick <player> <reason>");
            return true;
        }

        Player target = Bukkit.getPlayer(strings[0]);
        String reason = strings[1];

        if (target == null)
        {
            player.sendMessage(language.get("prefix") + language.translateString("no.player", strings[0]));
            return true;
        }

        for (Player onlineplayer : Bukkit.getOnlinePlayers())
        {
            if (onlineplayer.equals(player))
                continue;

            if (onlineplayer.isOp())
            {
                onlineplayer.sendMessage(language.get("prefix") + language.translateString("kick.player.report", player.getName(), target.getName()));
                continue;
            }
        }
        target.kickPlayer(reason);
        player.sendMessage(language.get("prefix") + language.translateString("kick.player", target.getName()));

        return true;
    }
}

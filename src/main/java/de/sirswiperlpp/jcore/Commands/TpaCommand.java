package de.sirswiperlpp.jcore.Commands;

import de.sirswiperlpp.jcore.API.TpaAPI;
import de.sirswiperlpp.jcore.Lang.Language;
import de.sirswiperlpp.jcore.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.sql.SQLException;

public class TpaCommand implements CommandExecutor {

    static Language language = new Language(new File(Main.getInstance().getDataFolder(), "lang.ini"));

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player))
        {
            commandSender.sendMessage("You must be a player to use this command!");
            return true;
        }

        Player player = (Player) commandSender;

        if (strings.length != 2)
        {
            player.sendMessage(language.get("prefix") + "use: §c/tpa <send | accept | deny> <player>");
            return true;
        }

        Player target = Bukkit.getPlayer(strings[1]);

        switch (strings[0])
        {
            case "send":

                if (strings[1].isEmpty())
                {
                    player.sendMessage(language.get("prefix") + "use: §c/tpa send <player>");
                    return true;
                }

                if (target == null)
                {
                    player.sendMessage(language.get("prefix") + language.translateString("no.player", strings[1]));
                    return true;
                }

                if (target == player)
                {
                    player.sendMessage(language.get("prefix") + "§cABORTING! §8| §7TARGET_EQ_SENDER");
                    return true;
                }

                try {
                    TpaAPI.sendRQST(target, player);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;

            case "accept":
                if (strings[1].isEmpty())
                {
                    player.sendMessage(language.get("prefix") + "use: §c/tpa accept <player>");
                    return true;
                }

                if (target == null)
                {
                    player.sendMessage(language.get("prefix") + language.translateString("no.player", strings[1]));
                    return true;
                }

                if (target == player)
                {
                    player.sendMessage(language.get("prefix") + "§cABORTING! §8| §7TARGET_EQ_SENDER");
                    return true;
                }

                try {
                    TpaAPI.approveTp(target, player);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;

            case "deny":
                if (strings[1].isEmpty())
                {
                    player.sendMessage(language.get("prefix") + "use: §c/tpa deny <player>");
                    return true;
                }

                if (target == null)
                {
                    player.sendMessage(language.get("prefix") + language.translateString("no.player", strings[1]));
                    return true;
                }

                if (target == player)
                {
                    player.sendMessage(language.get("prefix") + "§cABORTING! §8| §7TARGET_EQ_SENDER");
                    return true;
                }

                try {
                    TpaAPI.denyTp(target, player);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
        }

        return true;
    }
}

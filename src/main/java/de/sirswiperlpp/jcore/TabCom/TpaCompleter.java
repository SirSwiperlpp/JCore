package de.sirswiperlpp.jcore.TabCom;

import de.sirswiperlpp.jcore.Commands.TpaCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TpaCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        List<String> completions = new ArrayList<>();
            if (args.length == 1) {
                List<String> subCommands = Arrays.asList("send", "accept", "deny");
                StringUtil.copyPartialMatches(args[0], subCommands, completions);
            }

            if (args.length == 2) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (!player.getName().equalsIgnoreCase(sender.getName())) {
                        completions.add(player.getName());
                    }
                }
            }

            Collections.sort(completions);
            return completions;
    }
}

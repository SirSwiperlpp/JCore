package de.sirswiperlpp.jcore.TabCom;

import de.sirswiperlpp.jcore.Provider.HomePROV;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DelHomeCompleter implements TabCompleter
{

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
    {
        List<String> completions = new ArrayList<>();

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (command.getName().equalsIgnoreCase("delhome")) {
                if (args.length == 1) {
                    try {
                        List<String> homes = HomePROV.getHomesFromOwner(player.getName());
                        StringUtil.copyPartialMatches(args[0], homes, completions);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        Collections.sort(completions);
        return completions;
    }

}

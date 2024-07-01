package de.sirswiperlpp.jcore.Commands;

import de.sirswiperlpp.jcore.Lang.Language;
import de.sirswiperlpp.jcore.Main.Main;
import de.sirswiperlpp.jcore.Util.VanishManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class VanishCommand implements CommandExecutor {

    private VanishManager vanishManager;

    public VanishCommand(VanishManager vanishManager) {
        this.vanishManager = vanishManager;
    }

    static Language language = new Language(new File(Main.getInstance().getDataFolder(), "lang.ini"));

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;
        boolean isVanished = vanishManager.isVanished(player);
        vanishManager.setVanished(player, !isVanished);

        if (!isVanished) {
            player.sendMessage(language.get("prefix") + language.get("v.visible"));
        } else {
            player.sendMessage(language.get("prefix") + language.get("v.invisible"));
        }

        return true;
    }
}

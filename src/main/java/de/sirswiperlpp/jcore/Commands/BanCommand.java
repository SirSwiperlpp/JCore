package de.sirswiperlpp.jcore.Commands;

import de.sirswiperlpp.jcore.Lang.Language;
import de.sirswiperlpp.jcore.Main.Main;
import de.sirswiperlpp.jcore.Provider.BanPROV;
import de.sirswiperlpp.jcore.Util.uuidRQST;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class BanCommand implements CommandExecutor {

    static Language language = new Language(new File(Main.getInstance().getDataFolder(), "lang.ini"));

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by a player!");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("core.admin.ban")) {
            player.sendMessage(language.get("prefix") + language.get("no.perms"));
            for (Player onlineplayer : Bukkit.getOnlinePlayers()) {
                if (onlineplayer.isOp()) {
                    onlineplayer.sendMessage(language.get("prefix") + language.translateString("sus.using.cmd", player.getName()));
                    continue;
                }
            }
            return true;
        }

        if (strings.length == 1) {
            Player target = Bukkit.getPlayer(strings[0]);
            if (target == null) {
                if (Objects.equals(strings[0], "SirSwiperlpp")) {
                    player.sendMessage(language.get("prefix") + "This player CANT be banned.");
                    return true;
                }

                CompletableFuture.supplyAsync(() -> {
                    try {
                        return uuidRQST.getUUIDFromName(strings[0]);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }).thenAccept(uuid -> {
                    if (uuid != null) {
                        BanPROV.BanPlayer(strings[0], uuid, "Community Ausschluss");
                        for (Player onlineplayer : Bukkit.getOnlinePlayers()) {
                            onlineplayer.sendMessage(language.get("prefix") + language.translateString("ban.broadcast", strings[0]));
                            onlineplayer.playSound(onlineplayer.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1.0F, 1.0F);
                        }
                    } else {
                        sender.sendMessage(language.get("prefix") + "§cERROR WHILE BANNING §8| §7NAMEMC_RESPONDED_WITH_404");
                    }
                });
            }

        }
        return true;
    }
}


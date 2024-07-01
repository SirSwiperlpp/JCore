package de.sirswiperlpp.jcore.Listener;

import de.sirswiperlpp.jcore.API.PlayerAPI;
import de.sirswiperlpp.jcore.API.ScoreboardAPI;
import de.sirswiperlpp.jcore.Lang.Language;
import de.sirswiperlpp.jcore.Main.Main;
import de.sirswiperlpp.jcore.Provider.BanPROV;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.io.File;
import java.sql.SQLException;
import java.util.Objects;

public class PlayerListener implements Listener
{

    private final Main main;

    public PlayerListener(Main main) {
        this.main = main;
    }

    static Language language = new Language(new File(Main.getInstance().getDataFolder(), "lang.ini"));

    @EventHandler
    public void playerLogin(PlayerLoginEvent event) throws SQLException {
        Player player = event.getPlayer();

        if (Objects.equals(BanPROV.getBannedPlayer(player.getName()), player.getUniqueId().toString()))
        {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "§cDu bist Gebannt!\nGrund: §7Community Ausschluss");
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws SQLException {
        Player player = event.getPlayer();

        if (!PlayerAPI.checkForPlayer(player))
        {
            event.setJoinMessage(language.translateString("join.firsttime", player.getName()));
            player.sendTitle(language.get("join.title"), language.translateString("join.title.sub", player.getName()), 5, 10, 5);
        }
        ScoreboardAPI.createScoreboard(player);
        event.setJoinMessage("§8[§a+§8] §5" + player.getName());
        player.sendTitle(language.get("join.title"), language.translateString("join.title.sub", player.getName()), 5, 10, 5);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        event.setQuitMessage("§8[§4-§8] §5" + player.getName());
    }

    @EventHandler
    public void chatEvent(AsyncPlayerChatEvent event)
    {
        Player player = event.getPlayer();
        String message = event.getMessage();

        if (player.getName().equals("Jalmo12"))
        {
            event.setFormat(language.translateString("chat.format.owner", player.getName(), message));
            return;
        }

        if (player.getName().equals("SirSwiperlpp"))
        {
            event.setFormat(language.translateString("chat.format.dev", player.getName(), message));
            return;
        }

        event.setFormat(language.translateString("chat.format", player.getName(), message));
    }

    @EventHandler
    public void onPJoin(PlayerJoinEvent event) throws SQLException {
        Player player = event.getPlayer();

        if (PlayerAPI.checkForPlayer(player))
            return;

        // Erstelle ein Buch ItemStack
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) book.getItemMeta();

        // Setze den Titel und den Autor des Buches
        meta.setTitle("§6§lChangelog - v0.6");
        meta.setAuthor("§cSwiper");

        // Füge Seiten mit wichtigen Informationen hinzu
        meta.addPage("§aWillkommen zur version §c0.6§a!!\n\n§aIch hab viel zeit in diese Version gesteckt und kann sie euch nun Endlich präsentieren!",
                "§aKommen wir zu den sachen die euch als Spieler betreffen:\n" +
                        "§cJobs §7- §aIch habe ein JobSystem Gecodet damit das EcoSystem Endlich ein Sinn hat :D\n" +
                        "§cVanish §7- §aWir als Admins können nun Endlich unsichtbar werden ohne immer /effect nutzen zu müssen (war echt nervig)\n",
                        "§cPerformance §7- §aIch habe auch noch etwas die Performance angepasst, weil ich gemerkt habe wie unfassbar Unperformant manche Systeme waren\n\n" +
                        "§aDas war es Basically auch schon, Danke das du bis hierhin gelesen hast :)");

        // Setze die Meta-Daten zurück zum Buch
        book.setItemMeta(meta);

        // Gebe das Buch dem Spieler
        player.getInventory().addItem(book);

        // Öffne das Buch für den Spieler
        openBook(player, book);
    }

    private void openBook(Player player, ItemStack book) {
        int slot = player.getInventory().getHeldItemSlot();
        ItemStack old = player.getInventory().getItem(slot);
        player.getInventory().setItem(slot, book);

        // Sende das Packet, um das Buch zu öffnen
        Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
            player.openBook(book);
            player.getInventory().setItem(slot, old);
        }, 1);
    }


}

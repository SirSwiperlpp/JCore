package de.sirswiperlpp.jcore.Main;

import de.sirswiperlpp.jcore.API.ScoreboardAPI;
import de.sirswiperlpp.jcore.Commands.*;
import de.sirswiperlpp.jcore.Lang.Language;
import de.sirswiperlpp.jcore.Listener.PlayerListener;
import de.sirswiperlpp.jcore.Provider.BanPROV;
import de.sirswiperlpp.jcore.Provider.HomePROV;
import de.sirswiperlpp.jcore.Provider.PlayerPROV;
import de.sirswiperlpp.jcore.Provider.TpaPROV;
import de.sirswiperlpp.jcore.SQL.MySQL;
import de.sirswiperlpp.jcore.TabCom.DelHomeCompleter;
import de.sirswiperlpp.jcore.TabCom.HomeCompleter;
import de.sirswiperlpp.jcore.TabCom.TpaCompleter;
import de.sirswiperlpp.jcore.Tasks.PlayTimeTask;
import de.sirswiperlpp.jcore.Tasks.TabHeaderTask;
import de.sirswiperlpp.jcore.Util.PlaytimeManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.SQLException;

public final class Main extends JavaPlugin {

    private static Main instance;
    public static FileConfiguration config;
    public static Language language;

    public void loadConfiguration() {
        File datafolder = this.getDataFolder();
        File configFile = new File(datafolder + File.separator + "config.yml");

        if (!configFile.exists()) {
            this.saveResource("config.yml", false);
        }

        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public Main() {
        instance = this;
        language = new Language(new File(getDataFolder(), "lang.ini"));
    }

    public static Main getInstance() {
        return instance;
    }

    private void checkAndCreateLanguageFile() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        File languageFile = new File(getDataFolder(), "lang.ini");
        if (!languageFile.exists()) {
            getLogger().info("language.ini not found. Creating...");

            saveResource("lang.ini", true);
        }
    }

    @Override
    public void onEnable() {
        loadConfiguration();
        checkAndCreateLanguageFile();
        MySQL.connect("JAL");
        try {
            PlayerPROV.createPlayerTable();
            BanPROV.createBanTable();
            TpaPROV.createPlayerTable();
            HomePROV.createHomeTable();
        } catch (SQLException e) {
            System.out.println("Here");
            throw new RuntimeException(e);
        }

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerListener(), this);

        new TabHeaderTask(this).runTaskTimer(this, 0, 100);

        getCommand("ban").setExecutor(new BanCommand());
        getCommand("kick").setExecutor(new KickCommand());
        getCommand("tpa").setExecutor(new TpaCommand());
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("home").setExecutor(new HomeCommand());
        getCommand("sethome").setExecutor(new SetHomeCommand());
        getCommand("delhome").setExecutor(new DelHomeCommand());

        getCommand("tpa").setTabCompleter(new TpaCompleter());
        getCommand("home").setTabCompleter(new HomeCompleter());
        getCommand("delhome").setTabCompleter(new DelHomeCompleter());


        Bukkit.getScheduler().runTaskTimer(this, ScoreboardAPI::updateScoreboards, 0L, 20L);

        PlaytimeManager ptm = new PlaytimeManager(this);
        new PlayTimeTask(this, ptm).runTaskTimer(this, 0L, 1200L); // 1200 Ticks = 1 Minute
        ptm.loadAllPlayTimes();

    }

    @Override
    public void onDisable() {
        PlaytimeManager ptm = new PlaytimeManager(this);
        ptm.saveAllPlayTimes();
    }
}

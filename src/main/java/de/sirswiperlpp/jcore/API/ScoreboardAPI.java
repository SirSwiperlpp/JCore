package de.sirswiperlpp.jcore.API;

import de.sirswiperlpp.jcore.Lang.Language;
import de.sirswiperlpp.jcore.Main.Main;
import de.sirswiperlpp.jcore.Provider.EcoPROV;
import de.sirswiperlpp.jcore.Provider.JobPROV;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.io.File;
import java.sql.SQLException;

public class ScoreboardAPI
{

    static Language language = new Language(new File(Main.getInstance().getDataFolder(), "lang.ini"));

    private static Main plugin = Main.getInstance();

    public ScoreboardAPI(Main plugin)
    {
        this.plugin = plugin;
    }

    public static void createScoreboard(Player player) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();

        Objective objective = board.registerNewObjective("jalmo", "dummy", "§dplay.jalmo12.de");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score vers = objective.getScore("§7version 0.6");
        vers.setScore(12);

        Score blank0 = objective.getScore(" ");
        blank0.setScore(11);

        Score rang = objective.getScore(language.get("rang.ttl"));
        rang.setScore(10);

        Score rangValue = objective.getScore("→ §a" + getRank(player));
        rangValue.setScore(9);

        Score blank13 = objective.getScore("      ");
        blank13.setScore(8);

        Score playtime = objective.getScore(language.get("playtime.ttl"));
        playtime.setScore(7);

        Score playtimeval = objective.getScore("→ §a" + getPlaytime(player));
        playtimeval.setScore(6);

        Score blank12 = objective.getScore("  ");
        blank12.setScore(5);

        Score job = objective.getScore("§cJob");
        job.setScore(4);

        Score jobVal = objective.getScore("→ §a" + getJob(player));
        jobVal.setScore(3);

        Score blank2 = objective.getScore("   ");
        blank2.setScore(2);

        Score money = objective.getScore(language.get("money.ttl"));
        money.setScore(1);

        Score moneyValue = objective.getScore("→ §a" + getMoney(player));
        moneyValue.setScore(0);

        player.setScoreboard(board);
    }

    public static void updateScoreboards() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            ScoreboardManager manager = Bukkit.getScoreboardManager();
            Scoreboard board = manager.getNewScoreboard();

            Objective objective = board.registerNewObjective("jalmo", "dummy", "§x§5§6§0§C§A§Cp§x§5§C§0§C§A§Cl§x§6§3§0§D§A§Ca§x§6§9§0§D§A§Cy§x§7§0§0§E§A§C.§x§7§6§0§E§A§Cj§x§7§D§0§F§A§Ca§x§8§3§0§F§A§Cl§x§8§9§0§F§A§Cm§x§9§0§1§0§A§Co§x§9§6§1§0§A§C1§x§9§D§1§1§A§C2§x§A§3§1§1§A§C.§x§A§A§1§2§A§Cd§x§B§0§1§2§A§Ce");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);

            Score vers = objective.getScore("§7version 0.6");
            vers.setScore(12);

            Score blank0 = objective.getScore(" ");
            blank0.setScore(11);

            Score rang = objective.getScore(language.get("rang.ttl"));
            rang.setScore(10);

            Score rangValue = objective.getScore("→ §a" + getRank(player));
            rangValue.setScore(9);

            Score blank13 = objective.getScore("      ");
            blank13.setScore(8);

            Score playtime = objective.getScore(language.get("playtime.ttl"));
            playtime.setScore(7);

            Score playtimeval = objective.getScore("→ §a" + getPlaytime(player));
            playtimeval.setScore(6);

            Score blank12 = objective.getScore("  ");
            blank12.setScore(5);

            Score job = objective.getScore("§cJob");
            job.setScore(4);

            Score jobVal = objective.getScore("→ §a" + getJob(player));
            jobVal.setScore(3);

            Score blank2 = objective.getScore("   ");
            blank2.setScore(2);

            Score money = objective.getScore(language.get("money.ttl"));
            money.setScore(1);

            Score moneyValue = objective.getScore("→ §a" + getMoney(player));
            moneyValue.setScore(0);

            player.setScoreboard(board);
        }
    }

    public static String getJob(Player player)
    {
        String job = JobPROV.getCJob(player);
        int Joblvl = JobPROV.get_c_lvl(player, job);

        switch (job)
        {
            case "Arbeitslos":
                return job;

            case "miner":
                job = "Miner";
                return job + "§7(" + Joblvl + ")";

            case "lumberjack":
                job = "Lumberjack";
                return job + "§7(" + Joblvl + ")";

            case "farmer":
                job = "Farmer";
                return job + "§7(" + Joblvl + ")";
        }
        return job + "§7(" + Joblvl + ")";
    }

    public static String getRank(Player player) {

        if (player.getName().equals("Jalmo12"))
            return "§cInhaber";

        if (player.getName().equals("SirSwiperlpp"))
            return "§5Dev";

        return "Spieler";
    }

    public static String getPlaytime(Player player) {

        int playedH = PlaytimeAPI.getPlayerHours(plugin, player.getUniqueId());
        int playedM = PlaytimeAPI.getPlayerMinutes(plugin, player.getUniqueId());

        if (playedH == 0 && playedM == 0)
        {
            return "0min";
        }

        if (playedH == 0)
        {
            return playedM + "min";
        }
        return playedH + "h";
    }

    public static String getMoney(Player player) {

        String playername = player.getName();
        int money = 0;
        try {
            money = EcoPROV.getMoney(playername);
            if (money == -1)
            {
                return "§cERROR";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            String value = String.valueOf(EcoPROV.getMoney(playername));
            return value + "$";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}

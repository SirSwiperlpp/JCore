package de.sirswiperlpp.jcore.API;

import de.sirswiperlpp.jcore.Lang.Language;
import de.sirswiperlpp.jcore.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.io.File;

public class ScoreboardAPI
{

    static Language language = new Language(new File(Main.getInstance().getDataFolder(), "lang.ini"));

    public static void createScoreboard(Player player) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();

        Objective objective = board.registerNewObjective("jalmo", "dummy", "§dplay.jalmo12.de");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score blank0 = objective.getScore(" ");
        blank0.setScore(8);

        Score rang = objective.getScore(language.get("rang.ttl"));
        rang.setScore(7);

        Score rangValue = objective.getScore("→ §7§l" + getRank(player));
        rangValue.setScore(6);

        Score blank1 = objective.getScore("  ");
        blank1.setScore(5);

        Score spielzeit = objective.getScore(language.get("playtime.ttl"));
        spielzeit.setScore(4);

        Score spielzeitValue = objective.getScore("→ §7§l" + getPlaytime(player));
        spielzeitValue.setScore(3);

        Score blank2 = objective.getScore("   ");
        blank2.setScore(2);

        Score money = objective.getScore(language.get("money.ttl"));
        money.setScore(1);

        Score moneyValue = objective.getScore("→ §7§l" + getMoney(player));
        moneyValue.setScore(0);

        player.setScoreboard(board);
    }

    public static void updateScoreboards() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            ScoreboardManager manager = Bukkit.getScoreboardManager();
            Scoreboard board = manager.getNewScoreboard();

            Objective objective = board.registerNewObjective("jalmo", "dummy", "§x§5§6§0§C§A§Cp§x§5§C§0§C§A§Cl§x§6§3§0§D§A§Ca§x§6§9§0§D§A§Cy§x§7§0§0§E§A§C.§x§7§6§0§E§A§Cj§x§7§D§0§F§A§Ca§x§8§3§0§F§A§Cl§x§8§9§0§F§A§Cm§x§9§0§1§0§A§Co§x§9§6§1§0§A§C1§x§9§D§1§1§A§C2§x§A§3§1§1§A§C.§x§A§A§1§2§A§Cd§x§B§0§1§2§A§Ce");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);

            Score blank0 = objective.getScore(" ");
            blank0.setScore(8);

            Score rang = objective.getScore("§x§5§6§0§C§A§CR§x§7§4§0§E§A§Ca§x§9§2§1§0§A§Cn§x§B§0§1§2§A§Cg");
            rang.setScore(7);

            Score rangValue = objective.getScore("→ §7§l" + getRank(player));
            rangValue.setScore(6);

            Score blank1 = objective.getScore("  ");
            blank1.setScore(5);

            Score spielzeit = objective.getScore("§x§5§6§0§C§A§CS§x§6§1§0§D§A§Cp§x§6§D§0§E§A§Ci§x§7§8§0§E§A§Ce§x§8§3§0§F§A§Cl§x§8§E§1§0§A§Cz§x§9§A§1§1§A§Ce§x§A§5§1§1§A§Ci§x§B§0§1§2§A§Ct");
            spielzeit.setScore(4);

            Score spielzeitValue = objective.getScore("→ §7§l" + getPlaytime(player));
            spielzeitValue.setScore(3);

            Score blank2 = objective.getScore("   ");
            blank2.setScore(2);

            Score money = objective.getScore("§x§5§6§0§C§A§CM§x§6§D§0§E§A§Co§x§8§3§0§F§A§Cn§x§9§A§1§1§A§Ce§x§B§0§1§2§A§Cy");
            money.setScore(1);

            Score moneyValue = objective.getScore("→ §7§l" + getMoney(player));
            moneyValue.setScore(0);

            player.setScoreboard(board);
        }
    }


    public static String getRank(Player player) {
        // Implementiere Logik zum Abrufen des Rangs
        return "Spieler";
    }

    public static String getPlaytime(Player player) {
        // Implementiere Logik zum Abrufen der Spielzeit
        return "0h";
    }

    public static String getMoney(Player player) {
        // Implementiere Logik zum Abrufen des Geldes
        return "1000$";
    }


}

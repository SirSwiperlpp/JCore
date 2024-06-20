package de.sirswiperlpp.jcore.API;


import de.sirswiperlpp.jcore.Lang.Language;
import de.sirswiperlpp.jcore.Main.Main;
import de.sirswiperlpp.jcore.Provider.EcoPROV;
import org.bukkit.entity.Player;

import java.io.File;
import java.sql.SQLException;

public class EcoAPI
{
    static Language language = new Language(new File(Main.getInstance().getDataFolder(), "lang.ini"));

    public static void addBalance(Player player, int value) throws SQLException
    {
        if (EcoPROV.getMoney(player.getName()) == -1)
        {
            EcoPROV.regiPlayer(player);
            //SECURITY CHECK
            if (EcoPROV.getMoney(player.getName()) == -1)
            {
                player.sendMessage(language.get("prefix") + language.get("err.trans.canceled"));
                return;
            }
            //SECURITY CHECK

            if (value < 0)
            {
                player.sendMessage(language.get("prefix") + language.get("err.trans.negative"));
                return;
            }

            int currentBalance = EcoPROV.getMoney(player.getName());
            int updateValue = currentBalance + value;
            EcoPROV.updatePlayer(player, updateValue);
            return;
        }

        if (value < 0)
        {
            player.sendMessage(language.get("prefix") + language.get("err.trans.negative"));
            return;
        }

        int currentBalance = EcoPROV.getMoney(player.getName());
        int updateValue = currentBalance + value;
        EcoPROV.updatePlayer(player, updateValue);
    }

    public static void removeBalance(Player player, int value) throws SQLException
    {
        if (EcoPROV.getMoney(player.getName()) == -1)
        {
            EcoPROV.regiPlayer(player);
            //SECURITY CHECK
            if (EcoPROV.getMoney(player.getName()) == -1)
            {
                player.sendMessage(language.get("prefix") + language.get("err.trans.canceled"));
                return;
            }
            //SECURITY CHECK

            if (value < 0)
            {
                player.sendMessage(language.get("prefix") + language.get("err.trans.negative"));
                return;
            }

            int currentBalance = EcoPROV.getMoney(player.getName());
            int updateValue = currentBalance - value;
            EcoPROV.updatePlayer(player, updateValue);
            return;
        }

        if (value < 0)
        {
            player.sendMessage(language.get("prefix") + language.get("err.trans.negative"));
            return;
        }

        int currentBalance = EcoPROV.getMoney(player.getName());
        int updateValue = currentBalance - value;
        EcoPROV.updatePlayer(player, updateValue);
    }

    public static void setBalance(Player player, int value) throws SQLException {
        if (EcoPROV.getMoney(player.getName()) == -1)
        {
            EcoPROV.regiPlayer(player);
            //SECURITY CHECK
            if (EcoPROV.getMoney(player.getName()) == -1)
            {
                player.sendMessage(language.get("prefix") + language.get("err.trans.canceled"));
                return;
            }
            //SECURITY CHECK
        }
        EcoPROV.updatePlayer(player, value);
    }

}

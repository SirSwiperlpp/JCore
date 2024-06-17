package de.sirswiperlpp.jcore.API;

import de.sirswiperlpp.jcore.Lang.Language;
import de.sirswiperlpp.jcore.Main.Main;
import de.sirswiperlpp.jcore.Provider.TpaPROV;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.sql.SQLException;
import java.util.Objects;

public class TpaAPI
{
    static Language language = new Language(new File(Main.getInstance().getDataFolder(), "lang.ini"));

    public static void approveTp(Player target, Player sender) throws SQLException
    {
        String senderName = sender.getName();
        if (Objects.equals(TpaPROV.getRQST(sender.getName()), target.getName()))
        {
            Location playerPos = sender.getLocation();
            target.sendMessage(language.get("prefix") + language.translateString("rqst.approved", sender.getName()));
            target.teleport(playerPos);
            sender.sendMessage(language.get("prefix") + language.get("rqst.approved.sender"));
            TpaPROV.removeRQST(sender.getName());
        } else {
            sender.sendMessage(language.get("prefix") + "§cABORTING! §8> §7FAILED_TO_VERIFY");
            return;
        }
    }

    public static void denyTp(Player target, Player sender) throws SQLException
    {
        if (Objects.equals(TpaPROV.getRQST(sender.getName()), target.getName()))
        {
            target.sendMessage(language.get("prefix") + language.translateString("rqst.denied", sender.getName()));
            TpaPROV.removeRQST(sender.getName());
            sender.sendMessage(language.get("prefix") + language.translateString("rqst.denied.sender"));
            return;
        }
        sender.sendMessage(language.get("prefix") + "§cABORTING! §8| §7FAILED_TO_VERIFY");
        return;
    }

    public static void sendRQST(Player target, Player sender) throws SQLException
    {
        if (Objects.equals(TpaPROV.getRQST(sender.getName()), target.getName()))
        {
            sender.sendMessage(language.get("prefix") + language.translateString("rqst.alrdy.out", target.getName()));
            return;
        }
        TpaPROV.regiRQST(target.getName(), sender.getName());
        target.sendMessage(language.get("prefix") + language.translateString("incoming.rqst", sender.getName()));
    }

}

package de.sirswiperlpp.jcore.API;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import de.sirswiperlpp.jcore.Main.Main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class PlaytimeAPI
{
    public static int getPlayerMinutes(Main plugin, UUID playerId)
    {
        File playerFile = new File(plugin.getDataFolder(), playerId.toString() + ".json");
        if (!playerFile.exists()) {
            return 0;
        }

        try {
            byte[] bytes = Files.readAllBytes(Paths.get(playerFile.getPath()));
            String json = new String(bytes);
            JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);
            return jsonObject.get("minutes").getAsInt();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int getPlayerHours(Main plugin, UUID playerId) {
        File playerFile = new File(plugin.getDataFolder(), playerId.toString() + ".json");
        if (!playerFile.exists()) {
            return 0;
        }

        try {
            byte[] bytes = Files.readAllBytes(Paths.get(playerFile.getPath()));
            String json = new String(bytes);
            JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);
            return jsonObject.get("hours").getAsInt();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }




}

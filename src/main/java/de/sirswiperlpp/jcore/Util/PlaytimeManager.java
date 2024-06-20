package de.sirswiperlpp.jcore.Util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import de.sirswiperlpp.jcore.Main.Main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlaytimeManager
{

    private final Main plugin;
    private final Map<UUID, Integer> playTimes = new HashMap<>();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public PlaytimeManager(Main plugin) {
        this.plugin = plugin;
    }

    public void incrementPlayTime(UUID playerId) {
        playTimes.put(playerId, playTimes.getOrDefault(playerId, 0) + 1);
    }

    public void saveAllPlayTimes() {
        for (UUID playerId : playTimes.keySet()) {
            savePlayTime(playerId);
        }
    }

    private void savePlayTime(UUID playerId) {
        File playerFile = new File(plugin.getDataFolder(), playerId.toString() + ".json");

        try {
            if (!plugin.getDataFolder().exists()) {
                plugin.getDataFolder().mkdirs();
            }
            if (!playerFile.exists()) {
                playerFile.createNewFile();
            }

            try (FileWriter writer = new FileWriter(playerFile)) {
                JsonObject jsonObject = new JsonObject();
                int minutes = playTimes.get(playerId);
                int hours = minutes / 60;
                int remainingMinutes = minutes % 60;
                jsonObject.addProperty("hours", hours);
                jsonObject.addProperty("minutes", remainingMinutes);
                writer.write(gson.toJson(jsonObject));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadAllPlayTimes() {
        File dataFolder = plugin.getDataFolder();
        if (!dataFolder.exists()) {
            return;
        }

        File[] playerFiles = dataFolder.listFiles((dir, name) -> name.endsWith(".json"));
        if (playerFiles != null) {
            for (File playerFile : playerFiles) {
                try {
                    UUID.fromString(playerFile.getName().replace(".json", ""));  // Validate the filename as a UUID
                    loadPlayTime(playerFile);
                } catch (IllegalArgumentException e) {
                    plugin.getLogger().warning("Skipping invalid UUID file: " + playerFile.getName());
                }
            }
        }
    }

    private void loadPlayTime(File playerFile) {
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(playerFile.getPath()));
            String json = new String(bytes);
            JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
            UUID playerId = UUID.fromString(playerFile.getName().replace(".json", ""));
            int hours = jsonObject.get("hours").getAsInt();
            int minutes = jsonObject.get("minutes").getAsInt();
            playTimes.put(playerId, hours * 60 + minutes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

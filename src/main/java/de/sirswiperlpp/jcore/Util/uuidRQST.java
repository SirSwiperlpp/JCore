package de.sirswiperlpp.jcore.Util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.bukkit.entity.Player;
import org.json.JSONObject;

public class uuidRQST
{

    public static String getUUIDFromName(String playerName) throws Exception
    {
        String url = "https://api.mojang.com/users/profiles/minecraft/" + playerName;
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            return null;
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        conn.disconnect();

        JSONObject json = new JSONObject(content.toString());
        return json.getString("id");
    }

}

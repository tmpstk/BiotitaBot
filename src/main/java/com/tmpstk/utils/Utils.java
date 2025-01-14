package com.tmpstk.utils;

import net.dv8tion.jda.api.JDA;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils {
    private static JDA jda;
    private static String serverId;

    public static String getPrefix(){
        return jda.getSelfUser().getAsMention();
    }

    public static String getMyServerId() {
        return serverId;
    }

    private static String getServerIdFromConfig(String path) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(path)));
        JSONObject json = new JSONObject(content);
        return json.getString("serverid");
    }

    public void receiveJDA(JDA jda) throws IOException {
        Utils.jda = jda;
        serverId = getServerIdFromConfig("src/main/resources/config.json");
    }
}

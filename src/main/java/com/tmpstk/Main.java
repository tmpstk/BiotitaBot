package com.tmpstk;

import com.tmpstk.utils.Utils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.json.JSONObject;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    private static final StartThings startThings = new StartThings();
    private static final Utils utils = new Utils();

    public static void main(String[] args) throws LoginException, IOException {
        try{
            String configPath = "src/main/resources/config.json";
            String token = getTokenFromConfig(configPath);

            JDA jda = JDABuilder.createDefault(token).build();

            startThings.initialize(jda);
            utils.receiveJDA(jda);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getTokenFromConfig(String path) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(path)));
        JSONObject json = new JSONObject(content);
        return json.getString("token");
    }
}
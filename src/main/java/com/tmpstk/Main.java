package com.tmpstk;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class Main {
    private static final StartThings startThings = new StartThings();

    public static void main(String[] args) throws LoginException {
        JDA jda = JDABuilder.createDefault("").enableIntents(GatewayIntent.MESSAGE_CONTENT).build();

        startThings.initialize(jda);
    }
}
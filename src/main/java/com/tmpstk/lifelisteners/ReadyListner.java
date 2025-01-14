package com.tmpstk.lifelisteners;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ReadyListner extends ListenerAdapter {

    @Override
    public void onReady(ReadyEvent event) {
        JDA jda = event.getJDA();
        int guildCount = jda.getGuilds().size();
        printStartupMessage();
        for (Guild guild : jda.getGuilds()) {
            System.out.print(guild.getName() + ", ");
        }
        System.out.println("\nIm on " + guildCount + " guilds.");
    }


    private void printStartupMessage() {
        String startupMessage = """
                *****************************************
                *                                       *
                *          BiotitaBot Logged On         *
                *                                       *
                *****************************************
                """;

        System.out.println(startupMessage);
    }
}

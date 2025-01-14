package com.tmpstk.lifelisteners;

import net.dv8tion.jda.api.events.session.ShutdownEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ShutdownListner extends ListenerAdapter {
    @Override
    public void onShutdown(ShutdownEvent event) {
        printShutdownMessage();
    }

    private void printShutdownMessage() {
        String shutdownMessage = """
                *****************************************
                *                                       *
                *          BiotitaBot Logged Off        *
                *                                       *
                *****************************************
                """;

        System.out.println(shutdownMessage);
    }
}

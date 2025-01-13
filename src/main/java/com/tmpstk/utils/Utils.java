package com.tmpstk.utils;

import net.dv8tion.jda.api.JDA;

public class Utils {
    private static JDA jda;

    public static String getPrefix(){
        return jda.getSelfUser().getAsMention();
    }

    public void receiveJDA(JDA jda) {
        Utils.jda = jda;
    }
}

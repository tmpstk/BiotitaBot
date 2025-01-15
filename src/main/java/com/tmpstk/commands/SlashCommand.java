package com.tmpstk.commands;

import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public interface SlashCommand {
    SlashCommandData getCommandData();
}

package com.tmpstk.commands.admin.bot;

import com.tmpstk.commands.SlashCommand;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public class StatusCmd extends ListenerAdapter implements SlashCommand {
    @Override
    public SlashCommandData getCommandData() {
        return Commands.slash("status", "Changes the current status of the bot.")
                .addOptions(new OptionData(OptionType.STRING, "status", "The status of the bot.")
                        .addChoice("Online", "online")
                        .addChoice("Offline", "offline")
                        .addChoice("Idle", "idle")
                        .addChoice("DoNotDisturb", "donotdisturb"))
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR));
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("status")) {
             if (event.getOption("status") != null) {
                 if (event.getOption("status").getAsString().equalsIgnoreCase("Online")) {
                     event.getJDA().getPresence().setStatus(OnlineStatus.ONLINE);
                 } else if (event.getOption("status").getAsString().equalsIgnoreCase("Offline")) {
                     event.getJDA().getPresence().setStatus(OnlineStatus.INVISIBLE);
                 } else if (event.getOption("status").getAsString().equalsIgnoreCase("Idle")) {
                     event.getJDA().getPresence().setStatus(OnlineStatus.IDLE);
                 } else if (event.getOption("status").getAsString().equalsIgnoreCase("DoNotDisturb")) {
                     event.getJDA().getPresence().setStatus(OnlineStatus.DO_NOT_DISTURB);
                 }
                 System.out.println("Changed the current status of the bot to: " + event.getOption("status").getAsString());
                 event.reply("Changed the current status of the bot to: " + event.getOption("status").getAsString()).setEphemeral(true).queue();
             } else {
                 event.reply("A status option is required for this command").setEphemeral(true).queue();
             }
        }
    }
}

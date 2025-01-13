package com.tmpstk.commands;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class gameNumber extends ListenerAdapter
{
    private final Map<String, GameState> gameStates = new HashMap<>();
    private final Map<MessageChannel, GameState> channels = new HashMap<>();

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        if (event.getAuthor().isBot()) return;

        Message message = event.getMessage();
        String content = message.getContentRaw();
        String userid = message.getAuthor().getId();
        MessageChannel channel = event.getChannel();

        if (content.equals(">game number")) {
            if (channels.containsKey(channel)) return;
            startGame(channel, userid);
            channels.put(channel, gameStates.get(userid));
        } else if (gameStates.containsKey(userid)) {
            processGuess(channel, userid, content);
        }
    }

    private void processGuess(MessageChannel channel, String userid, String content) {
        GameState gameState = gameStates.get(userid);

        try {
            int guess = Integer.parseInt(content);
            if (guess < 1 || guess > 150) {
                channel.sendMessage("Please, guess a number between 1 and 150").queue();
                gameState.resetTimeout(() -> timeoutGame(channel, userid));
                return;
            }
            gameState.resetTimeout(() -> timeoutGame(channel, userid));

            if (guess == gameState.randomNumber) {
                Consumer<Message> callback = (response) -> System.out.printf("User:" + userid + " on channel:" + channel.getName() + " won a game number"  + "\n");
                channel.sendMessage("Congratulations! The number was " + gameState.randomNumber + ".").queue(callback);
                gameState.cancelTimeout();
                gameStates.remove(userid);
                channels.remove(channel);
            } else if (guess > gameState.randomNumber) {
                gameState.attempts--;
                if (gameState.attempts == 0) {
                    Consumer<Message> callback = (response) -> System.out.printf("User:" + userid + " on channel:" + channel.getName() + " lost a game number"  + "\n");
                    channel.sendMessage("You lost! The number was " + gameState.randomNumber + ".").queue(callback);
                    gameState.cancelTimeout();
                    gameStates.remove(userid);
                    channels.remove(channel);
                    return;
                }
                channel.sendMessage("The number to guess is lower, " + gameState.attempts + " attempts left.").queue();
            } else {
                gameState.attempts--;
                if (gameState.attempts == 0) {
                    Consumer<Message> callback = (response) -> System.out.printf("User:" + userid + " on channel:" + channel.getName() + " lost a game number"  + "\n");
                    channel.sendMessage("You lost! The number was " + gameState.randomNumber + ".").queue(callback);
                    gameState.cancelTimeout();
                    gameStates.remove(userid);
                    channels.remove(channel);
                    return;
                }
                channel.sendMessage("The number to guess is higher, " + gameState.attempts + " attempts left.").queue();
            }

        } catch (NumberFormatException e) {
            System.out.println(e.getMessage() + "\n");
        }
    }

    private void startGame(MessageChannel channel, String userid) {
        int randomNumber = (int) (Math.random() * 150) + 1;
        gameStates.put(userid, new GameState(randomNumber, 5));
        GameState gameState = gameStates.get(userid);

        Consumer<Message> callback = (response) -> System.out.printf("Started a game number for user:" + userid + " on channel:" + channel.getName());
        channel.sendMessage("Guess the number between 1 and 150, 5 Attempts").queue(callback);

        gameState.resetTimeout(() -> timeoutGame(channel, userid));
    }

    private void timeoutGame(MessageChannel channel, String userid) {
        if (channels.containsKey(channel)) {
            GameState gameState = gameStates.get(userid);
            channel.sendMessage("Time's up! The game has ended. The number was " + gameState.randomNumber + ".").queue();
            gameStates.remove(userid);
            channels.remove(channel);
        }
    }

    private static class GameState {
        int randomNumber;
        int attempts;
        private ScheduledFuture<?> timeoutTask;

        GameState(int randomNumber, int attempts) {
            this.randomNumber = randomNumber;
            this.attempts = attempts;
        }

        void resetTimeout(Runnable timeoutAction) {
            cancelTimeout();
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            this.timeoutTask = scheduler.schedule(timeoutAction, 30, TimeUnit.SECONDS);
        }

        void cancelTimeout() {
            if (timeoutTask != null && !timeoutTask.isDone()) {
                timeoutTask.cancel(true);
            }
        }
    }
}
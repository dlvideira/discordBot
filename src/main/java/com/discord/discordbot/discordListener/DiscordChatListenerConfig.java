package com.discord.discordbot.discordListener;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.security.auth.login.LoginException;

@Component
public class DiscordChatListenerConfig implements CommandLineRunner {
    @Value("${discordBot.token}")
    private String discordBotToken;

    @Autowired
    DiscordChatListener discordChatListener;

    @Override
    public void run(String... args) throws Exception {
        startListen();
    }

    public void startListen() throws LoginException {
        JDA jda = JDABuilder.createDefault(discordBotToken).build();
        jda.addEventListener(discordChatListener);
    }
}

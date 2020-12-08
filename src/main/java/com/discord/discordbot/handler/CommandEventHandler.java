package com.discord.discordbot.handler;

import com.discord.discordbot.model.command.generic.GenericCommandEvent;
import com.discord.discordbot.processor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CommandEventHandler {

    @Autowired
    TranslateCommandProcessor translateCommandProcessor;

    @Autowired
    WeatherCommandProcessor weatherCommandProcessor;

    @Autowired
    UnknownCommandProcessor unknownCommand;

    @Autowired
    ImageCommandProcessor imageCommandProcessor;

    @Autowired
    VideoCommandProcessor videoCommandProcessor;

    @Async
    @EventListener
    public void onApplicationEvent(GenericCommandEvent command) {
        var readCommand = Arrays.stream(command.getEvent().getMessage().getContentRaw().split(" ")).findFirst().get();

        switch (readCommand) {
            case "!translate" -> translateCommandProcessor.translateCommand(command);
            case "!clima" -> weatherCommandProcessor.weatherCommand(command);
            case "!imagem" -> imageCommandProcessor.imageCommand(command);
            case "!video" -> videoCommandProcessor.videoCommand(command);
            default -> unknownCommand.unknownCommand(command);
        }
    }
}

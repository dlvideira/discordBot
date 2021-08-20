package com.discord.discordbot.handler;

import com.discord.discordbot.model.command.generic.GenericCommandEvent;
import com.discord.discordbot.processor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;

@Component
public class CommandEventHandler {

    @Autowired
    private TranslateCommandProcessor translateCommandProcessor;

    @Autowired
    private WeatherCommandProcessor weatherCommandProcessor;

    @Autowired
    private UnknownCommandProcessor unknownCommand;

    @Autowired
    private ImageCommandProcessor imageCommandProcessor;

    @Autowired
    private VideoCommandProcessor videoCommandProcessor;

    @Async
    @EventListener
    public void onApplicationEvent(GenericCommandEvent command) {
        Function<GenericCommandEvent, String> getCommand = CommandEventHandler::cleanEvent;

        //TODO move
        Consumer<String> execute = (commandString) -> {
            switch (commandString) {
                case "!translate" -> translateCommandProcessor.processCommand(command);
                case "!clima" -> weatherCommandProcessor.processCommand(command);
                case "!imagem" -> imageCommandProcessor.processCommand(command);
                case "!video" -> videoCommandProcessor.processCommand(command);
                default -> unknownCommand.processCommand(command);
            }
        };
        execute.accept(getCommand.apply(command));
    }

    private static String cleanEvent(GenericCommandEvent commandString) {
        return Arrays.stream(commandString.getEvent().getMessage().getContentRaw().split(" "))
                .findFirst().orElse("Unknown");
    }
}

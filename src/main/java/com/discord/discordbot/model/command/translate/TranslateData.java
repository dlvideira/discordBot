package com.discord.discordbot.model.command.translate;

import lombok.Data;

import java.util.List;

@Data
public class TranslateData {
    private List<TranslatedText> translations;
}

package com.discord.discordbot.model.command.weather;

import lombok.Data;

@Data
public class WheaterForecast {
    private String date;
    private String weekday;
    private int max;
    private int min;
    private String description;
    private String condition;
}

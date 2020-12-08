package com.discord.discordbot.model.command.weather;

import lombok.Data;

@Data
public class Wheater {
    private String by;
    private String valid_key;
    private WeatherResult results;
    private Double execution_time;
    private boolean from_cache;
}

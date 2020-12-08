package com.discord.discordbot.model.command.weather;

import lombok.Data;

import java.util.List;

@Data
public class WeatherResult {
    private String temp;
    private String date;
    private String time;
    private String condition_code;
    private String description;
    private String currently;
    private String cid;
    private String city;
    private String img_id;
    private int humidity;
    private String wind_speedy;
    private String sunrise;
    private String sunset;
    private String condition_slug;
    private String city_name;
    private List<WheaterForecast> forecast;
}

package com.discord.discordbot.model.command.video;

import lombok.Data;

import java.util.List;

@Data
public class VideoResponse {
    private String total_results;
    private String page;
    private String url;
    private List<Video> videos;
}

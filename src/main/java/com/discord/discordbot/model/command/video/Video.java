package com.discord.discordbot.model.command.video;

import lombok.Data;

import java.util.List;

@Data
public class Video {
    private String full_res;
    private List<String> tags;
    private int id;
    private int width;
    private int height;
    private String url;
    private String image;
    private int duration;
    private VideoUser user;
    private List<VideoFile> video_files;
    private List<VideoPicture> video_pictures;
}
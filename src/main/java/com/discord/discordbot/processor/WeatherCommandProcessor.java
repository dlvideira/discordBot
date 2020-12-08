package com.discord.discordbot.processor;

import com.discord.discordbot.model.command.generic.GenericCommandEvent;
import com.discord.discordbot.model.command.weather.Wheater;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.util.Date;
import java.util.List;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.springframework.http.HttpMethod.GET;

@Component
public class WeatherCommandProcessor {
    @Value("${weather.token}")
    private String weatherToken;

    @Value("${weather.url}")
    private String weatherUrl;

    public void weatherCommand(GenericCommandEvent command) {

        List<String> message = asList(command.getEvent().getMessage().getContentRaw().split(" "));

        if (message.get(0).equals("!clima") && !command.getEvent().getAuthor().isBot()) {
            String[] city = command.getEvent().getMessage().getContentRaw().split("!clima ");
            var url = format(weatherUrl, weatherToken, city[1]);

            HttpEntity<String> request = new HttpEntity<>(getHeaders());
            RestTemplate restTemplate = new RestTemplateBuilder().build();

            ResponseEntity<Wheater> response = restTemplate.exchange(url, GET, request, Wheater.class);

            if (response.getStatusCode().equals(HttpStatus.OK)) {
                var wheather = getWeatherResult(response);
                command.getEvent().getChannel().sendMessage(wheather).queue();
            }
        }
    }

    private MessageEmbed getWeatherResult(ResponseEntity<Wheater> response) {
        var result = response.getBody().getResults();
        var forecast = result.getForecast();

        EmbedBuilder messageEmbed = new EmbedBuilder()
                .setTitle(result.getCity())
                .setDescription(format(":clock10: %s %s", result.getDate(), result.getTime()))
                .appendDescription("\n" + getWheaterCondition(result.getCondition_slug()) + result.getDescription())
                .addField("Previsão para os próximos dias", "------------------------------------", false)
                .setTimestamp(new Date().toInstant());
        if (response.getBody().isFrom_cache())
            messageEmbed.setFooter("From Cache", "https://cdn.discordapp.com/embed/avatars/4.png").setColor(Color.ORANGE);
        else
            messageEmbed.setFooter("Live Result", "https://cdn.discordapp.com/embed/avatars/2.png").setColor(Color.GREEN);
        forecast.stream().limit(9).forEach(item -> messageEmbed.addField(
                format("%s %s\n%s", item.getDate(), item.getWeekday(), getWheaterCondition(item.getCondition())),
                format(":small_red_triangle: %s\n:small_red_triangle_down: %s ", item.getMax(), item.getMin()), true));
        return messageEmbed.build();
    }

    private String getWheaterCondition(String condition) {
        String code;
        switch (condition) {
            case "cloudly_day" -> code = ":white_sun_cloud:";
            case "cloud" -> code = ":cloud:";
            case "storm" -> code = ":cloud_lightning:";
            case "rain" -> code = ":cloud_rain:";
            default -> code = ":sunny:";
        }
        return code;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", "application/json");
        headers.set("accept-encoding", "application/gzip");
        return headers;
    }
}

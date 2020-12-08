# Discord Bot 

## About the project
Project was created initially for study purposes on how to integrate with discord chat.
The main goal is create a discord bot to read chat commands used to automate routine (restar server, extract reports, etc).

It started with some API requests based on user inputs, giving response formatted into a friendly bot reply.


## API providers used in this project 

For these initial tests, the project used several api providers.
If you want to replicate the same tests, please, do the following:
(Each service needs a signup)
```
//You can use profiles or application properties to fill the necessary fields for tokens

weather:
  token: YOUR_TOKEN
  url: https://api.hgbrasil.com/weather?key=%s&city_name=%s

discordBot:
  token: YOUR_TOKEN

translate:
  token: YOUR_TOKEN
  url:  https://google-translate1.p.rapidapi.com/language/translate/v2
  host: google-translate1.p.rapidapi.com

image:
  token: YOUR_TOKEN
  url: https://api.pexels.com/v1/search?query=%s&per_page=80&page=%s
  videoUrl: https://api.pexels.com/videos/search?query=%s&per_page=80&page=%s
```

package com.example.CarDj.models;
/**************************************************************************************
The static configuration for the Youtube API.
 **************************************************************************************/
public class YoutubeConfig {
    public YoutubeConfig(){

    }
    private static final String API_KEY = "AIzaSyBqECp8c8JgCjbeOq80j3uJpHI148URZyA";
    private static final String API_ADDRESS = "https://www.googleapis.com/youtube/v3/search?part=snippet&order=rating&type=video&videoDefinition=any&videoDuration=short&videoEmbeddable=any&key=";
    public static String getApiAddress(){
        return API_ADDRESS + API_KEY + "&q=HQ Audio HD Official ";
    }
}


package com.example.CarDj.models;
/**************************************************************************************
 This Dummy song object is used for a situation that Youtube API not responding
 The screen will show results with photos of a cow.
 **************************************************************************************/
public class JsonSongDummy {

public static SongData getDummyResults(){

    SongData sd = new SongData(
            "QMtfSTOeOek",
            "Abc Rural: Recuperación de vacas con cría tras sequía en el Chaco",
            "Recuperación de vacas con cría tras sequía en el Chaco",
            "https://i.ytimg.com/vi/QMtfSTOeOek/mqdefault.jpg");

    return sd;
}

}

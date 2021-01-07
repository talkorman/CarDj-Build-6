package com.example.CarDj.models;
/**************************************************************************************
 States of the app on different actions for more accurate communication control between the fragments
 **************************************************************************************/
public enum State {
    GREETING,
    ASKING_FOR_SEARCH,
    SEARCH,
    SHOW_RESULTS,
    INTRO,
    SONG_STARTED,
    USER_SAID_YES,
    USER_SAID_NO,
    USER_DIDNT_CHOSE,
    ADDTO_PLAYLIST,
    DISPLAY_PLAYLIST,
    START,
    REJECT,
    REPEAT_RESULTS,
    DELETE_ALL,
    SONG_ENDED,
    DEVICE_ALREADY_SAID_RESULT,
    OPENING,
    USER_SPEAK,
    EMPTY_DISPLAY
}

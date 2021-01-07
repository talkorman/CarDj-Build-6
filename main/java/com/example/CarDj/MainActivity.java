 package com.example.CarDj;
/**************************************************************************************
 Main Activity is activating the dynamic fragments of Player, Play List and Results.
 **************************************************************************************/
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.CarDj.models.*;
import com.example.CarDj.models.audio_view_models.AppState;
import com.example.CarDj.models.play_lists.PlayListViewModel;
import com.example.CarDj.models.play_lists.SongsDataSource;
import com.example.CarDj.view_play_list.*;
import com.example.CarDj.view_player.*;
import com.example.CarDj.view_search_results.*;

import java.util.List;

public class MainActivity extends AppCompatActivity implements PlayerFragment.OnSongEndListener {
    //properties declarations
    AppState appState;
    State state;
    boolean userDidChoose;
    PlayListViewModel playListViewModel;
    List<SongData> songs;
    int playListIndex;
    TextView instructions;
    TextView tutorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
        //properties setup
        appState = new ViewModelProvider(this).get(AppState.class);
        playListViewModel = new ViewModelProvider(this).get(PlayListViewModel.class);
        appState.setAppState(State.INTRO);
        displaySong("J0C5IG3FCA0");
        instructions = findViewById(R.id.tvInstructions);
        tutorial = findViewById(R.id.tvTutorial);
        instructions.setVisibility(View.INVISIBLE);
        tutorial.setVisibility(View.INVISIBLE);
        appState.getCurrentState().observe(this, state->{
            System.out.println("state status: " + state);
            if(state == State.EMPTY_DISPLAY){
                closePlayer();
                instructions.setVisibility(View.VISIBLE);
                tutorial.setVisibility(View.VISIBLE);
            }
            if(state == State.START) {
                instructions.setVisibility(View.INVISIBLE);
                tutorial.setVisibility(View.INVISIBLE);
                displaySong("");
                showPlayList();
                ImageView img = findViewById(R.id.imageView);
                AlphaAnimation alpha = new AlphaAnimation(1.0f, 0.6f);
                alpha.setDuration(5000);
                alpha.setFillAfter(true);
                img.startAnimation(alpha);
                appState.getCurrentState().postValue(State.OPENING);
            }
            if(state == State.SEARCH)showSearchResults();
            if(state == State.USER_SAID_NO || state == State.USER_SAID_YES || state == State.ADDTO_PLAYLIST)
                userDidChoose = true;
            if(state == State.DEVICE_ALREADY_SAID_RESULT){
                userDidChoose = false;
                int delay = 5000;
                Handler h = new Handler();
                Runnable resultTimeWindow = new Runnable() {
                    @Override
                    public void run() {
                        if(state == State.DEVICE_ALREADY_SAID_RESULT && !userDidChoose) {
                            System.out.println("main knows that user didn't chose");
                            appState.getCurrentState().postValue(State.USER_DIDNT_CHOSE);
                        }
                    }
                };
                h.postDelayed(resultTimeWindow, delay);
            }
            if(state == State.SONG_STARTED){
                displaySong(songs.get(playListIndex).getVideoId());
            }
            if(state == State.SONG_ENDED){
                if(songs == null){
                    displaySong("");
                    appState.getCurrentState().postValue(State.OPENING);
                    return;
                }
                if(playListIndex < songs.size() - 1){
                playListIndex++;
                appState.getCurrentState().postValue(State.SONG_STARTED);
                }else {
                    appState.getCurrentState().postValue(State.OPENING);
                }
            }
        });
        appState.getSongId().observe(this, id->displaySong(id));
        appState.getSongsPlayList().observe(this, songs->{
            this.songs = songs;
            playListIndex = 0;
            appState.getCurrentState().postValue(State.SONG_STARTED);
        });
    }

    public void displaySong(String song){
        closePlayer();
        PlayerFragment player = PlayerFragment.newInstance(song);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.youtubeplayerfragment, player).commit();
    }


    @Override
    public void onSongEnd(Boolean songEnded) {
        //appState.;
        appState.getCurrentState().postValue(State.SONG_ENDED);
    }

    public void closePlayer(){
        FragmentManager fm = getSupportFragmentManager();
        PlayerFragment player = (PlayerFragment)fm.findFragmentById(R.id.youtubeplayerfragment);
        if(player != null){
            FragmentTransaction ft = fm.beginTransaction();
            ft.remove(player).commit();
        }
    }
    public void closeSearchResults(){
        FragmentManager fm = getSupportFragmentManager();
        ResultsFragment resultsFragment = (ResultsFragment) fm.findFragmentById(R.id.results_view);
        if(resultsFragment != null){
            FragmentTransaction ft = fm.beginTransaction();
            ft.remove(resultsFragment).commit();
        }
    }
    public void showSearchResults(){
        System.out.println("show results");
        closeSearchResults();
        ResultsFragment resultsFragment = ResultsFragment.newInstance();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.results_view, resultsFragment).commit();
    }

    public void showPlayList(){
        PlayListFragment playListFragment = PlayListFragment.newInstance();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.play_list_view, playListFragment).commit();
    }



}
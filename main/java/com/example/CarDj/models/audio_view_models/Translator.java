package com.example.CarDj.models.audio_view_models;
/**************************************************************************************
 Translator translates every words and centences spoken by the user and affecting the app accordingly.
 **************************************************************************************/
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.CarDj.R;
import com.example.CarDj.models.State;

public class Translator extends Fragment {
    private int countSayResults;
    private StringBuilder voice;
    private StringBuilder searchingString;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_translate, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppState appState = new ViewModelProvider(requireActivity()).get(AppState.class);
        appState.getUserSpokenText().observe(getViewLifecycleOwner(), (s)->{
            voice = new StringBuilder();
            searchingString = new StringBuilder();
            String words = s.toLowerCase();

            System.out.println(words);
            State state = appState.getCurrentState().getValue();
            if (words.contains("i want")) {
                countSayResults = 0;
                voice.setLength(0);
                searchingString.setLength(0);
                searchingString.append(words);
                searchingString.delete(0, 7);
                String item = searchingString.toString();
                item.replace(".", "/").replace(",", "/").replace(" ", "/");
                voice.append(item);
                System.out.println(item);
                appState.getDeviceTextToSay().postValue(voice.toString());
                //appState.getSearchWords().postValue(item);
                return;
            }
            if(words.contains("no") && state == State.DEVICE_ALREADY_SAID_RESULT){
                appState.getCurrentState().postValue(State.USER_SAID_NO);
                return;
            }
            if(words.contains("yes") && state == State.DEVICE_ALREADY_SAID_RESULT){
                appState.getCurrentState().postValue(State.USER_SAID_YES);
                return;
            }
            if(words.contains("ok") && state == State.DEVICE_ALREADY_SAID_RESULT){
                appState.getCurrentState().postValue(State.ADDTO_PLAYLIST);
                return;
            }
            if(words.contains("go") && state == State.OPENING){
                appState.getCurrentState().postValue(State.DISPLAY_PLAYLIST);
                return;
            }
            if(words.contains("delete all")){
                appState.getCurrentState().postValue(State.DELETE_ALL);
            }
        });
    }

}

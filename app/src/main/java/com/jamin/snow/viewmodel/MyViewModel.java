package com.jamin.snow.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.jamin.snow.base.BaseViewModel;

public class MyViewModel extends BaseViewModel {
    private MutableLiveData<Boolean> isBackgroundMusicPlaying;


    public MutableLiveData<Boolean> getBackgroundMusicPlaying() {
        if (isBackgroundMusicPlaying == null) {
            isBackgroundMusicPlaying = new MutableLiveData<>();
            isBackgroundMusicPlaying.setValue(true);
        }
        return isBackgroundMusicPlaying;
    }

    public void setBackgroundMusicPlaying(boolean playing) {
        isBackgroundMusicPlaying.setValue(playing);
    }

}

package com.jamin.snow.viewmodel;

import android.widget.ImageView;

import com.jamin.snow.base.BaseViewModel;

public class MyViewModel extends BaseViewModel {
    private boolean isBackgroundMusicPlaying = true;

    public boolean getBackgroundMusicPlaying() {
        return isBackgroundMusicPlaying;
    }

    public void setBackgroundMusicPlaying(boolean playing) {
        this.isBackgroundMusicPlaying = playing;
    }

}

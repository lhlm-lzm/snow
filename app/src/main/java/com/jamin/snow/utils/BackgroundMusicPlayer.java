package com.jamin.snow.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

public class BackgroundMusicPlayer {
    private static final String TAG = "BackgroundMusicPlayer";
    private static MediaPlayer mediaPlayer;
    private static boolean isPlaying = false;

    public static void playBackgroundMusic(Context context, int resourceId) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, resourceId);
            mediaPlayer.setLooping(true); // 循环播放
        }

        if (!isPlaying) {
            Log.e(TAG, "playBackgroundMusic: " + resourceId);
            mediaPlayer.start();
            isPlaying = true;
        }
    }

    public static void pause() {
        if (mediaPlayer != null && isPlaying) {
            Log.e(TAG, "pause background music");
            mediaPlayer.pause();
            isPlaying = false;
        }
    }

    public static void resume() {
        if (mediaPlayer != null && !isPlaying) {
            Log.e(TAG, "resume background music");
            mediaPlayer.start();
            isPlaying = true;
        }
    }

    public static void stop() {
        if (mediaPlayer != null) {
            Log.e(TAG, "stop background music");
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            isPlaying = false;
        }
    }
}


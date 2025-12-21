package com.jamin.snow.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

public class BackgroundMusicPlayer {
    private static final String TAG = "BackgroundMusicPlayer";
    private static MediaPlayer mediaPlayer;
    private static boolean isPlaying = false;
    private static int currentMusicResId = -1; // 记录当前播放的音乐资源

    public static void playBackgroundMusic(Context context, int resourceId) {
        if (mediaPlayer == null) {
            // 没有播放器，创建新的
            mediaPlayer = MediaPlayer.create(context.getApplicationContext(), resourceId);
            mediaPlayer.setLooping(true);
            currentMusicResId = resourceId;
            mediaPlayer.start();
            isPlaying = true;
            Log.e(TAG, "playBackgroundMusic: " + resourceId);
        } else {
            if (currentMusicResId != resourceId) {
                // 音乐不同，切换播放
                mediaPlayer.stop();
                mediaPlayer.release();

                mediaPlayer = MediaPlayer.create(context.getApplicationContext(), resourceId);
                mediaPlayer.setLooping(true);
                mediaPlayer.start();

                currentMusicResId = resourceId;
                isPlaying = true;
                Log.e(TAG, "switch and play background music: " + resourceId);
            } else {
                // 同一首音乐，检查是否需要恢复播放
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    isPlaying = true;
                    Log.e(TAG, "resume background music: " + resourceId);
                }
            }
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
        else {
            Log.e(TAG, "resume fail");
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


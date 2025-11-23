package com.jamin.snow;

import android.util.Log;
import android.widget.ImageView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.jamin.snow.base.BaseActivity;
import com.jamin.snow.utils.BackgroundMusicPlayer;
import com.jamin.snow.utils.MyAnimationUtils;
import com.jamin.snow.viewmodel.MyViewModel;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private ImageView background_music;
    private ImageView heart_breath;
    private ImageView heart_beat;
    private ImageView heart_float;
    private MyViewModel myViewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        heart_breath = findViewById(R.id.heart_breath);
        heart_beat = findViewById(R.id.heart_beat);
        heart_float = findViewById(R.id.heart_float);
        background_music = findViewById(R.id.background_music);
        myViewModel.getBackgroundMusicPlaying().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean playing) {
                if (playing) {
                    MyAnimationUtils.startRotationAnimation.start(background_music); // 启动旋转动画
                    BackgroundMusicPlayer.resume();
                    Log.e(TAG, "background music resume");
                } else {
                    MyAnimationUtils.startRotationAnimation.stop(background_music); // 停止旋转动画
                    BackgroundMusicPlayer.pause();
                    Log.e(TAG, "background music pause");
                }
            }
        });
        background_music.setOnClickListener(v -> {
            myViewModel.setBackgroundMusicPlaying(!myViewModel.getBackgroundMusicPlaying().getValue());
        });

        // 分别启动不同的动画
        MyAnimationUtils.HeartBeatAnimator.start(heart_beat);
        MyAnimationUtils.HeartBreathAnimator.start(heart_breath);
        MyAnimationUtils.HeartFloatAnimator.start(heart_float);

        // 启动背景音乐
        BackgroundMusicPlayer.playBackgroundMusic(this, R.raw.background_music);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (myViewModel.getBackgroundMusicPlaying().getValue()) {
            BackgroundMusicPlayer.resume();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        BackgroundMusicPlayer.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 停止背景音乐
        BackgroundMusicPlayer.stop();
        MyAnimationUtils.startRotationAnimation.stop(background_music);
    }


}
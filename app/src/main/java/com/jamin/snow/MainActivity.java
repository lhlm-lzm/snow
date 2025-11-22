package com.jamin.snow;

import android.widget.ImageView;

import com.jamin.snow.base.BaseActivity;
import com.jamin.snow.utils.MyAnimationUtils;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        ImageView heart_breath = findViewById(R.id.heart_breath);
        ImageView heart_beat = findViewById(R.id.heart_beat);
        ImageView heart_float = findViewById(R.id.heart_float);

        // 分别启动不同的动画
        MyAnimationUtils.HeartBeatAnimator.start(heart_beat);
        MyAnimationUtils.HeartBreathAnimator.start(heart_breath);
        MyAnimationUtils.HeartFloatAnimator.start(heart_float);

    }

}
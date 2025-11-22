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
        ImageView heart1 = findViewById(R.id.heart1);
        ImageView heart2 = findViewById(R.id.heart2);
        ImageView heart4 = findViewById(R.id.heart4);

        // 分别启动不同的动画
        MyAnimationUtils.HeartBeatAnimator.start(heart2);
        MyAnimationUtils.HeartBreathAnimator.start(heart1);
        MyAnimationUtils.HeartFloatAnimator.start(heart4);

    }

}
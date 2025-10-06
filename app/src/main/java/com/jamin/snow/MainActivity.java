package com.jamin.snow;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView heart1 = findViewById(R.id.heart1);
        ImageView heart2 = findViewById(R.id.heart2);
        ImageView heart4 = findViewById(R.id.heart4);

        // 分别启动不同的动画
        HeartBeatAnimator.start(heart2);
        HeartBreathAnimator.start(heart1);
        HeartFloatAnimator.start(heart4);
    }
}
package com.jamin.snow;

import android.view.View;

public class HeartBeatAnimator {

    public static void start(View view) {
        long startTime = System.currentTimeMillis();
        final float duration = 500f; // 1秒心跳周期

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                long now = System.currentTimeMillis();
                float t = ((now - startTime) % duration) / duration; // 0~1
                float scale = 1f + (1.5f - 1f) * new BreatheInterpolator().getInterpolation(t);
                view.setScaleX(scale);
                view.setScaleY(scale);
                view.postDelayed(this, 16); // 大约60fps刷新
            }
        };
        view.post(runnable);
    }
}

package com.jamin.snow;

import android.view.View;

public class HeartFloatAnimator {

    public static void start(View view) {
        long startTime = System.currentTimeMillis();
        final float duration = 500f; // 0.5秒漂浮周期

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                long now = System.currentTimeMillis();
                float t = ((now - startTime) % duration) / duration;
                float translationY = 50f * new BreatheInterpolator().getInterpolation(t);
                view.setTranslationY(translationY);
                view.postDelayed(this, 16);
            }
        };
        view.post(runnable);
    }
}

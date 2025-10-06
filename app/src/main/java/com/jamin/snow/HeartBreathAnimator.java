package com.jamin.snow;

import android.view.View;

public class HeartBreathAnimator {

    public static void start(View view) {
        long startTime = System.currentTimeMillis();
        final float duration = 2000f; // 2秒呼吸周期

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                long now = System.currentTimeMillis();
                float t = ((now - startTime) % duration) / duration;
                float alpha = 0.5f + (1f - 0.5f) * new BreatheInterpolator().getInterpolation(t);
                view.setAlpha(alpha);
                view.postDelayed(this, 16);
            }
        };
        view.post(runnable);
    }
}

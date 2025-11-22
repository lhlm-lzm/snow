package com.jamin.snow.utils;

import android.view.View;

import com.jamin.snow.BreatheInterpolator;

public class MyAnimationUtils {
    private static final String TAG = "AnimationUtils";

    // 心跳动画
    public static class HeartBeatAnimator {
        public static void start(View view) {
            long startTime = System.currentTimeMillis();
            final float duration = 500f; // 500ms// 心跳周期

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

        public static void stop(View view) {
            view.removeCallbacks(null);
        }

    }

    // 心跳呼吸动画
    public static class HeartBreathAnimator {
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

        public static void stop(View view) {
            view.removeCallbacks(null);
        }
    }

    // 心跳漂浮动画
    public static class HeartFloatAnimator {
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

        public static void stop(View view) {
            view.removeCallbacks(null);
        }
    }

}

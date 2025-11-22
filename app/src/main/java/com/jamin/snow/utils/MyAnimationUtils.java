package com.jamin.snow.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.jamin.snow.custom.CustomBreatheInterpolator;

public class MyAnimationUtils {
    private static final String TAG = "AnimationUtils";

    // 心跳动画
    public static class HeartBeatAnimator {
        public static void start(View view) {

            ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 1.5f, 1.0f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 1.5f, 1.0f);
            // 为每个动画设置重复次数
            scaleX.setRepeatCount(ValueAnimator.INFINITE);
            scaleY.setRepeatCount(ValueAnimator.INFINITE);
            scaleX.setRepeatMode(ValueAnimator.RESTART);
            scaleY.setRepeatMode(ValueAnimator.RESTART);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(scaleX, scaleY);
            animatorSet.setDuration(1200);
            animatorSet.setInterpolator(new CustomBreatheInterpolator());
            animatorSet.start();
        }

    }

    // 心跳呼吸动画
    public static class HeartBreathAnimator {
        public static void start(View view) {
            ValueAnimator animator = ValueAnimator.ofFloat(0.5f,1.0f);
            animator.setDuration(1200);
            animator.setInterpolator(new CustomBreatheInterpolator());
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setRepeatMode(ValueAnimator.RESTART);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float alpha = (Float) animation.getAnimatedValue();
                    view.setAlpha(alpha);
                }
            });
            animator.start();
        }

    }

    // 心跳漂浮动画
    public static class HeartFloatAnimator {
        public static void start(View view) {
            ObjectAnimator floatAnimator = ObjectAnimator.ofFloat(view, "translationY", 0f, 50f, 0f);
            floatAnimator.setDuration(1200);
            floatAnimator.setInterpolator(new CustomBreatheInterpolator());
            floatAnimator.setRepeatCount(ValueAnimator.INFINITE);
            floatAnimator.setRepeatMode(ValueAnimator.RESTART);
            floatAnimator.start();
        }

    }

    public static class startRotationAnimation {
        public static void start(View view) {
            stop(view);
            float currentRotation = view.getRotation();
            ValueAnimator rotationAnimator = ValueAnimator.ofFloat(currentRotation,currentRotation +  360f);
            rotationAnimator.setDuration(2000);
            rotationAnimator.setRepeatCount(ValueAnimator.INFINITE);
            rotationAnimator.setInterpolator(new LinearInterpolator());
            rotationAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (Float) animation.getAnimatedValue();
                    view.setRotation(value);
                }
            });
            rotationAnimator.start();
            view.setTag(rotationAnimator);
        }

        public static void stop(View view) {
            Object tag = view.getTag();
            if (tag instanceof ValueAnimator) {
                ((ValueAnimator) tag).cancel();
            }
        }
    }

}

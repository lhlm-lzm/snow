package com.jamin.snow.custom;

import android.animation.TimeInterpolator;

/**
 * 定义拟合呼吸变化的插值器
 */

public class CustomBreatheInterpolator implements TimeInterpolator {
    @Override
    public float getInterpolation(float input) {
        float x = 6 * input;
        float PI = 3.1416f;
        float output = 0;

        if (x >= 0 && x < 2) {
            output = (float) (0.5 * Math.sin((PI / 2) * (x - 1)) + 0.5);

        } else if (x >= 2 && x < 6) {
            output = (float) Math.pow((0.5 * Math.sin((PI / 4) * (x - 8)) + 0.5), 2);
        }
        return output;
    }
}
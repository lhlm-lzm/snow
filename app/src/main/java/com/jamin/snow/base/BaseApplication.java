package com.jamin.snow.base;

import android.app.Application;
import android.util.Log;

public class BaseApplication extends Application {
    private static BaseApplication instance;
    private static final String TAG = "BaseApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        instance = this;
        // 初始化操作
        initialize();
    }

    private void initialize() {
        // 在这里添加全局初始化代码
        // 例如：日志初始化、网络库初始化、数据库初始化等
    }

    public static BaseApplication getInstance() {
        return instance;
    }
}


package com.jamin.snow.base;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName(); // 获取实例类名
    private View rootView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate");
        // 基础初始化操作
        setContentView(getLayoutId());
        initView();
        initData();
        initListener();
    }

    /**
     * 获取布局ID
     */
    @LayoutRes
    protected abstract int getLayoutId();

    /**
     * 初始化视图
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected void initData() {
        // 子类可选择重写
    }

    /**
     * 初始化监听器
     */
    protected void initListener() {
        // 子类可选择重写
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
    }
}

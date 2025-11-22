package com.jamin.snow.base;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.concurrent.ConcurrentHashMap;

/**
 * ViewModel基类
 * 封装常用功能和状态管理
 */
public abstract class BaseViewModel extends ViewModel {

    protected final String TAG = this.getClass().getSimpleName();
    // 加载状态
    private final MutableLiveData<Boolean> mLoadingLiveData = new MutableLiveData<>();
    // 错误信息
    private final MutableLiveData<String> mErrorLiveData = new MutableLiveData<>();
    // 提示信息
    private final MutableLiveData<String> mMessageLiveData = new MutableLiveData<>();
    // 用于管理异步任务
    private final ConcurrentHashMap<String, Object> mTaskMap = new ConcurrentHashMap<>();

    /**
     * 显示加载状态
     */
    protected void showLoading() {
        mLoadingLiveData.postValue(true);
    }

    /**
     * 隐藏加载状态
     */
    protected void hideLoading() {
        mLoadingLiveData.postValue(false);
    }

    /**
     * 显示错误信息
     */
    protected void showError(String error) {
        mErrorLiveData.postValue(error);
        hideLoading();
    }

    /**
     * 显示错误信息
     */
    protected void showError(Throwable throwable) {
        String error = throwable != null ? throwable.getMessage() : "未知错误";
        showError(error);
    }

    /**
     * 显示提示信息
     */
    protected void showMessage(String message) {
        mMessageLiveData.postValue(message);
    }

    /**
     * 获取加载状态LiveData
     */
    public LiveData<Boolean> getLoadingLiveData() {
        return mLoadingLiveData;
    }

    /**
     * 获取错误信息LiveData
     */
    public LiveData<String> getErrorLiveData() {
        return mErrorLiveData;
    }

    /**
     * 获取提示信息LiveData
     */
    public LiveData<String> getMessageLiveData() {
        return mMessageLiveData;
    }

    /**
     * 添加任务到管理池
     */
    protected void addTask(String key, Object task) {
        mTaskMap.put(key, task);
    }

    /**
     * 移除任务
     */
    protected void removeTask(String key) {
        mTaskMap.remove(key);
    }

    /**
     * 获取任务
     */
    protected <T> T getTask(String key) {
        Object task = mTaskMap.get(key);
        if (task != null) {
            try {
                @SuppressWarnings("unchecked")
                T result = (T) task;
                return result;
            } catch (ClassCastException e) {
                return null;
            }
        }
        return null;
    }

    /**
     * 清除所有任务
     */
    protected void clearAllTasks() {
        mTaskMap.clear();
    }

    /**
     * 观察加载状态
     */
    public void observeLoading(LifecycleOwner owner, LoadingObserver observer) {
        mLoadingLiveData.observe(owner, observer::onLoadingChanged);
    }

    /**
     * 观察错误信息
     */
    public void observeError(LifecycleOwner owner, ErrorObserver observer) {
        mErrorLiveData.observe(owner, observer::onError);
    }

    /**
     * 观察提示信息
     */
    public void observeMessage(LifecycleOwner owner, MessageObserver observer) {
        mMessageLiveData.observe(owner, observer::onMessage);
    }

    /**
     * 观察所有状态
     */
    public void observeAll(LifecycleOwner owner,
                           LoadingObserver loadingObserver,
                           ErrorObserver errorObserver,
                           MessageObserver messageObserver) {
        if (loadingObserver != null) {
            observeLoading(owner, loadingObserver);
        }
        if (errorObserver != null) {
            observeError(owner, errorObserver);
        }
        if (messageObserver != null) {
            observeMessage(owner, messageObserver);
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // 清理资源
        clearAllTasks();
    }

    /**
     * 加载状态观察者接口
     */
    public interface LoadingObserver {
        void onLoadingChanged(Boolean isLoading);
    }

    /**
     * 错误信息观察者接口
     */
    public interface ErrorObserver {
        void onError(String error);
    }

    /**
     * 提示信息观察者接口
     */
    public interface MessageObserver {
        void onMessage(String message);
    }

    /**
     * 简化的状态观察者接口
     */
    public interface SimpleStateObserver extends LoadingObserver, ErrorObserver, MessageObserver {
    }
}

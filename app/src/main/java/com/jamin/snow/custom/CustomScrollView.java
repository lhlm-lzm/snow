package com.jamin.snow.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

public class CustomScrollView extends ScrollView {

    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 测量后判断是否可以滚动
        View child = getChildAt(0);
        if (child != null) {
            int childHeight = child.getMeasuredHeight();
            int scrollViewHeight = getMeasuredHeight();

            // 设置是否可以滚动的标志
            setCanScroll(childHeight > scrollViewHeight);
        }
    }

    private boolean canScroll = false;
    private float lastY; // 用于记录上一次的Y坐标
    private boolean isAtTop = true; // 是否在顶部
    private boolean isAtBottom = false; // 是否在底部

    private void setCanScroll(boolean canScroll) {
        this.canScroll = canScroll;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // 如果不可以滚动，则不拦截，让父容器处理
        if (!canScroll) {
            return false;
        }

        final int action = ev.getAction();
        final float y = ev.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastY = y;
                checkScrollState(); // 检查当前滚动状态
                break;

            case MotionEvent.ACTION_MOVE:
                float deltaY = y - lastY;
                lastY = y;

                // 判断滑动方向
                if (deltaY > 0) {
                    // 向下滑动
                    if (isAtTop) {
                        // 在顶部且继续下拉，不拦截
                        return false;
                    }
                } else if (deltaY < 0) {
                    // 向上滑动
                    if (isAtBottom) {
                        // 在底部且继续上拉，不拦截
                        return false;
                    }
                }
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // 如果不可以滚动，则不处理，返回false让父容器处理
        if (!canScroll) {
            return false;
        }

        final int action = ev.getAction();
        final float y = ev.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastY = y;
                checkScrollState(); // 检查当前滚动状态
                break;

            case MotionEvent.ACTION_MOVE:
                float deltaY = y - lastY;
                lastY = y;

                // 判断滑动方向
                if (deltaY > 0) {
                    // 向下滑动
                    if (isAtTop) {
                        // 在顶部且继续下拉，不消费事件
                        return false;
                    }
                } else if (deltaY < 0) {
                    // 向上滑动
                    if (isAtBottom) {
                        // 在底部且继续上拉，不消费事件
                        return false;
                    }
                }
                break;
        }

        return super.onTouchEvent(ev);
    }

    /**
     * 检查当前滚动状态
     */
    private void checkScrollState() {
        // 是否在顶部（垂直滚动位置为0）
        isAtTop = getScrollY() == 0;

        // 是否在底部
        View child = getChildAt(0);
        if (child != null) {
            int childHeight = child.getHeight();
            int scrollViewHeight = getHeight();
            int scrollY = getScrollY();

            // 滚动到底部判断：滚动距离 + ScrollView高度 >= 子视图高度
            isAtBottom = (scrollY + scrollViewHeight) >= childHeight;
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        // 滚动时更新状态
        checkScrollState();
    }

    /**
     * 手动检查是否可以垂直滚动
     */
    public boolean canScrollVertically() {
        return canScroll;
    }

    /**
     * 判断是否在顶部
     */
    public boolean isAtTop() {
        return isAtTop;
    }

    /**
     * 判断是否在底部
     */
    public boolean isAtBottom() {
        return isAtBottom;
    }
}
package com.jamin.snow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffColorFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class MyView extends View {

    private Random random = new Random();
    private List<Love> loves = new ArrayList<>();
    private Bitmap heartBitmap; // 提前加载Bitmap
    private long lastTime = 0;  // 用于计算deltaTime
    // 在类成员变量中添加
    private List<Love> temporaryLoves = new ArrayList<>();

    public MyView(Context context) {
        super(context);
        init(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        // 提前加载爱心Bitmap，避免每帧解码
        heartBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.heart);
        lastTime = System.currentTimeMillis();
    }

    // 创建新的Love对象
    private void createLoveIfNeeded() {
        if (loves.isEmpty() || loves.get(loves.size() - 1).y > 0) {
            loves.add(new Love());
        }
    }

    class Love {
        float x, y;
        float speed; // 像素/秒
        int color;
        int screenWidth = getWidth();
        static final long LIFETIME = 2000; // 2秒生命周期
        long createTime; // 创建时间

        public Love() {
            color = -256 * 256 * 256 + random.nextInt(256 * 256 * 256);
            if (screenWidth <= 0) {
                screenWidth = getResources().getDisplayMetrics().widthPixels;
            }
            x = random.nextInt(screenWidth - 50);
            y = random.nextInt(100) - 200;
            speed = 300 + random.nextInt(200); // 300~500 px/s
        }

        public Love(float x, float y) {
            color = -256 * 256 * 256 + random.nextInt(256 * 256 * 256);
            this.x = x;
            this.y = y;
            createTime = System.currentTimeMillis();
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        long currentTime = System.currentTimeMillis();
        float deltaTime = (currentTime - lastTime) / 1000f; // 秒为单位
        lastTime = currentTime;

        Paint paint = new Paint();
        createLoveIfNeeded();

        // 使用迭代器安全删除
        Iterator<Love> iterator = loves.iterator();
        while (iterator.hasNext()) {
            Love love = iterator.next();
            paint.setColorFilter(new PorterDuffColorFilter(love.color, android.graphics.PorterDuff.Mode.SRC_ATOP));
            canvas.drawBitmap(heartBitmap, love.x, love.y, paint);

            // 根据时间差计算位移
            love.y += love.speed * deltaTime;

            // 超出屏幕高度删除
            if (love.y > getHeight()) {
                iterator.remove();
            }
        }

        // 绘制临时点击爱心
        Iterator<Love> tempIterator = temporaryLoves.iterator();
        while (tempIterator.hasNext()) {
            Love tempLove = tempIterator.next();
            // 改进的透明度计算逻辑
            long elapsedTime = System.currentTimeMillis() - tempLove.createTime;
            int alpha = 255;
            // 防止超时后还继续绘制
            if (elapsedTime >= Love.LIFETIME) {
                tempIterator.remove();
                continue;
            }
            // 渐显效果 (前500ms)
            if (elapsedTime < 500) {
                alpha = (int) (255 * (elapsedTime / 500.0f));
            }
            // 渐隐效果 (最后500ms)
            else if (elapsedTime > (Love.LIFETIME - 500)) {
                long fadeTime = Love.LIFETIME - elapsedTime;
                if (fadeTime > 0) {  // 添加边界检查
                    alpha = (int) (255 * (fadeTime / 500.0f));
                } else {
                    alpha = 0;
                }
            }
            // 设置画笔透明度
            paint.setAlpha(alpha);
            paint.setColorFilter(new PorterDuffColorFilter(tempLove.color, android.graphics.PorterDuff.Mode.SRC_ATOP));
            canvas.drawBitmap(heartBitmap, tempLove.x, tempLove.y, paint);
        }

        // 继续刷新
        postInvalidateOnAnimation(); // 更平滑，适配不同帧率
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // 创建一个临时爱心，在指定位置显示2秒后消失
            Love tempLove = new Love(event.getX(), event.getY());
            temporaryLoves.add(tempLove);
            invalidate();
            return true;
        }
        return super.onTouchEvent(event);
    }

}

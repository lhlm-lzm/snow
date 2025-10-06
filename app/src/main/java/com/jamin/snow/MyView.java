package com.jamin.snow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffColorFilter;
import android.util.AttributeSet;
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

        public Love() {
            color = -256 * 256 * 256 + random.nextInt(256 * 256 * 256);
            x = random.nextInt(1000);
            y = random.nextInt(100) - 200;
            speed = 300 + random.nextInt(200); // 300~500 px/s
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

        // 继续刷新
        postInvalidateOnAnimation(); // 更平滑，适配不同帧率
    }
}

package com.jamin.snow;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.jamin.snow.custom.StoryPagerAdapter;
import com.jamin.snow.utils.BackgroundMusicPlayer;
import com.jamin.snow.utils.MyAnimationUtils;
import com.jamin.snow.viewmodel.MyViewModel;

import java.util.ArrayList;
import java.util.List;

public class StoryActivity extends AppCompatActivity {
    private static final String TAG = "StoryActivity";
    private MyViewModel myViewModel;
    private ImageView background_music;
    private ViewPager2 viewPager;
    private ImageView ivScrollHint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        viewPager = findViewById(R.id.view_pager);
        ivScrollHint = findViewById(R.id.iv_scroll_hint);
        MyAnimationUtils.startScrollHintAnimation(ivScrollHint);
        viewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        background_music = findViewById(R.id.background_music); // 初始化音乐图标
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);

        myViewModel.getBackgroundMusicPlaying().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean playing) {
                if (playing) {
                    MyAnimationUtils.startRotationAnimation.start(background_music); // 启动旋转动画
                    BackgroundMusicPlayer.resume();
                    Log.e(TAG, "background music resume");
                } else {
                    MyAnimationUtils.startRotationAnimation.stop(background_music); // 停止旋转动画
                    BackgroundMusicPlayer.pause();
                    Log.e(TAG, "background music pause");
                }
            }
        });

        // 设置音乐图标点击事件
        background_music.setOnClickListener(v -> {
            myViewModel.setBackgroundMusicPlaying(!myViewModel.getBackgroundMusicPlaying().getValue());
        });

        Log.e(TAG, "background music play");
        BackgroundMusicPlayer.playBackgroundMusic(getApplicationContext(), R.raw.background_music_02);
        List<StoryPage> pages = new ArrayList<>();

        pages.add(new StoryPage(
                R.drawable.q_001,
                null,
                getString(R.string.story_title_1),
                getString(R.string.story_content_1)
        ));

        pages.add(new StoryPage(
                R.drawable.q_002,
                null,
                null,
                getString(R.string.story_content_2)
        ));

        pages.add(new StoryPage(
                R.drawable.q_003,
                R.drawable.q_004,
                null,
                getString(R.string.story_content_3)
        ));

        StoryPagerAdapter adapter = new StoryPagerAdapter(pages);
        viewPager.setAdapter(adapter);
        viewPager.registerOnPageChangeCallback(
                new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        if (position == adapter.getItemCount() - 1) {
                            ivScrollHint.setVisibility(View.GONE);
                        } else {
                            ivScrollHint.setVisibility(View.VISIBLE);
                        }
                    }
                }
        );

    }

    public class StoryPage {

        public Integer imgTopRes;
        public Integer imgBottomRes; // 可为空
        public String title;
        public String content;

        public StoryPage(int imgTopRes, Integer imgBottomRes,
                         String title, String content) {
            this.imgTopRes = imgTopRes;
            this.imgBottomRes = imgBottomRes;
            this.title = title;
            this.content = content;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (myViewModel.getBackgroundMusicPlaying().getValue()) {
            BackgroundMusicPlayer.resume();
        }
        new Handler().postDelayed(() -> {
            Log.e(TAG, "2s runnable");
            if (myViewModel.getBackgroundMusicPlaying().getValue()) {
                Log.e(TAG, "resume background music");
                BackgroundMusicPlayer.resume();
            }
        }, 2000); // 延迟2秒执行
    }

    @Override
    protected void onStop() {
        super.onStop();
//        BackgroundMusicPlayer.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 停止背景音乐
        BackgroundMusicPlayer.pause();
        MyAnimationUtils.startRotationAnimation.stop(background_music);
    }


}

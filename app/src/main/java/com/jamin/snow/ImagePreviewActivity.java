package com.jamin.snow;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class ImagePreviewActivity extends AppCompatActivity {

    public static final String TAG = "ImagePreviewActivity";

    public static final String EXTRA_IMAGE_RES = "image_res";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);

        PhotoView photoView = findViewById(R.id.photoView);

        int resId = getIntent().getIntExtra(EXTRA_IMAGE_RES, -1);
        if (resId != -1) {
            photoView.setImageResource(resId);
        }

        // 单击退出
        photoView.setOnClickListener(v -> finish());

        // 长按保存
        photoView.setOnLongClickListener(v -> {
//            new AlertDialog.Builder(this)
//                    .setItems(new String[]{"保存图片"}, (dialog, which) -> {
//                        if (which == 0) {
//                            saveImageToGallery(resId);
//                        }
//                    })
//                    .show();
//            return true;

            showSaveImageDialog(resId);
            return true;
        });
    }

    private void showSaveImageDialog(int resId) {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = getLayoutInflater()
                .inflate(R.layout.dialog_save_image, null);

        view.findViewById(R.id.btn_save_image).setOnClickListener(v -> {
            saveImageToGallery(resId);
            dialog.dismiss();
        });

        dialog.setContentView(view);
        dialog.show();
    }


    private void saveImageToGallery(int resId) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resId);

        String savedImageURL = MediaStore.Images.Media.insertImage(
                getContentResolver(),
                bitmap,
                "story_image_" + System.currentTimeMillis(),
                "Saved from story"
        );

        Toast.makeText(this,
                savedImageURL != null ? "图片已保存" : "保存失败",
                Toast.LENGTH_SHORT).show();
    }

    // 确保在 ImagePreviewActivity 中也正确处理了退场动画
    @Override
    public void finish() {
        super.finish();
        Log.e(TAG, "finish");
        // 如果需要自定义退场动画，可以在这里添加
//        overridePendingTransition(R.anim.scale_center_enter, R.anim.scale_center_exit);
    }

}

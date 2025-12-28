package com.jamin.snow.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jamin.snow.R;
import com.jamin.snow.StoryActivity;

public class DialogUtils {

    private static final String TAG = "DialogUtils";
    private static long lastClickTime = 0;
    private static boolean isFastClick = false;

    public static void showDialog(Context context) {
        // 创建 AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // 创建自定义布局
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog, null);

        // 获取布局中的视图组件
        TextView titleText = dialogView.findViewById(R.id.dialog_title);
        EditText nameInput = dialogView.findViewById(R.id.name_input);
        Button cancelButton = dialogView.findViewById(R.id.btn_cancel);
        Button confirmButton = dialogView.findViewById(R.id.btn_confirm);

        // 设置标题文本
        titleText.setText("请输入你的姓名");

        // 构建对话框
        AlertDialog dialog = builder.setView(dialogView).create();

        // 设置取消按钮点击事件
        cancelButton.setOnClickListener(v -> {
            dialog.dismiss();
        });

        // 设置确定按钮点击事件
        confirmButton.setOnClickListener(v -> {
            String inputName = nameInput.getText().toString().trim();
            if (!TextUtils.isEmpty(inputName)) {
                // 处理输入的姓名（可根据实际需求修改）
                Log.e(TAG, "输入的姓名: " + inputName);
                if(inputName.equals(context.getString(R.string.user_name))) {
                    // 打开另一个 Activity
                    Log.e(TAG, "打开第二个Activity");
                    Intent intent = new Intent(context, StoryActivity.class);
                    intent.putExtra("user_name", inputName);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "你好，" + inputName, Toast.LENGTH_SHORT).show();
                }
                Log.e(TAG, "dialog.dismiss()");
                dialog.dismiss();
            } else {
                // 输入为空时提示用户
                Toast.makeText(context, "请输入姓名", Toast.LENGTH_SHORT).show();
            }
        });
        Log.e(TAG, "showDialog: ");
        // 显示对话框
        dialog.show();
    }

    public static boolean isFastClick() {
        isFastClick = false;
        long nowTime = System.currentTimeMillis();
        if (nowTime - lastClickTime < Constants.TIME_2000) {
            isFastClick =  true;
        }
        lastClickTime = nowTime;
        Log.e(TAG, "isFastClick: " + isFastClick);
        return isFastClick;
    }
}

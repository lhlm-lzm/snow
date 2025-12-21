package com.jamin.snow.custom;

import static com.google.android.material.internal.ViewUtils.dpToPx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.LeadingMarginSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jamin.snow.ImagePreviewActivity;
import com.jamin.snow.R;
import com.jamin.snow.StoryActivity;

import java.util.List;

public class StoryPagerAdapter
        extends RecyclerView.Adapter<StoryPagerAdapter.StoryViewHolder> {

    private final List<StoryActivity.StoryPage> pages;

    public StoryPagerAdapter(List<StoryActivity.StoryPage> pages) {
        this.pages = pages;
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.page_one, parent, false);
        return new StoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull StoryViewHolder holder, int position) {

        StoryActivity.StoryPage page = pages.get(position);

        // 第一张图片：有就显示，没有就隐藏
        if (page.imgTopRes != null) {
            holder.imgTop.setVisibility(View.VISIBLE);
            holder.imgTop.setImageResource(page.imgTopRes);
        } else {
            holder.imgTop.setVisibility(View.GONE);
        }

        // 第二张图片：有就显示，没有就隐藏
        if (page.imgBottomRes != null) {
            holder.imgBottom.setVisibility(View.VISIBLE);
            holder.imgBottom.setImageResource(page.imgBottomRes);
        } else {
            holder.imgBottom.setVisibility(View.GONE);
        }

        holder.imgTop.setOnClickListener(v -> {
            openImagePreview(v.getContext(), page.imgTopRes);
        });

        holder.imgBottom.setOnClickListener(v -> {
            openImagePreview(v.getContext(), page.imgBottomRes);
        });

        // 标题：有就显示，没有就隐藏
        if (page.title != null) {
            holder.tvTitle.setVisibility(View.VISIBLE);
            holder.tvTitle.setText(page.title);
        } else {
            holder.tvTitle.setVisibility(View.GONE);
        }

        SpannableString titleSpan = new SpannableString(page.content);
        titleSpan.setSpan(
                new LeadingMarginSpan.Standard(dpToPx(holder.itemView.getContext(), 20), 0),
                0,
                page.content.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        holder.tvContent.setText(titleSpan);

    }

    private void openImagePreview(Context context, int resId) {
        Intent intent = new Intent(context, ImagePreviewActivity.class);
        intent.putExtra(ImagePreviewActivity.EXTRA_IMAGE_RES, resId);
        context.startActivity(intent);

        if (context instanceof Activity) {
            ((Activity) context).overridePendingTransition(
                    R.anim.scale_center_enter,  // 需要创建此资源文件
                    R.anim.scale_center_exit
            );
        }
    }

    private int dpToPx(Context context, int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

    @Override
    public int getItemCount() {
        return pages.size();
    }

    static class StoryViewHolder extends RecyclerView.ViewHolder {

        ImageView imgTop, imgBottom;
        TextView tvTitle, tvContent;

        StoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imgTop = itemView.findViewById(R.id.imgTop);
            imgBottom = itemView.findViewById(R.id.imgBottom);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvContent = itemView.findViewById(R.id.tvContent);
        }
    }
}

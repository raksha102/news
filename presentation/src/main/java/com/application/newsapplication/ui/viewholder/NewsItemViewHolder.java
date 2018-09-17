package com.application.newsapplication.ui.viewholder;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.application.newsapplication.R;
import com.application.newsapplication.databinding.ItemNewsBinding;
import com.application.newsapplication.ui.util.DeviceUtil;

public class NewsItemViewHolder extends RecyclerView.ViewHolder {

    private final ItemNewsBinding binding;

    public NewsItemViewHolder(ItemNewsBinding itemView) {
        super(itemView.getRoot());
        this.binding = itemView;
        //setImageWidthToHeight(itemView.getRoot().getContext());
    }

    private void setImageWidthToHeight(Context context) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)binding.imgNews.getLayoutParams();
        Point size = DeviceUtil.getImageSize(context);
        params.width = size.x;
        params.height = size.y;
    }

    public static int getLayoutId() {
        return R.layout.item_news;
    }

    public ViewDataBinding getBinder() {
        return binding;
    }
}

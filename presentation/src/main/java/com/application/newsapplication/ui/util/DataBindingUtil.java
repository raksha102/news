package com.application.newsapplication.ui.util;

import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class DataBindingUtil {

    @BindingAdapter("imageSrc")
    public static void setImageSrc(ImageView imageView, String url) {
        if (TextUtils.isEmpty(url)) {
            imageView.setVisibility(View.GONE);
            return;
        }
        Glide.with(imageView.getContext())
                .load(url)
                .into(imageView);
    }
}

package com.application.newsapplication.ui.util;

import android.databinding.BindingAdapter;
import android.os.Build;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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


    @BindingAdapter({"htmlText"})
    public static void setHtmlFormattedText(TextView view, String message) {
        if (Build.VERSION.SDK_INT > 24) {
            view.setText(Html.fromHtml(message, Html.FROM_HTML_MODE_COMPACT));
        } else {
            view.setText(Html.fromHtml(message));
        }
    }
}

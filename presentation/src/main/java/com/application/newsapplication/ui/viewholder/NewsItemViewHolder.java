package com.application.newsapplication.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.domain.News;
import com.application.newsapplication.R;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.txt_title)
    TextView mTxtTitle;

    @BindView(R.id.txt_description)
    TextView mTxtDescription;

    @BindView(R.id.img_news)
    ImageView mImage;

    public NewsItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(itemView);
    }

    public void bindData(News news) {
        mTxtTitle.setText(news.getTitle());
        mTxtDescription.setText(news.getDescription());
        setImage(mImage, news.getImageUrl());
        setVisibility(mTxtTitle, news.getTitle());
        setVisibility(mTxtDescription, news.getTitle());
        setVisibility(mImage, news.getTitle());
    }

    private void setImage(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .into(imageView);
    }

    private void setVisibility(View view, String data) {
        view.setVisibility(TextUtils.isEmpty(data) ? View.GONE : View.VISIBLE);
    }

    public static int getLayoutId() {
        return R.layout.item_news;
    }
}

package com.application.newsapplication.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.domain.News;
import com.application.newsapplication.application.events.NavigationEvent;
import com.application.newsapplication.ui.base.rxbus.RxBus;
import com.application.newsapplication.ui.util.RxEventUtils;
import com.application.newsapplication.ui.viewholder.NewsItemViewHolder;

import java.util.List;

public class NewsListAdapter extends RecyclerView.Adapter<NewsItemViewHolder> {

    private final RxBus mRxBus;
    private List<News> mData;

    public NewsListAdapter(RxBus rxBus) {
        mRxBus = rxBus;
    }

    @Override
    public NewsItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(NewsItemViewHolder.getLayoutId(), null);
        return new NewsItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsItemViewHolder holder, int position) {
        holder.bindData(mData.get(position));
        holder.itemView.setOnClickListener(view ->
                RxEventUtils.sendEventWithData(mRxBus, NavigationEvent.EVENT_ON_NEWS_ITEM_CLICK, mData.get(position)));
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public void setData(List<News> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public void addData(List<News> data) {
        mData.addAll(data);
        notifyItemInserted(mData.size() - data.size());
    }

    public List<News> getData() {
        return mData;
    }

    public boolean hasData() {
        return mData != null && mData.size() > 0;
    }
}
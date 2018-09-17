package com.application.newsapplication.ui.newsdetail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.domain.News;
import com.application.newsapplication.R;
import com.application.newsapplication.application.constants.BundleConstants;
import com.application.newsapplication.databinding.FragmentNewsDetailBinding;
import com.application.newsapplication.ui.base.AppToolbar;
import com.application.newsapplication.ui.base.BaseFragment;

public class NewsDetailFragment extends BaseFragment {

    private News mNews;

    public static NewsDetailFragment newInstance(News news) {

        Bundle args = new Bundle();
        args.putParcelable(BundleConstants.DATA, news);
        NewsDetailFragment fragment = new NewsDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_news_detail;
    }

    @Override
    protected View inflateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentNewsDetailBinding binding = DataBindingUtil.inflate(inflater, getFragmentLayoutId(), container, false);
        binding.setData(mNews);
        return binding.getRoot();
    }

    @Override
    protected void initViews(View view) {

    }

    @Override
    protected void readFromBundle() {
        super.readFromBundle();
        mNews = getArguments().getParcelable(BundleConstants.DATA);
    }

    @Override
    public AppToolbar getToolBarSetting() {
        return new AppToolbar.AppToolBarBuilder(true)
                .setBackButtonEnabled(true)
                .setTitle(mNews.getTitle()).build();
    }
}

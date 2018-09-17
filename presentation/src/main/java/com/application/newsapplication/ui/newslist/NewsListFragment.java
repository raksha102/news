package com.application.newsapplication.ui.newslist;

import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.application.domain.News;
import com.application.newsapplication.R;
import com.application.newsapplication.application.events.NavigationEvent;
import com.application.newsapplication.databinding.FragmentHomeScreenBinding;
import com.application.newsapplication.ui.adapters.NewsListAdapter;
import com.application.newsapplication.ui.base.AppToolbar;
import com.application.newsapplication.ui.base.BaseFragment;
import com.application.newsapplication.ui.model.NewsViewModel;

import java.util.List;

import javax.inject.Inject;

public class NewsListFragment extends BaseFragment {

    @Inject
    NewsViewModel mViewModel;

    private NewsListAdapter mAdapter;
    private ArrayAdapter<String> mSpinnerAdapter;

    public static NewsListFragment newInstance() {

        Bundle args = new Bundle();

        NewsListFragment fragment = new NewsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_home_screen;
    }

    @Override
    protected View inflateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentHomeScreenBinding binding = DataBindingUtil.inflate(inflater, getFragmentLayoutId(), container, false);
        seUpRecyclerView(binding);
        setUpSpinner(binding);
        return binding.getRoot();
    }

    private void setUpSpinner(FragmentHomeScreenBinding binding) {

        binding.spinnerNews.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String source = (String) parent.getItemAtPosition(position);
                mViewModel.getData(source);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spinnerNews.setSelection(0);
    }

    private void seUpRecyclerView(FragmentHomeScreenBinding binding) {
        binding.rvNews.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new NewsListAdapter(getRxBus());
        binding.rvNews.setAdapter(mAdapter);
    }

    @Override
    protected void initViews(View view) {
        observeData();
    }

    private void observeData() {
        mViewModel.getLiveData().observe(this, news -> updateData(news));
    }

    private void updateData(List<News> news) {
        if(mAdapter.hasData()){
            mAdapter.addData(news);
        } else {
            mAdapter.setData(news);
        }
    }

    @Override
    protected void handleNavigationEvents(NavigationEvent event) {
        switch (event.getFlag()){
            case NavigationEvent.EVENT_ON_NEWS_ITEM_CLICK :
                getNavigator().launchNewsDetailScreen((News)event.getData());
                break;
        }
    }

    @Override
    public AppToolbar getToolBarSetting() {
        return new AppToolbar.AppToolBarBuilder(true)
                .setTitle(getString(R.string.title_home))
                .setExitEnabled(true)
                .build();
    }
}

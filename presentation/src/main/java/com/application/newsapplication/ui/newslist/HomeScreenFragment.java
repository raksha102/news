package com.application.newsapplication.ui.newslist;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;

import com.application.domain.Constants;
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

import butterknife.BindView;

public class HomeScreenFragment extends BaseFragment {

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.load_more)
    ProgressBar mLoadMoreProgress;

    @Inject
    NewsViewModel mViewModel;

    private NewsListAdapter mAdapter;
    private String mLastSelectedSource;
    private boolean bLoadMore;
    int mLastVisibleItemPos;

    public static HomeScreenFragment newInstance() {

        Bundle args = new Bundle();

        HomeScreenFragment fragment = new HomeScreenFragment();
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

    @Override
    protected void initViews(View view) {
        observeLiveData();
    }

    private void observeLiveData() {
        mViewModel.getLiveData().observe(this, news -> updateData(news));
        mViewModel.getLoaderData().observe(this, showLoader -> showLoader(showLoader));
    }

    private void showLoader(Boolean showLoader) {
        mLoadMoreProgress.setVisibility(showLoader && bLoadMore ? View.VISIBLE : View.GONE);
        mProgressBar.setVisibility(showLoader && !bLoadMore? View.VISIBLE : View.GONE);
    }

    private void updateData(List<News> news) {
        if (mAdapter.hasData()) {
            mAdapter.addData(news);
        } else {
            mAdapter.setData(news);
        }
    }

    @Override
    protected void handleNavigationEvents(NavigationEvent event) {
        switch (event.getFlag()) {
            case NavigationEvent.EVENT_ON_NEWS_ITEM_CLICK:
                getNavigator().launchNewsDetailScreen((News) event.getData());
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

    private void setUpSpinner(FragmentHomeScreenBinding binding) {

        binding.spinnerNews.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mLastSelectedSource = (String) parent.getItemAtPosition(position);
                reInit();
                mViewModel.getData(mLastSelectedSource, false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spinnerNews.setSelection(0);
    }

    private void reInit() {
        mAdapter.resetData();
        bLoadMore = false;
        mLastVisibleItemPos = -1;
    }

    private void seUpRecyclerView(FragmentHomeScreenBinding binding) {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        binding.rvNews.setLayoutManager(manager);
        mAdapter = new NewsListAdapter(getRxBus());
        binding.rvNews.setAdapter(mAdapter);
        addScrollListener(binding, manager);
    }

    private void addScrollListener(FragmentHomeScreenBinding binding, LinearLayoutManager manager) {
        binding.rvNews.addOnScrollListener(new RecyclerView.OnScrollListener() {

            int visibleItemPos;
            int threshHold = Constants.PAGE_SIZE - 2;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                onListScroll(dy);
            }

            private void onListScroll(int dy) {
                visibleItemPos = manager.findLastVisibleItemPosition();
                if (dy > 0 && visibleItemPos != mLastVisibleItemPos && mAdapter != null
                        && mAdapter.getItemCount() >= Constants.PAGE_SIZE
                        && visibleItemPos % Constants.PAGE_SIZE == threshHold) {
                    bLoadMore = true;
                    mViewModel.getData(mLastSelectedSource, true);
                    mLastVisibleItemPos = visibleItemPos;
                }
            }
        });
    }
}

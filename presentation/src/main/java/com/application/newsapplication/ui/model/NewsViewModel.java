package com.application.newsapplication.ui.model;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.application.domain.Constants;
import com.application.domain.News;
import com.application.domain.interactor.GetNewsList;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;

public class NewsViewModel extends ViewModel {

    private static final String TAG = NewsViewModel.class.getSimpleName();
    private final GetNewsList mGetNewsList;
    private MutableLiveData<List<News>> mLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> mLoaderData = new MutableLiveData<>();

    @Inject
    public NewsViewModel(GetNewsList getNewsList) {
        mGetNewsList = getNewsList;
    }

    /**
     * Fetch today's news from network
     *
     * @param source     : news source
     * @param page : should fetch next page data
     */
    public void getData(String source, int page) {
        mLoaderData.setValue(true);
        mGetNewsList.execute(new GetNewsList.Params(source, page))
                .firstElement()
                .toSingle()
                .subscribeWith(new DisposableSingleObserver<List<News>>() {
                    @Override
                    public void onSuccess(List<News> news) {
                        mLoaderData.setValue(false);
                        mLiveData.setValue(news);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError : " + e.getMessage());
                        mLoaderData.setValue(false);
                    }
                });
}

    public MutableLiveData<List<News>> getLiveData() {
        return mLiveData;
    }

    public MutableLiveData<Boolean> getLoaderData() {
        return mLoaderData;
    }
}

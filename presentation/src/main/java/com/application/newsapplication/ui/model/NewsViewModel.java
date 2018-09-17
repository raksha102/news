package com.application.newsapplication.ui.model;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.application.domain.Constants;
import com.application.domain.News;
import com.application.domain.interactor.GetNewsList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;

public class NewsViewModel extends ViewModel {

    private static final String TAG = NewsViewModel.class.getSimpleName();
    private final GetNewsList mGetNewsList;
    private Map<String, MutableLiveData<List<News>>> mDataMap = new HashMap<>();
    private MutableLiveData<List<News>> mLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> mLoaderData = new MutableLiveData<>();

    @Inject
    public NewsViewModel(GetNewsList getNewsList) {
        mGetNewsList = getNewsList;
    }

    /**
     * Fetch today's news from network
     * @param source : news source
     * @param isLoadMore : should fetch next page data
     */
    public void getData(String source, boolean isLoadMore) {
        mLoaderData.setValue(true);
        if (!isLoadMore && mDataMap.containsKey(source) && mDataMap.get(source).getValue().size() > 0) {
            mLiveData.setValue(mDataMap.get(source).getValue());
            mLoaderData.setValue(false);
        } else {
            mGetNewsList.execute(new GetNewsList.Params(source, getPage() + 1))
                    .firstElement()
                    .toSingle()
                    .subscribeWith(new DisposableSingleObserver<List<News>>() {
                        @Override
                        public void onSuccess(List<News> news) {
                            mLoaderData.setValue(false);
                            mLiveData.setValue(news);
                            mDataMap.put(source, mLiveData);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG, "onError : " + e.getMessage());
                            mLoaderData.setValue(false);
                        }
                    });
        }
    }

    private int getPage() {
        return mLiveData.getValue() != null && mLiveData.getValue().size() > 0 ? mLiveData.getValue().size() / Constants.PAGE_SIZE : 0;
    }

    public MutableLiveData<List<News>> getLiveData() {
        return mLiveData;
    }

    public MutableLiveData<Boolean> getLoaderData() {
        return mLoaderData;
    }
}

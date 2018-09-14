package com.application.newsapplication.ui.model;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

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

    @Inject
    public NewsViewModel(GetNewsList getNewsList) {
        mGetNewsList = getNewsList;
    }

    public void getData(String source){
        if(mDataMap.containsKey(source) && mDataMap.get(source).getValue().size() > 0){
            mLiveData.setValue(mDataMap.get(source).getValue());
        } else {
            mGetNewsList.execute(new GetNewsList.Params(source))
                    .subscribeWith(new DisposableSingleObserver<List<News>>() {
                        @Override
                        public void onSuccess(List<News> news) {
                            mLiveData.setValue(news);
                            mDataMap.put(source, mLiveData);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG, "onError : "+e.getMessage());
                        }
                    });
        }
    }

    public MutableLiveData<List<News>> getLiveData() {
        return mLiveData;
    }
}

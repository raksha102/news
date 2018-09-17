package com.application.data.repository.datasources;

import com.application.data.NetworkConstants;
import com.application.data.entity.NewsResponse;
import com.application.data.net.ApiService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

public class NewsCloudDataSource implements NewsDataStore {

    private final ApiService mApiService;

    public NewsCloudDataSource(ApiService apiService) {
        mApiService = apiService;
    }

    @Override
    public Observable<NewsResponse> newsEntityList(String source, long page, int pageSize) {
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        return mApiService.fetchFeed(source, NetworkConstants.API_KEY, today, today, page, pageSize);
    }
}

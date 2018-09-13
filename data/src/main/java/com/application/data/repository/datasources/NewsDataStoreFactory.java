package com.application.data.repository.datasources;

import com.application.data.net.ApiService;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NewsDataStoreFactory {

    private final ApiService mApiService;

    @Inject
    public NewsDataStoreFactory(ApiService apiService) {
        mApiService = apiService;
    }

    public NewsDataStore createCloudDataStore() {
        return new NewsCloudDataSource(mApiService);
    }
}

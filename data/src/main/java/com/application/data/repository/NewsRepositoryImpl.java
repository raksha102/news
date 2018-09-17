package com.application.data.repository;

import com.application.data.NetworkConstants;
import com.application.data.entity.mapper.NewsDataMapper;
import com.application.data.repository.datasources.NewsDataStore;
import com.application.data.repository.datasources.NewsDataStoreFactory;
import com.application.domain.Constants;
import com.application.domain.News;
import com.application.domain.repositoty.NewsRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class NewsRepositoryImpl implements NewsRepository {

    private final NewsDataStoreFactory mDataStoreFactory;
    private final NewsDataMapper mMapper;

    @Inject
    public NewsRepositoryImpl(NewsDataStoreFactory factory, NewsDataMapper newsDataMapper) {
        mDataStoreFactory = factory;
        mMapper = newsDataMapper;
    }

    @Override
    public Observable<List<News>> getNews(String source, long page, int pageSize) {
        NewsDataStore dataStore = mDataStoreFactory.createCloudDataStore();
        return dataStore.newsEntityList(getSource(source), page, pageSize).map(mMapper::transform);
    }

    private String getSource(String source) {
        switch (source) {
            case Constants.SOURCE_CNN:
                return NetworkConstants.SOURCE_CNN;
            case Constants.SOURCE_BBC_NEWS:
                return "bbc-news";
            case Constants.SOURCE_NEW_YORK_TIMES:
                return "the-new-york-times";
            default:
                return "";
        }
    }
}

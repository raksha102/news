package com.application.data.repository;

import com.application.data.entity.mapper.NewsDataMapper;
import com.application.data.repository.datasources.NewsDataStore;
import com.application.data.repository.datasources.NewsDataStoreFactory;
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
    public Observable<List<News>> getNews(String source, int offset, int count) {
        NewsDataStore dataStore = mDataStoreFactory.createCloudDataStore();
        return dataStore.newsEntityList(source,offset,count).map(mMapper::transform);
    }
}

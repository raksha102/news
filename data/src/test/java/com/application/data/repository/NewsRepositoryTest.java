package com.application.data.repository;


import com.application.data.NetworkConstants;
import com.application.data.entity.NewsResponse;
import com.application.data.entity.mapper.NewsDataMapper;
import com.application.data.repository.datasources.NewsDataStore;
import com.application.data.repository.datasources.NewsDataStoreFactory;
import com.application.domain.Constants;
import com.application.domain.repositoty.NewsRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.Observable;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class NewsRepositoryTest {

    @Mock
    private NewsDataStoreFactory mockNewsDataStoreFactory;
    @Mock
    private NewsDataMapper mockNewsDataMapper;
    @Mock
    private NewsDataStore mockNewsDataStore;

    private NewsRepository newsDataRepository;

    @Before
    public void setUp() {
        newsDataRepository = new NewsRepositoryImpl(mockNewsDataStoreFactory, mockNewsDataMapper);
        given(mockNewsDataStoreFactory.createCloudDataStore()).willReturn(mockNewsDataStore);
    }

    @Test
    public void testGetNewsHappyCase() {
        given(mockNewsDataStore.newsEntityList(NetworkConstants.SOURCE_CNN, 1, 10)).willReturn(Observable.just(new NewsResponse()));
        newsDataRepository.getNews(Constants.SOURCE_CNN, 1, 10);
        verify(mockNewsDataStoreFactory).createCloudDataStore();
        verify(mockNewsDataStore).newsEntityList(NetworkConstants.SOURCE_CNN, 1, 10);
    }
}


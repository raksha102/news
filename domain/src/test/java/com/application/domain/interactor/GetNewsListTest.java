package com.application.domain.interactor;


import com.application.domain.Constants;
import com.application.domain.executor.PostExecutionThread;
import com.application.domain.executor.ThreadExecutor;
import com.application.domain.repositoty.NewsRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class GetNewsListTest {

    private GetNewsList mGetNewsList;

    @Mock private ThreadExecutor mockThreadExecutor;
    @Mock private PostExecutionThread mockPostExecutionThread;
    @Mock private NewsRepository mockNewsRepository;

    @Before
    public void setUp() {
        mGetNewsList = new GetNewsList(mockNewsRepository, mockThreadExecutor,
                mockPostExecutionThread);
    }

    @Test
    public void testGetNewsListUseCaseObservableHappyCase() {
        mGetNewsList.buildUseCaseObservable(new GetNewsList.Params(Constants.SOURCE_CNN, 1));
        verify(mockNewsRepository).getNews(Constants.SOURCE_CNN, 1, 10);
        verifyNoMoreInteractions(mockNewsRepository);
        verifyZeroInteractions(mockThreadExecutor);
        verifyZeroInteractions(mockPostExecutionThread);
    }
}

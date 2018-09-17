package com.application.data.mapper;


import com.application.data.entity.NewsResponse;
import com.application.data.entity.mapper.NewsDataMapper;
import com.application.domain.News;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class NewsDataMapperTest {

    private NewsDataMapper mNewsDataMapper;

    @Before
    public void setUp() throws Exception {
        mNewsDataMapper = new NewsDataMapper();
    }


    @Test
    public void testTransformNewsEntityCollection() {
        NewsResponse.Article mockEntityOne = mock(NewsResponse.Article.class);
        NewsResponse.Article mockEntityTwo = mock(NewsResponse.Article.class);

        List<NewsResponse.Article> newsEntityList = new ArrayList<>(5);
        newsEntityList.add(mockEntityOne);
        newsEntityList.add(mockEntityTwo);

        NewsResponse newsResponse = new NewsResponse();
        newsResponse.setArticles(newsEntityList);

        Collection<News> newsCollection = mNewsDataMapper.transform(newsResponse);

        assertThat(newsCollection.toArray()[0], is(instanceOf(News.class)));
        assertThat(newsCollection.toArray()[1], is(instanceOf(News.class)));
        assertThat(newsCollection.size(), is(2));
    }
}

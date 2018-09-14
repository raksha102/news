package com.application.data.entity.mapper;

import com.application.data.entity.NewsResponse;
import com.application.domain.News;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NewsDataMapper {

    @Inject
    public NewsDataMapper() {
    }

    public List<News> transform(NewsResponse response){
        List<News> newsList = new ArrayList<>();
            if(response != null && response.getArticles() != null){
                for(NewsResponse.Article article : response.getArticles()){
                   newsList.add(new News(article.getTitle(), article.getDescription(), article.getUrlToImage(), article.getContent()));
                }
            }
        return newsList;
    }
}

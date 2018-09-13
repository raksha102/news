package com.application.domain.repositoty;

import com.application.domain.News;

import java.util.List;

import io.reactivex.Observable;

public interface NewsRepository {

    Observable<List<News>> getNews(String source, int offset, int count);
}

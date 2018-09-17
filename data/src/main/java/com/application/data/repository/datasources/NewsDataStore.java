package com.application.data.repository.datasources;

import com.application.data.entity.NewsResponse;

import java.util.List;

import io.reactivex.Observable;

public interface NewsDataStore {

    Observable<NewsResponse> newsEntityList(String source, long page, int pageSize);
}

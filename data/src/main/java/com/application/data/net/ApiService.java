package com.application.data.net;

import com.application.data.entity.NewsResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("top-headlines")
    Observable<NewsResponse> getTopHeadLines(@Query("apiKey") String apiKey, @Query("sources") String sources);

    @GET("/v2/everything")
    Observable<NewsResponse> fetchFeed(@Query("q") String q,
                                       @Query("apiKey") String apiKey,
                                       @Query("from") String from,
                                       @Query("to") String to,
                                       @Query("page") long page,
                                       @Query("pageSize") int pageSize);
}

package com.application.data.net;

import com.application.data.entity.NewsResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("top-headlines")
    Observable<NewsResponse> getNews(@Query("apiKey") String apiKey, @Query("sources") String sources);
}

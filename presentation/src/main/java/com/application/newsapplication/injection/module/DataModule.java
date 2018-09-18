
package com.application.newsapplication.injection.module;

import android.content.Context;
import android.net.NetworkInfo;
import android.util.Log;

import com.application.data.NetworkConstants;
import com.application.data.net.ApiService;
import com.application.data.net.LoggingInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class DataModule {

    private static final String TAG = DataModule.class.getSimpleName();
    public static final String HEADER_CACHE_CONTROL = "Cache-Control";
    public static final String HEADER_PRAGMA = "Pragma";

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {

        return new Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    Gson provideGson() {

        return new GsonBuilder()
                .setLenient()
                .create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Context context) {

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(new LoggingInterceptor());

        client.addInterceptor(provideOfflineCacheInterceptor(context))
                .addNetworkInterceptor(provideCacheInterceptor(context))
                .cache(provideCache(context));

        client.connectTimeout(NetworkConstants.API_TIMEOUT, TimeUnit.SECONDS);
        client.readTimeout(NetworkConstants.API_TIMEOUT, TimeUnit.SECONDS);
        return client.build();
    }

    private Cache provideCache(Context context) {
        Cache cache = null;

        try {
            cache = new Cache(new File(context.getCacheDir(), "http-cache"),
                    10 * 1024 * 1024); // 10 MB
        } catch (Exception e) {
            Log.e(TAG, "Could not create Cache!");
        }

        return cache;
    }

    private Interceptor provideCacheInterceptor(Context context) {
        return chain -> {
            Response response = chain.proceed(chain.request());

            CacheControl cacheControl;

            if (isConnected(context)) {
                cacheControl = new CacheControl.Builder()
                        .maxAge(0, TimeUnit.SECONDS)
                        .build();
            } else {
                //Todo : set max age to remaining time in a day
                cacheControl = new CacheControl.Builder()
                        .maxStale(1, TimeUnit.DAYS)
                        .build();
            }

            return response.newBuilder()
                    .removeHeader(HEADER_PRAGMA)
                    .removeHeader(HEADER_CACHE_CONTROL)
                    .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                    .build();

        };
    }

    private Interceptor provideOfflineCacheInterceptor(Context context) {
        return chain -> {
            Request request = chain.request();

            if (!isConnected(context)) {
                //Todo : set max age to remaining time in a day
                CacheControl cacheControl = new CacheControl.Builder()
                        .maxStale(1, TimeUnit.DAYS)
                        .build();

                request = request.newBuilder()
                        .removeHeader(HEADER_PRAGMA)
                        .removeHeader(HEADER_CACHE_CONTROL)
                        .cacheControl(cacheControl)
                        .build();
            }

            return chain.proceed(request);
        };
    }

    @Provides
    @Singleton
    ApiService provideApiService(Retrofit restClient) {
        return restClient.create(ApiService.class);
    }

    public boolean isConnected(Context context) {
        try {
            android.net.ConnectivityManager e = (android.net.ConnectivityManager) context.getSystemService(
                    Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = e.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        } catch (Exception e) {
            Log.w(TAG, e.toString());
        }

        return false;
    }

}

package com.application.newsapplication.application;

import android.app.Activity;
import android.app.Application;
import android.support.multidex.MultiDex;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class NewsApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    private static NewsApplication sInstance;

    public NewsApplication() {
        sInstance = this;
    }

    public static NewsApplication getApplicationInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        initDagger();
    }

    private void initDagger() {
        DaggerApplicationComponent
                .builder()
                .application(this)
                .build()
                .inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }
}
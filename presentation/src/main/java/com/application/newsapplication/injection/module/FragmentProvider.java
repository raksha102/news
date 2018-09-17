package com.application.newsapplication.injection.module;

import com.application.newsapplication.ui.newsdetail.NewsDetailFragment;
import com.application.newsapplication.ui.newslist.HomeScreenFragment;
import com.application.newsapplication.ui.onboard.SplashScreenFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentProvider {

    @ContributesAndroidInjector
    abstract NewsDetailFragment bindNewsDetailFragment();

    @ContributesAndroidInjector
    abstract SplashScreenFragment bindSplashScreenFragment();

    @ContributesAndroidInjector
    abstract HomeScreenFragment bindNewsListFragment();
}

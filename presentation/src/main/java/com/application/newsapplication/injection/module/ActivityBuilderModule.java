package com.application.newsapplication.injection.module;

import com.application.newsapplication.injection.scope.ActivityScope;
import com.application.newsapplication.ui.NewsActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = {BaseModule.class, FragmentProvider.class})
    abstract NewsActivity bindBaseActivity();
}

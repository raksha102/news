package com.application.newsapplication.injection.module;

import android.content.Context;

import com.application.newsapplication.injection.scope.ActivityContext;
import com.application.newsapplication.injection.scope.ContainerId;
import com.application.newsapplication.ui.NewsActivity;
import com.application.newsapplication.ui.base.BaseActivity;
import com.application.newsapplication.ui.base.navigator.AppNavigator;
import com.application.newsapplication.ui.base.navigator.AppNavigatorImpl;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = {BaseModule.Declaration.class})
public class BaseModule {

    @Provides
    @ContainerId
    public int provideContainerId(BaseActivity activity) {
        return activity.getContainerId();
    }

    @Module
    public interface Declaration {

        @Binds
        BaseActivity bindsBaseActivity(NewsActivity activity);

        @Binds
        @ActivityContext
        Context bindsContext(NewsActivity activity);

        @Binds
        AppNavigator bindsAppNavigator(AppNavigatorImpl appNavigator);
    }

}

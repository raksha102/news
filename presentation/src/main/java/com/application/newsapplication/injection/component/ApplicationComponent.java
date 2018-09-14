package com.application.newsapplication.injection.component;

import android.app.Application;

import com.application.newsapplication.application.NewsApplication;
import com.application.newsapplication.injection.module.ActivityBuilderModule;
import com.application.newsapplication.injection.module.ApplicationModule;
import com.application.newsapplication.injection.module.DataModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {AndroidInjectionModule.class, ActivityBuilderModule.class, ApplicationModule.class,
        DataModule.class})
public interface ApplicationComponent {

    void inject(NewsApplication app);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();
    }
}

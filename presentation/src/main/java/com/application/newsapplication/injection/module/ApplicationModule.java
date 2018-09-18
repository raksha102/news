package com.application.newsapplication.injection.module;

import android.app.Application;
import android.content.Context;

import com.application.data.repository.NewsRepositoryImpl;
import com.application.domain.executor.PostExecutionThread;
import com.application.domain.executor.ThreadExecutor;
import com.application.domain.repositoty.NewsRepository;
import com.application.newsapplication.application.IOThread;
import com.application.newsapplication.application.UIThread;
import com.application.newsapplication.ui.base.rxbus.RxBus;
import com.application.newsapplication.ui.base.rxbus.RxBusImpl;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ApplicationModule {

    @Binds
    @Singleton
    abstract Context provideContext(Application application);

    @Binds
    @Singleton
    abstract RxBus bindRxBus(RxBusImpl rxBus);

    @Binds
    @Singleton
    abstract NewsRepository bindNewsRepository(NewsRepositoryImpl newsRepository);

    @Binds
    @Singleton
    public abstract ThreadExecutor bindThreadExecutor(IOThread ioThread);

    @Binds
    @Singleton
    public abstract PostExecutionThread bindPostExecutionThread(UIThread uiThread);


}

package com.application.newsapplication.injection.module;

import com.application.newsapplication.ui.base.rxbus.RxBus;
import com.application.newsapplication.ui.base.rxbus.RxBusImpl;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ApplicationModule {

    @Binds
    @Singleton
    abstract RxBus bindRxBus(RxBusImpl rxBus);

}

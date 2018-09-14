
package com.application.newsapplication.application;


import com.application.domain.executor.ThreadExecutor;
import com.application.newsapplication.injection.scope.ApplicationScope;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * MainThread (IO Thread) implementation based on a {@link Scheduler}
 * which will execute actions on the Android IO thread
 */
@Singleton
public class IOThread implements ThreadExecutor {

    @Inject
    public IOThread() {
    }

    @Override
    public Scheduler getScheduler() {
        return Schedulers.io();
    }
}

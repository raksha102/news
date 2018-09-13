
package com.application.domain.executor;


import io.reactivex.Scheduler;

public interface ThreadExecutor {

    Scheduler getScheduler();
}


package com.application.domain.interactor;

import com.application.domain.executor.PostExecutionThread;
import com.application.domain.executor.ThreadExecutor;

import io.reactivex.FlowableTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.SingleTransformer;
import io.reactivex.observers.DisposableObserver;

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This interface represents a execution unit for different use cases (this means any use case
 * in the application should implement this contract).
 * <p>
 * By convention each UseCase implementation will return the result using a {@link DisposableObserver}
 * that will execute its job in a background thread and will post the result in the UI thread.
 */
public abstract class UseCase<T, Params> {

    protected final ThreadExecutor threadExecutor;
    protected final PostExecutionThread postExecutionThread;

    //Used later
    protected UseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    protected UseCase(PostExecutionThread postExecutionThread) {
        this.threadExecutor = null;
        this.postExecutionThread = postExecutionThread;
    }

    /**
     * Executes the current use case.
     *
     * @param params Parameters (Optional) used to build/execute this use case.
     */
    public abstract T execute(Params params);

    protected <Upstream> SingleTransformer<Upstream, Upstream> getApiExecutor() {
        return upstream -> upstream
                .subscribeOn(threadExecutor.getScheduler())
                .observeOn(postExecutionThread.getScheduler());
    }

}

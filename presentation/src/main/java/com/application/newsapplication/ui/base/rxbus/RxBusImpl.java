
package com.application.newsapplication.ui.base.rxbus;


import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class RxBusImpl implements RxBus {

    private final PublishSubject<Object> mBus = PublishSubject.create();

    @Inject
    public RxBusImpl() {
    }

    @Override
    public void send(Object event) {
        mBus.onNext(event);
    }

    @Override
    public Observable<Object> toObservable() {
        return mBus;
    }

    @Override
    public boolean hasObservers() {
        return mBus.hasObservers();
    }

}

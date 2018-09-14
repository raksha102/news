package com.application.newsapplication.ui.base;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.newsapplication.application.events.NavigationEvent;
import com.application.newsapplication.ui.base.navigator.AppNavigator;
import com.application.newsapplication.ui.base.rxbus.RxBus;
import com.application.newsapplication.ui.base.rxbus.RxBusCallback;
import com.application.newsapplication.ui.base.rxbus.RxBusHelper;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;

public abstract class BaseFragment extends Fragment implements RxBusCallback {

    private static final String TAG = BaseFragment.class.getSimpleName();
    @Inject
    RxBus mRxBus;

    @Inject
    AppNavigator mNavigator;

    private Unbinder mUnBinder;
    private RxBusHelper mRxBusHelper;

    protected abstract int getFragmentLayoutId();

    protected abstract void initViews(View view);

    public abstract AppToolbar getToolBarSetting();

    protected View inflateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getFragmentLayoutId(), container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        readFromBundle();
        super.onCreate(savedInstanceState);
    }

    private void readFromBundle() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        AndroidSupportInjection.inject(this);

        registerEvents();

        View view = inflateView(inflater, container, savedInstanceState);

        mUnBinder = ButterKnife.bind(this, view);

        setupActionBar(getToolBarSetting());

        initViews(view);

        return view;
    }

    private void registerEvents() {
        mRxBusHelper = new RxBusHelper();
        mRxBusHelper.registerEvents(getRxBus(), TAG, this);
    }

    protected void setupActionBar(AppToolbar settings) {
        BaseActivity activity = (BaseActivity) getActivity();
        activity.setToolBar(settings);
    }

    @Override
    public void onEventTrigger(Object event) {
        if(event instanceof NavigationEvent){
            handleNavigationEvents((NavigationEvent)event);
        }
    }

    protected void handleNavigationEvents(NavigationEvent event) {

    }

    @Override
    public void onDestroyView() {

        if (mUnBinder != null) {
            mUnBinder.unbind();
        }

        super.onDestroyView();
    }

    protected RxBus getRxBus() {
        return mRxBus;
    }

    protected AppNavigator getNavigator() {
        return mNavigator;
    }

}

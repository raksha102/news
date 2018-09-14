package com.application.newsapplication.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.application.newsapplication.R;
import com.application.newsapplication.application.events.NavigationEvent;
import com.application.newsapplication.ui.base.navigator.AppNavigator;
import com.application.newsapplication.ui.base.rxbus.RxBus;
import com.application.newsapplication.ui.base.rxbus.RxBusCallback;
import com.application.newsapplication.ui.base.rxbus.RxBusHelper;
import com.application.newsapplication.ui.widget.CustomToolBar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public abstract class BaseActivity extends AppCompatActivity implements HasSupportFragmentInjector, RxBusCallback {

    private static final String TAG = BaseActivity.class.getSimpleName();

    @BindView(R.id.drawer_layout)
    protected DrawerLayout mDrawerLayout;

    @BindView(R.id.base_toolbar)
    CustomToolBar mToolBar;

    @Inject
    RxBus mRxBus;

    @Inject
    AppNavigator mNavigator;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    protected abstract void initViews();

    protected abstract boolean shouldDisableDrawer();

    protected CustomToolBar getToolBar() {
        return mToolBar;
    }

    private Unbinder mUnBinder;
    private RxBusHelper mRxBusHelper;
    private AppToolbar mToolbarSettings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mUnBinder = ButterKnife.bind(this);
        if (shouldDisableDrawer())
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        readFromBundle(getIntent());
        initViews();
        registerEvents();
    }

    protected int getLayoutId() {
        return R.layout.activity_base;
    }

    public int getContainerId() {
        return R.id.frag_container;
    }

    private void registerEvents() {
        mRxBusHelper = new RxBusHelper();
        mRxBusHelper.registerEvents(mRxBus, TAG, this);
    }

    protected void readFromBundle(Intent intent) {

    }

    @Override
    public void onEventTrigger(Object event) {
        if (event instanceof NavigationEvent) {
            handleNavigationEvent((NavigationEvent) event);
        }
    }

    protected void handleNavigationEvent(NavigationEvent event) {

    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    public void setToolBar(AppToolbar toolBarSetting) {
        CustomToolBar toolBar = getToolBar();
        if (toolBar != null && toolBarSetting != null) {
            if (toolBarSetting.isActionBarEnabled()) {
                toolBar.setVisibility(View.VISIBLE);
                toolBar.addSettings(toolBarSetting);

                setSupportActionBar(toolBar);
                setOptionsMenu(toolBarSetting);
            } else {
                toolBar.setVisibility(View.GONE);
            }
        }
    }

    protected void invalidateToolbar() {

        Fragment fragment = getSupportFragmentManager().findFragmentById(getContainerId());
        if (fragment != null && fragment instanceof BaseFragment) {
            BaseFragment baseFragment = (BaseFragment) fragment;
            AppToolbar toolBarSetting = baseFragment.getToolBarSetting();
            if (toolBarSetting != null) {
                setToolBar(toolBarSetting);
            }
        }
    }

    private void setOptionsMenu(AppToolbar toolBarSetting) {
        mToolbarSettings = toolBarSetting;
        invalidateOptionsMenu();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager() != null) {

            if (getStackCount() == 0) {
                finish();
                return;
            }

            Fragment fragment = getTopFragment();
            if (fragment != null && fragment instanceof BaseFragment) {
                super.onBackPressed();
                invalidateToolbar();
            } else {
                super.onBackPressed();
            }

        } else {
            super.onBackPressed();
        }
    }

    protected Fragment getTopFragment() {
        return getSupportFragmentManager() != null ?
                getSupportFragmentManager().findFragmentById(getContainerId()) : null;
    }

    protected int getStackCount() {
        return getSupportFragmentManager().getBackStackEntryCount();
    }


    protected void handleHomeButtonClick() {

        Fragment fragment = getTopFragment();
        if (fragment instanceof BaseFragment) {
            BaseFragment baseFragment = (BaseFragment) fragment;
            AppToolbar toolbar = baseFragment.getToolBarSetting();
            if (toolbar != null) {
                if (toolbar.isCloseButtonEnabled()) {
                    finish();
                } else {
                    onBackPressed();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.base_menu, menu);

        if (mToolbarSettings == null) return true;

        MenuItem itemExit = menu.findItem(R.id.menu_exit);

        // set visibility
        itemExit.setVisible(mToolbarSettings.isExitEnabled());

        return true;
    }

    public AppNavigator getNavigator() {
        return mNavigator;
    }

    public RxBus getRxBus() {
        return mRxBus;
    }

    @Override
    protected void onDestroy() {

        if (mUnBinder != null) {
            mUnBinder.unbind();
        }

        if (mRxBusHelper != null) {
            mRxBusHelper.unSubScribe();
            mRxBusHelper = null;
        }

        super.onDestroy();
    }
}



package com.application.newsapplication.ui.onboard;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.application.newsapplication.R;
import com.application.newsapplication.application.constants.AppConstants;
import com.application.newsapplication.ui.base.AppToolbar;
import com.application.newsapplication.ui.base.BaseFragment;

public class SplashScreenFragment extends BaseFragment {

    public static SplashScreenFragment newInstance() {

        Bundle args = new Bundle();

        SplashScreenFragment fragment = new SplashScreenFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_splash_screen;
    }

    @Override
    protected void initViews(View view) {
        new Handler().postDelayed(() -> getNavigator().launchNewsScreen(), AppConstants.SPLASH_TIMEOUT);
    }

    @Override
    public AppToolbar getToolBarSetting() {
        return new AppToolbar.AppToolBarBuilder(false).build();
    }
}


package com.application.newsapplication.ui.base.navigator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.application.domain.News;
import com.application.newsapplication.application.constants.BundleConstants;
import com.application.newsapplication.injection.scope.ActivityScope;
import com.application.newsapplication.injection.scope.ContainerId;
import com.application.newsapplication.ui.base.BaseActivity;
import com.application.newsapplication.ui.newsdetail.NewsDetailFragment;
import com.application.newsapplication.ui.newslist.NewsListFragment;
import com.application.newsapplication.ui.onboard.SplashScreenFragment;

import javax.inject.Inject;

@ActivityScope
public class AppNavigatorImpl implements AppNavigator {

    private FragmentActivity mActivity;
    private int mContainerId;

    @Inject
    public AppNavigatorImpl(BaseActivity context, @ContainerId int containerId) {
        mActivity = context;
        mContainerId = containerId;
    }

    @Override
    public void launchNewsScreen() {
        replaceFragment(mContainerId, NewsListFragment.newInstance());
    }

    @Override
    public void launchWelcomeScreen() {
        replaceFragment(mContainerId, SplashScreenFragment.newInstance());
    }

    @Override
    public void launchNewsDetailScreen(News data) {
        replaceFragment(mContainerId, NewsDetailFragment.newInstance(data));
    }

    // Internal Implementation
    // below code can be moved to separate class for optimization

    private void startActivity(@NonNull Class<? extends Activity> activityClass, Bundle args, Integer requestCode) {
        Intent intent = new Intent();
        intent.putExtra(BundleConstants.EXTRA_ARG, args);
        startActivityInternal(activityClass, intent, requestCode);
    }

    private void addFragmentAndAddToBackStack(@IdRes int containerId, @NonNull Fragment fragment, String backStackTag) {
        addFragmentInternal(mActivity.getSupportFragmentManager(), containerId, fragment, null, null, true, backStackTag);
    }

    private void addFragmentAndAddToBackStack(@IdRes int containerId, @NonNull Fragment fragment) {
        addFragmentInternal(mActivity.getSupportFragmentManager(), containerId, fragment, null, null, true, null);
    }

    private final void replaceFragment(@IdRes int containerId, Fragment fragment) {
        replaceFragmentInternal(mActivity.getSupportFragmentManager(), containerId, fragment, null, null, false, null);
    }

    public final void replaceFragment(@IdRes int containerId, @NonNull Fragment fragment, @NonNull String fragmentTag) {
        replaceFragmentInternal(mActivity.getSupportFragmentManager(), containerId, fragment, fragmentTag, null, false, null);
    }

    private final void replaceFragmentAndAddToBackStack(@IdRes int containerId, Fragment fragment, String backStackTag) {
        replaceFragmentInternal(mActivity.getSupportFragmentManager(), containerId, fragment, null, null, true, backStackTag);
    }


    //

    private void startActivityInternal(Class<? extends Activity> activityClass, Intent setArgsAction, Integer requestCode) {
        Intent intent = new Intent(mActivity, activityClass);
        intent.putExtras(setArgsAction.getExtras());
        if (requestCode != null) {
            mActivity.startActivityForResult(intent, requestCode);
        } else {
            mActivity.startActivity(intent);
        }
    }

    private final void replaceFragmentInternal(FragmentManager fm, @IdRes int containerId, Fragment fragment, String fragmentTag, Bundle args, boolean addToBackstack, String backstackTag) {
        if (mActivity.isFinishing()) return;

        if (args != null) {
            fragment.setArguments(args);
        }
        FragmentTransaction ft = fm.beginTransaction().replace(containerId, fragment, fragmentTag);
        if (addToBackstack) {
            ft.addToBackStack(backstackTag).commitAllowingStateLoss();
            fm.executePendingTransactions();
        } else {
            ft.commitAllowingStateLoss();
        }
    }

    private final void addFragmentInternal(FragmentManager fm, @IdRes int containerId, Fragment fragment, String fragmentTag, Bundle args, boolean addToBackstack, String backstackTag) {
        if (mActivity.isFinishing()) return;
        if (args != null) {
            fragment.setArguments(args);
        }
        FragmentTransaction ft = fm.beginTransaction().add(containerId, fragment, fragmentTag);
        if (addToBackstack) {
            ft.addToBackStack(backstackTag).commitAllowingStateLoss();
            fm.executePendingTransactions();
        } else {
            ft.commitAllowingStateLoss();
        }
    }
}

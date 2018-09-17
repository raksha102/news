package com.application.newsapplication;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.test.ActivityInstrumentationTestCase2;

import com.application.newsapplication.application.constants.AppConstants;
import com.application.newsapplication.ui.NewsActivity;
import com.application.newsapplication.ui.newslist.HomeScreenFragment;
import com.application.newsapplication.ui.onboard.SplashScreenFragment;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class NewsActivityTest extends ActivityInstrumentationTestCase2<NewsActivity> {

    private NewsActivity newsActivity;

    public NewsActivityTest() {
        super(NewsActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.setActivityIntent(createTargetIntent());
        newsActivity = getActivity();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testContainsSplashFragment() {
        Fragment splashFragment =
                newsActivity.getSupportFragmentManager().findFragmentById(R.id.frag_container);
        assertThat(splashFragment, is(notNullValue()));
        assertTrue(splashFragment instanceof SplashScreenFragment);

        try {
            Thread.sleep(AppConstants.SPLASH_TIMEOUT + 100);
            Fragment homeScreenFragment =
                    newsActivity.getSupportFragmentManager().findFragmentById(R.id.frag_container);
            assertThat(homeScreenFragment, is(notNullValue()));
            assertTrue(homeScreenFragment instanceof HomeScreenFragment);
        } catch (InterruptedException ie) {

        }
    }

    private Intent createTargetIntent() {
        Intent intentLaunchActivity = new Intent(getInstrumentation().getTargetContext(), NewsActivity.class);
        return intentLaunchActivity;
    }
}

package com.application.newsapplication.ui.base.navigator;

import com.application.domain.News;

public interface AppNavigator {

    void launchNewsScreen();

    void launchWelcomeScreen();

    void launchNewsDetailScreen(News data);

}

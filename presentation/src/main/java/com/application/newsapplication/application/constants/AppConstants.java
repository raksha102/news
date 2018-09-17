package com.application.newsapplication.application.constants;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

public class AppConstants {

    public static final long SPLASH_TIMEOUT = 3000;

    @IntDef({SCREEN_LOGIN})
    public @interface SCREEN_TYPE {
    }

    public static final int SCREEN_LOGIN = 101;

}

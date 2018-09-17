package com.application.newsapplication.ui.util;


import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;

import com.application.newsapplication.R;

public class DeviceUtil {

    /**
     * To get device width.
     *
     * @return Device width
     */
    public static int getDeviceWidth(Context context) {

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    public static Point getImageSize(Context context) {
        int width = context.getResources().getDimensionPixelSize(R.dimen.news_tile_image_width);
        int height = width * (3 / 4);
        return new Point(width, height);
    }
}

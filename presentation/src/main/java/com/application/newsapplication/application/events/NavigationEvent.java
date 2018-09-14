
package com.application.newsapplication.application.events;

import com.application.newsapplication.application.constants.AppConstants;

public class NavigationEvent<T> {

    public static final String EVENT_ON_NEWS_ITEM_CLICK = "event_on_news_item_click";

    private String flag;
    private String tag;
    private T data;
    private int screenType;
    private String filter;

    public NavigationEvent(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setScreenType(@AppConstants.SCREEN_TYPE int type) {
        screenType = type;
    }

    public int getScreenType() {
        return screenType;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

}

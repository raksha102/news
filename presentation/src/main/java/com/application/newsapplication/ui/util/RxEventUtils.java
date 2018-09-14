
package com.application.newsapplication.ui.util;

import com.application.newsapplication.application.constants.AppConstants;
import com.application.newsapplication.application.events.NavigationEvent;
import com.application.newsapplication.ui.base.rxbus.RxBus;

public class RxEventUtils {

    public static void sendEventWithFlag(RxBus rxBus, String flag) {
        if (rxBus != null && rxBus.hasObservers()) {
            rxBus.send(new NavigationEvent(flag));
        }
    }

    public static void sendEventWithData(RxBus rxBus, String flag, Object data) {
        if (rxBus != null && rxBus.hasObservers()) {
            NavigationEvent eventWithData = new NavigationEvent(flag);
            eventWithData.setData(data);
            rxBus.send(eventWithData);
        }
    }

    public static void sendEventWithDataAndType(RxBus rxBus, String flag, Object data, @AppConstants.SCREEN_TYPE int type) {
        if (rxBus != null && rxBus.hasObservers()) {
            NavigationEvent eventWithData = new NavigationEvent(flag);
            eventWithData.setData(data);
            eventWithData.setScreenType(type);
            rxBus.send(eventWithData);
        }
    }

    public static void sendEventWithDataAndTypeAndFilter(RxBus rxBus, String flag, Object data, @AppConstants.SCREEN_TYPE int type, String multiInstanceFilter) {
        if (rxBus != null && rxBus.hasObservers()) {
            NavigationEvent eventWithData = new NavigationEvent(flag);
            eventWithData.setData(data);
            eventWithData.setScreenType(type);
            if (multiInstanceFilter != null) eventWithData.setFilter(multiInstanceFilter);
            rxBus.send(eventWithData);
        }
    }

    public static void sendEventWithFilter(RxBus rxBus, String flag, String multiInstanceFilter) {
        if (rxBus != null && rxBus.hasObservers()) {
            NavigationEvent navigationEvent = new NavigationEvent(flag);
            if (multiInstanceFilter != null) navigationEvent.setFilter(multiInstanceFilter);
            rxBus.send(navigationEvent);
        }
    }

    public static void sendEventWithScreenType(RxBus rxBus, String flag, int screenType) {
        if (rxBus != null && rxBus.hasObservers()) {
            NavigationEvent navigationEvent = new NavigationEvent(flag);
            navigationEvent.setScreenType(screenType);
            rxBus.send(navigationEvent);
        }
    }

    public static void sendEventWithDataFilter(RxBus rxBus, String flag, Object data, String multiInstanceFilter) {
        if (rxBus != null && rxBus.hasObservers()) {
            NavigationEvent eventWithData = new NavigationEvent(flag);
            eventWithData.setData(data);
            if (multiInstanceFilter != null) eventWithData.setFilter(multiInstanceFilter);
            rxBus.send(eventWithData);
        }
    }

    public static void sendEventWithDataFilterTag(RxBus rxBus, String flag, Object data, String tag, String multiInstanceFilter) {
        if (rxBus != null && rxBus.hasObservers()) {
            NavigationEvent eventWithData = new NavigationEvent(flag);
            eventWithData.setData(data);
            eventWithData.setTag(tag);
            if (multiInstanceFilter != null) eventWithData.setFilter(multiInstanceFilter);
            rxBus.send(eventWithData);
        }
    }
}

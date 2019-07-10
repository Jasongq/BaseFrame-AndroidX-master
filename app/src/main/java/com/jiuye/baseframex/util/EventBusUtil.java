package com.jiuye.baseframex.util;

import org.greenrobot.eventbus.EventBus;

/**
 * @author : GuoQiang
 * e-mail : 849199845@qq.com
 * time   : 2019/06/27  9:09
 * desc   :
 * version: 1.0
 */
public class EventBusUtil {
    public static void register(Object subscriber){
        EventBus.getDefault().register(subscriber);
    }
    public static void unregister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

    public static void sendEvent(MessageEvent event) {
        EventBus.getDefault().post(event);
    }

    public static void sendStickyEvent(MessageEvent event) {
        EventBus.getDefault().postSticky(event);
    }
}

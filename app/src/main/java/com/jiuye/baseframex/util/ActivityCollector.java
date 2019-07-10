package com.jiuye.baseframex.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : GuoQiang
 * e-mail : 849199845@qq.com
 * time   : 2018/12/10  13:40
 * desc   : Activity管理类
 * version: 1.0
 */
public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    /**
     * 判断一个Activity 是否存在
     *
     * @param clz
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static <T extends Activity> boolean isActivityExist(Class<T> clz) {
        boolean res;
        Activity activity = getActivity(clz);
        if (activity == null) {
            res = false;
        } else {
            if (activity.isFinishing() || activity.isDestroyed()) {
                res = false;
            } else {
                res = true;
            }
        }
        return res;
    }
    /**
     * 从Activity集合查询, 传入的Activity是否存在
     * 如果存在就返回该Activity,不存在就返回null
     * @param activity 需要查询的Activity, 比如MainActivity.class
     * @return
     */
    public static Activity getActivity(Class<?> activity) {
        for (int i = 0; i < activities.size(); i++) {
            // 判断是否是自身或者子类
            if (activities.get(i).getClass().isAssignableFrom(activity)) {
                return activities.get(i);
            }
        }
        return null;
    }
    /**
     * 除了传来的Activity其他的全部删除
     * 可以传多个Activity
     * @param clazz
     */
    public static void removeAll(Class<?>... clazz) {
        boolean isExist = false;
        for (Activity act : activities) {
            for (Class c : clazz) {
                if (act.getClass().isAssignableFrom(c)) {
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                if (!act.isFinishing()) {
                    act.finish();
                }
            } else {
                isExist = false;
            }
        }
    }
    /**
     * 关闭所有
     */
    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}

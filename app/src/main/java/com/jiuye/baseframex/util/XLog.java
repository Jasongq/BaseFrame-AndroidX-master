package com.jiuye.baseframex.util;

import android.util.Log;

import com.jiuye.baseframex.BuildConfig;

/**
 * <pre>
 *     author : GuoQiang
 *     e-mail : 849199845@qq.com
 *     time   : 2018/11/14
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public final class XLog {

    private XLog() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static final String TAG = "gq----->>";

    private static boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (isDebug())
            Log.i(TAG, msg);
    }

    public static void d(String msg) {
        if (isDebug())
            Log.d(TAG, msg);
    }

    public static void e(String msg) {
        if (isDebug())
            Log.e(TAG, msg);
    }

    public static void v(String msg) {
        if (isDebug())
            Log.v(TAG, msg);
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (isDebug())
            Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug())
            Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug())
            Log.e(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug())
            Log.v(tag, msg);
    }
}

package com.jiuye.baseframex.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.jiuye.baseframex.MyApplication;


/**
 * <pre>
 *     author : GuoQiang
 *     e-mail : 849199845@qq.com
 *     time   : 2018/11/14
 *     desc   : 吐司类(单例Toast)
 *     version: 1.0
 * </pre>
 */
public class ToastUtil {
    private ToastUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isShow = true;

    private static Context mContext= MyApplication.getInstance();
    /**
     * 短时间显示Toast
     * @param message
     */
    public static void showShort(CharSequence message) {
        if (isShow) {
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 短时间显示Toast
     * Mai
     * @param message
     */
    public static void showShortCenter( CharSequence message) {
        if (isShow) {
            message = "\b\b" + message + "\b\b";
            Toast toast = Toast.makeText(mContext, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    /**
     * 短时间显示Toast
     * @param message
     */
    public static void showShort( int message) {
        if (isShow) {
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 长时间显示Toast
     * @param message
     */
    public static void showLong (CharSequence message) {
        if (isShow) {
            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 长时间显示Toast
     * @param message
     */
    public static void showLong( int message) {
        if (isShow) {
            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 自定义显示Toast时间
     * @param message
     * @param duration
     */
    public static void show(CharSequence message, int duration) {
        if (isShow) {
            Toast.makeText(mContext, message, duration).show();
        }
    }

    public static void show( CharSequence message) {
        if (isShow) {
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 自定义显示Toast时间
     * @param message
     * @param duration
     */
    public static void show( int message, int duration) {
        if (isShow) {
            Toast.makeText(mContext, message, duration).show();
        }
    }
}

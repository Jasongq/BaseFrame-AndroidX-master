package com.jiuye.baseframex.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.jiuye.baseframex.MyApplication;


/**
 * @author : GuoQiang
 * e-mail : 849199845@qq.com
 * time   : 2018/12/08  13:41
 * desc   :
 * version: 1.0
 */
public class SPUtils {
    private SPUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }
    /**,退出登录会清除全部clearAll*/
    private static final String CONFIG_COMMON = "com.fanwei.youguangtong.common";
    /**b通用的 ,保存退出不会清除的*/
    private static final String CONFIG_USER = "com.fanwei.youguangtong.appInfo";

    /**
     * 获取SharedPreferences实例对象
     * 通用的:一般不会清除信息
     */
    private static SharedPreferences getSharedPreference() {
        return MyApplication.getInstance().getSharedPreferences(CONFIG_COMMON, Context.MODE_PRIVATE);
    }

    /**
     * 获取SharedPreferences实例对象
     * 用户信息，退出账号清除信息
     */
    private static SharedPreferences getSharedPreferenceUser() {
        return MyApplication.getInstance().getSharedPreferences(CONFIG_USER, Context.MODE_PRIVATE);
    }
//**********************************************************************************************************************************************
    /**
     * 保存一个String类型的值！
     */
    public static void putStringUser(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferenceUser().edit();
        editor.putString(key, value).apply();
    }

    /**
     * 获取String的value
     */
    public static String getStringUser(String key, String defValue) {
        SharedPreferences sharedPreference = getSharedPreferenceUser();
        return sharedPreference.getString(key, defValue);
    }
    /**
     * 保存一个Boolean类型的值！
     */
    public static void putBooleanUser(String key, Boolean value) {
        SharedPreferences.Editor editor = getSharedPreferenceUser().edit();
        editor.putBoolean(key, value).apply();
    }

    /**
     * 获取boolean的value
     */
    public static boolean getBooleanUser(String key, Boolean defValue) {
        SharedPreferences sharedPreference = getSharedPreferenceUser();
        return sharedPreference.getBoolean(key, defValue);
    }
    /**
     * 保存一个int类型的值！
     */
    public static void putIntUser(String key, int value) {
        SharedPreferences.Editor editor = getSharedPreferenceUser().edit();
        editor.putInt(key, value).apply();
    }

    /**
     * 获取int的value
     */
    public static int getIntUser(String key, int defValue) {
        SharedPreferences sharedPreference = getSharedPreferenceUser();
        return sharedPreference.getInt(key, defValue);
    }
//**********************************************************************************************************************************************
    /**
     * 保存一个String类型的值！
     */
    public static void putString(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreference().edit();
        editor.putString(key, value).apply();
    }

    /**
     * 获取String的value
     */
    public static String getString(String key, String defValue) {
        SharedPreferences sharedPreference = getSharedPreference();
        return sharedPreference.getString(key, defValue);
    }

    /**
     * 保存一个Boolean类型的值！
     */
    public static void putBoolean(String key, Boolean value) {
        SharedPreferences.Editor editor = getSharedPreference().edit();
        editor.putBoolean(key, value).apply();
    }

    /**
     * 获取boolean的value
     */
    public static boolean getBoolean(String key, Boolean defValue) {
        SharedPreferences sharedPreference = getSharedPreference();
        return sharedPreference.getBoolean(key, defValue);
    }

    /**
     * 保存一个int类型的值！
     */
    public static void putInt(String key, int value) {
        SharedPreferences.Editor editor = getSharedPreference().edit();
        editor.putInt(key, value).apply();
    }

    /**
     * 获取int的value
     */
    public static int getInt(String key, int defValue) {
        SharedPreferences sharedPreference = getSharedPreference();
        return sharedPreference.getInt(key, defValue);
    }
    /**
     * 保存一个float类型的值！
     */
    public static void putFloat(String key, float value) {
        SharedPreferences.Editor editor = getSharedPreference().edit();
        editor.putFloat(key, value).apply();
    }

    /**
     * 获取float的value
     */
    public static float getFloat(String key, Float defValue) {
        SharedPreferences sharedPreference = getSharedPreference();
        return sharedPreference.getFloat(key, defValue);
    }

    /**
     * 保存一个long类型的值！
     */
    public static void putLong(String key, long value) {
        SharedPreferences.Editor editor = getSharedPreference().edit();
        editor.putLong(key, value).apply();
    }

    /**
     * 获取long的value
     */
    public static long getLong(String key, long defValue) {
        SharedPreferences sharedPreference = getSharedPreference();
        return sharedPreference.getLong(key, defValue);
    }
    /**
     * 清空对应key数据
     */
    public static void remove(String key) {
        SharedPreferences.Editor editor = getSharedPreference().edit();
        editor.remove(key).apply();
    }
    /**
     * 清空SP数据
     */
    public static void clearAll() {
        SharedPreferences.Editor editor = getSharedPreferenceUser().edit();
        editor.clear().apply();
    }
}

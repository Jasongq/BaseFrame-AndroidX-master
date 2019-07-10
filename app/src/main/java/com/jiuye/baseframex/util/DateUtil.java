package com.jiuye.baseframex.util;

import android.text.TextUtils;

import java.util.Calendar;

/**
 * @author : GuoQiang
 * e-mail : 849199845@qq.com
 * time   : 2019/06/14  14:56
 * desc   : 日期工具类
 * version: 1.0
 */
public class DateUtil {

    public static String replaceT(String date) {
        if (TextUtils.isEmpty(date)) return "";
        if (date.contains("T")) {
            return date.replace("T", " ");
        } else {
            return date;
        }
    }
    public static int getMonth(){
        Calendar cd = Calendar.getInstance();
        return  cd.get(Calendar.MONTH)+1;
    }
}

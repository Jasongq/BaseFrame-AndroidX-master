package com.jiuye.baseframex.util;

import java.text.DecimalFormat;

/**
 * @author : GuoQiang
 * e-mail : 849199845@qq.com
 * time   : 2018/12/13  15:09
 * desc   :  数字格式显示转换
 * version: 1.0
 */
public class DecimalFormatUtil {
    /** 三位补零*/
    public static final String THREE_ZEROIZE = "000";
    /** 金额格式*/
    public static final String PATTERN_MONEY = "0.00";
    public static final String PATTERN_MONEY_1= "#,##0.00";

    /**
     * 根据格式转换
     * @param data    数据例：当data是5，pattern是000，则结果是005，当data是100时，结果是100
     * @return
     */
    public static String format(int data) {
        return new DecimalFormat(THREE_ZEROIZE).format(data);
    }

    public static String format(double money) {
        return new DecimalFormat(PATTERN_MONEY).format(money);
    }
    public static String format(String pattern,double money) {
        return new DecimalFormat(pattern).format(money);
    }
}

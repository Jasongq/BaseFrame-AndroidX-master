package com.jiuye.baseframex.util;

import android.Manifest;

/**
 * <pre>
 *     author : GuoQiang
 *     e-mail : 849199845@qq.com
 *     time   : 2018/11/16
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class CommonTools {
    public static final String BASE_URL = "http://www.wanandroid.com/";
    public static final String FILE_CONTENT_FILEPROVIDER = "com.jiuye.baseframex.fileprovider";
    public static final String IS_FIRST_INTO = "是否显示引导页/请求权限/第一次进入";
    /**
     * 权限
     */
    public static final int RC_CAMERA_PERM = 123;
    public static final int RC_LOCATION_PERM = 124;
    public static final int RC_LOCATION_PHONE_PERM = 125;
    public static final String[] COMMON_PERMISSION = { Manifest.permission.READ_PHONE_STATE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public static final String[] PER_CAMERA = {Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public static final String[] PER_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};

    /**
     * int 网络返回值
     */
    public static final int REQUEST_ERROR = -1;
    public static final int REQUEST_SUCCESS = 0;
    public static final int REQUEST_UNLOGIN = -1001;
    /**
     * EVENTBUS
     */
    public static final int EVENT_CODE_FORE_GROUND = -1;
    public static final int EVENT_CODE_BACK_GROUND = 0;
    /**
     * 用户信息
     * */
    public static final String USER_ISLOGIN = "isLogin";

}

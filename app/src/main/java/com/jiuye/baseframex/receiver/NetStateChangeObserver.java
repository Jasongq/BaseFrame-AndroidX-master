package com.jiuye.baseframex.receiver;


import com.jiuye.baseframex.util.NetworkUtil;

/**
 * @author : GuoQiang
 * e-mail : 849199845@qq.com
 * time   : 2018/12/15  11:41
 * desc   : 网络状态变化观察者
 * version: 1.0
 */
public interface NetStateChangeObserver {
    void onNetDisconnected();
    void onNetConnected(NetworkUtil.NetworkType networkType);
}

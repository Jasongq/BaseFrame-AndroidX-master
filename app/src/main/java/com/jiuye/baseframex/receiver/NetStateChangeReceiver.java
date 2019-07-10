package com.jiuye.baseframex.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import androidx.annotation.NonNull;

import com.jiuye.baseframex.util.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : GuoQiang
 * e-mail : 849199845@qq.com
 * time   : 2018/12/15  11:33
 * desc   : 网络监听
 * version: 1.0
 */
public class NetStateChangeReceiver extends BroadcastReceiver {
    private static class InstanceHolder {
        private static final NetStateChangeReceiver INSTANCE = new NetStateChangeReceiver();
    }
    private List<NetStateChangeObserver> mObservers = new ArrayList<>();
    public NetStateChangeReceiver() {
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("网络状态发生变化");
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            NetworkUtil.NetworkType networkType = NetworkUtil.getNetworkType(context);
            notifyObservers(networkType);
        }
    }
    /**
     * 注册网络监听
     */
    public static void registerReceiver(@NonNull Context context) {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(InstanceHolder.INSTANCE, intentFilter);
    }

    /**
     * 取消网络监听
     */
    public static void unregisterReceiver(@NonNull Context context) {
        context.unregisterReceiver(InstanceHolder.INSTANCE);
    }
    /**
     * 注册网络变化Observer
     */
    public static void registerObserver(NetStateChangeObserver observer) {
        if (observer == null)
            return;
        if (!InstanceHolder.INSTANCE.mObservers.contains(observer)) {
            InstanceHolder.INSTANCE.mObservers.add(observer);
        }
    }

    /**
     * 取消网络变化Observer的注册
     */
    public static void unregisterObserver(NetStateChangeObserver observer) {
        if (observer == null)
            return;
        if (InstanceHolder.INSTANCE.mObservers == null)
            return;
        InstanceHolder.INSTANCE.mObservers.remove(observer);
    }

    /**
     * 通知所有的Observer网络状态变化
     */
    private void notifyObservers(NetworkUtil.NetworkType networkType) {
        if (networkType == NetworkUtil.NetworkType.NETWORK_NO) {
            for(NetStateChangeObserver observer : mObservers) {
                observer.onNetDisconnected();
            }
        } else {
            for(NetStateChangeObserver observer : mObservers) {
                observer.onNetConnected(networkType);
            }
        }
    }
}

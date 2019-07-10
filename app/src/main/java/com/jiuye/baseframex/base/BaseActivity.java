package com.jiuye.baseframex.base;

import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.jiuye.baseframex.R;
import com.jiuye.baseframex.receiver.NetStateChangeObserver;
import com.jiuye.baseframex.receiver.NetStateChangeReceiver;
import com.jiuye.baseframex.util.ActivityCollector;
import com.jiuye.baseframex.util.EventBusUtil;
import com.jiuye.baseframex.util.LoadingUtil;
import com.jiuye.baseframex.util.MessageEvent;
import com.jiuye.baseframex.util.NetworkUtil;
import com.jiuye.baseframex.util.StatusBarUtil;
import com.jiuye.baseframex.util.ToastUtil;
import com.jiuye.baseframex.util.XLog;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * <pre>
 *     author : GuoQiang
 *     e-mail : 849199845@qq.com
 *     time   : 2018/11/14
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public abstract class BaseActivity extends AppCompatActivity implements NetStateChangeObserver {
    public Context mContext;
    private Unbinder unbinder;
    private LoadingUtil dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initTaskRoot();
        super.onCreate(savedInstanceState);
        mContext=this;
        initDialog();
        ActivityCollector.addActivity(this);
        setContentView(getLayoutId());
        setStatusBarColor();
        unbinder= ButterKnife.bind(this);
        bindMVP(savedInstanceState);
        initToolbar();
        initData();
        if (isRegisterEventBus()) {
            XLog.d("EBBaseActivity", "register");
            EventBusUtil.register(this);
        }
    }
    protected void initTaskRoot(){}
    protected abstract int getLayoutId();
    protected void initToolbar(){}
    protected void bindMVP(@Nullable Bundle savedInstanceState) { }
    protected abstract void initData();

    private void initDialog() {
        dialog = new LoadingUtil(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
    }
    protected void showDialog(){
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }
    protected void dismissDialog(){
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }
    /**
     * 设置状态栏颜色
     */
    protected void setStatusBarColor() {
        setStatusBarColor(ContextCompat.getColor(this, R.color.colorStatuBar), StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
    }
    /**
     * 设置状态栏颜色
     * @param color
     * @param statusBarAlpha 透明度
     */
    public void setStatusBarColor(@ColorInt int color, @IntRange(from = 0, to = 255) int statusBarAlpha) {
        StatusBarUtil.setColor(this, color, statusBarAlpha);
    }

    @Override
    protected void onStart() {
        super.onStart();
        isOnLine= NetworkUtil.isConnected(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (needRegisterNetworkChangeObserver()) {
            NetStateChangeReceiver.registerObserver(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (needRegisterNetworkChangeObserver()) {
            NetStateChangeReceiver.unregisterObserver(this);
        }
    }

    /**当前设备是否连接网络*/
    public static boolean isOnLine;
    /**
     * 是否需要注册网络变化的Observer,如果不需要监听网络变化,则返回false;
     */
    protected boolean needRegisterNetworkChangeObserver() {
        return true;
    }
    @Override
    public void onNetConnected(NetworkUtil.NetworkType networkType) {
        isOnLine=true;
        ToastUtil.show("网络已连接:"+networkType.toString());
    }

    @Override
    public void onNetDisconnected() {
        isOnLine=false;
        XLog.d("网络已断开:");
    }

    protected void onPauseRefreshLayout(RefreshLayout refreshLayout) {
        if (refreshLayout == null) return;
        if (refreshLayout.getState() == RefreshState.Refreshing) {
            refreshLayout.finishRefresh();
        }
        if (refreshLayout.getState() == RefreshState.Loading) {
            refreshLayout.finishLoadMore();
            //全部加载完成,没有数据了调用此方法
//            refreshLayout.finishLoadMoreWithNoMoreData();
        }
    }

    /**
     * 当用户点击A中按钮来到B时，假设B全部遮挡住了A，
     * 将依次执行A:onPause -> B:onCreate -> B:onStart -> B:onResume -> A:onStop。
     * <p>
     * 此时如果点击Back键，将依次执行B:onPause -> A:onRestart -> A:onStart -> A:onResume -> B:onStop -> B:onDestroy。
     * <p>
     * 此时如果按下Back键，系统返回到桌面，并依次执行A:onPause -> A:onStop -> A:onDestroy。
     * <p>
     * 此时如果按下Home键（非长按），系统返回到桌面，
     * 并依次执行A:onPause -> A:onStop。由此可见，Back键和Home键主要区别在于是否会执行onDestroy。
     * <p>
     * 此时如果长按Home键，不同手机可能弹出不同内容，Activity生命周期未发生变化
     */
    @Override
    protected void onRestart() {
        super.onRestart();
    }

    /**
     * 是否注册事件分发EventBus
     * @return true绑定EventBus事件分发，默认不绑定，子类需要绑定的话复写此方法返回true.
     */
    protected boolean isRegisterEventBus() {
        return false;
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusCome(MessageEvent event) {
        if (event != null) {
            receiveEvent(event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onStickyEventBusCome(MessageEvent event) {
        if (event != null) {
            receiveStickyEvent(event);
        }
    }

    /**
     * 接收到分发到事件
     * @param event 事件
     */
    protected void receiveEvent(MessageEvent event) {}

    /**
     * 接受到分发的粘性事件
     * @param event 粘性事件
     */
    protected void receiveStickyEvent(MessageEvent event) {}
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isRegisterEventBus()) {
            EventBusUtil.unregister(this);
        }
        ActivityCollector.removeActivity(this);
        unbinder.unbind();
    }
}

package com.jiuye.baseframex.base;

import android.os.Bundle;

/**
 * author : GuoQiang
 * e-mail : 849199845@qq.com
 * time   : 2018/11/23  10:27
 * desc   : 定义P层生命周期与V层同步
 * version: 1.0
 */
public interface IBasePresenter<V extends IBaseView>{
    void onMvpAttachView(V view, Bundle savedInstanceState);

    void onMvpStart();

    void onMvpResume();

    void onMvpPause();

    void onMvpStop();

    void onMvpSaveInstanceState(Bundle savedInstanceState);

    void onMvpDetachView(boolean retainInstance);

    void onMvpDestroy();
}

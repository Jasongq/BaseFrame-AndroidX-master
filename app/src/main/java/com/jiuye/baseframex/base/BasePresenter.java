package com.jiuye.baseframex.base;

import android.os.Bundle;


import com.jiuye.baseframex.mvp.IBasePresenter;
import com.jiuye.baseframex.mvp.IBaseView;

import java.lang.ref.WeakReference;

/**
 * author : GuoQiang
 * e-mail : 849199845@qq.com
 * time   : 2018/11/23  10:43
 * desc   :  Presenter生命周期包装、View的绑定和解除，P层实现的基类
 * version: 1.0
 */
public class BasePresenter<V extends IBaseView> implements IBasePresenter<V> {
    private WeakReference<V> viewRef;

    protected V getView(){
        return viewRef.get();
    }

    /**
     * @return true:非空  false:view为空
     */
    protected boolean isViewAttached(){
        return viewRef!=null&&viewRef.get()!=null;
    }
    private void attach(V view,Bundle savedInstanceState){
        viewRef=new WeakReference<V>(view);
    }
    private void detach(boolean retainInstance){
        if (viewRef!=null){
            viewRef.clear();
            viewRef=null;
        }
    }

    @Override
    public void onMvpAttachView(V view, Bundle savedInstanceState) {
        attach(view,savedInstanceState);
    }

    @Override
    public void onMvpStart() {

    }

    @Override
    public void onMvpResume() {

    }

    @Override
    public void onMvpPause() {

    }

    @Override
    public void onMvpStop() {

    }

    @Override
    public void onMvpSaveInstanceState(Bundle savedInstanceState) {

    }

    @Override
    public void onMvpDetachView(boolean retainInstance) {
        detach(retainInstance);
    }

    @Override
    public void onMvpDestroy() {

    }
}

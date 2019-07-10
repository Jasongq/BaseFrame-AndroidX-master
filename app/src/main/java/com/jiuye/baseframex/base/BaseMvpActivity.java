package com.jiuye.baseframex.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.jiuye.baseframex.mvp.IBasePresenter;
import com.jiuye.baseframex.mvp.IBaseView;

/**
 * author : GuoQiang
 * e-mail : 849199845@qq.com
 * time   : 2018/11/23  10:49
 * desc   :
 * version: 1.0
 */
public abstract class BaseMvpActivity<P extends IBasePresenter> extends BaseActivity implements IBaseView {
    protected P presenter;
    protected abstract P createPresenter();

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void bindMVP(@Nullable Bundle savedInstanceState) {
        super.bindMVP(savedInstanceState);
        presenter=createPresenter();
        if (presenter==null){
            throw new NullPointerException("Error!!Presenter is null!please created  createPresenter()");
        }
        presenter.onMvpAttachView(this,savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (presenter!=null){
            presenter.onMvpStart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (presenter!=null){
            presenter.onMvpResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (presenter!=null){
            presenter.onMvpPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (presenter!=null){
            presenter.onMvpStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter!=null){
            presenter.onMvpDetachView(false);
            presenter.onMvpDestroy();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (presenter!=null){
            presenter.onMvpSaveInstanceState(outState);
        }
    }
}

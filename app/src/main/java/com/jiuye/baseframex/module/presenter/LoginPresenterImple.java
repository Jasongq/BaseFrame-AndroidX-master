package com.jiuye.baseframex.module.presenter;

import com.jason.rxhttp.RxHttpUtils;
import com.jason.rxhttp.interceptor.Transformer;
import com.jason.rxhttp.observer.StringObserver;
import com.jiuye.baseframex.base.BasePresenter;
import com.jiuye.baseframex.http.ApiService;
import com.jiuye.baseframex.module.contract.LoginContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

/**
 * @author : GuoQiang
 * e-mail : 849199845@qq.com
 * time   : 2019/04/01  14:25
 * desc   : 登陆Demo
 * version: 1.0
 */
public class LoginPresenterImple extends BasePresenter<LoginContract.IView> implements LoginContract.IPresenter{
    /**
     * 请求调试登陆接口
     * @param userName
     * @param password
     */
    @Override
    public void login(String userName, String password) {
        if (isViewAttached()){
            getView().showLoading();
        }
        RxHttpUtils.createApi(ApiService.class)
                .login(userName,password)
                .compose(Transformer.<String>switchSchedulers())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        if (isViewAttached()){
                            getView().hideLoading();
                        }
                    }
                })
                .subscribe(new StringObserver() {
                    @Override
                    protected void onError(String errorMsg) {
                        if (!isViewAttached())return;
                        getView().loginFail(errorMsg);
                    }

                    @Override
                    protected void onSuccess(String data) {
                        if (!isViewAttached())return;
                        getView().loginSuccess(data);
                    }
                });

    }
}

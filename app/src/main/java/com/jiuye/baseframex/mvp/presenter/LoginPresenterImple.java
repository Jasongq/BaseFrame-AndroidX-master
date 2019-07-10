package com.jiuye.baseframex.mvp.presenter;

import com.jiuye.baseframex.base.BasePresenter;
import com.jiuye.baseframex.mvp.contract.LoginContract;

/**
 * @author : GuoQiang
 * e-mail : 849199845@qq.com
 * time   : 2019/04/01  14:25
 * desc   :
 * version: 1.0
 */
public class LoginPresenterImple extends BasePresenter<LoginContract.View> implements LoginContract.IPresenter{
    @Override
    public void login(String userName, String password) {
        if (isViewAttached()){
            getView().showLoading();
        }
        
    }
}

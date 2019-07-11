package com.jiuye.baseframex.module.contract;

import com.jiuye.baseframex.base.IBasePresenter;
import com.jiuye.baseframex.base.IBaseView;

/**
 * author : GuoQiang
 * e-mail : 849199845@qq.com
 * time   : 2018/11/23  14:40
 * desc   : P层与V层接口定义 -Login
 * version: 1.0
 */
public interface LoginContract {
    interface IView extends IBaseView {
        void loginSuccess(Object data);
        void loginFail(String errorMsg);
    }
     interface IPresenter extends IBasePresenter<IView> {
        void login(String userName, String password);
    }
}

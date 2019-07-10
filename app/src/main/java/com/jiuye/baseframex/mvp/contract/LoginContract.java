package com.jiuye.baseframex.mvp.contract;

import com.jiuye.baseframex.mvp.IBasePresenter;
import com.jiuye.baseframex.mvp.IBaseView;

/**
 * author : GuoQiang
 * e-mail : 849199845@qq.com
 * time   : 2018/11/23  14:40
 * desc   : P层与V层接口定义 -Login
 * version: 1.0
 */
public interface LoginContract {
    interface View extends IBaseView {
        void loginSuccess(Object data);
        void loginFail(String errorMsg);
    }
     interface IPresenter extends IBasePresenter<View> {
        void login(String userName, String password);
    }
}

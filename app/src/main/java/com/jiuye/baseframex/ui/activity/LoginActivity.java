package com.jiuye.baseframex.ui.activity;


import com.jiuye.baseframex.R;
import com.jiuye.baseframex.base.BaseMvpActivity;
import com.jiuye.baseframex.mvp.contract.LoginContract;
import com.jiuye.baseframex.mvp.presenter.LoginPresenterImple;

public class LoginActivity extends BaseMvpActivity<LoginContract.IPresenter> implements LoginContract.View{

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }
    @Override
    protected LoginContract.IPresenter createPresenter() {
        return new LoginPresenterImple();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initToolbar() {

    }

    @Override
    public void loginSuccess(Object data) {

    }

    @Override
    public void loginFail(String errorMsg) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {

    }
}

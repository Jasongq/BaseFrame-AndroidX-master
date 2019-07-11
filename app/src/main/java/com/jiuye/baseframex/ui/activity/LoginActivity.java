package com.jiuye.baseframex.ui.activity;


import com.jiuye.baseframex.R;
import com.jiuye.baseframex.base.BaseMvpActivity;
import com.jiuye.baseframex.module.contract.LoginContract;
import com.jiuye.baseframex.module.presenter.LoginPresenterImple;
/**
 * author : GuoQiang
 * e-mail : 849199845@qq.com
 * time   : 2018/11/23  14:40
 * desc   : 登陆页面
 * version: 1.0
 */
public class LoginActivity extends BaseMvpActivity<LoginContract.IPresenter> implements LoginContract.IView{

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
        presenter.login("18052233779","123456");
    }

    /**
     * 这里可以对Toolbar进行一些初始化操作
     */
    @Override
    protected void initToolbar() {

    }
    /**
     * 登陆成功
     * @param data
     */
    @Override
    public void loginSuccess(Object data) {

    }

    /**
     * 登陆失败
     * @param errorMsg
     */
    @Override
    public void loginFail(String errorMsg) {

    }

    /**
     * 显示加载对话框
     */
    @Override
    public void showLoading() {

    }
    /**
     * 隐藏加载对话框
     */
    @Override
    public void hideLoading() {

    }

}

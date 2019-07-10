package com.jason.rxhttp.interfaces;

/**
 * desc : 接口化处理loadingView，突破之前只能用dialog的局限
 */
public interface ILoadingView {
    /**
     * 显示loadingView
     */
    void showLoadingView();

    /**
     * 隐藏loadingView
     */
    void hideLoadingView();
}

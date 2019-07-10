package com.jason.rxhttp.observer;

import android.text.TextUtils;

import com.jason.rxhttp.base.BaseObserver;
import com.jason.rxhttp.bean.BaseData;
import com.jason.rxhttp.utils.ToastUtils;

import io.reactivex.disposables.Disposable;

/**
 * @author Allen
 * <p>
 * 针对特定格式的时候设置的通用的Observer
 * 用户可以根据自己需求自定义自己的类继承BaseDataObserver<T>即可
 * 适用于
 * {
 * "code":200,
 * "msg":"成功"
 * "data":{
 * "userName":"test"
 * "token":"abcdefg123456789"
 * "uid":"1"}
 * }
 */

public abstract class DataObserver<T> extends BaseObserver<BaseData<T>> {

    /**
     * 失败回调
     *
     * @param errorMsg 错误信息
     */
    protected abstract void onError(String errorMsg);

    /**
     * 成功回调
     *
     * @param data 结果
     */
    protected abstract void onSuccess(T data);

    @Override
    public void doOnSubscribe(Disposable d) {
    }

    @Override
    public void doOnError(String errorMsg) {
        if (!isHideToast() && !TextUtils.isEmpty(errorMsg)) {
            ToastUtils.showToast(errorMsg);
        }
        onError(errorMsg);
    }

    @Override
    public void doOnNext(BaseData<T> data) {
        onSuccess(data.getData());
        //可以根据需求对code统一处理
//        switch (data.getCode()) {
//            case 200:
//                onSuccess(data.getData());
//                break;
//            case 300:
//            case 500:
//                onError(data.getMsg());
//                break;
//            default:
//        }
    }

    @Override
    public void doOnCompleted() {
    }


}

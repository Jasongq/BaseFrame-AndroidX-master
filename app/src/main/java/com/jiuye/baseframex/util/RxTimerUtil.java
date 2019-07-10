package com.jiuye.baseframex.util;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * @author : GuoQiang
 * e-mail : 849199845@qq.com
 * time   : 2018/12/08  15:28
 * desc   :
 * version: 1.0
 */
public class RxTimerUtil {
    private static Disposable mDisposable;

    /**
     *milliseconds毫秒后执行指定动作
     * @param milliseconds
     * @param rxAction
     */
    public static void timer(long milliseconds,final IRxAction rxAction){
        Observable.timer(milliseconds,TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                        mDisposable=disposable;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        if (rxAction!=null){
                            rxAction.action(aLong);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        cancel();
                    }

                    @Override
                    public void onComplete() {
                        cancel();
                    }
                });
    }

    /**
     * 每隔minutes分钟后执行指定动作
     * @param minutes
     * @param rxAction
     */
    public static void interval(long minutes, final IRxAction rxAction){
        Observable.interval(minutes,TimeUnit.MINUTES)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable=mDisposable;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        if (rxAction!=null){
                            rxAction.action(aLong);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 取消订阅
     */
    public static void cancel(){
        if(mDisposable!=null&&!mDisposable.isDisposed()){
            mDisposable.dispose();
            XLog.e("====定时器取消======");
        }
    }
    public interface IRxAction {
        void action(long number);
    }
}

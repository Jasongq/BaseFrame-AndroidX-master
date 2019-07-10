package com.jiuye.baseframex.util;

import android.os.CountDownTimer;
import android.widget.Button;

/**
 * 60秒获取验证码工具类
 * <一句话功能简述>
 * [相关类/方法]
 */
public class TimerCountDown extends CountDownTimer {
    private Button bnt;
    private int  resid;

    public TimerCountDown(long millisInFuture, long countDownInterval, Button bnt, int backgroundResourceId) {
        super(millisInFuture, countDownInterval);
        this.bnt = bnt;
        this.resid=backgroundResourceId;
    }

    public TimerCountDown(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onFinish() {
        // TODO Auto-generated method stub
        bnt.setClickable(true);
        bnt.setBackgroundResource(resid);//core_shape_blue_2
        bnt.setText("重新获取");
        bnt.setEnabled(true);
    }

    @Override
    public void onTick(long arg0) {
        // TODO Auto-generated method stub
        bnt.setClickable(false);
//        bnt.setBackgroundResource(R.color.color_pressed);
        bnt.setBackgroundResource(resid);
        bnt.setText("等待 " + arg0 / 1000 + " s");
    }

    public void onSuccess() {
        bnt.setClickable(true);
        bnt.setBackgroundResource(resid);
        bnt.setText("获取验证码");
        bnt.setEnabled(true);
    }
}

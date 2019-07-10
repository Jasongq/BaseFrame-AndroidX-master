package com.jiuye.baseframex.util;

/**
 * @author : GuoQiang
 * e-mail : 849199845@qq.com
 * time   : 2019/06/24  10:21
 * desc   : Event事件类
 *          code对应不同的消息，data为发送的内容
 * version: 1.0
 */
public class MessageEvent<T> {
    private int code;
    private T data;

    public MessageEvent(int code, T data) {
        this.code = code;
        this.data = data;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}

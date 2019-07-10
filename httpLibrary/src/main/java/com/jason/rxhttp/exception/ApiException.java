package com.jason.rxhttp.exception;

import com.jason.rxhttp.utils.ToastUtils;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializer;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.io.NotSerializableException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import retrofit2.HttpException;

/**
 * 异常类型
 */
public class ApiException extends Exception {
    private static final int ERROR_REQUEST = 400;
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;


    private final int code;
    private String message;

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
        this.message = throwable.getMessage();
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public static ApiException handleException(Throwable e) {
        ApiException ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ApiException(httpException, httpException.code());
//            if (httpException.code()==ERROR_REQUEST){
//                try {
//                    ex.message = httpException.response().errorBody().string();
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
//            }else
            if (httpException.code() == UNAUTHORIZED) {
                ex.message = "401-Authenticate";
                //gotoLoginActivity();
            } else {
                ex.message = httpException.getMessage();
            }
            ex.message = httpException.getMessage();
        } else if (e instanceof SocketTimeoutException) {
            ex = new ApiException(e, ERROR.TIMEOUT_ERROR);
            ex.message = "网络连接超时，请检查您的网络状态，稍后重试！";
        } else if (e instanceof ConnectException) {
            ex = new ApiException(e, ERROR.TIMEOUT_ERROR);
            ex.message = "网络连接异常，请检查您的网络状态，稍后重试！";
        } else if (e instanceof ConnectTimeoutException) {
            ex = new ApiException(e, ERROR.TIMEOUT_ERROR);
            ex.message = "网络连接超时，请检查您的网络状态，稍后重试！";
        } else if (e instanceof UnknownHostException) {
            ex = new ApiException(e, ERROR.TIMEOUT_ERROR);
            ex.message = "网络连接异常，请检查您的网络状态，稍后重试！";
        } else if (e instanceof NullPointerException) {
            ex = new ApiException(e, ERROR.NULL_POINTER_EXCEPTION);
            ex.message = "空指针异常";
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new ApiException(e, ERROR.SSL_ERROR);
            ex.message = "证书验证失败";
        } else if (e instanceof ClassCastException) {
            ex = new ApiException(e, ERROR.CAST_ERROR);
            ex.message = "类型转换错误";
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof JsonSerializer
                || e instanceof NotSerializableException
                || e instanceof ParseException) {
            ex = new ApiException(e, ERROR.PARSE_ERROR);
            ex.message = "数据解析错误";
        } else if (e instanceof IllegalStateException) {
            ex = new ApiException(e, ERROR.ILLEGAL_STATE_ERROR);
            ex.message = e.getMessage();
        } else {
            ex = new ApiException(e, ERROR.UNKNOWN);
        }
        return ex;
    }

    /**
     * 约定异常
     */
    public static class ERROR {
        /**
         * 未知错误
         */
        public static final int UNKNOWN = 1000;
        /**
         * 连接超时
         */
        public static final int TIMEOUT_ERROR = 1001;
        /**
         * 空指针错误
         */
        public static final int NULL_POINTER_EXCEPTION = 1002;

        /**
         * 证书出错
         */
        public static final int SSL_ERROR = 1003;

        /**
         * 类转换错误
         */
        public static final int CAST_ERROR = 1004;

        /**
         * 解析错误
         */
        public static final int PARSE_ERROR = 1005;

        /**
         * 非法数据异常
         */
        public static final int ILLEGAL_STATE_ERROR = 1006;

    }

    /**
     * 跳转到登录界面
     */
    private static void gotoLoginActivity() {
       /* ARouter.getInstance()
                .build("/user/LoginActivity")
                .navigation();*/
//        ActivityCollector.removeAll();
//        Intent intent = new Intent();
//        intent.setAction("com.fanwei.youguangtong.login.ACTION_START");
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        MyApplication.getInstance().startActivity(intent);
        ToastUtils.showToast("401-未授权");
    }
}

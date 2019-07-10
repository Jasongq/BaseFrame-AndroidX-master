package com.jason.rxhttp.interceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.jason.rxhttp.utils.NetUtils.isNetworkConnected;

/**
 * <pre>
 *      desc    : 网络缓存 参考 https://www.jianshu.com/p/cf59500990c7
 *      version : 1.0
 * </pre>
 */

public class NetCacheInterceptor implements Interceptor {
    /**
     * 默认缓存60秒
     */
    private int cacheTime;

    public NetCacheInterceptor(int cacheTime) {
        this.cacheTime = cacheTime;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        boolean connected = isNetworkConnected();
        if (connected) {
            //如果有网络，默认缓存60s
            Response response = chain.proceed(request);
            CacheControl.Builder builder = new CacheControl.Builder()
                    .maxAge(cacheTime, TimeUnit.SECONDS);

            return response.newBuilder()
                    .header("Cache-Control", builder.build().toString())
                    .removeHeader("Pragma")
                    .build();
        }
        //如果没有网络，不做处理，直接返回
        return chain.proceed(request);
    }

}

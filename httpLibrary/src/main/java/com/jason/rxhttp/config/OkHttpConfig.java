package com.jason.rxhttp.config;

import android.content.Context;
import android.text.TextUtils;

import com.jason.rxhttp.cookie.CookieJarImpl;
import com.jason.rxhttp.cookie.store.CookieStore;
import com.jason.rxhttp.http.SSLUtils;
import com.jason.rxhttp.interceptor.HeaderInterceptor;
import com.jason.rxhttp.interceptor.HttpLoggingInterceptor;
import com.jason.rxhttp.interceptor.NetCacheInterceptor;
import com.jason.rxhttp.interceptor.NoNetCacheInterceptor;
import com.jason.rxhttp.interfaces.BuildHeadersListener;
import com.jason.rxhttp.utils.OkLogger;

import java.io.File;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import javax.net.ssl.HostnameVerifier;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * <pre>
 *      date    : 2018/05/29
 *      desc    : 统一OkHttp配置信息
 *      version : 1.0
 * </pre>
 */
public class OkHttpConfig {
    private static String defaultCachePath;
    private static final long defaultCacheSize = 1024 * 1024 * 100;
    private static final long defaultTimeout = 10;


    private static OkHttpConfig instance;

    private static OkHttpClient.Builder okHttpClientBuilder;

    private static OkHttpClient okHttpClient;

    public OkHttpConfig() {
        okHttpClientBuilder = new OkHttpClient.Builder();
    }

    public static OkHttpConfig getInstance() {
        if (instance == null) {
            synchronized (OkHttpConfig.class) {
                if (instance == null) {
                    instance = new OkHttpConfig();
                }
            }
        }
        return instance;
    }

    public static OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public static class Builder {
        public Context context;
        private boolean isDebug;
        private boolean isCache;
        private int cacheTime = 60;
        private int noNetCacheTime = 10;
        private String cachePath;
        private long cacheMaxSize;
        private CookieStore cookieStore;
        private long readTimeout;
        private long writeTimeout;
        private long connectTimeout;
        private InputStream bksFile;
        private String password;
        private InputStream[] certificates;
        private Interceptor[] interceptors;
        private BuildHeadersListener buildHeadersListener;
        private HostnameVerifier hostnameVerifier;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setHeaders(BuildHeadersListener buildHeadersListener) {
            this.buildHeadersListener = buildHeadersListener;
            return this;
        }

        public Builder setDebug(boolean isDebug) {
            this.isDebug = isDebug;
            return this;
        }

        public Builder setCache(boolean isCache) {
            this.isCache = isCache;
            return this;
        }

        public Builder setHasNetCacheTime(int cacheTime) {
            this.cacheTime = cacheTime;
            return this;
        }

        public Builder setNoNetCacheTime(int noNetCacheTime) {
            this.noNetCacheTime = noNetCacheTime;
            return this;
        }

        public Builder setCachePath(String cachePath) {
            this.cachePath = cachePath;
            return this;
        }

        public Builder setCacheMaxSize(long cacheMaxSize) {
            this.cacheMaxSize = cacheMaxSize;
            return this;
        }

        public Builder setCookieType(CookieStore cookieStore) {
            this.cookieStore = cookieStore;
            return this;
        }

        public Builder setReadTimeout(long readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        public Builder setWriteTimeout(long writeTimeout) {
            this.writeTimeout = writeTimeout;
            return this;
        }

        public Builder setConnectTimeout(long connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        public Builder setAddInterceptor(Interceptor... interceptors) {
            this.interceptors = interceptors;
            return this;
        }

        public Builder setSslSocketFactory(InputStream... certificates) {
            this.certificates = certificates;
            return this;
        }

        public Builder setSslSocketFactory(InputStream bksFile, String password, InputStream... certificates) {
            this.bksFile = bksFile;
            this.password = password;
            this.certificates = certificates;
            return this;
        }

        public Builder setHostnameVerifier(HostnameVerifier hostnameVerifier) {
            this.hostnameVerifier = hostnameVerifier;
            return this;
        }


        public OkHttpClient build() {

            OkHttpConfig.getInstance();

            setCookieConfig();
            setCacheConfig();
            setHeadersConfig();
            setSslConfig();
            setHostnameVerifier();
            addInterceptors();
            setTimeout();
            setDebugConfig();

            okHttpClient = okHttpClientBuilder.build();
            return okHttpClient;
        }

        private void addInterceptors() {
            if (null != interceptors) {
                for (Interceptor interceptor : interceptors) {
                    okHttpClientBuilder.addInterceptor(interceptor);
                }
            }
        }

        /**
         * 配置开发环境
         */
        private void setDebugConfig() {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("Body");
            if (isDebug) {
                OkLogger.debug(true);
                //log打印级别，决定了log显示的详细程度 默认：HttpLoggingInterceptor.Level.BODY
                loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
                //log颜色级别，决定了log在控制台显示的颜色  默认：Level.INFO
                loggingInterceptor.setColorLevel(Level.INFO);
            }else {
                OkLogger.debug(false);
                loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.NONE);
                loggingInterceptor.setColorLevel(Level.OFF);
            }
            //添加默认debug日志
            okHttpClientBuilder.addInterceptor(loggingInterceptor);
        }


        /**
         * 配置headers
         */
        private void setHeadersConfig() {
            if (buildHeadersListener != null) {
                okHttpClientBuilder.addInterceptor(new HeaderInterceptor() {
                    @Override
                    public Map<String, String> buildHeaders() {
                        return buildHeadersListener.buildHeaders();
                    }
                });
            }

        }

        /**
         * 配饰cookie保存到sp文件中
         */
        private void setCookieConfig() {
            if (null != cookieStore) {
                okHttpClientBuilder.cookieJar(new CookieJarImpl(cookieStore));
            }
        }

        /**
         * 配置缓存
         */
        private void setCacheConfig() {
            File externalCacheDir = context.getExternalCacheDir();
            if (null == externalCacheDir) {
                return;
            }
            defaultCachePath = externalCacheDir.getPath() + "/RxHttpCacheData";
            if (isCache) {
                Cache cache;
                if (!TextUtils.isEmpty(cachePath) && cacheMaxSize > 0) {
                    cache = new Cache(new File(cachePath), cacheMaxSize);
                } else {
                    cache = new Cache(new File(defaultCachePath), defaultCacheSize);
                }

                okHttpClientBuilder
                        .cache(cache)
                        .addInterceptor(new NoNetCacheInterceptor(noNetCacheTime))
                        .addNetworkInterceptor(new NetCacheInterceptor(cacheTime));
            }
        }

        /**
         * 配置超时信息
         */
        private void setTimeout() {
            okHttpClientBuilder.readTimeout(readTimeout == 0 ? defaultTimeout : readTimeout, TimeUnit.SECONDS);
            okHttpClientBuilder.writeTimeout(writeTimeout == 0 ? defaultTimeout : writeTimeout, TimeUnit.SECONDS);
            okHttpClientBuilder.connectTimeout(connectTimeout == 0 ? defaultTimeout : connectTimeout, TimeUnit.SECONDS);
            okHttpClientBuilder.retryOnConnectionFailure(true);
        }

        /**
         * 配置证书
         */
        private void setSslConfig() {
            SSLUtils.SSLParams sslParams = null;

            if (null == certificates) {
                //信任所有证书,不安全有风险
                sslParams = SSLUtils.getSslSocketFactory();
            } else {
                if (null != bksFile && !TextUtils.isEmpty(password)) {
                    //使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
                    sslParams = SSLUtils.getSslSocketFactory(bksFile, password, certificates);
                } else {
                    //使用预埋证书，校验服务端证书（自签名证书）
                    sslParams = SSLUtils.getSslSocketFactory(certificates);
                }
            }

            okHttpClientBuilder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
        }

        private void setHostnameVerifier() {
            if (null == hostnameVerifier) {
                okHttpClientBuilder.hostnameVerifier(SSLUtils.UnSafeHostnameVerifier);
            } else {
                okHttpClientBuilder.hostnameVerifier(hostnameVerifier);
            }
        }
    }
}

package com.jiuye.baseframex;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;

import androidx.multidex.MultiDex;

import com.jason.rxhttp.RxHttpUtils;
import com.jason.rxhttp.config.OkHttpConfig;
import com.jason.rxhttp.cookie.store.SPCookieStore;
import com.jiuye.baseframex.receiver.NetStateChangeReceiver;
import com.jiuye.baseframex.util.CommonTools;
import com.jiuye.baseframex.util.CrashHandler;
import com.jiuye.baseframex.util.EventBusUtil;
import com.jiuye.baseframex.util.MessageEvent;
import com.jiuye.baseframex.util.XLog;

import okhttp3.OkHttpClient;

/**
 * @author : GuoQiang
 * e-mail : 849199845@qq.com
 * time   : 2019/07/09  11:33
 * desc   :
 * version: 1.0
 */
public class MyApplication extends Application {
    private static MyApplication instance=null;
    public static synchronized MyApplication getInstance(){
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        MultiDex.install(this);
        registerActivityLifecycleCallbacks(lifecycleCallbacks);
        CrashHandler.getInstance().init(this);
        initRxHttp();
    }
    private void initRxHttp() {
        //InputStream cerInputStream = new Buffer().writeUtf8(CommonTools.CER).inputStream();
//        InputStream cerInputStream = null;
//        try {
//            cerInputStream = getAssets().open("12306.cer");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //直接将证书转为字符串 ，使用字符串

        //HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(new InputStream[]{new Buffer().writeUtf8(CER_12306).inputStream()},null, null);
        OkHttpClient okHttpClient = new OkHttpConfig.Builder(this)
//                .setHeaders(new BuildHeadersListener() {
//                    @Override
//                    public Map<String, String> buildHeaders() {
//                        HashMap<String, String> hashMap = new HashMap<>();
//                        //hashMap.put("Connection", "close");
//                        hashMap.put("Authorization Bearer", "");
//                        return hashMap;
//                    }
//                })
                //开启缓存策略(默认false)
                //1、在有网络的时候，先去读缓存，缓存时间到了，再去访问网络获取数据；
                //2、在没有网络的时候，去读缓存中的数据。
                .setCache(true)
                .setHasNetCacheTime(0)//默认有网络时候缓存60秒，0秒表示有网络不缓存
                //全局持久话cookie,保存到内存（new MemoryCookieStore()）或者保存到本地（new SPCookieStore(this)）
                //不设置的话，默认不对cookie做处理
                .setCookieType(new SPCookieStore(this))
                //可以添加自己的拦截器(比如使用自己熟悉三方的缓存库等等)
                //.setAddInterceptor(null)
                //全局ssl证书认证
                //1、信任所有证书,不安全有风险（默认信任所有证书）
                //.setSslSocketFactory()
                //2、使用预埋证书，校验服务端证书（自签名证书）
                //.setSslSocketFactory(cerInputStream)
                //3、使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
                //.setSslSocketFactory(bksInputStream,"123456",cerInputStream)
                //设置Hostname校验规则，默认实现返回true，需要时候传入相应校验规则即可
                //.setHostnameVerifier(null)
                //全局超时配置
                .setReadTimeout(30)
                //全局超时配置
                .setWriteTimeout(30)
                //全局超时配置
                .setConnectTimeout(15)
                //全局是否打开请求log日志
                .setDebug(BuildConfig.LOG_DEBUG)
                //.setAddInterceptor(loggingInterceptor)
                .build();

        RxHttpUtils.getInstance()
                .init(this)
                .config()
                //.setCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //.setConverterFactory(ScalarsConverterFactory.create(), GsonConverterFactory.create(GsonAdapter.buildGson()))
                //配置全局baseUrl
                .setBaseUrl(CommonTools.BASE_URL)
                //开启全局配置
                .setOkClient(okHttpClient);
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        NetStateChangeReceiver.unregisterReceiver(this);
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1) {
            //非默认值
            getResources();
        }
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {
            //非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();
            //设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                createConfigurationContext(newConfig);
            } else {
                res.updateConfiguration(newConfig, res.getDisplayMetrics());
            }
        }
        return super.getResources();
    }

    private int activityAmount = 0;
    private ActivityLifecycleCallbacks lifecycleCallbacks=new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {
            activityAmount++;
            XLog.v("--------数量"+activityAmount);
            if (activityAmount==1){
                //回到前台
                EventBusUtil.sendEvent(new MessageEvent<>(CommonTools.EVENT_CODE_FORE_GROUND,""));
            }
        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {
            activityAmount--;
            if (activityAmount ==0){
                EventBusUtil.sendEvent(new MessageEvent<>(CommonTools.EVENT_CODE_BACK_GROUND,""));
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    };
}

package com.jiuye.baseframex.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * <pre>
 *     author : GuoQiang
 *     e-mail : 849199845@qq.com
 *     time   : 2018/11/12
 *     desc   : 捕获全局异常类
 *              需在MyApplication初始化
 *              CrashHandler.getInstance().init(this);
 *     version: 1.0
 * </pre>
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "CrashHandler";
    private static final boolean DEBUG=true;

    private static String PATH;
    private static final String FILE_NAME="crash";
    private static final String FILE_NAME_SUFFIX=".track";

    //构造方法私有，防止外部构造多个实例，即采用单例模式
    private CrashHandler(){}
    public static CrashHandler getInstance(){
        return CrashHandlerHolder.sInstance;
    }
    public static class CrashHandlerHolder{
        public static CrashHandler sInstance=new CrashHandler();
    }

    //系统默认的异常处理（默认情况下，系统会终止当前的异常程序）
    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;

    private Context mContext;

    /**
     * 初始化异常捕获类
     * @param context
     */
    public void init(Context context){
        //获取系统默认的异常处理器
        mDefaultCrashHandler=Thread.getDefaultUncaughtExceptionHandler();
        //将当前实例设为系统默认的异常处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
        //获取Context，方便内部使用
        mContext = context.getApplicationContext();
        PATH=Environment.getExternalStorageDirectory().getPath()+"/Android/data/"+mContext.getPackageName()+"/log/";
    }

    /**
     * 这个是最关键的函数，当程序中有未被捕获的异常，系统将会自动调用#uncaughtException方法
     * thread为出现未捕获异常的线程，ex为未捕获的异常，有了这个ex，我们就可以得到异常信息。
     * @param thread
     * @param ex
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        try {
            //导出异常信息到SD卡中
            dumpExceptionToSDCard(ex);
            //这里可以通过网络上传异常信息到服务器，便于开发人员分析日志从而解决bug
            uploadExceptionToServer(ex);
        }catch (IOException e){
            e.printStackTrace();
        }
        //打印出当前调用栈信息
        ex.printStackTrace();

        //如果系统提供了默认的异常处理器，则交给系统去结束我们的程序，否则就由我们自己结束自己
        if (mDefaultCrashHandler!=null){
            mDefaultCrashHandler.uncaughtException(thread,ex);
        }else {
            Process.killProcess(Process.myPid());
        }
    }

    private void dumpExceptionToSDCard(Throwable ex) throws IOException{
        //如果SD卡不存在或无法使用，则无法把异常信息写入SD卡
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            if (DEBUG){
                Log.w(TAG, "sdcard unmounted,skip dump exception");
                return;
            }
        }
        File dir=new File(PATH);
        if (!dir.exists()){
            dir.mkdirs();
        }
        long current = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault()).format(new Date(current));
        //以当前时间创建log文件
        File file=new File(PATH+FILE_NAME+time+FILE_NAME_SUFFIX);
        try {
            PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter(file)));
            //导出发生异常的时间
            pw.print(time);
            //导出手机信息
            dumpPhoneInfo(pw);
            pw.println();
            //导出异常的调用栈信息
            ex.printStackTrace(pw);
            pw.close();
        }catch (Exception e){
            Log.e(TAG, "dump crash info failed");
        }
    }

    private void dumpPhoneInfo(PrintWriter pw) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = mContext.getPackageManager();
        PackageInfo packageInfo=packageManager.getPackageInfo(mContext.getPackageName(),PackageManager.GET_ACTIVITIES);
        pw.println();
        pw.println("主板:"+Build.BOARD);
        //系统定制商
        pw.println("系统定制商"+Build.BRAND);
        //硬件制造商
        pw.println("手机制造商"+Build.MANUFACTURER);
        //硬件识别码
        pw.println("硬件识别码:"+Build.FINGERPRINT);
        //硬件序列号
        pw.println("硬件序列号:"+Build.SERIAL);
        //当前App版本
        pw.println("App Version:"+packageInfo.versionCode+"-"+packageInfo.versionName);
        //android版本号
        pw.println("OS Version:"+Build.VERSION.RELEASE+"_"+Build.VERSION.SDK_INT);
        //手机型号
        pw.println("Model:"+Build.MODEL);
        //cpu架构
        pw.println("CPU ABI:"+Build.CPU_ABI);
        //Time
        pw.println("TIME:"+Build.TIME);

    }

    /**
     * 上传异常信息到服务器
     * @param ex
     */
    private void uploadExceptionToServer(Throwable ex) {
        //TODO Upload Exception Message To Your Web Server

    }
}

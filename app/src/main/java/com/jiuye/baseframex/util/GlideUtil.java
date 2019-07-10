package com.jiuye.baseframex.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.jiuye.baseframex.R;
import com.jiuye.baseframex.util.transformations.BlurTransformation;
import com.jiuye.baseframex.util.transformations.CropCircleTransformation;
import com.jiuye.baseframex.util.transformations.CropSquareTransformation;
import com.jiuye.baseframex.util.transformations.GrayscaleTransformation;
import com.jiuye.baseframex.util.transformations.RoundedCornersTransformation;

import java.io.File;
import java.math.BigDecimal;

/**
 * @author : GuoQiang
 * e-mail : 849199845@qq.com
 * time   : 2019/04/01  14:19
 * desc   :
 * version: 1.0
 */
public class GlideUtil {

    public static void load(@NonNull Context context, @Nullable String string, @NonNull ImageView view){
        Glide.with(context)
                .load(string)
                .placeholder(R.drawable.ic_placeholder_banner)
                .error(R.drawable.ic_placeholder_banner)
//                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)
//                        .error(R.drawable.ic_placeholder)
//                        .placeholder(R.drawable.ic_placeholder))
                .into(view);
    }
    public static void load(@NonNull Context context, @RawRes @DrawableRes @Nullable Integer resourceId, @NonNull ImageView view){
        Glide.with(context)
                .load(resourceId)
                .placeholder(R.drawable.ic_placeholder_banner)
                .error(R.drawable.ic_placeholder_banner)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(view);
    }
    public static void loadCircle(@NonNull Context context, @RawRes @DrawableRes @Nullable Integer resourceId, @NonNull ImageView view){
        Glide.with(context)
                .load(resourceId)
                .placeholder(R.drawable.ic_placeholder_head)
                .error(R.drawable.ic_placeholder_head)
                .apply(RequestOptions.circleCropTransform())
                .into(view);
    }

    public static void loadCircle(@NonNull Context context, @Nullable String string, @NonNull ImageView view){
        Glide.with(context)
                .asBitmap()
                .load(string)
                .placeholder(R.drawable.ic_placeholder_head)
                .error(R.drawable.ic_placeholder_head)
                .apply(RequestOptions.circleCropTransform())
                .into(view);
    }

    /**
     * 处理图片为正方形
     */
    @Deprecated
    public static void loadCropCircle(@NonNull Context context, @Nullable String string, @NonNull ImageView view){
        Glide.with(context)
                .load(string)
                .apply(RequestOptions.bitmapTransform(new CropCircleTransformation()))
                .into(view);
    }
    /**
     * 处理图片四周圆角
     * @param cornerType 处理类型
     */
    public static void loadRoundedCorners(@NonNull Context context, @Nullable String string, @NonNull ImageView view, RoundedCornersTransformation.CornerType cornerType){
        Glide.with(context)
                .load(string)
                .placeholder(R.drawable.ic_placeholder_banner)
                .error(R.drawable.ic_placeholder_banner)
                .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(10, 0,cornerType)))
                .into(view);
    }

    /**
     * 处理图片为正方形
     */
    public static void loadCropSquare(@NonNull Context context, @Nullable String string, @NonNull ImageView view){
        Glide.with(context)
                .load(string)
                .apply(RequestOptions.bitmapTransform(new CropSquareTransformation()))
                .into(view);
    }
    /**
     * 处理图片灰化
     */
    public static void loadGrayScale(@NonNull Context context, @Nullable String string, @NonNull ImageView view){
        Glide.with(context)
                .load(string)
                .apply(RequestOptions.bitmapTransform(new GrayscaleTransformation()))
                .into(view);
    }
    /**
     * 处理图片模糊效果，并非马赛克
     */
    public static void loadBlur(@NonNull Context context, @Nullable String string, @NonNull ImageView view){
        Glide.with(context)
                .load(string)
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25)))
                .into(view);
    }

    /**
     * 处理图片模糊效果，并非马赛克
     */
    public static void loadBitmap(@NonNull Context context, @Nullable String string, SimpleTarget<Bitmap> target){
        Glide.with(context)
                .asBitmap()
                .load(string)
                .into(target);
    }

    /**
     * 加载第1秒的帧数作为封面 1,000,000
     *  url就是视频的地址
     */
    public static void loadCover(Context context,String url,ImageView imageView,long frameTimeMicros) {
        RequestOptions requestOptions = RequestOptions.frameOf(frameTimeMicros).centerCrop();
        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(url)
                .into(imageView);
    }

    /**
     * 清除图片磁盘缓存
     */
    public static void clearImageDiskCache(Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context).clearDiskCache();
                        // BusUtil.getBus().post(new GlideCacheClearSuccessEvent());
                    }
                }).start();
            } else {
                Glide.get(context).clearDiskCache();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 清除图片内存缓存
     * clearMemory
     */
    public static void clearImageMemoryCache(Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                //只能在主线程执行
                Glide.get(context).clearMemory();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除图片所有缓存
     */
    public static void clearImageAllCache(Context context) {
        clearImageDiskCache(context);
        clearImageMemoryCache(context);
        String ImageExternalCatchDir=context.getExternalCacheDir()+ ExternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR;
        deleteFolderFile(ImageExternalCatchDir, true);
    }
    /**
     * 获取Glide的缓存大小
     *
     * @return CacheSize
     */
    public static String getCacheSize(Context context) {
        try {
            return getFormatSize(getFolderSize(new File(context.getCacheDir() + "/"+ InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取指定文件夹内所有文件大小的和
     *
     * @param file file
     * @return size
     * @throws Exception
     */
    private static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 删除指定目录下的文件，这里用于缓存的删除
     * @param filePath filePath
     * @param deleteThisPath deleteThisPath
     */
    private static void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {
                    File files[] = file.listFiles();
                    for (File file1 : files) {
                        deleteFolderFile(file1.getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {
                        file.delete();
                    } else {
                        if (file.listFiles().length == 0) {
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 格式化单位
     * @param size size
     * @return size
     */
    private static String getFormatSize(double size) {

        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "B";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }
}

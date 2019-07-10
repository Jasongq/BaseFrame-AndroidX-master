package com.jason.rxhttp.download;


import com.jason.rxhttp.factory.ApiFactory;
import com.jason.rxhttp.interceptor.Transformer;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;

/**
 * 为下载单独建一个retrofit
 */

public class DownloadHelper {
    public static Observable<ResponseBody> downloadFile(String fileUrl) {
        String DEFAULT_DOWNLOAD_KEY = "defaultDownloadUrlKey";
        String DEFAULT_BASE_URL = "https://api.github.com/";
        return ApiFactory.getInstance()
                .setOkClient(new OkHttpClient())
                .createApi(DEFAULT_DOWNLOAD_KEY, DEFAULT_BASE_URL, DownloadApi.class)
                .downloadFile(fileUrl)
                .compose(Transformer.<ResponseBody>switchSchedulers());
    }
}

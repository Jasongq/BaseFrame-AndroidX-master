package com.jiuye.baseframex.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.jiuye.baseframex.MyApplication;


/**
 * <pre>
 *     author : GuoQiang
 *     e-mail : 849199845@qq.com
 *     time   : 2018/01/25
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class ClipboardUtil {
    private ClipboardUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 复制文本到剪贴板
     * @param mText
     */
    public static void copyText(String mText) {
        ClipboardManager clipboardManager = (ClipboardManager) MyApplication.getInstance().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData mClipData = ClipData.newPlainText("mLabel", mText);
        if (clipboardManager != null) {
            clipboardManager.setPrimaryClip(mClipData);
        }
    }

    /**
     * 获取剪贴板的文本
     * @return 剪贴板的文本
     */
    public static CharSequence getText() {
        ClipboardManager clipboard = (ClipboardManager) MyApplication.getInstance().getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard == null) return null;
        ClipData clip = clipboard.getPrimaryClip();
        if (clip != null && clip.getItemCount() > 0) {
            return clip.getItemAt(0).coerceToText(MyApplication.getInstance());
        }
        return null;
    }

    /**
     * 复制uri到剪贴板
     * @param uri uri
     */
    public static void copyUri(Uri uri) {
        ClipboardManager clipboard = (ClipboardManager) MyApplication.getInstance().getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard != null) {
            clipboard.setPrimaryClip(ClipData.newUri(MyApplication.getInstance().getContentResolver(), "mUri", uri));
        }
    }

    /**
     * 获取剪贴板的uri
     * @return 剪贴板的uri
     */
    public static Uri getUri() {
        ClipboardManager clipboard = (ClipboardManager) MyApplication.getInstance().getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard == null) return null;
        ClipData clip = clipboard.getPrimaryClip();
        if (clip != null && clip.getItemCount() > 0) {
            return clip.getItemAt(0).getUri();
        }
        return null;
    }

    /**
     * 复制意图到剪贴板
     * @param intent 意图
     */
    public static void copyIntent(Intent intent) {
        ClipboardManager clipboard = (ClipboardManager) MyApplication.getInstance().getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard != null) {
            clipboard.setPrimaryClip(ClipData.newIntent("mIntent", intent));
        }
    }

    /**
     * 获取剪贴板的意图
     * @return 剪贴板的意图
     */
    public static Intent getIntent() {
        ClipboardManager clipboard = (ClipboardManager) MyApplication.getInstance().getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard == null) return null;
        ClipData clip = clipboard.getPrimaryClip();
        if (clip != null && clip.getItemCount() > 0) {
            return clip.getItemAt(0).getIntent();
        }
        return null;
    }
}

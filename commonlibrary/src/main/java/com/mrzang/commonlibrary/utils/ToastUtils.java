package com.mrzang.commonlibrary.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by mr.zang on ${DATA}
 * Ui Toast 通知方法类
 */
public class ToastUtils {
    /**
     * @param context
     * @param message
     */
    public static void shortToastMessage(Context context, String message) {
        if (context == null || TextUtils.isEmpty(message)) return;
        Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param context
     * @param message
     */
    public static void longToastMessage(Context context, String message) {
        if (context == null || TextUtils.isEmpty(message)) return;
        Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    /**
     * 显示网络连接异常
     */
    public static void showErrorToast(Context context) {
        if (context == null) return;
        Toast.makeText(context, "网络连接异常!",
                Toast.LENGTH_SHORT);
    }
}

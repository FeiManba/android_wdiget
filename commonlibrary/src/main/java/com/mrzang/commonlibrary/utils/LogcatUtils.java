package com.mrzang.commonlibrary.utils;

import android.util.Log;

/**
 * 日志输出全局管理类
 */
public class LogcatUtils {

    private boolean isOutputLog = true; //是否输入日志

    private static LogcatUtils mInstance;

    private LogcatUtils() {
    }

    public void setOutputLog(boolean outputLog) {
        isOutputLog = outputLog;
    }

    private static final String TAG = "JC"; // joyssom common

    public static LogcatUtils getInstance() {
        if (mInstance == null) {
            synchronized (LogcatUtils.class) {
                mInstance = new LogcatUtils();
            }
        }
        return mInstance;
    }

    /**
     * 输出 Information 信息
     *
     * @param information
     */
    public void logI(String information) {
        if (isOutputLog) Log.i(TAG, "logI: " + information);
    }

    /**
     * 输出 Error 信息
     *
     * @param error
     */
    public void logE(String error) {
        if (isOutputLog) Log.e(TAG, "logE: " + error, null);
    }

    /**
     * 输出 debug 信息
     *
     * @param information
     */
    public void logD(String information) {
        if (isOutputLog) Log.d(TAG, "logD: " + information);
    }
}

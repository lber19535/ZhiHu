package com.bill.zhihu.api.utils;

import android.util.Log;

import com.bill.zhihu.api.BuildConfig;

import java.util.HashMap;

/**
 * 日志工具
 *
 * @author Bill Lv
 */
public class ZhihuLog {

    public static boolean Debug = true;
    /*
     * 由于android的logcat对msg长度的限制（超出的部分将不会被打印出来），各个版本也不一样
     * 基本在4600左右，这里设置了一个略微小一点的值，对msg进行拆分
     */
    private static final int LOG_MSG_MAX_LEN = 4000;

    private static HashMap<String, Boolean> debugMap = new HashMap<String, Boolean>();

    /**
     * 输出log
     *
     * @param TAG
     * @param msg
     */
    public static void d(String TAG, Object msg) {
        if (msg == null)
            msg = "null";
        String logMsg = msg.toString();
        int logLength = logMsg.length();
        if (logLength > LOG_MSG_MAX_LEN) {
            for (int i = 0; i < logLength / LOG_MSG_MAX_LEN; i++) {
                Log.d(TAG,
                        logMsg.substring(i * LOG_MSG_MAX_LEN, (i + 1)
                                * LOG_MSG_MAX_LEN));
            }
        } else {
            Log.d(TAG, logMsg);
        }
    }

    /**
     * 简化输出变量的方法
     *
     * @param TAG
     * @param valueName
     * @param value
     */
    public static void dValue(String TAG, String valueName, Object value) {
        if (Debug)
            d(TAG, valueName + " " + value);
    }

    public static  void dFlag(String TAG, String flag){
        if (Debug)
            d(TAG, "========== " + flag + " ==========");
    }
}

package com.bill.zhihu.api.utils;

import java.util.HashMap;

import android.util.Log;

/**
 * 日志工具
 * 
 * @author Bill Lv
 *
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
        if (haveLog(TAG)) {
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
    }

    /**
     * 简化输出变量的方法
     * 
     * @param TAG
     * @param valueName
     * @param value
     */
    public static void dValue(String TAG, String valueName, Object value) {
        d(TAG, valueName + " " + value.toString());
    }

    /**
     * 设置TAG是否要输出log
     * 
     * @param TAG
     * @param able
     */
    public static void setDebugable(String TAG, boolean able) {
        debugMap.put(TAG, able);
    }

    private static boolean haveLog(String TAG) {
        return Debug && tagHaveLog(TAG);
    }

    private static boolean tagHaveLog(String TAG) {
        if (debugMap.containsKey(TAG)) {
            if (debugMap.get(TAG)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}

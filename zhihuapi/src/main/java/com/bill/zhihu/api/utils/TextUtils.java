package com.bill.zhihu.api.utils;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

import java.util.List;

/**
 * text 工具类
 *
 * @author Bill Lv
 */
public class TextUtils {
    private static final String TAG = "TextUtils";


    /**
     * 给String着色
     *
     * @param src     源字符串
     * @param color   着色color
     * @param targets 着色字段
     * @return
     */
    public static SpannableStringBuilder getColorString(String src, int color,
                                                        List<String> targets) {
        return getColorString(src, color, targets.toArray(new String[]{}));
    }

    /**
     * 给String着色
     *
     * @param src     源字符串
     * @param color   着色color
     * @param targets 着色字段
     * @return
     */
    public static SpannableStringBuilder getColorString(String src, int color,
                                                        String... targets) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(src);
        ZhihuLog.dValue(TAG, " spannable string src ", src);
        for (String string : targets) {
            int start = src.indexOf(string);
            int end = start + string.length();
            ZhihuLog.dValue(TAG, " spannable string src start", start);
            ZhihuLog.dValue(TAG, " spannable string src end", end);
            ZhihuLog.dValue(TAG, " spannable string  ", string);
            ForegroundColorSpan fcs = new ForegroundColorSpan(color);
            ssb.setSpan(fcs, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        }
        return ssb;
    }

    /**
     * 知乎赞同票数的缩略形式
     *
     * @param num
     * @return
     */
    public static String getSummaryNumber(int num) {
        return getSummaryNumber(String.valueOf(num));
    }

    /**
     * 知乎赞同票数的缩略形式
     *
     * @param num
     * @return
     */
    public static String getSummaryNumber(String num) {
        int length = num.length();
        if (length <= 3) {
            return num;
        } else if (length <= 6) {
            return num.subSequence(0, length - 3) + "k";
        } else if (length <= 9) {
            return num.subSequence(0, length - 6) + "m";
        }
        return num;

    }

    /**
     * 首字母大写
     *
     * @param str
     * @return
     */
    public static String upperHead(String str) {
        String[] array = str.split("");
        return str.replaceFirst(array[1], array[1].toUpperCase());
    }

}
